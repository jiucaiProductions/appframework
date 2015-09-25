package org.jiucai.appframework.base.web.render;

import java.io.PrintWriter;

import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Render的基类，实现共有的 output方法
 *
 * @author zhaidw
 *
 */
public abstract class BaseRender implements Render {

    protected Logger log = LoggerFactory.getLogger(getClass());

    public void output(HttpServletResponse response, String msg) {
        PrintWriter out = null;
        try {
            // 必须放在 response.getWriter(); 之前否则不起作用
            response.addHeader("Content-Type", getContentType());
            out = response.getWriter();
            out.write(msg);
        } catch (Exception e) {
            log.error("output failed:", e);
        } finally {
            if (null != out) {
                out.close();
            }
        }

    }

}
