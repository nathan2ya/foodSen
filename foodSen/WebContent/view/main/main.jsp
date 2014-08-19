<%@ page language="java" contentType="text/html; charset=euc-kr"
	pageEncoding="euc-kr"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt_rt" %>

<jsp:include page="/view/include/mainTop.jsp"/>
<link href="/css/base.css" rel="stylesheet" type="text/css" />
<link href="/css/common.css" rel="stylesheet" type="text/css" />
	
<script type="text/javascript" src="http://code.jquery.com/jquery-1.10.2.min.js"></script>
<script type="text/javascript">
	doGoTab1 = function(thisObject, tab) {
		
		$(".main_tab").find(">li>a").each(function(index, el) {
			$(el).removeClass("main_tab0"+(index+1)+"_on");
			$(el).addClass("main_tab0"+(index+1));
		});
		
		$(thisObject).addClass("main_tab"+tab+"_on");

		if("01"==tab){
			$("#tab02,#tab03").hide();
			$("#tab01").show();
		}else if("02"==tab){
			$("#tab01,#tab03").hide();
			$("#tab02").show();
		}else{
			$("#tab01,#tab02").hide();
			$("#tab03").show();
		}
	};
	
	doGoTab2 = function(thisObject, tab) {
		$(".s_main_tab").find(">li>a").each(function(index, el) {
			$(el).removeClass("s_main_tab0"+(index+1)+"_on");
			$(el).addClass("s_main_tab0"+(index+1));
		});
		$(thisObject).addClass("s_main_tab"+tab+"_on");
		if("01"==tab){
			$("#s_tab02,#s_tab03,#s_tab04").hide();
			$("#s_tab01").show();
		}else if("02"==tab){
			$("#s_tab01,#s_tab03,#s_tab04").hide();
			$("#s_tab02").show();
		}else if("03"==tab){
			$("#s_tab01,#s_tab02,#s_tab04").hide();
			$("#s_tab03").show();
		}else{
			$("#s_tab01,#s_tab02,#s_tab03").hide();
			$("#s_tab04").show();
		}
	};
		 
	function getCookie( name ){
		var nameOfCookie = name + "=";
		var x = 0;
		while ( x <= document.cookie.length )
		{
			var y = (x+nameOfCookie.length);
			if ( document.cookie.substring( x, y ) == nameOfCookie ) {
				if ( (endOfCookie=document.cookie.indexOf( ";", y )) == -1 )
					endOfCookie = document.cookie.length;
					return unescape( document.cookie.substring( y, endOfCookie ) );
			}
			x = document.cookie.indexOf( " ", x ) + 1;
			if ( x == 0 )
			break;
		}
		return "";
	}

	/* 
	// 팝업창에서 만들어진 쿠키 pop1 의 값이 done 이 아니면(즉, 체크하지 않으면,) 
	// 공지창 (pop1.html) 을 띄웁니다
	$(document).ready(function(){
		$(".title1").each(function(index, item){
			if(getStrByte($(item).text())>36){
				$(item).html($(item).text().cut(36));
			}
		});
		
		$(".title2").each(function(index, item){
			if(getStrByte($(item).text())>28){
				$(item).html($(item).text().cut(28));
			}
		});
		
		$(".title3").each(function(index, item){
			if(getStrByte($(item).text())>110){
				$(item).html($(item).text().cut(110));
			}
		});
		
		var cnt1=0;
		var cnt2=0;
		
		cnt1=$('#popup1Cnt').val();
		cnt2=$('#popup2Cnt').val();
		
		var left=0;
		
		if(cnt1 > 0){
			for(var i=0;i < cnt1;i++){
				if ( getCookie( "pop1_"+(i+1) ) != "done" ) {
					noticeWindow = window.open("popup1_"+(i+1)+".do", "popup1_"+(i+1),"left="+left+", top=0, width=427, height=460");
					left = left+460;
				}
			}
		}
		 

		if(cnt2 > 0){
			for(var k=0;k < cnt2;k++){
				if ( getCookie( "pop2_"+(k+1) ) != "done" ) {
					setWindow = window.open("popup2_"+(k+1)+".do", "popup2_"+(k+1),"left="+left+" , top=0, width=420, height=470");
					left = left+450;
				}
			}
		}
	});
	 */
	 
	
	function getStrByte(str) {
		var p, len = 0;
		for(p=0; p<str.length; p++) {
			(str.charCodeAt(p) > 255) ? len+=2 : len++; // charCodeAt(문자열) - 문자열을 유니코드값으로 변환하여 255보다 크면 한글.
		}
		return len;
	} // 문자열의 byte수를 구하는 함수 - 한글이라면 글자당 2bytes, 그외에는 1byte로 계산한다.

	String.prototype.cut = function(len) {
        var str = this;
        var l = 0;
        for (var i=0; i<str.length; i++) {
                l += (str.charCodeAt(i) > 255) ? 2 : 1;
                if (l > len) return str.substring(0,i) + "...";
        }
        return str;
	}; // 문자열을 잘라주는 함수 - 원하는 byte수만큼 잘라주고 '...'을 붙여준다
	
	function goView(seq, type){
		if(type == "news"){
			view.seq.value = seq;
			view.action = "foodNewsView.do";
			view.submit();
		}else if(type == "event"){
			view.seq.value = seq;
			view.action = "trainingEventView.do";
			view.submit();
		}else if(type == "research"){
			view.sur_seq.value = seq;
			view.action = "researchView.do";
			view.submit();
		}else if(type == "pa"){
			view.seq.value = seq;
			view.action = "peculiarityActivityView.do";
			view.submit();
		}else if(type == "te"){
			view.seq.value = seq;
			view.action = "trainingEventResultView.do";
			view.submit();
		}
	}
	
	function goEventView(seq, up_seq){
		if(seq == up_seq){
			view.seq.value = seq;
			view.action = "trainingEventView.do";
			view.submit();
		}else if(seq != up_seq){
			view.seq.value = seq;
			view.action = "trainingEventResultView.do";
			view.submit();
		}
	}
	
	function goSearch(){
		search.submit();
	}
	
