package com.laowen.controller;

import com.alibaba.fastjson.JSON;
import com.laowen.bean.WXGetway;
import com.laowen.service.WXGatewayService;
import com.laowen.utils.PropUtil;
import com.laowen.utils.Result;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;

@Controller
@RequestMapping("/wx")
public class WXController {

    private Logger log = LoggerFactory.getLogger(getClass());

    @Autowired
    private WXGatewayService wxGatewayService;

    @RequestMapping("/index")
    public void index(HttpServletRequest request, HttpServletResponse response) throws Exception {
        log.info("微信请求参数:{}", JSON.toJSONString(request.getParameterMap()));
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");
        String appId = PropUtil.getProp("appId");
        String companyId = PropUtil.getProp("companyId");
        String result = "";
        // 判断是否是微信接入激活验证，只有首次接入验证时才会收到echostr参数，此时需要把它直接返回
        String echostr = request.getParameter("echostr");
        if (StringUtils.isNotBlank(echostr)) {
            String signature = request.getParameter("signature");// 微信加密签名signature结合了开发者填写的token参数和请求中的timestamp参数、nonce参数。
            String timestamp = request.getParameter("timestamp");// 时间戳
            String nonce = request.getParameter("nonce");// 随机数

            Result<WXGetway> wxGetwayResult = wxGatewayService.findByAppIdAndCompanyId(appId, companyId);
            if (!wxGetwayResult.getCode()) {
                log.error(wxGetwayResult.getMsg());
                return;
            }
            WXGetway wxGetway = wxGetwayResult.getT();
            log.debug("微信网关为:{}", JSON.toJSONString(wxGetway));
            if (null == wxGetway) {
                log.info("请求参数未找到风关, appId:{}, companyId:{}", appId, companyId);
                return;
            }
            String TOKEN = wxGetway.getWxToken();
            //
            String sortString = sort(TOKEN, timestamp, nonce);// 排序
            String mySignature = sha1(sortString);// 加密
            //
            if (mySignature != null && mySignature != "" && mySignature.equals(signature)) {// 校验签名
                log.info("微信首次接口配置校验成功...");
                result = echostr;
            } else {
                log.info("微信首次接口配置校验失败...");
            }
        } else { // 正常的微信处理流程
            StringBuffer sb = new StringBuffer();
            InputStream is = request.getInputStream();
            InputStreamReader isr = new InputStreamReader(is, "UTF-8");
            BufferedReader br = new BufferedReader(isr);
            String tmp = "";
            while ((tmp = br.readLine()) != null) {
                sb.append(tmp);
            }
            String xml = sb.toString(); // 此即为接收到微信端发送过来的xml数据
            log.warn("微信服务器转发进来的消息:{}...", xml);
            if (null != br) {
                br.close();
            }
            if (null != isr) {
                isr.close();
            }
            if (null != is) {
                is.close();
            }
            Result<String> answerResult = wxGatewayService.autoAnswer(xml);
            if (!answerResult.getCode()) {
                log.error(answerResult.getMsg());
                return;
            }
            result = answerResult.getT();
            log.info("请求微信服务转发出去的消息:{}...", result);
        }

        try {
            OutputStream os = response.getOutputStream();
            os.write(result.getBytes("UTF-8"));
            os.flush();
            os.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 排序方法
     *
     * @param token
     * @param timestamp
     * @param nonce
     * @return
     */
    private String sort(String token, String timestamp, String nonce) {
        String[] strArray = {token, timestamp, nonce};
        Arrays.sort(strArray);
        StringBuilder sb = new StringBuilder();
        for (String str : strArray) {
            sb.append(str);
        }
        return sb.toString();
    }

    /**
     * 将字符串进行sha1加密
     *
     * @param str 需要加密的字符串
     * @return 加密后的内容
     */
    private String sha1(String str) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-1");
            digest.update(str.getBytes());
            byte messageDigest[] = digest.digest();
            // Create Hex String
            StringBuffer hexString = new StringBuffer();
            // 字节数组转换为 十六进制 数
            for (int i = 0; i < messageDigest.length; i++) {
                String shaHex = Integer.toHexString(messageDigest[i] & 0xFF);
                if (shaHex.length() < 2) {
                    hexString.append(0);
                }
                hexString.append(shaHex);
            }
            return hexString.toString();
        } catch (NoSuchAlgorithmException e) {
            log.error("字符串:{} 进行sha1加密失败...", str);
        }
        return "";
    }
}
