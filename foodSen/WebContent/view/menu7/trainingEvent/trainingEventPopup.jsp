<%@ page language="java" contentType="text/html; charset=EUC-KR"
    pageEncoding="EUC-KR"%>
    

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=EUC-KR">
<title>연수행사</title>
</head>
<link href="./css/base.css" rel="stylesheet" type="text/css" />
<link href="./css/common.css" rel="stylesheet" type="text/css" />
<body>
	<script type="text/javascript" src="http://code.jquery.com/jquery-1.10.2.min.js"></script>
	<script type="text/javascript">

	$(document).ready(function(){
		
		var cnt=$('#cnt').val();
		
		$('#prev').click(function(){
			var offset=$('#img').offset();
	
			if(offset.left < 8){
				offset.left += 350;
			}
			
			$('#img').animate(function(){
				left: '+= 350';
			}, 400);
			
			$('#img').offset(offset);
		});
		
		$('#next').click(function(){
			var offset=$('#img').offset();
			//alert(offset.left);
			if(offset.left > -(356*(cnt-1))+60){
				offset.left -= 350;
			}
	
			$('#img').animate(function(){
				left: '-= 350';
			},400);
			
			$('#img').offset(offset);
		});
	});
	
</script>

	<input type="hidden" id="cnt" name="cnt" value="${cnt}">
	
	<!-- w408 h426px -->
	<div class="pop">
	
		<!-- 팝업박스 -->
		<div class="pop_box">
		
			<h1>연수행사</h1>
			<div class="img_box">
				<ul id="img" class="img_box_list">
					<li><img src="${resultClass.img1}" alt="" /></li>
					<li><img src="${resultClass.img2}" alt="" /></li>
					<li><img src="${resultClass.img3}" alt="" /></li>
				</ul>

				<div class="btn_box">
					<span class="btn_l">
						<img src="./images/sub/factory/pre_01.gif" alt="이전" /> <a id="prev" href="#">이전</a>
					</span> 
					<span class="btn_r">
						<a id="next" href="#">다음</a> <img src="./images/sub/factory/next_01.gif" alt="다음" />
					</span>
				</div>
			</div>
			<p class="pt20"></p>
			<div class="pop_btn">
				<span class="gray_l">
					<a href="javascript:self.close();" class="gray_r">닫기</a>
				</span>
			</div>
			
		</div>
		<!-- .//팝업박스 -->

	</div>
	<!-- .//w408 h426px -->
</body>
</html>