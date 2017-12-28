<!DOCTYPE HTML >
<html>
<head>
    <title>今晚吃啥啊</title>
    <meta http-equiv="Content-Type" content="text/html ; charset=utf-8">
	<meta http-equiv="description" content="what2Eat">
	<link rel="stylesheet" type="text/css" href="css/style.css">
</head>
<body>
<center>
<a href='/trigger'>爬虫触发</a>
</br>
<a href='/removeDuplicate'>去重</a>
</br>
<a href='/preHeater'>ip预热</a>
</br>
<a href='/rankTrigger'>排行榜接口爬取</a>

</center>
  
<script type="text/javascript" src="js/jquery-3.1.1.min.js"></script>
<script>
$(function(){
	var selectVal = null;
	//var mapOne = new map();
	
	
	//改变下拉框 把属性赋给变量selectVal
	$(".selectVal").change(function(){
		selectVal = $(".selectVal").find("option:selected").val() ;
		alert(  selectVal );
	});
	
	$(".choose-btn").click(function(){
		if(  selectVal == "zero"  ){
			alert( "请选择食堂");
		}
		if(  selectVal == "one"  ){
			alert( "1");
		}
		if(  selectVal == "two"  ){
			alert( "2");
		}
		if(  selectVal == "three"  ){
			alert( "3");
		}
			
			
			
		
	})
	
}		)



</script>
</body>
</html>
