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
			
				<c:forEach var="i" begin="0" end="${resultClass3Cnt-1}" step="1">
					
					<c:if test="${description[i] != ' '}">
						<h2>${title[i]} </h2>
					</c:if>
					
					<!-- 문항, 사유 둘다 존재할경우 -->
					<c:if test="${item[i] != ' ' && description[i] != ' '}">
						<ul class="research_list">
							${item[i]} <br/>
							${description[i]} <br/>
						</ul>
					</c:if>
					
				</c:forEach>
				
			</div>
		</div>
		
		<p class="pt20"></p>
		<div class="pop_btn"><span class="blue_l"><a href="javascript:self.close()" class="blue_r">확인</a></span></div>
		
	</div>
</div>