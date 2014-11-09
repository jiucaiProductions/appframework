package org.jiucai.appframework.base.web;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.exception.ExceptionUtils;
import org.jiucai.appframework.base.helper.AppRequestHelper;
import org.jiucai.appframework.base.helper.SpringHelper;
import org.jiucai.appframework.base.mapper.ParameterMapper;
import org.jiucai.appframework.base.service.BinaryService;
import org.jiucai.appframework.base.service.DataService;
import org.jiucai.appframework.base.service.DownloadService;
import org.jiucai.appframework.base.service.PageService;
import org.jiucai.appframework.base.service.UploadService;
import org.jiucai.appframework.common.util.EscapeUtil;
import org.springframework.web.multipart.MultipartHttpServletRequest;

/**
 * 应用前端统一控制器
 *
 * <pre>
 * 页面请求 /ControllerPATH/p?sid=pageServiceId&mid=mapperId&pid=1
 * 数据请求 /ControllerPATH/d?sid=dataServiceId&mid=mapperId&pid=1
 * </pre>
 *
 * @author zhaidw
 *
 */
public abstract class AbstractBaseController extends BaseController {

    /**
     * binary service 404 的图片文件classpath 路径
     */
    protected static String IMG_404 = "static/404";

    /**
     * Request 请求中的 服务ID 参数名
     */
    protected static String REQ_SID = "sid";

    /**
     * Request 请求中的 Mapper服务ID 参数名
     */
    protected static String REQ_MID = "mid";

