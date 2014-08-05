<%@ page language="java" contentType="text/html; charset=EUC-KR"
    pageEncoding="EUC-KR"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt_rt" %>

<jsp:include page="/view/include/top.jsp"/>
<link href="/css/base.css" rel="stylesheet" type="text/css" />
<link href="/css/common.css" rel="stylesheet" type="text/css" />

<script type="text/javascript" src="http://code.jquery.com/jquery-1.10.2.min.js"></script>
<script type="text/javascript" src="http://code.jquery.com/ui/1.10.3/jquery-ui.js"></script>
<link rel="stylesheet" type="text/css" href="http://code.jquery.com/ui/1.10.3/themes/smoothness/jquery-ui.css">


<script type="text/javascript">

	$(document).ready(function(){
		
		$('#str_date').datepicker({
			dateFormat : 'yy-mm-dd',
			defaultDate : 0,
			changeMonth : true,
			changeYear : true,
			showMonthAfterYear: true ,
			yearRange: 'c-10:c+10',
			dayNamesMin : ['일','월','화','수','목','금','토'],
			monthNamesShort : ['1월','2월','3월','4월','5월','6월','7월','8월','9월','10월','11월','12월'],
			showAnim : 'slideDown',
		});
		
		$('#end_date').datepicker({
			dateFormat : 'yy-mm-dd',
			defaultDate : 0,
			changeMonth : true,
			changeYear : true,
			showMonthAfterYear: true ,
			yearRange: 'c-10:c+10',
			dayNamesMin : ['일','월','화','수','목','금','토'],
			monthNamesShort : ['1월','2월','3월','4월','5월','6월','7월','8월','9월','10월','11월','12월'],
			showAnim : 'slideDown',
		});
	});

	function goCreate(){
		
		var currentTime = "${currentTime}";
		
		if(!trainingEventCreateFrom.title.value){
			alert("제목을 입력하세요.");
			trainingEventCreateFrom.title.focus();
			return;
		}

		if(!trainingEventCreateFrom.str_date.value){
			alert("행사시작일을 입력하세요.");
			createOK.str_date.focus();
			return;
		}
		
		if(!trainingEventCreateFrom.end_date.value){
			alert("행사종료일을 입력하세요.");
			createOK.end_date.focus();
			return;
		}
		
		if(trainingEventCreateFrom.str_date.value < currentTime){
			alert("행사시작일은 현재일 이상이여야 합니다.");	
			return;
		}
		
		if(trainingEventCreateFrom.str_date.value > trainingEventCreateFrom.end_date.value){
			alert("행사종료일은 행사시작일보다 미래여야 합니다.");
			return;
		}
		
		if(!trainingEventCreateFrom.pw.value){
			alert("비밀번호를 입력하세요.");
			trainingEventCreateFrom.pw.focus();
			return;
		}
		
		if(getStrByte(trainingEventCreateFrom.title.value) > 120){
			alert("제목은 200자까지만 입력할 수 있습니다.");
			trainingEventCreateFrom.title.value = trainingEventCreateFrom.title.value.cut(120);
			trainingEventCreateFrom.title.focus();
			return;
		}
		
		if(getStrByte(trainingEventCreateFrom.description.value) > 1200){
			alert("내용은 2000자까지만 입력할 수 있습니다.");
			trainingEventCreateFrom.description.value = trainingEventCreateFrom.description.value.cut(1200);
			trainingEventCreateFrom.description.focus();
			return;
		}
		
		// 제목 : <아아아아아
		if(validateSQL(trainingEventCreateFrom.title.value) > -1){
			alert("특수문자는 입력할 수 없습니다.");
			trainingEventCreateFrom.title.focus();
			return;
		}
		
		if(validateSQL(trainingEventCreateFrom.description.value) > -1){
			alert("특수문자는 입력할 수 없습니다.");
			trainingEventCreateFrom.description.focus();
			return;
		}
		
		var thumbext = document.getElementById("filename").value;

		thumbext = thumbext.slice(thumbext.indexOf(".") + 1).toLowerCase();
		

		if(!(thumbext=="" || thumbext=="jpg" || thumbext=="avi" || thumbext=="doc" || thumbext=="hwp" || thumbext=="pptx"
			|| thumbext=="gif")){ 
			alert('다음 확장자만 첨부할 수 있습니다. \n jpg, gif, doc, hwp, pptx, avi');
			return;
		}
		
		
		/* 
		if(thumbext=="mp4" || thumbext=="avi" || thumbext=="mkv" || thumbext=="ts" || thumbext=="gom"
			|| thumbext=="svi" || thumbext=="divx" || thumbext=="sax" || thumbext=="asf" || thumbext=="wmx"
			|| thumbext=="wmv" || thumbext=="wm" || thumbext=="wmp" || thumbext=="mpg" || thumbext=="mpe"
			|| thumbext=="mpeg" || thumbext=="ifo" || thumbext=="vob" || thumbext=="m1v" || thumbext=="m2v"
			|| thumbext=="tp" || thumbext=="trp" || thumbext=="mts" || thumbext=="m2ts" || thumbext=="m2t"
			|| thumbext=="dmb" || thumbext=="m4v" || thumbext=="k3g" || thumbext=="3gp" || thumbext=="skm"
			|| thumbext=="dmskm" || thumbext=="lmp4" || thumbext=="rm" || thumbext=="rmvb" || thumbext=="ogm"
			|| thumbext=="obv" || thumbext=="swf" || thumbext=="flv" || thumbext=="mqv" || thumbext=="mov"){ 
			alert('동영상은 첨부할 수 없습니다.');
			return;
		}
		 */
		var upfiles=document.getElementById("filename").value;
		
		//match 는 없으면 null 을 반환함
		if(upfiles.match(/\.(php|php3|html|htm|cgi|pl|asp|jsp)$/i)){ //기호뒤에 다음의 확장명이 있으면 true
			alert('업로드가 불가능한 확장자 입니다.');
			return;
		}
		
		
		//파일업로드 30mb제한
		if(upfiles == null || upfiles == ""){
			
		}else{
			var size = 0;
			var browser=navigator.appName;
			
			if (browser=="Microsoft Internet Explorer")
			{
				var oas = new ActiveXObject("Scripting.FileSystemObject");
				var filepath = document.getElementById('filename').value;
				var e = oas.getFile(filepath);
				size = e.size;
			}
			else{
				var node = document.getElementById('filename');
				size = node.files[0].size;
			}
			if(fileCheck(size) == false){
				 alert("첨부파일 사이즈는 30MB 이내로 등록 가능합니다.");
				 return;
			}
		}
		
		/* 파일사이즈 체크 */
		function fileCheck(fileSize){
		   //사이즈체크
		   var maxSize  = 30000000;   //30MB
		   if(fileSize > maxSize){
				return false;
		   }
		   return true;
		}

		trainingEventCreateFrom.submit();
	}
	
	
	
	function validateSQL(obj){ //태그를 막는 스크립트
		var x=obj; // 사용자가 입력한 제목 :   <아아아아아   일경우
		var pos = 0;
		var pos1 = 0;
		var pos2 = 0;
		var pos3 = 0;
		
		//없을 경우 -1을 반환함.
		pos=x.indexOf("'"); // -1
		pos1=x.indexOf("\""); // -1
		pos2=x.indexOf("<"); // -1
		pos3=x.indexOf(">"); //  0
		
		if (pos!=-1){
			return pos;
		}
		if (pos1!=-1){
			return pos1;
		}
		if (pos2!=-1){
			return pos2;
		}
		if (pos3!=-1){
			return pos3;
		}
	}
	
	//문자수 체크
	function getStrByte(str) {
		var p, len = 0;
		for(p=0; p<str.length; p++) {
			(str.charCodeAt(p) > 255) ? len+=2 : len++; // charCodeAt(문자열) - 문자열을 유니코드값으로 변환하여 255보다 크면 한글.
		}
		return len;
	} // 문자열의 byte수를 구하는 함수 - 한글이라면 글자당 2bytes, 그외에는 1byte로 계산한다.
	
	
	//문자열 자르기
	String.prototype.cut = function(len) {
	    var str = this;
	    var l = 0;
	    for (var i=0; i<str.length; i++) {
            l += (str.charCodeAt(i) > 255) ? 2 : 1;
            
            if (l > len)//초과시 잘라줌
            	return str.substring(0,i);
	    }
	    return str;
	}; // 문자열을 잘라주는 함수 - 원하는 byte수만큼 잘라준다
	
	
	//백스페이스나 delete 키를 누르면 새롭게 input 태그를 만듬
	function onKeyUp(){
		var code = event.keyCode;
		if(code == 8 || code == 46){
			var obj = document.getElementById("filename");
			obj.outerHTML  = '<input type="file" id="filename" name="filename" onkeyup="javascript:onKeyUp();"/>';
		}
	}
	
