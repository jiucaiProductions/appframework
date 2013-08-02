package org.jiucai.appframework.base.spring.web;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.http.Header;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicHeader;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/xhr")
public class XHRController extends BaseController {

	@RequestMapping(value = "req")
	public void req(HttpServletRequest request, HttpServletResponse response) {
		StringBuffer sb = new StringBuffer();
		String result = "";
		String url = "http://www.sodao.com/ShowTime/gt1";
		String charset;

		url = request.getParameter("url");
		charset = request.getParameter("charset");
		if (null == charset) {
			charset = "utf-8";
		}

		// 必须放在 response.getWriter(); 之前否则不起作用
		response.addHeader("Content-Type", "text/plain; charset=" + charset);

		try {

			if (null == url) {
				throw new Exception("parameter url must not be null.");
			}

			HttpUriRequest req = new HttpGet(url);

			Header userAgent = new BasicHeader("User-Agent", "Mozilla/7.0");
			req.setHeader(userAgent);

			HttpClient client = new DefaultHttpClient();

			HttpResponse res = client.execute(req);
			InputStream inputStream = res.getEntity().getContent();

			BufferedReader in = new BufferedReader(new InputStreamReader(
					inputStream, "utf-8"));
			String line = null;

			while ((line = in.readLine()) != null) {
				sb.append(line);
			}

			result = sb.toString();

			log.debug("result: " + result);

			PrintWriter out = response.getWriter();
			out.write(result);
			out.close();

		} catch (Exception e) {
			log.error("requset failed: ", e);
			try {
				PrintWriter out = response.getWriter();
				out.write(e.getMessage());
				out.close();
			} catch (Exception ex) {
				log.error("requset failed: ", ex);
			}

		}

	}

}
