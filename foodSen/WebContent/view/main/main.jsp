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
	
	
	
	
		<!-- ���ʿ��� -->
		<p class="main_img">
			<img src="./images/main/img_v.jpg" alt="�ǰ��ѱ޽�, �ູ���б�,�ް� ���� Ű��� �����ູ����" />
		</p>
		<!-- .//���ʿ��� -->
		
		
		
		
		<!-- �����ʿ��� -->
		<div class="main_right">
			
			
			<!-- �������� -->
			<div class="notice">
			
				<!--�� Ŭ�� -->
				<ul class="main_tab">
					<li><a href="javascript:void(0)" onclick="doGoTab1(this,'01');" class="main_tab01_on">�޽ļҽ�</a></li>
					<li><a href="javascript:void(0)" onclick="doGoTab1(this,'02');" class="main_tab02">���������Ȱ��</a></li>
					<li><a href="javascript:void(0)" onclick="doGoTab1(this,'03');" class="main_tab03">��������</a></li>
				</ul>
				<!--///�� Ŭ�� -->
		          
		          
		        <!-- �� �ܺ� �׼� -->
				<div id="tab01" class="tabDivArea">
					<c:if test="${voNew eq null || empty voNew}">
						<ul>
							<li>�Խñ��� �����ϴ�. <span class="main_data"></span></li>
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
						<a href="foodNewsList.do"><img src="./images/main/more.gif" alt="������" /></a>
					</span>
				</div>
				
				

				</div>
				<!-- .//�������� -->
				
				

		</div>
		<!--.//�����ʿ��� -->
		
		
		
		<!-- �ϴܹ�� -->
		<div class="banner">
			<p><img src="images/main/banner/img_01.gif" alt="���û���Ʈ" /></p>
	        <ul>
	          <li><a href="http://www.moe.go.kr/" target="_blank"><img src="./images/main/banner/banner_01.gif" alt="������" /></a></li>
	          <li><a href="http://www.seoul.go.kr/" target="_blank"><img src="./images/main/banner/banner_02.gif" alt="����Ư���ñ���û" /></a></li>
	          <li><a href="http://www.mfds.go.kr/" target="_blank"><img src="./images/main/banner/banner_03.gif" alt="��ǰ�Ǿ�ǰ����ó" /></a></li>
	          <li><a href="http://www.maf.go.kr/" target="_blank"><img src="./images/main/banner/banner_04.gif" alt="������ǰ��" /></a></li>
	          <li><a href="http://www.mof.go.kr/" target="_blank"><img src="./images/main/banner/banner_05.gif" alt="�ؾ�����" /></a></li>
	          <li><a href="http://www.kosha.or.kr/" target="_blank"><img src="./images/main/banner/banner_06.gif" alt="�������ǰ���" /></a></li>
	        </ul>
       		<span class="btn"> <a href="#"><img src="./images/main/banner/up.gif" alt="����" /></a> <a href="#"><img src="images/main/banner/stop.gif" alt="����" /></a> <a href="#"><img src="images/main/banner/down.gif" alt="�Ʒ���" /></a> </span> 
   		 </div>
		 <!-- .//�ϴܹ�� -->
		 
		
	</div>
</div>



<!-- Ǫ�� -->
<jsp:include page="/view/include/footer.jsp"/>



</body>
</html>