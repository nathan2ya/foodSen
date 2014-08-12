<%@ page language="java" contentType="text/html; charset=EUC-KR"
    pageEncoding="EUC-KR"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt_rt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<jsp:include page="../../include/top.jsp"/>
<script type="text/javascript" src="http://code.jquery.com/jquery-1.10.2.min.js"></script>
<script type="text/javascript" src="http://code.jquery.com/ui/1.10.3/jquery-ui.js"></script>
<link rel="stylesheet" type="text/css" href="http://code.jquery.com/ui/1.10.3/themes/smoothness/jquery-ui.css">

<script type="text/javascript">

	function goDelete(){
		if(confirm("�Խñ��� �����Ͻðڽ��ϱ�?")!=0){
			deleteOK.submit();
		}else{
			return;
		}
	}

	function goEdit(){
		if(!editOK.sur_title.value){
			alert("������ �Է��ϼ���.");
			editOK.sur_title.focus();
			return;
		}
		if(!editOK.sur_sat_date.value){
			alert("���۳�¥�� �����ϼ���.");
			editOK.sur_sat_date.focus();
			return;
		}
		
		if(!editOK.sur_end_date.value){
			alert("���ᳯ¥�� �����ϼ���.");
			editOK.sur_end_date.focus();
			return;
		}
		
		if(editOK.sur_sat_date.value > editOK.sur_end_date.value){
			alert("���ᳯ¥�� ���۳�¥���� �̷����� �մϴ�.");
			return;
		}
		
		var num = $("#que_cnt").text();
		
		for(var i=1; i<=num; i++){
			var titleTxt=document.getElementById("surq_item"+i);
			var item1=document.getElementById("item1"+i);
			var item2=document.getElementById("item2"+i);
			var item3=document.getElementById("item3"+i);
			var item4=document.getElementById("item4"+i);
			var item5=document.getElementById("item5"+i);
			
			if(titleTxt.value == ""){
				alert("����"+i+"���� �Է��ϼ���.");
				titleTxt.focus();
				return;
			}
			
			if(item1.value == ""){
				alert("����"+i+"���� 1�� �׸��� �Է��ϼ���.");
				item1.focus();
				return;
			}
			
			if(item2.value == ""){
				alert("����"+i+"���� 2�� �׸��� �Է��ϼ���.");
				item2.focus();
				return;
			}
			
			if(item3.value == ""){
				alert("����"+i+"���� 3�� �׸��� �Է��ϼ���.");
				item3.focus();
				return;
			}
			
			if(item4.value == ""){
				alert("����"+i+"���� 4�� �׸��� �Է��ϼ���.");
				item4.focus();
				return;
			}
			
			if(item5.value == ""){
				alert("����"+i+"���� 5�� �׸��� �Է��ϼ���.");
				item5.focus();
				return;
			}
			
			if(getStrByte(titleTxt.value) > 200){
				alert("���� ������ ���ڼ��� �ʰ� �Ǿ����ϴ�.");
				//titleTxt.value = titleTxt.value.cut(120);
				titleTxt.focus();
				return;
			}
			
			if(getStrByte(item1.value) > 200){
				alert("������ ���ڼ��� �ʰ� �Ǿ����ϴ�.");
				//item1.value = item1.value.cut(120);
				item1.focus();
				return;
			}
			
			if(getStrByte(item2.value) > 200){
				alert("������ ���ڼ��� �ʰ� �Ǿ����ϴ�.");
				//item2.value = item2.value.cut(120);
				item2.focus();
				return;
			}
			
			if(getStrByte(item3.value) > 200){
				alert("������ ���ڼ��� �ʰ� �Ǿ����ϴ�.");
				//item3.value = item3.value.cut(120);
				item3.focus();
				return;
			}
			
			if(getStrByte(item4.value) > 200){
				alert("������ ���ڼ��� �ʰ� �Ǿ����ϴ�.");
				//item4.value = item4.value.cut(120);
				item4.focus();
				return;
			}
			
			if(getStrByte(item5.value) > 200){
				alert("������ ���ڼ��� �ʰ� �Ǿ����ϴ�.");
				//item5.value = item5.value.cut(120);
				item5.focus();
				return;
			}
		
		}
		
		if(getStrByte(editOK.sur_title.value) > 200){
			alert("������ ���ڼ��� �ʰ� �Ǿ����ϴ�.");
			//editOK.sur_title.value = editOK.sur_title.value.cut(120);
			editOK.sur_title.focus();
			return;
		}
		
		editOK.submit();
	}
	
	function getStrByte(str) {
		var p, len = 0;
		for(p=0; p<str.length; p++) {
			(str.charCodeAt(p) > 255) ? len+=3 : len++; // charCodeAt(���ڿ�) - ���ڿ��� �����ڵ尪���� ��ȯ�Ͽ� 255���� ũ�� �ѱ�.
		}
		return len;
	} // ���ڿ��� byte���� ���ϴ� �Լ� - �ѱ��̶�� ���ڴ� 2bytes, �׿ܿ��� 1byte�� ����Ѵ�.
	
	String.prototype.cut = function(len) {
	    var str = this;
	    var l = 0;
	    for (var i=0; i<str.length; i++) {
	            l += (str.charCodeAt(i) > 255) ? 3 : 1;
	            if (l > len) return str.substring(0,i);
	    }
	    return str;
	}; // ���ڿ��� �߶��ִ� �Լ� - ���ϴ� byte����ŭ �߶��ش�
	
	$(document).ready(function(){
		$('.suri1').show();
		var cnt = "${cnt}";
		
		$('#que_cnt').change(function(){
			var sel = $('#que_cnt option:selected').text();
			var cnt=0;
			for(var i=0;i<=sel;i++){
				$('.suri'+i).show();
				cnt=i;
			}
			for(var j=cnt;j<17;j++){
				$('.suri'+(j+1)).hide();
			}
			for(var k=0; k<17; k++){
				$('#surq_title'+k).val("");
			}
			
			$('.i').each(function(index, item){
				$(item).val("");
			});
		});
		if(cnt <1){
			$('#sur_sat_date').datepicker({
				dateFormat : 'yy-mm-dd',
				defaultDate : 0,
				changeMonth : true,
				changeYear : true,
				showMonthAfterYear: true ,
				yearRange: 'c-90:c+100',
				dayNamesMin : ['��','��','ȭ','��','��','��','��'],
				monthNamesShort : ['1��','2��','3��','4��','5��','6��','7��','8��','9��','10��','11��','12��'],
				showAnim : 'slideDown',
			});
		}
		
		$('#sur_end_date').datepicker({
			dateFormat : 'yy-mm-dd',
			defaultDate : 0,
			changeMonth : true,
			changeYear : true,
			showMonthAfterYear: true ,
			yearRange: 'c-90:c+100',
			dayNamesMin : ['��','��','ȭ','��','��','��','��'],
			monthNamesShort : ['1��','2��','3��','4��','5��','6��','7��','8��','9��','10��','11��','12��'],
			showAnim : 'slideDown',
		});
	});

