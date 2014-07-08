<%@ page language="java" contentType="text/html; charset=EUC-KR"
    pageEncoding="EUC-KR"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
    
<html>
<head>
	<title>ID중복체크</title>
	
	<SCRIPT type="text/javascript">
		
		//공백제거
		function toPass(){
			var input = document.checkUser_idFrom.user_id.value;//사용자가 입력한 값
			document.checkUser_idFrom.user_id.value = input.replace(/^\s+|\s+$/g,""); //앞뒤 공백을 제거함
		}
		
		//대문자를 소문자로 변경
		function toPass1(){ 
			var input = document.checkUser_idFrom.user_id.value;//사용자가 입력한 값
			document.checkUser_idFrom.user_id.value = input.toLowerCase(); //대문자를 소문자로 변경
		}
		
		//특수문자가 있을 경우 경고
		function toPass2(){
			if((event.keyCode >=32&&event.keyCode <=47)||(event.keyCode>=58&&event.keyCode<=64)||(event.keyCode>=91&&event.keyCode<=96)||(event.keyCode>=123&&event.keyCode<=126) ) {
				alert("아이디에 공백 및 특수문자는 입력할 수 없습니다.");
			}
		}
		
		//null 유효성검사
		function checkIt() {
			inputForm = eval("document.checkUser_idFrom");

			if (!inputForm.user_id.value) {
				alert("아이디를 입력해주세요.");
				checkUser_idFrom.user_id.focus();
				return false;
			}
		}
		
		//사용하기
		function close(){
			alert("aaaaaaaaaaaaaaaaaaaa");
			
			/* 
			var user_id = ${user_id};
			alert("실행여부 테스트 : 아이디value = "+user_id);
			
			//호출한 페이지의 user_id 값 채우기
			opener.document.memberCreateForm.user_id.value = user_id;
			self.close();
			 */
			
		}
		
		//아이디중복인경우
		function found() {
			alert("이미 존재하는 아이디 입니다! 다른아이디를 사용해주세요.");
			checkUser_idFrom.user_id.focus();
		}
		
		//중복이아닌경우
		function notFound() {
			alert("사용할 수 있는 아이디 입니다!");
			checkUser_idFrom.user_id.focus();
		}

	</script>
</head>

<h1 align="center">아이디 중복 체크</h1>

<!-- 최초진입시 -->
<c:if test="${notFound == 2}">
	<body>
</c:if>

<!-- 아이디 중복인 경우 경고 -->
<c:if test="${notFound == 1}">
	<body onload="return found();">
</c:if>

<!-- 아이디 중복이 되지 않았을 경우 -->
<c:if test="${notFound == 0}">
	<body onload="return notFound();">
</c:if>
	
	<form name="checkUser_idFrom" action="/foodSen/checkUser_id.do" method="post" onSubmit="return checkIt()">
	
		<table border="0" align="center" width="500">
			
			<tr>
				<td height="8"colspan="2" align="center"> <font size=3><b>아이디를 입력해주세요</b></font></td>
			</tr>
			
			
			<tr>
				<td height="8"colspan="2" align="center">
					<input type="text" name="user_id" id="user_id" style='IME-MODE:disabled' value="${user_id}" size="15" maxlength="12" onkeypress="toPass(); toPass1(); toPass2();" onblur="toPass(); toPass1();" />
					<input type="submit" name="submit" value="중복체크"/>
				</td>
			</tr>

	
			<tr>
				<td height="8"colspan="2" align="center">
					<input type="button" value="사용하기" onclick="close()" />
				</td>
			</tr>
			
		</table>
	
	</form>
	
</body>
</html>

