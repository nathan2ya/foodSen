<%@ page language="java" contentType="text/html; charset=euc-kr"
	pageEncoding="euc-kr"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<html>
<head>
	<title>�α���</title>
	
	<SCRIPT type="text/javascript">
		//null ��ȿ���˻�
		function checkIt(){
			inputForm=eval("document.loginForm");
		 
			if(!inputForm.user_id.value){
				alert("���̵� �Է����ּ���.");
				loginForm.user_id.focus();
				return false;
			}
			if(!inputForm.user_pw.value){
				alert("��й�ȣ�� �Է����ּ���.");
				loginForm.user_pw.focus();
				return false;
			}
		}
		
		//���ڵ带 ã�� ���Ѱ��
		function notFound(){
			alert("���̵� �Ǵ� ��й�ȣ�� Ȯ�����ּ���");
			loginForm.user_pw.focus();
		}
		
	</script>
</head>


<h1 align="center">�α���</h1>


<!-- ���ڵ带 ã�� ���� ��� ��� -->
<c:if test="${notFound == 1}">
	<body onload="return notFound();">
</c:if>

<!-- ���� ���� -->
<c:if test="${notFound == 0}">
	<body>
</c:if>
	
	<!-- �� �α��λ��� -->
	<c:if test="${sessionScope.session_id == null}">
		
		<form name="loginForm" action="/foodSen/loginPro.do" method="post" onSubmit="return checkIt()">
		
			<table border="1" align="center" width="800" bordercolor="#E7E7E7">
				<tr>
					<td>���̵�</td>
					<td><input type="text" name="user_id" id="user_id" size="20" maxlength="20" value="${user_id}"/></td>
				</tr>
				<tr>
					<td>��й�ȣ</td>
					<td><input type="password" name="user_pw" id="user_pw" size="20" maxlength="20" /></td>
				</tr>
				
				<tr>
					<td colspan="2">
						<input type="submit" name="submit" value="�α���"/>
					</td>
				</tr>
			</table>
			
		</form>
	</c:if>
	
	
	<!-- �α��εȻ��� -->
	<c:if test="${sessionScope.session_id != null}">
		<form name="loginedForm" action="/foodSen/logoutPro.do" method="post" onSubmit="return checkIt()">
		
			<table border="1" align="center" width="800" bordercolor="#E7E7E7">
				<tr>
					<td colspan="2">
						${sessionScope.session_id}�� ȯ���մϴ�.
					</td>
				</tr>
				
				<tr>
					<td colspan="2">
						<input type="submit" name="submit" value="�α׾ƿ�"/>
					</td>
				</tr>
			</table>
			
		</form>
	</c:if>
	
</body>