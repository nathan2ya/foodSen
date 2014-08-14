<%@ page language="java" contentType="text/html; charset=euc-kr"
	pageEncoding="euc-kr"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<jsp:include page="/view/include/top.jsp"/>
<link href="/css/base.css" rel="stylesheet" type="text/css" />
<link href="/css/common.css" rel="stylesheet" type="text/css" />

<script type="text/javascript" src="http://code.jquery.com/jquery-1.10.2.min.js"></script>
<script type="text/javascript" src="http://code.jquery.com/ui/1.10.3/jquery-ui.js"></script>
<link rel="stylesheet" type="text/css" href="http://code.jquery.com/ui/1.10.3/themes/smoothness/jquery-ui.css">
<script type="text/javascript">

	function goCreate(){
		var current_date = "${current_date}";
		
		if(!researchCreateForm.sur_title.value){
			alert("제목을 입력하세요.");
			researchCreateForm.sur_title.focus();
			return;
		}
		
		if(!researchCreateForm.sur_sat_date.value){
			alert("시작날짜를 선택하세요.");
			researchCreateForm.sur_sat_date.focus();
			return;
		}
		
		if(researchCreateForm.sur_sat_date.value < current_date){
			alert("설문조사 시작일은 현재일 이후만 등록가능합니다.");
			return;
		}
		
		if(researchCreateForm.sur_sat_date.value > researchCreateForm.sur_end_date.value){
			alert("시작날짜는 종료날짜보다 과거여야 합니다.");
			researchCreateForm.sur_sat_date.focus();
			return;
		}
		
		if(!researchCreateForm.sur_end_date.value){
			alert("종료날짜를 선택하세요.");
			researchCreateForm.sur_end_date.focus();
			return;
		}
		
		if(researchCreateForm.sur_sat_date.value > researchCreateForm.sur_end_date.value){
			alert("종료날짜는 시작날짜보다 미래여야 합니다.");
			researchCreateForm.sur_end_date.focus();
			return;
		}
		
		var num=document.getElementById("que_cnt").value;
		
		for(var i=1; i<=num; i++){
			var titleTxt=document.getElementById("surq_title"+i);
			var item1=document.getElementById("item1"+i);
			var item2=document.getElementById("item2"+i);
			var item3=document.getElementById("item3"+i);
			var item4=document.getElementById("item4"+i);
			var item5=document.getElementById("item5"+i);
			
			//문제, 문항의 입력이 안되었을때
			if(titleTxt.value == ""){
				alert("문제"+i+"번을 입력하세요.");
				titleTxt.focus();
				return;
			}
			
			if(item1.value == ""){
				alert("문제"+i+"번의 1번 항목을 입력하세요. \n 한 문제 1번항목은 필수로 입력하셔야 합니다.");
				item1.focus();
				return;
			}
			
			if(item2.value == ""){
				alert("문제"+i+"번의 2번 항목을 입력하세요. \n 한 문제 2번항목은 필수로 입력하셔야 합니다.");
				item2.focus();
				return;
			}
			/* 
			if(item3.value == ""){
				alert("문제"+i+"번의 3번 항목을 입력하세요.");
				item3.focus();
				return;
			}
			
			if(item4.value == ""){
				alert("문제"+i+"번의 4번 항목을 입력하세요.");
				item4.focus();
				return;
			}
			
			if(item5.value == ""){
				alert("문제"+i+"번의 5번 항목을 입력하세요.");
				item5.focus();
				return;
			}
			 */
			
			//문제, 문항의 글자수가 초과되었을때
			if(getStrByte(titleTxt.value) > 200){
				alert("문제 제목의 글자수가 초과 되었습니다.");
				//titleTxt.value = titleTxt.value.cut(120);
				titleTxt.focus();
				return;
			}
			
			if(getStrByte(item1.value) > 200){
				alert("문항의 글자수가 초과 되었습니다.");
				//item1.value = item1.value.cut(120);
				item1.focus();
				return;
			}
			
			if(getStrByte(item2.value) > 200){
				alert("문항의 글자수가 초과 되었습니다.");
				//item2.value = item2.value.cut(120);
				item2.focus();
				return;
			}
			
			if(getStrByte(item3.value) > 200){
				alert("문항의 글자수가 초과 되었습니다.");
				//item3.value = item3.value.cut(120);
				item3.focus();
				return;
			}
			
			if(getStrByte(item4.value) > 200){
				alert("문항의 글자수가 초과 되었습니다.");
				//item4.value = item4.value.cut(120);
				item4.focus();
				return;
			}
			
			if(getStrByte(item5.value) > 200){
				alert("문항의 글자수가 초과 되었습니다.");
				//item5.value = item5.value.cut(120);
				item5.focus();
				return;
			}
		
		}
		
		if(getStrByte(researchCreateForm.sur_title.value) > 200){
			alert("제목의 글자수가 초과 되었습니다.");
			//researchCreateForm.sur_title.value = researchCreateForm.sur_title.value.cut(120);
			researchCreateForm.sur_title.focus();
			return;
		}
		
		researchCreateForm.submit();
	}
	
	function getStrByte(str) {
		var p, len = 0;
		for(p=0; p<str.length; p++) {
			(str.charCodeAt(p) > 255) ? len+=3 : len++; // charCodeAt(문자열) - 문자열을 유니코드값으로 변환하여 255보다 크면 한글.
		}
		return len;
	} // 문자열의 byte수를 구하는 함수 - 한글이라면 글자당 2bytes, 그외에는 1byte로 계산한다.
	
	String.prototype.cut = function(len) {
	    var str = this;
	    var l = 0;
	    for (var i=0; i<str.length; i++) {
	            l += (str.charCodeAt(i) > 255) ? 3 : 1;
	            if (l > len) return str.substring(0,i);
	    }
	    return str;
	}; // 문자열을 잘라주는 함수 - 원하는 byte수만큼 잘라준다
	
	$(document).ready(function(){
		$('.suri1').show();
		
		$('#que_cnt').change(function(){
			var sel = $('#que_cnt option:selected').text();
			var cnt=0;
			for(var i=0;i<=sel;i++){
				$('.suri'+i).show();
				cnt=i;
			}
			for(var j=cnt;j<17;j++){
				$('.suri'+(j+1)).hide();
			}
		});
		
		$('#sur_sat_date').datepicker({
			dateFormat : 'yy-mm-dd',
			defaultDate : 0,
			changeMonth : true,
			changeYear : true,
			showMonthAfterYear: true ,
			yearRange: 'c-90:c+100',
			dayNamesMin : ['일','월','화','수','목','금','토'],
			monthNamesShort : ['1월','2월','3월','4월','5월','6월','7월','8월','9월','10월','11월','12월'],
			showAnim : 'slideDown',
		});
		
		$('#sur_end_date').datepicker({
			dateFormat : 'yy-mm-dd',
			defaultDate : 0,
			changeMonth : true,
			changeYear : true,
			showMonthAfterYear: true ,
			yearRange: 'c-90:c+100',
			dayNamesMin : ['일','월','화','수','목','금','토'],
			monthNamesShort : ['1월','2월','3월','4월','5월','6월','7월','8월','9월','10월','11월','12월'],
			showAnim : 'slideDown',
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
				
				<form name="researchCreateForm" action="/foodSen/researchCreate.do" method="post">
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
								<td colspan="5" class="tl"><input type="text" id="sur_title" name="sur_title" class="inp" /></td>
							</tr>
							<tr>
								<th>시작일</th>
								<td class="tl"><input id="sur_sat_date" name="sur_sat_date" class="inp" style="width:100px;" readonly/></td>
								<th>종료일</th>
								<td class="tl"><input id="sur_end_date" name="sur_end_date" class="inp" style="width:100px;" readonly/></td>
								<th>문항수</th>
								<td class="tl">
									<select id="que_cnt" name="que_cnt">
										<option value="1"/>1</option>
										<option value="2"/>2</option>
										<option value="3"/>3</option>
										<option value="4"/>4</option>
										<option value="5"/>5</option>
										<option value="6"/>6</option>
										<option value="7"/>7</option>
										<option value="8"/>8</option>
										<option value="9"/>9</option>
										<option value="10"/>10</option>
										<option value="11"/>11</option>
										<option value="12"/>12</option>
										<option value="13"/>13</option>
										<option value="14"/>14</option>
										<option value="15"/>15</option>
										<option value="16"/>16</option>
									</select>
								</td>
							</tr>
							<tr>
								<th>내용</th>
								<td colspan="5" class="tl">
								
									<c:forEach var="q" begin="1" end="16" step="1" varStatus="i">
					       				<div class="research suri${i.index}" style="display: none">
					       				
					               	   		<!--문제-->
					      					<p>${i.index }. <input type="text" id="surq_title${i.index}" name="surq_title${i.index}" class="inp"/></p>
					      					<!--.//문제-->
					      					
					      					<!--문항-->
					                        <ul>
						                        <li>① <input type="text" id="item1${i.index}" name="item1${i.index}" class="inp i"/></li>
						                        <li>② <input type="text" id="item2${i.index}" name="item2${i.index}" class="inp i"/></li>
						                        <li>③ <input type="text" id="item3${i.index}" name="item3${i.index}" class="inp i"/></li>
						                        <li>④ <input type="text" id="item4${i.index}" name="item4${i.index}" class="inp i"/></li>
						                        <li>⑤ <input type="text" id="item5${i.index}" name="item5${i.index}" class="inp i"/></li>
					                        </ul>
					                        <!--.//문항-->
					                        
					                       	<!-- i번문제 존재유무 체크 -->
					                        <input type="hidden" id="surq_seqItem" name="surq_seqItem" value="${i.index}" />
					                        
										</div>
									</c:forEach>
								
								</td>
							</tr>
							
						</tbody>
					</table>
				</form>
				
				<p class="pt40"></p>
				
				<!--목록.등록 버튼--> 
				<span class="bbs_btn"> 
				<span class="wte_l"><a href="/foodSen/researchList.do" class="wte_r">목록</a></span> 
				<span class="per_l"><a href="javascript:goCreate()" class="pre_r">저장</a></span>
				</span> 
				<!--.//목록.등록 버튼--> 
			         
			</div>
		</div>
	</div>
<p class="bottom_bg"></p>
</div>


<jsp:include page="/view/include/footer.jsp"/>