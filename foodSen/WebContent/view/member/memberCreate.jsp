<%@ page language="java" contentType="text/html; charset=EUC-KR"
    pageEncoding="EUC-KR"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
    
<html>
<head>
	<title>�����ϱ�</title>
	
	<script src="http://dmaps.daum.net/map_js_init/postcode.js"></script>
	
	
	<SCRIPT type="text/javascript">
	
		//��������
		function toPass(){
			var input = document.memberCreateForm.user_id.value;//����ڰ� �Է��� ��
			document.memberCreateForm.user_id.value = input.replace(/^\s+|\s+$/g,""); //�յ� ������ ������
		}
		
		//�빮�ڸ� �ҹ��ڷ� ����
		function toPass1(){ 
			var input = document.memberCreateForm.user_id.value;//����ڰ� �Է��� ��
			document.memberCreateForm.user_id.value = input.toLowerCase(); //�빮�ڸ� �ҹ��ڷ� ����
		}
		
		//Ư�����ڰ� ���� ��� ���
		function toPass2(){
			if((event.keyCode >=32&&event.keyCode <=47)||(event.keyCode>=58&&event.keyCode<=64)||(event.keyCode>=91&&event.keyCode<=96)||(event.keyCode>=123&&event.keyCode<=126) ) {
				alert("���̵� ���� �� Ư�����ڴ� �Է��� �� �����ϴ�.");
			}
		}
		

		//���̵� �ߺ� Ȯ��
		function toPass3() {
			inputForm = eval("document.memberCreateForm");
			user_id = inputForm.user_id.value;

			if (!inputForm.user_id.value) {
				alert("���̵� �Է� �� �ߺ�üũ ���ּ���!");
				memberCreateForm.user_id.focus();
				return false;
			}

			//url
			url = "http://localhost:8000/foodSen/checkUser_idFrom.do?user_id="
					+ user_id
			// ���ο� �����츦 ���ϴ�.
			open(
					url,
					"confirm",
					"toolbar=no,location=no,status=no,menubar=no,scrollbars=no,resizable=no,width=500,height=220");
		}

		//���̵� �ߺ�üũ�� �������� �ʾ��� ���
		function checkIDvalue() {
			memberCreateForm.idCheck.value = 0;
		}

		//�̸������� üũ
		function emailValue() {
			var t = escape(this.memberCreateForm.sen_email.value);
			//sung2li@naver.com �϶��� sung2li@naver.co.kr�϶��� ���� üũ
			if (t.match(/^(\w+)@(\w+)[.](\w+)$/ig) == null
					&& t.match(/^(\w+)@(\w+)[.](\w+)[.](\w+)$/ig) == null) {
				alert("�̸����� ���� �ʽ��ϴ�.");
			}
		}

		//null ��ȿ���˻�
		function checkIt() {
			inputForm = eval("document.memberCreateForm");

			if (!inputForm.user_id.value) {
				alert("���̵� �Է����ּ���.");
				memberCreateForm.user_id.focus();
				return false;
			}
			if (!inputForm.user_pw.value) {
				alert("��й�ȣ�� �Է����ּ���.");
				memberCreateForm.user_pw.focus();
				return false;
			}
			if (!inputForm.member_name.value) {
				alert("�̸��� �Է����ּ���.");
				memberCreateForm.member_name.focus();
				return false;
			}
			if (!inputForm.school_name.value) {
				alert("�б����� �Է����ּ���.");
				memberCreateForm.school_name.focus();
				return false;
			}
			if (!inputForm.sen_email.value) {
				alert("�̸����� �Է����ּ���.");
				memberCreateForm.sen_email.focus();
				return false;
			}

			if (memberCreateForm.idCheck.value == 0) {
				alert("���̵� �ߺ� üũ�� �������ּ���.");
				return false;
			}

			alert(inputForm.user_id.value + "��! ȸ�������� �Ϸ�Ǿ����ϴ�.");
			window.close();
		}

		//��й�ȣ, ���Էµ� ��й�ȣȭ ��ġ���� ���� ���
		function checkPw() {
			inputForm = eval("document.memberCreateForm");

			if (inputForm.user_pw.value != inputForm.user_pw_re.value) {
				alert("��й�ȣ�� ��ġ���� �ʽ��ϴ�.");
				memberCreateForm.user_pw_re.focus();
				return false;
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
		
		
		
		//���� �ּ� API
		function openDaumPostcode() {
	    	new daum.Postcode({
	        	oncomplete: function(data) {
	                // �˾����� �˻���� �׸��� Ŭ�������� ������ �ڵ带 �ۼ��ϴ� �κ�. �����ȣ�� �ּ� ������ �ش� �ʵ忡 �ְ�, Ŀ���� ���ּ� �ʵ�� �̵��Ѵ�.
	                document.getElementById("post1").value = data.postcode1;
	                document.getElementById("post2").value = data.postcode2;
	                document.getElementById("addr").value = data.address;
	                //��ü �ּҿ��� ���� ���� �� ()�� ���� �ִ� �ΰ������� �����ϰ��� �� ���,
	                //�Ʒ��� ���� ���Խ��� ����ص� �ȴ�. ���Խ��� ������� �Ը��� �°� �����ؼ� ��� �����ϴ�.
	                //var addr = data.address.replace(/(\s|^)\(.+\)$|\S+~\S+/g, '');
	                //document.getElementById(&#39;addr&#39;).value = addr;
	                document.getElementById("addr2").focus();
	            }
	        }).open();
	    }
		//.���� �ּ� API ����
		
		
	</script>
</head>

<h1 align="center">���� �����Ư������û Ȩ������(ȸ������)</h1>

<body style="padding-left: 50px">
	
	<form name="memberCreateForm" action="/foodSen/memberCreate.do" method="post" onSubmit="return checkIt()">
	
		<table border="1" align="center" width="700" bordercolor="#E7E7E7">
			<tr>
				<td width="200" height="8" align="right">���̵�</td>
				<td width="500" height="8" align="left">
					<input type="text" name="user_id" id="user_id" size="15" style='IME-MODE:disabled' maxlength="12" onkeyup="checkIDvalue();" onkeypress="toPass(); toPass1(); toPass2();" onblur="toPass(); toPass1();" />
					<input type="button" name="checkID" value="ID�ߺ�üũ" onClick="toPass3();" />
					<br/>
					<font color="gray" size="2">���̵�� ����(�ҹ���), ���� �ִ� 12�ڸ�, Ư������ �� ������ ���Ե� �� �����ϴ�.</font>
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
				<td width="200" height="8" align="right">�����ȣ</td>
				<td width="500" height="8" align="left">
					<input type="text" id="post1" name="post1" size="8" class="d_form mini"> - <input type="text" id="post2" name="post2" size="8" class="d_form mini">
				    <input type="button" onclick="openDaumPostcode()" value="�����ȣ ã��" class="d_btn">
				</td>
			</tr>
			
			<tr>	
				<td width="200" height="8" align="right">�ּ�</td>
				<td width="500" height="8" align="left">
					<input type="text" id="addr" name="addr" class="d_form" size="45" placeholder="�ּ�">
				</td>
			</tr>
			
			<tr>	
				<td width="200" height="8" align="right">���ּ�</td>
				<td width="500" height="8" align="left">
					<input type="text" id="addr2" name="addr2" class="d_form" size="45" placeholder="���ּ�">
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
						<option value = "01">�ʵ��б�</option>
						<option value = "02">���б�</option>
						<option value = "03">����б�</option>
						<option value = "04">Ư���б�</option>
						<option value = "05">����</option>
					</select>
				</td>
			</tr>
			
			<tr>	
				<td width="200" height="8" align="right">����</td>
				<td width="500" height="8" align="left">
					<select name = "position">
						<option value = "01">�����</option>
						<option value = "02">������</option>
						<option value = "03">������</option>
					</select>
				</td>
			</tr>
	
			<tr>	
				<td width="200" height="16" align="right">�̸���</td>
				<td width="500" height="16" align="left">
					<input type="text" name="sen_email" id="sen_email" size="45" maxlength="20" onblur="emailValue();"/>
					<br/>
					<font color="gray" size="2">��� �ּҸ� �������ּ���. �ִ�20��</font>
				</td>
			</tr>
			
			<tr>	
				<td width="200" height="16" align="right">����ȣ</td>
				<td width="500" height="16" align="left">
					<input type="text" name="phone" id="phone" size="45" maxlength="13" onkeydown="mphon(this);" onkeyup="mphon(this);" />
				</td>
			</tr>
	
			<tr>
				<td height="8"colspan="2" align="right">
				
					<!-- ���̵��ߺ�üũ ���� ����. 0�� �ߺ�üũ ����, 1�� �ߺ�üũ �Ϸ� -->
					<input type="hidden" name="idCheck" value="0" />
					
					<!-- �������Խ�û -->
					<input type="submit" name="submit" value="�����ϱ�"/>
					
				</td>
			</tr>
		</table>
	
	</form>
	
</body>
</html>
