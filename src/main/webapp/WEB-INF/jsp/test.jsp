<%@ page contentType="text/html; charset=UTF-8"%>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery/jquery.js?ver=1.7.2"></script>
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/style.css" />
<style type="text/css">

/*mm clock*/

.clock_area {
	border: 1px solid #808080;
	width: 283px;
	margin: 10px auto;
	padding: 10px;
}

.clock_mm {
	padding: 3px;
	background: white;
	border: 1px solid #ddd;
}

.clock_sec_bg {
	width: 30px;
	height: 30px;
	color: #f00;
	left: 4px;
	top: 4px;
	line-height: 32px;
	text-indent: 6px;
}
</style>



<div style="width: 800px; border: 1px solid #efefef;background: #eee;margin: 10px auto; padding: 10px;">

test

${req}

${req.a}


</div>














<script type="text/javascript">

app = {} || app;
app.path = '${pageContext.request.contextPath}';

	$(function() {
	    
	/*利用API显示美女时钟*/
		var fnGetMM = function(){
			$.get(app.path + "/xhr/req?url=http://www.sodao.com/ShowTime/gt1",{},function(data){
				if(data !== []){
					var json = eval(data);
					var src = json[0].path, 
					id = json[0].pcb_id;
					if(src){
						$("#clockMM_a").attr("href", src).attr("rel",id);
						$("#clockMM_img").attr("src", src);
						
						var title = '';
						
						title += '<br/>昵称 ' + json[0].nickName;
						
						title += '<br/>年龄 ' + json[0].age;
						title += '<br/>生日 ' + json[0].birthday;
						title += '<br/>籍贯 ' + json[0].birthplace;
						if(json[0].height && json[0].height != '0'){
							title += '<br/>身高 ' + json[0].height;
						}
						title += '<br/>星座 ' + json[0].constellation;
						
						if(json[0].job && json[0].job != ''){
							title += '<br/>职业 ' + json[0].job;
						}
						title += '<br/>血型 ' + json[0].blood;
						
						title += '<br/>时钟时间 ' + json[0].showTime;
						title += '<br/>拍照城市 ' + json[0].takeCity;
						title += '<br/>拍照地点 ' + json[0].takeAddress;
						
						if(json[0].wish && json[0].wish != ''){
							title += '<br/>愿望 ' + json[0].wish;
						}
						
						
						
						$("#clock_mm_info").html(title);
						$("#clockArea").show();
						return false;
					}
				}
			});
		};
		fnGetMM();
		var fnShowClock = function(){
			var myDate = new Date();
			var sec = myDate.getSeconds();
			var lefts = 60 - sec;
			if(lefts < 10){
				lefts = "0" + lefts;	
			}
			$("#mmSecond").text(lefts);
			if(sec === 0){
				fnGetMM();
			}
			setTimeout(fnShowClock, 1000);
		};
		fnShowClock();
		

	});


</script>
                    
                    
<div id="clockArea" class="clock_area dn" >
<h3 class="the_title"></h3>
<a id="clockMM_a" href="#nogo" class="dib rel tdn" rel="random"  title="点击查看大图"  target="_blank">
<strong id="mmSecond" class="abs clock_sec_bg f16 fa"></strong>
<img id="clockMM_img" width="265" class="clock_mm" src="http://mat1.gtimg.com/www/iskin09/spacer.gif" />
</a>
<div id="clock_mm_info"></div>
</div>



