<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%> 
<%@ taglib prefix="tf" tagdir="/WEB-INF/tags" %>
<!DOCTYPE html>
<html lang="ko">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>검증 구성 관리</title>
<link rel="stylesheet" href="/webjars/bootstrap/4.1.3/dist/css/bootstrap.min.css">
<link rel="stylesheet" href="<c:url value='/resources/css/NewFile.css'/>">
<link rel="stylesheet" href="//maxcdn.bootstrapcdn.com/font-awesome/4.3.0/css/font-awesome.min.css">
<!-- Custom styles for this template -->
<link href="/resources/css/logo-nav.css" rel="stylesheet">
<style>
body {margin: 10px; background-color: #ebebeb;}
.where {
	display: block;
	margin: 25px 15px;
	font-size: 11px;
	color: #000;
	text-decoration: none;
	font-style: italic;
} 

.filebox input[type="file"] {
    position: absolute;
    width: 1px;
    height: 1px;
    padding: 0;
    margin: -1px;
    overflow: hidden;
    clip:rect(0,0,0,0);
    border: 0;
}
.custom-file-input: (
	en: "Browse",
	kr: "찾기"
)

</style>
</head>
	<script src="/webjars/jquery/3.3.1-1/jquery.min.js"></script>
	<script src="/webjars/bootstrap/4.1.3/dist/js/bootstrap.min.js"></script>
	<script src="/webjars/bootstrap/4.1.3/dist/js/bootstrap.bundle.min.js"></script>
<script>
$(document).ready(function(){
	$('[data-toggle="popover"]').popover({
	      placement: 'top',
	      trigger: 'hover'
	});

	
	//document.getElementById('modelTable').getElementsByTagName('tbody')[0].width="5%";

	console.log(document.getElementById('modelTable').getElementsByTagName('tbody')[0].width);
	
	  var fileTarget = $('.upload-hidden');

	    fileTarget.on('change', function(){
	        if(window.FileReader){
	            var filename = $(this)[0].files[0].name;
	        } else {
	            var filename = $(this).val().split('/').pop().split('\\').pop();
	        }
	        document.getElementById('fileNameText').innerHTML = filename;
	        $(this).siblings('.custom-file-label').val(filename);
	    });
});
function addModel() {
	if(nullCheck() == true) {
		return;
	}
	var formData = new FormData($("#fileForm")[0]);
	console.log(formData.get('description'));
	$.ajax({
		type : 'post',
		url : '/addFile',
		data : formData,
		processData : false,
		contentType : false,
		async: false,
		success : function(mlModel) {
			var modelName = $('#modelName').val();
			var modelFile = $('#modelFile').val();
			var feature = $('#feature').val();
			
			var modelTable = document.getElementById('modelTable').getElementsByTagName('tbody')[0];
			var row = modelTable.insertRow(modelTable.rows.length); // 하단에 추가
		    row.id = mlModel.id;
		    var cell0 = row.insertCell(0);
		    var cell1 = row.insertCell(1);
		    var cell2 = row.insertCell(2);
		    var cell3 = row.insertCell(3);
		    var cell4 = row.insertCell(4);
		    var cell5 = row.insertCell(5);
		    var cell6 = row.insertCell(6);
		    
		    var d = new Date(mlModel.register_Time);
		    cell0.innerHTML = modelTable.rows.length;
		    cell1.innerHTML = d.getFullYear() + "/" + (d.getMonth() + 1) + "/" + d.getDate().toLocaleString('en-US', {minimumIntegerDigits: 2, useGrouping:false}) + " " + d.getHours().toLocaleString('en-US', {minimumIntegerDigits: 2, useGrouping:false}) + ":" + d.getMinutes().toLocaleString('en-US', {minimumIntegerDigits: 2, useGrouping:false});
		    cell2.innerHTML = "<input type='text' value='" + mlModel.name + "' readonly style='border:none; background-color:transparent'>";
		    cell3.innerHTML = "<input type='text' value='" + mlModel.file + "' readonly style='border:none; background-color:transparent;width:100%;'>";
		    cell4.innerHTML = "<input type='text' value='" + mlModel.feature + "'readonly style='border:none; background-color:transparent;width:100%;'>";
		    cell5.innerHTML = "<input type='text' value='" + mlModel.description + "'readonly style='border:none; background-color:transparent;width:100%;'>";
		    cell6.innerHTML = '<a href="#" onclick="editRow(this);"><span class="fa fa-pencil"></span></a>&nbsp;<a href="#" onclick="delete_row(this);"><span class="fa fa-times"></span></a>';

		    makeNull();

		},
		error : function(error) {
			alert("파일 업로드 실패");
			console.log(error);
			console.log(error.status);
		}
	})
}
function editRow(obj) {
	var tr = $(obj).parent().parent();
	tr[0].children[2].children[0].readOnly = false;
	tr[0].children[2].children[0].style.border = "1px solid black";
	tr[0].children[4].children[0].readOnly = false;
	tr[0].children[4].children[0].style.border = "1px solid black";
	tr[0].children[5].children[0].readOnly = false;
	tr[0].children[5].children[0].style.border = "1px solid black";
	tr[0].children[6].innerHTML = '<a href="#" onclick="completeRow(this);"><span class="fa fa-check"></span></a>';
	
	
}
function delete_row(obj) {
    var tr = $(obj).parent().parent();
	var formData = new FormData();
	formData.append('id', tr[0].id);

    $.ajax({
		type : 'post',
		url : '/deleteModel',
		data : formData,
		processData : false,
		contentType : false,
		async: false,
		success : function(mlModel) {
			console.log('success');
		}
    });

    //라인 삭제
    tr.remove();
}
function completeRow(obj) {
	var tr = $(obj).parent().parent();
	tr[0].children[2].children[0].readOnly = true;
	tr[0].children[2].children[0].style.border = "transparent";
	tr[0].children[4].children[0].readOnly = true;
	tr[0].children[4].children[0].style.border = "transparent";
	tr[0].children[5].children[0].readOnly = true;
	tr[0].children[5].children[0].style.border = "transparent";
	tr[0].children[6].innerHTML = '<a href="#" onclick="editRow(this);"><span class="fa fa-pencil"></span></a>&nbsp;<a href="#" onclick="delete_row(this);"><span class="fa fa-times"></span></a>';
	
	var formData = new FormData();
	formData.append('id', tr[0].id);
	formData.append('feature', tr[0].children[4].children[0].value);
	formData.append('name', tr[0].children[2].children[0].value);
	formData.append('description', tr[0].children[5].children[0].value)
    $.ajax({
		type : 'post',
		url : '/editModel',
		data : formData,
		processData : false,
		contentType : false,
		async: false,
		success : function(mlModel) {
			console.log('success');
		}
    });
}
function searchModel() {
	var formData = new FormData();
	formData.append('subject', $("#subject option:selected").val());
	formData.append('name', $("#searchText").val());
	formData.append('start_time',$("#start_time").val());
	formData.append('end_time', $("#end_time").val());
	$.ajax({
		type : 'post',
		url : '/searchModel',
		data : formData,
		processData : false,
		contentType : false,
		async: false,
		success : function(mlModel) {
			var modelTable = document.getElementById('modelTable');
			$("#modelTable > tbody").empty();
			for(var i = 0; i < mlModel.length; i++) {
				var row = modelTable.getElementsByTagName('tbody')[0].insertRow(modelTable.rows.length-2); // 하단에 추가
			    row.id = mlModel[i].id;
			    var cell0 = row.insertCell(0);
			    cell0.width = "5%";
			    var cell1 = row.insertCell(1);
			    cell1.width="15%";
			    var cell2 = row.insertCell(2);
			    cell2.width="15%";
			    var cell3 = row.insertCell(3);
			    cell3.width="20%";
			    var cell4 = row.insertCell(4);
			    cell4.width="40%";
			    var cell5 = row.insertCell(5);
			    cell5.width="5%";
			    var d = new Date(mlModel[i].register_Time);
			    cell0.innerHTML = mlModel.length-i;
			    cell1.innerHTML = d.getFullYear() + "/" + (d.getMonth() + 1) + "/" + d.getDate().toLocaleString('en-US', {minimumIntegerDigits: 2, useGrouping:false}) + " " + d.getHours().toLocaleString('en-US', {minimumIntegerDigits: 2, useGrouping:false}) + ":" + d.getMinutes().toLocaleString('en-US', {minimumIntegerDigits: 2, useGrouping:false});
			    
			    cell2.innerHTML = "<input type='text' value='" + mlModel[i].name + "' readonly style='border:none; background-color:transparent'>";
			    cell3.innerHTML = "<input type='text' value='" + mlModel[i].file + "' readonly style='border:none; background-color:transparent;width:100%;'>";
			    cell4.innerHTML = "<input type='text' value='" + mlModel[i].feature + "'readonly style='border:none; background-color:transparent;width:100%;'>";
			    cell5.innerHTML = '<a href="#" onclick="editRow(this);"><span class="fa fa-pencil"></span></a><a href="#" onclick="delete_row(this);"><span class="fa fa-times"></span></a>';
			}
			
		}
    });
}
function nullCheck() {
	if($("#modelName").val() == "") {
		alert("모델 이름을 작성하세요");
		return true;
	} else if($("#fileNameText").val() == "파일 찾기") {
		alert("학습 모델을 선택하세요");
		return true;
	} else if($("#feature").val() == "") {
		alert("테스트 항목을 작성하세요");
		return true;
	}
}

function makeNull() {
    $("#fileNameText").text("파일 찾기");
    $('#modelName').val('');
    $('#feature').val('');
    $('#description').val('');
    
}
</script>
<body>
	<%@include  file="header.jsp" %>

	<div class="container border card">
		<div class="card-body">
			<h5 class="card-title">검증 구성 관리</h5>
			<div class="card">
				<div class="card-body">
					<label for="date">등록 기간</label>
					&nbsp;<input type="date" class="form-control d-inline w-25" id="start_time">~<input type="date" class="form-control d-inline w-25" id="end_time">
					&nbsp;<select class="form-control" style="display: inline; width:13%;" id="subject">
						<option value="default" disabled selected>검색 항목</option>
						<option value="model">검증명</option>
						<option value="file">모델 파일</option>
						<option value="feature">검증 항목</option>
					</select>
					<input type="text" class="form-control w-25 d-inline" id="searchText" name="searchText">
					
					<a href="#" onclick="searchModel();" class="d-inline">
							<span class="fa fa-search"></span>
					</a>
					<br><br>
					<table class="table table-sm table-hover centerTable" id="modelTable">	
						<thead>
							<tr>
								<th style="width:4%;">번호</th>
								<th style="width:13%">등록 시간</th>
								<th style="width:13%">검증명</th>
								<th style="width:26%">모델 파일</th>
								<th style="width:40%">검증 항목</th>
								<th style="width:4%">설명</th>
								<th style="width:5%;">수정</th>
							</tr>
						</thead>
						<tbody style="text-align: center;">
							<c:forEach var="mlModel" items="${MLModelList}" varStatus="status">
								<tr id=${mlModel.id }>
									<td><input type="text" value='${status.count}' readonly style="border:none; background-color:transparent"></td>
									<td><input type="text" value='${localDateTimeFormat.format(mlModel.register_Time)}' readonly style="border:none; background-color:transparent"></td>
									<td><input type="text" value='${mlModel.name}' readonly style="border:none; background-color:transparent;width:100%;"></td>
									<td><input type="text" value='${mlModel.file}' readonly style="border:none; background-color:transparent;width:100%;"></td>
									<td><input type="text" value='${mlModel.feature}' readonly style="border:none; background-color:transparent;width:100%;"></td>
									<%-- <td><input type="text" value='${mlModel.description }' readonly style="border:none; background-color:transparent;width:100%;"></td> --%>
									<td> <a href="#" data-toggle="popover" title="설명" data-content="${mlModel.description }">
											<span class="fa fa-search"></span>
										</a>
									<td><a href="#" onclick="editRow(this);">
											<span class="fa fa-pencil"></span>
										</a>
										<a href="#" onclick="delete_row(this);">
											<span class="fa fa-times"></span>
										</a>
									</td>
								</tr>
							</c:forEach>
						</tbody>
					</table>
				</div>
			</div>
		</div>
	</div>
	<br>
	<div class="container border card">
		<div class="card-body">
			<h5 class="card-title">검증 구성 추가</h5>
			<div class="card">
				<div class="card-body">
					
					<form method="POST" enctype="multipart/form-data" class="filebox bs3-primary" id="fileForm">
						검증 명<input type="text" id="modelName" name="modelName" class="form-control" style="margin: 3px;width:100%;">
						학습 모델
						<div class="custom-file">
							
						  <input type="file" class="custom-file-input" id="fileName" lang="kr">
						  <label class="custom-file-label" for="uploadFile" id="fileNameText">파일 찾기</label>
						</div>
						<input type="file" name="file" id="uploadFile" class="upload-hidden" accept=".h5"/>
						<br>
						검증 항목
						<input type="text" id="feature" name="feature" style="margin: 3px;width:100%;" class="form-control" placeholder="A,B형식으로 작성하세요">
						<br><br>
						<div class="form-group">
							<label for="comment">설명</label>
							<textarea class="form-control" rows="5" id="description" name="description"></textarea>
						</div>
						<input type="button" onClick="addModel();" class="btn btn-primary float-right" value="업로드" />
					</form> 
<!-- 					<button onClick="javascript:addRow()">추가</button>
 -->				</div>
			</div>
		</div>
	</div>
	
</body>
</html>