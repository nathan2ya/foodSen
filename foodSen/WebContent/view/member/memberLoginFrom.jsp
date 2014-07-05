<%@ page language="java" contentType="text/html; charset=euc-kr"
	pageEncoding="euc-kr"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>


<jsp:include page="/view/include/top.jsp"/>
<link href="/css/base.css" rel="stylesheet" type="text/css" />
<link href="/css/common.css" rel="stylesheet" type="text/css" />


<html>
<head>
	<title>로그인</title>
	
	<SCRIPT type="text/javascript">
		//null 유효성검사
		function checkIt(){
			inputForm=eval("document.loginForm");
		 
			if(!inputForm.user_id.value){
				alert("아이디를 입력해주세요.");
				loginForm.user_id.focus();
				return false;
			}
			if(!inputForm.user_pw.value){
				alert("비밀번호를 입력해주세요.");
				loginForm.user_pw.focus();
				return false;
			}
		}
		
		//레코드를 찾지 못한경우
		function notFound(){
			alert("아이디 또는 비밀번호를 확인해주세요");
			loginForm.user_pw.focus();
		}
		
	</script>
</head>


<div id="container">
	<div id="contents">
	
		<p><img src="./images/sub/etc/sub_vimg_01.jpg" alt="건강한 급식 행복한 학교" /></p>
		
		<!-- 좌측메뉴 -->
		<jsp:include page="/view/include/menu7/faqLnb.jsp"/>
		<!-- .//좌측메뉴 -->
		
	
	
		<!-- 우측 컨텐츠 -->
		<div class="right_box">
		
		
			<!-- 레코드를 찾지 못한 경우 경고 -->
			<c:if test="${notFound == 1}">
				<body onload="return notFound();">
			</c:if>
			
			<!-- 정상 진입 -->
			<c:if test="${notFound == 0}">
				<body>
			</c:if>
				
				
				<!-- 오른쪽 상단 경로 정보 -->
				<h3><img src="./images/sub/etc/title_01.gif" alt="로그인" /></h3>
				<p class="history">
					<img src="./images/sub/history_home.gif" alt="home" /> 기타 <img src="./images/sub/history_arrow.gif" alt="다음" /> <strong>로그인</strong>
				</p>
       			<p class="pt30"></p>
				<!-- .//오른쪽 상단 경로 정보 -->
				
				
				
				<!-- 비 로그인상태 -->
				<c:if test="${sessionScope.session_id == null}">
				
					<fieldset>
			        	<legend>로그인</legend>
			        	<form name="loginForm" action="/foodSen/loginPro.do" method="post" onSubmit="return checkIt()">
			        	
				        	<div class="login">            
				            	<h4><img src="./images/sub/etc/login_img_01.gif" alt="아이디를 입력하세요" /></h4>
				                <dl>
				                	<dt>
				                		&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
				                		사용자ID <input type="text" class="inp" name="user_id" id="user_id" style="width:180px;" maxlength="20" value="${user_id}"/> <br/><br/>
				                		&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
				                		비밀번호 <input type="password" class="inp" name="user_pw" id="user_pw" style="width:180px;" maxlength="20" />
				                		&nbsp;&nbsp;
				                		<input type="submit" class="btn_submit_login" name="login" value="login" />
			                    	</dt>
			                		
				                </dl>
				                
				                
				                <ul class="login_text">
				                    <li>서울특별시교육청(학교포함) 소속 교직원은 서울시교육청 홈페이지 가입시 등록한 아이디와 
										NEIS에서 사용하는 인증서를 이용하여 로그인하시기 바랍니다.<br />
										<span class="f_eb7c10" style="vertical-align: bottom;">※ 로그인이 되지 않을 경우, 서울시교육청 홈페이지에서 먼저 <a target="_blank" href="http://www.sen.go.kr/web/services/member/agreement.action"><img src="./images/sub/etc/login_btn_01.gif" alt="사용자등록" height="20px"/></a>을 하시기 바랍니다.</span>
									</li>
				                	<li>학생, 학부모, 시민은 별도의 회원가입 없이 본인확인(아이핀인증 또는 실명인증)만으로 <br />서비스 이용이 가능합니다. (사용자 등록 불가)</li>
				                </ul>
				            </div>
				            
			            </form>
			        </fieldset>
					
				</c:if>
				<!-- .//비 로그인상태 -->
				
				
				
				<!-- 로그인된상태 -->
				<c:if test="${sessionScope.session_id != null}">
					<form name="loginedForm" action="/foodSen/logoutPro.do" method="post" onSubmit="return checkIt()">
					
						<table border="1" align="center" width="800" bordercolor="#E7E7E7">
							<tr>
								<td colspan="2">
									${sessionScope.session_id}님 환영합니다.
								</td>
							</tr>
							
							<tr>
								<td colspan="2">
									<input type="submit" name="submit" value="로그아웃"/>
								</td>
							</tr>
						</table>
						
					</form>
				</c:if>
				<!-- .//로그인된상태 -->
				
				
				
			</body>
			
			
		</div>
		<!-- .//우측 컨텐츠 -->
		
		
	</div>
</div>