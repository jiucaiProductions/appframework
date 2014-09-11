package org.jiucai.appframework.base.web.render;

import org.springframework.stereotype.Service;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.HierarchicalStreamDriver;
import com.thoughtworks.xstream.io.xml.XppDriver;

@Service("xmlRender")
public class XmlRender extends BaseRender {

	private String encoding;

	private static XStream stream;
	private static HierarchicalStreamDriver streamDriver = new XppDriver();

	static {

		stream = new XStream(streamDriver);

		stream.setMode(XStream.NO_REFERENCES);

		// // 转换条件中的日期对象
		// stream.registerConverter(new DateConverter(Constant.FMT_DATETIME,
		// dateFormats));
		// // 转换数据库查询结果中的日期字段
		// stream.registerConverter(new CustomTimeStampConverter(
		// Constant.FMT_DATETIME));
		// stream.registerConverter(new CustomFloatConverter());

	}

	public XmlRender() {
		super();
	}

	public XmlRender(String encoding) {
		this.encoding = encoding;
	}

	public XStream getXStream() {
		return stream;
	}

	public String getEncoding() {
		return encoding;
	}

	public void setEncoding(String encoding) {
		this.encoding = encoding;
	}

	@Override
	public String getContentType() {
		return "text/xml; charset=" + encoding;
	}

	@Override
	public String getString(Object data) {
		String result = null;
		result = stream.toXML(data);
		return result;
	}

}
