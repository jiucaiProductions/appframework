package org.jiucai.appframework.base.chart.fc;

import org.jiucai.appframework.base.chart.bean.ChartStyles;

public class BaseFusionChart extends BaseChart {

	/**
	 * Lets you set the background color for the chart. Use hex color code
	 * without #. To use a gradient fill, specify all the colors required for
	 * the gradient fill separated by commas here. bgColor="647881"
	 * bgColor="999999,FFFFFF"
	 */
	public String bgColor = "FFFFFF";

	/**
	 * Lets you set the alpha (transparency) for the background. Valid range is
	 * from 0-100.
	 */
	public String bgAlpha = "100";

	/**
	 * Whether to show a border around the chart or not. By default, it's set to
	 * 1 in 2D charts and 0 in 3D charts
	 */
	public Integer showBorder = 0;

	/**
	 * Border color of the chart
	 */
	public String borderColor;

	/**
	 * Border thickness of the chart (in pixels)
	 */
	public Integer borderThickness;

	/**
	 * Border alpha of the chart
	 */
	public String borderAlpha;

	/**
	 * Ratio of each color in the gradient on a scale of 100. The total ratios
	 * specified as this attribute should sum up to 100. For example, if you
	 * want to plot a equidistant gradient for 2 colors, specify bgRatio as
	 * "0,100". bgRatio="40,60"
	 */
	public String bgRatio;

	/**
	 * Angle of the gradient fill (in degrees - 0-360).;
	 */
	public String bgAngle;

	/**
	 * Lets you specify the Url (with full path) of the background image / SWF
	 * File. Make sure that the image and the chart SWF file are in the same
	 * sub-domain and you're providing a relative path. Absolute paths would be
	 * ignored and logged in debug window. bgSWF="FruitsPic.jpg"
	 */
	public String bgSWF;

	/**
	 * You can configure the alpha of the background (loaded) image using this
	 * property. bgSWFAlpha="40"
	 */
	public String bgSWFAlpha;

	public String canvasbgColor;
	public String canvasbgAlpha;

	/**
	 * Font Name This attribute lets you set the font face (family) of all the
	 * text (data labels, values etc.) on chart. If you specify outCnvBaseFont
	 * attribute also, then this attribute controls only the font face of text
	 * within the chart canvas bounds.
	 */
	public String baseFont = "Arial";

	/**
	 * 0-72 This attribute sets the base font size of the chart i.e., all the
	 * values and the names in the chart which lie on the canvas will be
	 * displayed using the font size provided here.
	 */
	public String baseFontSize = "12";

	/**
	 * Hex color code without # This attribute sets the base font color of the
	 * chart i.e., all the values and the names in the chart which lie on the
	 * canvas will be displayed using the font color provided here.
	 */
	public String baseFontColor = "000000";

	// Chart Titles and Axis Names

	/**
	 * String Caption of the chart.
	 */
	public String caption;
	/**
	 * String Sub-caption of the chart.
	 */
	public String subCaption;
	/**
	 * String X-Axis Title of the Chart.
	 */
	public String xAxisName;
	/**
	 * String Y-Axis Title of the chart.
	 */
	public String yAxisName;

	public String showValues;

	public Integer showToolTip = 1;
	public Integer showToolTipShadow = 0;

	public Integer drawToolbarButtons = 0;
	
	public String showPercentageInLabel;//显示百分比数值

	/**
	 * Number The bubble/scatter chart have both x and y axis as numeric. This
	 * attribute lets you explicitly set the x-axis lower limit. If you do not
	 * specify this value, FusionCharts will automatically calculate the best
	 * value for you. xA
	 */
	public String xAxisMinValue;

	/**
	 * Number The bubble/scatter chart have both x and y axis as numeric. This
	 * attribute lets you explicitly set the x-axis upper limit. If you do not
	 * specify this value, FusionCharts will automatically calculate the best
	 * value for you.
	 */
	public String xAxisMaxValue;

	/**
	 * Number This attribute helps you explicitly set the lower limit of the
	 * chart. If you don't specify this value, it is automatically calculated by
	 * FusionCharts based on the data provided by you.
	 */
	public String yAxisMinValue;

