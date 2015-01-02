package org.jiucai.test.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.exception.ExceptionUtils;
import org.jiucai.appframework.base.util.HttpClientUtil;
import org.jiucai.appframework.base.web.BaseController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/xhr")
public class XHRController extends BaseController {

	@RequestMapping(value = "req")
	public void req(HttpServletRequest request, HttpServletResponse response) {
		String result = "";
		String url = "";
		String charset;
		url = request.getParameter("url");
		
		if(StringUtils.isBlank(url)){
			url = "http://www.sodao.com/ShowTime/gt1";
		}
		
		charset = request.getParameter("charset");
		if (null == charset) {
			charset = "utf-8";
		}

		String contentType =  "text/plain; charset=" + charset;
		// 必须放在 response.getWriter(); 之前否则不起作用
		response.addHeader("Content-Type", contentType);

		try {
			result = HttpClientUtil.get(url, null, charset);

			output(response, result, contentType);

		} catch (Exception e) {
			log.error("requset failed: ", e);
			output(response, ExceptionUtils.getRootCauseMessage(e), contentType);
		}

	}

}