    /**
     * 二进制请求
     *
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    public void binary(final String serviceId, HttpServletRequest request,
            HttpServletResponse response) throws ServletException, IOException {
        String errorMsg = "";
        Map<String, String> reqParam = parseRequest(request);
        // String serviceId = reqParam.get(getRequestServiceId());
        String mapperId = reqParam.get(getMapperId());
        Boolean errFlag = false;
        Boolean isFileExists = false;

        if (StringUtils.isBlank(mapperId)) {
            mapperId = getDefaultMapperId();
            log.debug("param mapperId not found, use default mapperId: " + mapperId);
        }

        if (StringUtils.isBlank(serviceId)) {
            errFlag = true;
            errorMsg = "service id is required.";
        }

        if (!errFlag) {
            BinaryService service = null;
            ParameterMapper mapper = null;

            try {
                service = SpringHelper.getBean(serviceId + "BinaryService", BinaryService.class);

                if (null != service) {

                    mapper = SpringHelper.getBean(mapperId + "ParameterMapper",
                            ParameterMapper.class);

                    if (null != mapper) {
                        isFileExists = service.fileExists(mapper);

                        if (isFileExists) {

                            response.addHeader("Cache-Control", "must-revalidate");
                            response.addHeader("Cache-Control", "no-cache");
                            response.addHeader("Cache-Control", "no-store");

                            response.setDateHeader("Expires", 0);

                            response.setHeader("Pragma", "no-cache");
                            response.setHeader("Connection", "close");

                            service.handleRequest(mapper, response.getOutputStream());

                        } else {
                            errFlag = true;
                            errorMsg = "file not exists.";

                            response.addHeader("Cache-Control", "must-revalidate");
                            response.addHeader("Cache-Control", "no-cache");
                            response.addHeader("Cache-Control", "no-store");

                            response.setDateHeader("Expires", 0);

                            response.setHeader("Pragma", "no-cache");
                            response.setHeader("Connection", "close");

                            output404File(response.getOutputStream());

                        }
                    } else {
                        errFlag = true;
                        errorMsg = "mapper " + mapperId + " not exists.";
                    }

                } else {
                    errFlag = true;
                    errorMsg = "service " + serviceId + " not exists.";
                }

            } catch (Exception e) {

                log.error("binary service failed: " + ExceptionUtils.getFullStackTrace(e));

                String errMsg = ExceptionUtils.getRootCauseMessage(e);
                errFlag = true;
                errorMsg = "service " + serviceId + " failed: " + errMsg;
            }

        }

        if (errFlag) {
            log.error("binary service failed: " + errorMsg);

            request.setAttribute("failed_msg", "请求的文件不存在或已经被删除。");

            return;
        }

    }

    /**
     * 数据请求
     *
     * @param model
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    public void data(final String serviceId, Map<String, Object> model, HttpServletRequest request,
            HttpServletResponse response) throws ServletException, IOException {
        String resultData = null;
        String contentType = "application/json; charset=UTF-8";
        String dataType = DataService.DATA_TYPE_JSON;
        Map<String, String> reqParam = parseRequest(request);
        // String serviceId = reqParam.get(getRequestServiceId());
        String mapperId = reqParam.get(getMapperId());
        Boolean errFlag = false;

        if (StringUtils.isBlank(mapperId)) {
            mapperId = getDefaultMapperId();
            log.debug("param mapperId not found, use default mapperId: " + mapperId);
        }

        if (StringUtils.isBlank(serviceId)) {
            errFlag = true;
            resultData = formatMsg(dataType, "service id is required.");
        }

        if (!errFlag) {

            DataService service = null;
            ParameterMapper mapper = null;

            try {
                service = SpringHelper.getBean(serviceId + "DataService", DataService.class);

                if (null != service) {

                    mapper = SpringHelper.getBean(mapperId + "ParameterMapper",
                            ParameterMapper.class);

                    if (null != mapper) {
                        resultData = service.handleRequest(mapper);
                        contentType = service.getContentType();

                    } else {
                        errFlag = true;
                        resultData = formatMsg(dataType, "mapper " + mapperId + " not exists.");
                    }

                } else {
                    errFlag = true;
                    resultData = formatMsg(dataType, "service " + serviceId + " not exists.");
                }

            } catch (Exception e) {
                log.error("data service failed: " + ExceptionUtils.getFullStackTrace(e));
                String errMsg = ExceptionUtils.getRootCauseMessage(e);
                errFlag = true;
                resultData = formatMsg(dataType, "service " + serviceId + " failed: " + errMsg);
            }

        }

        if (errFlag) {
            log.error("data service failed: " + resultData);
        }

        output(response, resultData, contentType);

    }

    /**
     * 下载请求
     *
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    public void download(final String serviceId, HttpServletRequest request,
            HttpServletResponse response) throws ServletException, IOException {
        String errorMsg = "";
        Map<String, String> reqParam = parseRequest(request);
        // String serviceId = reqParam.get(getRequestServiceId());
        String mapperId = reqParam.get(getMapperId());
        Boolean errFlag = false;
        String fileName = null;
        Boolean isFileExists = false;

        if (StringUtils.isBlank(mapperId)) {
            mapperId = getDefaultMapperId();
            log.debug("param mapperId not found, use default mapperId: " + mapperId);
        }

        if (StringUtils.isBlank(serviceId)) {
            errFlag = true;
            errorMsg = "service id is required.";
        }

        if (!errFlag) {
            DownloadService service = null;
            ParameterMapper mapper = null;

            try {
                service = SpringHelper
                        .getBean(serviceId + "DownloadService", DownloadService.class);

                if (null != service) {

                    mapper = SpringHelper.getBean(mapperId + "ParameterMapper",
                            ParameterMapper.class);

                    if (null != mapper) {

                        fileName = service.getFileName(mapper);
                        isFileExists = service.fileExists(mapper);

                        log.info("download file name:" + fileName);

                        if (isFileExists && StringUtils.isNotBlank(fileName)) {

                            // 下载的文件名中不能包含空格，否则浏览器无法解析
                            fileName = fileName.trim().replace(" ", "");

                            if (request.getHeader("User-Agent").toUpperCase().indexOf("MSIE") > 0) {
                                fileName = URLEncoder.encode(fileName, "UTF-8");// IE浏览器
                            } else {
                                fileName = new String(fileName.getBytes("UTF-8"), "ISO8859-1");// 非IE浏览器
                            }

                            response.setContentType(service.getContentType());

                            response.addHeader("Cache-Control", "must-revalidate");
                            response.addHeader("Cache-Control", "no-cache");
                            response.addHeader("Cache-Control", "no-store");

                            response.setDateHeader("Expires", 0);

                            response.setHeader("Pragma", "no-cache");
                            response.setHeader("Connection", "close");

                            response.setHeader("Content-Disposition", "attachment;filename="
                                    + fileName);

                            service.handleRequest(mapper, response.getOutputStream());

                        } else {
                            errFlag = true;
                            errorMsg = "file not exists or download file name is null.";
                            response.sendError(HttpServletResponse.SC_NOT_FOUND, errorMsg);
                            return;
                        }

                    } else {
                        errFlag = true;
                        errorMsg = "mapper " + mapperId + " not exists.";
                    }

                } else {
                    errFlag = true;
                    errorMsg = "service " + serviceId + " not exists.";
                }

            } catch (Exception e) {

                log.error("download service failed: " + ExceptionUtils.getFullStackTrace(e));

                String errMsg = ExceptionUtils.getRootCauseMessage(e);
                errFlag = true;
                errorMsg = "service " + serviceId + " failed: " + errMsg;
            }

        }

        if (errFlag) {
            log.error("download service failed: " + errorMsg);

            request.setAttribute("failed_msg", "请求的文件不存在或已经被删除。");
            // request.getRequestDispatcher("/common/error/session_failed.jsp").forward(request,
            // response);

            String path = request.getContextPath();
            String basePath = new StringBuffer(request.getScheme()).append("://")
                    .append(request.getServerName()).append(":").append(request.getServerPort())
                    .append(path).toString();

            response.sendRedirect(basePath + "/common/error/session_failed.jsp");
            return;
        }

    }

    public String get404ImageClasspath() {
        return IMG_404;
    }

    /**
     * app default ParameterMapper beanId
     * 
     * @return 返回接口 {@link org.jiucai.appframework.base.mapper.ParameterMapper }
     *         的实现类的 beanId
     */
    public String getDefaultMapperId() {
        return "default";
    }

