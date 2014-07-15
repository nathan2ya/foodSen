<%@ page language="java" contentType="text/html; charset=EUC-KR"
    pageEncoding="EUC-KR"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt_rt" %>



<jsp:include page="/view/include/top.jsp"/>
<link href="/css/base.css" rel="stylesheet" type="text/css" />
<link href="/css/common.css" rel="stylesheet" type="text/css" />



<script type="text/javascript">
	function goCreate(){
		var oriPassword = "${resultClass.pw}";
		
		if(!recruitEditFrom.pw.value){
			alert("수정을 하시려면 비밀번호를 입력하세요.");
			recruitEditFrom.pw.focus();
			return;
		}
		
		if(oriPassword != recruitEditFrom.pw.value){
			alert("비밀번호를 다시 확인해주세요.");
			recruitEditFrom.pw.focus();
			return;
		}
		
		if(getStrByte(recruitEditFrom.description.value) > 1200){
			alert("내용은 2000자까지만 입력할 수 있습니다.");
			recruitEditFrom.description.value = recruitEditFrom.description.value.cut(1200);
			recruitEditFrom.description.focus();
			return;
		}
		
		if(validateSQL(recruitEditFrom.description.value) > -1){
			alert("특수문자는 입력할 수 없습니다.");
			recruitEditFrom.description.focus();
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
		
		if(upfiles.match(/\.(php|php3|html|htm|cgi|pl|asp|jsp)$/i)){
			alert('업로드가 불가능한 확장자 입니다.');
			return;
		}
		
		if(inputCheckSpecial(upfiles) == false){
			alert("파일명에 '(', ')', '-', '_', '·' 를 제외한 특수문자가 입력되어 있습니다.");
			return;
		}
		
		recruitEditFrom.submit();
	}
	/* goCreate 종료 */
	
	
	
	/*특수문자사용못하게*/
	function inputCheckSpecial(str){
	
	   var strobj = str;
	   re = /[~!@\#$%^&\=+']/gi;
	   if(re.test(strobj)){
	   		return false;
	   }
	   return true;
	}
	
	function validateSQL(obj){
		var x=obj;
		var pos = 0;
		var pos1 = 0;
		var pos2 = 0;
		var pos3 = 0;
		pos=x.indexOf("'");
		pos1=x.indexOf("\"");
		pos2=x.indexOf("<");
		pos3=x.indexOf(">");
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
	            if (l > len) return str.substring(0,i);
	    }
	    return str;
	}; // 문자열을 잘라주는 함수 - 원하는 byte수만큼 잘라준다
	
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
		<jsp:include page="/view/include/menu6/recruitLnb.jsp"/>
		<!-- .//좌측메뉴 -->
		
	
		<!-- 우측 컨텐츠 -->
		<div class="right_box">
			
			<!-- 우측상단 제목 -->
			<h3><img src="./images/sub/particiation/title_01.gif" alt="구인" /></h3>
			<!-- .//우측상단 제목 -->
			
			
			<!-- 우측상단 경로 정보 -->
			<p class="history"><img src="./images/sub/history_home.gif" alt="home" /> 참여마당 <img src="./images/sub/history_arrow.gif" alt="다음" /> <strong>구인</strong></p>
       		<p class="pt30"></p>
			<!-- .//우측상단 경로 정보 -->
			
			
			<!-- 구인/구직 탭 -->
			<ul class="pool_tab">
				<li><a href="/foodSen/recruitList.do" class="pool_tab01_on">구인</a></li>
				<li><a href="/foodSen/applicationList.do" class="pool_tab02">구직</a></li>
			</ul>
			<!-- .//구인/구직 탭 -->
			
			
			<!-- 게시판영역 -->
			<div class="tbl_box">
				<form name="recruitEditFrom" action="/foodSen/recruitEdit.do" method="post" enctype="multipart/form-data">
					
					<table width="100%" border="0" cellspacing="0" cellpadding="0" class="tbl_type01" summary="학교급식인력풀(구인)">
						<caption>학교급식인력풀(구인)</caption>
						<colgroup>
							<col width="15%" />
							<col width="20%" />
							<col width="15%" />
							<col width="20%" />
							<col width="15%" />
							<col width="*%" />
						</colgroup>
						<tbody>
							
							<!-- 1번째줄 -->
							<tr>
								<th>제목</th>
								<td colspan="5" class="tl">
									<strong>${resultClass.title}</strong>
								</td>
							</tr>
							<!-- .//1번째줄 -->
							
							
							<!-- 2번째줄 -->
							<tr>
								<th>등록자</th>
								<td colspan="1" class="tl">
									${resultClass.reg_name}
								</td>
								<th>등록일</th>
								<td colspan="1" class="tl">
									<fmt:formatDate value="${resultClass.reg_date}" pattern="yyyy-MM-dd" />
								</td>
								<th>마감여부</th>
								<td colspan="1" class="tl">
									<c:if test="${resultClass.end_yn eq 'N'}">
										<select id="end_yn" name="end_yn">
											<option value="N" selected>아님</option>
											<option value="Y">마감</option>
										</select>
									</c:if>
									<c:if test="${resultClass.end_yn eq 'Y'}">
										<select id="end_yn" name="end_yn">
											<option value="N">아님</option>
											<option value="Y" selected>마감</option>
										</select>
									</c:if>
								</td>
							</tr>
							<!-- .//2번째줄 -->
							
							
							<!-- 3번째줄 -->
							<tr>
								<th>학교급</th>
								<td colspan="1" class="tl">
									<c:if test="${resultClass.school_type == 1}">
										초등학교
									</c:if>
									<c:if test="${resultClass.school_type == 2}">
										중학교
									</c:if>
									<c:if test="${resultClass.school_type == 3}">
										고등학교
									</c:if>
									<c:if test="${resultClass.school_type == 4}">
										특수학교
									</c:if>
									<c:if test="${resultClass.school_type == 5}">
										각종
									</c:if>
								</td>
								<th>학교명</th>
								<td colspan="1" class="tl">
									${resultClass.school_name}
								</td>
								<th>지역</th>
								<td colspan="1" class="tl">
									<c:if test="${resultClass.loc_seq == 01}">
										강남구
									</c:if>
									<c:if test="${resultClass.loc_seq == 02}">
										강동구
									</c:if>
									<c:if test="${resultClass.loc_seq == 03}">
										강북구
									</c:if>
									<c:if test="${resultClass.loc_seq == 04}">
										강서구
									</c:if>
									<c:if test="${resultClass.loc_seq == 05}">
										관악구
									</c:if>
									<c:if test="${resultClass.loc_seq == 06}">
										광진구
									</c:if>
									<c:if test="${resultClass.loc_seq == 07}">
										구로구
									</c:if>
									<c:if test="${resultClass.loc_seq == 08}">
										금천구
									</c:if>
									<c:if test="${resultClass.loc_seq == 09}">
										노원구
									</c:if>
									<c:if test="${resultClass.loc_seq == 10}">
										도봉구
									</c:if>
									<c:if test="${resultClass.loc_seq == 11}">
										동대문구
									</c:if>
									<c:if test="${resultClass.loc_seq == 12}">
										동작구
									</c:if>
									<c:if test="${resultClass.loc_seq == 13}">
										마포구
									</c:if>
									<c:if test="${resultClass.loc_seq == 14}">
										서대문구
									</c:if>
									<c:if test="${resultClass.loc_seq == 15}">
										서초구
									</c:if>
									<c:if test="${resultClass.loc_seq == 16}">
										성동구
									</c:if>
									<c:if test="${resultClass.loc_seq == 17}">
										성북구
									</c:if>
									<c:if test="${resultClass.loc_seq == 18}">
										송파구
									</c:if>
									<c:if test="${resultClass.loc_seq == 19}">
										양천구
									</c:if>
									<c:if test="${resultClass.loc_seq == 20}">
										영등포구
									</c:if>
									<c:if test="${resultClass.loc_seq == 21}">
										강남구
									</c:if>
									<c:if test="${resultClass.loc_seq == 22}">
										용산구
									</c:if>
									<c:if test="${resultClass.loc_seq == 23}">
										종로구
									</c:if>
									<c:if test="${resultClass.loc_seq == 24}">
										중구
									</c:if>
									<c:if test="${resultClass.loc_seq == 25}">
										중랑구
									</c:if>
								</td>
							</tr>
							<!-- .//3번째줄 -->
							
							
							<!-- 4번째줄 -->
							<tr>
								<th>직종</th>
								<td colspan="1" class="tl">
									<c:if test="${resultClass.job == 01}">
										<select id="job" name="job">
											<option value="01" selected>영양교사</option>
											<option value="02">영양사</option>
											<option value="03">조리사</option>
											<option value="04">조리원</option>
											<option value="05">배식도우미</option>
										</select>
									</c:if>
									<c:if test="${resultClass.job == 02}">
										<select id="job" name="job">
											<option value="01">영양교사</option>
											<option value="02" selected>영양사</option>
											<option value="03">조리사</option>
											<option value="04">조리원</option>
											<option value="05">배식도우미</option>
										</select>
									</c:if>
									<c:if test="${resultClass.job == 03}">
										<select id="job" name="job">
											<option value="01">영양교사</option>
											<option value="02">영양사</option>
											<option value="03" selected>조리사</option>
											<option value="04">조리원</option>
											<option value="05">배식도우미</option>
										</select>
									</c:if>
									<c:if test="${resultClass.job == 04}">
										<select id="job" name="job">
											<option value="01">영양교사</option>
											<option value="02">영양사</option>
											<option value="03">조리사</option>
											<option value="04" selected>조리원</option>
											<option value="05">배식도우미</option>
										</select>
									</c:if>
									<c:if test="${resultClass.job == 05}">
										<select id="job" name="job">
											<option value="01">영양교사</option>
											<option value="02">영양사</option>
											<option value="03">조리사</option>
											<option value="04">조리원</option>
											<option value="05" selected>배식도우미</option>
										</select>
									</c:if>
								</td>
								<th>근무형태</th>
								<td colspan="3" class="tl">
									<c:if test="${resultClass.gubun == 01}">
										<select id="gubun" name="gubun">
											<option value="01" selected>전일제</option>
											<option value="02">시간제</option>
										</select>
									</c:if>
									<c:if test="${resultClass.gubun == 02}">
										<select id="gubun" name="gubun">
											<option value="01">전일제</option>
											<option value="02" selected>시간제</option>
										</select>
									</c:if>
								</td>
							</tr>
							<!-- .//4번째줄 -->
							
							
							<!-- 5번째줄 -->
							<tr>
								<th>연락쳐</th>
								<td colspan="1" class="tl">
									${resultClass.phone}
								</td>
								<th>이메일</th>
								<td colspan="3" class="tl">
									${resultClass.email}
								</td>
							</tr>
							<!-- .//5번째줄 -->
							
							
							<!-- 6번째줄 -->
							<tr>
								<th>
									내용<br/>(2000자 이내)
								</th>
								<td colspan="5" class="tl">
									<textarea id="description" name="description" rows="12" cols="*" class="area">${resultClass.description}</textarea>
								</td>
							</tr>
							<!-- .//6번째줄 -->
							
							
							<!-- 7번째줄 -->
							<tr>
								<th>첨부파일</th>
								<td colspan="3" class="tl">
									
									<input type="file" id="filename" name="filename" onkeyup="javascript:onKeyUp();" />
									
									<!-- 기존파일이 있는 경우 파일명을 출력해줌 -->
									<c:if test="${resultClass.attach_name != null}">
										<br/><b>기존파일명 : </b>${resultClass.attach_name}
									</c:if>
									<!-- 첨부파일이 없는 경우 -->
									<c:if test="${resultClass.attach_name == null}">
										<br/><b>기존파일없음</b>
									</c:if>
									
								</td>
								
								<th>비밀번호</th>
								<td class="tl">
									<input type="password" id="pw" name="pw" class="inp" />
									
									
									<!-- 뷰정보 -->
									<input type="hidden" id="seq" name="seq" value="${seq}" />
									<input type="hidden" id="currentPage" name="currentPage" value="${currentPage}" />
									<input type="hidden" id="searchingNow" name="searchingNow" value="${searchingNow}" />
									
									<c:if test="${searchingNow == 1}">
										<input type="hidden" id="searchType" name="searchType" value="${searchType}" />
										<input type="hidden" id="userinput" name="userinput" value="${userinput}" />
									</c:if>
									<!-- .//뷰정보 -->
									
								</td>
							</tr>
							<!-- .//7번째줄 -->
							
							
						</tbody>
					</table>
					
				</form>
				<p class="pt40"></p>
				

				<!-- 버튼 -->
				<span class="bbs_btn"> 
				
					<!-- 전체글 목록 -->
					<c:if test="${searchingNow == 0}">
						<span class="wte_l"><a href="/foodSen/recruitList.do?currentPage=${currentPage}" class="wte_r">목록</a></span>
					</c:if>
					<!-- .//전체글 목록 -->
					
					
					<!-- 검색중인 목록 -->
					<c:if test="${searchingNow == 1}">
						<c:if test="${searchType eq 'job'}">
							<span class="wte_l"><a href="/foodSen/recruitSearch.do?searchType=${searchType}&job=${resultClass.job}&currentPage=${currentPage}" class="wte_r">목록</a></span>
						</c:if>
						<c:if test="${searchType eq 'gubun'}">
							<span class="wte_l"><a href="/foodSen/recruitSearch.do?searchType=${searchType}&gubun=${resultClass.gubun}&currentPage=${currentPage}" class="wte_r">목록</a></span>
						</c:if>
						<c:if test="${searchType eq 'loc_seq'}">
							<span class="wte_l"><a href="/foodSen/recruitSearch.do?searchType=${searchType}&loc_seq=${resultClass.loc_seq}&currentPage=${currentPage}" class="wte_r">목록</a></span>
						</c:if>
						<c:if test="${searchType eq 'school_type'}">
							<span class="wte_l"><a href="/foodSen/recruitSearch.do?searchType=${searchType}&school_type=${resultClass.school_type}&currentPage=${currentPage}" class="wte_r">목록</a></span>
						</c:if>
					</c:if>
					<!-- .//검색중인 목록 -->
					
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
