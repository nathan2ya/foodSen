<%@ page language="java" contentType="text/html; charset=EUC-KR"
    pageEncoding="EUC-KR"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt_rt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=euc-kr" />
<meta http-equiv="X-UA-Compatible" content="IE=edge" />
<title>�����б��޽�����</title>
<link href="./css/base.css" rel="stylesheet" type="text/css" />
<link href="./css/common.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="http://code.jquery.com/jquery-1.10.2.min.js"></script>
<script type="text/javascript">
<!--

	var flag1=true;
	var flag2=true;
	
	$(document).ready(function(){
		$(".mainMenu").each(function(index, item){
			$(item).click(function(){
				flag1=false;
			});
		});
		
		$(".subMenu").each(function(index, item){
			$(item).click(function(){
				flag1=true;
				flag2=false;
			});
		});
	});

   function getElementsByClass(searchClass, node, tag) {
     var classElements = new Array();
     if ( node == null ) node = document;
     if ( tag == null ) tag = '*';
     var els = node.getElementsByTagName(tag);
     var elsLen = els.length;
     var pattern = new RegExp("(^|\\s)"+searchClass+"(\\s|$)");
     for (i = 0, j = 0; i < elsLen; i++) {
      if ( pattern.test(els[i].className) ) {
        classElements[j] = els[i];
        j++;
      }
    }
    return classElements;
  }

   function menuHidden(menu, sub) {
    //menu.src = menu.src.replace("On", "Off");
    if(menu == "undefined" || menu == null || menu == ""){
	    $(sub).css('display','none');
    }else{
    	$(menu).attr('src', $(menu).attr('src').replace('On','Off'));
    	$(sub).css('display','none');
    }
  }

  function setEvtGnb() {
    var mainMenu = getElementsByClass("mainMenu");
    var prevMenu1, prevSub1, isHid1, prevMenu2, prevSub2,isHid2;
	
	var subMenu = getElementsByClass("subMenu");
	
    for (var i=0; i<mainMenu.length; i++) {
      (function (pos){
        mainMenu[pos].getElementsByTagName("img")[0].onmouseover = function(){
          if(prevMenu1) menuHidden(prevMenu1, prevSub1);
          prevMenu1 = this;
          this.src = this.src.replace("Off", "On");
          prevSub1 = document.getElementById("sub"+("0"+(pos+1)).match(/..$/));
          prevSub1.style.display = "block";
        };
    
        mainMenu[pos].onmouseout = function(e){
          var bool, e= e || event;
          (function (obj, tobj) {
            var childs = obj.childNodes;
            for (var x=0; x<childs.length; x++) {
              if(childs[x] == tobj) bool = true;
              else arguments.callee(childs[x], tobj);
            }
          })(this, document.elementFromPoint(e.clientX, e.clientY));
          if(flag1){
	          if(bool) return false;
	          menuHidden(prevMenu1, prevSub1);
          }
        };
      })(i);
    }
	
	for (var j=0; j<subMenu.length; j++) {
      (function (pos){
        subMenu[pos].getElementsByTagName("img")[0].onmouseover = function(){
          prevMenu2 = this;
          this.src = this.src.replace("Off", "On");
          prevSub2 = document.getElementById("sub"+("0"+(pos+1)).match(/..$/));
        };
    
        subMenu[pos].onmouseout = function(e){
          var bool, e= e || event;
          (function (obj, tobj) {
            var childs = obj.childNodes;
            for (var x=0; x<childs.length; x++) {
              if(childs[x] == tobj) bool = true;
              else arguments.callee(childs[x], tobj);
            }
          })(this, document.elementFromPoint(e.clientX, e.clientY));
          if(flag2){
	          if(bool) return false;
	          menuHidden(prevMenu2, prevSub2);
          }
        };
      })(j);
    }
  }
 
  window.onload = function() {
    setEvtGnb();
  }
  
  function openApprove(){
	//���������� �˾�â�� �켱 �ϳ� ����.
	window.open('approvePop1.do', 'popup_post1', 'width=408, height=290, resizable= yes');
  }
