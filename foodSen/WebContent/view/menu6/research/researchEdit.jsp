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
		var current_date = "${current_date}";
		
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
		
		if(editOK.sur_sat_date.value < current_date){
			alert("�������� �������� ������ ���ĸ� ��ϰ����մϴ�.");
			editOK.sur_sat_date.focus();
			return;
		}
		
		if(editOK.sur_sat_date.value > editOK.sur_end_date.value){
			alert("���۳�¥�� ���ᳯ¥���� ���ž߾� �մϴ�.");
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
			editOK.sur_end_date.focus();
			return;
		}
		
		var num = $("#que_cnt").text();
		
		for(var i=1; i<=num; i++){
			
			var titleTxt = document.getElementById("surq_item"+i);
			var item1 = document.getElementById("item1"+i);
			var item2 = document.getElementById("item2"+i);
			var item3 = document.getElementById("item3"+i);
			var item4 = document.getElementById("item4"+i);
			var item5 = document.getElementById("item5"+i);
			
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


<div id="container">
	<div id="contents">
	
		<p><img src="./images/sub/particiation/sub_vimg_01.jpg" alt="�ǰ��� �޽� �ູ�� �б�" /></p>
		
		<!-- �����޴� -->
		<jsp:include page="/view/include/menu6/researchLnb.jsp"/>
		<!-- .//�����޴� -->
		
	
		<!-- ���� ������ -->
		<div class="right_box">
			
			<!-- ������� ���� -->
			<h3><img src="./images/sub/particiation/title_05.gif" alt="��������" /></h3>
			<!-- .//������� ���� -->
			
			
			<!-- ������� ��� ���� -->
			<p class="history"><img src="./images/sub/history_home.gif" alt="home" /> �������� <img src="./images/sub/history_arrow.gif" alt="����" /> <strong>��������</strong></p>
       		<p class="pt30"></p>
			<!-- .//������� ��� ���� -->
			
			<!-- �Խ��ǿ��� -->
			<div class="tbl_box">
				<form name="editOK" action="/foodSen/researchEdit.do" method="post">
				
					<table width="100%" border="0" cellspacing="0" cellpadding="0" class="tbl_type01" summary="��������">
					
			            <caption>��������</caption>
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
								<td colspan="5" class="tl"><input type="text" id="sur_title" name="sur_title" class="inp" value="${resultClass.sur_title}"/></td>
							</tr>
							<tr>
								<th>������</th>
								<td class="tl">
									<c:if test="${current_date < resultClass.sur_sat_date}">
										<input type="text" id="sur_sat_date" name="sur_sat_date" class="inp" style="width:100px;" value="${resultClass.sur_sat_date}" readonly/>
									</c:if>
									<c:if test="${resultClass.sur_sat_date <= current_date && current_date <= resultClass.sur_end_date}">
										${resultClass.sur_sat_date}
										<input type="hidden" id="sur_sat_date" name="sur_sat_date" class="inp" style="width:100px;" value="${resultClass.sur_sat_date}" readonly/>
									</c:if>
								</td>
								<th>������</th>
								<td class="tl">
									<input id="sur_end_date" name="sur_end_date" class="inp" style="width:100px;" value="${resultClass.sur_end_date}" readonly/>
								</td>
								<th>���׼�</th>
								<td class="tl">
									${resultClass.que_cnt}
								</td>
							</tr>
							<tr>
								<th>����</th>
								<td colspan="5" class="tl">
									
									<c:forEach var="i" begin="0" end="${cnt-1}" step="1"> 
										<div class="research">
											
											<!-- ������ -->
											<input type="hidden" id="sur_seq" name="sur_seq" value="${sur_seq}" />
											<input type="hidden" id="cnt" name="cnt" value="${cnt}" />
											<input type="hidden" id="current_date" name="current_date" value="${current_date}" />
											<input type="hidden" id="searchingNow" name="searchingNow" value="${searchingNow}" />
											<c:if test="${searchingNow == 1}">
												<input type="hidden" id="searchType" name="searchType" value="${searchType}" />
												<input type="hidden" id="userinput" name="userinput" value="${userinput}" />
											</c:if>
											<!-- .//������ -->
											
											<!-- �����ǽ����� -->
											<input type="hidden" id="resultClass1_seq${i+1}" name="resultClass1_seq${i+1}" value="${resultClass1_seq[i]}" />
											<!-- �����ǽ����� -->
											<input type="hidden" id="resultClass2_seq${i+1}" name="resultClass2_seq${i+1}" value="${resultClass2_seq[i]}" />
											
											
											<!-- ���� -->
											<c:if test="${permit == 0}">
												<p>${i+1}. <input type="text" id="surq_title${i+1}" name="surq_title${i+1}" value="${title[i]}" /></p>
											</c:if>
											<c:if test="${permit == 1}">
												<p>${i+1}. "${title[i]}"</p>
											</c:if>
											<!-- .//���� -->
											
											<!-- ���� -->
											<input type="hidden" id="surq_seqItem${i+1}" name="surq_seqItem" class="txt" value="${i+1}">
											<%-- <input type="hidden" id="suri_seqItem${j.count }" name="suri_seqItem" class="inp" value="${re.suri_seq}"/> --%>
											<input type="hidden" id="surq_item${i+1}" name="surq_item" value="${title[i]}">
											
											
											<c:if test="${permit == 0}">
												<ul>
													<li>�� <input type="text" id="item1${i+1}" name="item1${i+1}" value="${i_title1[i]}" /> </li>
													<li>�� <input type="text" id="item2${i+1}" name="item2${i+1}" value="${i_title2[i]}" /> </li>
													<li>�� <input type="text" id="item3${i+1}" name="item3${i+1}" value="${i_title3[i]}" /> </li>
													<li>�� <input type="text" id="item4${i+1}" name="item4${i+1}" value="${i_title4[i]}" /> </li>
													<li>�� <input type="text" id="item5${i+1}" name="item5${i+1}" value="${i_title5[i]}" /> </li>
												</ul>
											</c:if>
											
											<c:if test="${permit == 1}">
												<ul>
													<li>�� ${i_title1[i]}<input type="hidden" id="item1${i+1}" name="item1${i+1}" value="${i_title1[i]}" /> </li>
													<li>�� ${i_title2[i]}<input type="hidden" id="item2${i+1}" name="item2${i+1}" value="${i_title2[i]}" /> </li>
													<li>�� ${i_title3[i]}<input type="hidden" id="item3${i+1}" name="item3${i+1}" value="${i_title3[i]}" /> </li>
													<li>�� ${i_title4[i]}<input type="hidden" id="item4${i+1}" name="item4${i+1}" value="${i_title4[i]}" /> </li>
													<li>�� ${i_title5[i]}<input type="hidden" id="item5${i+1}" name="item5${i+1}" value="${i_title5[i]}" /> </li>
												</ul>
											</c:if>
											<!-- .//���� -->
											
										</div>
									</c:forEach> 
									
								</td>
							</tr>
						</tbody>
					</table>
					
				</form>

				<p class="pt40"></p>
				
				<!--��ư--> 
				<span class="bbs_btn"> 
					<span class="wte_l"><a href="researchList.do" class="wte_r">���</a></span>
					<span class="per_l"><a href="javascript:goEdit()" class="pre_r">����</a></span>
				</span> 
				<!--.//��ư--> 
        
			</div>
			<!--.//�Խ��ǿ��� -->
			
		</div>
		<!--.//���������� -->
    
		<p class="bottom_bg"></p>
		
	</div>
</div>

<form name="deleteOK" action="researchDelete.do" method="post">
 	<input type="hidden" id="sur_seq" name="sur_seq" value="${resultClass.sur_seq}" />
 	<input type="hidden" name="url" id="url" value="research">
</form>

<jsp:include page="../../include/footer.jsp"/>