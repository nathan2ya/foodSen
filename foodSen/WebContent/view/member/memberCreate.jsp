<%@ page language="java" contentType="text/html; charset=EUC-KR"
    pageEncoding="EUC-KR"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
    
<html>
<head>
	<title>가입하기</title>
	
	<script src="http://dmaps.daum.net/map_js_init/postcode.js"></script>
	
	
	<SCRIPT type="text/javascript">
	
		//공백제거
		function toPass(){
			var input = document.memberCreateForm.user_id.value;//사용자가 입력한 값
			document.memberCreateForm.user_id.value = input.replace(/^\s+|\s+$/g,""); //앞뒤 공백을 제거함
		}
		
		//대문자를 소문자로 변경
		function toPass1(){ 
			var input = document.memberCreateForm.user_id.value;//사용자가 입력한 값
			document.memberCreateForm.user_id.value = input.toLowerCase(); //대문자를 소문자로 변경
		}
		
		//특수문자가 있을 경우 경고
		function toPass2(){
			if((event.keyCode >=32&&event.keyCode <=47)||(event.keyCode>=58&&event.keyCode<=64)||(event.keyCode>=91&&event.keyCode<=96)||(event.keyCode>=123&&event.keyCode<=126) ) {
				alert("아이디에 공백 및 특수문자는 입력할 수 없습니다.");
			}
		}
		

		//아이디 중복 확인
		function toPass3() {
			inputForm = eval("document.memberCreateForm");
			user_id = inputForm.user_id.value;

			if (!inputForm.user_id.value) {
				alert("아이디 입력 후 중복체크 해주세요!");
				memberCreateForm.user_id.focus();
				return false;
			}

			//url
			url = "http://localhost:8000/foodSen/checkUser_idFrom.do?user_id="
					+ user_id
			// 새로운 윈도우를 엽니다.
			open(
					url,
					"confirm",
					"toolbar=no,location=no,status=no,menubar=no,scrollbars=no,resizable=no,width=500,height=220");
		}

		//아이디 중복체크를 실행하지 않았을 경우
		function checkIDvalue() {
			memberCreateForm.idCheck.value = 0;
		}

		//이메일형식 체크
		function emailValue() {
			var t = escape(this.memberCreateForm.sen_email.value);
			//sung2li@naver.com 일때와 sung2li@naver.co.kr일때의 형식 체크
			if (t.match(/^(\w+)@(\w+)[.](\w+)$/ig) == null
					&& t.match(/^(\w+)@(\w+)[.](\w+)[.](\w+)$/ig) == null) {
				alert("이메일이 맞지 않습니다.");
			}
		}

		//null 유효성검사
		function checkIt() {
			inputForm = eval("document.memberCreateForm");

			if (!inputForm.user_id.value) {
				alert("아이디를 입력해주세요.");
				memberCreateForm.user_id.focus();
				return false;
			}
			if (!inputForm.user_pw.value) {
				alert("비밀번호를 입력해주세요.");
				memberCreateForm.user_pw.focus();
				return false;
			}
			if (!inputForm.member_name.value) {
				alert("이름를 입력해주세요.");
				memberCreateForm.member_name.focus();
				return false;
			}
			if (!inputForm.school_name.value) {
				alert("학교명을 입력해주세요.");
				memberCreateForm.school_name.focus();
				return false;
			}
			if (!inputForm.sen_email.value) {
				alert("이메일을 입력해주세요.");
				memberCreateForm.sen_email.focus();
				return false;
			}

			if (memberCreateForm.idCheck.value == 0) {
				alert("아이디 중복 체크를 실행해주세요.");
				return false;
			}

			alert(inputForm.user_id.value + "님! 회원가입이 완료되었습니다.");
			window.close();
		}

		//비밀번호, 재입력된 비밀번호화 일치하지 않을 경우
		function checkPw() {
			inputForm = eval("document.memberCreateForm");

			if (inputForm.user_pw.value != inputForm.user_pw_re.value) {
				alert("비밀번호가 일치하지 않습니다.");
				memberCreateForm.user_pw_re.focus();
				return false;
			}
		}
		
		
		//전화번호 (-) 자동마스킹
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
		//.전화번호 자동마스킹 종료
		
		
		
		//다음 주소 API
		function openDaumPostcode() {
	    	new daum.Postcode({
	        	oncomplete: function(data) {
	                // 팝업에서 검색결과 항목을 클릭했을때 실행할 코드를 작성하는 부분. 우편번호와 주소 정보를 해당 필드에 넣고, 커서를 상세주소 필드로 이동한다.
	                document.getElementById("post1").value = data.postcode1;
	                document.getElementById("post2").value = data.postcode2;
	                document.getElementById("addr").value = data.address;
	                //전체 주소에서 연결 지번 및 ()로 묶여 있는 부가정보를 제거하고자 할 경우,
	                //아래와 같은 정규식을 사용해도 된다. 정규식은 사용자의 입맛에 맞게 수정해서 사용 가능하다.
	                //var addr = data.address.replace(/(\s|^)\(.+\)$|\S+~\S+/g, '');
	                //document.getElementById(&#39;addr&#39;).value = addr;
	                document.getElementById("addr2").focus();
	            }
	        }).open();
	    }
		//.다음 주소 API 종료
		
		
	</script>
