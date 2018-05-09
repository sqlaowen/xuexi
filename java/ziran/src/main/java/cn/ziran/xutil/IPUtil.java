package cn.ziran.xutil;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;

/**
 * @ClassName: ZRUtil
 * @Description: TODO
 * @author: ziran
 * @date: 2015年9月14日 下午4:06:11
 */
public class IPUtil {

	/**
	 * @Title: getClientIp
	 * @Description: 取客户端IP
	 * @param request
	 *            HttpServletRequest
	 * @return: String
	 */
	public static String getClientIp(HttpServletRequest request) {
		String ip = request.getHeader("X-Forwarded-For");
		if (StringUtils.isNotEmpty(ip) && !"unKnown".equalsIgnoreCase(ip)) {
			// 多次反向代理后会有多个ip值，第一个ip才是真实ip
			int index = ip.indexOf(",");
			if (index != -1) {
				return ip.substring(0, index);
			} else {
				return ip;
			}
		}
		ip = request.getHeader("X-Real-IP");
		if (StringUtils.isNotEmpty(ip) && !"unKnown".equalsIgnoreCase(ip)) {
			return ip;
		}
		return request.getRemoteAddr();
	}
}
