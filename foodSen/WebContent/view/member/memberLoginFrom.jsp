<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
	
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<html>
<head>
	<title>로그인</title>
	
	<SCRIPT type="text/javascript">
		//null 유효성검사
		function checkIt(){
			inputForm=eval("document.loginForm");
		 
			if(!inputForm.user_id.value){
				alert("아이디를 입력해주세요.");
				loginForm.user_id.focus();
				return false;
			}
			if(!inputForm.user_pw.value){
				alert("비밀번호를 입력해주세요.");
				loginForm.user_pw.focus();
				return false;
			}
		}
	</script>
</head>


<h1 align="center">로그인</h1>

<body>
	
	<!-- 비 로그인상태 -->
	<c:if test="${sessionScope.session_id == null}">
		<form name="loginForm" action="/foodSen/loginPro.do" method="post" onSubmit="return checkIt()">
		
			<table border="1" align="center" width="800" bordercolor="#E7E7E7">
				<tr>
					<td>아이디</td>
					<td><input type="text" name="user_id" id="user_id" size="20" maxlength="20" /></td>
				</tr>
				<tr>
					<td>비밀번호</td>
					<td><input type="password" name="user_pw" id="user_pw" size="20" maxlength="20" /></td>
				</tr>
				
				<tr>
					<td colspan="2">
						<input type="submit" name="submit" value="로그인"/>
					</td>
				</tr>
			</table>
			
		</form>
	</c:if>
	
	
	<!-- 로그인된상태 -->
	<c:if test="${sessionScope.session_id != null}">
		<form name="loginedForm" action="/foodSen/logoutPro.do" method="post" onSubmit="return checkIt()">
		
			<table border="1" align="center" width="800" bordercolor="#E7E7E7">
				<tr>
					<td colspan="2">
						${sessionScope.session_id}님 환영합니다.
					</td>
				</tr>
				
				<tr>
					<td colspan="2">
						<input type="submit" name="submit" value="로그아웃"/>
					</td>
				</tr>
			</table>
			
		</form>
	</c:if>
	
</body>