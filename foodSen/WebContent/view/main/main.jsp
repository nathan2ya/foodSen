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
	// �˾�â���� ������� ��Ű pop1 �� ���� done �� �ƴϸ�(��, üũ���� ������,) 
	// ����â (pop1.html) �� ���ϴ�
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
			(str.charCodeAt(p) > 255) ? len+=2 : len++; // charCodeAt(���ڿ�) - ���ڿ��� �����ڵ尪���� ��ȯ�Ͽ� 255���� ũ�� �ѱ�.
		}
		return len;
	} // ���ڿ��� byte���� ���ϴ� �Լ� - �ѱ��̶�� ���ڴ� 2bytes, �׿ܿ��� 1byte�� ����Ѵ�.

	String.prototype.cut = function(len) {
        var str = this;
        var l = 0;
        for (var i=0; i<str.length; i++) {
                l += (str.charCodeAt(i) > 255) ? 2 : 1;
                if (l > len) return str.substring(0,i) + "...";
        }
        return str;
	}; // ���ڿ��� �߶��ִ� �Լ� - ���ϴ� byte����ŭ �߶��ְ� '...'�� �ٿ��ش�
	
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
	
	
	
		<!-- ���ʿ��� -->
		<p class="main_img">
			<img src="./images/main/img_v.jpg" alt="�ǰ��ѱ޽�, �ູ���б�,�ް� ���� Ű��� �����ູ����" />
		</p>
		<!-- .//���ʿ��� -->
		
		
		
		<!-- �����ʿ��� -->
		<div class="main_right">
			
			
			<!-- �������� -->
			<div class="notice">
			
				<!--�� Ŭ�� -->
				<ul class="main_tab">
					<li><a href="javascript:void(0)" onclick="doGoTab1(this,'01');" class="main_tab01_on">�޽ļҽ�</a></li>
					<li><a href="javascript:void(0)" onclick="doGoTab1(this,'02');" class="main_tab02">���������Ȱ��</a></li>
					<li><a href="javascript:void(0)" onclick="doGoTab1(this,'03');" class="main_tab03">��������</a></li>
				</ul>
				<!--///�� Ŭ�� -->
		          
		          
		        <!-- �� �ܺ� �׼� -->
				<div id="tab01" class="tabDivArea">
					<c:if test="${voNew eq null || empty voNew}">
						<ul>
							<li>�Խñ��� �����ϴ�. <span class="main_data"></span></li>
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
						<a href="#"><img src="./images/main/more.gif" alt="������" /></a>
					</span>
				</div>

				<div id="tab02" class="tabDivArea" style="display: none;">
					<c:if test="${voEvent eq null || empty voEvent}">
						<ul>
							<li>�Խñ��� �����ϴ�. <span class="main_data"></span></li>
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
						<a href="#"><img src="./images/main/more.gif" alt="������" /></a>
					</span>
				</div>
				
				<div id="tab03" class="tabDivArea" style="display: none;">
					<c:if test="${voResearch eq null || empty voResearch}">
						<ul>
							<li>�Խñ��� �����ϴ�. <span class="main_data"></span></li>
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
						<a href="#"><img src="./images/main/more.gif" alt="������" /></a>
					</span>
				</div>
				<!-- .//�� �ܺ� �׼� -->
				

			</div>
			<!-- .//�������� -->
			
			

			<!-- �б��޽� �η�Ǯ -->
			<div class="m_search_box">
				<h3>
					<img src="./images/main/title_01.gif" alt="�б��޽��η�Ǯ" />
				</h3>
				<fieldset>
					<form name="search" method="post" action="recruitSearch.do">
						<legend>�˻�</legend>
						<p>
							���� <select name="searchString">
								<option value="all">��ü</option>
								<c:forEach var="j" items="${job }">
									<option value="${j.code }">${j.code_name }</option>
								</c:forEach>
							</select>
						</p>
						<p>
							���� <select name="searchString1">
								<option value="all">��ü</option>
								<c:forEach var="l" items="${loc }">
									<option value="${l.code }">${l.code_name }</option>
								</c:forEach>
							</select> <a href="javascript:goSearch()"><img
								src="./images/main/btn_ser.gif" alt="�˻�" /></a>
						</p>
						<input type="hidden" name="searchType" id="searchType"
							value="main" />
					</form>
				</fieldset>
			</div>
			<!-- .//�б��޽� �η�Ǯ -->
			
			
			
			<!-- ������ -->
			<div class="main_icon">
				<ul>
					<li><a href="#"><img src="./images/main/img_btn_01.gif" alt="�б��޽İ����ⱸ" /></a></li>
					<li><a href="#"><img src="./images/main/img_btn_02.gif" alt="����(��)���̾߱�" /></a></li>
					<li><a href="#"><img src="./images/main/img_btn_03.gif" alt="������(��)�̾߱�" /></a></li>
					<li><a href="#"><img src="./images/main/img_btn_04.gif" alt="��õ�Ĵ�" /></a></li>
					<li><a href="#"><img src="./images/main/img_btn_05.gif" alt="��õ������" /></a></li>
					<li class="prn"><a href="#"><img src="./images/main/img_btn_06.gif" alt="�б��޽�������" /></a></li>
				</ul>
			</div>
   			<!-- .//������ -->
        	
        	
        	
        	<!-- �ϴ� ���ʿ��� -->
			<div class="main_left_box">
				<h3><img src="./images/main/main_h3_01.gif" alt="�б��޽�Ư��Ȱ��" /><span class="more"><a href="peculiarityActivityList.do"><img src="./images/main/more1.gif" alt="������" /></a></span></h3>
				<dl>
					<c:if test="${pa ne null && not empty pa }">
						<dt><img src="./FOOD/peculiarityActivity/${pa.seq }/imgs/${pa.img1}" alt="" /></dt>
						<dd><a class="title2" href="javascript:goView('${pa.seq }', 'pa')">${pa.title }</a></dd>
						<dd class="text title3"><td class="tl h150"><pre>${pa.description }</pre></td></dd>
					</c:if>
					<c:if test="${pa eq null || empty pa }">
						<dt><img src="./images/sub/factory/no_s_img.gif" alt="" /></dt>
						<dd>�Խñ��� �����ϴ�.</dd>
						<dd class="text"></dd>
					</c:if>
				</dl>
				<h3><img src="./images/main/main_h3_02.gif" alt="��������� Ȱ��" /> <span class="more"><a href="trainingEvent.do"><img src="./images/main/more1.gif" alt="������" /></a></span></h3>
				<dl class="dlbox">
					<c:if test="${te ne null && not empty te }">
						<dt><img src="./FOOD/trainingEvent/${te.seq }/imgs/${te.img1}" alt="" /></dt>
						<dd><a class="title2" href="javascript:goView('${te.seq }', 'te')">${te.title }</a></dd>
						<dd class="text title3"><td class="tl h150"><pre>${te.description }</pre></td></dd>
					</c:if>
					<c:if test="${te eq null || empty te }">
						<dt><img src="./images/sub/factory/no_s_img.gif" alt="" /></dt>
						<dd>�Խñ��� �����ϴ�.</dd>
						<dd class="text"></dd>
					</c:if>
				</dl>
			</div>
			<!-- .//�ϴ� ���ʿ��� -->
			
			
			
			<!-- �ϴ� ������ ��� ��� -->
			<div class="s_banner">
	          <ul>
	            <li><a href="#"><img src="./images/main/s_banner_01.gif" alt="���ߵ� �߻��� ��ó���" /></a></li>
	            <li><a href="#"><img src="./images/main/s_banner_02.gif" alt="������� �߻��� ��ó���" /></a></li>
	          </ul>
	        </div>
			<!-- .//�ϴ� ������ ��� ��� -->
			
			
			
			<!-- �ϴ� ������ �ϴ� �� -->
			<div class="s_notice">
				<!--tab object -->
				<ul class="s_main_tab">
					<li><a href="javascript:void(0)" onclick="doGoTab2(this,'01');" class="s_main_tab01_on">�ȳ�</a></li>
					<li><a href="javascript:void(0)" onclick="doGoTab2(this,'02');" class="s_main_tab02">����</a></li>
					<li><a href="javascript:void(0)" onclick="doGoTab2(this,'03');" class="s_main_tab03">���ñ��</a></li>
					<li><a href="javascript:void(0)" onclick="doGoTab2(this,'04');" class="s_main_tab04">����û</a></li>
				</ul>
				<!--//tab object -->

				<div id="s_tab01" class="tabDivArea">
					<ul>
						<li><a href="http://www.mfds.go.kr/jsp/page/food_zone_new.jsp" target="_blank">���ߵ�����</a></li>
						<li><a href="http://www.mfds.go.kr/fm/index.do" target="_blank">���ߵ�����</a></li>
						<li><a href="http://foodnara.go.kr/ews/user/loginForm.do?error=nullSession" target="_blank">���ߵ�����溸�ý���</a></li>
						<li><a href="http://www.haccphub.or.kr/welcome.do" target="_blank">HACCP���հ˻�</a></li>
						<li><a href="http://www.foodsafety.go.kr/fsafe/main.fs" target="_blank">���ǰ������������</a></li>
						<li><a href="baseWayList.do" target="_self">�б��޽� �⺻����</a></li>
					</ul>
				</div>
				<div id="s_tab02" class="tabDivArea" style="display: none;">
					<ul>
						<li><a href="http://www.naqs.go.kr/index.jsp" target="_blank">��깰 ǰ��</a></li>
						<li><a href="http://www.enviagro.go.kr/" target="_blank">����</a></li>
						<li><a href="http://www.kamis.co.kr/customer/main/main.do" target="_blank">����</a></li>
						<li><a href="http://www.ekape.or.kr/view/user/main/main.asp" target="_blank">��깰 ǰ��</a></li>
						<li><a href="http://cattle.mtrace.go.kr/index.do" target="_blank">�̷�</a></li>
						<li><a href="http://www.ekapepia.com/home/homeIndex.do" target="_blank">����</a></li>
						<li><a href="http://www.nfqs.go.kr/2013/index.asp" target="_blank">���깰 ǰ��</a></li>
						<li><a href="http://www.fishtrace.go.kr/" target="_blank">�̷�</a></li>
						<li><a href="http://www.fips.go.kr/" target="_blank">����</a></li>
						<li><a href="https://schoolhealth.kedi.re.kr/" target="_blank">�л��ǰ�����</a></li>
						<li><a href="http://www.foodnara.go.kr/foodnara/index.do" target="_blank">��ǰ����</a></li>
						<li><a href="http://call.mfds.go.kr/kfda" target="_blank">�ľ�óFAQ</a></li>
						<li><a href="http://kostat.go.kr/portal/korea/kor_nw/2/1/index.board?bmode=list&bSeq=&aSeq=&pageNo=1&rowNum=10&navCount=10&currPg=&sTarget=title&sTxt=%EC%86%8C%EB%B9%84%EC%9E%90" target="_blank">��������</a></li>
					</ul>
				</div>
				<div id="s_tab03" class="tabDivArea" style="display: none;">
					<ul>
						<li><a href="http://www.moe.go.kr/" target="_blank">������</a></li>
						<li><a href="http://www.mfds.go.kr/" target="_blank">��ǰ�Ǿ�ǰ����ó</a></li>
						<li><a href="http://www.maf.go.kr/" target="_blank">������ǰ��</a></li>
						<li><a href="http://www.mof.go.kr/" target="_blank">�ؾ�����</a></li>
						<li><a href="http://www.moel.go.kr/" target="_blank">���뵿��</a></li>
						<li><a href="http://www.kosha.or.kr/" target="_blank">�������ǰ���</a></li>
						<li><a href="http://www.mw.go.kr/" target="_blank">���Ǻ�����</a></li>
						<li><a href="http://www.cdc.go.kr/" target="_blank">������������</a></li>
						<li><a href="http://www.g2b.go.kr/" target="_blank">g2b</a></li>
						<li><a href="http://www.eat.co.kr/" target="_blank">eaT</a></li>
						<li><a href="http://www.orbon.co.kr/" target="_blank">ģȯ�����뼾��</a></li>
					</ul>
				</div>
				<div id="s_tab04" class="tabDivArea" style="display: none;">
					<ul>
						<li><a href="http://www.sen.go.kr/" target="_blank">����Ư���ñ���û</a></li>
						<li><a href="http://www.bogun.seoul.kr/" target="_blank">����Ư�����б����������</a></li>
						<li><a href="http://www.sendb.go.kr/" target="_blank">����</a></li>
						<li><a href="http://www.sens.go.kr/" target="_blank">����</a></li>
						<li><a href="http://www.nambuedu.seoul.kr/" target="_blank">����</a></li>
						<li><a href="http://www.ben.go.kr/" target="_blank">�Ϻ�</a></li>
						<li><a href="http://www.senjb.go.kr/" target="_blank">�ߺ�</a></li>
						<li><a href="http://www.edugd.seoul.kr/" target="_blank">����</a></li>
						<li><a href="http://www.sengs.go.kr/" target="_blank">����</a></li>
						<li><a href="http://www.knen.go.kr/" target="_blank">����</a></li>
						<li><a href="http://www.djedu.go.kr/" target="_blank">����</a></li>
						<li><a href="http://www.sensd.go.kr/" target="_blank">����</a></li>
						<li><a href="http://www.sensb.go.kr/" target="_blank">����</a></li>
					</ul>
				</div>
			</div>
	
	
			</div>
		<!--.//�����ʿ��� -->
		
		
		
		<!-- �ϴܹ�� -->
		<div class="banner">
			<p><img src="images/main/banner/img_01.gif" alt="���û���Ʈ" /></p>
	        <ul>
	          <li><a href="http://www.moe.go.kr/" target="_blank"><img src="./images/main/banner/banner_01.gif" alt="������" /></a></li>
	          <li><a href="http://www.seoul.go.kr/" target="_blank"><img src="./images/main/banner/banner_02.gif" alt="����Ư���ñ���û" /></a></li>
	          <li><a href="http://www.mfds.go.kr/" target="_blank"><img src="./images/main/banner/banner_03.gif" alt="��ǰ�Ǿ�ǰ����ó" /></a></li>
	          <li><a href="http://www.maf.go.kr/" target="_blank"><img src="./images/main/banner/banner_04.gif" alt="������ǰ��" /></a></li>
	          <li><a href="http://www.mof.go.kr/" target="_blank"><img src="./images/main/banner/banner_05.gif" alt="�ؾ�����" /></a></li>
	          <li><a href="http://www.kosha.or.kr/" target="_blank"><img src="./images/main/banner/banner_06.gif" alt="�������ǰ���" /></a></li>
	        </ul>
       		<span class="btn"> <a href="#"><img src="./images/main/banner/up.gif" alt="����" /></a> <a href="#"><img src="images/main/banner/stop.gif" alt="����" /></a> <a href="#"><img src="images/main/banner/down.gif" alt="�Ʒ���" /></a> </span> 
   		 </div>
		 <!-- .//�ϴܹ�� -->
		 
		
	</div>
</div>



<!-- Ǫ�� -->
<jsp:include page="/view/include/footer.jsp"/>

