package test.web;

import java.io.IOException;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.jiucai.appframework.base.web.AbstractBaseController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartHttpServletRequest;

@Controller
@RequestMapping("/testapp")
public class TestAppController extends AbstractBaseController {

	public static final String REQ_PREFIX = "_test_app_";
	public static final String DEFAULT_MAPPER_ID = "testDefault";
	
	@Override
	public String getReqPrefix() {
		return REQ_PREFIX;
	}
	
    public  String getDefaultMapperId(){
    	return DEFAULT_MAPPER_ID;
    }

	@RequestMapping("/p")
	public String page(Map<String, Object> model, HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		return super.page(model, request, response);
	}
	
	@RequestMapping("/d")
	public void data(Map<String, Object> model, HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		super.data(model, request, response);
	}
	
	@RequestMapping("/upload")
	public void upload(MultipartHttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException{
		
		super.upload(request, response);
	}
	
	@RequestMapping("/download")
	public void download(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		super.download(request, response);
	
	}
	
	@RequestMapping("/binary")
	public void binary(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		super.binary(request, response);
	}
}