	/**
	 * Number This attribute helps you explicitly set the upper limit of the
	 * chart. If you don't specify this value, it is automatically calculated by
	 * FusionCharts based on the data provided by you.
	 */

	public String yAxisMaxValue;
	/**
	 * Number 1-5 FusionCharts v3 introduces the concept of Color Palettes. Each
	 * chart has 5 pre-defined color palettes which you can choose from. Each
	 * palette renders the chart in a different color theme. Valid values are
	 * 1-5.
	 */
	public Integer palette;

	/**
	 * String List of hex color codes separated by comma While the palette
	 * attribute allows to select a palette theme that applies to chart
	 * background, canvas, font and tool-tips, it does not change the colors of
	 * data items (i.e., column, line, pie etc.). Using paletteColors attribute,
	 * you can specify your custom list of hex colors for the data items. The
	 * list of colors have to be separated by comma e.g., <chart
	 * paletteColors='FF0000,0372AB,FF5904...'>. The chart will cycle through
	 * the list of specified colors and then render the data plot accordingly.
	 * 
	 * To use the same set of colors throughout all your charts in a web
	 * application, you can store the list of palette colors in your application
	 * globally and then provide the same in each chart XML.
	 */
	public String paletteColors;

	// //////////////////////

	public Integer unescapeLinks = 0;
	
	
	public Integer showPrintMenuItem = 0;

	/**
	 * Boolean 0/1 Setting this to 1 shows up a custom context menu in the
	 * chart, which can be customized to show your text and can be linked to
	 * your Url.
	 * 
	 * For e.g., you can set the context menu of the chart to include
	 * "About your company name" and then link to your company home page. By
	 * default, the chart shows "About FusionCharts" when right clicked.
	 */
	public Integer showAboutMenuItem = 1;

	/**
	 * String The menu item label for the custom context menu item.
	 */
	public String aboutMenuItemLabel = "About Emarbox";

	/**
	 * String URL Link for the custom context menu item. You can specify the
	 * link in FusionCharts Link format to be able to open the same in new
	 * window, pop-ups, frames or as JavaScript links.
	 * 
	 * link format doc : Contents/DrillDown/LinkFormat.html
	 */
	public String aboutMenuItemLink = "N-http://www.emarbox.com";
	
	
	public String defaultAnimation = "1";

	/**
	 * FusionCharts v3 introduces Styles to help you apply font, effects and
	 * animations to various objects of the chart. Styles lends a simple
	 * mechanism using which you can easily control the visual layout of charts.
	 */
	public ChartStyles styles;
	
	
	//chart export options
	public String exportEnabled;
	public String exportHandler;
	public String exportAtClient;
	public String exportAction;
	public String exportTargetWindow;
	public String exportFileName;
	public String exportFormats;
	public String exportShowMenuItem;
	
	public Integer showExportDialog;		// 	Boolean (0/1) 	Whether to show the export dialog during capture phase. If not, the chart starts capturing process without the dialog visible.
	public String exportDialogMessage;		// 	String 			The message to be shown in the dialog box. The default is "Capturing Data : "
	public String exportDialogColor;		// 	Hex Color 		Background color of dialog box.
	public String exportDialogBorderColor;	// 	Hex Color 		Border color of dialog box.
	public String exportDialogFontColor;	// 	Hex Color 		Font color to be used for text in dialog.
	public String exportDialogPBColor;		// 	Hex Color 		Color of progress bar in dialog. 
	
	//format
	
	/** 是否显示 千 百万 等的逗号分隔符 **/
	public Integer formatNumber;
	
	/** 是否用 K M 等缩写显示数字 **/
	public Integer formatNumberScale;
	
	/** 小数点后数字强制显示 **/
	public Integer forceDecimals;
	
	/** 数字前缀 **/
	public String numberPrefix;
	
	/** 数字后缀 **/
	public String numberSuffix;
	/** 保留小数的位数， 1 - 10 位  formatNumber=1 时生效。**/
	public Integer decimals;
	
	/** 是否纵向显示标签 **/
	public Integer rotateLabels;

}
