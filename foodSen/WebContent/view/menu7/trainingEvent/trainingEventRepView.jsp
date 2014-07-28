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
		
		location.href='/foodSen/improvementCaseEditFrom.do?seq='+seq+'&currentPage='+currentPage+'&searchingNow='+searchingNow;
	}
	
	function goDelete(){
		var seq = "${seq}"; // 뷰페이지 시퀀스넘버
		var pw = "${resultClass.pw}"; // 뷰페이지 현재페이지
		
		//url
		url="/foodSen/trainingEventRepDeleteForm.do?seq="+seq+"&pw="+pw;
		// 새로운 윈도우를 엽니다.
   		open(url,"confirm","toolbar=no,location=no,status=no,menubar=no,scrollbars=no,resizable=no,width=500,height=220");
	}
	
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
	
	
	//이미지 체인져
	function chgImg(obj){
		$('#preview').attr('src',obj);
	}
	
	//이미지 팝업띄우기
	function openPrev(seq){
		
		var frm = document.prevImg;    //팝업에 넘길 부모창의 폼
		frm.seq.value = seq;           //폼의 값들을 셋팅한다.  

		//빈페이지로 팝업창을 우선 하나 띄운다.
		window.open('', 'popup_post', 'width=408, height=415, resizable= yes');

		//부모창의 타겟을 빈페이지로 띄운 팝업창의 이름으로 한다
		frm.target = 'popup_post';   

		//넘길 폼의 action을 팝업에 나타낼 페이지로 한다.
		frm.action = 'trainingEventPrevImg.do';

		//팝업으로 넘길 값을 가지고있는 폼을 submit 한다.
		frm.submit();
	}
	
	
</script>

<div id="container">
	<div id="contents">
	
		<p><img src="./images/sub/factory/sub_vimg_01.jpg" alt="건강한 급식 행복한 학교" /></p>
		
		<!-- 좌측메뉴 -->
		<jsp:include page="/view/include/menu7/trainingEventLnb.jsp"/>
		<!-- .//좌측메뉴 -->
		
	
		<!-- 우측 컨텐츠 -->
		<div class="right_box">
			
			<!-- 우측상단 제목 -->
			<p><img src="./images/sub/factory/title_02.gif" alt="연수행사" /></p>
			<!-- .//우측상단 제목 -->
			
			
			<!-- 우측상단 경로 정보 -->
			<p class="history"><img src="./images/sub/history_home.gif" alt="home" /> 연수행사 <img src="./images/sub/history_arrow.gif" alt="다음" /> <strong>연수행사</strong></p>
       		<p class="pt30"></p>
			<!-- .//우측상단 경로 정보 -->
			
			
			<!-- 우측게시판영역 틀 -->
			<div class="img_list">
				
				
				<!-- 이미지영역 -->
				<div class="img_box">
					
					
					<!-- 첫번째 이미지를 노출시킴 -->
					<span>
						<c:if test="${resultClass.img1 != null}">
							<a href="javascript:openPrev('${seq}')">
								<img id="preview" src="${resultClass.img1}" alt="" />
							</a>
						</c:if>
						<c:if test="${resultClass.img1 == null}">
							<li class="prn"><img src="./images/sub/factory/no_s_img.gif" alt="" /></li>
						</c:if>
					</span>
					<!-- .//첫번째 이미지를 노출시킴 -->
					
					<ul>
						<c:if test="${resultClass.img1 != null}">
							<li><a href="javascript:chgImg('${resultClass.img1}')"><img src="${resultClass.img1}" alt="첫번째이미지" /></a></li>
						</c:if>
						<c:if test="${resultClass.img1 == null}">
							<li class="prn"><img src="./images/sub/factory/no_s_img.gif" alt="" /></li>
						</c:if>
						
						<c:if test="${resultClass.img2 != null}">
							<li><a href="javascript:chgImg('${resultClass.img2}')"><img src="${resultClass.img2}" alt="두번째이미지" /></a></li>
						</c:if>
						<c:if test="${resultClass.img2 == null}">
							<li class="prn"><img src="./images/sub/factory/no_s_img.gif" alt="" /></li>
						</c:if>
						
						<c:if test="${resultClass.img3 != null}">
							<li class="prn"><a href="javascript:chgImg('${resultClass.img3}')"><img src="${resultClass.img3}" alt="세번째이미지" /></a></li>
						</c:if>
						<c:if test="${resultClass.img3 == null}">
							<li class="prn"><img src="./images/sub/factory/no_s_img.gif" alt="" /></li>
						</c:if>
						
						
					</ul>

				</div>
				<!-- .//이미지영역 -->
				
			
				<!-- 게시판영역 -->
				<div class="tbl_box w490 fl">
					
					<table width="100%" border="0" cellspacing="0" cellpadding="0" class="tbl_type01" summary="연수행사">
						<caption>연수행사</caption>
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
								<td>${resultClass.writer}</td>
								<th>등록일</th>
								<td><fmt:formatDate value="${resultClass.reg_date}" pattern="yyyy-MM-dd" /></td>
								<th>조회수</th>
								<td>${resultClass.hits}</td>
							</tr>
							<tr>
								<th>내용</th>
								<td id="description" colspan="5" class="tl h150"><pre>${resultClass.description}</pre></td>
							</tr>
							<tr>
								<th>첨부파일</th>
								<td colspan="5" class="tl">
									<c:if test="${resultClass.attach_name != null}">
										<a href="/foodSen/trainingEvent_FileDownload.do?attach_name=${resultClass.attach_name}">
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
							<span class="wte_l"><a href="/foodSen/TrainingEventList.do?currentPage=${currentPage}" class="wte_r">목록</a></span>
						</c:if>
						<c:if test="${searchingNow == 1}">
							<span class="wte_l"><a href="/foodSen/TrainingEventList.do?searchType=${searchType}&userinput=${userinput}&currentPage=${currentPage}" class="wte_r">목록</a></span>
						</c:if>
						
						
						<c:if test="${sessionScope.session_id == resultClass.writer}">
							<span class="per_l"><a href="javascript:goEdit()" class="pre_r">수정</a></span>
							<span class="wte_l"><a href="javascript:goDelete()" class="wte_r">삭제</a></span>
						</c:if>
					</span>
					<!-- //버튼 -->
				
				
				</div>
				<!-- .//게시판영역 -->
				
			</div>
			<!-- .//우측게시판영역 틀 -->
			
			
			
		</div>
		<!-- .//우측 컨텐츠 -->
		
		<p class="bottom_bg"></p>
		
	</div>
</div>

 <form name="prevImg" method="post">
 	<input type="hidden" id="seq" name="seq" value="${seq}">
 </form>

<jsp:include page="/view/include/footer.jsp"/>