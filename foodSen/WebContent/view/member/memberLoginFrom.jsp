<%@ page language="java" contentType="text/html; charset=euc-kr"
	pageEncoding="euc-kr"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>


<jsp:include page="/view/include/top.jsp"/>
<link href="/css/base.css" rel="stylesheet" type="text/css" />
<link href="/css/common.css" rel="stylesheet" type="text/css" />


<html>
<head>
	<title>�α���</title>
	
	<SCRIPT type="text/javascript">
		//null ��ȿ���˻�
		function checkIt(){
			inputForm=eval("document.loginForm");
		 
			if(!inputForm.user_id.value){
				alert("���̵� �Է����ּ���.");
				loginForm.user_id.focus();
				return false;
			}
			if(!inputForm.user_pw.value){
				alert("��й�ȣ�� �Է����ּ���.");
				loginForm.user_pw.focus();
				return false;
			}
		}
		
		//���ڵ带 ã�� ���Ѱ��
		function notFound(){
			alert("���̵� �Ǵ� ��й�ȣ�� Ȯ�����ּ���");
			loginForm.user_pw.focus();
		}
		
	</script>
</head>


<div id="container">
	<div id="contents">
	
		<p><img src="./images/sub/etc/sub_vimg_01.jpg" alt="�ǰ��� �޽� �ູ�� �б�" /></p>
		
		<!-- �����޴� -->
		<jsp:include page="/view/include/menu7/faqLnb.jsp"/>
		<!-- .//�����޴� -->
		
	
	
		<!-- ���� ������ -->
		<div class="right_box">
		
		
			<!-- ���ڵ带 ã�� ���� ��� ��� -->
			<c:if test="${notFound == 1}">
				<body onload="return notFound();">
			</c:if>
			
			<!-- ���� ���� -->
			<c:if test="${notFound == 0}">
				<body>
			</c:if>
				
				
				<!-- ������ ��� ��� ���� -->
				<h3><img src="./images/sub/etc/title_01.gif" alt="�α���" /></h3>
				<p class="history">
					<img src="./images/sub/history_home.gif" alt="home" /> ��Ÿ <img src="./images/sub/history_arrow.gif" alt="����" /> <strong>�α���</strong>
				</p>
       			<p class="pt30"></p>
				<!-- .//������ ��� ��� ���� -->
				
				
				
				<!-- �� �α��λ��� -->
				<c:if test="${sessionScope.session_id == null}">
				
					<fieldset>
			        	<legend>�α���</legend>
			        	<form name="loginForm" action="/foodSen/loginPro.do" method="post" onSubmit="return checkIt()">
			        	
				        	<div class="login">            
				            	<h4><img src="./images/sub/etc/login_img_01.gif" alt="���̵� �Է��ϼ���" /></h4>
				                <dl>
				                	<dt>
				                		&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
				                		�����ID <input type="text" class="inp" name="user_id" id="user_id" style="width:180px;" maxlength="20" value="${user_id}"/> <br/><br/>
				                		&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
				                		��й�ȣ <input type="password" class="inp" name="user_pw" id="user_pw" style="width:180px;" maxlength="20" />
				                		&nbsp;&nbsp;
				                		<input type="submit" class="btn_submit_login" name="login" value="login" />
			                    	</dt>
			                		
				                </dl>
				                
				                
				                <ul class="login_text">
				                    <li>����Ư���ñ���û(�б�����) �Ҽ� �������� ����ñ���û Ȩ������ ���Խ� ����� ���̵�� 
										NEIS���� ����ϴ� �������� �̿��Ͽ� �α����Ͻñ� �ٶ��ϴ�.<br />
										<span class="f_eb7c10" style="vertical-align: bottom;">�� �α����� ���� ���� ���, ����ñ���û Ȩ���������� ���� <a target="_blank" href="http://www.sen.go.kr/web/services/member/agreement.action"><img src="./images/sub/etc/login_btn_01.gif" alt="����ڵ��" height="20px"/></a>�� �Ͻñ� �ٶ��ϴ�.</span>
									</li>
				                	<li>�л�, �кθ�, �ù��� ������ ȸ������ ���� ����Ȯ��(���������� �Ǵ� �Ǹ�����)������ <br />���� �̿��� �����մϴ�. (����� ��� �Ұ�)</li>
				                </ul>
				            </div>
				            
			            </form>
			        </fieldset>
					
				</c:if>
				<!-- .//�� �α��λ��� -->
				
				
				
				<!-- �α��εȻ��� -->
				<c:if test="${sessionScope.session_id != null}">
					<form name="loginedForm" action="/foodSen/logoutPro.do" method="post" onSubmit="return checkIt()">
					
						<table border="1" align="center" width="800" bordercolor="#E7E7E7">
							<tr>
								<td colspan="2">
									${sessionScope.session_id}�� ȯ���մϴ�.
								</td>
							</tr>
							
							<tr>
								<td colspan="2">
									<input type="submit" name="submit" value="�α׾ƿ�"/>
								</td>
							</tr>
						</table>
						
					</form>
				</c:if>
				<!-- .//�α��εȻ��� -->
				
				
				
			</body>
			
			
		</div>
		<!-- .//���� ������ -->
		
		
	</div>
</div>