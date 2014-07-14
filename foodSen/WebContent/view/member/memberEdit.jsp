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
		
		
		//�̸������� üũ
		function emailValue(){
			var t = escape(this.memberCreateForm.sen_email.value);
			//sung2li@naver.com �϶��� sung2li@naver.co.kr�϶��� ���� üũ
			if (t.match(/^(\w+)@(\w+)[.](\w+)$/ig) == null && t.match(/^(\w+)@(\w+)[.](\w+)[.](\w+)$/ig) == null) {
				alert("�̸����� ���� �ʽ��ϴ�.");
			}
		}
		
		//��ȭ��ȣ (-) �ڵ�����ŷ
		function mphon( obj ) { 
			obj.value =  PhonNumStr( obj.value ); 
		} 
		
		function PhonNumStr( str ){ 
			var RegNotNum  = /[^0-9]/g; 
			var RegPhonNum = ""; 
			var DataForm   = ""; 
		
			// return blank     
			if( str == "" || str == null ) return ""; 
		
			// delete not number
			str = str.replace(RegNotNum,''); 
		
			if( str.length < 4 ) return str; 
		
			if( str.length > 3 && str.length < 7 ) { 
			   	DataForm = "$1-$2"; 
				RegPhonNum = /([0-9]{3})([0-9]+)/; 
			} else if(str.length == 7 ) {
				DataForm = "$1-$2"; 
				RegPhonNum = /([0-9]{3})([0-9]{4})/; 
			} else if(str.length == 9 ) {
				DataForm = "$1-$2-$3"; 
				RegPhonNum = /([0-9]{2})([0-9]{3})([0-9]+)/; 
			} else if(str.length == 10){ 
				if(str.substring(0,2)=="02"){
					DataForm = "$1-$2-$3"; 
					RegPhonNum = /([0-9]{2})([0-9]{4})([0-9]+)/; 
				}else{
					DataForm = "$1-$2-$3"; 
					RegPhonNum = /([0-9]{3})([0-9]{3})([0-9]+)/;
				}
			} else if(str.length > 10){ 
				DataForm = "$1-$2-$3"; 
				RegPhonNum = /([0-9]{3})([0-9]{4})([0-9]+)/; 
			} 
		
			while( RegPhonNum.test(str) ) {  
				str = str.replace(RegPhonNum, DataForm);  
			} 
		
			return str; 
		}
		//.��ȭ��ȣ �ڵ�����ŷ ����
		
	</script>
</head>


<h1 align="center">���� �����Ư������û Ȩ������(ȸ������)</h1>

<body>

<form name="memberEditForm" action="/foodSen/memberEdit.do" method="post" onSubmit="return checkIt()">
	
	<table border="1" align="center" width="700" bordercolor="#E7E7E7">
	
		<!-- ������ ���� ��� -->
		<c:if test="${sessionScope.session_id == null}">
			<tr>	
				<td colspan="2" align="center">
					�α��� �� �̿��� �ּ���!
				</td>
			</tr>
		</c:if>
		<!-- .//������ ���� ��� -->
		
	
		<!-- ������ �ִ� ��� -->
		<c:if test="${sessionScope.session_id != null}">
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
					
					<c:if test="${resultClass.school_type == 0}">
						<font color="red">������</font><input type="hidden" name="school_type" value="0" />
					</c:if>
					
					<c:if test="${resultClass.school_type == 01}">
						<select name = "school_type">
							<option value = "01" selected>�ʵ��б�</option>
							<option value = "02">���б�</option>
							<option value = "03">����б�</option>
							<option value = "04">Ư���б�</option>
							<option value = "05">����</option>
						</select>
					</c:if>
					
					<c:if test="${resultClass.school_type == 02}">
						<select name = "school_type">
							<option value = "01">�ʵ��б�</option>
							<option value = "02" selected>���б�</option>
							<option value = "03">����б�</option>
							<option value = "04">Ư���б�</option>
							<option value = "05">����</option>
						</select>
					</c:if>
					
					<c:if test="${resultClass.school_type == 03}">
						<select name = "school_type">
							<option value = "01">�ʵ��б�</option>
							<option value = "02">���б�</option>
							<option value = "03" selected>����б�</option>
							<option value = "04">Ư���б�</option>
							<option value = "05">����</option>
						</select>
					</c:if>
					
					<c:if test="${resultClass.school_type == 04}">
						<select name = "school_type">
							<option value = "01">�ʵ��б�</option>
							<option value = "02">���б�</option>
							<option value = "03">����б�</option>
							<option value = "04" selected>Ư���б�</option>
							<option value = "05">����</option>
						</select>
					</c:if>
					
					<c:if test="${resultClass.school_type == 05}">
						<select name = "school_type">
							<option value = "01">�ʵ��б�</option>
							<option value = "02">���б�</option>
							<option value = "03">����б�</option>
							<option value = "04">Ư���б�</option>
							<option value = "05" selected>����</option>
						</select>
					</c:if>
					
				</td>
			</tr>
			
			<tr>	
				<td width="200" height="8" align="right">����</td>
				<td width="500" height="8" align="left">
				
					<c:if test="${resultClass.position == 0}">
						<font color="red">������</font><input type="hidden" name="position" value="0" />
					</c:if>
					
					<c:if test="${resultClass.position == 01}">
						<select name = "position">
							<option value = "01" selected>�����</option>
							<option value = "02">������</option>
							<option value = "03">������</option>
						</select>
					</c:if>
					
					<c:if test="${resultClass.position == 02}">
						<select name = "position">
							<option value = "01">�����</option>
							<option value = "02" selected>������</option>
							<option value = "03">������</option>
						</select>
					</c:if>
					
					<c:if test="${resultClass.position == 03}">
						<select name = "position">
							<option value = "01">�����</option>
							<option value = "02">������</option>
							<option value = "03" selected>������</option>
						</select>
					</c:if>
					
				</td>
			</tr>
			
			<tr>	
				<td width="200" height="16" align="right">�̸���</td>
				<td width="500" height="16" align="left">
					<input type="text" name="sen_email" id="sen_email" size="45" maxlength="20" value="${resultClass.sen_email}" onblur="emailValue();"/>
					<br/>
					<font color="gray" size="2">��� �ּҸ� �������ּ���. �ִ�20��</font>
				</td>
			</tr>
			
			<tr>	
				<td width="200" height="16" align="right">����ȣ</td>
				<td width="500" height="16" align="left">
					<input type="text" name="phone" id="phone" size="45" maxlength="13" onkeydown="mphon(this);" onkeyup="mphon(this);" value="${resultClass.phone}" />
				</td>
			</tr>
			
			<tr>
				<td height="8"colspan="2" align="right">
					<input type="submit" name="submit" value="����"/>
				</td>
			</tr>
		</c:if>
		<!-- .//������ �ִ� ��� -->
		
	</table>
	
</form>

</body>
</html>