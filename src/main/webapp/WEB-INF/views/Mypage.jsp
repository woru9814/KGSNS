<%@page import="com.finalproject.sns.vo.MemVO"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>my page</title>
<link rel="stylesheet" href="<c:url value="/resources/css/mypage.css"/>">



<style>
	#profile {cursor:pointer;}
</style>
</head>
<body>
	<!--  header.jsp들어갈 부분 -->


	<hr>
	<form name="update" action="updateProfile.do" method="post" enctype="multipart/form-data">
	<input type="hidden" name="id" value="${loginVO.id}" >
	<table border="0" align="center">
		<tr>
			<td colspan="2" class="title">My Page</td>
		</tr>
		<tr>
			<td colspan="2" class="labelpic"><input type="file" id="upload" name="file" class="input" accept="image/*" style="display:none;">
			<img id="profile" src="<c:url value="/profile/${loginVO.memimg }"/>" width="200px" height="200px"
				onclick="imgSelect();"></td>
		</tr>
		</table>
		
		<table class="table2" border="0" align="center">
		<tr>
			<td class="labelid">● ID</td>
			<td class="updateid" colspan=2 align="left">${loginVO.id}</td>
		</tr>

		<tr>
			<td class="labelname">● 이름</td>
			<td class="updatename" colspan=2 align="left">${loginVO.memname}</td>
		</tr>
		<tr>
			<td class="labelemail">● 이메일</td>
			<td class="updateemail" colspan=2 align="left">${loginVO.email}</td>
		</tr>
	
	</table>
	
	</form>
	<br>
	<div class="btnwrap" align="center">
		<div align="center" class="updatebtn">
			<a href="#" class="Mypage">정보 수정</a>
		</div>
		<div align="center" class="deletebtn">
			<a onclick="return confirm('정말로 탈퇴하시겠습니까?');" href="userDelete.do?id=${loginVO.id }">회원 탈퇴</a>
		</div>
	</div>


	<hr>

	<!-- 작성할 게시글 들어갈 부분 -->
</body>
<script>
$('.Mypage').click(function(){
	$.ajax({
		url:"userUpdate.do",
		type:"get",
		data: {"" : ""},
		success : function(data){ 
			if(data){
				$("#centerMenu").empty();
				$("#centerMenu").append(data);
			} else{

			}
		},
		error : function(data){
		}
	});
});
function imgSelect(){
	$('#upload').click();
	
	var upload = document.querySelector('#upload');
	
	upload.addEventListener('change', function(e){
		var get_file = e.target.files;
		var reader = new FileReader();
		
		var image = document.querySelector('#profile');
		reader.onload = (function(aImg){
			
			return function(e){
				aImg.src = e.target.result
			}
		})(image)
		if(get_file){
			reader.readAsDataURL(get_file[0]);
		}
		
		document.update.submit();
	});
	
	
}

</script>
</html>