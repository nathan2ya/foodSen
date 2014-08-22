<%@ page language="java" contentType="text/html; charset=EUC-KR"
    pageEncoding="EUC-KR"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt_rt" %>



<jsp:include page="/view/include/top.jsp"/>
<link href="/css/base.css" rel="stylesheet" type="text/css" />
<link href="/css/common.css" rel="stylesheet" type="text/css" />

<!-- 네이버에디터 -->
<script type="text/javascript" src="<%=request.getContextPath()%>/assets/se1/js/HuskyEZCreator.js" charset="utf-8"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/assets/se1/js/hp_SE2_TimeStamper.js" charset="utf-8"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/assets/se1/photo_uploader/plugin/hp_SE2M_AttachQuickPhoto.js" charset="utf-8"></script>


<script type="text/javascript">
/* 
	//네이버에디터로인해 기존 유효성 검사 스크립트 주석처리
	function goCreate(){
		if(!inspectionResultCreateFrom.title.value){
			alert("제목을 입력하세요.");
			inspectionResultCreateFrom.title.focus();
			return;
		}
		
		if(!inspectionResultCreateFrom.pw.value){
			alert("비밀번호를 입력하세요.");
			inspectionResultCreateFrom.pw.focus();
			return;
		}
		
		if(getStrByte(inspectionResultCreateFrom.title.value) > 120){
			alert("제목은 200자까지만 입력할 수 있습니다.");
			inspectionResultCreateFrom.title.value = inspectionResultCreateFrom.title.value.cut(120);
			inspectionResultCreateFrom.title.focus();
			return;
		}
		
		if(getStrByte(inspectionResultCreateFrom.description.value) > 1200){
			alert("내용은 2000자까지만 입력할 수 있습니다.");
			inspectionResultCreateFrom.description.value = inspectionResultCreateFrom.description.value.cut(1200);
			inspectionResultCreateFrom.description.focus();
			return;
		}
		
		// 제목 : <아아아아아
		if(validateSQL(inspectionResultCreateFrom.title.value) > -1){
			alert("특수문자는 입력할 수 없습니다.");
			inspectionResultCreateFrom.title.focus();
			return;
		}
		
		if(validateSQL(inspectionResultCreateFrom.description.value) > -1){
			alert("특수문자는 입력할 수 없습니다.");
			inspectionResultCreateFrom.description.focus();
			return;
		}
		
		var thumbext = document.getElementById("filename").value;

		thumbext = thumbext.slice(thumbext.indexOf(".") + 1).toLowerCase();
		
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
		
		var upfiles=document.getElementById("filename").value;
		
		//match 는 없으면 null 을 반환함
		if(upfiles.match(/\.(php|php3|html|htm|cgi|pl|asp|jsp)$/i)){ //기호뒤에 다음의 확장명이 있으면 true
			alert('업로드가 불가능한 확장자 입니다.');
			return;
		}
		
		inspectionResultCreateFrom.submit();
	}
 */	
	
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
	
		<p><img src="./images/sub/safety/sub_vimg_01.jpg" alt="건강한 급식 행복한 학교" /></p>
		
		<!-- 좌측메뉴 -->
		<jsp:include page="/view/include/menu2/inspectionResultLnb.jsp"/>
		<!-- .//좌측메뉴 -->
		
		<!-- 우측 컨텐츠 -->
		<div class="right_box">
			
			<!-- 우측상단 제목 -->
			<p><img src="./images/sub/safety/title_05.gif" alt="위생.안전성 검사결과" /></p>
			<!-- .//우측상단 제목 -->
			
			
			<!-- 우측상단 경로 정보 -->
			<p class="history"><img src="./images/sub/history_home.gif" alt="home" /> 학교급식위생안전 <img src="./images/sub/history_arrow.gif" alt="다음" /> <strong>위생.안전성 검사결과</strong></p>
       		<p class="pt30"></p>
			<!-- .//우측상단 경로 정보 -->
			
			
			<!-- 게시판영역 -->
			<div class="tbl_box">
				
				<form name="inspectionResultCreateFrom" action="/foodSen/inspectionResultCreate.do" method="post" enctype="multipart/form-data">
					
					<table width="100%" border="0" cellspacing="0" cellpadding="0" class="tbl_type01" summary="위생.안전성 검사결과">
						<caption>위생.안전성 검사결과</caption>
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
								<th>
									내용<!-- <br/>(2000자 이내) -->
								</th>
								<!-- 네이버에디터 -->
								<td colspan="5" class="tl">
									<div class="form-group">
										<!-- <label for="test_content">내용</label> -->
							  			<!-- <textarea name="notice_content" id="notice_content" Style="width:1098px"></textarea> -->
							  			<textarea id="description" name="description" rows="12" Style="width:610px" cols="*" class="area"></textarea>
							  		</div>
						  		</td>
								
								<!-- 
								기존 내용입력 공간
								<td colspan="5" class="tl">
									<textarea id="description" name="description" rows="12" cols="*" class="area"></textarea>
								</td>
								 -->
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
						<a href="/foodSen/inspectionResultList.do" class="wte_r">목록</a>
					</span> 
					<span class="per_l">
					
						<!-- 
						기존 저장버튼
						<a href="javascript:goCreate()" class="pre_r">저장</a>
						 -->
						 
						 <!-- 네이버스마트에디터 저장버튼 -->
						 <a href="javascript:submitContents(this)" class="pre_r">저장</a>
						 
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

