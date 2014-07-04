<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>

<html>
<head>
	<title>수정하기</title>
	
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
	</script>
</head>


<h1 align="center">회원수정</h1>

<body>

<form name="memberEditForm" action="/foodSen/memberEdit.do" method="post" onSubmit="return checkIt()">
	
	<table border="1" align="center" width="800" bordercolor="#E7E7E7">
		<tr>
			<td>아이디</td>
			<td>${resultClass.user_id}</td>
		</tr>
		
		<tr>	
			<td>비밀번호</td>
			<td><input type="password" name="user_pw" id="user_pw" size="20" maxlength="20" value="${resultClass.user_pw}"/></td>
		</tr>
			
		<tr>
			<td>이&nbsp;름</td>
			<td>${resultClass.member_name}</td>
		</tr>
		
		<tr>	
			<td>학교명</td>
			<td><input type="text" name="school_name" id="school_name" size="20" maxlength="20" value="${resultClass.school_name}"/></td>
		</tr>
		
		<tr>	
			<td>학교종류</td>
			<td>
				<select name = "school_type">
					<option value = "1">초등학교</option>
					<option value = "2">중학교</option>
					<option value = "3">고등학교</option>
				</select>
			</td>
		</tr>
		
		<tr>	
			<td>직위</td>
			<td>
				<select name = "position">
					<option value = "1">영양사</option>
					<option value = "2">조리원</option>
				</select>
			</td>
		</tr>
		
		<tr>	
			<td>이메일</td>
			<td><input type="text" name="sen_email" id="sen_email" size="30" maxlength="30" value="${resultClass.sen_email}"/></td>
		</tr>
		
		<tr>
			<td colspan="2">
				<input type="submit" name="submit" value="수정"/>
				<input type="button" name="cancel" value="취소" onClick="javascript:location.href='/foodSen/main.do'"/>
			</td>
		</tr>
	</table>
	
</form>

</body>
</html>