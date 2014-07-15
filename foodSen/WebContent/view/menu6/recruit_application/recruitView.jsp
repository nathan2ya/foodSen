<%@ page language="java" contentType="text/html; charset=EUC-KR"
    pageEncoding="EUC-KR"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt_rt" %>



<jsp:include page="/view/include/top.jsp"/>
<link href="/css/base.css" rel="stylesheet" type="text/css" />
<link href="/css/common.css" rel="stylesheet" type="text/css" />


<script type="text/javascript">

	function goEdit(){
		var seq = "${seq}"; // 뷰페이지 시퀀스넘버
		var currentPage = "${currentPage}"; // 뷰페이지 현재페이지
		var searchingNow = "${searchingNow}"; // 뷰페이지 검색중인 여부
		
		location.href='/foodSen/recruitEditFrom.do?seq='+seq+'&currentPage='+currentPage+'&searchingNow='+searchingNow;
	}

	function goDelete(){
		var seq = "${seq}"; // 뷰페이지 시퀀스넘버
		var pw = "${resultClass.pw}"; // 뷰페이지 현재페이지
		
		//url
		url="/foodSen/recruitDeleteFrom.do?seq="+seq+"&pw="+pw;
		// 새로운 윈도우를 엽니다.
		open(url,"confirm","toolbar=no,location=no,status=no,menubar=no,scrollbars=no,resizable=no,width=500,height=220");
	}
	
	//내용 부분 오토링크
	$(document).ready(function(){
		autolink(document.getElementById("description"));
	});
	
	function autolink(id) {
	    var container = id;
	    var doc = container.innerHTML;
	    
	    //주소자동링크
	    var regURL = new RegExp("(http|https|ftp|telnet|news|irc)://([-/.a-zA-Z0-9_~#%$?&=:200-377()]+)","gi");
	    
	    //이메일자동링크
	    var regEmail = new RegExp("([xA1-xFEa-z0-9_-]+@[xA1-xFEa-z0-9-]+\.[a-z0-9-]+\.[a-z0-9-]+)","gi");
	
	    container.innerHTML = doc.replace(regURL,"<a href='$1://$2' target='_blank'>$1://$2</a>").replace(regEmail,"<a href='mailto:$1'>$1</a>");
	}
	//.내용 부분 오토링크 종료
	
</script>



