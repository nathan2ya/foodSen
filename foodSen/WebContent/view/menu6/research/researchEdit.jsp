<%@ page language="java" contentType="text/html; charset=EUC-KR"
    pageEncoding="EUC-KR"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt_rt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<jsp:include page="../../include/top.jsp"/>
<script type="text/javascript" src="http://code.jquery.com/jquery-1.10.2.min.js"></script>
<script type="text/javascript" src="http://code.jquery.com/ui/1.10.3/jquery-ui.js"></script>
<link rel="stylesheet" type="text/css" href="http://code.jquery.com/ui/1.10.3/themes/smoothness/jquery-ui.css">

<script type="text/javascript">

	function goDelete(){
		if(confirm("게시글을 삭제하시겠습니까?")!=0){
			deleteOK.submit();
		}else{
			return;
		}
	}

	function goEdit(){
		if(!editOK.sur_title.value){
			alert("제목을 입력하세요.");
			editOK.sur_title.focus();
			return;
		}
		if(!editOK.sur_sat_date.value){
			alert("시작날짜를 선택하세요.");
			editOK.sur_sat_date.focus();
			return;
		}
		
		if(!editOK.sur_end_date.value){
			alert("종료날짜를 선택하세요.");
			editOK.sur_end_date.focus();
			return;
		}
		
		if(editOK.sur_sat_date.value > editOK.sur_end_date.value){
			alert("종료날짜는 시작날짜보다 미래여야 합니다.");
			return;
		}
		
		var num = $("#que_cnt").text();
		
		for(var i=1; i<=num; i++){
			var titleTxt=document.getElementById("surq_item"+i);
			var item1=document.getElementById("item1"+i);
			var item2=document.getElementById("item2"+i);
			var item3=document.getElementById("item3"+i);
			var item4=document.getElementById("item4"+i);
			var item5=document.getElementById("item5"+i);
			
			if(titleTxt.value == ""){
				alert("문제"+i+"번을 입력하세요.");
				titleTxt.focus();
				return;
			}
			
			if(item1.value == ""){
				alert("문제"+i+"번의 1번 항목을 입력하세요.");
				item1.focus();
				return;
			}
			
			if(item2.value == ""){
				alert("문제"+i+"번의 2번 항목을 입력하세요.");
				item2.focus();
				return;
			}
			
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
		
		if(getStrByte(editOK.sur_title.value) > 200){
			alert("제목의 글자수가 초과 되었습니다.");
			//editOK.sur_title.value = editOK.sur_title.value.cut(120);
			editOK.sur_title.focus();
			return;
		}
		
		editOK.submit();
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
		var cnt = "${cnt}";
		
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
			for(var k=0; k<17; k++){
				$('#surq_title'+k).val("");
			}
			
			$('.i').each(function(index, item){
				$(item).val("");
			});
		});
		if(cnt <1){
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
		}
		
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

  <!-- container-->
  <div id="container">
    <div id="contents">
      <h2>메인내용</h2>      
      	<p><img src="./images/sub/particiation/sub_vimg_01.jpg" alt="건강한 급식 행복한 학교" /></p>
      <jsp:include page="../../include/particiation/researchLnb.jsp"/>
      
      <div class="right_box">
        <h3><img src="./images/sub/particiation/title_05.gif" alt="설문조사" /></h3>
        <p class="history"><img src="./images/sub/history_home.gif" alt="home" /> 참여마당 <img src="./images/sub/history_arrow.gif" alt="다음" /> <strong>설문조사</strong></p>
        <p class="pt30"></p>

	        <div class="tbl_box">
	        <form name="editOK" action="researchEditOK.do" method="post">
	         <input type="hidden" id="sur_seq" name="sur_seq" class="inp" value="${voi.sur_seq }"/>
	         <table width="100%" border="0" cellspacing="0" cellpadding="0" class="tbl_type01" summary="설문조사">
	            <caption>
	            	설문조사
	            </caption>
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
	                <td colspan="5" class="tl"><input type="text" id="sur_title" name="sur_title" class="inp" value="${voi.sur_title }"<c:if test="${cnt > 0 }"> readonly</c:if>/></td>
	                </tr>
	              <tr>
	                <th>시작일</th>
	                <td class="tl"><input type="text" id="sur_sat_date" name="sur_sat_date" class="inp" style="width:100px;" value="${voi.sur_sat_date }" readonly/></td>
	                <th>종료일</th>
	                <td class="tl"><input id="sur_end_date" name="sur_end_date" class="inp" style="width:100px;" value="${voi.sur_end_date }" readonly/></td>
	              	<th>문항수</th>
	                <td class="tl" id="que_cnt" >${voi.que_cnt }</td>
	              </tr>
	              <tr>
	              <th>내용</th>
	                <td colspan="5" class="tl">
	                    <c:forEach var="vo" items="${vo }" varStatus="j">
		               	   <div class="research">
		               	   		<input type="hidden" id="surq_seqItem${j.count }" name="surq_seqItem" class="inp" value="${vo.surq_seq }"/>
		               	   		<input type="hidden" id="suri_seqItem${j.count }" name="suri_seqItem" class="inp" value="${vo.suri_seq }"/>
			                    <p>${j.count }. <input type="text" id="surq_item${j.count }" name="surq_item" class="inp" value="${vo.surq_title }" <c:if test="${cnt > 0 }"> readonly</c:if>/></p>
		                        <ul>
		                        
		                        <li>① <input type="text" id="item1${j.count }" name="item1" class="inp i" value="${vo.suri_title1 }" <c:if test="${cnt > 0 }"> readonly</c:if>/></li>
		                        <li>② <input type="text" id="item2${j.count }" name="item2" class="inp i" value="${vo.suri_title2 }" <c:if test="${cnt > 0 }"> readonly</c:if>/></li>
		                        <li>③ <input type="text" id="item3${j.count }" name="item3" class="inp i" value="${vo.suri_title3 }" <c:if test="${cnt > 0 }"> readonly</c:if>/></li>
		                        <li>④ <input type="text" id="item4${j.count }" name="item4" class="inp i" value="${vo.suri_title4 }" <c:if test="${cnt > 0 }"> readonly</c:if>/></li>
		                        <li>⑤ <input type="text" id="item5${j.count }" name="item5" class="inp i" value="${vo.suri_title5 }" <c:if test="${cnt > 0 }"> readonly</c:if>/></li>
		                        </ul>
							</div>
						</c:forEach>
	               </td>
	              </tr>
	            </tbody>
	          </table>
	          </form>
	          <p class="pt40"></p>
	          
	          <!-- btn--> 
	          <span class="bbs_btn"> 
	          <span class="wte_l"><a href="researchList.do" class="wte_r">목록</a></span> 
	          <span class="per_l"><a href="javascript:goEdit()" class="pre_r">저장</a></span>
	          <span class="wte_l"><a href="javascript:goDelete()" class="wte_r">삭제</a></span>
	          </span> 
	          <!-- //btn--> 
	          
	        </div>
	        </div>
    </div>
    <p class="bottom_bg"></p>
  </div>
  
   <form name="deleteOK" action="researchDelete.do" method="post">
 	<input type="hidden" id="sur_seq" name="sur_seq" value="${voi.sur_seq }" />
 	<input type="hidden" name="url" id="url" value="research">
 </form>
  <!-- //container-->
  <%-- <input type="hidden" id="size" name="size" value="${vo.size() }"> --%>
  
<jsp:include page="../../include/footer.jsp"/>