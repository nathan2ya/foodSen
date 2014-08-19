<%@ page language="java" contentType="text/html; charset=EUC-KR"
    pageEncoding="EUC-KR"%>
<link href="./css/base.css" rel="stylesheet" type="text/css" />
<link href="./css/common.css" rel="stylesheet" type="text/css" />
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt_rt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<div class="pop">
	<div class="pop_box">
	
		<font size=3> <b>${resultClass.sur_title}</b></font> <br/>
		<font color='gray'> &nbsp;&nbsp;��
							���û��� �ۼ��ڴ� <font color=red><b>${selectedCnt}</b></font>�� �Դϴ�. (�����Ͻ� : ${current_date})</font>
		
		<div class="pop_list">
			<div class="research_box">
			
				<c:forEach var="i" begin="0" end="${resultClass3Cnt-1}" step="1">
					
					<c:if test="${description[i] != ' '}">
						<h2>${title[i]} </h2>
					</c:if>
					
					<!-- ����, ���� �Ѵ� �����Ұ�� -->
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
		<div class="pop_btn"><span class="blue_l"><a href="javascript:self.close()" class="blue_r">Ȯ��</a></span></div>
		
	</div>
</div>