<%@ page language="java" contentType="text/html; charset=euc-kr"
	pageEncoding="euc-kr"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<html>
<head>
	<title>�����ϱ�</title>
	
	<SCRIPT type="text/javascript">
		//null ��ȿ���˻�
		function checkIt(){
			inputForm=eval("document.memberEditForm");
		 
			if(!inputForm.user_pw.value){
				alert("��й�ȣ�� �Է����ּ���.");
				memberEditForm.user_pw.focus();
				return false;
			}
			if(!inputForm.school_name.value){
				alert("�б����� �Է����ּ���.");
				memberEditForm.school_name.focus();
				return false;
			}
			if(!inputForm.sen_email.value){
				alert("�̸����� �Է����ּ���.");
				memberEditForm.sen_email.focus();
				return false;
			}
		}
	</script>
</head>


<h1 align="center">ȸ������</h1>

<body>

<form name="memberEditForm" action="/foodSen/memberEdit.do" method="post" onSubmit="return checkIt()">
	
	<table border="1" align="center" width="700" bordercolor="#E7E7E7">
		<tr>
			<td width="200" height="8" align="right">���̵�</td>
			<td width="500" height="8" align="left">
				${resultClass.user_id}
			</td>
		</tr>
		
		<tr>	
			<td width="200" height="8" align="right">��й�ȣ</td>
			<td width="500" height="8" align="left">
				<input type="password" name="user_pw" id="user_pw" size="21" maxlength="15" value="${resultClass.user_pw}"/>
				<font color="gray" size="2">��й�ȣ�� �ִ� 15�ڸ����� �Է°����մϴ�.</font>
			</td>
		</tr>
			
		<tr>
			<td width="200" height="8" align="right">��&nbsp;��</td>
			<td width="500" height="8" align="left">
				${resultClass.member_name}
			</td>
		</tr>
		
		<tr>	
			<td width="200" height="8" align="right">�б���</td>
			<td width="500" height="8" align="left">
				<input type="text" name="school_name" id="school_name"size="7" maxlength="5" value="${resultClass.school_name}"/>
				<font color="gray" size="2">�ִ� 5�� �Է�</font>
			</td>
		</tr>
		
		<tr>	
			<td width="200" height="8" align="right">�б�����</td>
			<td width="500" height="8" align="left">
				
				<c:if test="${resultClass.school_type == 1}">
					<select name = "school_type">
						<option value = "1" selected>�ʵ��б�</option>
						<option value = "2">���б�</option>
						<option value = "3">����б�</option>
					</select>
				</c:if>
				
				<c:if test="${resultClass.school_type == 2}">
					<select name = "school_type">
						<option value = "1">�ʵ��б�</option>
						<option value = "2" selected>���б�</option>
						<option value = "3">����б�</option>
					</select>
				</c:if>
				
				<c:if test="${resultClass.school_type == 3}">
					<select name = "school_type">
						<option value = "1">�ʵ��б�</option>
						<option value = "2">���б�</option>
						<option value = "3" selected>����б�</option>
					</select>
				</c:if>
				
			</td>
		</tr>
		
		<tr>	
			<td width="200" height="8" align="right">����</td>
			<td width="500" height="8" align="left">
				
				<c:if test="${resultClass.position == 1}">
					<select name = "position">
						<option value = "1" selected>�����</option>
						<option value = "2">������</option>
					</select>
				</c:if>
				
				<c:if test="${resultClass.position == 2}">
					<select name = "position">
						<option value = "1">�����</option>
						<option value = "2" selected>������</option>
					</select>
				</c:if>
				
			</td>
		</tr>
		
		<tr>	
			<td width="200" height="16" align="right">�̸���</td>
			<td width="500" height="16" align="left">
				<input type="text" name="sen_email" id="sen_email" size="45" maxlength="20" value="${resultClass.sen_email}"/>
				<br/>
				<font color="gray" size="2">��� �ּҸ� �������ּ���. �ִ�20��</font>
			</td>
		</tr>
		
		<tr>
			<td height="8"colspan="2" align="center">
				<input type="submit" name="submit" value="����"/>
				<input type="button" name="cancel" value="���" onClick="javascript:location.href='/foodSen/main.do'"/>
			</td>
		</tr>
	</table>
	
</form>

</body>
</html>