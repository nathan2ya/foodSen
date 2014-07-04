<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>

<html>
<head>
	<title>로그인</title>
	
	<SCRIPT type="text/javascript">
		//null 유효성검사
		function checkIt(){
			inputForm=eval("document.memberCreateForm");
		 
			if(!inputForm.user_id.value){
				alert("아이디를 입력해주세요.");
				memberCreateForm.user_id.focus();
				return false;
			}
			if(!inputForm.user_pw.value){
				alert("비밀번호를 입력해주세요.");
				memberCreateForm.user_pw.focus();
				return false;
			}
		}
	</script>
</head>


<h1 align="center">로그인</h1>

<body>
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
</body>