package org.jiucai.appframework.base.chart.bean;

public class ChartCategory extends ChartBean {

	/**
	 * String This attribute determines the label for the data item. The label
	 * appears on the x-axis of chart.
	 */
	public String label;

	/**
	 * Boolean 0/1 You can individually opt to show/hide labels of individual
	 * data items using this attribute.
	 */
	public Integer showLabel;

	/**
	 * The label of each category shows up on the x-axis. However, there might
	 * be cases where you want to display short label (or abbreviated label) on
	 * the axis and show the full label as tool tip. This attribute lets you
	 * specify the tool tip text for the label.
	 */
	public String toolText;

	// Bubble / XY Plot Charts

	/**
	 * Numeric Value The bubble/scatter chart have both x and y axis as numeric.
	 * This attribute lets you define the x value (numerical position on the
	 * x-axis) where this category name would be placed.
	 */
	public String x;

	/**
	 * Boolean 0/1 Whether the vertical line should be shown for this category.
	 */
	public Integer showVerticalLine;

	/**
	 * Boolean 0/1 Whether the vertical line should appear as dashed.
	 */
	public Integer lineDashed;

	public ChartCategory() {
		super();
	}

	public ChartCategory(String label) {
		super();
		this.label = label;
	}

	public ChartCategory(String label, String x) {
		super();
		this.label = label;
		this.x = x;
	}

	public ChartCategory(String label, String x, Integer showVerticalLine) {
		super();
		this.label = label;
		this.x = x;
		this.showVerticalLine = showVerticalLine;
	}

}
