<%@ page language="java" import="java.util.*" contentType="text/html; charset=utf-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="zh">
<head>
<%
 pageContext.setAttribute("APP_PATH", request.getContextPath());
 %>
	<meta charset="UTF-8">
	<title>Document</title>
	<link href="https://cdn.bootcss.com/bootstrap/3.3.7/css/bootstrap.css" rel="stylesheet">
	<script src="http://cdn.static.runoob.com/libs/jquery/2.1.1/jquery.min.js"></script>
     <link rel="stylesheet" type="text/css" href="${APP_PATH}/WEB-INF/css/common.css">
    <link rel="stylesheet" type="text/css" href="css/common.css">
    <meta name="viewport" content="width=device-width, initial-scale=1">
</head>
<body>
	<div class="navbar navbar-default">
	<div class="container">
		<div class="navbar-header">
			<!-- <div class="navbar-brand">人气美食推荐系统</div> -->
		<a href="${pageContext.request.contextPath}/recommend" class="navbar-brand"></a>
		</div>
		<ul class="nav navbar-nav">
			<li><a href="${pageContext.request.contextPath}/recommend">首页</a></li>
			<li><a href="#">今日推荐</a></li>
		</ul>
		<ul class="nav navbar-nav navbar-right">
			<li><a href="#">登陆</a></li>
			<li><a href="#">注册</a></li>
			<li><a href="#">我的收藏</a></li>
		</ul>
	</div>
	</div>
	<div class="container">
		<div class="row">


	<div class="col-sm-2">
	<div class="list-group side-bar">
		<a href="${pageContext.request.contextPath}/recommend" class="list-group-item active">全部</a>
		<a href="${pageContext.request.contextPath}/recommend?pageNum=1&category=508" class="list-group-item">烧烤</a>
		<a href="${pageContext.request.contextPath}/recommend?pageNum=1&category=110" class="list-group-item">火锅</a>
		<a href="${pageContext.request.contextPath}/recommend?pageNum=1&category=116" class="list-group-item">西餐</a>
		<a href="${pageContext.request.contextPath}/recommend?pageNum=1&category=113" class="list-group-item">日料</a>
		<a href="${pageContext.request.contextPath}/recommend?pageNum=1&category=111" class="list-group-item">自助</a>
		<a href="${pageContext.request.contextPath}/recommend?pageNum=1&category=103" class="list-group-item">粤菜</a>
		<a href="${pageContext.request.contextPath}/recommend?pageNum=1&category=102" class="list-group-item">川菜</a>
		<a href="${pageContext.request.contextPath}/recommend?pageNum=1&category=101" class="list-group-item">江浙菜</a>
		<a href="${pageContext.request.contextPath}/recommend?pageNum=1&category=118" class="list-group-item">其他</a>
	</div>
	</div>
	<div class="col-sm-8">
		<div class="priceSortDiv">
			人均￥<input type="text" class="startPrice priceSort" value="${param.start}">-￥<input type="text" class="endPrice priceSort" value="${param.end}">
		<a href="#" type="submit" class="sortSubmit" role="botton">确定</a>
		</div>
	<div class="foodlist">
	<c:forEach items="${data}" var="list">
		<div class="foodlist-item clearfix">
			<div class="col-sm-4">
				<img src="${list.defaultPic}" alt="img"></div>
			<div class="col-sm-8">
				<a href="${list.url}" class="tittle">${list.shopName}</a>
			<ul class="food-info">
           	<li class="grade">
           		口味:<span>${list.refinedScore1}</span>
           		环境:<span>${list.refinedScore2}</span>
           		服务:<span>${list.refinedScore3}</span>
           		<span class='pull-right'>${list.categoryId}</span>
            </li>
        	<br>
       	 	<li><span>人均：</span>${list.avgPrice}元</li>
       		 <br>
       	 	<li><span>地址 :</span>${list.address}</li>
       	 	<span class='pull-right'>打分:${list.categoryId}</span>
        	<br>
        	<button type="button" class="btn btn-info btn-sm">加入收藏</button>
    		</ul>
		</div>
		</div>
	</c:forEach>

	</div>
	</div>
	<div class="col-sm-2">
		<div class="search-bar">
		<input type="search" class="form-control" placeholder="想吃啥点我">
		</div>
		<div class="side-bar-card">
			广告出租2333
		</div>
		<div class="signword">标签</div>
	</div>
	</div>

	<!--显示分页信息-->
    <div class="row">
        <!--文字信息-->
        <div class="col-md-6">
            当前第 ${pageInfo.pageNum} 页.总共 ${pageInfo.pages} 页.一共 ${pageInfo.total} 条记录
        </div>

        <!--点击分页-->
        <div class="col-md-6">
            <nav aria-label="Page navigation">
                <ul class="pagination">

                    <li><a href="${pageContext.request.contextPath}/recommend?pageNum=1&${param.category}&start=${param.start}&end=${param.end}">第一页</a></li>

                    <!--上一页-->
                    <li>
                        <c:if test="${pageInfo.hasPreviousPage}">
                            <a href="${pageContext.request.contextPath}/recommend?pageNum=${pageInfo.pageNum-1}&category=${param.category}&start=${param.start}&end=${param.end}" aria-label="Previous">
                                <span aria-hidden="true">«</span>
                            </a>
                        </c:if>
                    </li>

                    <!--循环遍历连续显示的页面，若是当前页就高亮显示，并且没有链接-->
                    <c:forEach items="${pageInfo.navigatepageNums}" var="page_num">
                        <c:if test="${page_num == pageInfo.pageNum}">
                            <li class="active"><a href="#">${page_num}</a></li>
                        </c:if>
                        <c:if test="${page_num != pageInfo.pageNum}">
                            <li><a href="${pageContext.request.contextPath}/recommend?pageNum=${page_num}&category=${param.category}&start=${param.start}&end=${param.end}">${page_num}</a></li>
                        </c:if>
                    </c:forEach>

                    <!--下一页-->
                    <li>
                        <c:if test="${pageInfo.hasNextPage}">
                            <a href="${pageContext.request.contextPath}/recommend?pageNum=${pageInfo.pageNum+1}&category=${param.category}&start=${param.start}&end=${param.end}"
                               aria-label="Next">
                                <span aria-hidden="true">»</span>
                            </a>
                        </c:if>
                    </li>

                    <li><a href="${pageContext.request.contextPath}/recommend?pageNum=${pageInfo.pages}&category=${param.category}&start=${param.start}&end=${param.end}">最后一页</a></li>
                </ul>
            </nav>
        </div>

    </div>

	</div>



