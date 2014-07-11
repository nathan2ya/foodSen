<%@ page language="java" contentType="text/html; charset=EUC-KR"
    pageEncoding="EUC-KR"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt_rt" %>



<jsp:include page="/view/include/top.jsp"/>
<link href="/css/base.css" rel="stylesheet" type="text/css" />
<link href="/css/common.css" rel="stylesheet" type="text/css" />





<script type="text/javascript">
	function goDelete(){
		var seq = "${seq}"; // �������� �������ѹ�
		var pw = "${resultClass.pw}"; // �������� ����������
		
		//url
		url="/foodSen/improvementCaseDeleteForm.do?seq="+seq+"&pw="+pw;
		// ���ο� �����츦 ���ϴ�.
   		open(url,"confirm","toolbar=no,location=no,status=no,menubar=no,scrollbars=no,resizable=no,width=500,height=220");
	}
	
	function goEdit(){
		var seq = "${seq}"; // �������� �������ѹ�
		var currentPage = "${currentPage}"; // �������� ����������
		var searchingNow = "${searchingNow}"; // �������� �˻����� ����
		
		location.href='/foodSen/improvementCaseEditFrom.do?seq='+seq+'&currentPage='+currentPage+'&searchingNow='+searchingNow;
	}
	
	$(document).ready(function(){
		autolink(document.getElementById("description"));
	});
	
	function autolink(id) {
		
	    var container = id;
	    var doc = container.innerHTML;
	    var regURL = new RegExp("(http|https|ftp|telnet|news|irc)://([-/.a-zA-Z0-9_~#%$?&=:200-377()]+)","gi");
	    var regEmail = new RegExp("([xA1-xFEa-z0-9_-]+@[xA1-xFEa-z0-9-]+\.[a-z0-9-]+)","gi");
	
	    container.innerHTML = doc.replace(regURL,"<a href='$1://$2' target='_blank'>$1://$2</a>").replace(regEmail,"<a href='mailto:$1'>$1</a>");
	}
	
	
	//�̹��� ü����
	function chgImg(obj,path){
		$('#preview').attr('src',path+obj);
	}
	
	//�̹��� �˾�����
	function openPrev(seq){
		
		var frm = document.prevImg;    //�˾��� �ѱ� �θ�â�� ��
		frm.seq.value = seq;           //���� ������ �����Ѵ�.  

		//���������� �˾�â�� �켱 �ϳ� ����.
		window.open('', 'popup_post', 'width=408, height=415, resizable= yes');

		//�θ�â�� Ÿ���� ���������� ��� �˾�â�� �̸����� �Ѵ�
		frm.target = 'popup_post';   

		//�ѱ� ���� action�� �˾��� ��Ÿ�� �������� �Ѵ�.
		frm.action = 'improvementCasePrevImg.do';

		//�˾����� �ѱ� ���� �������ִ� ���� submit �Ѵ�.
		frm.submit();
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
			
			
			<!-- �����Խ��ǿ��� Ʋ -->
			<div class="img_list">
				
				
				<!-- �̹������� -->
				<div class="img_box">
				
					<span>
						<c:if test="${resultClass.img1 != null}">
							<a href="javascript:openPrev('${seq}')">
								<img id="preview" src="${resultClass.attach_path}${resultClass.img1}" alt="" />
							</a>
						</c:if>
						<c:if test="${resultClass.img1 == null}">
							<li class="prn"><img src="./images/sub/factory/no_s_img.gif" alt="" /></li>
						</c:if>
					</span>
					
					<ul>
						<c:if test="${resultClass.img1 != null}">
							<li><a href="javascript:chgImg('${resultClass.img1}','${resultClass.attach_path}')"><img src="${resultClass.attach_path}${resultClass.img1}" alt="ù��°�̹���" /></a></li>
						</c:if>
						<c:if test="${resultClass.img1 == null}">
							<li class="prn"><img src="./images/sub/factory/no_s_img.gif" alt="" /></li>
						</c:if>
						
						<c:if test="${resultClass.img2 != null}">
							<li><a href="javascript:chgImg('${resultClass.img2}','${resultClass.attach_path}')"><img src="${resultClass.attach_path}${resultClass.img2}" alt="�ι�°�̹���" /></a></li>
						</c:if>
						<c:if test="${resultClass.img2 == null}">
							<li class="prn"><img src="./images/sub/factory/no_s_img.gif" alt="" /></li>
						</c:if>
						
						<c:if test="${resultClass.img3 != null}">
							<li class="prn"><a href="javascript:chgImg('${resultClass.img3}','${resultClass.attach_path}')"><img src="${resultClass.attach_path}${resultClass.img3}" alt="����°�̹���" /></a></li>
						</c:if>
						<c:if test="${resultClass.img3 == null}">
							<li class="prn"><img src="./images/sub/factory/no_s_img.gif" alt="" /></li>
						</c:if>
						
						<c:if test="${resultClass.img4 != null}">
							<li ><a href="javascript:chgImg('${resultClass.img4}','${resultClass.attach_path}')"><img src="${resultClass.attach_path}${resultClass.img4}" alt="�׹�°�̹���" /></a></li>
						</c:if>
						<c:if test="${resultClass.img4 == null}">
							<li class="prn"><img src="./images/sub/factory/no_s_img.gif" alt="" /></li>
						</c:if>
						
						<c:if test="${resultClass.img5 != null}">
							<li><a href="javascript:chgImg('${resultClass.img5}','${resultClass.attach_path}')"><img src="${resultClass.attach_path}${resultClass.img5}" alt="�ټ���°�̹���" /></a></li>
						</c:if>
						<c:if test="${resultClass.img5 == null}">
							<li class="prn"><img src="./images/sub/factory/no_s_img.gif" alt="" /></li>
						</c:if>
						
						<c:if test="${resultClass.img6 != null}">
							<li><a href="javascript:chgImg('${resultClass.img6}','${resultClass.attach_path}')"><img src="${resultClass.attach_path}${resultClass.img6}" alt="�ټ���°�̹���" /></a></li>
						</c:if>
						<c:if test="${resultClass.img6 == null}">
							<li class="prn"><img src="./images/sub/factory/no_s_img.gif" alt="" /></li>
						</c:if>
						
					</ul>

				</div>
				<!-- .//�̹������� -->
				
			
				<!-- �Խ��ǿ��� -->
				<div class="tbl_box w490 fl">
					
					<table width="100%" border="0" cellspacing="0" cellpadding="0" class="tbl_type01" summary="�޽Ľü��������">
						<caption>�޽Ľü��������</caption>
						<colgroup>
							<col width="15%" />
							<col width="20%" />
							<col width="15%" />
							<col width="20%" />
							<col width="15%" />
							<col width="%" />
						</colgroup>
						<tbody>
							<tr>
								<th>����</th>
								<td colspan="5" class="tl"><strong>${resultClass.title}</strong></td>
							</tr>
							<tr>
								<th>�����</th>
								<td>${resultClass.wirte}</td>
								<th>�����</th>
								<td><fmt:formatDate value="${resultClass.reg_date}" pattern="yyyy-MM-dd" /></td>
								<th>��ȸ��</th>
								<td>${resultClass.hits}</td>
							</tr>
							<tr>
								<th>����</th>
								<td id="description" colspan="5" class="tl h150"><pre>${resultClass.description}</pre></td>
							</tr>
							<tr>
								<th>÷������</th>
								<td colspan="5" class="tl">
									<c:if test="${resultClass.attach_name != null}">
										<a href="/foodSen/improvementCase_FileDownload.do?attach_name=${resultClass.attach_name}">
											${resultClass.attach_name}
											<img src="./images/sub/btn/btn_down.gif" alt="pdf" />
										</a>
									</c:if>
								</td>
							</tr>
						</tbody>
					</table>
				
					<p class="pt40"></p>
				
					<!-- ��ư -->
					<span class="bbs_btn"> 
						<c:if test="${searchingNow == 0}">
							<span class="wte_l"><a href="/foodSen/improvementCaseList.do?currentPage=${currentPage}" class="wte_r">���</a></span>
						</c:if>
						<c:if test="${searchingNow == 1}">
							<span class="wte_l"><a href="/foodSen/improvementCaseSearch.do?searchType=${searchType}&userinput=${userinput}&currentPage=${currentPage}" class="wte_r">���</a></span>
						</c:if>
						
						
						<c:if test="${sessionScope.session_position == 1 || sessionScope.session_position == 2}">
							<span class="per_l"><a href="javascript:goEdit()" class="pre_r">����</a></span>
							<span class="wte_l"><a href="javascript:goDelete()" class="wte_r">����</a></span>
						</c:if>
					</span>
					<!-- //��ư -->
				
				
				</div>
				<!-- .//�Խ��ǿ��� -->
				
			</div>
			<!-- .//�����Խ��ǿ��� Ʋ -->
			
			
			
		</div>
		<!-- .//���� ������ -->
		
		<p class="bottom_bg"></p>
		
	</div>
</div>

 <form name="prevImg" method="post">
 	<input type="hidden" id="seq" name="seq" value="${seq}">
 </form>

<jsp:include page="/view/include/footer.jsp"/>