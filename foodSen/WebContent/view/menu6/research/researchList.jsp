<%@ page language="java" contentType="text/html; charset=euc-kr"
	pageEncoding="euc-kr"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>


<jsp:include page="/view/include/top.jsp"/>
<link href="/css/base.css" rel="stylesheet" type="text/css" />
<link href="/css/common.css" rel="stylesheet" type="text/css" />


<script type="text/javascript" src="http://code.jquery.com/jquery-1.10.2.min.js"></script>
<script type="text/javascript">
	function goView(seq){
		view.sur_seq.value=seq;
		view.submit();
	}
	
	function goSearch(){
		search.submit();
	}
	
	$(document).ready(function(){
		var i=40;
		$(".title").each(function(index, item){		
			if(getStrByte($(item).text())>25){
				$(item).html($(item).text().cut(25));
			}
		});
	});
	
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
	
	function goResult(sur_seq) {
		var frm = document.result;    //팝업에 넘길 부모창의 폼
	
		frm.sur_seq.value = sur_seq;  //폼의 값들을 셋팅한다.  
	
		//빈페이지로 팝업창을 우선 하나 띄운다.
		window.open('', 'popup_post', 'width=550, height=545, resizable=yes');
	
		//부모창의 타겟을 빈페이지로 띄운 팝업창의 이름으로 한다
		frm.target = 'popup_post';   
	
		//넘길 폼의 action을 팝업에 나타낼 페이지로 한다.
		frm.action = 'researchResult.do';
	
		//팝업으로 넘길 값을 가지고있는 폼을 submit 한다.
		frm.submit();
	}
</script>



<div id="container">
	<div id="contents">
	
		<p><img src="./images/sub/particiation/sub_vimg_01.jpg" alt="건강한 급식 행복한 학교" /></p>
		
		<!-- 좌측메뉴 -->
		<jsp:include page="/view/include/menu6/researchLnb.jsp"/>
		<!-- .//좌측메뉴 -->
		
	
		<!-- 우측 컨텐츠 -->
		<div class="right_box">
			
			<!-- 우측상단 제목 -->
			<h3><img src="./images/sub/particiation/title_05.gif" alt="설문조사" /></h3>
			<!-- .//우측상단 제목 -->
			
			
			<!-- 우측상단 경로 정보 -->
			<p class="history"><img src="./images/sub/history_home.gif" alt="home" /> 참여마당 <img src="./images/sub/history_arrow.gif" alt="다음" /> <strong>설문조사</strong></p>
       		<p class="pt30"></p>
			<!-- .//우측상단 경로 정보 -->
			
			<!-- 게시판영역 -->
			<div class="tbl_box">
			
				<table width="100%" border="0" cellspacing="0" cellpadding="0" class="tbl_type01" summary="설문조사">
				
					<caption>설문조사</caption>
					<colgroup>
						<col width="8%"/>
			            <col width="45%"/>
			            <col width="15%"/>
			            <col width="15%"/>
			            <col width="10%"/>
			            <col width="8%"/>
					</colgroup>
					
					<tbody>
						<tr>
							<th>NO</th>
			                <th>제목</th>
			                <th>시작일</th>
			                <th>마감일</th>
			                <th>완료여부</th>
			                <th>조회수</th>
						</tr>
			
						<c:if test="${list eq '[]'}">
							<tr>
								<td colspan="9" align="center">게시글이 없습니다.</td>
							</tr>
						</c:if>
						
						<c:if test="${list != null}">
							
							<c:forEach var="list" items="${list}">
							
								<c:url var="url" value="/researchView.do"> 
									<c:param name="sur_seq" value="${list.sur_seq}"/>
									<c:param name="currentPage" value="${currentPage}"/>
									<c:param name="searchingNow" value="${searchingNow}"/>
									<c:if test="${searchingNow == 1}">
										<c:param name="searchType" value="${searchType}"/>
										<c:param name="userinput" value="${userinput}"/>
									</c:if>
								</c:url>
								
								<tr>
									<td align="center">
										<c:out value="${number}" />
										<c:set var="number" value="${number-1}"/>
									</td>
									<td align="left">
										<a href="${url}">${list.sur_title}</a>
									</td>
									<td align="center">${list.sur_sat_date}</td>
									<td align="center">${list.sur_end_date}</td>
									<td align="center">
										<c:if test="${list.sur_end_date < currentTime}">
											완료
										</c:if>
										<c:if test="${list.sur_end_date > currentTime}">
											진행중
										</c:if>
									</td>
									<td align="center">${list.hits}</td>
								</tr>
							</c:forEach>
							
							
						</c:if>
					</tbody>
					
				</table>


				<!-- 페이징 -->
				<ul class="paging">
					<li>${pagingHtml}</li>
				</ul>
				<!-- .//페이징 -->


				<!-- 목록 . 등록 버튼 -->
				<span class="bbs_btn"> 
					<span class="wte_l">
						<a href="/foodSen/researchList.do" class="wte_r">목록</a>
					</span>
					
					<!-- 관리자인 경우만 등록버튼 노출 -->
					<c:if test="${sessionScope.session_admin_yn == 'y'}">
						<span class="per_l">
							<a href="/foodSen/researchCreateForm.do" class="pre_r">등록</a>
						</span>
					</c:if>
					<!-- .//관리자인 경우만 등록버튼 노출 -->
					
				</span>
				<!-- .//목록 등록 버튼 -->

			</div>
			<!-- .//게시판영역 -->
			
			
			
			<!-- 검색 공간 -->
			<div class="search_box">
		        <form name="search" action="/foodSen/researchSearch.do" method="post">
		          <select name="searchType">
		            <option value="title">제목</option>
		          </select>
		          <input type="text" id="userinput" name="userinput" />
		          <a href="javascript:goSearch()"><img src="./images/sub/btn/btn_serch.gif" alt="검색" /></a> 
		        </form>
	        </div>
			<!-- .//검색 공간 -->
			
			
		</div>
		<!-- .//우측 컨텐츠 -->
		
		
		<p class="bottom_bg"></p>
		
	</div>
</div>

<jsp:include page="/view/include/footer.jsp"/>