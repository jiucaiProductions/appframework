package org.jiucai.appframework.base.chart.bean;


public class ChartLine extends ChartBean {

	/**
	 * Number Numeric Value The starting value for the trendline. Say, if you
	 * want to plot a slanted trendline from value 102 to 109, the startValue
	 * would be 102.
	 */
	public String startValue;

	/**
	 * Number Numeric Value The ending y-axis value for the trendline. Say, if
	 * you want to plot a slanted trendline from value 102 to 109, the endValue
	 * would be 109. If you do not specify a value for endValue, it would
	 * automatically assume the same value as startValue.
	 */
	public String endValue;

	/**
	 * String If you want to display a string caption for the trend line by its
	 * side, you can use this attribute. Example: displayValue='Last Month
	 * High'. When you don't supply this attribute, it automatically takes the
	 * value of startValue.
	 */
	public String displayValue;

	/**
	 * String Hex Code Color of the trend line and its associated text.
	 */
	public String color;

	/**
	 * Boolean 0/1 Whether the trend would display a line, or a zone (filled
	 * colored rectangle).
	 */
	public Integer isTrendZone;

	/**
	 * Boolean 0/1 Whether the trend line/zone would be displayed over data
	 * plots or under them.
	 */
	public Integer showOnTop;
	/**
	 * Number In Pixels If you've opted to show the trend as a line, this
	 * attribute lets you define the thickness of trend line.
	 */
	public String thickness;

	/**
	 * Number 0-100 Alpha of the trend line.
	 */
	public String alpha;

	/**
	 * Boolean 0/1 If you've opted to show the trend as a line, this attribute
	 * lets you define Whether the trend line would appear as dashed.
	 */
	public Integer dashed;

	/**
	 * Number In Pixels If you've opted to show trend line as dash, this
	 * attribute lets you control the length of each dash.
	 */
	public String dashLen;

	/**
	 * Number In Pixels If you've opted to show trend line as dash, this
	 * attribute lets you control the length of each dash gap.
	 */
	public String dashGap;

	/**
	 * Boolean 0/1 Whether to show the trend line value on left side or right
	 * side of chart. This is particularly useful when the trend line display
	 * values on the chart are colliding with divisional lines values on the
	 * chart.
	 */
	public Integer valueOnRight;

	/**
	 * String Custom tool-text for this trendline/zone.
	 */
	public String toolText;

}
