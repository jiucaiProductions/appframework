package org.jiucai.appframework.base.chart.bean;


public class ChartSet extends ChartBean {

	public String label;

	/**
	 * Numerical value for the data item. This value would be plotted on the
	 * chart.
	 */
	public String value;

	/**
	 * If instead of the numerical value of this data, you wish to display a
	 * custom string value, you can specify the same here. Examples are
	 * annotation for a data item etc.
	 */
	public String displayValue;

	/**
	 * Hex Code For combination charts, you can define the color of data-sets at
	 * dataset level. However, if you wish to highlight a particular data
	 * element, you can specify it's color at "set" level using this attribute.
	 * This attribute accepts hex color codes without #.
	 */
	public String color;

	public String link;

	/**
	 * By default, FusionCharts shows the series Name, Category Name and value
	 * as tool tip text for that data item. But, if you want to display more
	 * information for the data item as tool tip, you can use this attribute to
	 * specify the same.
	 */
	public String toolText;

	public Integer showLabel;

	/**
	 * Boolean 0/1 You can individually opt to show/hide values of individual
	 * data items using this attribute. This value over-rides the data-set level
	 * value.
	 */
	public Integer showValue;

	public String valuePosition;
	public Integer dashed;
	public String alpha;

	public Integer anchorSides;
	public Integer anchorRadius;
	public String anchorBorderColor;
	public Integer anchorBorderThickness;
	public String anchorBgColor;
	public Integer anchorAlpha;
	public Integer anchorBgAlpha;

	// Bubble / XY Plot Charts

	/**
	 * Numeric Value X-axis value for the set. The bubble/scatter point will be
	 * placed horizontally on the x-axis based on this value.
	 */
	public String x;

	/**
	 * Numeric Value Y-axis value for the set. The bubble/scatter point will be
	 * placed vertically on the y-axis based on this value.
	 */
	public String y;

	public String name;

	/**
	 * Numeric Value Z-axis numerical value for the set of data. The size of
	 * bubble would depend on this value (with respect to z values of other
	 * bubbles on the chart).
	 */
	public String z;

	public ChartSet() {
		super();
	}

	public ChartSet(String value) {
		super();
		this.value = value;
	}

	public ChartSet(String x, String y) {
		super();
		this.x = x;
		this.y = y;
	}

	public ChartSet(String x, String y, String z) {
		super();
		this.x = x;
		this.y = y;
		this.z = z;
	}

}
