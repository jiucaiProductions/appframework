package test.zdw.web;

import java.io.IOException;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.jiucai.appframework.base.web.AbstractBaseController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import test.web.TestAppController;

@Controller
@RequestMapping("/zdw")
public class AppZdwController extends AbstractBaseController {

    @RequestMapping("/binary/{serviceId}")
    @Override
    public void binary(@PathVariable("serviceId") String serviceId, HttpServletRequest request,
            HttpServletResponse response) throws ServletException, IOException {
        super.binary(serviceId, request, response);
    }

    @RequestMapping("/d/{serviceId}")
    @Override
    public void data(@PathVariable("serviceId") String serviceId, Map<String, Object> model,
            HttpServletRequest request, HttpServletResponse response) throws ServletException,
            IOException {
        super.data(serviceId, model, request, response);

    }

    @RequestMapping("/download/{serviceId}")
    @Override
    public void download(@PathVariable("serviceId") String serviceId, HttpServletRequest request,
            HttpServletResponse response) throws ServletException, IOException {
        super.download(serviceId, request, response);
    }

    @Override
    public String getDefaultMapperId() {
        return TestAppController.DEFAULT_MAPPER_ID;
    }

    @Override
    public String getReqPrefix() {
        return TestAppController.REQ_PREFIX;
    }

    @RequestMapping("/p/{serviceId}")
    @Override
    public String page(@PathVariable("serviceId") String serviceId, Map<String, Object> model,
            HttpServletRequest request, HttpServletResponse response) throws ServletException,
            IOException {
        return super.page(serviceId, model, request, response);

    }

    @RequestMapping("/upload/{serviceId}")
    @Override
    public void upload(@PathVariable("serviceId") String serviceId,
            MultipartHttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        super.upload(serviceId, request, response);
    }

}
