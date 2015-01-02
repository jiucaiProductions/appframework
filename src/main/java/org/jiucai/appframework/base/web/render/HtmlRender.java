package org.jiucai.appframework.base.web.render;

import org.springframework.stereotype.Service;

@Service("htmlRender")
public class HtmlRender extends BaseRender {
	private String encoding;

	public HtmlRender() {
		super();
	}

	public HtmlRender(String encoding) {
		this.encoding = encoding;
	}

	public String getEncoding() {
		return encoding;
	}

	public void setEncoding(String encoding) {
		this.encoding = encoding;
	}

	@Override
	public String getContentType() {
		return "text/html;charset=" + encoding;
	}

	@Override
	public String getString(Object data){
		String result = null;
		result = (String) data;
		return result;
	}

}
