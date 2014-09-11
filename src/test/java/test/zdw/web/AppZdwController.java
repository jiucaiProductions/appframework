package test.zdw.web;

import java.io.IOException;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.jiucai.appframework.base.web.AbstractBaseController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import test.web.TestAppController;


@Controller
@RequestMapping("/zdw")
public class AppZdwController extends AbstractBaseController {
    
    @Override
    public String getReqPrefix() {
        return TestAppController.REQ_PREFIX;
    }
    
    public  String getDefaultMapperId(){
    	return TestAppController.DEFAULT_MAPPER_ID;
    }

    @RequestMapping("/p")
    @Override
    public String page(Map<String, Object> model, HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
		return super.page(model, request, response);
		
    }

}
