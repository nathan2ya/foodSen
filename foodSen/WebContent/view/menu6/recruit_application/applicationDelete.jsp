<%@ page language="java" contentType="text/html; charset=EUC-KR"
    pageEncoding="EUC-KR"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt_rt" %>


<link href="/css/base.css" rel="stylesheet" type="text/css" />
<link href="/css/common.css" rel="stylesheet" type="text/css" />


<script type="text/javascript">
function goCreate(){
	var orinPassword = "${pw}";
	
	if(!applicationDelete.pw.value){
		alert("비밀번호를 입력하세요.");
		applicationDelete.pw.focus();
		return;
	}
	
	if(orinPassword != applicationDelete.pw.value){
		alert("비밀번호를 다시 확인해주세요.");
		applicationDelete.pw.focus();
		return;
	}
	
	applicationDelete.submit();
	
	//부모창을 리다이렉트 시킴
	opener.location.href = "/foodSen/goStay2sec.do?service=application";
	self.close(); //창닫기
}
	
</script>


<!-- 삭제를위한 폼 -->
<form name="applicationDelete" action="/foodSen/applicationDelete.do" method="post" align="left">
	<input type="hidden" id="seq" name="seq" value="${seq}" />
	<table width="150" align="center">
		<tr align="center"><td><br/><b>비밀번호 입력</b></td></tr>
		<tr align="center"><td><input type="password" id="pw" name="pw" /></td></tr>
		<tr align="center">
			<td>
				<input type="hidden" id="seq" name="seq" value="${seq}" />
				
				<br/>
				<input type="button" id="button" name="button" value="글 삭제 하기" onClick="goCreate();" />
			</td>
		</tr>
	</table>	
	
</form>
<!-- .//삭제를위한 폼 -->