</head>

<h1 align="center">가상 서울시특별교육청 홈페이지(회원가입)</h1>

<body style="padding-left: 50px">
	
	<form name="memberCreateForm" action="/foodSen/memberCreate.do" method="post" onSubmit="return checkIt()">
	
		<table border="1" align="center" width="700" bordercolor="#E7E7E7">
			<tr>
				<td width="200" height="8" align="right">아이디</td>
				<td width="500" height="8" align="left">
					<input type="text" name="user_id" id="user_id" size="15" style='IME-MODE:disabled' maxlength="12" onkeyup="checkIDvalue();" onkeypress="toPass(); toPass1(); toPass2();" onblur="toPass(); toPass1();" />
					<input type="button" name="checkID" value="ID중복체크" onClick="toPass3();" />
					<br/>
					<font color="gray" size="2">아이디는 영문(소문자), 숫자 최대 12자리, 특수문자 및 공백이 포함될 수 없습니다.</font>
				</td>
			</tr>
			
			<tr>	
				<td width="200" height="8" align="right">비밀번호</td>
				<td width="500" height="8" align="left">
					<input type="password" name="user_pw" id="user_pw" size="21" maxlength="15" />
					<font color="gray" size="2">비밀번호는 최대 15자리까지 입력가능합니다.</font>
				</td>
			</tr>
			
			<tr>	
				<td width="200" height="8" align="right">비밀번호</td>
				<td width="500" height="8" align="left">
					<input type="password" name="user_pw_re" id="user_pw_re" size="21" maxlength="15" onblur="checkPw();"/>
					<font color="gray" size="2">비밀번호 확인을 위해 재입력해주세요.</font>
				</td>
			</tr>
			
			<tr>
				<td width="200" height="8" align="right">이&nbsp;름</td>
				<td width="500" height="8" align="left">
					<input type="text" name="member_name" id="member_name" size="7" maxlength="4" />
					<font color="gray" size="2">이름은 4글자까지 입력가능합니다.</font>
				</td>
			</tr>
			
			<tr>	
				<td width="200" height="8" align="right">우편번호</td>
				<td width="500" height="8" align="left">
					<input type="text" id="post1" name="post1" size="8" class="d_form mini"> - <input type="text" id="post2" name="post2" size="8" class="d_form mini">
				    <input type="button" onclick="openDaumPostcode()" value="우편번호 찾기" class="d_btn">
				</td>
			</tr>
			
			<tr>	
				<td width="200" height="8" align="right">주소</td>
				<td width="500" height="8" align="left">
					<input type="text" id="addr" name="addr" class="d_form" size="45" placeholder="주소">
				</td>
			</tr>
			
			<tr>	
				<td width="200" height="8" align="right">상세주소</td>
				<td width="500" height="8" align="left">
					<input type="text" id="addr2" name="addr2" class="d_form" size="45" placeholder="상세주소">
				</td>
			</tr>
			
			<tr>	
				<td width="200" height="8" align="right">학교명</td>
				<td width="500" height="8" align="left">
					<input type="text" name="school_name" id="school_name" size="7" maxlength="5" />
					<font color="gray" size="2">최대 5자 입력</font>
				</td>
			</tr>
			
			<tr>	
				<td width="200" height="8" align="right">학교종류</td>
				<td width="500" height="8" align="left">
					<select name = "school_type">
						<option value = "01">초등학교</option>
						<option value = "02">중학교</option>
						<option value = "03">고등학교</option>
						<option value = "04">특수학교</option>
						<option value = "05">각종</option>
					</select>
				</td>
			</tr>
			
			<tr>	
				<td width="200" height="8" align="right">직위</td>
				<td width="500" height="8" align="left">
					<select name = "position">
						<option value = "01">영양사</option>
						<option value = "02">조리원</option>
						<option value = "03">교직원</option>
					</select>
				</td>
			</tr>
	
			<tr>	
				<td width="200" height="16" align="right">이메일</td>
				<td width="500" height="16" align="left">
					<input type="text" name="sen_email" id="sen_email" size="45" maxlength="20" onblur="emailValue();"/>
					<br/>
					<font color="gray" size="2">모든 주소를 기입해주세요. 최대20자</font>
				</td>
			</tr>
			
			<tr>	
				<td width="200" height="16" align="right">폰번호</td>
				<td width="500" height="16" align="left">
					<input type="text" name="phone" id="phone" size="45" maxlength="13" onkeydown="mphon(this);" onkeyup="mphon(this);" />
				</td>
			</tr>
	
			<tr>
				<td height="8"colspan="2" align="right">
				
					<!-- 아이디중복체크 여부 논리값. 0은 중복체크 안함, 1은 중복체크 완료 -->
					<input type="hidden" name="idCheck" value="0" />
					
					<!-- 최종가입신청 -->
					<input type="submit" name="submit" value="가입하기"/>
					
				</td>
			</tr>
		</table>
	
	</form>
	
</body>
</html>
