<%@ page language="java" contentType="text/html; charset=euc-kr"
	pageEncoding="euc-kr"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>


<jsp:include page="/view/include/top.jsp"/>
<link href="/css/base.css" rel="stylesheet" type="text/css" />
<link href="/css/common.css" rel="stylesheet" type="text/css" />


<script type="text/javascript">


	
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
			<p><img src="./images/sub/safety/title_05.gif" alt="위생 안전성 검사결과" /></p>
			<!-- .//우측상단 제목 -->
			
			
			<!-- 우측상단 경로 정보 -->
			<p class="history"><img src="./images/sub/history_home.gif" alt="home" /> 학교급식위생안전 <img src="./images/sub/history_arrow.gif" alt="다음" /> <strong>위생.안전성 검사결과</strong></p>
       		<p class="pt30"></p>
			<!-- .//우측상단 경로 정보 -->
		
			<!-- 게시판영역 -->
			<div class="tbl_box">

				<table width="100%" border="0" cellspacing="0" cellpadding="0" class="tbl_type01" summary="위생.안전성 검사결과">
				
					<caption>위생.안전성 검사결과</caption>
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
						
						
						<c:if test="${list == null}">
							<tr>
								<td colspan="6" align="center">게시글이 없습니다.</td>
							</tr>
						</c:if>
						
						<c:if test="${list != null}">
							
							
							<c:forEach var="list" items="${list}">
								
								<tr>
									<td align="center">seq</td>
									<td align="left">${list.title}</td>
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
					<li>${pageNavi}</li>
				</ul>
				<!-- .//페이징 -->


				<!-- 목록 . 등록 버튼 -->
				<span class="bbs_btn"> 
					<span class="wte_l">
						<a href="/foodSen/inspectionResultList.do" class="wte_r">목록</a>
					</span>
					
					<!-- 관리자일경우 등록버튼 노출 -->
					<c:if test="${sessionScope.session_admin_yn == 'y'}">
						<span class="per_l">
							<a href="/foodSen/inspectionResultCreateFrom.do" class="pre_r">등록</a>
						</span>
					</c:if>
					<!-- .//관리자일경우 등록버튼 노출 -->
					
				</span>
				<!-- .//목록 등록 버튼 -->

			</div>
			<!-- .//게시판영역 -->
			
			
			
			<!-- 검색 공간 -->
			<div class="search_box">
		        <form name="search" action="foodNewsSearch.do" method="post">
		          <select name="searchType">
		            <option value="title">제목</option>
		            <option value="writer">등록자</option>
		          </select>
		          <input type="text" id="searchString" name="searchString" />
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