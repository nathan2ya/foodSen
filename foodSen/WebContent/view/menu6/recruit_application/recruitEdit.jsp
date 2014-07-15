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
			alert("������ �Ͻ÷��� ��й�ȣ�� �Է��ϼ���.");
			recruitEditFrom.pw.focus();
			return;
		}
		
		if(oriPassword != recruitEditFrom.pw.value){
			alert("��й�ȣ�� �ٽ� Ȯ�����ּ���.");
			recruitEditFrom.pw.focus();
			return;
		}
		
		if(getStrByte(recruitEditFrom.description.value) > 1200){
			alert("������ 2000�ڱ����� �Է��� �� �ֽ��ϴ�.");
			recruitEditFrom.description.value = recruitEditFrom.description.value.cut(1200);
			recruitEditFrom.description.focus();
			return;
		}
		
		if(validateSQL(recruitEditFrom.description.value) > -1){
			alert("Ư�����ڴ� �Է��� �� �����ϴ�.");
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
			alert('�������� ÷���� �� �����ϴ�.');
			return;
		}
		
		var upfiles=document.getElementById("filename").value;
		
		if(upfiles.match(/\.(php|php3|html|htm|cgi|pl|asp|jsp)$/i)){
			alert('���ε尡 �Ұ����� Ȯ���� �Դϴ�.');
			return;
		}
		
		if(inputCheckSpecial(upfiles) == false){
			alert("���ϸ� '(', ')', '-', '_', '��' �� ������ Ư�����ڰ� �ԷµǾ� �ֽ��ϴ�.");
			return;
		}
		
		recruitEditFrom.submit();
	}
	/* goCreate ���� */
	
	
	
	/*Ư�����ڻ����ϰ�*/
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
			(str.charCodeAt(p) > 255) ? len+=2 : len++; // charCodeAt(���ڿ�) - ���ڿ��� �����ڵ尪���� ��ȯ�Ͽ� 255���� ũ�� �ѱ�.
		}
		return len;
	} // ���ڿ��� byte���� ���ϴ� �Լ� - �ѱ��̶�� ���ڴ� 2bytes, �׿ܿ��� 1byte�� ����Ѵ�.
	
	String.prototype.cut = function(len) {
	    var str = this;
	    var l = 0;
	    for (var i=0; i<str.length; i++) {
	            l += (str.charCodeAt(i) > 255) ? 2 : 1;
	            if (l > len) return str.substring(0,i);
	    }
	    return str;
	}; // ���ڿ��� �߶��ִ� �Լ� - ���ϴ� byte����ŭ �߶��ش�
	
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
	
		<p><img src="./images/sub/safety/sub_vimg_01.jpg" alt="�ǰ��� �޽� �ູ�� �б�" /></p>
		
		<!-- �����޴� -->
		<jsp:include page="/view/include/menu6/recruitLnb.jsp"/>
		<!-- .//�����޴� -->
		
	
		<!-- ���� ������ -->
		<div class="right_box">
			
			<!-- ������� ���� -->
			<h3><img src="./images/sub/particiation/title_01.gif" alt="����" /></h3>
			<!-- .//������� ���� -->
			
			
			<!-- ������� ��� ���� -->
			<p class="history"><img src="./images/sub/history_home.gif" alt="home" /> �������� <img src="./images/sub/history_arrow.gif" alt="����" /> <strong>����</strong></p>
       		<p class="pt30"></p>
			<!-- .//������� ��� ���� -->
			
			
			<!-- ����/���� �� -->
			<ul class="pool_tab">
				<li><a href="/foodSen/recruitList.do" class="pool_tab01_on">����</a></li>
				<li><a href="/foodSen/applicationList.do" class="pool_tab02">����</a></li>
			</ul>
			<!-- .//����/���� �� -->
			
			
			<!-- �Խ��ǿ��� -->
			<div class="tbl_box">
				<form name="recruitEditFrom" action="/foodSen/recruitEdit.do" method="post" enctype="multipart/form-data">
					
					<table width="100%" border="0" cellspacing="0" cellpadding="0" class="tbl_type01" summary="�б��޽��η�Ǯ(����)">
						<caption>�б��޽��η�Ǯ(����)</caption>
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
								<th>����</th>
								<td colspan="5" class="tl">
									<strong>${resultClass.title}</strong>
								</td>
							</tr>
							<tr>
								<th>
									����<br/>(2000�� �̳�)
								</th>
								<td colspan="5" class="tl">
									<textarea id="description" name="description" rows="12" cols="*" class="area">${resultClass.description}</textarea>
								</td>
							</tr>
							<tr>
								<th>÷������</th>
								<td colspan="3" class="tl">
									
									<input type="file" id="filename" name="filename" onkeyup="javascript:onKeyUp();" />
									
									<!-- ���������� �ִ� ��� ���ϸ��� ������� -->
									<c:if test="${resultClass.attach_name != null}">
										<br/><b>�������ϸ� : </b>${resultClass.attach_name}
									</c:if>
									<!-- ÷�������� ���� ��� -->
									<c:if test="${resultClass.attach_name == null}">
										<br/><b>�������Ͼ���</b>
									</c:if>
									
								</td>
								
								<th>��й�ȣ</th>
								<td class="tl">
									<input type="password" id="pw" name="pw" class="inp" />
									
									
									<!-- ������ -->
									<input type="hidden" id="seq" name="seq" value="${seq}" />
									<input type="hidden" id="currentPage" name="currentPage" value="${currentPage}" />
									<input type="hidden" id="searchingNow" name="searchingNow" value="${searchingNow}" />
									
									<c:if test="${searchingNow == 1}">
										<input type="hidden" id="searchType" name="searchType" value="${searchType}" />
										<input type="hidden" id="userinput" name="userinput" value="${userinput}" />
									</c:if>
									<!-- .//������ -->
									
								</td>
							</tr>
						</tbody>
					</table>
					
				</form>
				<p class="pt40"></p>
				

				<!-- ��ư -->
				<span class="bbs_btn"> 
				
					<!-- ��ü�� ��� -->
					<c:if test="${searchingNow == 0}">
						<span class="wte_l"><a href="/foodSen/recruitList.do?currentPage=${currentPage}" class="wte_r">���</a></span>
					</c:if>
					<!-- .//��ü�� ��� -->
					
					
					<!-- �˻����� ��� -->
					<c:if test="${searchingNow == 1}">
						<c:if test="${searchType eq 'job'}">
							<span class="wte_l"><a href="/foodSen/recruitSearch.do?searchType=${searchType}&job=${resultClass.job}&currentPage=${currentPage}" class="wte_r">���</a></span>
						</c:if>
						<c:if test="${searchType eq 'gubun'}">
							<span class="wte_l"><a href="/foodSen/recruitSearch.do?searchType=${searchType}&gubun=${resultClass.gubun}&currentPage=${currentPage}" class="wte_r">���</a></span>
						</c:if>
						<c:if test="${searchType eq 'loc_seq'}">
							<span class="wte_l"><a href="/foodSen/recruitSearch.do?searchType=${searchType}&loc_seq=${resultClass.loc_seq}&currentPage=${currentPage}" class="wte_r">���</a></span>
						</c:if>
						<c:if test="${searchType eq 'school_type'}">
							<span class="wte_l"><a href="/foodSen/recruitSearch.do?searchType=${searchType}&school_type=${resultClass.school_type}&currentPage=${currentPage}" class="wte_r">���</a></span>
						</c:if>
					</c:if>
					<!-- .//�˻����� ��� -->
					
					<span class="per_l">
						<a href="javascript:goCreate()" class="pre_r">����</a>
					</span>
				</span>
				<!-- .//��ư-->


			</div>
			<!-- .//�Խ��ǿ��� -->
			
			
		</div>
		<!-- .//���� ������ -->
		
		<p class="bottom_bg"></p>
		
	</div>
</div>

<jsp:include page="/view/include/footer.jsp"/>
