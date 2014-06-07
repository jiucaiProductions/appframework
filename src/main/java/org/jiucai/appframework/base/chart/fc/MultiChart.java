package org.jiucai.appframework.base.chart.fc;

import java.util.List;

import org.jiucai.appframework.base.chart.bean.ChartCategoryGroup;
import org.jiucai.appframework.base.chart.bean.ChartDataSet;
import org.jiucai.appframework.base.chart.bean.ChartLine;

public class MultiChart extends BaseFusionChart {

	public ChartCategoryGroup categories;

	/***
	 * datasets.size() must equals categories.category.size()
	 */
	public List<ChartDataSet> datasets;

	/**
	 * Horizontal trend lines spanning the chart canvas which aid in
	 * interpretation of data with respect to some pre-determined value. For
	 * example, if you are plotting sales data of current year, you might want
	 * to add previous year's average monthly sales as trend indicator for ease
	 * of comparison.
	 */
	public List<ChartLine> trendLines;

	/**
	 * Vertical trend lines help you create vertical trend lines/zones on
	 * scatter (XY Plot) or Bubble charts. These are vertical lines spanning the
	 * chart canvas which aid in interpretation of data with respect to some
	 * pre-determined value.
	 */
	public List<ChartLine> vTrendLines;

}
