<%@ page language="java" contentType="text/html; charset=euc-kr"
	pageEncoding="euc-kr"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<html>
<head>
	<title>수정하기</title>
	
	<SCRIPT type="text/javascript">
		//null 유효성검사
		function checkIt(){
			inputForm=eval("document.memberEditForm");
		 
			if(!inputForm.user_pw.value){
				alert("비밀번호를 입력해주세요.");
				memberEditForm.user_pw.focus();
				return false;
			}
			if(!inputForm.school_name.value){
				alert("학교명을 입력해주세요.");
				memberEditForm.school_name.focus();
				return false;
			}
			if(!inputForm.sen_email.value){
				alert("이메일을 입력해주세요.");
				memberEditForm.sen_email.focus();
				return false;
			}
		}
		
		
		//이메일형식 체크
		function emailValue(){
			var t = escape(this.memberCreateForm.sen_email.value);
			//sung2li@naver.com 일때와 sung2li@naver.co.kr일때의 형식 체크
			if (t.match(/^(\w+)@(\w+)[.](\w+)$/ig) == null && t.match(/^(\w+)@(\w+)[.](\w+)[.](\w+)$/ig) == null) {
				alert("이메일이 맞지 않습니다.");
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
		
	</script>
</head>


<h1 align="center">가상 서울시특별교육청 홈페이지(회원수정)</h1>

<body>

<form name="memberEditForm" action="/foodSen/memberEdit.do" method="post" onSubmit="return checkIt()">
	
	<table border="1" align="center" width="700" bordercolor="#E7E7E7">
	
		<!-- 세션이 없는 경우 -->
		<c:if test="${sessionScope.session_id == null}">
			<tr>	
				<td colspan="2" align="center">
					로그인 후 이용해 주세요!
				</td>
			</tr>
		</c:if>
		<!-- .//세션이 없는 경우 -->
		
	
		<!-- 세션이 있는 경우 -->
		<c:if test="${sessionScope.session_id != null}">
			<tr>
				<td width="200" height="8" align="right">아이디</td>
				<td width="500" height="8" align="left">
					${resultClass.user_id}
				</td>
			</tr>
			
			<tr>	
				<td width="200" height="8" align="right">비밀번호</td>
				<td width="500" height="8" align="left">
					<input type="password" name="user_pw" id="user_pw" size="21" maxlength="15" value="${resultClass.user_pw}"/>
					<font color="gray" size="2">비밀번호는 최대 15자리까지 입력가능합니다.</font>
				</td>
			</tr>
				
			<tr>
				<td width="200" height="8" align="right">이&nbsp;름</td>
				<td width="500" height="8" align="left">
					${resultClass.member_name}
				</td>
			</tr>
			
			<tr>	
				<td width="200" height="8" align="right">학교명</td>
				<td width="500" height="8" align="left">
					<input type="text" name="school_name" id="school_name"size="7" maxlength="5" value="${resultClass.school_name}"/>
					<font color="gray" size="2">최대 5자 입력</font>
				</td>
			</tr>
			
			<tr>	
				<td width="200" height="8" align="right">학교종류</td>
				<td width="500" height="8" align="left">
					
					<c:if test="${resultClass.school_type == 0}">
						<font color="red">관리자</font><input type="hidden" name="school_type" value="0" />
					</c:if>
					
					<c:if test="${resultClass.school_type == 01}">
						<select name = "school_type">
							<option value = "01" selected>초등학교</option>
							<option value = "02">중학교</option>
							<option value = "03">고등학교</option>
							<option value = "04">특수학교</option>
							<option value = "05">각종</option>
						</select>
					</c:if>
					
					<c:if test="${resultClass.school_type == 02}">
						<select name = "school_type">
							<option value = "01">초등학교</option>
							<option value = "02" selected>중학교</option>
							<option value = "03">고등학교</option>
							<option value = "04">특수학교</option>
							<option value = "05">각종</option>
						</select>
					</c:if>
					
					<c:if test="${resultClass.school_type == 03}">
						<select name = "school_type">
							<option value = "01">초등학교</option>
							<option value = "02">중학교</option>
							<option value = "03" selected>고등학교</option>
							<option value = "04">특수학교</option>
							<option value = "05">각종</option>
						</select>
					</c:if>
					
					<c:if test="${resultClass.school_type == 04}">
						<select name = "school_type">
							<option value = "01">초등학교</option>
							<option value = "02">중학교</option>
							<option value = "03">고등학교</option>
							<option value = "04" selected>특수학교</option>
							<option value = "05">각종</option>
						</select>
					</c:if>
					
					<c:if test="${resultClass.school_type == 05}">
						<select name = "school_type">
							<option value = "01">초등학교</option>
							<option value = "02">중학교</option>
							<option value = "03">고등학교</option>
							<option value = "04">특수학교</option>
							<option value = "05" selected>각종</option>
						</select>
					</c:if>
					
				</td>
			</tr>
			
			<tr>	
				<td width="200" height="8" align="right">직위</td>
				<td width="500" height="8" align="left">
				
					<c:if test="${resultClass.position == 0}">
						<font color="red">관리자</font><input type="hidden" name="position" value="0" />
					</c:if>
					
					<c:if test="${resultClass.position == 01}">
						<select name = "position">
							<option value = "01" selected>영양사</option>
							<option value = "02">조리원</option>
							<option value = "03">교직원</option>
						</select>
					</c:if>
					
					<c:if test="${resultClass.position == 02}">
						<select name = "position">
							<option value = "01">영양사</option>
							<option value = "02" selected>조리원</option>
							<option value = "03">교직원</option>
						</select>
					</c:if>
					
					<c:if test="${resultClass.position == 03}">
						<select name = "position">
							<option value = "01">영양사</option>
							<option value = "02">조리원</option>
							<option value = "03" selected>교직원</option>
						</select>
					</c:if>
					
				</td>
			</tr>
			
			<tr>	
				<td width="200" height="16" align="right">이메일</td>
				<td width="500" height="16" align="left">
					<input type="text" name="sen_email" id="sen_email" size="45" maxlength="20" value="${resultClass.sen_email}" onblur="emailValue();"/>
					<br/>
					<font color="gray" size="2">모든 주소를 기입해주세요. 최대20자</font>
				</td>
			</tr>
			
			<tr>	
				<td width="200" height="16" align="right">폰번호</td>
				<td width="500" height="16" align="left">
					<input type="text" name="phone" id="phone" size="45" maxlength="13" onkeydown="mphon(this);" onkeyup="mphon(this);" value="${resultClass.phone}" />
				</td>
			</tr>
			
			<tr>
				<td height="8"colspan="2" align="right">
					<input type="submit" name="submit" value="수정"/>
				</td>
			</tr>
		</c:if>
		<!-- .//세션이 있는 경우 -->
		
	</table>
	
</form>

</body>
</html>