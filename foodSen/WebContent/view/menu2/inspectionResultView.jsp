<%@ page language="java" contentType="text/html; charset=EUC-KR"
    pageEncoding="EUC-KR"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt_rt" %>


<jsp:include page="/view/include/top.jsp"/>
<link href="/css/base.css" rel="stylesheet" type="text/css" />
<link href="/css/common.css" rel="stylesheet" type="text/css" />


<script type="text/javascript">
	function goDelete(){
		var seq = "${seq}"; // 뷰페이지 시퀀스넘버
		var pw = "${resultClass.pw}"; // 뷰페이지 현재페이지
		
		//url
		url="/foodSen/inspectionResultDeleteFrom.do?seq="+seq+"&pw="+pw;
		// 새로운 윈도우를 엽니다.
   		open(url,"confirm","toolbar=no,location=no,status=no,menubar=no,scrollbars=no,resizable=no,width=500,height=220");
	}
	
	function goEdit(){
		var seq = "${seq}"; // 뷰페이지 시퀀스넘버
		var currentPage = "${currentPage}"; // 뷰페이지 현재페이지
		var searchingNow = "${searchingNow}"; // 뷰페이지 검색중인 여부
		
		location.href='/foodSen/inspectionResultEditFrom.do?seq='+seq+'&currentPage='+currentPage+'&searchingNow='+searchingNow;
	}
	
	
	//내용 부분 오토링크
	$(document).ready(function(){
		autolink(document.getElementById("description"));
	});
	
	function autolink(id) {
		
	    var container = id;
	    var doc = container.innerHTML;
	    var regURL = new RegExp("(http|https|ftp|telnet|news|irc)://([-/.a-zA-Z0-9_~#%$?&=:200-377()]+)","gi");
	    var regEmail = new RegExp("([xA1-xFEa-z0-9_-]+@[xA1-xFEa-z0-9-]+\.[a-z0-9-]+\.[a-z0-9-]+)","gi");
	
	    container.innerHTML = doc.replace(regURL,"<a href='$1://$2' target='_blank'>$1://$2</a>").replace(regEmail,"<a href='mailto:$1'>$1</a>");
	}
	//.내용 부분 오토링크 종료
	
</script>

<div id="container">
	<div id="contents">
	
		<p><img src="./images/sub/safety/sub_vimg_01.jpg" alt="건강한 급식 행복한 학교" /></p>
		
		
		<!-- 좌측메뉴 -->
		<jsp:include page="/view/include/menu2/inspectionResultLnb.jsp"/>
		<!-- .//좌측메뉴 -->
		
		<!-- 우측 컨텐츠 -->
		<div class="right_box">
			
			<!-- 우측상단 제목 -->
			<p><img src="./images/sub/safety/title_05.gif" alt="위생.안전성 검사결과" /></p>
			<!-- .//우측상단 제목 -->
			
			
			<!-- 우측상단 경로 정보 -->
			<p class="history"><img src="./images/sub/history_home.gif" alt="home" /> 학교급식위생안전 <img src="./images/sub/history_arrow.gif" alt="다음" /> <strong>위생.안전성 검사결과</strong></p>
       		<p class="pt30"></p>
			<!-- .//우측상단 경로 정보 -->
			
			
			<!-- 게시판영역 -->
			<div class="tbl_box">
				
				<table width="100%" border="0" cellspacing="0" cellpadding="0" class="tbl_type01" summary="학교급식위생안전">
					<caption>학교급식위생안전</caption>
					<colgroup>
						<col width="15%" />
						<col width="20%" />
						<col width="15%" />
						<col width="20%" />
						<col width="15%" />
						<col width="%" />
					</colgroup>
					<tbody>
						<tr>
							<th>제목</th>
							<td colspan="5" class="tl"><strong>${resultClass.title}</strong></td>
						</tr>
						<tr>
							<th>등록자</th>
							<td>${resultClass.wirte}</td>
							<th>등록일</th>
							<td><fmt:formatDate value="${resultClass.reg_date}" pattern="yyyy-MM-dd" /></td>
							<th>조회수</th>
							<td>${resultClass.hits}</td>
						</tr>
						<tr>
							<th>내용</th>
							<td id="description" colspan="5" class="tl h150">
								<pre>${resultClass.description}</pre>
							</td>
						</tr>
						<tr>
							<th>첨부파일</th>
							<td colspan="5" class="tl">
								<c:if test="${resultClass.attach_name != null}">
									<a href="/foodSen/inspectionResult_FileDownload.do?attach_name=${resultClass.attach_name}">
										${resultClass.attach_name}
										<img src="./images/sub/btn/btn_down.gif" alt="pdf" />
									</a>
								</c:if>
							</td>
						</tr>
					</tbody>
				</table>
			
				<p class="pt40"></p>
			
				<!-- 버튼 -->
				<span class="bbs_btn"> 
					<c:if test="${searchingNow == 0}">
						<span class="wte_l"><a href="/foodSen/inspectionResultList.do?currentPage=${currentPage}" class="wte_r">목록</a></span>
					</c:if>
					<c:if test="${searchingNow == 1}">
						<span class="wte_l"><a href="/foodSen/inspectionResultSearch.do?searchType=${searchType}&userinput=${userinput}&currentPage=${currentPage}" class="wte_r">목록</a></span>
					</c:if>
					
					
					<c:if test="${sessionScope.session_admin_yn == 'y'}">
						<span class="per_l"><a href="javascript:goEdit()" class="pre_r">수정</a></span>
						<span class="wte_l"><a href="javascript:goDelete()" class="wte_r">삭제</a></span>
					</c:if>
				</span>
				<!-- //버튼 -->
			
			
			</div>
			<!-- .//게시판영역 -->
			
			
			
		</div>
		<!-- .//우측 컨텐츠 -->
		
		<p class="bottom_bg"></p>
		
	</div>
</div>

<jsp:include page="/view/include/footer.jsp"/>