</script>



<div id="container">
	<div id="contents">
	
		<p><img src=./images/sub/notice/sub_vimg_01.jpg alt="건강한 급식 행복한 학교" /></p>
		
		
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
				<form name="trainingEventCreateFrom" action="/foodSen/trainingEventCreate.do" method="post" enctype="multipart/form-data">
					
					<table width="100%" border="0" cellspacing="0" cellpadding="0" class="tbl_type01" summary="연수.행사">
						<caption>연수.행사</caption>
						<colgroup>
							<col width="15%" />
							<col width="20%" />
							<col width="15%" />
							<col width="20%" />
							<col width="15%" />
							<col width="*%" />
						</colgroup>
						<tbody>
							<tr>
								<th>제목</th>
								<td colspan="5" class="tl">
									<input type="text" id="title" name="title" class="inp" />
								</td>
							</tr>
							
							<tr>
								<th>행사시작일</th>
								<td class="tl" colspan="2">
									<input id="str_date" name="str_date" class="inp" readonly />
								</td>
								<th>행사종료일</th>
								<td class="tl" colspan="2">
									<input id="end_date" name="end_date" class="inp" readonly />
								</td>
							</tr>
							
							<tr>
								<th>
									내용<br/>(2000자 이내)
								</th>
								<td colspan="5" class="tl">
									<textarea id="description" name="description" rows="12" cols="*" class="area"></textarea>
									<div>※ ' , " , < . > 문자는 사용 할 수 없습니다.</div>
								</td>
							</tr>
							<tr>
								<th>첨부파일</th>
								<td colspan="3" class="tl">
									<input type="file" id="filename" name="filename" onkeyup="javascript:onKeyUp();" />
								</td>
								
								<th>비밀번호</th>
								<td class="tl">
									<input type="password" id="pw" name="pw" class="inp" />
								</td>
							</tr>
						</tbody>
					</table>
					
				</form>
				<p class="pt40"></p>
				

				<!-- 버튼 -->
				<span class="bbs_btn"> 
					<span class="wte_l">
						<a href="/foodSen/TrainingEventList.do" class="wte_r">목록</a>
					</span> 
					<span class="per_l">
						<a href="javascript:goCreate()" class="pre_r">저장</a>
					</span>
				</span>
				<!-- .//버튼-->


			</div>
			<!-- .//게시판영역 -->
			
			
			
		</div>
		<!-- .//우측 컨텐츠 -->
		
		<p class="bottom_bg"></p>
		
	</div>
</div>

<jsp:include page="/view/include/footer.jsp"/>
