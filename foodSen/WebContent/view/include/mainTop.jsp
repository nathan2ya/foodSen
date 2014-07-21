<%@ page language="java" contentType="text/html; charset=EUC-KR"
    pageEncoding="EUC-KR"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt_rt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=euc-kr" />
<meta http-equiv="X-UA-Compatible" content="IE=edge" />
<title>서울학교급식포털</title>
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
	//빈페이지로 팝업창을 우선 하나 띄운다.
	window.open('approvePop1.do', 'popup_post1', 'width=408, height=290, resizable= yes');
  }
//-->
</script>
</head>
<body>
<div id="wrap"> 
  <!--skip S-->
  <ul id="skipnavi">
    <li><a href="#gnb">주메뉴 바로가기</a></li>
    <li><a href="#contents">메인내용 바로가기</a></li>
    <li><a href="#footer">하단 바로가기</a></li>
  </ul>
  <!--skip E--> 
 </div>
  
 <!-- header-->
 <div id="header">
    <h1><a href="/foodSen/main.do"><img src="./images/header/common/logo.gif" alt="서울학교급식포털" /></a></h1>
   
   <div class="topmenu">
		
		<c:choose>
		
			<c:when test="${sessionScope.session_id eq null}">
				<ul>
				<li class="bn"><a href="/foodSen/main.do">HOME</a></li>
				<li><a href="#">SITEMAP</a></li>
				<li class="bn"> <a href="/foodSen/login.do"><img src="./images/header/common/btn_login.gif" alt="로그인" /></a></li>
				</ul>
			</c:when>
			
			<c:when test="${sessionScope.session_id ne null}">
				<ul>
					<li class="bn f12">
						<span class="orange">${sessionScope.session_resultClass.member_name}</span>님 환영합니다.
					</li>
					
					<%-- 
					<c:if test="${loginUser.position ne '01' && loginUser.position ne '02' && loginUser.position ne '99' }">
						<li class="bn"><a href="javascript:openApprove()"><img src="images/header/common/btn_join.gif" alt="회원등록" /></a></li>
					</c:if>
				   --%>
					  
					<li class="bn"><a href="/foodSen/main.do">HOME</a></li>
					<li><a href="#">SITEMAP</a></li>
					<li class="bn"> <a href="/foodSen/logoutPro.do"><img src="./images/header/common/btn_logout.gif" alt="로그아웃" /></a></li>
				</ul>
			</c:when>
			
		</c:choose>
		
	</div>
   
    
    
    <div id="gnb">
      <h2>주메뉴</h2>
      <ul class="MM">
        <li class="mainMenu first"><a href="#"><img src="./images/header/common/mm_infoOff.gif" id="sel1" alt="서울학교급식소개" /></a>
         <div class="subMenu" id="sub01" style="display:none;">
            <div class="boxSR">
              <ul class="boxSM">
                <li class="left_bg"></li>
                <li class="subMenu"><a href="#"><img src="./images/header/common/sm_info01Off.gif" alt="인사말" /></a></li>
                <li class="subMenu"><a href="#"><img src="./images/header/common/sm_info02Off.gif" alt="학교급식기본방향" /></a></li>
                <li class="subMenu"><a href="#"><img src="./images/header/common/sm_info03Off.gif" alt="학교급식현황" /></a></li>
                <li class="last subMenu"><a href="#"><img src="./images/header/common/sm_info04Off.gif" alt="학교급식 담당부서" /></a></li>
                <li class="right_bg"></li>
              </ul>
            </div>
          </div>
        </li>
        <li class="mainMenu"><a href="/foodSen/inspectionResultList.do"><img src="./images/header/common/mm_safetyOff.gif" alt="학교급식위생안전" /></a>
          <div class="subMenu" id="sub02" style="display:none;">
            <div class="boxSR">
              <ul class="boxSM">
                <li class="left_bg"></li>
                <li class="subMenu"><a href="#"><img src="./images/header/common/sm_safety01Off.gif" alt="학교급식 위생관리" /></a></li>
                <li class="subMenu"><a href="#"><img src="./images/header/common/sm_safety02Off.gif" alt="식중독 대처요령" /></a></li>
                <li class="subMenu"><a href="#"><img src="./images/header/common/sm_safety03Off.gif" alt="안전사고예방" /></a></li>
                <li class="subMenu"><a href="#"><img src="./images/header/common/sm_safety04Off.gif" alt="안전사고 대처요령" /></a></li>
                <li class="last subMenu"><a href="/foodSen/inspectionResultList.do"><img src="./images/header/common/sm_safety05Off.gif" alt="위생.안전성 검사결과" /></a></li>
                <li class="right_bg"></li>
              </ul>
            </div>
          </div>
        </li>
        <li class="mainMenu"><a href="/foodSen/improvementCaseList.do"><img src="./images/header/common/mm_factoryOff.gif" alt="학교급식시설관리" /></a>
          <div class="subMenu" id="sub03" style="display:none;">
            <div class="boxSR">
              <ul class="boxSM">
                 <li class="left_bg"></li>
                <li class="subMenu"><a href="#"><img src="./images/header/common/sm_factory01Off.gif" alt="급식환경개선사업" /></a></li>
                <li class="subMenu"><a href="/foodSen/improvementCaseList.do"><img src="./images/header/common/sm_factory02Off.gif" alt="급식시설개선사례" /></a></li>
                <li class="subMenu"><a href="#"><img src="./images/header/common/sm_factory03Off.gif" alt="급식기구관리전환" /></a></li>
                <li class="last subMenu"><a href="#"><img src="./images/header/common/sm_factory04Off.gif" alt="컨설팅신청안내" /></a></li>
                <li class="right_bg"></li>
              </ul>
            </div>
          </div>
        </li>
        <li class="mainMenu"><a href="#"><img src="./images/header/common/mm_foodOff.gif" alt="학교급식식재료" /></a>
          <div class="subMenu" id="sub04" style="display:none;">
            <div class="boxSR">
              <ul class="boxSM">
                 <li class="left_bg"></li>
                <li class="subMenu"><a href="#"><img src="./images/header/common/sm_food01Off.gif" alt="식재료 구매·관리" /></a></li>
                <li class="subMenu"><a href="#"><img src="./images/header/common/sm_food02Off.gif" alt="식재료 시장조사" /></a></li>
                <li class="last subMenu"><a href="#"><img src="./images/header/common/sm_food03Off.gif" alt="부적합 납품 업체" /></a></li>
                <li class="right_bg"></li>
              </ul>
            </div>
          </div>
        </li>
        <li class="mainMenu"><a href="#"><img src="./images/header/common/mm_eduOff.gif" alt="영양,교육" /></a>
          <div class="subMenu" id="sub05" style="display:none;">
            <div class="boxSR">
              <ul class="boxSM">
                <li class="left_bg"></li>
                <li class="subMenu"><a href="#"><img src="./images/header/common/sm_edu01Off.gif" alt="영양·식생활교육" /></a></li>
                <li class="subMenu"><a href="#"><img src="./images/header/common/sm_edu02Off.gif" alt="추천식단" /></a></li>
                <li class="subMenu"><a href="#"><img src="./images/header/common/sm_edu03Off.gif" alt="추천레시피" /></a></li>
                <li class="last subMenu"><a href="#"><img src="./images/header/common/sm_edu04Off.gif" alt="학교급식특색활동" /></a></li>
                <li class="right_bg"></li>
              </ul>
            </div>
          </div>
        </li>
        <li class="mainMenu"><a href="/foodSen/recruitList.do"><img src="./images/header/common/mm_partOff.gif" alt="참여마당" /></a>
          <div class="subMenu" id="sub06" style="display:none;">
            <div class="boxSR">
              <ul class="boxSM">
                <li class="left_bg"></li>
                <li class="subMenu"><a href="/foodSen/recruitList.do"><img src="./images/header/common/sm_part01Off.gif" alt="학교급식인력풀" /></a></li>
                <li class="subMenu"><a href="#"><img src="./images/header/common/sm_part02Off.gif" alt="영양(교)사이야기" /></a></li>
                <li class="subMenu"><a href="#"><img src="./images/header/common/sm_part03Off.gif" alt="조리(원)사이야기" /></a></li>
                <li class="subMenu"><a href="#"><img src="./images/header/common/sm_part04Off.gif" alt="자유게시판" /></a></li>
                <li class="last subMenu"><a href="http://food.sen.go.kr/researchList.do"><img src="./images/header/common/sm_part05Off.gif" alt="설문조사" /></a></li>
                <li class="right_bg"></li>
              </ul>
            </div>
          </div>
        </li>
        <li class="mainMenu last"><a href="/foodSen/TrainingEvent.do"><img src="./images/header/common/mm_noticeOff.gif" alt="알림마당" /></a>
          <div class="subMenu" id="sub07" style="display:none;">
            <div class="boxSR">
              <ul class="boxSM">
                <li class="left_bg"></li>
                <li class="subMenu"><a href="#"><img src="./images/header/common/sm_notice01Off.gif" alt="급식소식" /></a></li>
                <li class="subMenu"><a href="/foodSen/TrainingEvent.do"><img src="./images/header/common/sm_notice02Off.gif" alt="연수·행사" /></a></li>
                <li class="subMenu"><a href="#"><img src="./images/header/common/sm_notice03Off.gif" alt="업무자료실" /></a></li>
                <li class="subMenu"><a href="#"><img src="./images/header/common/sm_notice04Off.gif" alt="관련법규" /></a></li>
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