</body>
</html>
<script type="text/javascript">
console.log("我进来辣！")
//切换 sidebar active
$(function(){
	$('.list-group-item').removeClass("active");
	$('.list-group-item').each(function(){
		var context = location.href.split('recommend');
		if(context.length==1){
			$(this).addClass("active");
			return false;
		}else{
			if($(this).attr("href").split('recommend')[1].split('&')[1]===location.href.split('recommend')[1].split('&')[1]){
				$(this).addClass("active");
				return false;
			}
		}
	});
});

//给botton 添加动态地址
$(".priceSort").blur(function(){
	console.log(location.href);
	var baseUrl =location.href;
	//http://localhost:8080/spider-dianping2/recommend?pageNum=1&category=116&start=200&end=300
	if(baseUrl.indexOf('start')>=0){
		var index = baseUrl.indexOf('start');
		console.log(baseUrl.substring(0,index-1));
		//http://localhost:8080/spider-dianping2/recommend?pageNum=1&category=116
		baseUrl = baseUrl.substring(0,index-1);
	}
	var start = $(".startPrice").val();
	var end = $(".endPrice").val();
	if(baseUrl.indexOf("?")<0){
		baseUrl = baseUrl + "?";
	}
	//http://localhost:8080/spider-dianping2/recommend?pageNum=1&category=116#
	if(baseUrl.indexOf("#")>=0){
		baseUrl = baseUrl.replace("#","");
	}
	console.log(baseUrl+'\&start='+start+'\&end='+end);
	$(".sortSubmit").attr('href',baseUrl+'\&start='+start+'\&end='+end)
})
</script>
