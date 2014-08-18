<%@ page language="java" contentType="text/html; charset=EUC-KR"
    pageEncoding="EUC-KR"%>
<link href="./css/base.css" rel="stylesheet" type="text/css" />
<link href="./css/common.css" rel="stylesheet" type="text/css" />
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt_rt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>    

<script type="text/javascript" src="http://code.jquery.com/jquery-1.10.2.min.js"></script>

<script src="http://code.highcharts.com/highcharts.js"></script>
<script src="http://code.highcharts.com/modules/data.js"></script>
<script src="http://code.highcharts.com/modules/drilldown.js"></script>

<script type="text/javascript" src="https://www.google.com/jsapi"></script>
<script type="text/javascript">
	
	google.load("visualization", "1", {packages:["corechart"]});
	google.setOnLoadCallback(drawVisualization);
	
	function drawVisualization(){
		var cnt = "${cnt}"; //문제개수
		var title = new Array();
		var i_title1 = new Array();
		var i_title2 = new Array();
		var i_title3 = new Array();
		var i_title4 = new Array();
		var i_title5 = new Array();
		
		var res_cnt_arr1 = new Array();
		var res_cnt_arr2 = new Array();
		var res_cnt_arr3 = new Array();
		var res_cnt_arr4 = new Array();
		var res_cnt_arr5 = new Array();
		
		for(var i = 0; i<cnt; i++){
			title[i] = document.getElementById("title"+i).value;
			i_title1[i] = document.getElementById("i_title1"+i).value;
			i_title2[i] = document.getElementById("i_title2"+i).value;
			i_title3[i] = document.getElementById("i_title3"+i).value;
			i_title4[i] = document.getElementById("i_title4"+i).value;
			i_title5[i] = document.getElementById("i_title5"+i).value;
			
			res_cnt_arr1[i] = document.getElementById("res_cnt_arr1"+i).value;
			res_cnt_arr2[i] = document.getElementById("res_cnt_arr2"+i).value;
			res_cnt_arr3[i] = document.getElementById("res_cnt_arr3"+i).value;
			res_cnt_arr4[i] = document.getElementById("res_cnt_arr4"+i).value;
			res_cnt_arr5[i] = document.getElementById("res_cnt_arr5"+i).value;
		
			/* 
			alert(res_cnt_arr1[0]);
			alert(res_cnt_arr2[0]);
			alert(res_cnt_arr3[0]);
			alert(res_cnt_arr4[0]);
			alert(res_cnt_arr5[0]);
			 */
		
			var data = google.visualization.arrayToDataTable([
				['title', i_title1[i], i_title2[i], i_title3[i], i_title4[i], i_title5[i]],
				[title[i], 11, 22, 33, 44, 55],
			]);
			
			var options = {
				title : (i+1)+'. 문제 : '+title[i],
				vAxis: {title: "선택횟수"},
				hAxis: {title: "문항"},
				seriesType: "bars",
				series: {5: {type: "line"}}
			};
			
			var chart = new google.visualization.ComboChart(document.getElementById('chart_div'+i));
			chart.draw(data, options);
		}
	}

	
	//이곳 위쪽은 추가
	//이곳 아래는 원본
	
	
	$(document).ready(function(){
		var i=40;
		$(".title").each(function(index, item){		
			if(getStrByte($(item).text())>25){
				$(item).html($(item).text().cut(25));
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

<!-- w100% h545px -->
<div class="pop">
	<div class="pop_box" style="overflow-y: scroll; height: 500px;">
		
		<!-- 제목 -->
		<h1>${resultClass.sur_title}</h1>
		<!-- .//제목 -->
		
		<!-- 결과박스 -->
		<div class="pop_list">
		
			<!-- 출력공간 -->
			<div class="research_box1">
				
				<table width="100%">
				
					<colgroup>
						<col width="5%"/>
						<col width="50%"/>
						<col width="5%"/>
						<col width="%"/>
					</colgroup>
					
					<c:forEach var="i" begin="0" end="${cnt-1}" step="1">
						
						<!-- 차트출력을 위한 hidden값 초기화 -->
						<input type="hidden" id="title${i}" name="title" class="txt" value="${title[i]}">
						
						<input type="hidden" id="i_title1${i}" name="i_title1" class="txt" value="${i_title1[i]}">
						<input type="hidden" id="i_title2${i}" name="i_title2" class="txt" value="${i_title2[i]}">
						<input type="hidden" id="i_title3${i}" name="i_title3" class="txt" value="${i_title3[i]}">
						<input type="hidden" id="i_title4${i}" name="i_title4" class="txt" value="${i_title4[i]}">
						<input type="hidden" id="i_title5${i}" name="i_title5" class="txt" value="${i_title5[i]}">
						
						<input type="hidden" id="res_cnt_arr1${i}" name="res_cnt_arr" class="txt" value="${res_cnt_arr[i][0]}">
						<input type="hidden" id="res_cnt_arr2${i}" name="res_cnt_arr" class="txt" value="${res_cnt_arr[i][1]}">
						<input type="hidden" id="res_cnt_arr3${i}" name="res_cnt_arr" class="txt" value="${res_cnt_arr[i][2]}">
						<input type="hidden" id="res_cnt_arr4${i}" name="res_cnt_arr" class="txt" value="${res_cnt_arr[i][3]}">
						<input type="hidden" id="res_cnt_arr5${i}" name="res_cnt_arr" class="txt" value="${res_cnt_arr[i][4]}">
						<!-- .//차트출력을 위한 hidden값 초기화 -->
						
						
						<!-- 차트출력공간 -->
						<tr><td>
							<div id="chart_div${i}" style="width: 900px; height: 500px;"></div>
						</td></tr>
						<!-- .//차트출력공간 -->
						
						
						<%-- 
						<!-- 기존출력공간 -->
						<tr>
							<td colspan="4">
								<h2>${i+1}. ${title[i]} </h2>
							</td>
						</tr>
						<tr>
							<td class="tl">①</td>
							<td class="tl"><img alt="${i_title1[i]}" src="./images/graph/bar1.JPG" width="${res_cnt_arr[i][0] * 10}px" height="10px"></td>
							<td>&nbsp;${res_cnt_arr[i][0]}</td>
							<td><span class="title">① ${i_title1[i]}</span></td>
						</tr>
						<tr>
							<td class="tl">②</td>
							<td class="tl"><img alt="${i_title2[i]}" src="./images/graph/bar2.JPG" width="${res_cnt_arr[i][1] * 10}px" height="10px"></td>
							<td>&nbsp;${res_cnt_arr[i][1]}</td>
							<td><span class="title">② ${i_title2[i]}</span></td>
						</tr>
						
						<c:if test="${i_title3[i] != null}">
							<tr>
								<td class="tl">③</td>
								<td class="tl"><img alt="${i_title3[i]}" src="./images/graph/bar3.JPG" width="${res_cnt_arr[i][2] * 10}px" height="10px"></td>
								<td>&nbsp;${res_cnt_arr[i][2]}</td>
								<td><span class="title">③ ${i_title3[i]}</span></td>
							</tr>
						</c:if>
						
						<c:if test="${i_title4[i] != null}">
							<tr>
								<td class="tl">④</td>
								<td class="tl"><img alt="${i_title4[i]}" src="./images/graph/bar4.JPG" width="${res_cnt_arr[i][3] * 10}px" height="10px"></td>
								<td>&nbsp;${res_cnt_arr[i][3]}</td>
								<td><span class="title">④ ${i_title4[i]}</span></td>
							</tr>
						</c:if>
						
						<c:if test="${i_title5[i] != null}">	
							<tr>
								<td class="tl">⑤</td>
								<td class="tl"><img alt="${i_title5[i]}" src="./images/graph/bar5.JPG" width="${res_cnt_arr[i][4] * 10}px" height="10px"></td>
								<td>&nbsp;${res_cnt_arr[i][4]}</td>
								<td><span class="title">⑤ ${i_title5[i]}</span></td>
							</tr>
						</c:if>
						
						<tr><td colsapn="4"><br/></td></tr>
						<!-- .//기존출력공간 -->
						 --%>
						
					</c:forEach>
					
				</table>
				
			</div>
			<!-- .//출력공간 -->
		    
		</div>
		<!-- 결과박스 -->
		
		<p class="pt20"></p>
		<div class="pop_btn">
			<span class="blue_l"><a href="javascript:self.close()" class="blue_r">확인</a></span>
		</div>
    	
	</div>
</div>