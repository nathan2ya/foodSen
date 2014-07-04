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
	</script>
</head>


<h1 align="center">회원수정</h1>

<body>

<form name="memberEditForm" action="/foodSen/memberEdit.do" method="post" onSubmit="return checkIt()">
	
	<table border="1" align="center" width="700" bordercolor="#E7E7E7">
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
				
				<c:if test="${resultClass.school_type == 1}">
					<select name = "school_type">
						<option value = "1" selected>초등학교</option>
						<option value = "2">중학교</option>
						<option value = "3">고등학교</option>
					</select>
				</c:if>
				
				<c:if test="${resultClass.school_type == 2}">
					<select name = "school_type">
						<option value = "1">초등학교</option>
						<option value = "2" selected>중학교</option>
						<option value = "3">고등학교</option>
					</select>
				</c:if>
				
				<c:if test="${resultClass.school_type == 3}">
					<select name = "school_type">
						<option value = "1">초등학교</option>
						<option value = "2">중학교</option>
						<option value = "3" selected>고등학교</option>
					</select>
				</c:if>
				
			</td>
		</tr>
		
		<tr>	
			<td width="200" height="8" align="right">직위</td>
			<td width="500" height="8" align="left">
				
				<c:if test="${resultClass.position == 1}">
					<select name = "position">
						<option value = "1" selected>영양사</option>
						<option value = "2">조리원</option>
					</select>
				</c:if>
				
				<c:if test="${resultClass.position == 2}">
					<select name = "position">
						<option value = "1">영양사</option>
						<option value = "2" selected>조리원</option>
					</select>
				</c:if>
				
			</td>
		</tr>
		
		<tr>	
			<td width="200" height="16" align="right">이메일</td>
			<td width="500" height="16" align="left">
				<input type="text" name="sen_email" id="sen_email" size="45" maxlength="20" value="${resultClass.sen_email}"/>
				<br/>
				<font color="gray" size="2">모든 주소를 기입해주세요. 최대20자</font>
			</td>
		</tr>
		
		<tr>
			<td height="8"colspan="2" align="center">
				<input type="submit" name="submit" value="수정"/>
				<input type="button" name="cancel" value="취소" onClick="javascript:location.href='/foodSen/main.do'"/>
			</td>
		</tr>
	</table>
	
</form>

</body>
</html>