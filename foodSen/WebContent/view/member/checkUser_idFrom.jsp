<%@ page language="java" contentType="text/html; charset=EUC-KR"
    pageEncoding="EUC-KR"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
    
<html>
<head>
	<title>ID�ߺ�üũ</title>
	
	<SCRIPT type="text/javascript">
		
		//��������
		function toPass(){
			var input = document.checkUser_idFrom.user_id.value;//����ڰ� �Է��� ��
			document.checkUser_idFrom.user_id.value = input.replace(/^\s+|\s+$/g,""); //�յ� ������ ������
		}
		
		//�빮�ڸ� �ҹ��ڷ� ����
		function toPass1(){ 
			var input = document.checkUser_idFrom.user_id.value;//����ڰ� �Է��� ��
			document.checkUser_idFrom.user_id.value = input.toLowerCase(); //�빮�ڸ� �ҹ��ڷ� ����
		}
		
		//Ư�����ڰ� ���� ��� ���
		function toPass2(){
			if((event.keyCode >=32&&event.keyCode <=47)||(event.keyCode>=58&&event.keyCode<=64)||(event.keyCode>=91&&event.keyCode<=96)||(event.keyCode>=123&&event.keyCode<=126) ) {
				alert("���̵� ���� �� Ư�����ڴ� �Է��� �� �����ϴ�.");
			}
		}
		
		//null ��ȿ���˻�
		function checkIt() {
			inputForm = eval("document.checkUser_idFrom");

			if (!inputForm.user_id.value) {
				alert("���̵� �Է����ּ���.");
				checkUser_idFrom.user_id.focus();
				return false;
			}
		}
		
		//����ϱ�
		function close(){
			alert("aaaaaaaaaaaaaaaaaaaa");
			
			/* 
			var user_id = ${user_id};
			alert("���࿩�� �׽�Ʈ : ���̵�value = "+user_id);
			
			//ȣ���� �������� user_id �� ä���
			opener.document.memberCreateForm.user_id.value = user_id;
			self.close();
			 */
			
		}
		
		//���̵��ߺ��ΰ��
		function found() {
			alert("�̹� �����ϴ� ���̵� �Դϴ�! �ٸ����̵� ������ּ���.");
			checkUser_idFrom.user_id.focus();
		}
		
		//�ߺ��̾ƴѰ��
		function notFound() {
			alert("����� �� �ִ� ���̵� �Դϴ�!");
			checkUser_idFrom.user_id.focus();
		}

	</script>
</head>

<h1 align="center">���̵� �ߺ� üũ</h1>

<!-- �������Խ� -->
<c:if test="${notFound == 2}">
	<body>
</c:if>

<!-- ���̵� �ߺ��� ��� ��� -->
<c:if test="${notFound == 1}">
	<body onload="return found();">
</c:if>

<!-- ���̵� �ߺ��� ���� �ʾ��� ��� -->
<c:if test="${notFound == 0}">
	<body onload="return notFound();">
</c:if>
	
	<form name="checkUser_idFrom" action="/foodSen/checkUser_id.do" method="post" onSubmit="return checkIt()">
	
		<table border="0" align="center" width="500">
			
			<tr>
				<td height="8"colspan="2" align="center"> <font size=3><b>���̵� �Է����ּ���</b></font></td>
			</tr>
			
			
			<tr>
				<td height="8"colspan="2" align="center">
					<input type="text" name="user_id" id="user_id" style='IME-MODE:disabled' value="${user_id}" size="15" maxlength="12" onkeypress="toPass(); toPass1(); toPass2();" onblur="toPass(); toPass1();" />
					<input type="submit" name="submit" value="�ߺ�üũ"/>
				</td>
			</tr>

	
			<tr>
				<td height="8"colspan="2" align="center">
					<input type="button" value="����ϱ�" onclick="close()" />
				</td>
			</tr>
			
		</table>
	
	</form>
	
</body>
</html>

