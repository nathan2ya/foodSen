<%@ page language="java" contentType="text/html; charset=euc-kr"
	pageEncoding="euc-kr"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>


<jsp:include page="/view/include/top.jsp"/>
<link href="/css/base.css" rel="stylesheet" type="text/css" />
<link href="/css/common.css" rel="stylesheet" type="text/css" />


<script type="text/javascript" src="http://code.jquery.com/jquery-1.10.2.min.js"></script>
<script type="text/javascript">

	function goSearch(){
		search.submit();
	}

	$(document).ready(function(){
		$(".title").each(function(index, item){		
			if(getStrByte($(item).text())>16){
				$(item).html($(item).text().cut(16));
			}
		});
		
		$('#job').change(function(){
			$('#searchString').val($(this).val());
		});
	
		
		//검색타입에 따라 하위 셀렉트메뉴 변경
		$("#searchType").change(function(){
			var sType = $("#searchType option:selected").text();
			
			if(sType == '지역'){
				$('#searchString').val($('#loc').val());
				$('.loc').css('display', 'inline'); //loc만 inline 하고 나머지는 hide!
				$('.job').hide();
				$('.gubun').hide();
				$('.school_type').hide();
				$('#loc').change(function(){
					$('#searchString').val($(this).val());
				});
				
			}else if(sType == '직종'){
				
				$('#searchString').val($('#job').val());
				$('.loc').hide();
				$('.job').css('display', 'inline');
				$('.gubun').hide();
				$('.school_type').hide();
				$('#job').change(function(){
					$('#searchString').val($(this).val());
				});
				
			}else if(sType == '근무형태'){
				$('#searchString').val($('#gubun').val());
				$('.loc').hide();
				$('.job').hide();
				$('.gubun').css('display', 'inline');
				$('.school_type').hide();
				$('#gubun').change(function(){
					$('#searchString').val($(this).val());
				});
			}else if(sType == '학교급'){
				$('#searchString').val($('#school_type').val());
				$('.loc').hide();
				$('.job').hide();
				$('.gubun').hide();
				$('.school_type').css('display', 'inline');
				$('#school_type').change(function(){
					$('#searchString').val($(this).val());
				});
			}
		}); //.//검색타입에 따라 하위 셀렉트메뉴 변경 종료
		
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
	
		<p><img src="./images/sub/particiation/sub_vimg_01.jpg" alt="건강한 급식 행복한 학교" /></p>
		
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
			
			
			<!-- 게시판사용알림 -->
			<ul class="top_box">
	        	<li>구인등록은 서울시교육청(산하기관) 및 학교 교직원만 작성 가능합니다. </li>
				<li>구직정보는 서울시교육청(산하기관) 및 학교 교직원만 열람 가능합니다.</li>
				<li>구직정보는 서울시교육청 및 학교 교직원만 조회할 수 있도록 하였으나, 웹환경의 특성상 개인정보의 노출 시 피해를 입을 수 있으므로 불필요한 정보를 상세히 적는 일이 없도록 주의하여 주시기 바랍니다.(이력서 등에 주민등록번호가 작성되어 있는 경우, 임의 삭제합니다.)</li>
				<li><a href="http://www.sen.go.kr/web/services/bbs/bbsList.action?bbsBean.bbsCd=99">서울시교육청 구인/구직 바로가기</a></li>
        	</ul>
        	<!-- .//게시판사용알림 -->
		
		
			<!-- 게시판영역 -->
			<div class="tbl_box">
			
				<!-- 구인/구직 탭 -->
				<ul class="pool_tab">
					<li><a href="/foodSen/recruitList.do" class="pool_tab01_on">구인</a></li>
					<li><a href="/foodSen/applicationList.do" class="pool_tab02">구직</a></li>
				</ul>
				<!-- .//구인/구직 탭 -->
			 

				<table width="100%" border="0" cellspacing="0" cellpadding="0" class="tbl_type01" summary="구인">
				
					<caption>구인</caption>
					<colgroup>
						<col width="8%"/>
						<col width="*%"/>
						<col width="15%"/>
						<col width="8%"/>
						<col width="10%"/>
						<col width="8%"/>
						<col width="10%"/>
						<col width="15%"/>
						<col width="10%"/>
					</colgroup>
					
					<tbody>
						<tr>
							<th>NO</th>
							<th>제목</th>
							<th>직종</th>
							<th>근무<br />형태</th>
							<th>지역</th>
							<th>마감<br />여부</th>
							<th>학교명</th>
							<th>등록일</th>
							<th>조회수</th>
						</tr>
			
						
						<c:if test="${list eq '[]'}">
							<tr>
								<td colspan="9" align="center">게시글이 없습니다.</td>
							</tr>
						</c:if>
						
						<c:if test="${list != null}">
							
							
							<c:forEach var="list" items="${list}">
							
								<c:url var="url" value="/inspectionResultView.do"> 
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
				<form name="search" action="/foodSen/inspectionResultSearch.do" method="post">
					<div style="display: inline;">
						<select id="searchType" name="searchType">
							<option value="job">직종</option>
							<option value="gubun">근무형태</option>
							<option value="loc_seq">지역</option>
							<option value="school_type">학교급</option>
						</select>
					</div>
					<div class="job" style="display: inline;">
						<select id="job" name="job">
							<option value="01">영양교사</option>
							<option value="02">영양사</option>
							<option value="03">조리사</option>
							<option value="04">조리원</option>
							<option value="05">배식도우미</option>
						</select>
					</div>
					<div class="loc" style="display: none;">
						<select id="loc" name="loc">
							<option value="01">강남구</option>
							<option value="02">강동구</option>
							<option value="03">강북구</option>
							<option value="04">강서구</option>
							<option value="05">관악구</option>
							<option value="06">광진구</option>
							<option value="07">구로구</option>
							<option value="08">금천구</option>
							<option value="09">노원구</option>
							<option value="10">도봉구</option>
							<option value="11">동대문구</option>
							<option value="12">동작구</option>
							<option value="13">마포구</option>
							<option value="14">서대문구</option>
							<option value="15">서초구</option>
							<option value="16">성동구</option>
							<option value="17">성북구</option>
							<option value="18">송파구</option>
							<option value="19">양천구</option>
							<option value="20">영등포구</option>
							<option value="21">용산구</option>
							<option value="22">은평구</option>
							<option value="23">종로구</option>
							<option value="24">중구</option>
							<option value="25">중랑구</option>
						</select>
					</div>
					<div class="gubun" style="display: none;">
						<select id="gubun" name="gubun">
							<option value="01">전일제</option>
							<option value="02">시간제</option>
						</select>
					</div>
					<div class="school_type" style="display: none;">
						<select id="school_type" name="school_type">
							<option value="01">초</option>
							<option value="02">중</option>
							<option value="03">고</option>
							<option value="04">특수</option>
							<option value="05">각종</option>
						</select>
					</div>
					<input type="hidden" id="searchString" name="searchString" value="01" /> 
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