<%@ page language="java" contentType="text/html; charset=EUC-KR"
    pageEncoding="EUC-KR"%>
    
<html>
<head>
	<title>�����ϱ�</title>
	
	<SCRIPT type="text/javascript">
		//null ��ȿ���˻�
		function checkIt(){
			inputForm=eval("document.memberCreateForm");

			if(!inputForm.user_id.value){
				alert("���̵� �Է����ּ���.");
				memberCreateForm.user_id.focus();
				return false;
			}
			if(!inputForm.user_pw.value){
				alert("��й�ȣ�� �Է����ּ���.");
				memberCreateForm.user_pw.focus();
				return false;
			}
			if(!inputForm.member_name.value){
				alert("�̸��� �Է����ּ���.");
				memberCreateForm.member_name.focus();
				return false;
			}
			if(!inputForm.school_name.value){
				alert("�б����� �Է����ּ���.");
				memberCreateForm.school_name.focus();
				return false;
			}
			if(!inputForm.sen_email.value){
				alert("�̸����� �Է����ּ���.");
				memberCreateForm.sen_email.focus();
				return false;
			}
		}

		//��й�ȣ, ���Էµ� ��й�ȣȭ ��ġ���� ���� ���
		function checkPw(){
			inputForm=eval("document.memberCreateForm");

			if(inputForm.user_pw.value != inputForm.user_pw_re.value){
				alert("��й�ȣ�� ��ġ���� �ʽ��ϴ�.");
				return false;
			}
		}

	</script>
</head>

<h1 align="center">ȸ������</h1>

<body>

<form name="memberCreateForm" action="/foodSen/memberCreate.do" method="post" onSubmit="return checkIt()">

	<table border="1" align="center" width="700" bordercolor="#E7E7E7">
		<tr>
			<td width="200" height="8" align="right">���̵�</td>
			<td width="500" height="8" align="left">
				<input type="text" name="user_id" id="user_id" size="12" maxlength="10" />
				<font color="gray" size="2">���̵�� ����, ���� �ִ� 12�ڸ����� ������ �� �ֽ��ϴ�.</font>
			</td>
		</tr>
		
		<tr>	
			<td width="200" height="8" align="right">��й�ȣ</td>
			<td width="500" height="8" align="left">
				<input type="password" name="user_pw" id="user_pw" size="21" maxlength="15" />
				<font color="gray" size="2">��й�ȣ�� �ִ� 15�ڸ����� �Է°����մϴ�.</font>
			</td>
		</tr>
		
		<tr>	
			<td width="200" height="8" align="right">��й�ȣ</td>
			<td width="500" height="8" align="left">
				<input type="password" name="user_pw_re" id="user_pw_re" size="21" maxlength="15" onblur="checkPw();"/>
				<font color="gray" size="2">��й�ȣ Ȯ���� ���� ���Է����ּ���.</font>
			</td>
		</tr>
		
		<tr>
			<td width="200" height="8" align="right">��&nbsp;��</td>
			<td width="500" height="8" align="left">
				<input type="text" name="member_name" id="member_name" size="7" maxlength="4" />
				<font color="gray" size="2">�̸��� 4���ڱ��� �Է°����մϴ�.</font>
			</td>
		</tr>
		
		<tr>	
			<td width="200" height="8" align="right">�б���</td>
			<td width="500" height="8" align="left">
				<input type="text" name="school_name" id="school_name" size="7" maxlength="5" />
				<font color="gray" size="2">�ִ� 5�� �Է�</font>
			</td>
		</tr>
		
		<tr>	
			<td width="200" height="8" align="right">�б�����</td>
			<td width="500" height="8" align="left">
				<select name = "school_type">
					<option value = "1">�ʵ��б�</option>
					<option value = "2">���б�</option>
					<option value = "3">����б�</option>
				</select>
			</td>
		</tr>
		
		<tr>	
			<td width="200" height="8" align="right">����</td>
			<td width="500" height="8" align="left">
				<select name = "position">
					<option value = "1">�����</option>
					<option value = "2">������</option>
				</select>
			</td>
		</tr>

		<tr>	
			<td width="200" height="16" align="right">�̸���</td>
			<td width="500" height="16" align="left">
				<input type="text" name="sen_email" id="sen_email" size="45" maxlength="20" />
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
