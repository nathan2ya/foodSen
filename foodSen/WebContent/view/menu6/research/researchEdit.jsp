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
		var current_date = "${current_date}";
		var sur_sat_date = "${resultClass.sur_sat_date}";
		var sur_end_date = "${resultClass.sur_end_date}";
		
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
		
		if(current_date < sur_sat_date){ //설문조사 진행전이면
			if(editOK.sur_sat_date.value < current_date){
				alert("설문조사 시작일은 현재일 이후만 등록가능합니다.");
				editOK.sur_sat_date.focus();
				return;
			}
		}
		
		if(editOK.sur_sat_date.value > editOK.sur_end_date.value){
			alert("시작날짜는 종료날짜보다 과거야야 합니다.");
			editOK.sur_sat_date.focus();
			return;
		}
		
		if(!editOK.sur_end_date.value){
			alert("종료날짜를 선택하세요.");
			editOK.sur_end_date.focus();
			return;
		}
		
		if(editOK.sur_end_date.value < sur_end_date){
			alert("종료날짜는 기존종료일 이후만 수정가능합니다. \n 기존종료일 "+sur_end_date+" 입니다.");
			editOK.sur_end_date.focus();
			return;
		}
		
		if(editOK.sur_sat_date.value > editOK.sur_end_date.value){
			alert("종료날짜는 시작날짜보다 미래여야 합니다.");
			editOK.sur_end_date.focus();
			return;
		}
		
		var num=document.getElementById("que_cnt").value;
		
		for(var i=1; i<=num; i++){
			
			var titleTxt = document.getElementById("surq_title"+i);
			var item1 = document.getElementById("item1"+i);
			var item2 = document.getElementById("item2"+i);
			var item3 = document.getElementById("item3"+i);
			var item4 = document.getElementById("item4"+i);
			var item5 = document.getElementById("item5"+i);
			
			if(titleTxt.value == ""){
				alert("문제"+i+"번을 입력하세요.");
				titleTxt.focus();
				return;
			}
			
			if(item1.value == ""){
				alert("문제"+i+"번의 1번 항목을 입력하세요. \n 한 문제에 최소 2개의 문항은 존재하여야 합니다.");
				item1.focus();
				return;
			}
			
			if(item2.value == ""){
				alert("문제"+i+"번의 2번 항목을 입력하세요. \n 한 문제에 최소 2개의 문항은 존재하여야 합니다.");
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
		//시작과 동시에 항목수 만큼 div show
		var sel = $('#que_cnt option:selected').text(); //항목수 선택값
		var cnt=0;
		
		for(var i=0; i<sel; i++){
			$('.suri'+i).show();
			cnt=i;
		}
		for(var j=cnt; j<17; j++){ //나머지div는 숨김
			$('.suri'+(j+1)).hide(); //div숨김
			$('#surq_title'+(j+2)).val(""); //문제값제거
			$('#item1'+(j+2)).val("");//항목1값제거
			$('#item2'+(j+2)).val("");//항목2값제거
			$('#item3'+(j+2)).val("");//항목3값제거
			$('#item4'+(j+2)).val("");//항목4값제거
			$('#item5'+(j+2)).val("");//항목5값제거
		}
		
		//항목수를 선택할때마다 suri1~16 div show // hide
		$('#que_cnt').change(function(){
			var sel = $('#que_cnt option:selected').text(); //항목수 선택값
			var cnt=0;
			
			
			for(var i=0; i<sel; i++){
				$('.suri'+i).show();
				cnt=i;
			}
			for(var j=cnt; j<17; j++){ //나머지div는 숨김
				$('.suri'+(j+1)).hide(); //div숨김
				$('#surq_title'+(j+2)).val(""); //문제값제거
				$('#item1'+(j+2)).val("");//항목1값제거
				$('#item2'+(j+2)).val("");//항목2값제거
				$('#item3'+(j+2)).val("");//항목3값제거
				$('#item4'+(j+2)).val("");//항목4값제거
				$('#item5'+(j+2)).val("");//항목5값제거
			}
			
			/* 
			//1~16까지 제목을 지움
			for(var k=1; k<17; k++){
				$('#surq_title'+k).val("");
			}
			 */
			 
			$('.i').each(function(index, item){
				$(item).val("");
			});
		});
		
		//행사시작일 달력
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
		
		//행사종료일 달력
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
			
				<form name="editOK" action="/foodSen/researchEdit.do" method="post">
				
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
								<td colspan="5" class="tl"><input type="text" id="sur_title" name="sur_title" class="inp" value="${resultClass.sur_title}"/></td>
							</tr>
							<tr>
								<th>시작일</th>
								<td class="tl">
									<c:if test="${current_date < resultClass.sur_sat_date}">
										<input type="text" id="sur_sat_date" name="sur_sat_date" class="inp" style="width:100px;" value="${resultClass.sur_sat_date}" readonly/>
									</c:if>
									<c:if test="${resultClass.sur_sat_date <= current_date && current_date <= resultClass.sur_end_date}">
										${resultClass.sur_sat_date}
										<input type="hidden" id="sur_sat_date" name="sur_sat_date" class="inp" style="width:100px;" value="${resultClass.sur_sat_date}" readonly/>
									</c:if>
								</td>
								<th>종료일</th>
								<td class="tl">
									<input id="sur_end_date" name="sur_end_date" class="inp" style="width:100px;" value="${resultClass.sur_end_date}" readonly/>
								</td>
								<th>문항수</th>
								<td class="tl">
									<c:if test="${resultClass.que_cnt == 1}">
										<select id="que_cnt" name="que_cnt">
											<option value="1" selected />1</option>
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
									</c:if>
									<c:if test="${resultClass.que_cnt == 2}">
										<select id="que_cnt" name="que_cnt">
											<option value="1"/>1</option>
											<option value="2" selected/>2</option>
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
									</c:if>
									<c:if test="${resultClass.que_cnt == 3}">
										<select id="que_cnt" name="que_cnt">
											<option value="1"/>1</option>
											<option value="2"/>2</option>
											<option value="3" selected />3</option>
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
									</c:if>
									<c:if test="${resultClass.que_cnt == 4}">
										<select id="que_cnt" name="que_cnt">
											<option value="1"/>1</option>
											<option value="2"/>2</option>
											<option value="3"/>3</option>
											<option value="4" selected />4</option>
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
									</c:if>
									<c:if test="${resultClass.que_cnt == 5}">
										<select id="que_cnt" name="que_cnt">
											<option value="1"/>1</option>
											<option value="2"/>2</option>
											<option value="3"/>3</option>
											<option value="4"/>4</option>
											<option value="5" selected />5</option>
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
									</c:if>
									<c:if test="${resultClass.que_cnt == 6}">
										<select id="que_cnt" name="que_cnt">
											<option value="1"/>1</option>
											<option value="2"/>2</option>
											<option value="3"/>3</option>
											<option value="4"/>4</option>
											<option value="5"/>5</option>
											<option value="6" selected />6</option>
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
									</c:if>
									<c:if test="${resultClass.que_cnt == 7}">
										<select id="que_cnt" name="que_cnt">
											<option value="1"/>1</option>
											<option value="2"/>2</option>
											<option value="3"/>3</option>
											<option value="4"/>4</option>
											<option value="5"/>5</option>
											<option value="6"/>6</option>
											<option value="7" selected />7</option>
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
									</c:if>
									<c:if test="${resultClass.que_cnt == 8}">
										<select id="que_cnt" name="que_cnt">
											<option value="1"/>1</option>
											<option value="2"/>2</option>
											<option value="3"/>3</option>
											<option value="4"/>4</option>
											<option value="5"/>5</option>
											<option value="6"/>6</option>
											<option value="7"/>7</option>
											<option value="8" selected />8</option>
											<option value="9"/>9</option>
											<option value="10"/>10</option>
											<option value="11"/>11</option>
											<option value="12"/>12</option>
											<option value="13"/>13</option>
											<option value="14"/>14</option>
											<option value="15"/>15</option>
											<option value="16"/>16</option>
										</select>
									</c:if>
									<c:if test="${resultClass.que_cnt == 9}">
										<select id="que_cnt" name="que_cnt">
											<option value="1"/>1</option>
											<option value="2"/>2</option>
											<option value="3"/>3</option>
											<option value="4"/>4</option>
											<option value="5"/>5</option>
											<option value="6"/>6</option>
											<option value="7"/>7</option>
											<option value="8"/>8</option>
											<option value="9" selected />9</option>
											<option value="10"/>10</option>
											<option value="11"/>11</option>
											<option value="12"/>12</option>
											<option value="13"/>13</option>
											<option value="14"/>14</option>
											<option value="15"/>15</option>
											<option value="16"/>16</option>
										</select>
									</c:if>
									<c:if test="${resultClass.que_cnt == 10}">
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
											<option value="10" selected />10</option>
											<option value="11"/>11</option>
											<option value="12"/>12</option>
											<option value="13"/>13</option>
											<option value="14"/>14</option>
											<option value="15"/>15</option>
											<option value="16"/>16</option>
										</select>
									</c:if>
									<c:if test="${resultClass.que_cnt == 11}">
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
											<option value="11" selected />11</option>
											<option value="12"/>12</option>
											<option value="13"/>13</option>
											<option value="14"/>14</option>
											<option value="15"/>15</option>
											<option value="16"/>16</option>
										</select>
									</c:if>
									<c:if test="${resultClass.que_cnt == 12}">
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
											<option value="12" selected />12</option>
											<option value="13"/>13</option>
											<option value="14"/>14</option>
											<option value="15"/>15</option>
											<option value="16"/>16</option>
										</select>
									</c:if>
									<c:if test="${resultClass.que_cnt == 13}">
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
											<option value="13" selected />13</option>
											<option value="14"/>14</option>
											<option value="15"/>15</option>
											<option value="16"/>16</option>
										</select>
									</c:if>
									<c:if test="${resultClass.que_cnt == 14}">
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
											<option value="14" selected />14</option>
											<option value="15"/>15</option>
											<option value="16"/>16</option>
										</select>
									</c:if>
									<c:if test="${resultClass.que_cnt == 15}">
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
											<option value="15" selected />15</option>
											<option value="16"/>16</option>
										</select>
									</c:if>
									<c:if test="${resultClass.que_cnt == 16}">
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
											<option value="16" selected />16</option>
										</select>
									</c:if>
								</td>
							</tr>
							<tr>
								<th>내용</th>
								<td colspan="5" class="tl">
									
									<c:forEach var="i" begin="0" end="15" step="1"> 
										<div class="research suri${i}" style="display: none">
											
											<!-- 뷰정보 -->
											<input type="hidden" id="sur_seq" name="sur_seq" value="${sur_seq}" />
											<input type="hidden" id="cnt" name="cnt" value="${cnt}" />
											<input type="hidden" id="current_date" name="current_date" value="${current_date}" />
											<input type="hidden" id="searchingNow" name="searchingNow" value="${searchingNow}" />
											<c:if test="${searchingNow == 1}">
												<input type="hidden" id="searchType" name="searchType" value="${searchType}" />
												<input type="hidden" id="userinput" name="userinput" value="${userinput}" />
											</c:if>
											<!-- .//뷰정보 -->
											
											<!-- 문제의시퀀스 -->
											<input type="hidden" id="resultClass1_seq${i+1}" name="resultClass1_seq${i+1}" value="${resultClass1_seq[i]}" />
											<!-- 문항의시퀀스 -->
											<input type="hidden" id="resultClass2_seq${i+1}" name="resultClass2_seq${i+1}" value="${resultClass2_seq[i]}" />
											
											
											<!-- 문제 -->
											<c:if test="${permit == 0}">
												<p>${i+1}. <input type="text" id="surq_title${i+1}" name="surq_title${i+1}" value="${title[i]}" /></p>
											</c:if>
											<c:if test="${permit == 1}">
												<p>${i+1}. "${title[i]}"</p>
												<input type="hidden" id="surq_title${i+1}" name="surq_title${i+1}" value="${title[i]}" />
											</c:if>
											<!-- .//문제 -->
											
											<!-- 문항 -->
											<input type="hidden" id="surq_seqItem${i+1}" name="surq_seqItem" class="txt" value="${i+1}">
											<%-- <input type="hidden" id="suri_seqItem${j.count }" name="suri_seqItem" class="inp" value="${re.suri_seq}"/> --%>
											<input type="hidden" id="surq_item${i+1}" name="surq_item" value="${title[i]}">
											
											<!-- 설문조사를 누군가가 할경우 수정할 수 있음 -->
											<c:if test="${permit == 0}">
												<ul>
													<li>① <input type="text" id="item1${i+1}" name="item1${i+1}" value="${i_title1[i]}" /> </li>
													<li>② <input type="text" id="item2${i+1}" name="item2${i+1}" value="${i_title2[i]}" /> </li>
													<li>③ <input type="text" id="item3${i+1}" name="item3${i+1}" value="${i_title3[i]}" /> </li>
													<li>④ <input type="text" id="item4${i+1}" name="item4${i+1}" value="${i_title4[i]}" /> </li>
													<li>⑤ <input type="text" id="item5${i+1}" name="item5${i+1}" value="${i_title5[i]}" /> </li>
												</ul>
											</c:if>
											<!-- 설문조사를 그누구도 하지 않았을 경우 수정못함 -> 히든값으로 java로 넘김 -->
											<c:if test="${permit == 1}">
												<ul>
													<li>① ${i_title1[i]}<input type="hidden" id="item1${i+1}" name="item1${i+1}" value="${i_title1[i]}" /> </li>
													<li>② ${i_title2[i]}<input type="hidden" id="item2${i+1}" name="item2${i+1}" value="${i_title2[i]}" /> </li>
													<li>③ ${i_title3[i]}<input type="hidden" id="item3${i+1}" name="item3${i+1}" value="${i_title3[i]}" /> </li>
													<li>④ ${i_title4[i]}<input type="hidden" id="item4${i+1}" name="item4${i+1}" value="${i_title4[i]}" /> </li>
													<li>⑤ ${i_title5[i]}<input type="hidden" id="item5${i+1}" name="item5${i+1}" value="${i_title5[i]}" /> </li>
												</ul>
											</c:if>
											<!-- .//문항 -->
											
										</div>
									</c:forEach> 
									
								</td>
							</tr>
						</tbody>
					</table>
					
				</form>

				<p class="pt40"></p>
				
				<!--버튼--> 
				<span class="bbs_btn"> 
					<span class="wte_l"><a href="researchList.do" class="wte_r">목록</a></span>
					<span class="per_l"><a href="javascript:goEdit()" class="pre_r">저장</a></span>
				</span> 
				<!--.//버튼--> 
        
			</div>
			<!--.//게시판영역 -->
			
		</div>
		<!--.//우측컨텐츠 -->
    
		<p class="bottom_bg"></p>
		
	</div>
</div>

<form name="deleteOK" action="researchDelete.do" method="post">
 	<input type="hidden" id="sur_seq" name="sur_seq" value="${resultClass.sur_seq}" />
 	<input type="hidden" name="url" id="url" value="research">
</form>

<jsp:include page="../../include/footer.jsp"/>