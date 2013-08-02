package org.jiucai.appframework.base.chart.bean;


public class ChartStyle extends ChartBean {

	/**
	 * Style name can only include alphabets and numbers. Punctuation marks
	 * (including underscore) shouldn't be used. Style name must be unique,
	 * i.e., two style definitions cannot have the same name as there will be a
	 * conflict between the two.
	 */
	public String name;

	/**
	 * values must be either 'font' or 'animation' or 'shadow' or 'glow' or 'blur' or
	 * bevel'
	 */
	public String type;
	
	////////////////////// font

	public String font;   // 	Sets the font family for the text. 	Verdana, Arial ..
	public String size;   //  	Specifies the font size 	10, 12 ..
	public String color;   //  	Sets the font color, should be hex color code without # 	FF0000, FFFFDD
	public String align;   //  	Sets the alignment of the chart caption or sub-caption. This attribute is only applicable to chart captions and sub captions. The default value is center 	left, right, center
	public String bold;   //  	Flag indicating whether font should be bold or not 	1 for Yes, 0 for No
	public String italic;   //  	Flag indicating whether font should be italics or not 	1 for Yes, 0 for No
	public String underline;   //  	Flag indicating whether font should be underlined 	1 for Yes, 0 for No
	public String bgColor;   //  	Sets the background color for a text box, should be hex color code without # 	FF0000, FFFFDD
	public String borderColor;   //  	If you need a border around your text, you can use this parameter 	 
	public String isHTML;   //  	Helps you set whether the text should be rendered as HTML or plain text. 	 
	public String leftMargin;   //  	The left margin of the text, in points 	 
	public String letterSpacing;   //  	The amount of space that is uniformly distributed between characters of the text 	 
	
	
	
	/////////////////////// Animation 
	
	public String param;   // 	This attribute lets you specify the property of chart object which you want to animate e.g., _x, _y, _xscale etc..
	public String start;   // 	The start value of animation e.g., if you're animating the data plot for fade in alpha effect, the start value of alpha would be 0.
	public String duration;   // 	Using this, you can control the duration of animation in seconds.
	public String easing;   // 	This attribute lets you specify the pattern of animation easing. Valid values are "elastic", "bounce", "regular", "strong" or "none".
	
	
	
	////////////////////  Shadow 

	public String distance;   // 	The offset distance for the shadow (in pixels). The default value is 4.
	public String angle;   // 	The angle of the shadow. Valid values are 0 to 360˚. The default value is 45.
//	public String color;   // 	The color of the shadow in hex code (without #). The default value is CCCCCC.
	public String alpha;   // 	The alpha transparency value for the shadow color. Valid values are 0 to 100. For example, 25 sets a transparency value of 25%.
	public String blurX;   // 	The amount of horizontal blur. Valid values are 0 to 255. The default value is 4. Values that are a power of 2 (such as 2, 4, 8, 16 and 32) are optimized to render more quickly than other values.
	public String blurY;   // 	The amount of vertical blur. Valid values are 0 to 255. The default value is 4. Values that are a power of 2 (such as 2, 4, 8, 16 and 32) are optimized to render more quickly than other values.
	public String strength;   // 	The strength of the imprint or spread. The higher the value, the more color is imprinted and the stronger the contrast between the shadow and the background. Valid values are from 0 to 255. The default is 1.
	public String quality;   // 	The number of times to apply the shadow effect. Valid values are 0 to 15. The default value is 1, which is equivalent to low quality. A value of 2 is medium quality, and a value of 3 is high quality. Shadow with lower values are rendered quicker.
	
	
	///////////////////////// Glow 

//	public String color;   // 	The color of the glow in hex code (without #). The default value is FF0000.
//	public String alpha;   // 	The alpha transparency value for the shadow color. Valid values are 0 to 100. For example, 25 sets a transparency value of 25%.
//	public String blurX;   // 	The amount of horizontal blur. Valid values are 0 to 255. The default value is 8. Values that are a power of 2 (such as 2, 4, 8, 16 and 32) are optimized to render more quickly than other values.
//	public String blurY;   // 	The amount of vertical blur. Valid values are 0 to 255. The default value is 8. Values that are a power of 2 (such as 2, 4, 8, 16 and 32) are optimized to render more quickly than other values.
//	public String strength;   // 	The strength of the imprint or spread. The higher the value, the more color is imprinted and the stronger the contrast between the glow and the background. Valid values are 0 to 255. The default is 2.
//	public String quality;   // 	The number of times to apply the effect. Valid values are 0 to 15. The default value is 1, which is equivalent to low quality. A value of 2 is medium quality, and a value of 3 is high quality. Glows with lower values are rendered quicker.
	
	//////////////////  Bevel 
	
//	public String angle;   // 	The angle of the bevel. Valid values are from 0 to 360 degrees. The default value is 45.
//	public String distance;   // 	The offset distance of the bevel. Valid values are in pixels (floating point). The default value is 4.
	public String shadowColor;   // 	The shadow color of the bevel. Valid values are in hexadecimal format RRGGBB (without #). The default value is 000000.
	public String shadowAlpha;   // 	The alpha transparency value of the shadow color. This value is specified as a normalized value from 0 to 100. For example, 25 set a transparency value of 25%. The default value is 50.
	public String highlightColor;   // 	The highlight color of the bevel. Valid values are in hexadecimal format RRGGBB (without #). The default value is FFFFFF.
	public String highlightAlpha;   // 	The alpha transparency value of the highlight color. The value is specified as a normalized value from 0 to 100. For example, 25 sets a transparency value of 25%. The default value is 50.
//	public String blurX;   // 	The amount of horizontal blur in pixels. Valid values are from 0 to 255 (floating point). The default value is 4. Values that are a power of 2 (such as 2, 4, 8, 16, and 32) are optimized to render more quickly than other values.
//	public String blurY;   // 	The amount of vertical blur in pixels. Valid values are from 0 to 255 (floating point). The default value is 4. Values that are a power of 2 (such as 2, 4, 8, 16, and 32) are optimized to render more quickly than other values.
//	public String strength;   // 	The strength of the imprint or spread. Valid values are from 0 to 255. The larger the value, the more color is imprinted and the stronger the contrast between the bevel and the background. The default value is 1.
//	public String quality;   // 	The number of times to apply the filter. The default value is 1, which is equivalent to low quality. A value of 2 is medium quality, and a value of 3 is high quality. Filters with lower values are rendered more quickly.
	
	//////////////////////// Blur 
	
//	public String blurX;   // 	The amount to blur horizontally. Valid values are from 0 to 255. The default value is 4. Values that are a power of 2 (such as 2, 4, 8, 16 and 32) are optimized to render quicker than other values.
//	public String blurY;   // 	The amount to blur vertically.
//	public String quality;   // 	The number of times to apply the filter. The default value is 1, which is equivalent to low quality. A value of 2 is medium quality, and a value of 3 is high quality and approximates a Gaussian blur.
	

}
