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
	
		//��������
		function toPass(){
			var input = document.loginForm.user_id.value;//����ڰ� �Է��� ��
			document.loginForm.user_id.value = input.replace(/^\s+|\s+$/g,""); //�յ� ������ ������
		}
		
		//�빮�ڸ� �ҹ��ڷ� ����
		function toPass1(){ 
			var input = document.loginForm.user_id.value;//����ڰ� �Է��� ��
			document.loginForm.user_id.value = input.toLowerCase(); //�빮�ڸ� �ҹ��ڷ� ����
		}
		
		//Ư�����ڰ� ���� ��� ���
		function toPass2(){
			if((event.keyCode >=32&&event.keyCode <=47)||(event.keyCode>=58&&event.keyCode<=64)||(event.keyCode>=91&&event.keyCode<=96)||(event.keyCode>=123&&event.keyCode<=126) ) {
				alert("���̵� ���� �� Ư�����ڴ� �Է��� �� �����ϴ�.");
			}
		}
		
		

		//null ��ȿ���˻�
		function checkIt() {
			inputForm = eval("document.loginForm");

			if (!inputForm.user_id.value) {
				alert("���̵� �Է����ּ���.");
				loginForm.user_id.focus();
				return false;
			}
			if (!inputForm.user_pw.value) {
				alert("��й�ȣ�� �Է����ּ���.");
				loginForm.user_pw.focus();
				return false;
			}
		}

		//���ڵ带 ã�� ���Ѱ��
		function notFound() {
			alert("���̵� �Ǵ� ��й�ȣ�� Ȯ�����ּ���");
			loginForm.user_pw.focus();
		}
		
		
	</script>
</head>


<div id="container">
	<div id="contents">
	
		<p><img src="./images/sub/etc/sub_vimg_01.jpg" alt="�ǰ��� �޽� �ູ�� �б�" /></p>
		
		<!-- �����޴� -->
		<c:if test="${sessionScope.session_id == null}">
			<jsp:include page="/view/include/member/loginLnb.jsp"/>
		</c:if>
		
		<c:if test="${sessionScope.session_id != null}">
			<jsp:include page="/view/include/member/logoutLnb.jsp"/>
		</c:if>
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
				                	<dt style="padding-left: 40px">
				                		�����ID <input type="text" class="inp" name="user_id" id="user_id" style="width:180px;" maxlength="20" value="${user_id}" onkeypress="toPass(); toPass1(); toPass2();" onblur="toPass(); toPass1();" /> <br/><br/>
				                		��й�ȣ <input type="password" class="inp" name="user_pw" id="user_pw" style="width:180px;" maxlength="20" />
				                		&nbsp;&nbsp;
				                		<input type="submit" class="btn_submit_login" name="login" value="login" />
			                    	</dt>
			                		
				                </dl>
				                
				                
				                <ul class="login_text">
				                    <li>����Ư���ñ���û(�б�����) �Ҽ� �������� ����ñ���û Ȩ������ ���Խ� ����� ���̵�� 
										NEIS���� ����ϴ� �������� �̿��Ͽ� �α����Ͻñ� �ٶ��ϴ�.<br />
										<span class="f_eb7c10" style="vertical-align: bottom;">�� �α����� ���� ���� ���, ����ñ���û Ȩ���������� ���� <a target="new" href="http://localhost:8000/foodSen/memberCreateFrom.do"><img src="./images/sub/etc/login_btn_01.gif" alt="����ڵ��" height="20px"/></a>�� �Ͻñ� �ٶ��ϴ�.</span>
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
				
					<fieldset>
			        	<legend>�α׾ƿ�</legend>
			        	
			        	<form name="loginedForm" action="/foodSen/logoutPro.do" method="post" onSubmit="return checkIt()">
				        	
				        	<div class="login">            
				            	<h4><img src="./images/sub/etc/login_img_01.gif" alt="���̵� �Է��ϼ���" /></h4>
				                <dl>
				                	<dt style="padding-left: 90px">
				                		<br/>
				                		<font color="red"><b>${sessionScope.session_id}</b></font> ��
				                		<br/>������ ȯ�� �մϴ�.
				                	</dt>
				                    <dd style="padding-left: 10px">
				                    	<br/>
				                    	<input type="submit" class="btn_submit_logout" name="logout" value="logout"/>
			                    	</dd>
				                </dl>
				                
				                <ul class="login_text">
				                </ul>
				                
				            </div>
				            
			            </form>
			        </fieldset>
					
				</c:if>
				<!-- .//�α��εȻ��� -->
				
				
				
			</body>
			
			
		</div>
		<!-- .//���� ������ -->
		
		
	</div>
</div>