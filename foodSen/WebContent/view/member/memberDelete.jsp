<%@ page language="java" contentType="text/html; charset=euc-kr"
	pageEncoding="euc-kr"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<html>
<head>
	<title>Ż���ϱ�</title>
	
	<SCRIPT type="text/javascript">
		//null ��ȿ���˻�
		function checkIt(){
			inputForm=eval("document.memberDeleteForm");
			var password = ${resultClass.user_pw};
		 
			if(!inputForm.user_pw.value){
				alert("��й�ȣ�� �Է����ּ���.");
				memberDeleteForm.user_pw.focus();
				return false;
			}
			if(inputForm.user_pw.value != password){
				alert("��й�ȣ�� Ȯ�����ּ���");
				memberDeleteForm.user_pw.focus();
				return false;
			}
			
			alert("Ż�� ���������� �Ϸ�Ǿ����ϴ�!");
		}
	</script>
</head>


<h1 align="center">���� �����Ư������û Ȩ������(ȸ��Ż��)</h1>

<body>

<form name="memberDeleteForm" action="/foodSen/memberDelete.do" method="post" onSubmit="return checkIt()">
	
	<table border="1" align="center" width="700" bordercolor="#E7E7E7">
		
		<tr>	
			<td colspan="2" align="center">
				${sessionScope.session_id}���� ���������� ���� ��й�ȣ�� ��Ȯ�� �ϰڽ��ϴ�. <br/>
				<input type="password" name="user_pw" id="user_pw" size="21" maxlength="15" />
			</td>
		</tr>
			
		<tr>
			<td height="8"colspan="2" align="right">
				<input type="submit" name="submit" value="Ż��"/>
			</td>
		</tr>
		
	</table>
	
</form>

</body>
</html>