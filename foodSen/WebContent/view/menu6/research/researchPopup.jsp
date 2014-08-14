<%@ page language="java" contentType="text/html; charset=EUC-KR"
    pageEncoding="EUC-KR"%>
<link href="./css/base.css" rel="stylesheet" type="text/css" />
<link href="./css/common.css" rel="stylesheet" type="text/css" />
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt_rt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>    

<script type="text/javascript" src="http://code.jquery.com/jquery-1.10.2.min.js"></script>
<script type="text/javascript" src="https://www.google.com/jsapi"></script>
<script type="text/javascript">
	/* 
	//Load the Visualization API and the piechart package.
	google.load('visualization', '1.0', {'packages':['corechart']});
	
	//Set a callback to run when the Google Visualization API is loaded.
	google.setOnLoadCallback(drawChart);
	
	//Callback that creates and populates a data table,
	//instantiates the pie chart, passes in the data and
	//draws it.
	function drawChart()
	{
		var cnt = "${cnt-1}";
		var i = 0;
		var i_title1 = "${i_title1}";

		
		for(i=0; i<cnt; i++){
			
			var item1 = "${i_title1[i]}"
			var item2 = "${i_title2[i]}";
			var item3 = "${i_title3[i]}";
			var item4 = "${i_title4[i]}";
			var item5 = "${i_title5[i]}";
			
			var item1Cnt = "${res_cnt_arr[i][0]}";
			var item2Cnt = "${res_cnt_arr[i][1]}";
			var item3Cnt = "${res_cnt_arr[i][2]}";
			var item4Cnt = "${res_cnt_arr[i][3]}";
			var item5Cnt = "${res_cnt_arr[i][4]}";
			
			
			// Create the data table.
			var data = new google.visualization.DataTable();
			data.addColumn('string', 'Topping');
			data.addColumn('number', 'Slices');
			data.addRows([
			['1��', 1],
			['2��', 2],
			['3��', 3],
			['4��', 4],
			['5��', 5]
			]);
		
			// Set chart options
			var options =
			{
				'title': ''+(i+1)+'��° ���׿� ���� ���',
				'width':400,
				'height':300
			};
		
			// Instantiate and draw our chart, passing in some options.
			var chart = new google.visualization.PieChart(document.getElementById('chart_div'+i));
			chart.draw(data, options);
			
		}
		
	}
	 */
	
	//�̰� ������ �߰�
	//�̰� �Ʒ��� ����
	
	
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
			(str.charCodeAt(p) > 255) ? len+=2 : len++; // charCodeAt(���ڿ�) - ���ڿ��� �����ڵ尪���� ��ȯ�Ͽ� 255���� ũ�� �ѱ�.
		}
		return len;
	} // ���ڿ��� byte���� ���ϴ� �Լ� - �ѱ��̶�� ���ڴ� 2bytes, �׿ܿ��� 1byte�� ����Ѵ�.
	
	String.prototype.cut = function(len) {
	    var str = this;
	    var l = 0;
	    for (var i=0; i<str.length; i++) {
	            l += (str.charCodeAt(i) > 255) ? 2 : 1;
	            if (l > len) return str.substring(0,i) + "...";
	    }
	    return str;
	}; // ���ڿ��� �߶��ִ� �Լ� - ���ϴ� byte����ŭ �߶��ְ� '...'�� �ٿ��ش�
</script>

<!-- w100% h545px -->
<div class="pop">
	<div class="pop_box" style="overflow-y: scroll; height: 500px;">
		
		<!-- ���� -->
		<h1>${resultClass.sur_title}</h1>
		<!-- .//���� -->
		
		<!-- ����ڽ� -->
		<div class="pop_list">
		
			<!-- ��°��� -->
			<div class="research_box1">
				
				<table width="100%">
				
					<colgroup>
						<col width="5%"/>
						<col width="50%"/>
						<col width="5%"/>
						<col width="%"/>
					</colgroup>
					
					<c:forEach var="i" begin="0" end="${cnt-1}" step="1">
						<tr>
							<td colspan="4">
								<h2>${i+1}. ${title[i]} </h2>
							</td>
						</tr>
						
						<%-- 
						<!-- ��Ʈ��°��� -->
						<tr><td>
						<div id="chart_div${i}"></div>
						</td></tr>
						 --%>
						
						<!-- ������°��� -->
						<tr>
							<td class="tl">��</td>
							<td class="tl"><img alt="${i_title1[i]}" src="./images/graph/bar1.JPG" width="${res_cnt_arr[i][0] * 10}px" height="10px"></td>
							<td>&nbsp;${res_cnt_arr[i][0]}</td>
							<td><span class="title">�� ${i_title1[i]}</span></td>
						</tr>
						<tr>
							<td class="tl">��</td>
							<td class="tl"><img alt="${i_title2[i]}" src="./images/graph/bar2.JPG" width="${res_cnt_arr[i][1] * 10}px" height="10px"></td>
							<td>&nbsp;${res_cnt_arr[i][1]}</td>
							<td><span class="title">�� ${i_title2[i]}</span></td>
						</tr>
						<tr>
							<td class="tl">��</td>
							<td class="tl"><img alt="${i_title3[i]}" src="./images/graph/bar3.JPG" width="${res_cnt_arr[i][2] * 10}px" height="10px"></td>
							<td>&nbsp;${res_cnt_arr[i][2]}</td>
							<td><span class="title">�� ${i_title3[i]}</span></td>
						</tr>
						<tr>
							<td class="tl">��</td>
							<td class="tl"><img alt="${i_title4[i]}" src="./images/graph/bar4.JPG" width="${res_cnt_arr[i][3] * 10}px" height="10px"></td>
							<td>&nbsp;${res_cnt_arr[i][3]}</td>
							<td><span class="title">�� ${i_title4[i]}</span></td>
						</tr>
						<tr>
							<td class="tl">��</td>
							<td class="tl"><img alt="${i_title5[i]}" src="./images/graph/bar5.JPG" width="${res_cnt_arr[i][4] * 10}px" height="10px"></td>
							<td>&nbsp;${res_cnt_arr[i][4]}</td>
							<td><span class="title">�� ${i_title5[i]}</span></td>
						</tr>
						<tr><td colsapn="4"><br/></td></tr>
						<!-- .//������°��� -->
						
					</c:forEach>
					
				</table>
				
			</div>
			<!-- .//��°��� -->
		    
		</div>
		<!-- ����ڽ� -->
		
		<p class="pt20"></p>
		<div class="pop_btn">
			<span class="blue_l"><a href="javascript:self.close()" class="blue_r">Ȯ��</a></span>
		</div>
    	
	</div>
</div>