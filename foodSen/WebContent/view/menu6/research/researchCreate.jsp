<%@ page language="java" contentType="text/html; charset=euc-kr"
	pageEncoding="euc-kr"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<jsp:include page="/view/include/top.jsp"/>
<link href="/css/base.css" rel="stylesheet" type="text/css" />
<link href="/css/common.css" rel="stylesheet" type="text/css" />

<script type="text/javascript" src="http://code.jquery.com/jquery-1.10.2.min.js"></script>
<script type="text/javascript" src="http://code.jquery.com/ui/1.10.3/jquery-ui.js"></script>
<link rel="stylesheet" type="text/css" href="http://code.jquery.com/ui/1.10.3/themes/smoothness/jquery-ui.css">
<script type="text/javascript">

	function goCreate(){
		var current_date = "${current_date}";
		
		if(!researchCreateForm.sur_title.value){
			alert("������ �Է��ϼ���.");
			researchCreateForm.sur_title.focus();
			return;
		}
		
		if(!researchCreateForm.sur_sat_date.value){
			alert("���۳�¥�� �����ϼ���.");
			researchCreateForm.sur_sat_date.focus();
			return;
		}
		
		if(researchCreateForm.sur_sat_date.value < current_date){
			alert("�������� �������� ������ ���ĸ� ��ϰ����մϴ�.");
			return;
		}
		
		if(researchCreateForm.sur_sat_date.value > researchCreateForm.sur_end_date.value){
			alert("���۳�¥�� ���ᳯ¥���� ���ſ��� �մϴ�.");
			researchCreateForm.sur_sat_date.focus();
			return;
		}
		
		if(!researchCreateForm.sur_end_date.value){
			alert("���ᳯ¥�� �����ϼ���.");
			researchCreateForm.sur_end_date.focus();
			return;
		}
		
		if(researchCreateForm.sur_sat_date.value > researchCreateForm.sur_end_date.value){
			alert("���ᳯ¥�� ���۳�¥���� �̷����� �մϴ�.");
			researchCreateForm.sur_end_date.focus();
			return;
		}
		
		var num=document.getElementById("que_cnt").value;
		
		for(var i=1; i<=num; i++){
			var titleTxt=document.getElementById("surq_title"+i);
			var item1=document.getElementById("item1"+i);
			var item2=document.getElementById("item2"+i);
			var item3=document.getElementById("item3"+i);
			var item4=document.getElementById("item4"+i);
			var item5=document.getElementById("item5"+i);
			
			//����, ������ �Է��� �ȵǾ�����
			if(titleTxt.value == ""){
				alert("����"+i+"���� �Է��ϼ���.");
				titleTxt.focus();
				return;
			}
			
			if(item1.value == ""){
				alert("����"+i+"���� 1�� �׸��� �Է��ϼ���. \n �� ���� 1���׸��� �ʼ��� �Է��ϼž� �մϴ�.");
				item1.focus();
				return;
			}
			
			if(item2.value == ""){
				alert("����"+i+"���� 2�� �׸��� �Է��ϼ���. \n �� ���� 2���׸��� �ʼ��� �Է��ϼž� �մϴ�.");
				item2.focus();
				return;
			}
			/* 
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
			 */
			
			//����, ������ ���ڼ��� �ʰ��Ǿ�����
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
		
		if(getStrByte(researchCreateForm.sur_title.value) > 200){
			alert("������ ���ڼ��� �ʰ� �Ǿ����ϴ�.");
			//researchCreateForm.sur_title.value = researchCreateForm.sur_title.value.cut(120);
			researchCreateForm.sur_title.focus();
			return;
		}
		
		researchCreateForm.submit();
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
				
				<form name="researchCreateForm" action="/foodSen/researchCreate.do" method="post">
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
								<td colspan="5" class="tl"><input type="text" id="sur_title" name="sur_title" class="inp" /></td>
							</tr>
							<tr>
								<th>������</th>
								<td class="tl"><input id="sur_sat_date" name="sur_sat_date" class="inp" style="width:100px;" readonly/></td>
								<th>������</th>
								<td class="tl"><input id="sur_end_date" name="sur_end_date" class="inp" style="width:100px;" readonly/></td>
								<th>���׼�</th>
								<td class="tl">
									<select id="que_cnt" name="que_cnt">
										<option value="1"/>1</option>
										<option value="2"/>2</option>
										<option value="3"/>3</option>
										<option value="4"/>4</option>
										<option value="5"/>5</option>
										<option value="6"/>6</option>
										<option value="7"/>7</option>
										<option value="8"/>8</option>
										<option value="9"/>9</option>
										<option value="10"/>10</option>
										<option value="11"/>11</option>
										<option value="12"/>12</option>
										<option value="13"/>13</option>
										<option value="14"/>14</option>
										<option value="15"/>15</option>
										<option value="16"/>16</option>
									</select>
								</td>
							</tr>
							<tr>
								<th>����</th>
								<td colspan="5" class="tl">
								
									<c:forEach var="q" begin="1" end="16" step="1" varStatus="i">
					       				<div class="research suri${i.index}" style="display: none">
					       				
					               	   		<!--����-->
					      					<p>${i.index }. <input type="text" id="surq_title${i.index}" name="surq_title${i.index}" class="inp"/></p>
					      					<!--.//����-->
					      					
					      					<!--����-->
					                        <ul>
						                        <li>�� <input type="text" id="item1${i.index}" name="item1${i.index}" class="inp i"/></li>
						                        <li>�� <input type="text" id="item2${i.index}" name="item2${i.index}" class="inp i"/></li>
						                        <li>�� <input type="text" id="item3${i.index}" name="item3${i.index}" class="inp i"/></li>
						                        <li>�� <input type="text" id="item4${i.index}" name="item4${i.index}" class="inp i"/></li>
						                        <li>�� <input type="text" id="item5${i.index}" name="item5${i.index}" class="inp i"/></li>
					                        </ul>
					                        <!--.//����-->
					                        
					                       	<!-- i������ �������� üũ -->
					                        <input type="hidden" id="surq_seqItem" name="surq_seqItem" value="${i.index}" />
					                        
										</div>
									</c:forEach>
								
								</td>
							</tr>
							
						</tbody>
					</table>
				</form>
				
				<p class="pt40"></p>
				
				<!--���.��� ��ư--> 
				<span class="bbs_btn"> 
				<span class="wte_l"><a href="/foodSen/researchList.do" class="wte_r">���</a></span> 
				<span class="per_l"><a href="javascript:goCreate()" class="pre_r">����</a></span>
				</span> 
				<!--.//���.��� ��ư--> 
			         
			</div>
		</div>
	</div>
<p class="bottom_bg"></p>
</div>


<jsp:include page="/view/include/footer.jsp"/>