<script type="text/javascript">
	var oEditors = [];
		nhn.husky.EZCreator.createInIFrame({
   		oAppRef: oEditors,
   		elPlaceHolder: "description",
   		sSkinURI: "<%=request.getContextPath()%>/assets/se1/SmartEditor2Skin.html",
   		fCreator: "createSEditor2"
	});
		
	function submitContents(elClickedObj){ 
		oEditors.getById["description"].exec("UPDATE_CONTENTS_FIELD", []);
		
		//alert(document.getElementById("description").value);
		
		//val
		if(!inspectionResultCreateFrom.title.value){
			alert("제목을 입력하세요.");
			inspectionResultCreateFrom.title.focus();
			return;
		}
		
		if(!inspectionResultCreateFrom.pw.value){
			alert("비밀번호를 입력하세요.");
			inspectionResultCreateFrom.pw.focus();
			return;
		}
		
		if(getStrByte(inspectionResultCreateFrom.title.value) > 120){
			alert("제목은 200자까지만 입력할 수 있습니다.");
			inspectionResultCreateFrom.title.value = inspectionResultCreateFrom.title.value.cut(120);
			inspectionResultCreateFrom.title.focus();
			return;
		}
		
		// 제목 : <아아아아아
		if(validateSQL(inspectionResultCreateFrom.title.value) > -1){
			alert("특수문자는 입력할 수 없습니다.");
			inspectionResultCreateFrom.title.focus();
			return;
		}
		
		var thumbext = document.getElementById("filename").value;

		thumbext = thumbext.slice(thumbext.indexOf(".") + 1).toLowerCase();
		
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
		
		var upfiles=document.getElementById("filename").value;
		
		//match 는 없으면 null 을 반환함
		if(upfiles.match(/\.(php|php3|html|htm|cgi|pl|asp|jsp)$/i)){ //기호뒤에 다음의 확장명이 있으면 true
			alert('업로드가 불가능한 확장자 입니다.');
			return;
		}
		//.val
				
		try{ 
			elClickedObj.inspectionResultCreateFrom.submit();
		}catch(e){} 
	} 
	
	// textArea에 이미지 첨부
	function pasteHTML(filepath){
	    var sHTML = '<img src="<%=request.getContextPath()%>/assets/se1/notice_upload/'+filepath+'">';
	    oEditors.getById["description"].exec("PASTE_HTML", [sHTML]); 
	}
</script>
