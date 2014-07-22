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


	function goCreate(){
		
		if(!trainingEventResCreateFrom.title.value){
			alert("������ �Է��ϼ���.");
			trainingEventResCreateFrom.title.focus();
			return;
		}

		if(!trainingEventResCreateFrom.pw.value){
			alert("��й�ȣ�� �Է��ϼ���.");
			trainingEventResCreateFrom.pw.focus();
			return;
		}
		
		if(getStrByte(trainingEventResCreateFrom.title.value) > 120){
			alert("������ 200�ڱ����� �Է��� �� �ֽ��ϴ�.");
			trainingEventResCreateFrom.title.value = trainingEventResCreateFrom.title.value.cut(120);
			trainingEventResCreateFrom.title.focus();
			return;
		}
		
		if(getStrByte(trainingEventResCreateFrom.description.value) > 1200){
			alert("������ 2000�ڱ����� �Է��� �� �ֽ��ϴ�.");
			trainingEventResCreateFrom.description.value = trainingEventResCreateFrom.description.value.cut(1200);
			trainingEventResCreateFrom.description.focus();
			return;
		}
		
		// ���� : <�ƾƾƾƾ�
		if(validateSQL(trainingEventResCreateFrom.title.value) > -1){
			alert("Ư�����ڴ� �Է��� �� �����ϴ�.");
			trainingEventResCreateFrom.title.focus();
			return;
		}
		
		if(validateSQL(trainingEventResCreateFrom.description.value) > -1){
			alert("Ư�����ڴ� �Է��� �� �����ϴ�.");
			trainingEventResCreateFrom.description.focus();
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
		
		//match �� ������ null �� ��ȯ��
		if(upfiles.match(/\.(php|php3|html|htm|cgi|pl|asp|jsp)$/i)){ //��ȣ�ڿ� ������ Ȯ����� ������ true
			alert('���ε尡 �Ұ����� Ȯ���� �Դϴ�.');
			return;
		}
		
		trainingEventResCreateFrom.submit();
	}
	
	
	
	function validateSQL(obj){ //�±׸� ���� ��ũ��Ʈ
		var x=obj; // ����ڰ� �Է��� ���� :   <�ƾƾƾƾ�   �ϰ��
		var pos = 0;
		var pos1 = 0;
		var pos2 = 0;
		var pos3 = 0;
		
		//���� ��� -1�� ��ȯ��.
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
	
	//���ڼ� üũ
	function getStrByte(str) {
		var p, len = 0;
		for(p=0; p<str.length; p++) {
			(str.charCodeAt(p) > 255) ? len+=2 : len++; // charCodeAt(���ڿ�) - ���ڿ��� �����ڵ尪���� ��ȯ�Ͽ� 255���� ũ�� �ѱ�.
		}
		return len;
	} // ���ڿ��� byte���� ���ϴ� �Լ� - �ѱ��̶�� ���ڴ� 2bytes, �׿ܿ��� 1byte�� ����Ѵ�.
	
	
	//���ڿ� �ڸ���
	String.prototype.cut = function(len) {
	    var str = this;
	    var l = 0;
	    for (var i=0; i<str.length; i++) {
            l += (str.charCodeAt(i) > 255) ? 2 : 1;
            
            if (l > len)//�ʰ��� �߶���
            	return str.substring(0,i);
	    }
	    return str;
	}; // ���ڿ��� �߶��ִ� �Լ� - ���ϴ� byte����ŭ �߶��ش�
	
	
	//�齺���̽��� delete Ű�� ������ ���Ӱ� input �±׸� ����
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
	
		<p><img src=./images/sub/notice/sub_vimg_01.jpg alt="�ǰ��� �޽� �ູ�� �б�" /></p>
		
		
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
				<form name="trainingEventResCreateFrom" action="/foodSen/trainingEventResCreate.do" method="post" enctype="multipart/form-data">
					
					<table width="100%" border="0" cellspacing="0" cellpadding="0" class="tbl_type01" summary="����.���">
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
									<input type="text" id="title" name="title" value="[���] ${resultClass.title}" class="inp" />
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
									<textarea id="description" name="description" rows="12" cols="*" class="area"></textarea>
								</td>
							</tr>
							<tr>
								<th>÷������</th>
								<td colspan="3" class="tl">
									<input type="file" id="filename" name="filename" onkeyup="javascript:onKeyUp();" />
								</td>
								
								<th>��й�ȣ</th>
								<td class="tl">
									<input type="password" id="pw" name="pw" class="inp" />
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
									
									<input type="hidden" id="seq" name="seq" value="${seq}" />
									<input type="hidden" id="currentPage" name="currentPage" value="${currentPage}" />
									<input type="hidden" id="searchingNow" name="searchingNow" value="${searchingNow}" />
								</td>
							</tr>
							
						</tbody>
					</table>
					
				</form>
				<p class="pt40"></p>
				

				<!-- ��ư -->
				<span class="bbs_btn"> 
					<span class="wte_l">
						<a href="/foodSen/TrainingEventList.do" class="wte_r">���</a>
					</span> 
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
