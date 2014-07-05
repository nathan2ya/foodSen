<%@ page language="java" contentType="text/html; charset=euc-kr"
	pageEncoding="euc-kr"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<html>
<head>
	<title>탈퇴하기</title>
	
	<SCRIPT type="text/javascript">
		//null 유효성검사
		function checkIt(){
			inputForm=eval("document.memberDeleteForm");
			var password = ${resultClass.user_pw};
		 
			if(!inputForm.user_pw.value){
				alert("비밀번호를 입력해주세요.");
				memberDeleteForm.user_pw.focus();
				return false;
			}
			if(inputForm.user_pw.value != password){
				alert("비밀번호를 확인해주세요");
				memberDeleteForm.user_pw.focus();
				return false;
			}
			
			alert("탈퇴가 성공적으로 완료되었습니다!");
		}
	</script>
</head>


<h1 align="center">가상 서울시특별교육청 홈페이지(회원탈퇴)</h1>

<body>

<form name="memberDeleteForm" action="/foodSen/memberDelete.do" method="post" onSubmit="return checkIt()">
	
	<table border="1" align="center" width="700" bordercolor="#E7E7E7">
		
		<tr>	
			<td colspan="2" align="center">
				${sessionScope.session_id}님의 본인인증을 위해 비밀번호를 재확인 하겠습니다. <br/>
				<input type="password" name="user_pw" id="user_pw" size="21" maxlength="15" />
			</td>
		</tr>
			
		<tr>
			<td height="8"colspan="2" align="right">
				<input type="submit" name="submit" value="탈퇴"/>
			</td>
		</tr>
		
	</table>
	
</form>

</body>
</html>