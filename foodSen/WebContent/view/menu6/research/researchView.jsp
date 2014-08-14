<%@ page language="java" contentType="text/html; charset=EUC-KR"
    pageEncoding="EUC-KR"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt_rt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<jsp:include page="../../include/top.jsp"/>

<script>
	//수정
	function goEdit(cnt){
		var sur_seq = "${resultClass.sur_seq}"; //이글의 시퀀스(정보)
		var res_cnt = "${res_cnt}"; //이글의 결과등록개수
		var permit = 0; //0이면 내용수정가능, 1이면 내용수정불가
		var current_date = "${current_date}"; //오늘날짜
		var sur_end_date = "${resultClass.sur_end_date}"; //설문종료일
		var searchType = "${searchType}";
		var userinput = "${userinput}";
		var searchingNow = "${searchingNow}";
		
		if(sur_end_date < current_date){
			alert("설문조사가 종료되면 수정이 불가능합니다.");
			return;
		}
		
		if(res_cnt > 0){
			permit = 1;
			if(confirm("설문결과가 존재하여 수정하실경우 설문조사의 데이터가 삭제됩니다. \n 게시글을 수정하시겠습니까?")!=0){
				location.href = '/foodSen/researchEditForm.do?sur_seq='+sur_seq+'&permit='+permit+'&searchType='+searchType+'&userinput='+userinput+'&searchingNow='+searchingNow;
			}else{
				return;
			}
		}
		
		location.href = '/foodSen/researchEditForm.do?sur_seq='+sur_seq+'&permit='+permit+'&searchType='+searchType+'&userinput='+userinput+'&searchingNow='+searchingNow;
	}
	
	//삭제
	function goDelete(){
		var current_date = "${current_date}"; //오늘날짜
		var sur_sat_date = "${resultClass.sur_sat_date}"; //설문시작일
		var sur_end_date = "${resultClass.sur_end_date}"; //설문종료일
		var res_cnt = "${res_cnt}"; //이글의 결과등록개수
		
		if(current_date < sur_sat_date || sur_end_date < current_date){  //시작하지않았거나, 끝났을경우
			if(confirm("게시글을 삭제하시겠습니까?")!=0){
				deleteOK.submit();
			}else{
				return;
			}
		}else{
			if(res_cnt > 0){
				if(confirm("설문조사 참여자가 있습니다. 삭제할경우 모든 데이터가 삭제됩니다. \n 그래도 삭제하시겠습니까?")!=0){
					deleteOK.submit();
				}else{
					return;
				}
			}else{
				if(confirm("설문조사 진행중입니다. \n 그래도 삭제하시겠습니까?")!=0){
					deleteOK.submit();
				}else{
					return;
				}
			}
		}
		
	}
	
	//결과전체보기
	function goResultView(){
		var sur_seq = "${resultClass.sur_seq}";
		
		//url
		url = '/foodSen/researchResult.do?sur_seq='+sur_seq;
		// 새로운 윈도우를 엽니다.
		open(url,"confirm","toolbar=no,location=no,status=no,menubar=no,scrollbars=yes,resizable=yes,width=550,height=545");
	}
	
	//사유전체보기
	function goReason(sur_seq){
		var sur_seq = "${resultClass.sur_seq}";
		
		//url
		url = '/foodSen/researchResult1.do?sur_seq='+sur_seq;
		// 새로운 윈도우를 엽니다.
		open(url,"confirm","toolbar=no,location=no,status=no,menubar=no,scrollbars=yes,resizable=yes,width=500,height=450");
	}
	
	function goAlert(){
		var sur_sat_date = "${resultClass.sur_sat_date}"; //설문시작일
		alert("설문조사가 시작되어야 결과열람이 가능합니다. \n 설문조사 시작일은 "+sur_sat_date+" 입니다.");
	}
	
	//저장
	function goSave() {
		var current_date = "${current_date}"; //오늘날짜
		var sur_sat_date = "${resultClass.sur_sat_date}"; //설문시작일
		var sur_end_date = "${resultClass.sur_end_date}"; //설문종료일
		var canSave = "${canSave}"; //참여가능0, 참여불가능1
		
		if(canSave == 1){
			alert("이미 설문조사에 참여하셨습니다. \n 설문조사는 1번만 참여하실 수 있습니다.");
			return;
		}
		
		if(current_date < sur_sat_date){//설문조사 시작전일때
			alert("지금은 설문조사는 기간이 아닙니다. \n 시작일자는 "+sur_sat_date+" 입니다.");
			return;
		}
		if(sur_end_date < current_date){//설문조사 종료되었을때
			alert("설문조사가 종료되어 참여하실 수 없습니다.");
			return;
		}
		
		
		var selectYn = "Y";
		var lengthMax = "Y";
		var lengthMaxTxt = "";
		$('.txt').each(function(index, item){
			/* 모든문항을 선택해야 설문조사 참여가 가능하도록 하려면 이 주석을 해제할것
			if($('input:radio[name=suri_numItem'+(index+1)+']:checked').val() == "undefined" || $('input:radio[name=suri_numItem'+(index+1)+']:checked').val() == "" || $('input:radio[name=suri_numItem'+(index+1)+']:checked').val() == null)
			{
				selectYn = "N";
			}
			 */
			if(getStrByte($('#descriptionItem'+(index+1)).val()) > 120){
				lengthMaxTxt = $('#descriptionItem'+(index+1));
				//$('#descriptionItem'+(index+1)).val($('#descriptionItem'+(index+1)).val().cut(120));
				$('#descriptionItem'+(index+1)).focus();
				lengthMax = "N";
				return;
			}
		});
		
		if(selectYn == "N"){
			alert("선택되지 않은 문항이 존재합니다.");
			return;
		}
		if(lengthMax == "N"){
			alert("선택사유는 100자 까지만 입력할 수 있습니다.");
			lengthMaxTxt.focus();
			return;
		}
		saveOK.submit();
		alert("설문이 완료되었습니다.\n참여하여 주셔서 감사합니다.");
	}
	
	function getStrByte(str){
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
	            if (l > len) return str.substring(0,i);
	    }
	    return str;
	}; // 문자열을 잘라주는 함수 - 원하는 byte수만큼 잘라준다
	
	

	$(document).ready(function(){
		$('#goSave').click(function(){
			var surq_seqItem = "";
			var surq_item = "";
			var suri_seqItem = "";
			var suri_numItem = "";
			var descriptionItem = "";
			
			$('.txt').each(function(index, item){
				surq_seqItem += $('#surq_seqItem'+(index+1)).val() + "|";
				surq_item += $('#surq_item'+(index+1)).val() + "|";
				suri_seqItem += $('#suri_seqItem'+(index+1)).val() + "|";
				suri_numItem += $('input:radio[name=suri_numItem'+(index+1)+']:checked').val() + "|";
				if($('#descriptionItem'+(index+1)).val() != ""){
					descriptionItem += $('#descriptionItem'+(index+1)).val() + "|";
				}else{
					descriptionItem += " |";
				}
			});
			$('.surq_seqItem').val(surq_seqItem);
			$('.surq_item').val(surq_item);
			$('.suri_seqItem').val(suri_seqItem);
			$('.suri_numItem').val(suri_numItem);
			$('.descriptionItem').val(descriptionItem);
		});
	});

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
			            <col width="15%"/>
			            <col width="20%"/>
			            <col width="15%"/>
			            <col width="20%"/>
			            <col width="15%"/>
			            <col width="%"/>
		            </colgroup>
            
            
					<tbody>
						<tr>
							<th>제목</th>
							<td colspan="5" class="tl"><strong>${resultClass.sur_title}</strong></td>
						</tr>
						<tr>
							<th>시작일</th>
							<td class="tl">${resultClass.sur_sat_date}</td>
							<th>종료일</th>
							<td class="tl">${resultClass.sur_end_date}</td>
							<th>문항수</th>
							<td class="tl">${resultClass.que_cnt}</td>
						</tr>
						<tr>
							<th>내용</th>
							<td colspan="5" class="tl">
								
								<c:forEach var="i" begin="0" end="${cnt-1}" step="1"> 
									<div class="research">
										<p>${i+1}. ${title[i]}</p>
										<input type="hidden" id="surq_seqItem${i+1}" name="surq_seqItem" class="txt" value="${i+1}">
										<input type="hidden" id="surq_item${i+1}" name="surq_item" value="${title[i]}">
										<ul>
											<c:if test="${i_title1[i] ne null}">
												<li><input type="radio" id="suri_num${i+1}" name="suri_numItem${i+1}" value="① ${i_title1[i]}" />  <label for="suri_num${i+1}">①  ${i_title1[i]} </label></li>
											</c:if>
											<c:if test="${i_title2[i] ne null}">
												<li><input type="radio" id="suri_num${i+1}" name="suri_numItem${i+1}" value="② ${i_title2[i]}" />  <label for="suri_num${i+1}">②  ${i_title2[i]} </label></li>
											</c:if>
											<c:if test="${i_title3[i] ne null}">
												<li><input type="radio" id="suri_num${i+1}" name="suri_numItem${i+1}" value="③ ${i_title3[i]}" />  <label for="suri_num${i+1}">③  ${i_title3[i]} </label></li>
											</c:if>
											<c:if test="${i_title4[i] ne null}">
												<li><input type="radio" id="suri_num${i+1}" name="suri_numItem${i+1}" value="④ ${i_title4[i]}" />  <label for="suri_num${i+1}">④  ${i_title4[i]} </label></li>
											</c:if>
											<c:if test="${i_title5[i] ne null}">
												<li><input type="radio" id="suri_num${i+1}" name="suri_numItem${i+1}" value="⑤ ${i_title5[i]}" />  <label for="suri_num${i+1}">⑤  ${i_title5[i]} </label></li>
											</c:if>
											<li>선택사유 <input type="text" id="descriptionItem${i+1 }" name="descriptionItem" class="inp" style="width:200px;" /> </li>
										</ul>
									</div>
								</c:forEach>  
								                  
							</td>
						</tr>
					</tbody>
				</table> 

				<p class="pt40"></p>
				
				<!--버튼--> 
				<span class="bbs_btn"> 
				
					<span class="wte_l"><a href="researchList.do" class="wte_r">목록</a></span>
					<c:if test="${sessionScope.session_admin_yn == 'y'}">
						<span class="wte_l"><a href="javascript:goEdit('${resCnt}')" class="wte_r">수정</a></span>
						<span class="wte_l"><a href="javascript:goDelete()" class="wte_r">삭제</a></span>
					</c:if>
					
					<c:if test="${sessionScope.session_admin_yn != 'y'}">
						<span class="per_l"><a href="javascript:goSave()" class="pre_r" id="goSave">저장</a></span>
					</c:if>
					
					<c:if test="${sessionScope.session_admin_yn == 'y'}">
						<!-- 진행전 -->
						<c:if test="${current_date < resultClass.sur_sat_date}">
							<span class="wte_l"><a href="javascript:goAlert()" class="wte_r">설문결과</a></span>
							<span class="wte_l"><a href="javascript:goAlert()" class="wte_r">사유전체보기</a></span>
						</c:if>
						
						<!-- 진행중 -->
						<c:if test="${resultClass.sur_sat_date <= current_date && current_date <= resultClass.sur_end_date}">
							<span class="wte_l"><a href="javascript:goResultView()" class="wte_r">설문결과</a></span>
							<span class="wte_l"><a href="javascript:goReason()" class="wte_r">사유전체보기</a></span>
						</c:if>
						
						<!-- 완료시 -->
						<c:if test="${resultClass.sur_end_date < current_date}">
							<span class="wte_l"><a href="javascript:goResultView()" class="wte_r">설문결과</a></span>
							<span class="wte_l"><a href="javascript:goReason()" class="wte_r">사유전체보기</a></span>
						</c:if>
					</c:if>

				</span> 
				<!--.//버튼--> 
        
			</div>
			<!--.//게시판영역 -->
			
		</div>
		<!--.//우측컨텐츠 -->
    
		<p class="bottom_bg"></p>
		
	</div>
</div>

<form name="saveOK" action="/foodSen/researchSave.do" method="post">
	<input type="hidden" id="sur_seq" name="sur_seq" value="${resultClass.sur_seq}" />
	<input type="hidden" id="surq_seqItem" name="surq_seqItem" class="surq_seqItem" />
	<input type="hidden" id="surq_item" name="surq_item"  class="surq_item"/>
	<input type="hidden" id="suri_seqItem" name="suri_seqItem" class="suri_seqItem"/>
	<input type="hidden" id="suri_numItem" name="suri_numItem" class="suri_numItem"/>
	<input type="hidden" id="descriptionItem" name="descriptionItem" class="descriptionItem"/>
</form>


<form name="deleteOK" action="/foodSen/researchDelete.do" method="post">
	<input type="hidden" id="sur_seq" name="sur_seq" value="${resultClass.sur_seq}" />
</form>

<jsp:include page="../../include/footer.jsp"/>