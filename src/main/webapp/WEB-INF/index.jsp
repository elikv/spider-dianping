<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<% %>
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
	<div class="choose-select">
	<select class="selectVal"  >
	<option value="zero" >请选择食堂</option>
  	<option value="one"> 一食堂 </option>
  	<option value="two"> 二食堂 </option>
  	<option value="three"> 三食堂 </option>
  	</select>
  	</div>
  	
  	
  	<div class = " choose-btn ">
  	<input type="button"  value="random floor" >
  	</div>
  	

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
