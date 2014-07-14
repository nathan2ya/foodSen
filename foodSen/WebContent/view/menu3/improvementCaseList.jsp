<%@ page language="java" contentType="text/html; charset=euc-kr"
	pageEncoding="euc-kr"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>


<jsp:include page="/view/include/top.jsp"/>
<link href="/css/base.css" rel="stylesheet" type="text/css" />
<link href="/css/common.css" rel="stylesheet" type="text/css" />


<script type="text/javascript">

	function goSearch(){
		if(validateSQL(search.userinput.value) > -1){
			alert("특수문자는 입력할 수 없습니다.");
			search.userinput.focus();
			return;
		}
		search.submit();
	}
	
	function validateSQL(obj){
		// SQLInjection을 막는 가장 간단한 방법은 입력되는 내용에서 '또는 "을 찾아 바꾸거나 입력하지 못하게 하는 것이다.	
		var x=obj;
		var pos = 0;
		var pos1 = 0;
		var pos2 = 0;
		var pos3 = 0;
		pos=x.indexOf("'"); // 객체ID의 내용에서 '을 찾는다. "을 찾으려면 pos=x.indexOf("\""); 이렇게 쓰면 된다.
		pos1=x.indexOf("\"");
		pos2=x.indexOf("<");
		pos3=x.indexOf(">");
		if (pos!=-1){
			return pos;
		}
		if (pos1!=-1){
			return pos1;
		}
		if (pos2!=-1){
			return pos2;
		}
		if (pos3!=-1){
			return pos3;
		}
	}
	
	$(document).ready(function(){
		$(".title").each(function(index, item){
			if(getStrByte($(item).text())>46){
				$(item).html($(item).text().cut(46));
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
	
</script>



<div id="container">
	<div id="contents">
	
		<p><img src="./images/sub/factory/sub_vimg_01.jpg" alt="건강한 급식 행복한 학교" /></p>
		
		<!-- 좌측메뉴 -->
		<jsp:include page="/view/include/menu3/improvementCaseLnb.jsp"/>
		<!-- .//좌측메뉴 -->
		
	
		<!-- 우측 컨텐츠 -->
		<div class="right_box">
			
			<!-- 우측상단 제목 -->
			<p><img src="./images/sub/factory/title_02.gif" alt="급식시설 개선 사례" /></p>
			<!-- .//우측상단 제목 -->
			
			
			<!-- 우측상단 경로 정보 -->
			<p class="history"><img src="./images/sub/history_home.gif" alt="home" /> 학교급식 시설관리 <img src="./images/sub/history_arrow.gif" alt="다음" /> <strong>급식시설개선사례</strong></p>
       		<p class="pt30"></p>
			<!-- .//우측상단 경로 정보 -->
		
		
			<!-- 게시판영역 -->
			<div class="tbl_box">

				<table width="100%" border="0" cellspacing="0" cellpadding="0" class="tbl_type01" summary="급식시설개선사례">
				
					<caption>급식시설개선사례</caption>
					<colgroup>
						<col width="8%" />
						<col width="*%" />
						<col width="10%" />
						<col width="15%" />
						<col width="15%" />
						<col width="10%" />
					</colgroup>
					
					<tbody>
						<tr>
							<th>NO</th>
							<th>제목</th>
							<th>첨부파일</th>
							<th>등록자</th>
							<th>등록일</th>
							<th>조회수</th>
						</tr>
						
						
						<c:if test="${list eq '[]'}">
							<tr>
								<td colspan="6" align="center">게시글이 없습니다.</td>
							</tr>
						</c:if>
						
						<c:if test="${list != null}">
							
							
							<c:forEach var="list" items="${list}">
							
								<c:url var="url" value="/improvementCaseView.do"> 
									<c:param name="seq" value="${list.seq}"/>
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
										<a href=${url}>${list.title}</a>
									</td>
									<td>
										<c:if test="${list.attach_name != null}">
											<img src="./images/sub/btn/btn_down.gif" alt="pdf" />
										</c:if>
									</td>
									<td align="center">${list.wirte}</td>
									<td align="center"><fmt:formatDate value="${list.reg_date}"  pattern="yyyy-MM-dd" /></td>
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
						<a href="/foodSen/improvementCaseList.do" class="wte_r">목록</a>
					</span>
					
					<!-- 회원일 경우 등록버튼 노출 -->
					<c:if test="${sessionScope.session_position == 1 || sessionScope.session_position == 2}">
						<span class="per_l">
							<a href="/foodSen/improvementCaseCreateFrom.do" class="pre_r">등록</a>
						</span>
					</c:if>
					<!-- .//관리자일경우 등록버튼 노출 -->
					
				</span>
				<!-- .//목록 등록 버튼 -->

			</div>
			<!-- .//게시판영역 -->
			
			
			
			<!-- 검색 공간 -->
			<div class="search_box">
		        <form name="search" action="/foodSen/improvementCaseSearch.do" method="post">
		          <select name="searchType">
		            <option value="title">제목</option>
		            <option value="writer">등록자</option>
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