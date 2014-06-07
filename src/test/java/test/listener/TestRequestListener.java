package test.listener;

import javax.servlet.annotation.WebListener;
import javax.servlet.http.HttpServletRequest;

import org.jiucai.appframework.base.listener.AbstractSecureRequestListener;

import test.web.TestAppController;

@WebListener
public class TestRequestListener extends AbstractSecureRequestListener {

	@Override
	public void doSecureRequest(HttpServletRequest request) {

	}

	@Override
	public String getReqPrefix() {
		return TestAppController.REQ_PREFIX;
	}

}