</script>


<div id="container">
	<div id="main_contents">
	
	
	
		<!-- 왼쪽영역 -->
		<p class="main_img">
			<img src="./images/main/img_v.jpg" alt="건강한급식, 행복한학교,꿈과 끼를 키우는 서울행복교육" />
		</p>
		<!-- .//왼쪽영역 -->
		
		
		
		<!-- 오른쪽영역 -->
		<div class="main_right">
			
			
			<!-- 공지사항 -->
			<div class="notice">
			
				<!--탭 클릭 -->
				<ul class="main_tab">
					<li><a href="javascript:void(0)" onclick="doGoTab1(this,'01');" class="main_tab01_on">급식소식</a></li>
					<li><a href="javascript:void(0)" onclick="doGoTab1(this,'02');" class="main_tab02">연수·행사활동</a></li>
					<li><a href="javascript:void(0)" onclick="doGoTab1(this,'03');" class="main_tab03">설문조사</a></li>
				</ul>
				<!--///탭 클릭 -->
		          
		          
		        <!-- 각 텝별 액션 -->
				<div id="tab01" class="tabDivArea">
					<c:if test="${voNew eq null || empty voNew}">
						<ul>
							<li>게시글이 없습니다. <span class="main_data"></span></li>
						</ul>
					</c:if>
					<c:if test="${voNew ne null && not empty voNew}">
						<ul>
							<c:forEach var="voNew" items="${voNew}" varStatus="i">
								<li>
									<a class="title1" href="javascript:goView('${voNew.seq }', 'news')">${voNew.title }</a>
									<span class="main_data"><fmt:formatDate value="${voNew.reg_date }" pattern="yyyy-MM-dd" /></span>
								</li>
							</c:forEach>
						</ul>
					</c:if>
					<span class="more">
						<a href="#"><img src="./images/main/more.gif" alt="더보기" /></a>
					</span>
				</div>

				<div id="tab02" class="tabDivArea" style="display: none;">
					<c:if test="${voEvent eq null || empty voEvent}">
						<ul>
							<li>게시글이 없습니다. <span class="main_data"></span></li>
						</ul>
					</c:if>
					<c:if test="${voEvent ne null && not empty voEvent}">
						<ul>
							<c:forEach var="voEvent" items="${voEvent}" varStatus="i">
								<li>
									<a class="title1" href="javascript:goView('${voEvent.seq }', 'event')">${voEvent.title }</a>
									<span class="main_data"><fmt:formatDate value="${voEvent.reg_date }" pattern="yyyy-MM-dd" /></span>
								</li>
							</c:forEach>
						</ul>
					</c:if>
					<span class="more">
						<a href="#"><img src="./images/main/more.gif" alt="더보기" /></a>
					</span>
				</div>
				
				<div id="tab03" class="tabDivArea" style="display: none;">
					<c:if test="${voResearch eq null || empty voResearch}">
						<ul>
							<li>게시글이 없습니다. <span class="main_data"></span></li>
						</ul>
					</c:if>
					<c:if test="${voResearch ne null && not empty voResearch}">
						<ul>
							<c:forEach var="voResearch" items="${voResearch}" varStatus="i">
								<li>
									<a class="title1" href="javascript:goView('${voResearch.sur_seq }', 'research')">${voResearch.sur_title }</a>
									<span class="main_data"><fmt:formatDate value="${voResearch.reg_date }" pattern="yyyy-MM-dd" /></span>
								</li>
							</c:forEach>
						</ul>
					</c:if>
					<span class="more">
						<a href="#"><img src="./images/main/more.gif" alt="더보기" /></a>
					</span>
				</div>
				<!-- .//각 텝별 액션 -->
				

			</div>
			<!-- .//공지사항 -->
			
			

			<!-- 학교급식 인력풀 -->
			<div class="m_search_box">
				<h3>
					<img src="./images/main/title_01.gif" alt="학교급식인력풀" />
				</h3>
				<fieldset>
					<form name="search" method="post" action="recruitSearch.do">
						<legend>검색</legend>
						<p>
							직종 <select name="searchString">
								<option value="all">전체</option>
								<c:forEach var="j" items="${job }">
									<option value="${j.code }">${j.code_name }</option>
								</c:forEach>
							</select>
						</p>
						<p>
							지역 <select name="searchString1">
								<option value="all">전체</option>
								<c:forEach var="l" items="${loc }">
									<option value="${l.code }">${l.code_name }</option>
								</c:forEach>
							</select> <a href="javascript:goSearch()"><img
								src="./images/main/btn_ser.gif" alt="검색" /></a>
						</p>
						<input type="hidden" name="searchType" id="searchType"
							value="main" />
					</form>
				</fieldset>
			</div>
			<!-- .//학교급식 인력풀 -->
			
			
			
			<!-- 아이콘 -->
			<div class="main_icon">
				<ul>
					<li><a href="#"><img src="./images/main/img_btn_01.gif" alt="학교급식관리기구" /></a></li>
					<li><a href="#"><img src="./images/main/img_btn_02.gif" alt="영양(교)사이야기" /></a></li>
					<li><a href="#"><img src="./images/main/img_btn_03.gif" alt="조리사(원)이야기" /></a></li>
					<li><a href="#"><img src="./images/main/img_btn_04.gif" alt="추천식단" /></a></li>
					<li><a href="#"><img src="./images/main/img_btn_05.gif" alt="추천레시피" /></a></li>
					<li class="prn"><a href="#"><img src="./images/main/img_btn_06.gif" alt="학교급식컨설팅" /></a></li>
				</ul>
			</div>
   			<!-- .//아이콘 -->
        	
        	
        	
        	<!-- 하단 왼쪽영역 -->
			<div class="main_left_box">
				<h3><img src="./images/main/main_h3_01.gif" alt="학교급식특색활동" /><span class="more"><a href="peculiarityActivityList.do"><img src="./images/main/more1.gif" alt="더보기" /></a></span></h3>
				<dl>
					<c:if test="${pa ne null && not empty pa }">
						<dt><img src="./FOOD/peculiarityActivity/${pa.seq }/imgs/${pa.img1}" alt="" /></dt>
						<dd><a class="title2" href="javascript:goView('${pa.seq }', 'pa')">${pa.title }</a></dd>
						<dd class="text title3"><td class="tl h150"><pre>${pa.description }</pre></td></dd>
					</c:if>
					<c:if test="${pa eq null || empty pa }">
						<dt><img src="./images/sub/factory/no_s_img.gif" alt="" /></dt>
						<dd>게시글이 없습니다.</dd>
						<dd class="text"></dd>
					</c:if>
				</dl>
				<h3><img src="./images/main/main_h3_02.gif" alt="연수·행사 활동" /> <span class="more"><a href="trainingEvent.do"><img src="./images/main/more1.gif" alt="더보기" /></a></span></h3>
				<dl class="dlbox">
					<c:if test="${te ne null && not empty te }">
						<dt><img src="./FOOD/trainingEvent/${te.seq }/imgs/${te.img1}" alt="" /></dt>
						<dd><a class="title2" href="javascript:goView('${te.seq }', 'te')">${te.title }</a></dd>
						<dd class="text title3"><td class="tl h150"><pre>${te.description }</pre></td></dd>
					</c:if>
					<c:if test="${te eq null || empty te }">
						<dt><img src="./images/sub/factory/no_s_img.gif" alt="" /></dt>
						<dd>게시글이 없습니다.</dd>
						<dd class="text"></dd>
					</c:if>
				</dl>
			</div>
			<!-- .//하단 왼쪽영역 -->
			
			
			
			<!-- 하단 오른쪽 상단 배너 -->
			<div class="s_banner">
	          <ul>
	            <li><a href="#"><img src="./images/main/s_banner_01.gif" alt="식중독 발생시 대처요령" /></a></li>
	            <li><a href="#"><img src="./images/main/s_banner_02.gif" alt="안전사고 발생시 대처요령" /></a></li>
	          </ul>
	        </div>
			<!-- .//하단 오른쪽 상단 배너 -->
			
			
			
			<!-- 하단 오른쪽 하단 탭 -->
			<div class="s_notice">
				<!--tab object -->
				<ul class="s_main_tab">
					<li><a href="javascript:void(0)" onclick="doGoTab2(this,'01');" class="s_main_tab01_on">안내</a></li>
					<li><a href="javascript:void(0)" onclick="doGoTab2(this,'02');" class="s_main_tab02">정보</a></li>
					<li><a href="javascript:void(0)" onclick="doGoTab2(this,'03');" class="s_main_tab03">관련기관</a></li>
					<li><a href="javascript:void(0)" onclick="doGoTab2(this,'04');" class="s_main_tab04">교육청</a></li>
				</ul>
				<!--//tab object -->

				<div id="s_tab01" class="tabDivArea">
					<ul>
						<li><a href="http://www.mfds.go.kr/jsp/page/food_zone_new.jsp" target="_blank">식중독지수</a></li>
						<li><a href="http://www.mfds.go.kr/fm/index.do" target="_blank">식중독예방</a></li>
						<li><a href="http://foodnara.go.kr/ews/user/loginForm.do?error=nullSession" target="_blank">식중독조기경보시스템</a></li>
						<li><a href="http://www.haccphub.or.kr/welcome.do" target="_blank">HACCP통합검색</a></li>
						<li><a href="http://www.foodsafety.go.kr/fsafe/main.fs" target="_blank">농식품안전정보서비스</a></li>
						<li><a href="baseWayList.do" target="_self">학교급식 기본방향</a></li>
					</ul>
				</div>
				<div id="s_tab02" class="tabDivArea" style="display: none;">
					<ul>
						<li><a href="http://www.naqs.go.kr/index.jsp" target="_blank">농산물 품질</a></li>
						<li><a href="http://www.enviagro.go.kr/" target="_blank">인증</a></li>
						<li><a href="http://www.kamis.co.kr/customer/main/main.do" target="_blank">유통</a></li>
						<li><a href="http://www.ekape.or.kr/view/user/main/main.asp" target="_blank">축산물 품질</a></li>
						<li><a href="http://cattle.mtrace.go.kr/index.do" target="_blank">이력</a></li>
						<li><a href="http://www.ekapepia.com/home/homeIndex.do" target="_blank">유통</a></li>
						<li><a href="http://www.nfqs.go.kr/2013/index.asp" target="_blank">수산물 품질</a></li>
						<li><a href="http://www.fishtrace.go.kr/" target="_blank">이력</a></li>
						<li><a href="http://www.fips.go.kr/" target="_blank">유통</a></li>
						<li><a href="https://schoolhealth.kedi.re.kr/" target="_blank">학생건강정보</a></li>
						<li><a href="http://www.foodnara.go.kr/foodnara/index.do" target="_blank">식품나라</a></li>
						<li><a href="http://call.mfds.go.kr/kfda" target="_blank">식약처FAQ</a></li>
						<li><a href="http://kostat.go.kr/portal/korea/kor_nw/2/1/index.board?bmode=list&bSeq=&aSeq=&pageNo=1&rowNum=10&navCount=10&currPg=&sTarget=title&sTxt=%EC%86%8C%EB%B9%84%EC%9E%90" target="_blank">물가동향</a></li>
					</ul>
				</div>
				<div id="s_tab03" class="tabDivArea" style="display: none;">
					<ul>
						<li><a href="http://www.moe.go.kr/" target="_blank">교육부</a></li>
						<li><a href="http://www.mfds.go.kr/" target="_blank">식품의약품안전처</a></li>
						<li><a href="http://www.maf.go.kr/" target="_blank">농림축산식품부</a></li>
						<li><a href="http://www.mof.go.kr/" target="_blank">해양수산부</a></li>
						<li><a href="http://www.moel.go.kr/" target="_blank">고용노동부</a></li>
						<li><a href="http://www.kosha.or.kr/" target="_blank">안전보건공단</a></li>
						<li><a href="http://www.mw.go.kr/" target="_blank">보건복지부</a></li>
						<li><a href="http://www.cdc.go.kr/" target="_blank">질병관리본부</a></li>
						<li><a href="http://www.g2b.go.kr/" target="_blank">g2b</a></li>
						<li><a href="http://www.eat.co.kr/" target="_blank">eaT</a></li>
						<li><a href="http://www.orbon.co.kr/" target="_blank">친환경유통센터</a></li>
					</ul>
				</div>
				<div id="s_tab04" class="tabDivArea" style="display: none;">
					<ul>
						<li><a href="http://www.sen.go.kr/" target="_blank">서울특별시교육청</a></li>
						<li><a href="http://www.bogun.seoul.kr/" target="_blank">서울특별시학교보건진흥원</a></li>
						<li><a href="http://www.sendb.go.kr/" target="_blank">동부</a></li>
						<li><a href="http://www.sens.go.kr/" target="_blank">서부</a></li>
						<li><a href="http://www.nambuedu.seoul.kr/" target="_blank">남부</a></li>
						<li><a href="http://www.ben.go.kr/" target="_blank">북부</a></li>
						<li><a href="http://www.senjb.go.kr/" target="_blank">중부</a></li>
						<li><a href="http://www.edugd.seoul.kr/" target="_blank">강동</a></li>
						<li><a href="http://www.sengs.go.kr/" target="_blank">강서</a></li>
						<li><a href="http://www.knen.go.kr/" target="_blank">강남</a></li>
						<li><a href="http://www.djedu.go.kr/" target="_blank">동작</a></li>
						<li><a href="http://www.sensd.go.kr/" target="_blank">성동</a></li>
						<li><a href="http://www.sensb.go.kr/" target="_blank">성북</a></li>
					</ul>
				</div>
			</div>
	
	
			</div>
		<!--.//오른쪽영역 -->
		
		
		
		<!-- 하단배너 -->
		<div class="banner">
			<p><img src="images/main/banner/img_01.gif" alt="관련사이트" /></p>
	        <ul>
	          <li><a href="http://www.moe.go.kr/" target="_blank"><img src="./images/main/banner/banner_01.gif" alt="교육부" /></a></li>
	          <li><a href="http://www.seoul.go.kr/" target="_blank"><img src="./images/main/banner/banner_02.gif" alt="서울특별시교육청" /></a></li>
	          <li><a href="http://www.mfds.go.kr/" target="_blank"><img src="./images/main/banner/banner_03.gif" alt="식품의약품안전처" /></a></li>
	          <li><a href="http://www.maf.go.kr/" target="_blank"><img src="./images/main/banner/banner_04.gif" alt="농림축산식품부" /></a></li>
	          <li><a href="http://www.mof.go.kr/" target="_blank"><img src="./images/main/banner/banner_05.gif" alt="해양수산부" /></a></li>
	          <li><a href="http://www.kosha.or.kr/" target="_blank"><img src="./images/main/banner/banner_06.gif" alt="안전보건공단" /></a></li>
	        </ul>
       		<span class="btn"> <a href="#"><img src="./images/main/banner/up.gif" alt="위로" /></a> <a href="#"><img src="images/main/banner/stop.gif" alt="정지" /></a> <a href="#"><img src="images/main/banner/down.gif" alt="아래로" /></a> </span> 
   		 </div>
		 <!-- .//하단배너 -->
		 
		
	</div>
</div>



<!-- 푸터 -->
<jsp:include page="/view/include/footer.jsp"/>

