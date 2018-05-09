package com.laowen.tag;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.BodyTagSupport;
import java.io.IOException;

/**
 * Created by iyou on 2017-06-25.
 */
public class ContentTag extends BodyTagSupport {

    private static final long serialVersionUID = 1L;
    private Logger log = LoggerFactory.getLogger(this.getClass());

    @Override
    public int doAfterBody() throws JspException {
        return SKIP_BODY;
    }

    @Override
    public int doStartTag() throws JspException {
        return EVAL_BODY_BUFFERED;
    }

    @Override
    public int doEndTag() throws JspException {
        String content = this.bodyContent.getString();
        try {
            this.pageContext.getRequest().setAttribute(this.getContentPlaceHolderId(), content);
            this.bodyContent.clear();
        } catch (IOException e) {
            log.error(">>> com.laowen.tag.ContentTag#doEndTag error", e);
        }
        return EVAL_PAGE;//default
    }

    private String contentPlaceHolderId;

    public String getContentPlaceHolderId() {
        return contentPlaceHolderId;
    }

    public void setContentPlaceHolderId(String contentPlaceHolderId) {
        this.contentPlaceHolderId = contentPlaceHolderId;
    }

}
