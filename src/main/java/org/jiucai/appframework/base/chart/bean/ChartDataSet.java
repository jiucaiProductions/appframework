package org.jiucai.appframework.base.chart.bean;

import java.util.List;

public class ChartDataSet extends ChartBean {

	public String renderAs;

	/**
	 * P or S renderAs attribute over-rides this attribute in FusionCharts v3
	 * for Single Y Combination Charts. This attribute allows you to set the
	 * parent axis of the dataset - P (primary) or S (secondary). Primary
	 * data-sets are drawn as Columns and secondary data-sets as lines.
	 */
	public String parentYAxis;

	/**
	 * 0/1 Whether to show the border of this data-set (area or column only).
	 */
	public Integer showPlotBorder;

	public String plotBorderColor;
	public String plotBorderThickness;
	public String plotBorderAlpha;

	public String seriesName;

	/**
	 * 0-100 or Comma Separated List This attribute sets the alpha
	 * (transparency) of the entire data-set.
	 */
	public String color;

	public String alpha;

	/**
	 * 0/1 Whether to show the values for this data-set.
	 */
	public String showValues;

	/**
	 * ABOVE BELOW AUTO This attribute lets you adjust the vertical alignment of
	 * data values for all dataplots in the dataset. The alignment is set with
	 * respect to the position of the dataplots on the chart. By default, the
	 * attribute is set to AUTO mode in which the alignment of each data value
	 * is determined automatically based on the position of each plot point. In
	 * ABOVE mode, data values are displayed above the plot points unless a plot
	 * point is too close to the upper edge of the canvas. While in BELOW mode,
	 * data values are displayed below the plot points unless a plot point is
	 * too close to the lower edge of the canvas. The attribute will function
	 * only if showValue attribute is set to 1 in this particular data-set.
	 */
	public String valuePosition;

	public Integer dashed;
	public Integer includeInLegend;

	public Integer drawAnchors;
	public Integer anchorSides;
	public Integer anchorRadius;
	public String anchorBorderColor;
	public Integer anchorBorderThickness;
	public String anchorBgColor;
	public Integer anchorAlpha;

	public Integer lineThickness;
	public Integer lineDashLen;
	public Integer lineDashGap;

	public List<ChartSet> sets;

	public ChartDataSet() {
		super();
	}

	public ChartDataSet(String seriesName) {
		super();
		this.seriesName = seriesName;
	}

	public ChartDataSet(String seriesName, List<ChartSet> sets) {
		super();
		this.seriesName = seriesName;
		this.sets = sets;
	}

}
