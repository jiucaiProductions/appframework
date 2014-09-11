package test.qht.web;

import java.io.IOException;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import test.web.TestAppController;



@Controller
@RequestMapping("/test")
public class AppController<T> extends AbstractAppController<T> {
    
    @Override
    public String getReqPrefix() {
        return TestAppController.REQ_PREFIX;
    }


    @RequestMapping("/p")
    @Override
    public String page(Map<String, Object> model, HttpServletRequest request, HttpServletResponse response, T t)
            throws ServletException, IOException {
        return super.page(model, request, response, t);
    }

}
