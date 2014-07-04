<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>

<html>
<head>
	<title>가입하기</title>
	
	<SCRIPT type="text/javascript">
		//null 유효성검사
		function checkIt(){
			inputForm=eval("document.memberCreateForm");
		 
			if(!inputForm.user_id.value){
				alert("아이디를 입력해주세요.");
				memberCreateForm.user_id.focus();
				return false;
			}
			if(!inputForm.user_pw.value){
				alert("비밀번호를 입력해주세요.");
				memberCreateForm.user_pw.focus();
				return false;
			}
			if(!inputForm.member_name.value){
				alert("이름를 입력해주세요.");
				memberCreateForm.member_name.focus();
				return false;
			}
			if(!inputForm.school_name.value){
				alert("학교명을 입력해주세요.");
				memberCreateForm.school_name.focus();
				return false;
			}
			if(!inputForm.sen_email.value){
				alert("이메일을 입력해주세요.");
				memberCreateForm.sen_email.focus();
				return false;
			}
		}
		
		//비밀번호, 재입력된 비밀번호화 일치하지 않을 경우
		function checkPw(){
			inputForm=eval("document.memberCreateForm");
			
			if(inputForm.user_pw.value != inputForm.user_pw_re.value){
				alert("비밀번호가 일치하지 않습니다.");
				return false;
			}
		}
		
	</script>
</head>


<h1 align="center">회원가입</h1>

<body>

<form name="memberCreateForm" action="/foodSen/memberCreate.do" method="post" onSubmit="return checkIt()">
	
	<table border="1" align="center" width="700" bordercolor="#E7E7E7">
		<tr>
			<td width="200" height="8" align="right">아이디</td>
			<td width="500" height="8" align="left">
				<input type="text" name="user_id" id="user_id" size="12" maxlength="10" />
				<font color="gray" size="2">아이디는 영문, 숫자 최대 12자리까지 생성할 수 있습니다.</font>
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
				<font color="gray" size="2">이름은 쵀대 한글4자까지 입력가능합니다.</font>
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
					<option value = "1">초등학교</option>
					<option value = "2">중학교</option>
					<option value = "3">고등학교</option>
				</select>
			</td>
		</tr>
		
		<tr>	
			<td width="200" height="8" align="right">직위</td>
			<td width="500" height="8" align="left">
				<select name = "position">
					<option value = "1">영양사</option>
					<option value = "2">조리원</option>
				</select>
			</td>
		</tr>
		
		<tr>	
			<td width="200" height="16" align="right">이메일</td>
			<td width="500" height="16" align="left">
				<input type="text" name="sen_email" id="sen_email" size="45" maxlength="20" />
				<br/>
				<font color="gray" size="2">모든 주소를 기입해주세요. 최대20자</font>
			</td>
		</tr>
		
		<tr>
			<td height="8"colspan="2" align="center">
				<input type="submit" name="submit" value="가입"/>
				<input type="button" name="cancel" value="취소" onClick="javascript:location.href='/foodSen/main.do'"/>
			</td>
		</tr>
	</table>
	
</form>

</body>
</html>