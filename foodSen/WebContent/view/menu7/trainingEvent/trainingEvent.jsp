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
	//var holystr = new Array("신정", "", "설날", "", "삼일절", "어린이날", "현충일", "광복절", "개천절", "성탄절");
	var txt_date="";
	var txt_seq="";

	function memorialDay(mday, name, year){
		this.mday = mday;
		this.name = name;
		this.year = year;
	}


/* 
	var holyday = Array (
		new memorialDay("0101", "신정","0"),
		new memorialDay("0130", "","2014"),
		new memorialDay("0131", "설날","2014"),
		new memorialDay("0201", "","2014"),
		new memorialDay("0301", "삼일절","0"),
		new memorialDay("0505", "어린이날","0"),
		new memorialDay("0506", "석가탄신일","2014"),
		new memorialDay("0606", "현충일","0"),
		new memorialDay("0815", "광복절","0"),
		new memorialDay("0907", "","2014"),
		new memorialDay("0908", "추석","2014"),
		new memorialDay("0909", "","2014"),
		new memorialDay("1003", "개천절","0"),
		new memorialDay("1009", "한글날","2014"),
		new memorialDay("1225", "성탄절","0")
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


	//arr  =  2차원 배열로 arr[][0]에는 행사 시작일, arr[][1]에는 행사 종료일이 들어간다.
	//sarr =  2차원 배열로 sarr[][0]에는 해당 행사의 seq, sarr[][1]에는 해당 행사의 제목이 들어간다.
	//순차적으로 날짜를 편집 하고 달력을 그릴때 포함된 <div>태그 안에 이벤트 정보를 넣어준다.
	function EventView(arr, sarr){//달력에 보여줄 정보를 만든는 기능
		
		var to_month="";
		if((Month+1)<10){
			to_month = Year+"0"+(Month+1);
		}else{
			to_month = Year+""+(Month+1);
		}
		var date_sub = new Array(); //yyyyMMdd 형식으로 
		var date_ym = new Array();  //yyyyMM
		var str_end_sub = new Array();  //시작일과 종료일을 판단하기 위해 날짜 저장
		var str_end = new Array();  //시작일과 종료일 저장
		//arr 을 -을 빼고 나서 date_sub에 넣어준다.
		for(var j=0; j<arr.length;j++){
			date_sub[j] = new Array(2);
			date_ym[j] = new Array(2);
			str_end_sub[j] = new Array(2);
			for(var k=0;k<arr[j].length;k++){
				date_sub[j][k] = arr[j][k].replace(/-/g,''); //arr의 날짜가 yyyy-MM-dd이므로 -를 제거 한다.
				if(date_sub[j][k].substring(6,7)==0){// -을 제거한 날짜의 10일 이전의 날짜는 앞에 0이 들어가 있기 때문에 0을 제거한다. 
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
					if(date_ym[a][b] < to_month){//data_ym에 저장된시작일이 지금의 년월보다 작을경우 1일 부터 시작하도록 한다.
						str_end[a][b] = "1";
					}else{
						str_end[a][b] = str_end_sub[a][b];
					}
				}else{
					if(date_ym[a][b] > to_month){//date_ym에 저장된  종료일이 지금의 년월보다 클경우 해당월의 마지막 날까지 표시하도록 한다.
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
						//alert(c+"일에 일정 있음"+str_end[d][0]+"    "+str_end[d][1]);
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
					ev+='<a href="trainingEventList.do"><img src="./images/sub/notice/btn_add.gif" alt="더보기" /></a>';
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
			(str.charCodeAt(p) > 255) ? len+=2 : len++; // charCodeAt(문자열) - 문자열을 유니코드값으로 변환하여 255보다 크면 한글.
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
	
	function nextMonth(){ //다음 달로 넘어가는 기능
		Month = Month+1;
		if(Month>11){
			Year = Year+1;
			Month = 0;
		}
		//Make_Calender(Year, Month);
		getMonthEvent();
	}
	
	function prevMonth(){ //이전달로 넘어가는 기능
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
		   var Header_String = "<a href='javascript:prevMonth()'><img src='./images/sub/notice/btn_a.gif' alt='이전년도' /></a> "+Year + "년 "+(Month+1)+"월"+" <a href='javascript:nextMonth()'><img src='./images/sub/notice/btn_b.gif' alt='다음년도' /></a> ";
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
		
		var msg = '<table width="100%" border="0" cellspacing="0" cellpadding="0" class="tbl_type03" summary="일정">';
		msg += '<caption>일정</caption><colgroup><col width="15%"/><col width="14%"/><col width="14%"/><col width="14%"/><col width="14%"/><col width="14%"/>';
		msg += '<col width="*%"/></colgroup><thead><tr><th colspan="7">'+Heading+'</th></tr></thead>';
		msg += '<tbody><tr><th><span class="c_ff2d2d">일</span></th><th>월</th><th>화</th><th>수</th><th>목</th><th>금</th><th><span class="c_6391c5">토</span></th></tr>';
		
		var Day_Counter = 1;
		var Loop_Counter = 1;
		for (var j = 1; j <= Rows; j++) {
		   msg += '<tr>';
		   for (var i = 1; i < 8; i++) {
			   var day = new Date(Year,Month,Day_Counter).getDay();
			   //alert(Year+"년"+Month+"월"+Day_Counter+"일"+day);
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
	
		<p><img src="./images/sub/info/sub_vimg_01.jpg" alt="건강한 급식 행복한 학교" /></p>
		
		
		<!-- 좌측메뉴 -->
		<jsp:include page="/view/include/menu7/trainingEventLnb.jsp"/>
		<!-- .//좌측메뉴 -->
		
	
		<!-- 우측 컨텐츠 -->
		<div class="right_box">
			
			<!-- 우측상단 제목 -->
			<h3><img src="./images/sub/notice/title_02.gif" alt="연수.행사" /></h3>
			<!-- .//우측상단 제목 -->
			
			
			<!-- 우측상단 경로 정보 -->
			<p class="history"><img src="./images/sub/history_home.gif" alt="home" /> 정보마당 <img src="./images/sub/history_arrow.gif" alt="다음" /> <strong>연수.행사</strong></p>
       		<p class="pt30"></p>
			<!-- .//우측상단 경로 정보 -->		
			
			
			
			<!-- 게시판영역 -->
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
				
				
				<!-- 달력영역 -->
				<div id="month"></div>
				<!-- .//달력영역 -->
				
				
         
        		<p class="pt40"></p>
         		 
         		 
         		<!-- 목록 . 등록 버튼 -->
				<span class="bbs_btn"> 
					<span class="wte_l">
						<a href="/foodSen/TrainingEventList.do" class="wte_r">목록</a>
					</span>
					
					<!-- 관리자인 경우만 등록버튼 노출 -->
					<c:if test="${sessionScope.session_admin_yn == 'y'}">
						<span class="per_l">
							<a href="/foodSen/trainingEventCreateForm.do" class="pre_r">등록</a>
						</span>
					</c:if>
					<!-- .//관리자인 경우만 등록버튼 노출 -->
					
				</span>
				<!-- .//목록 등록 버튼 -->
             
            	
			</div>
			<!-- .//게시판영역 -->
			
          
		</div>
		<!-- .//우측 컨텐츠 -->
		
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