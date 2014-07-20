<%@ page language="java" contentType="text/html; charset=EUC-KR"
    pageEncoding="EUC-KR"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt_rt" %>



<jsp:include page="/view/include/top.jsp"/>
<link href="/css/base.css" rel="stylesheet" type="text/css" />
<link href="/css/common.css" rel="stylesheet" type="text/css" />


<script type="text/javascript">
	function goCreate(){
		if(!applicationCreateFrom.title.value){
			alert("������ �Է��ϼ���.");
			applicationCreateFrom.title.focus();
			return;
		}
		
		if(!applicationCreateFrom.pw.value){
			alert("��й�ȣ�� �Է��ϼ���.");
			applicationCreateFrom.pw.focus();
			return;
		}
		
		if(!applicationCreateFrom.age.value){
			alert("���̸� �Է��ϼ���.");
			applicationCreateFrom.age.focus();
			return;
		}
		
		if(getStrByte(applicationCreateFrom.title.value) > 120){
			alert("������ 200�ڱ����� �Է��� �� �ֽ��ϴ�.");
			applicationCreateFrom.title.value = applicationCreateFrom.title.value.cut(120);
			applicationCreateFrom.title.focus();
			return;
		}
		
		if(getStrByte(applicationCreateFrom.description.value) > 1200){
			alert("������ 2000�ڱ����� �Է��� �� �ֽ��ϴ�.");
			applicationCreateFrom.description.value = applicationCreateFrom.description.value.cut(1200);
			applicationCreateFrom.description.focus();
			return;
		}
		
		if(validateSQL(applicationCreateFrom.title.value) > -1){
			alert("Ư�����ڴ� �Է��� �� �����ϴ�.");
			applicationCreateFrom.title.focus();
			return;
		}
		
		if(validateSQL(applicationCreateFrom.description.value) > -1){
			alert("Ư�����ڴ� �Է��� �� �����ϴ�.");
			applicationCreateFrom.description.focus();
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
		
		if(upfiles.match(/\.(php|php3|html|htm|cgi|pl|asp|jsp)$/i))
		{
			alert('���ε尡 �Ұ����� Ȯ���� �Դϴ�.');
			return;
		}
		
		if(inputCheckSpecial(upfiles) == false){
			alert("���ϸ� '(', ')', '-', '_', '��' �� ������ Ư�����ڰ� �ԷµǾ� �ֽ��ϴ�.");
			return;
		}
		
		applicationCreateFrom.submit();
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
	
	//���� �����Է� ����
	function check_age(){
		if(!((event.keyCode >= 48 && event.keyCode<=57) || (event.keyCode >= 96 && event.keyCode <= 105) || event.keyCode==8 )){
			alert("���̴� ���ڸ� �Է��� �� �ֽ��ϴ�.");
			applicationCreateFrom.age.value = "";
			applicationCreateFrom.age.focus();
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
			
			
			<!-- �Խ��ǿ��� -->
			<div class="tbl_box">
				<form name="applicationCreateFrom" action="/foodSen/applicationCreate.do" method="post" enctype="multipart/form-data">
					
					<table width="100%" border="0" cellspacing="0" cellpadding="0" class="tbl_type01" summary="�б��޽��η�Ǯ(����)">
						<caption>�б��޽��η�Ǯ(����)</caption>
						<colgroup>
							<col width="15%" />
							<col width="15%" />
							<col width="15%" />
							<col width="15%" />
							<col width="15%" />
							<col width="15%" />
							<col width="*%" />
						</colgroup>
						<tbody>
							<tr>
								<th>����</th>
								<td colspan="5" class="tl">
									<input type="text" id="title" name="title" class="inp" />
								</td>
							</tr>
							
							<tr>
								<th>�������</th>
								<td colspan="1" class="tl">
									<select id="loc_seq" name="loc_seq">
										<option value="01">������</option>
										<option value="02">������</option>
										<option value="03">���ϱ�</option>
										<option value="04">������</option>
										<option value="05">���Ǳ�</option>
										<option value="06">������</option>
										<option value="07">���α�</option>
										<option value="08">��õ��</option>
										<option value="09">�����</option>
										<option value="10">������</option>
										<option value="11">���빮��</option>
										<option value="12">���۱�</option>
										<option value="13">������</option>
										<option value="14">���빮��</option>
										<option value="15">���ʱ�</option>
										<option value="16">������</option>
										<option value="17">���ϱ�</option>
										<option value="18">���ı�</option>
										<option value="19">��õ��</option>
										<option value="20">��������</option>
										<option value="21">��걸</option>
										<option value="22">����</option>
										<option value="23">���α�</option>
										<option value="24">�߱�</option>
										<option value="25">�߶���</option>
									</select>
								</td>
								
								<th>����</th>
								<td colspan="1" class="tl">
									<select id="job" name="job">
										<option value="01">���米��</option>
										<option value="02">�����</option>
										<option value="03">������</option>
										<option value="04">������</option>
										<option value="05">��ĵ����</option>
									</select>
								</td>
								
								<th>�ٹ�����</th>
								<td colspan="1" class="tl">
									<select id="gubun" name="gubun">
										<option value="01">������</option>
										<option value="02">�ð���</option>
									</select>
								</td>
								
							</tr>
							
							<tr>
								<th>����</th>
								<td colspan="1" class="tl">
									�� <input type="text" id="age" name="age"  size="4"  maxlength="2" onkeydown="check_age();" /> ��
								</td>
								
								<th>����</th>
								<td colspan="3" class="tl">
									<select id="sex" name="sex">
										<option value="01">����</option>
										<option value="02">����</option>
									</select>
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
						</tbody>
					</table>
					
				</form>
				<p class="pt40"></p>
				

				<!-- ��ư -->
				<span class="bbs_btn"> 
					<span class="wte_l">
						<a href="/foodSen/recruitList.do" class="wte_r">���</a>
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
