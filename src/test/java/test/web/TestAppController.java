package test.web;

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

@Controller
@RequestMapping("/testapp")
public class TestAppController extends AbstractBaseController {

    public static final String REQ_PREFIX = "_test_app_";
    public static final String DEFAULT_MAPPER_ID = "testDefault";

    @Override
    @RequestMapping("/binary/{serviceId}")
    public void binary(@PathVariable("serviceId") String serviceId, HttpServletRequest request,
            HttpServletResponse response) throws ServletException, IOException {
        super.binary(serviceId, request, response);
    }

    @Override
    @RequestMapping("/d/{serviceId}")
    public void data(@PathVariable("serviceId") String serviceId, Map<String, Object> model,
            HttpServletRequest request, HttpServletResponse response) throws ServletException,
            IOException {
        super.data(serviceId, model, request, response);
    }

    @Override
    @RequestMapping("/download/{serviceId}")
    public void download(@PathVariable("serviceId") String serviceId, HttpServletRequest request,
            HttpServletResponse response) throws ServletException, IOException {
        super.download(serviceId, request, response);

    }

    @Override
    public String getDefaultMapperId() {
        return DEFAULT_MAPPER_ID;
    }

    @Override
    public String getReqPrefix() {
        return REQ_PREFIX;
    }

    @Override
    @RequestMapping("/p/{serviceId}")
    public String page(@PathVariable("serviceId") String serviceId, Map<String, Object> model,
            HttpServletRequest request, HttpServletResponse response) throws ServletException,
            IOException {
        return super.page(serviceId, model, request, response);
    }

    @Override
    @RequestMapping("/upload/{serviceId}")
    public void upload(@PathVariable("serviceId") String serviceId,
            MultipartHttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        super.upload(serviceId, request, response);
    }
}