    public String getMapperId() {
        return REQ_MID;
    }

    /**
     * request attribute 中的请求参数的前缀
     * 
     * @return 放在 request attribute 中的请求参数的前缀
     */
    public abstract String getReqPrefix();

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
    public String page(final String serviceId, Map<String, Object> model,
            HttpServletRequest request, HttpServletResponse response) throws ServletException,
            IOException {
        String errorMsg = "";

        String templatePage = "error/404";
        Boolean errFlag = false;

        Map<String, String> reqParam = parseRequest(request);
        // String serviceId = reqParam.get(getRequestServiceId());
        String mapperId = reqParam.get(getMapperId());

        if (StringUtils.isBlank(mapperId)) {
            mapperId = getDefaultMapperId();
            log.debug("param mapperId not found, use default mapperId: " + mapperId);
        }

        if (StringUtils.isBlank(serviceId)) {
            errFlag = true;
            errorMsg = "service id is required.";
        }

        if (!errFlag) {

            PageService service = null;
            ParameterMapper mapper = null;

            try {

                service = SpringHelper.getBean(serviceId + "PageService", PageService.class);

                if (null != service) {

                    mapper = SpringHelper.getBean(mapperId + "ParameterMapper",
                            ParameterMapper.class);

                    if (null != mapper) {
                        templatePage = service.handleRequest(mapper, model);
                    } else {
                        errFlag = true;
                        errorMsg = "mapper " + mapperId + " not exists.";
                    }

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

        }

        if (errFlag) {
            log.error("page service failed: " + errorMsg);

            model.put("errorMsg", "系统忙，请稍后再试。");
        }

        return templatePage;

    }

    public Map<String, String> parseRequest(HttpServletRequest request) {

        return AppRequestHelper.parseRequest(request, getReqPrefix());
    }

    /**
     * 下载请求
     *
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    public void upload(final String serviceId, MultipartHttpServletRequest request,
            HttpServletResponse response) throws ServletException, IOException {

        String resultData = null;
        String contentType = "application/json; charset=UTF-8";
        String dataType = DataService.DATA_TYPE_JSON;
        Map<String, String> reqParam = parseRequest(request);
        // String serviceId = reqParam.get(getRequestServiceId());
        String mapperId = reqParam.get(getMapperId());
        Boolean errFlag = false;

        if (StringUtils.isBlank(mapperId)) {
            mapperId = getDefaultMapperId();
            log.debug("param mapperId not found, use default mapperId: " + mapperId);
        }

        if (StringUtils.isBlank(serviceId)) {
            errFlag = true;
            resultData = formatMsg(dataType, "service id is required.");
        }

        if (!errFlag) {
            UploadService service = null;
            ParameterMapper mapper = null;
            try {
                service = SpringHelper.getBean(serviceId + "UploadService", UploadService.class);

                if (null != service) {

                    mapper = SpringHelper.getBean(mapperId + "ParameterMapper",
                            ParameterMapper.class);

                    if (null != mapper) {
                        resultData = service.handleRequest(mapper, request);
                        contentType = service.getContentType();

                    } else {
                        errFlag = true;
                        resultData = formatMsg(dataType, "mapper " + mapperId + " not exists.");
                    }

                } else {
                    errFlag = true;
                    resultData = formatMsg(dataType, "service " + serviceId + " not exists.");
                }

            } catch (Exception e) {
                log.error("upload service failed: " + ExceptionUtils.getFullStackTrace(e));
                String errMsg = ExceptionUtils.getRootCauseMessage(e);
                errFlag = true;
                resultData = formatMsg(dataType, "service " + serviceId + " failed: " + errMsg);
            }

        }

        if (errFlag) {
            log.error("upload service failed: " + resultData);
        }

        output(response, resultData, contentType);

    }

    protected String formatMsg(String fmt, String data) {
        String result = data;

        if (DataService.DATA_TYPE_JSON.equalsIgnoreCase(fmt)) {

            result = result.replaceAll("\\\n", "<br/>");
            result = result.replaceAll("\\\r", "");
            result = result.replaceAll("\\\t", "&nbsp;&nbsp;&nbsp;&nbsp;");

            result = new StringBuffer("{\"error\": \"").append(EscapeUtil.escape(data))
                    .append("\"}").toString();

        } else if (DataService.DATA_TYPE_XML.equalsIgnoreCase(fmt)) {

            result = new StringBuffer("<error>").append(EscapeUtil.escape(data)).append("<error>")
                    .toString();

        } else {
            // default
        }

        return result;
    }

    protected void output404File(OutputStream out) {

        try {
            File file = getFileFromClasspath(get404ImageClasspath());
            FileInputStream in = new FileInputStream(file);
            IOUtils.copy(in, out);
            out.close();
            in.close();
        } catch (Throwable e) {
            log.error("output404File failed:", e);
        }
    }

}