<div id="container">
	<div id="contents">
	
		<p><img src="./images/sub/safety/sub_vimg_01.jpg" alt="건강한 급식 행복한 학교" /></p>
		
		<!-- 좌측메뉴 -->
		<jsp:include page="/view/include/menu6/recruitLnb.jsp"/>
		<!-- .//좌측메뉴 -->
		
	
		<!-- 우측 컨텐츠 -->
		<div class="right_box">
			
			<!-- 우측상단 제목 -->
			<h3><img src="./images/sub/particiation/title_01.gif" alt="구인" /></h3>
			<!-- .//우측상단 제목 -->
			
			
			<!-- 우측상단 경로 정보 -->
			<p class="history"><img src="./images/sub/history_home.gif" alt="home" /> 참여마당 <img src="./images/sub/history_arrow.gif" alt="다음" /> <strong>구인</strong></p>
       		<p class="pt30"></p>
			<!-- .//우측상단 경로 정보 -->
			
			
			<!-- 구인/구직 탭 -->
			<ul class="pool_tab">
				<li><a href="/foodSen/recruitList.do" class="pool_tab01_on">구인</a></li>
				<li><a href="/foodSen/applicationList.do" class="pool_tab02">구직</a></li>
			</ul>
			<!-- .//구인/구직 탭 -->
			
			
			<!-- 게시판영역 -->
			<div class="tbl_box">
					
				<table width="100%" border="0" cellspacing="0" cellpadding="0" class="tbl_type01" summary="학교급식인력풀(구인)">
					<caption>학교급식인력풀(구인)</caption>
					<colgroup>
						<col width="15%"/>
						<col width="20%"/>
						<col width="15%"/>
						<col width="20%"/>
						<col width="15%"/>
						<col width="%"/>
					</colgroup>
					<tbody>
					
						<!-- 1번째줄 -->
						<tr>
							<th>제목</th>
							<td colspan="5" class="tl">
								${resultClass.title}
							</td>
						</tr>
						<!-- .//1번째줄 -->
						
						
						<!-- 2번째줄 -->
						<tr>
							<th>등록자</th>
							<td colspan="1" class="tl">
								${resultClass.reg_name}
							</td>
							<th>등록일</th>
							<td colspan="1" class="tl">
								<fmt:formatDate value="${resultClass.reg_date}" pattern="yyyy-MM-dd" />
							</td>
							<th>마감여부</th>
							<td colspan="1" class="tl">
								${resultClass.end_yn}
							</td>
						</tr>
						<!-- .//2번째줄 -->
						
						
						<!-- 3번째줄 -->
						<tr>
							<th>학교급</th>
							<td colspan="1" class="tl">
								<c:if test="${resultClass.school_type == 1}">
									초등학교
								</c:if>
								<c:if test="${resultClass.school_type == 2}">
									중학교
								</c:if>
								<c:if test="${resultClass.school_type == 3}">
									고등학교
								</c:if>
								<c:if test="${resultClass.school_type == 4}">
									특수학교
								</c:if>
								<c:if test="${resultClass.school_type == 5}">
									각종
								</c:if>
							</td>
							<th>학교명</th>
							<td colspan="1" class="tl">
								${resultClass.school_name}
							</td>
							<th>지역</th>
							<td colspan="1" class="tl">
								<c:if test="${resultClass.loc_seq == 01}">
									강남구
								</c:if>
								<c:if test="${resultClass.loc_seq == 02}">
									강동구
								</c:if>
								<c:if test="${resultClass.loc_seq == 03}">
									강북구
								</c:if>
								<c:if test="${resultClass.loc_seq == 04}">
									강서구
								</c:if>
								<c:if test="${resultClass.loc_seq == 05}">
									관악구
								</c:if>
								<c:if test="${resultClass.loc_seq == 06}">
									광진구
								</c:if>
								<c:if test="${resultClass.loc_seq == 07}">
									구로구
								</c:if>
								<c:if test="${resultClass.loc_seq == 08}">
									금천구
								</c:if>
								<c:if test="${resultClass.loc_seq == 09}">
									노원구
								</c:if>
								<c:if test="${resultClass.loc_seq == 10}">
									도봉구
								</c:if>
								<c:if test="${resultClass.loc_seq == 11}">
									동대문구
								</c:if>
								<c:if test="${resultClass.loc_seq == 12}">
									동작구
								</c:if>
								<c:if test="${resultClass.loc_seq == 13}">
									마포구
								</c:if>
								<c:if test="${resultClass.loc_seq == 14}">
									서대문구
								</c:if>
								<c:if test="${resultClass.loc_seq == 15}">
									서초구
								</c:if>
								<c:if test="${resultClass.loc_seq == 16}">
									성동구
								</c:if>
								<c:if test="${resultClass.loc_seq == 17}">
									성북구
								</c:if>
								<c:if test="${resultClass.loc_seq == 18}">
									송파구
								</c:if>
								<c:if test="${resultClass.loc_seq == 19}">
									양천구
								</c:if>
								<c:if test="${resultClass.loc_seq == 20}">
									영등포구
								</c:if>
								<c:if test="${resultClass.loc_seq == 21}">
									강남구
								</c:if>
								<c:if test="${resultClass.loc_seq == 22}">
									용산구
								</c:if>
								<c:if test="${resultClass.loc_seq == 23}">
									종로구
								</c:if>
								<c:if test="${resultClass.loc_seq == 24}">
									중구
								</c:if>
								<c:if test="${resultClass.loc_seq == 25}">
									중랑구
								</c:if>
							</td>
						</tr>
						<!-- .//3번째줄 -->
						
						
						<!-- 4번째줄 -->
						<tr>
							<th>직종</th>
							<td colspan="1" class="tl">
								<c:if test="${resultClass.job == 01}">
									영양교사
								</c:if>
								<c:if test="${resultClass.job == 02}">
									영양사
								</c:if>
								<c:if test="${resultClass.job == 03}">
									조리사
								</c:if>
								<c:if test="${resultClass.job == 04}">
									조리원
								</c:if>
								<c:if test="${resultClass.job == 05}">
									배식도우미
								</c:if>
							</td>
							<th>근무형태</th>
							<td colspan="3" class="tl">
								<c:if test="${resultClass.gubun == 01}">
									전일제
								</c:if>
								<c:if test="${resultClass.gubun == 02}">
									시간제
								</c:if>
							</td>
						</tr>
						<!-- .//4번째줄 -->
						
						
						<!-- 5번째줄 -->
						<tr>
							<th>연락쳐</th>
							<td colspan="1" class="tl">
								${resultClass.phone}
							</td>
							<th>이메일</th>
							<td colspan="3" class="tl">
								${resultClass.email}
							</td>
						</tr>
						<!-- .//5번째줄 -->
						
						<!-- 6번째줄 -->
						<tr>
							<th>
								내용<br/>(2000자 이내)
							</th>
							<td id="description" colspan="5" class="tl">
								<pre>${resultClass.description}</pre>
							</td>
						</tr>
						<!-- .//6번째줄 -->
						
						
						<!-- 7번째줄 -->
						<tr>
							<th>첨부파일</th>
							<td colspan="5" class="tl">
								<c:if test="${resultClass.attach_name != null}">
									<a href="/foodSen/recruit_FileDownload.do?attach_name=${resultClass.attach_name}">
										${resultClass.attach_name}
										<img src="./images/sub/btn/btn_down.gif" alt="pdf" />
									</a>
								</c:if>
							</td>
						</tr>
						<!-- .//7번째줄 -->
						
					</tbody>
				</table>
					
				<p class="pt40"></p>
				

				<!-- 버튼 -->
				<span class="bbs_btn"> 
					
					<!-- 전체글 목록 -->
					<c:if test="${searchingNow == 0}">
						<span class="wte_l"><a href="/foodSen/recruitList.do?currentPage=${currentPage}" class="wte_r">목록</a></span>
					</c:if>
					<!-- .//전체글 목록 -->
					
					
					<!-- 검색중인 목록 -->
					<c:if test="${searchingNow == 1}">
						<c:if test="${searchType eq 'job'}">
							<span class="wte_l"><a href="/foodSen/recruitSearch.do?searchType=${searchType}&job=${resultClass.job}&currentPage=${currentPage}" class="wte_r">목록</a></span>
						</c:if>
						<c:if test="${searchType eq 'gubun'}">
							<span class="wte_l"><a href="/foodSen/recruitSearch.do?searchType=${searchType}&gubun=${resultClass.gubun}&currentPage=${currentPage}" class="wte_r">목록</a></span>
						</c:if>
						<c:if test="${searchType eq 'loc_seq'}">
							<span class="wte_l"><a href="/foodSen/recruitSearch.do?searchType=${searchType}&loc_seq=${resultClass.loc_seq}&currentPage=${currentPage}" class="wte_r">목록</a></span>
						</c:if>
						<c:if test="${searchType eq 'school_type'}">
							<span class="wte_l"><a href="/foodSen/recruitSearch.do?searchType=${searchType}&school_type=${resultClass.school_type}&currentPage=${currentPage}" class="wte_r">목록</a></span>
						</c:if>
					</c:if>
					<!-- .//검색중인 목록 -->
					
					
					<!-- 글 작성자일 경우만 버튼 노출 -->
					<c:if test="${sessionScope.session_id == resultClass.writer}">
						<span class="per_l"><a href="javascript:goEdit()" class="pre_r">수정</a></span>
						<span class="wte_l"><a href="javascript:goDelete()" class="wte_r">삭제</a></span>
					</c:if>
					<!-- .//글 작성자일 경우만 버튼 노출 -->
					
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
