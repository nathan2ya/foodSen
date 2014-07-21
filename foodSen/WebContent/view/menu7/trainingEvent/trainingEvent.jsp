<%@ page language="java" contentType="text/html; charset=EUC-KR"
    pageEncoding="EUC-KR" import="java.util.*"%>
    
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt_rt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<jsp:include page="../../include/top.jsp"/>
<script type="text/javascript" src="http://code.jquery.com/jquery-1.10.2.min.js"></script>
<script type="text/javascript">

	var Current_Date = new Date();
	var Year = Current_Date.getFullYear();
	var Month = Current_Date.getMonth();
	
	var Days_in_Month = new Array(31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31);
	//var holyday = new Array("0101","0130", "0131","0201","0301", "0505", "0606", "0815", "1003", "1225");
	//var holystr = new Array("����", "", "����", "", "������", "��̳�", "������", "������", "��õ��", "��ź��");
	var txt_date="";
	var txt_seq="";

	function memorialDay(mday, name, year){
		this.mday = mday;
		this.name = name;
		this.year = year;
	}


/* 
	var holyday = Array (
		new memorialDay("0101", "����","0"),
		new memorialDay("0130", "","2014"),
		new memorialDay("0131", "����","2014"),
		new memorialDay("0201", "","2014"),
		new memorialDay("0301", "������","0"),
		new memorialDay("0505", "��̳�","0"),
		new memorialDay("0506", "����ź����","2014"),
		new memorialDay("0606", "������","0"),
		new memorialDay("0815", "������","0"),
		new memorialDay("0907", "","2014"),
		new memorialDay("0908", "�߼�","2014"),
		new memorialDay("0909", "","2014"),
		new memorialDay("1003", "��õ��","0"),
		new memorialDay("1009", "�ѱ۳�","2014"),
		new memorialDay("1225", "��ź��","0")
	); 
*/
	
	
	$(document).ready(function(){
		var cnt = $("#count").val();
		
		for(var a=0; a<cnt;a++){
			var eventarr = $("#date"+a).val();
			var seqarr = $("#seq"+a).val();
			txt_date += eventarr+"|";
			txt_seq += seqarr+"|";
		}
		var arr = txt_date.split('|');
		var sarr = txt_seq.split('|');
		for(var b=0;b<arr.length;b++){
			arr[b] = arr[b].split(':');
			sarr[b] = sarr[b].split(':');
		}
		var size1 = sarr.length-1;
		var size2 = arr.length-1;
		arr.splice(size2,1);
		sarr.splice(size1,1);
		Make_Calender(Year, Month);
		EventView(arr, sarr);
	});


	//arr  =  2���� �迭�� arr[][0]���� ��� ������, arr[][1]���� ��� �������� ����.
	//sarr =  2���� �迭�� sarr[][0]���� �ش� ����� seq, sarr[][1]���� �ش� ����� ������ ����.
	//���������� ��¥�� ���� �ϰ� �޷��� �׸��� ���Ե� <div>�±� �ȿ� �̺�Ʈ ������ �־��ش�.
	function EventView(arr, sarr){//�޷¿� ������ ������ ����� ���
		
		var to_month="";
		if((Month+1)<10){
			to_month = Year+"0"+(Month+1);
		}else{
			to_month = Year+""+(Month+1);
		}
		var date_sub = new Array(); //yyyyMMdd �������� 
		var date_ym = new Array();  //yyyyMM
		var str_end_sub = new Array();  //�����ϰ� �������� �Ǵ��ϱ� ���� ��¥ ����
		var str_end = new Array();  //�����ϰ� ������ ����
		//arr �� -�� ���� ���� date_sub�� �־��ش�.
		for(var j=0; j<arr.length;j++){
			date_sub[j] = new Array(2);
			date_ym[j] = new Array(2);
			str_end_sub[j] = new Array(2);
			for(var k=0;k<arr[j].length;k++){
				date_sub[j][k] = arr[j][k].replace(/-/g,''); //arr�� ��¥�� yyyy-MM-dd�̹Ƿ� -�� ���� �Ѵ�.
				if(date_sub[j][k].substring(6,7)==0){// -�� ������ ��¥�� 10�� ������ ��¥�� �տ� 0�� �� �ֱ� ������ 0�� �����Ѵ�. 
					str_end_sub[j][k] = date_sub[j][k].substring(7,8);
				}else{
					str_end_sub[j][k] = date_sub[j][k].substring(6,8);
				}
				date_ym[j][k] = date_sub[j][k].substring(0,6);
			}
		}
		
		
		
		for(var a=0; a<date_ym.length;a++){
			str_end[a] = new Array(2);
			for(var b=0; b<date_ym[0].length;b++){
				if(b==0){
					if(date_ym[a][b] < to_month){//data_ym�� ����Ƚ������� ������ ������� ������� 1�� ���� �����ϵ��� �Ѵ�.
						str_end[a][b] = "1";
					}else{
						str_end[a][b] = str_end_sub[a][b];
					}
				}else{
					if(date_ym[a][b] > to_month){//date_ym�� �����  �������� ������ ������� Ŭ��� �ش���� ������ ������ ǥ���ϵ��� �Ѵ�.
						str_end[a][b] = Days_in_Month[Month];
					}else{
						str_end[a][b] = str_end_sub[a][b];
					}
				}
			}
		}
	 
	 
		
		
		for(var c=1;c<eval(Days_in_Month[Month]+"+"+1);c++){
			var ev ='<ul>';
			var check=1;
			var cnt=0;
			for(var d=0;d<str_end.length;d++){
				
				if(cnt==3){
					check = 2;
				}
				
				if(check==1){
					if((str_end[d][0]<=c && str_end[d][1]>=c)){
						var str="javascript:goView('"+sarr[d][0]+"')";
						//alert(str);
						//alert(c+"�Ͽ� ���� ����"+str_end[d][0]+"    "+str_end[d][1]);
						var len = getStrByte(sarr[d][1]);
						var title ="";
						if(len>14){
							title = sarr[d][1].substring(0,8)+"..";
						}else{
							title = sarr[d][1];
						}
						ev+="<li class='txt'><a href="+str+">"+title+"</a></li>";
						cnt++;
						
					};
				} else if(check==2){
					cnt++;
					check=3;
					ev+='<a href="trainingEventList.do"><img src="./images/sub/notice/btn_add.gif" alt="������" /></a>';
				} else{
					
				}
			}
			ev+='</ul>';
			$('#event'+c).html(ev);
		};
		
	}
	
	function getStrByte(str) {
		var p, len = 0;
		for(p=0; p<str.length; p++) {
			(str.charCodeAt(p) > 255) ? len+=2 : len++; // charCodeAt(���ڿ�) - ���ڿ��� �����ڵ尪���� ��ȯ�Ͽ� 255���� ũ�� �ѱ�.
		}
		return len;
	}
	
	function changearr(txt_date, txt_seq){
		var arr = txt_date.split('|');
		var sarr = txt_seq.split('|');
		for(var b=0;b<arr.length;b++){
			arr[b] = arr[b].split(':');
			sarr[b] = sarr[b].split(':');
		}
		var size1 = sarr.length-1;
		var size2 = arr.length-1;
		arr.splice(size2,1);
		sarr.splice(size1,1);
		Make_Calender(Year, Month);
		EventView(arr, sarr);
	}
	
	function goView(seq){
		$("#seq").val(seq);
		$("#view").submit();
	}
	
	function getMonthEvent(){
		$.ajax({
			type: 'post',
			url: 'trainingEventMonth.do',
			dataType:"json",
			contentType: "application/x-www-form-urlencoded; charset=UTF-8", 
			data: {
				"year":Year,
				"month":Month+1
				},
			success:function(json){
				var date="";
				var seq="";
				$(json.list).each(function(index, data){
					date+= data.str_date+":"+data.end_date+"|";
					seq+= data.seq+":"+data.title+"|";
				});
				changearr(date, seq);
			},
			error:function(request,status,error){
		        //alert("code:"+request.status+"\n"+"message:"+request.responseText+"\n"+"error:"+error);
		      }
		});
	}
	
	function nextMonth(){ //���� �޷� �Ѿ�� ���
		Month = Month+1;
		if(Month>11){
			Year = Year+1;
			Month = 0;
		}
		//Make_Calender(Year, Month);
		getMonthEvent();
	}
	
	function prevMonth(){ //�����޷� �Ѿ�� ���
		Month = Month-1;
		if(Month<0){
			Year = Year-1;
			Month = 11;
		}
		getMonthEvent();
	}
	
	function Header(Year, Month) {
		   if (Month == 1) {
		   Days_in_Month[1] = ((Year % 400 == 0) || ((Year % 4 == 0) && (Year % 100 !=0))) ? 29 : 28;
		   }
		   var Header_String = "<a href='javascript:prevMonth()'><img src='./images/sub/notice/btn_a.gif' alt='�����⵵' /></a> "+Year + "�� "+(Month+1)+"��"+" <a href='javascript:nextMonth()'><img src='./images/sub/notice/btn_b.gif' alt='�����⵵' /></a> ";
		   return Header_String;
		}
	
	function Make_Calender(Year, Month) {
		var Rows;
		var First_Date = new Date(Year, Month, 1);
		var Heading = Header(Year, Month);
		var First_Day = First_Date.getDay() + 1;
		if (((Days_in_Month[Month] == 31) && (First_Day >= 6)) ||
			((Days_in_Month[Month] == 30) && (First_Day == 7))) {
			Rows = 6;
		}
		else if ((Days_in_Month[Month] == 28) && (First_Day == 1)) {
			Rows = 4;
		}
		else {
			Rows = 5;
		}
		
		var msg = '<table width="100%" border="0" cellspacing="0" cellpadding="0" class="tbl_type03" summary="����">';
		msg += '<caption>����</caption><colgroup><col width="15%"/><col width="14%"/><col width="14%"/><col width="14%"/><col width="14%"/><col width="14%"/>';
		msg += '<col width="*%"/></colgroup><thead><tr><th colspan="7">'+Heading+'</th></tr></thead>';
		msg += '<tbody><tr><th><span class="c_ff2d2d">��</span></th><th>��</th><th>ȭ</th><th>��</th><th>��</th><th>��</th><th><span class="c_6391c5">��</span></th></tr>';
		
		var Day_Counter = 1;
		var Loop_Counter = 1;
		for (var j = 1; j <= Rows; j++) {
		   msg += '<tr>';
		   for (var i = 1; i < 8; i++) {
			   var day = new Date(Year,Month,Day_Counter).getDay();
			   //alert(Year+"��"+Month+"��"+Day_Counter+"��"+day);
			   if ((Loop_Counter >= First_Day) && (Day_Counter <= Days_in_Month[Month])) {
				   var mon = Month+1;
				   var flag = false;
				   var holy = "";
				   if(mon < 10){
					   mon = "0"+mon;
				   }
				   var hday = Day_Counter;
				   if(hday<10){
					   hday = "0"+hday;
				   }
				   
				   /* for(var x=0;x<holyday.length;x++){
					   var instant = mon+""+hday;
					   
					   if(instant == holyday[x].mday && (holyday[x].year=="0"|| holyday[x].year==Year)){
						   holy = Day_Counter+"  "+holyday[x].name;
						   flag = true;
					   }
				   } */
				   
				   
				   if(flag == false){
					   if(day ==0){
							msg += "<td><span class='c_ff2d2d'>"+Day_Counter + "</span><div id='event"+Day_Counter+"'></div></td>";
					   }else if(day == 6){
						   msg += "<td><span class='c_6391c5'>"+Day_Counter + "</span><div id='event"+Day_Counter+"'></div></td>";
					   }else{
						   msg += "<td>"+Day_Counter + "<div id='event"+Day_Counter+"'></div></td>";
					   }
					   
				   }else{
					   msg += "<td><span class='c_ff2d2d'>"+holy + "</span><div id='event"+Day_Counter+"'></div></td>";
				   }
					Day_Counter++;    
				}else {
					msg += '<td></td>';
				}
				Loop_Counter++;
			}
		   msg += '</tr>';
		}
	   msg += '</tbody></table>';
	   $("#month").html(msg);
	}
	
