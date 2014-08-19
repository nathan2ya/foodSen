<%@ page language="java" contentType="text/html; charset=EUC-KR"
    pageEncoding="EUC-KR"%>
<link href="./css/base.css" rel="stylesheet" type="text/css" />
<link href="./css/common.css" rel="stylesheet" type="text/css" />
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt_rt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<div class="pop">
	<div class="pop_box">
	
		<h1>${resultClass.sur_title}</h1>
		<div class="pop_list">
			<div class="research_box">
			
				<c:forEach var="i" begin="0" end="${cnt-1}" step="1">
					
					<h2>${i+1}. ${title[i]} </h2>
					
					<ul class="research_list">
					
						<c:if test="${item[i] != ' ' && description[i] != ' '}">
							${item[i]} <br/>
							${description[i]} <br/>
						</c:if>
						
						<c:if test="${description[i] == ' '}">
							<font color='gray'> &nbsp;&nbsp;&nbsp;해당 문항에는 작성된 사유가 없습니다. </font>
						</c:if>
					</ul>
					
					<%-- 
					<c:if test="${resultClass3.description != ' ' || resultClass3.description != ' '}">
						<li>${resultClass3.suri_num}</li>
						<li>&nbsp;&nbsp;&nbsp; ${resultClass3.description}</li>
					</c:if>
					 --%>
					
					
				</c:forEach>
				
			</div>
		</div>
		
		<p class="pt20"></p>
		<div class="pop_btn"><span class="blue_l"><a href="javascript:self.close()" class="blue_r">확인</a></span></div>
		
	</div>
</div>