</script>

  <!-- container-->
  <div id="container">
    <div id="contents">
      <h2>���γ���</h2>      
      	<p><img src="./images/sub/particiation/sub_vimg_01.jpg" alt="�ǰ��� �޽� �ູ�� �б�" /></p>
      <jsp:include page="../../include/particiation/researchLnb.jsp"/>
      
      <div class="right_box">
        <h3><img src="./images/sub/particiation/title_05.gif" alt="��������" /></h3>
        <p class="history"><img src="./images/sub/history_home.gif" alt="home" /> �������� <img src="./images/sub/history_arrow.gif" alt="����" /> <strong>��������</strong></p>
        <p class="pt30"></p>

	        <div class="tbl_box">
	        <form name="editOK" action="researchEditOK.do" method="post">
	         <input type="hidden" id="sur_seq" name="sur_seq" class="inp" value="${voi.sur_seq }"/>
	         <table width="100%" border="0" cellspacing="0" cellpadding="0" class="tbl_type01" summary="��������">
	            <caption>
	            	��������
	            </caption>
	            <colgroup>
	            <col width="15%"/>
	            <col width="20%"/>
	            <col width="15%"/>
	            <col width="20%"/>
	            <col width="15%"/>
	            <col width="%"/>
	            </colgroup>
	            <tbody>
	              <tr>
	                <th>����</th>
	                <td colspan="5" class="tl"><input type="text" id="sur_title" name="sur_title" class="inp" value="${voi.sur_title }"<c:if test="${cnt > 0 }"> readonly</c:if>/></td>
	                </tr>
	              <tr>
	                <th>������</th>
	                <td class="tl"><input type="text" id="sur_sat_date" name="sur_sat_date" class="inp" style="width:100px;" value="${voi.sur_sat_date }" readonly/></td>
	                <th>������</th>
	                <td class="tl"><input id="sur_end_date" name="sur_end_date" class="inp" style="width:100px;" value="${voi.sur_end_date }" readonly/></td>
	              	<th>���׼�</th>
	                <td class="tl" id="que_cnt" >${voi.que_cnt }</td>
	              </tr>
	              <tr>
	              <th>����</th>
	                <td colspan="5" class="tl">
	                    <c:forEach var="vo" items="${vo }" varStatus="j">
		               	   <div class="research">
		               	   		<input type="hidden" id="surq_seqItem${j.count }" name="surq_seqItem" class="inp" value="${vo.surq_seq }"/>
		               	   		<input type="hidden" id="suri_seqItem${j.count }" name="suri_seqItem" class="inp" value="${vo.suri_seq }"/>
			                    <p>${j.count }. <input type="text" id="surq_item${j.count }" name="surq_item" class="inp" value="${vo.surq_title }" <c:if test="${cnt > 0 }"> readonly</c:if>/></p>
		                        <ul>
		                        
		                        <li>�� <input type="text" id="item1${j.count }" name="item1" class="inp i" value="${vo.suri_title1 }" <c:if test="${cnt > 0 }"> readonly</c:if>/></li>
		                        <li>�� <input type="text" id="item2${j.count }" name="item2" class="inp i" value="${vo.suri_title2 }" <c:if test="${cnt > 0 }"> readonly</c:if>/></li>
		                        <li>�� <input type="text" id="item3${j.count }" name="item3" class="inp i" value="${vo.suri_title3 }" <c:if test="${cnt > 0 }"> readonly</c:if>/></li>
		                        <li>�� <input type="text" id="item4${j.count }" name="item4" class="inp i" value="${vo.suri_title4 }" <c:if test="${cnt > 0 }"> readonly</c:if>/></li>
		                        <li>�� <input type="text" id="item5${j.count }" name="item5" class="inp i" value="${vo.suri_title5 }" <c:if test="${cnt > 0 }"> readonly</c:if>/></li>
		                        </ul>
							</div>
						</c:forEach>
	               </td>
	              </tr>
	            </tbody>
	          </table>
	          </form>
	          <p class="pt40"></p>
	          
	          <!-- btn--> 
	          <span class="bbs_btn"> 
	          <span class="wte_l"><a href="researchList.do" class="wte_r">���</a></span> 
	          <span class="per_l"><a href="javascript:goEdit()" class="pre_r">����</a></span>
	          <span class="wte_l"><a href="javascript:goDelete()" class="wte_r">����</a></span>
	          </span> 
	          <!-- //btn--> 
	          
	        </div>
	        </div>
    </div>
    <p class="bottom_bg"></p>
  </div>
  
   <form name="deleteOK" action="researchDelete.do" method="post">
 	<input type="hidden" id="sur_seq" name="sur_seq" value="${voi.sur_seq }" />
 	<input type="hidden" name="url" id="url" value="research">
 </form>
  <!-- //container-->
  <%-- <input type="hidden" id="size" name="size" value="${vo.size() }"> --%>
  
<jsp:include page="../../include/footer.jsp"/>