//-->
</script>
</head>
<body>
<div id="wrap"> 
  <!--skip S-->
  <ul id="skipnavi">
    <li><a href="#gnb">�ָ޴� �ٷΰ���</a></li>
    <li><a href="#contents">���γ��� �ٷΰ���</a></li>
    <li><a href="#footer">�ϴ� �ٷΰ���</a></li>
  </ul>
  <!--skip E--> 
 </div>
  
 <!-- header-->
 <div id="header">
    <h1><a href="/foodSen/main.do"><img src="./images/header/common/logo.gif" alt="�����б��޽�����" /></a></h1>
   
   <div class="topmenu">
		
		<c:choose>
		
			<c:when test="${sessionScope.session_id eq null}">
				<ul>
				<li class="bn"><a href="/foodSen/main.do">HOME</a></li>
				<li><a href="#">SITEMAP</a></li>
				<li class="bn"> <a href="/foodSen/login.do"><img src="./images/header/common/btn_login.gif" alt="�α���" /></a></li>
				</ul>
			</c:when>
			
			<c:when test="${sessionScope.session_id ne null}">
				<ul>
					<li class="bn f12">
						<span class="orange">${sessionScope.session_resultClass.member_name}</span>�� ȯ���մϴ�.
					</li>
					
					<%-- 
					<c:if test="${loginUser.position ne '01' && loginUser.position ne '02' && loginUser.position ne '99' }">
						<li class="bn"><a href="javascript:openApprove()"><img src="images/header/common/btn_join.gif" alt="ȸ�����" /></a></li>
					</c:if>
				   --%>
					  
					<li class="bn"><a href="/foodSen/main.do">HOME</a></li>
					<li><a href="#">SITEMAP</a></li>
					<li class="bn"> <a href="/foodSen/logoutPro.do"><img src="./images/header/common/btn_logout.gif" alt="�α׾ƿ�" /></a></li>
				</ul>
			</c:when>
			
		</c:choose>
		
	</div>
   
    
    
    <div id="gnb">
      <h2>�ָ޴�</h2>
      <ul class="MM">
        <li class="mainMenu first"><a href="#"><img src="./images/header/common/mm_infoOff.gif" id="sel1" alt="�����б��޽ļҰ�" /></a>
         <div class="subMenu" id="sub01" style="display:none;">
            <div class="boxSR">
              <ul class="boxSM">
                <li class="left_bg"></li>
                <li class="subMenu"><a href="#"><img src="./images/header/common/sm_info01Off.gif" alt="�λ縻" /></a></li>
                <li class="subMenu"><a href="#"><img src="./images/header/common/sm_info02Off.gif" alt="�б��޽ı⺻����" /></a></li>
                <li class="subMenu"><a href="#"><img src="./images/header/common/sm_info03Off.gif" alt="�б��޽���Ȳ" /></a></li>
                <li class="last subMenu"><a href="#"><img src="./images/header/common/sm_info04Off.gif" alt="�б��޽� ���μ�" /></a></li>
                <li class="right_bg"></li>
              </ul>
            </div>
          </div>
        </li>
        <li class="mainMenu"><a href="/foodSen/inspectionResultList.do"><img src="./images/header/common/mm_safetyOff.gif" alt="�б��޽���������" /></a>
          <div class="subMenu" id="sub02" style="display:none;">
            <div class="boxSR">
              <ul class="boxSM">
                <li class="left_bg"></li>
                <li class="subMenu"><a href="#"><img src="./images/header/common/sm_safety01Off.gif" alt="�б��޽� ��������" /></a></li>
                <li class="subMenu"><a href="#"><img src="./images/header/common/sm_safety02Off.gif" alt="���ߵ� ��ó���" /></a></li>
                <li class="subMenu"><a href="#"><img src="./images/header/common/sm_safety03Off.gif" alt="���������" /></a></li>
                <li class="subMenu"><a href="#"><img src="./images/header/common/sm_safety04Off.gif" alt="������� ��ó���" /></a></li>
                <li class="last subMenu"><a href="/foodSen/inspectionResultList.do"><img src="./images/header/common/sm_safety05Off.gif" alt="����.������ �˻���" /></a></li>
                <li class="right_bg"></li>
              </ul>
            </div>
          </div>
        </li>
        <li class="mainMenu"><a href="/foodSen/improvementCaseList.do"><img src="./images/header/common/mm_factoryOff.gif" alt="�б��޽Ľü�����" /></a>
          <div class="subMenu" id="sub03" style="display:none;">
            <div class="boxSR">
              <ul class="boxSM">
                 <li class="left_bg"></li>
                <li class="subMenu"><a href="#"><img src="./images/header/common/sm_factory01Off.gif" alt="�޽�ȯ�氳�����" /></a></li>
                <li class="subMenu"><a href="/foodSen/improvementCaseList.do"><img src="./images/header/common/sm_factory02Off.gif" alt="�޽Ľü��������" /></a></li>
                <li class="subMenu"><a href="#"><img src="./images/header/common/sm_factory03Off.gif" alt="�޽ıⱸ������ȯ" /></a></li>
                <li class="last subMenu"><a href="#"><img src="./images/header/common/sm_factory04Off.gif" alt="�����ý�û�ȳ�" /></a></li>
                <li class="right_bg"></li>
              </ul>
            </div>
          </div>
        </li>
        <li class="mainMenu"><a href="#"><img src="./images/header/common/mm_foodOff.gif" alt="�б��޽Ľ����" /></a>
          <div class="subMenu" id="sub04" style="display:none;">
            <div class="boxSR">
              <ul class="boxSM">
                 <li class="left_bg"></li>
                <li class="subMenu"><a href="#"><img src="./images/header/common/sm_food01Off.gif" alt="����� ���š�����" /></a></li>
                <li class="subMenu"><a href="#"><img src="./images/header/common/sm_food02Off.gif" alt="����� ��������" /></a></li>
                <li class="last subMenu"><a href="#"><img src="./images/header/common/sm_food03Off.gif" alt="������ ��ǰ ��ü" /></a></li>
                <li class="right_bg"></li>
              </ul>
            </div>
          </div>
        </li>
        <li class="mainMenu"><a href="#"><img src="./images/header/common/mm_eduOff.gif" alt="����,����" /></a>
          <div class="subMenu" id="sub05" style="display:none;">
            <div class="boxSR">
              <ul class="boxSM">
                <li class="left_bg"></li>
                <li class="subMenu"><a href="#"><img src="./images/header/common/sm_edu01Off.gif" alt="���硤�Ļ�Ȱ����" /></a></li>
                <li class="subMenu"><a href="#"><img src="./images/header/common/sm_edu02Off.gif" alt="��õ�Ĵ�" /></a></li>
                <li class="subMenu"><a href="#"><img src="./images/header/common/sm_edu03Off.gif" alt="��õ������" /></a></li>
                <li class="last subMenu"><a href="#"><img src="./images/header/common/sm_edu04Off.gif" alt="�б��޽�Ư��Ȱ��" /></a></li>
                <li class="right_bg"></li>
              </ul>
            </div>
          </div>
        </li>
        <li class="mainMenu"><a href="/foodSen/recruitList.do"><img src="./images/header/common/mm_partOff.gif" alt="��������" /></a>
          <div class="subMenu" id="sub06" style="display:none;">
            <div class="boxSR">
              <ul class="boxSM">
                <li class="left_bg"></li>
                <li class="subMenu"><a href="/foodSen/recruitList.do"><img src="./images/header/common/sm_part01Off.gif" alt="�б��޽��η�Ǯ" /></a></li>
                <li class="subMenu"><a href="#"><img src="./images/header/common/sm_part02Off.gif" alt="����(��)���̾߱�" /></a></li>
                <li class="subMenu"><a href="#"><img src="./images/header/common/sm_part03Off.gif" alt="����(��)���̾߱�" /></a></li>
                <li class="subMenu"><a href="#"><img src="./images/header/common/sm_part04Off.gif" alt="�����Խ���" /></a></li>
                <li class="last subMenu"><a href="http://food.sen.go.kr/researchList.do"><img src="./images/header/common/sm_part05Off.gif" alt="��������" /></a></li>
                <li class="right_bg"></li>
              </ul>
            </div>
          </div>
        </li>
        <li class="mainMenu last"><a href="/foodSen/TrainingEvent.do"><img src="./images/header/common/mm_noticeOff.gif" alt="�˸�����" /></a>
          <div class="subMenu" id="sub07" style="display:none;">
            <div class="boxSR">
              <ul class="boxSM">
                <li class="left_bg"></li>
                <li class="subMenu"><a href="#"><img src="./images/header/common/sm_notice01Off.gif" alt="�޽ļҽ�" /></a></li>
                <li class="subMenu"><a href="/foodSen/TrainingEvent.do"><img src="./images/header/common/sm_notice02Off.gif" alt="���������" /></a></li>
                <li class="subMenu"><a href="#"><img src="./images/header/common/sm_notice03Off.gif" alt="�����ڷ��" /></a></li>
                <li class="subMenu"><a href="#"><img src="./images/header/common/sm_notice04Off.gif" alt="���ù���" /></a></li>
                <li class="last subMenu"><a href="
                							<c:if test="${loginUser.admin_yn eq null || loginUser.admin_yn eq 'N' }"> faqList.do </c:if>
                							<c:if test="${loginUser.admin_yn eq 'Y' }"> faqSBMList.do </c:if>
                						"><img src="./images/header/common/sm_notice05Off.gif" alt="FAQ" /></a></li>
                <li class="right_bg"></li>
              </ul>
            </div>
          </div>
        </li>
      </ul>
    </div>
  </div>
  <!-- //header--> 