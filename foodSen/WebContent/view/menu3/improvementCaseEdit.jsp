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
		
		if(!improvementCaseEditFrom.pw.value){
			alert("������ �Ͻ÷��� ��й�ȣ�� �Է��ϼ���.");
			improvementCaseEditFrom.pw.focus();
			return;
		}
		
		if(oriPassword != improvementCaseEditFrom.pw.value){
			alert("��й�ȣ�� �ٽ� Ȯ�����ּ���.");
			improvementCaseEditFrom.pw.focus();
			return;
		}
		
		if(getStrByte(improvementCaseEditFrom.description.value) > 1200){
			alert("������ 2000�ڱ����� �Է��� �� �ֽ��ϴ�.");
			improvementCaseEditFrom.description.value = improvementCaseEditFrom.description.value.cut(1200);
			improvementCaseEditFrom.description.focus();
			return;
		}
		
		if(validateSQL(improvementCaseEditFrom.description.value) > -1){
			alert("Ư�����ڴ� �Է��� �� �����ϴ�.");
			improvementCaseEditFrom.description.focus();
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
			alert("���ϸ��� '(', ')', '-', '_', '��' �� ������ Ư�����ڰ� �ԷµǾ� �ֽ��ϴ�.");
			return;
		}
		
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
			else
			{
				var node = document.getElementById('filename');
				size = node.files[0].fileSize;
			}
			if(fileCheck(size) == false){
				 alert("÷������ ������� 50MB �̳��� ��� �����մϴ�.");
				 return;
			}
		}
		
		improvementCaseEditFrom.submit();
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
		
		// SQLInjection�� ���� ���� ������ ����� �ԷµǴ� ���뿡�� '�Ǵ� "�� ã�� �ٲٰų� �Է����� ���ϰ� �ϴ� ���̴�.	
		var x=obj;
		var pos = 0;
		var pos1 = 0;
		var pos2 = 0;
		var pos3 = 0;
		pos=x.indexOf("'"); // ��üID�� ���뿡�� '�� ã�´�. "�� ã������ pos=x.indexOf("\""); �̷��� ���� �ȴ�.
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
	
	
	//�̹��� ���δ� �߰�
	fields = 1;

	function addInput(imgName) {
		if (fields != 7){
			document.getElementById('text').innerHTML += "<div id=imgInput"+fields+"><input type='file' name='optupload"+fields+"' /> <br/> <font color='gray'>"+imgName+"(�����̹���)</font> </div>";
												//+"<a href='#' onclick='removeInput(this.parentNode)' /> �̹���"+fields+" �׸� ���� <br/><hr></div>";  //�ش� ���δ��� �����ϱ�.
			fields += 1;
		}else{
			alert("�̹����� �ִ� 6������ ��� �� �� �ֽ��ϴ�.");
			document.improvementCaseCreateFrom.add.disabled = true;
		}
	}
	
	//�̹��� ���δ� ������DIV ����
	function removeInput1(){
		var addedFormDiv = document.getElementById('text');
		
		if(fields > 1){ // 2�ϰ�� ���� 1�� �ִٴ� �ǹ�. �� �����Ұ��� �������� �ǹ�
			var addedDiv = document.getElementById("imgInput"+(--fields)); // ������ div���� �ʱ�ȭ
			addedFormDiv.removeChild(addedDiv); //�ش� div ���� 
		}else{
			alert("������ �̹��� ���δ��� �������� �ʽ��ϴ�.");
		}
    }
	
</script>



<div id="container">
	<div id="contents">
	
		<p><img src="./images/sub/factory/sub_vimg_01.jpg" alt="�ǰ��� �޽� �ູ�� �б�" /></p>
		
		<!-- �����޴� -->
		<jsp:include page="/view/include/menu3/improvementCaseLnb.jsp"/>
		<!-- .//�����޴� -->
		
	
		<!-- ���� ������ -->
		<div class="right_box">
			
			<!-- ������� ���� -->
			<p><img src="./images/sub/factory/title_02.gif" alt="�޽Ľü� ���� ���" /></p>
			<!-- .//������� ���� -->
			
			
			<!-- ������� ��� ���� -->
			<p class="history"><img src="./images/sub/history_home.gif" alt="home" /> �б��޽� �ü����� <img src="./images/sub/history_arrow.gif" alt="����" /> <strong>�޽Ľü��������</strong></p>
       		<p class="pt30"></p>
			<!-- .//������� ��� ���� -->
			
			
			<!-- �Խ��ǿ��� -->
			<div class="tbl_box">
				<form name="improvementCaseEditFrom" action="/foodSen/improvementCaseEdit.do" method="post" enctype="multipart/form-data">
					
					<table width="100%" border="0" cellspacing="0" cellpadding="0" class="tbl_type01" summary="�޽Ľü��������">
						<caption>�޽Ľü��������</caption>
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
								<th>÷���̹���</th>
								<td colspan="5" class="tl">
									<input type="button"  name="add" value="�̹��� ���δ� �߰�" onclick="addInput()"/>
									<input type="button"  name="remove" value="�̹��� ���δ� ����" onclick="removeInput1()"/>
									<font color='#BDBDBD'>�̹����� �ִ� 6������ ÷���� �� �ֽ��ϴ�.</font> 
									
									<div id="text">
										<!-- �ɼ��߰� Ŭ���� ���⿡ �±� �߰� -->
										
										<!-- �������ڵ� �������� -->
										<c:forEach var="list" items="${imgNames}">
											
											<script>
												addInput('${list}');
											</script>
											
										</c:forEach>
										<!-- .//�������ڵ� �������� -->
										
									</div>
								</td>
							</tr>
							
						</tbody>
					</table>
					
				</form>
				<p class="pt40"></p>
				

				<!-- ��ư -->
				<span class="bbs_btn"> 
				
					<c:if test="${searchingNow == 0}">
						<span class="wte_l"><a href="/foodSen/improvementCaseList.do?currentPage=${currentPage}" class="wte_r">���</a></span>
					</c:if>
					<c:if test="${searchingNow == 1}">
						<span class="wte_l"><a href="/foodSen/improvementCaseSearch.do?searchType=${searchType}&userinput=${userinput}&currentPage=${currentPage}" class="wte_r">���</a></span>
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