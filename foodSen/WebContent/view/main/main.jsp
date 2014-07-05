<%@ page language="java" contentType="text/html; charset=euc-kr"
	pageEncoding="euc-kr"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt_rt" %>

<jsp:include page="/view/include/mainTop.jsp"/>
<link href="/css/base.css" rel="stylesheet" type="text/css" />
<link href="/css/common.css" rel="stylesheet" type="text/css" />
	
<html>
<head>
<title>main</title>
</head>
<body>


<div id="container">
	<div id="main_contents">
	
	
	
	
		<!-- 왼쪽영역 -->
		<p class="main_img">
			<img src="./images/main/img_v.jpg" alt="건강한급식, 행복한학교,꿈과 끼를 키우는 서울행복교육" />
		</p>
		<!-- .//왼쪽영역 -->
		
		
		
		
		<!-- 오른쪽영역 -->
		<div class="main_right">
			
			
			<!-- 공지사항 -->
			<div class="notice">
			
				<!--탭 클릭 -->
				<ul class="main_tab">
					<li><a href="javascript:void(0)" onclick="doGoTab1(this,'01');" class="main_tab01_on">급식소식</a></li>
					<li><a href="javascript:void(0)" onclick="doGoTab1(this,'02');" class="main_tab02">연수·행사활동</a></li>
					<li><a href="javascript:void(0)" onclick="doGoTab1(this,'03');" class="main_tab03">설문조사</a></li>
				</ul>
				<!--///탭 클릭 -->
		          
		          
		        <!-- 각 텝별 액션 -->
				<div id="tab01" class="tabDivArea">
					<c:if test="${voNew eq null || empty voNew}">
						<ul>
							<li>게시글이 없습니다. <span class="main_data"></span></li>
						</ul>
					</c:if>
					<c:if test="${voNew ne null && not empty voNew}">
						<ul>
							<c:forEach var="voNew" items="${voNew}" varStatus="i">
								<li>
									<a class="title1" href="javascript:goView('${voNew.seq }', 'news')">${voNew.title }</a>
									<span class="main_data"><fmt:formatDate value="${voNew.reg_date }" pattern="yyyy-MM-dd" /></span>
								</li>
							</c:forEach>
						</ul>
					</c:if>
					<span class="more">
						<a href="foodNewsList.do"><img src="./images/main/more.gif" alt="더보기" /></a>
					</span>
				</div>
				
				

				</div>
				<!-- .//공지사항 -->
				
				

		</div>
		<!--.//오른쪽영역 -->
		
		
		
		<!-- 하단배너 -->
		<div class="banner">
			<p><img src="images/main/banner/img_01.gif" alt="관련사이트" /></p>
	        <ul>
	          <li><a href="http://www.moe.go.kr/" target="_blank"><img src="./images/main/banner/banner_01.gif" alt="교육부" /></a></li>
	          <li><a href="http://www.seoul.go.kr/" target="_blank"><img src="./images/main/banner/banner_02.gif" alt="서울특별시교육청" /></a></li>
	          <li><a href="http://www.mfds.go.kr/" target="_blank"><img src="./images/main/banner/banner_03.gif" alt="식품의약품안전처" /></a></li>
	          <li><a href="http://www.maf.go.kr/" target="_blank"><img src="./images/main/banner/banner_04.gif" alt="농림축산식품부" /></a></li>
	          <li><a href="http://www.mof.go.kr/" target="_blank"><img src="./images/main/banner/banner_05.gif" alt="해양수산부" /></a></li>
	          <li><a href="http://www.kosha.or.kr/" target="_blank"><img src="./images/main/banner/banner_06.gif" alt="안전보건공단" /></a></li>
	        </ul>
       		<span class="btn"> <a href="#"><img src="./images/main/banner/up.gif" alt="위로" /></a> <a href="#"><img src="images/main/banner/stop.gif" alt="정지" /></a> <a href="#"><img src="images/main/banner/down.gif" alt="아래로" /></a> </span> 
   		 </div>
		 <!-- .//하단배너 -->
		 
		
	</div>
</div>



<!-- 푸터 -->
<jsp:include page="/view/include/footer.jsp"/>



</body>
</html>