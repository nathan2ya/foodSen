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
		
		if(!trainingEventRepEdit.pw.value){
			alert("������ �Ͻ÷��� ��й�ȣ�� �Է��ϼ���.");
			trainingEventRepEdit.pw.focus();
			return;
		}
		
		if(oriPassword != trainingEventRepEdit.pw.value){
			alert("��й�ȣ�� �ٽ� Ȯ�����ּ���.");
			trainingEventRepEdit.pw.focus();
			return;
		}
		
		if(getStrByte(trainingEventRepEdit.description.value) > 1200){
			alert("������ 2000�ڱ����� �Է��� �� �ֽ��ϴ�.");
			trainingEventRepEdit.description.value = trainingEventRepEdit.description.value.cut(1200);
			trainingEventRepEdit.description.focus();
			return;
		}
		
		if(validateSQL(trainingEventRepEdit.description.value) > -1){
			alert("Ư�����ڴ� �Է��� �� �����ϴ�.");
			trainingEventRepEdit.description.focus();
			return;
		}
		
		
		var thumbext = document.getElementById("filename").value;

		thumbext = thumbext.slice(thumbext.indexOf(".") + 1).toLowerCase();
		
		if(!(thumbext=="" || thumbext=="jpg" || thumbext=="avi" || thumbext=="doc" || thumbext=="hwp" || thumbext=="pptx"
			|| thumbext=="gif")){ 
			alert('���� Ȯ���ڸ� ÷���� �� �ֽ��ϴ�. \n jpg, gif, doc, hwp, pptx, avi');
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
			alert('�������� ÷���� �� �����ϴ�.');
			return;
		}
		 */
		var upfiles=document.getElementById("filename").value;
		
		if(upfiles.match(/\.(php|php3|html|htm|cgi|pl|asp|jsp)$/i)){
			alert('���ε尡 �Ұ����� Ȯ���� �Դϴ�.');
			return;
		}
		
		if(inputCheckSpecial(upfiles) == false){
			alert("���ϸ� '(', ')', '-', '_', '��' �� ������ Ư�����ڰ� �ԷµǾ� �ֽ��ϴ�.");
			return;
		}
		
		//���Ͼ��ε� 30mb����
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
				 alert("÷������ ������� 30MB �̳��� ��� �����մϴ�.");
				 return;
			}
		}
		
		/* ���ϻ����� üũ */
		function fileCheck(fileSize){
		   //������üũ
		   var maxSize  = 30000000;   //30MB
		   if(fileSize > maxSize){
				return false;
		   }
		   return true;
		}
		
		
		trainingEventRepEdit.submit();
	}
	/* goCreate ���� */
	
	
	
	/* ���ϻ����� üũ */
	function fileCheck(fileSize)
	{
	   //������üũ
	   var maxSize  = 50000000;   //50MB
	   if(fileSize > maxSize){
	        return false;
	   }
	
	   return true;
	}
	
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
	} // ���ڿ��� byte���� ���ϴ� �Լ� - �ѱ��̶�� ���ڴ� 2bytes, �׿ܿ��� 1byte�� ���
	
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
	
		<p><img src="./images/sub/factory/sub_vimg_01.jpg" alt="�ǰ��� �޽� �ູ�� �б�" /></p>
		
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
				<form name="trainingEventRepEdit" action="/foodSen/trainingEventRepEdit.do" method="post" enctype="multipart/form-data">
					
					<table width="100%" border="0" cellspacing="0" cellpadding="0" class="tbl_type01" summary="�������">
						<caption>����.���</caption>
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
								<th>��������</th>
								<td class="tl" colspan="2">
									<input id="str_date" name="str_date" value="${resultClass.str_date}" class="inp" readonly />
								</td>
								<th>���������</th>
								<td class="tl" colspan="2">
									<input id="end_date" name="end_date" value="${resultClass.end_date}" class="inp" readonly />
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
										<br/><font color="gray">${resultClass.attach_name} (��������)</font>
									</c:if>
									<!-- ÷�������� ���� ��� -->
									<c:if test="${resultClass.attach_name == null}">
										<br/><font color="gray">�������Ͼ���</font>
									</c:if>
									
								</td>
								
								<th>��й�ȣ</th>
								<td class="tl">
									<input type="password" id="pw" name="pw" class="inp" />
									
									
									<!-- ������ -->
									<input type="hidden" id="seq" name="seq" value="${seq}" />
									<input type="hidden" id="currentPage" name="currentPage" value="${currentPage}" />
									<input type="hidden" id="searchingNow" name="searchingNow" value="${searchingNow}" />
									
									<input type="hidden" id="cnt" name="cnt" value="${cnt}" />
									
									<c:if test="${searchingNow == 1}">
										<input type="hidden" id="searchType" name="searchType" value="${searchType}" />
										<input type="hidden" id="userinput" name="userinput" value="${userinput}" />
									</c:if>
									
									<!-- .//������ -->
									
								</td>
							</tr>
							
							<tr>
								<th>�̹���1</th>
								<td colspan="5" class="tl">
									<input type="file" id="img1" name="img1" />
								</td>
							</tr>
							
							<tr>
								<th>�̹���2</th>
								<td colspan="5" class="tl">
									<input type="file" id="img2" name="img2" /> 
								</td>
							</tr>
							
							<tr>
								<th>�̹���3</th>
								<td colspan="5" class="tl">
									<input type="file" id="img3" name="img3" /> 
								</td>
							</tr>
							
						</tbody>
					</table>
					
				</form>
				<p class="pt40"></p>
				

				<!-- ��ư -->
				<span class="bbs_btn"> 
				
					<c:if test="${searchingNow == 0}">
						<span class="wte_l"><a href="/foodSen/TrainingEventList.do?currentPage=${currentPage}" class="wte_r">���</a></span>
					</c:if>
					<c:if test="${searchingNow == 1}">
						<span class="wte_l"><a href="/foodSen/trainingEventSearch.do?searchType=${searchType}&userinput=${userinput}&currentPage=${currentPage}" class="wte_r">���</a></span>
					</c:if>
					
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
