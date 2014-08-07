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
		
		if(!researchCreateForm.sur_end_date.value){
			alert("���ᳯ¥�� �����ϼ���.");
			researchCreateForm.sur_end_date.focus();
			return;
		}
		
		if(researchCreateForm.sur_sat_date.value > researchCreateForm.sur_end_date.value){
			alert("���ᳯ¥�� ���۳�¥���� �̷����� �մϴ�.");
			return;
		}
		
		var num=document.getElementById("que_cnt").value;
		
		for(var i=1; i<=num; i++){
			var titleTxt=document.getElementById("surq_item"+i);
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
								 		
								<div class="research suri1" style="display: none">
									<p>1. <input type="text" id="surq_item1" name="surq_item" class="inp"/></p>
									<ul>
										<li>�� <input type="text" id="item11" name="item1" class="inp i"/></li>
										<li>�� <input type="text" id="item21" name="item2" class="inp i"/></li>
										<li>�� <input type="text" id="item31" name="item3" class="inp i"/></li>
										<li>�� <input type="text" id="item41" name="item4" class="inp i"/></li>
										<li>�� <input type="text" id="item51" name="item5" class="inp i"/></li>
									</ul>
									<input type="hidden" id="surq_seqItem" name="surq_seqItem" value="1" />
								</div>
								
								<div class="research suri2" style="display: none">
									<p>2. <input type="text" id="surq_item2" name="surq_item" class="inp"/></p>
									<ul>
										<li>�� <input type="text" id="item12" name="item1" class="inp i"/></li>
										<li>�� <input type="text" id="item22" name="item2" class="inp i"/></li>
										<li>�� <input type="text" id="item32" name="item3" class="inp i"/></li>
										<li>�� <input type="text" id="item42" name="item4" class="inp i"/></li>
										<li>�� <input type="text" id="item52" name="item5" class="inp i"/></li>
									</ul>
									<input type="hidden" id="surq_seqItem" name="surq_seqItem" value="2" />
								</div>
								
								<div class="research suri3" style="display: none">
									<p>3. <input type="text" id="surq_item3" name="surq_item" class="inp"/></p>
									<ul>
										<li>�� <input type="text" id="item13" name="item1" class="inp i"/></li>
										<li>�� <input type="text" id="item23" name="item2" class="inp i"/></li>
										<li>�� <input type="text" id="item33" name="item3" class="inp i"/></li>
										<li>�� <input type="text" id="item43" name="item4" class="inp i"/></li>
										<li>�� <input type="text" id="item53" name="item5" class="inp i"/></li>
									</ul>
									<input type="hidden" id="surq_seqItem" name="surq_seqItem" value="3" />
								</div>
								
								<div class="research suri4" style="display: none">
									<p>4. <input type="text" id="surq_item4" name="surq_item" class="inp"/></p>
									<ul>
										<li>�� <input type="text" id="item14" name="item1" class="inp i"/></li>
										<li>�� <input type="text" id="item24" name="item2" class="inp i"/></li>
										<li>�� <input type="text" id="item34" name="item3" class="inp i"/></li>
										<li>�� <input type="text" id="item44" name="item4" class="inp i"/></li>
										<li>�� <input type="text" id="item54" name="item5" class="inp i"/></li>
									</ul>
									<input type="hidden" id="surq_seqItem" name="surq_seqItem" value="4" />
								</div>
								
								<div class="research suri5" style="display: none">
									<p>5. <input type="text" id="surq_item5" name="surq_item" class="inp"/></p>
									<ul>
										<li>�� <input type="text" id="item15" name="item1" class="inp i"/></li>
										<li>�� <input type="text" id="item25" name="item2" class="inp i"/></li>
										<li>�� <input type="text" id="item35" name="item3" class="inp i"/></li>
										<li>�� <input type="text" id="item45" name="item4" class="inp i"/></li>
										<li>�� <input type="text" id="item55" name="item5" class="inp i"/></li>
									</ul>
									<input type="hidden" id="surq_seqItem" name="surq_seqItem" value="5" />
								</div>
								
								<div class="research suri6" style="display: none">
									<p>6. <input type="text" id="surq_item6" name="surq_item" class="inp"/></p>
									<ul>
										<li>�� <input type="text" id="item16" name="item1" class="inp i"/></li>
										<li>�� <input type="text" id="item26" name="item2" class="inp i"/></li>
										<li>�� <input type="text" id="item36" name="item3" class="inp i"/></li>
										<li>�� <input type="text" id="item46" name="item4" class="inp i"/></li>
										<li>�� <input type="text" id="item56" name="item5" class="inp i"/></li>
									</ul>
									<input type="hidden" id="surq_seqItem" name="surq_seqItem" value="6" />
								</div>
								
								<div class="research suri7" style="display: none">
									<p>7. <input type="text" id="surq_item7" name="surq_item" class="inp"/></p>
									<ul>
										<li>�� <input type="text" id="item17" name="item1" class="inp i"/></li>
										<li>�� <input type="text" id="item27" name="item2" class="inp i"/></li>
										<li>�� <input type="text" id="item37" name="item3" class="inp i"/></li>
										<li>�� <input type="text" id="item47" name="item4" class="inp i"/></li>
										<li>�� <input type="text" id="item57" name="item5" class="inp i"/></li>
									</ul>
									<input type="hidden" id="surq_seqItem" name="surq_seqItem" value="7" />
								</div>
								
								<div class="research suri8" style="display: none">
									<p>8. <input type="text" id="surq_item8" name="surq_item" class="inp"/></p>
									<ul>
										<li>�� <input type="text" id="item18" name="item1" class="inp i"/></li>
										<li>�� <input type="text" id="item28" name="item2" class="inp i"/></li>
										<li>�� <input type="text" id="item38" name="item3" class="inp i"/></li>
										<li>�� <input type="text" id="item48" name="item4" class="inp i"/></li>
										<li>�� <input type="text" id="item58" name="item5" class="inp i"/></li>
									</ul>
									<input type="hidden" id="surq_seqItem" name="surq_seqItem" value="8" />
								</div>
								
								<div class="research suri9" style="display: none">
									<p>9. <input type="text" id="surq_item9" name="surq_item" class="inp"/></p>
									<ul>
										<li>�� <input type="text" id="item19" name="item1" class="inp i"/></li>
										<li>�� <input type="text" id="item29" name="item2" class="inp i"/></li>
										<li>�� <input type="text" id="item39" name="item3" class="inp i"/></li>
										<li>�� <input type="text" id="item49" name="item4" class="inp i"/></li>
										<li>�� <input type="text" id="item59" name="item5" class="inp i"/></li>
									</ul>
									<input type="hidden" id="surq_seqItem" name="surq_seqItem" value="9" />
								</div>
								
								<div class="research suri10" style="display: none">
									<p>10. <input type="text" id="surq_item10" name="surq_item" class="inp"/></p>
									<ul>
										<li>�� <input type="text" id="item110" name="item1" class="inp i"/></li>
										<li>�� <input type="text" id="item210" name="item2" class="inp i"/></li>
										<li>�� <input type="text" id="item310" name="item3" class="inp i"/></li>
										<li>�� <input type="text" id="item410" name="item4" class="inp i"/></li>
										<li>�� <input type="text" id="item510" name="item5" class="inp i"/></li>
									</ul>
									<input type="hidden" id="surq_seqItem" name="surq_seqItem" value="10" />
								</div>
								
								<div class="research suri11" style="display: none">
									<p>11. <input type="text" id="surq_item11" name="surq_item" class="inp"/></p>
									<ul>
										<li>�� <input type="text" id="item111" name="item1" class="inp i"/></li>
										<li>�� <input type="text" id="item211" name="item2" class="inp i"/></li>
										<li>�� <input type="text" id="item311" name="item3" class="inp i"/></li>
										<li>�� <input type="text" id="item411" name="item4" class="inp i"/></li>
										<li>�� <input type="text" id="item511" name="item5" class="inp i"/></li>
									</ul>
									<input type="hidden" id="surq_seqItem" name="surq_seqItem" value="11" />
								</div>
								
								<div class="research suri12" style="display: none">
									<p>12. <input type="text" id="surq_item12" name="surq_item" class="inp"/></p>
									<ul>
										<li>�� <input type="text" id="item112" name="item1" class="inp i"/></li>
										<li>�� <input type="text" id="item212" name="item2" class="inp i"/></li>
										<li>�� <input type="text" id="item312" name="item3" class="inp i"/></li>
										<li>�� <input type="text" id="item412" name="item4" class="inp i"/></li>
										<li>�� <input type="text" id="item512" name="item5" class="inp i"/></li>
									</ul>
									<input type="hidden" id="surq_seqItem" name="surq_seqItem" value="12" />
								</div>
								
								<div class="research suri13" style="display: none">
									<p>13. <input type="text" id="surq_item13" name="surq_item" class="inp"/></p>
									<ul>
										<li>�� <input type="text" id="item113" name="item1" class="inp i"/></li>
										<li>�� <input type="text" id="item213" name="item2" class="inp i"/></li>
										<li>�� <input type="text" id="item313" name="item3" class="inp i"/></li>
										<li>�� <input type="text" id="item413" name="item4" class="inp i"/></li>
										<li>�� <input type="text" id="item513" name="item5" class="inp i"/></li>
									</ul>
									<input type="hidden" id="surq_seqItem" name="surq_seqItem" value="13" />
								</div>
								
								<div class="research suri14" style="display: none">
									<p>14. <input type="text" id="surq_item14" name="surq_item" class="inp"/></p>
									<ul>
										<li>�� <input type="text" id="item114" name="item1" class="inp i"/></li>
										<li>�� <input type="text" id="item214" name="item2" class="inp i"/></li>
										<li>�� <input type="text" id="item314" name="item3" class="inp i"/></li>
										<li>�� <input type="text" id="item414" name="item4" class="inp i"/></li>
										<li>�� <input type="text" id="item514" name="item5" class="inp i"/></li>
									</ul>
									<input type="hidden" id="surq_seqItem" name="surq_seqItem" value="14" />
								</div>
								
								<div class="research suri15" style="display: none">
									<p>15. <input type="text" id="surq_item15" name="surq_item" class="inp"/></p>
									<ul>
										<li>�� <input type="text" id="item115" name="item1" class="inp i"/></li>
										<li>�� <input type="text" id="item215" name="item2" class="inp i"/></li>
										<li>�� <input type="text" id="item315" name="item3" class="inp i"/></li>
										<li>�� <input type="text" id="item415" name="item4" class="inp i"/></li>
										<li>�� <input type="text" id="item515" name="item5" class="inp i"/></li>
									</ul>
									<input type="hidden" id="surq_seqItem" name="surq_seqItem" value="15" />
								</div>
								
								<div class="research suri16" style="display: none">
									<p>16. <input type="text" id="surq_item16" name="surq_item" class="inp"/></p>
									<ul>
										<li>�� <input type="text" id="item116" name="item1" class="inp i"/></li>
										<li>�� <input type="text" id="item216" name="item2" class="inp i"/></li>
										<li>�� <input type="text" id="item316" name="item3" class="inp i"/></li>
										<li>�� <input type="text" id="item416" name="item4" class="inp i"/></li>
										<li>�� <input type="text" id="item516" name="item5" class="inp i"/></li>
									</ul>
									<input type="hidden" id="surq_seqItem" name="surq_seqItem" value="16" />
								</div>
								
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