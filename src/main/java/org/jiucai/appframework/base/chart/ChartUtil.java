package org.jiucai.appframework.base.chart;

import java.lang.reflect.Field;

import org.jiucai.appframework.base.chart.bean.ChartApply;
import org.jiucai.appframework.base.chart.bean.ChartCategory;
import org.jiucai.appframework.base.chart.bean.ChartCategoryGroup;
import org.jiucai.appframework.base.chart.bean.ChartDataSet;
import org.jiucai.appframework.base.chart.bean.ChartLine;
import org.jiucai.appframework.base.chart.bean.ChartSet;
import org.jiucai.appframework.base.chart.bean.ChartStyle;
import org.jiucai.appframework.base.chart.bean.ChartStyles;
import org.jiucai.appframework.base.chart.fc.MultiChart;
import org.jiucai.appframework.base.chart.fc.SingleChart;
import org.jiucai.appframework.base.spring.web.render.XmlRender;

import com.thoughtworks.xstream.XStream;

/**
 * chart 设置工具类
 * 
 * @author zhaidw
 * 
 */
public class ChartUtil {

	/**
	 * 指定class 转换为 xml 时，把其属性转换为XML的节点属性
	 * 
	 * @param x
	 *            XStream 实例
	 * @param clazz
	 *            Chart Bean 类
	 */
	public static void useFeildAsAttribute(XStream x, Class<?> clazz) {

		Class<?> parent = clazz.getSuperclass();

		if (null != parent) {
			useFeildAsAttribute(x, parent);
		}

		Field[] fields = clazz.getDeclaredFields();

		for (Field f : fields) {
			x.useAttributeFor(clazz, f.getName());
		}
	}

	private static void useFeildAsAttribute(XStream x) {

		// use class feild as xml attribute

		ChartUtil.useFeildAsAttribute(x, ChartCategory.class);
		ChartUtil.useFeildAsAttribute(x, ChartCategoryGroup.class);
		ChartUtil.useFeildAsAttribute(x, ChartDataSet.class);
		ChartUtil.useFeildAsAttribute(x, ChartLine.class);
		ChartUtil.useFeildAsAttribute(x, ChartSet.class);
		ChartUtil.useFeildAsAttribute(x, ChartStyles.class);
		ChartUtil.useFeildAsAttribute(x, ChartStyle.class);
		ChartUtil.useFeildAsAttribute(x, ChartApply.class);

		ChartUtil.useFeildAsAttribute(x, SingleChart.class);
		ChartUtil.useFeildAsAttribute(x, MultiChart.class);

		
		x.alias("category", ChartCategory.class);
		x.alias("dataset", ChartDataSet.class);

		x.alias("line", ChartLine.class);
		x.alias("set", ChartSet.class);
		x.alias("style", ChartStyle.class);
		x.alias("apply", ChartApply.class);
		
	}

	public static String chartData(XmlRender xmlRender, SingleChart chart)
			throws Exception {

		XStream x = xmlRender.getXStream();

		x.alias("chart", SingleChart.class);

		// Omit the 'sets' tag
		x.addImplicitCollection(SingleChart.class, "sets");

		useFeildAsAttribute(x);

		return xmlRender.getString(chart);

	}

	public static String chartData(XmlRender xmlRender, MultiChart chart)
			throws Exception {

		XStream x = xmlRender.getXStream();

		// alias class to node name
		x.alias("chart", MultiChart.class);

		// Omit the 'sets' tag
		x.addImplicitCollection(MultiChart.class, "datasets");
		x.addImplicitCollection(ChartCategoryGroup.class, "categoryList");
		x.addImplicitCollection(ChartDataSet.class, "sets");

		useFeildAsAttribute(x);

		return xmlRender.getString(chart);

	}

}