</script>
</head>


<div id="container">
	<div id="contents">
	
		<p><img src="./images/sub/info/sub_vimg_01.jpg" alt="�ǰ��� �޽� �ູ�� �б�" /></p>
		
		
		<!-- �����޴� -->
		<jsp:include page="/view/include/menu7/trainingEventLnb.jsp"/>
		<!-- .//�����޴� -->
		
	
		<!-- ���� ������ -->
		<div class="right_box">
			
			<!-- ������� ���� -->
			<h3><img src="./images/sub/notice/title_02.gif" alt="����.���" /></h3>
			<!-- .//������� ���� -->
			
			
			<!-- ������� ��� ���� -->
			<p class="history"><img src="./images/sub/history_home.gif" alt="home" /> �������� <img src="./images/sub/history_arrow.gif" alt="����" /> <strong>����.���</strong></p>
       		<p class="pt30"></p>
			<!-- .//������� ��� ���� -->		
			
			
			
			<!-- �Խ��ǿ��� -->
			<div class="tbl_box">
				
				
				<%-- 
				<div>
				
					<form action="trainingEventView.do" method="post" id="view" name="view">
						<input type="hidden" name="seq" id="seq"/>
					</form>
					
					<input type="hidden" id="count" name="count" value="${count}"/>
					
					<c:forEach var="a" items="${list}" varStatus="i">
						<input type="hidden" id="date${i.index}" name="date${i.index}" value="${a.str_date}:${a.end_date}">
						<input type="hidden" id="seq${i.index}" name="seq${i.index}" value="${a.seq}:${a.title}">
					</c:forEach>
					
				</div>
				 --%>
				
				
				<!-- �޷¿��� -->
				<div id="month"></div>
				<!-- .//�޷¿��� -->
				
				
         
        		<p class="pt40"></p>
         		 
         		 
         		<!-- ��� . ��� ��ư -->
				<span class="bbs_btn"> 
					<span class="wte_l">
						<a href="/foodSen/TrainingEventList.do" class="wte_r">���</a>
					</span>
					
					<!-- �������� ��츸 ��Ϲ�ư ���� -->
					<c:if test="${sessionScope.session_admin_yn == 'y'}">
						<span class="per_l">
							<a href="/foodSen/trainingEventCreateForm.do" class="pre_r">���</a>
						</span>
					</c:if>
					<!-- .//�������� ��츸 ��Ϲ�ư ���� -->
					
				</span>
				<!-- .//��� ��� ��ư -->
             
            	
			</div>
			<!-- .//�Խ��ǿ��� -->
			
          
		</div>
		<!-- .//���� ������ -->
		
		<p class="bottom_bg"></p>
		
	</div>
</div>





<form name="calender" action="trainingEventDate.do" method="post">
	<input type="hidden" id="year" name="year">
	<input type="hidden" id="month" name="month">
</form>

<form name="view" method="post" action="trainingEventView.do">
	<input type="hidden" id="seq" name="seq">
</form>

<form name="Search" method="post" action="trainingEventSearch.do">
	<input type="hidden" id="searchType" name="searchType" value="currDate">
	<input type="hidden" id="searchString" name="searchString">
</form>


<jsp:include page="../../include/footer.jsp"/>