package test.qht.web;

import java.io.IOException;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.exception.ExceptionUtils;
import org.jiucai.appframework.base.helper.SpringHelper;
import org.jiucai.appframework.base.web.BaseController;

import test.qht.service.PageService;

/**
 * 应用前端统一控制器
 * 
 * <pre>
 * 页面请求 /ControllerPATH/p?sid=pageServiceId&pid=1
 * 数据请求 /ControllerPATH/d?sid=dataServiceId&pid=1
 * </pre>
 * 
 * @author zhaidw
 */
public abstract class AbstractAppController<T> extends BaseController {

    protected static ClassLoader loader = Thread.currentThread()
            .getContextClassLoader();

    /**
     * binary service 404 的图片文件classpath 路径
     */
    protected static String IMG_404 = "static/404";

    /**
     * Request 请求中的 服务ID 参数名
     */
    protected static String REQ_SID = "sid";

    public String get404ImageClasspath() {
        return IMG_404;
    }

    public String getRequestServiceId() {
        return REQ_SID;
    }

    /**
     * 页面请求
     * 
     * @param model
     * @param request
     * @param response
     * @return view path
     * @throws ServletException
     * @throws IOException
     */
    public String page(Map<String, Object> model, HttpServletRequest request,
            HttpServletResponse response,T t) throws ServletException, IOException {
        String errorMsg = "";

        String templatePage = "error/404";
        Boolean errFlag = false;
        PageService<T> service = null;

        Map<String, String> reqParam = parseRequest(request);
        String serviceId = reqParam.get(getRequestServiceId());

        if (null != serviceId) {

            try {
                service = SpringHelper.getBean(serviceId + "PageService",
                        PageService.class);

                if (null != service) {
                    templatePage = service.handleRequest(reqParam, model,t);
                } else {
                    errFlag = true;
                    errorMsg = "service " + serviceId + " not exists.";
                }

            } catch (Exception e) {
                log.error("page service failed: " + ExceptionUtils.getFullStackTrace(e));

                String errMsg = ExceptionUtils.getRootCauseMessage(e);
                errFlag = true;
                errorMsg = "service " + serviceId + " failed: " + errMsg;
            }

        } else {
            errFlag = true;
            errorMsg = "service id is required.";
        }

        if (errFlag) {
            log.error("page service failed: " + errorMsg);

            model.put("errorMsg", "系统忙，请稍后再试。");
        }

        return templatePage;

    }

    public abstract String getReqPrefix();

    public Map<String, String> parseRequest(HttpServletRequest request) {

        Map<String, String> param = new HashMap<String, String>();
        Object reqAttr = null;
        String attrName, paraName;

        Enumeration<String> attrs = request.getAttributeNames();

        while (attrs.hasMoreElements()) {
            attrName = (String) attrs.nextElement();

            if (null != attrName && attrName.startsWith(getReqPrefix())) {

                paraName = attrName.substring(attrName.indexOf(getReqPrefix()) + getReqPrefix().length(),
                        attrName.length());

                reqAttr = request.getAttribute(attrName);
                String attrVal = "";
                if (null != reqAttr) {
                    attrVal = reqAttr.toString();
                }
                param.put(paraName, attrVal);
            }
        }
        return param;

    }

}
