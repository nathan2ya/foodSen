<%@ page language="java" contentType="text/html; charset=EUC-KR"
    pageEncoding="EUC-KR"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt_rt" %>


<link href="/css/base.css" rel="stylesheet" type="text/css" />
<link href="/css/common.css" rel="stylesheet" type="text/css" />


<script type="text/javascript">
function goCreate(){
	var orinPassword = "${pw}";
	
	if(!recruitDelete.pw.value){
		alert("��й�ȣ�� �Է��ϼ���.");
		recruitDelete.pw.focus();
		return;
	}
	
	if(orinPassword != recruitDelete.pw.value){
		alert("��й�ȣ�� �ٽ� Ȯ�����ּ���.");
		recruitDelete.pw.focus();
		return;
	}
	
	recruitDelete.submit();
	
	//�θ�â�� �����̷�Ʈ ��Ŵ
	opener.location.href = "/foodSen/goStay2sec.do?service=recruit";
	self.close(); //â�ݱ�
}
	
</script>


<!-- ���������� �� -->
<form name="recruitDelete" action="/foodSen/recruitDelete.do" method="post" align="left">
	<input type="hidden" id="seq" name="seq" value="${seq}" />
	<table width="150" align="center">
		<tr align="center"><td><br/><b>��й�ȣ �Է�</b></td></tr>
		<tr align="center"><td><input type="password" id="pw" name="pw" /></td></tr>
		<tr align="center">
			<td>
				<input type="hidden" id="seq" name="seq" value="${seq}" />
				
				<br/>
				<input type="button" id="button" name="button" value="�� ���� �ϱ�" onClick="goCreate();" />
			</td>
		</tr>
	</table>	
	
</form>
<!-- .//���������� �� -->


