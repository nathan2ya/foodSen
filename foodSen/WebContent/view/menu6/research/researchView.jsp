<%@ page language="java" contentType="text/html; charset=EUC-KR"
    pageEncoding="EUC-KR"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt_rt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<jsp:include page="../../include/top.jsp"/>

<script>
	//����
	function goEdit(cnt){
		var sur_seq = "${resultClass.sur_seq}"; //�̱��� ������(����)
		var res_cnt = "${res_cnt}"; //�̱��� �����ϰ���
		var permit = 0; //0�̸� �����������, 1�̸� ��������Ұ�
		var current_date = "${current_date}"; //���ó�¥
		var sur_end_date = "${resultClass.sur_end_date}"; //����������
		var searchType = "${searchType}";
		var userinput = "${userinput}";
		var searchingNow = "${searchingNow}";
		
		if(sur_end_date < current_date){
			alert("�������簡 ����Ǹ� ������ �Ұ����մϴ�.");
			return;
		}
		
		if(res_cnt > 0){
			permit = 1;
			if(confirm("��������� �����Ͽ� �����Ͻǰ�� ���������� �����Ͱ� �����˴ϴ�. \n �Խñ��� �����Ͻðڽ��ϱ�?")!=0){
				location.href = '/foodSen/researchEditForm.do?sur_seq='+sur_seq+'&permit='+permit+'&searchType='+searchType+'&userinput='+userinput+'&searchingNow='+searchingNow;
			}else{
				return;
			}
		}
		
		location.href = '/foodSen/researchEditForm.do?sur_seq='+sur_seq+'&permit='+permit+'&searchType='+searchType+'&userinput='+userinput+'&searchingNow='+searchingNow;
	}
	
	//����
	function goDelete(){
		var current_date = "${current_date}"; //���ó�¥
		var sur_sat_date = "${resultClass.sur_sat_date}"; //����������
		var sur_end_date = "${resultClass.sur_end_date}"; //����������
		var res_cnt = "${res_cnt}"; //�̱��� �����ϰ���
		
		if(current_date < sur_sat_date || sur_end_date < current_date){  //���������ʾҰų�, ���������
			if(confirm("�Խñ��� �����Ͻðڽ��ϱ�?")!=0){
				deleteOK.submit();
			}else{
				return;
			}
		}else{
			if(res_cnt > 0){
				if(confirm("�������� �����ڰ� �ֽ��ϴ�. �����Ұ�� ��� �����Ͱ� �����˴ϴ�. \n �׷��� �����Ͻðڽ��ϱ�?")!=0){
					deleteOK.submit();
				}else{
					return;
				}
			}else{
				if(confirm("�������� �������Դϴ�. \n �׷��� �����Ͻðڽ��ϱ�?")!=0){
					deleteOK.submit();
				}else{
					return;
				}
			}
		}
		
	}
	
	//�����ü����
	function goResultView(){
		var sur_seq = "${resultClass.sur_seq}";
		
		//url
		url = '/foodSen/researchResult.do?sur_seq='+sur_seq;
		// ���ο� �����츦 ���ϴ�.
		open(url,"confirm","toolbar=no,location=no,status=no,menubar=no,scrollbars=yes,resizable=yes,width=550,height=545");
	}
	
	//������ü����
	function goReason(sur_seq){
		var sur_seq = "${resultClass.sur_seq}";
		
		//url
		url = '/foodSen/researchResult1.do?sur_seq='+sur_seq;
		// ���ο� �����츦 ���ϴ�.
		open(url,"confirm","toolbar=no,location=no,status=no,menubar=no,scrollbars=yes,resizable=yes,width=500,height=450");
	}
	
	function goAlert(){
		var sur_sat_date = "${resultClass.sur_sat_date}"; //����������
		alert("�������簡 ���۵Ǿ�� ��������� �����մϴ�. \n �������� �������� "+sur_sat_date+" �Դϴ�.");
	}
	
	//����
	function goSave() {
		var current_date = "${current_date}"; //���ó�¥
		var sur_sat_date = "${resultClass.sur_sat_date}"; //����������
		var sur_end_date = "${resultClass.sur_end_date}"; //����������
		var canSave = "${canSave}"; //��������0, �����Ұ���1
		
		if(canSave == 1){
			alert("�̹� �������翡 �����ϼ̽��ϴ�. \n ��������� 1���� �����Ͻ� �� �ֽ��ϴ�.");
			return;
		}
		
		if(current_date < sur_sat_date){//�������� �������϶�
			alert("������ ��������� �Ⱓ�� �ƴմϴ�. \n �������ڴ� "+sur_sat_date+" �Դϴ�.");
			return;
		}
		if(sur_end_date < current_date){//�������� ����Ǿ�����
			alert("�������簡 ����Ǿ� �����Ͻ� �� �����ϴ�.");
			return;
		}
		
		
		var selectYn = "Y";
		var lengthMax = "Y";
		var lengthMaxTxt = "";
		$('.txt').each(function(index, item){
			/* ��繮���� �����ؾ� �������� ������ �����ϵ��� �Ϸ��� �� �ּ��� �����Ұ�
			if($('input:radio[name=suri_numItem'+(index+1)+']:checked').val() == "undefined" || $('input:radio[name=suri_numItem'+(index+1)+']:checked').val() == "" || $('input:radio[name=suri_numItem'+(index+1)+']:checked').val() == null)
			{
				selectYn = "N";
			}
			 */
			if(getStrByte($('#descriptionItem'+(index+1)).val()) > 120){
				lengthMaxTxt = $('#descriptionItem'+(index+1));
				//$('#descriptionItem'+(index+1)).val($('#descriptionItem'+(index+1)).val().cut(120));
				$('#descriptionItem'+(index+1)).focus();
				lengthMax = "N";
				return;
			}
		});
		
		if(selectYn == "N"){
			alert("���õ��� ���� ������ �����մϴ�.");
			return;
		}
		if(lengthMax == "N"){
			alert("���û����� 100�� ������ �Է��� �� �ֽ��ϴ�.");
			lengthMaxTxt.focus();
			return;
		}
		saveOK.submit();
		alert("������ �Ϸ�Ǿ����ϴ�.\n�����Ͽ� �ּż� �����մϴ�.");
	}
	
	function getStrByte(str){
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
	
	

	$(document).ready(function(){
		$('#goSave').click(function(){
			var surq_seqItem = "";
			var surq_item = "";
			var suri_seqItem = "";
			var suri_numItem = "";
			var descriptionItem = "";
			
			$('.txt').each(function(index, item){
				surq_seqItem += $('#surq_seqItem'+(index+1)).val() + "|";
				surq_item += $('#surq_item'+(index+1)).val() + "|";
				suri_seqItem += $('#suri_seqItem'+(index+1)).val() + "|";
				suri_numItem += $('input:radio[name=suri_numItem'+(index+1)+']:checked').val() + "|";
				if($('#descriptionItem'+(index+1)).val() != ""){
					descriptionItem += $('#descriptionItem'+(index+1)).val() + "|";
				}else{
					descriptionItem += " |";
				}
			});
			$('.surq_seqItem').val(surq_seqItem);
			$('.surq_item').val(surq_item);
			$('.suri_seqItem').val(suri_seqItem);
			$('.suri_numItem').val(suri_numItem);
			$('.descriptionItem').val(descriptionItem);
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
							<td colspan="5" class="tl"><strong>${resultClass.sur_title}</strong></td>
						</tr>
						<tr>
							<th>������</th>
							<td class="tl">${resultClass.sur_sat_date}</td>
							<th>������</th>
							<td class="tl">${resultClass.sur_end_date}</td>
							<th>���׼�</th>
							<td class="tl">${resultClass.que_cnt}</td>
						</tr>
						<tr>
							<th>����</th>
							<td colspan="5" class="tl">
								
								<c:forEach var="i" begin="0" end="${cnt-1}" step="1"> 
									<div class="research">
										<p>${i+1}. ${title[i]}</p>
										<input type="hidden" id="surq_seqItem${i+1}" name="surq_seqItem" class="txt" value="${i+1}">
										<input type="hidden" id="surq_item${i+1}" name="surq_item" value="${title[i]}">
										<ul>
											<c:if test="${i_title1[i] ne null}">
												<li><input type="radio" id="suri_num${i+1}" name="suri_numItem${i+1}" value="�� ${i_title1[i]}" />  <label for="suri_num${i+1}">��  ${i_title1[i]} </label></li>
											</c:if>
											<c:if test="${i_title2[i] ne null}">
												<li><input type="radio" id="suri_num${i+1}" name="suri_numItem${i+1}" value="�� ${i_title2[i]}" />  <label for="suri_num${i+1}">��  ${i_title2[i]} </label></li>
											</c:if>
											<c:if test="${i_title3[i] ne null}">
												<li><input type="radio" id="suri_num${i+1}" name="suri_numItem${i+1}" value="�� ${i_title3[i]}" />  <label for="suri_num${i+1}">��  ${i_title3[i]} </label></li>
											</c:if>
											<c:if test="${i_title4[i] ne null}">
												<li><input type="radio" id="suri_num${i+1}" name="suri_numItem${i+1}" value="�� ${i_title4[i]}" />  <label for="suri_num${i+1}">��  ${i_title4[i]} </label></li>
											</c:if>
											<c:if test="${i_title5[i] ne null}">
												<li><input type="radio" id="suri_num${i+1}" name="suri_numItem${i+1}" value="�� ${i_title5[i]}" />  <label for="suri_num${i+1}">��  ${i_title5[i]} </label></li>
											</c:if>
											<li>���û��� <input type="text" id="descriptionItem${i+1 }" name="descriptionItem" class="inp" style="width:200px;" /> </li>
										</ul>
									</div>
								</c:forEach>  
								                  
							</td>
						</tr>
					</tbody>
				</table> 

				<p class="pt40"></p>
				
				<!--��ư--> 
				<span class="bbs_btn"> 
				
					<span class="wte_l"><a href="researchList.do" class="wte_r">���</a></span>
					<c:if test="${sessionScope.session_admin_yn == 'y'}">
						<span class="wte_l"><a href="javascript:goEdit('${resCnt}')" class="wte_r">����</a></span>
						<span class="wte_l"><a href="javascript:goDelete()" class="wte_r">����</a></span>
					</c:if>
					
					<c:if test="${sessionScope.session_admin_yn != 'y'}">
						<span class="per_l"><a href="javascript:goSave()" class="pre_r" id="goSave">����</a></span>
					</c:if>
					
					<c:if test="${sessionScope.session_admin_yn == 'y'}">
						<!-- ������ -->
						<c:if test="${current_date < resultClass.sur_sat_date}">
							<span class="wte_l"><a href="javascript:goAlert()" class="wte_r">�������</a></span>
							<span class="wte_l"><a href="javascript:goAlert()" class="wte_r">������ü����</a></span>
						</c:if>
						
						<!-- ������ -->
						<c:if test="${resultClass.sur_sat_date <= current_date && current_date <= resultClass.sur_end_date}">
							<span class="wte_l"><a href="javascript:goResultView()" class="wte_r">�������</a></span>
							<span class="wte_l"><a href="javascript:goReason()" class="wte_r">������ü����</a></span>
						</c:if>
						
						<!-- �Ϸ�� -->
						<c:if test="${resultClass.sur_end_date < current_date}">
							<span class="wte_l"><a href="javascript:goResultView()" class="wte_r">�������</a></span>
							<span class="wte_l"><a href="javascript:goReason()" class="wte_r">������ü����</a></span>
						</c:if>
					</c:if>

				</span> 
				<!--.//��ư--> 
        
			</div>
			<!--.//�Խ��ǿ��� -->
			
		</div>
		<!--.//���������� -->
    
		<p class="bottom_bg"></p>
		
	</div>
</div>

<form name="saveOK" action="/foodSen/researchSave.do" method="post">
	<input type="hidden" id="sur_seq" name="sur_seq" value="${resultClass.sur_seq}" />
	<input type="hidden" id="surq_seqItem" name="surq_seqItem" class="surq_seqItem" />
	<input type="hidden" id="surq_item" name="surq_item"  class="surq_item"/>
	<input type="hidden" id="suri_seqItem" name="suri_seqItem" class="suri_seqItem"/>
	<input type="hidden" id="suri_numItem" name="suri_numItem" class="suri_numItem"/>
	<input type="hidden" id="descriptionItem" name="descriptionItem" class="descriptionItem"/>
</form>


<form name="deleteOK" action="/foodSen/researchDelete.do" method="post">
	<input type="hidden" id="sur_seq" name="sur_seq" value="${resultClass.sur_seq}" />
</form>

<jsp:include page="../../include/footer.jsp"/>