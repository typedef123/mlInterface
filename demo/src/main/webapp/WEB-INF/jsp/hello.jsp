<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!DOCTYPE html>
<html lang="ko">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>검증</title>
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
);
</style>
</head>
	<script src="/webjars/jquery/3.3.1-1/jquery.min.js"></script>
	<script src="/webjars/bootstrap/4.1.3/dist/js/bootstrap.min.js"></script>
	<script src="/webjars/bootstrap/4.1.3/dist/js/bootstrap.bundle.min.js"></script>
<script>
$(document).ready(function(){
	var fileTarget = $('.upload-hidden');
	var filenames="";
	
	fileTarget.on('change', function(){
		$("#fileTable > tbody").empty();
		console.log();
		var files = $(this)[0].files;
		for(var i = 0; i < files.length; i++){
			if(window.FileReader){
			    var filename = $(this)[0].files[i].name;
			} else {
			    var filename = $(this).val().split('/').pop().split('\\').pop();
			}
			document.getElementById('fileNameText').innerHTML = filenames;
			$(this).siblings('.custom-file-label').val(filename);
			var fileTable = document.getElementById('fileTable').getElementsByTagName('tbody')[0];
		    // var row = my_tbody.insertRow(0); // 상단에 추가
		    var row = fileTable.insertRow(fileTable.rows.length); // 하단에 추가
		    var cell1 = row.insertCell(0);
		    cell1.innerHTML = $(this)[0].files[i].name;
		}

	});
	return;
});
function addMapping() {
	var testDataFeatureSelect = document.getElementById("testDataFeature");
	var testDataFeature = testDataFeatureSelect.options[testDataFeatureSelect.selectedIndex].value;
	console.log(testDataFeature);
	
	$("select[id='testDataFeature'] option:selected").remove();

	var modelFeatureSelect = document.getElementById("modelFeature");
	var modelFeature = modelFeatureSelect.options[modelFeatureSelect.selectedIndex].value;
	console.log(modelFeature);
	$("select[id='modelFeature'] option:selected").remove();

	var mappingTable = document.getElementById('mappingTable').getElementsByTagName('tbody')[0];
    // var row = my_tbody.insertRow(0); // 상단에 추가
    var row = mappingTable.insertRow(mappingTable.rows.length); // 하단에 추가
    var cell0 = row.insertCell(0);
    var cell1 = row.insertCell(1);
    var cell2 = row.insertCell(2);
    var cell3 = row.insertCell(3);
    cell0.innerHTML = mappingTable.rows.length;
    cell1.innerHTML = testDataFeature;
    cell2.innerHTML = modelFeature;
    cell3.innerHTML = '<a href="#" onclick="delete_row(this)"><span class="fa fa-times"></span></a>';
	
}
function delete_row(obj) {
	console.log(obj);
    var tr = $(obj).parent().parent();
    
    //라인 삭제
    tr.remove();
}
function fileSubmit() {
	var formData = new FormData($("#fileForm")[0]);
	if($("#fileNameText").text() == "파일 찾기") {
		alert("파일을 선택해주세요")
		return;
	}
	$.ajax({
		type : 'post',
		url : '/readMDF',
		data : formData,
		processData : false,
		contentType : false,
		async: false,
		success : function(featureArray) {
			var testDataFeature = document.getElementById("testDataFeature");
			$("#testDataFeature").empty();
			$("#testFeatureCount").val("(총 " + featureArray.length + "개)");

			for(var i = 0; i < featureArray.length; i++) {
				var op = new Option();
				op.value = featureArray[i];
				op.text = featureArray[i];
				testDataFeature.options.add(op);
			}
			
		},
		error : function(error) {
			alert("파일 업로드 실패");
			console.log(error);
			console.log(error.status);
		}
	});
}
function selectModelListener(id) {
/* 	var formData = new FormData();
	formData.append("id", id);
	$.ajax({
		type : 'post',
		url : '/getModel',
		data : formData,
		processData : false,
		contentType : false,
		async: false,
		success : function(mlModel) {
			console.log(mlModel);
			var modelFeature = document.getElementById("modelFeature");
			var featureArray = mlModel.feature.split(',');
			$("#modelFeature").empty();
			$("#modelFeatureCount").val("(총 " + featureArray.length + "개)");
			
			for(var i in featureArray) {
				var op = new Option();
				op.value = featureArray[i];
				op.text = featureArray[i];
				modelFeature.options.add(op);
			}
		},
		error : function(error) {
			alert("파일 업로드 실패");
			console.log(error);
			console.log(error.status);
		}
	}); */
	
	
	//"featureTable"
	
	var formData = new FormData();
	formData.append("id", id);
	$.ajax({
		type : 'post',
		url : '/getModel',
		data : formData,
		processData : false,
		contentType : false,
		async: false,
		success : function(mlModel) {
			console.log(mlModel);
			var featureTable = document.getElementById("featureTable");
			var featureArray = mlModel.feature.split(',');
			
			$("#modelFeature > tbody").empty();
			
			for(var i in featureArray) {
				var row = featureTable.getElementsByTagName('tbody')[0].insertRow(featureTable.rows.length-1); // 하단에 추가
				var cell0 = row.insertCell(0);
				var cell1 = row.insertCell(1);
				cell0.innerHTML = featureTable.rows.length - 1;
				cell1.innerHTML = featureArray[i];
			}
		},
		error : function(error) {
			alert("파일 업로드 실패");
			console.log(error);
			console.log(error.status);
		}
	});
}
function execTest() {
	if(nullCheck()==true) {
		return;
	}

	var mlList = new Array();
	
	$("input[name=mlList]:checked").each(function() {
		mlList.push($(this).val());
	});
	
	var formData = new FormData();
	formData.append('mlList', mlList);
	console.log(formData.get('mlList'));
	$.ajax({
		type : 'post',
		url : '/execTest',
		data : formData,
		processData : false,
		contentType : false,
		async: false,
		success : function() {
			alert('실행 완료되었습니다.');
		},
		error : function(error) {
			alert("파일 업로드 실패");
			console.log(error);
			console.log(error.status);
		}
	})
}

function nullCheck() {

}
function makeNull() {
	$("#fileNameText").text("파일 찾기");
	$("#modelSelectBox").val("default").prop("selected", true);
	$("#testDataFeature option").remove();
	$("#modelFeature option").remove();
	var mappingTable = document.getElementById('mappingTable');
	$("#mappingTable > tbody").empty();
	$("#fileTable > tbody").empty();
}
</script>
<body>
	<%@include  file="header.jsp" %>
	
	<div class="container border card">
		<div class="card-body">
			<h5 class="card-title">검증</h5>
			
			<!-- <div class="card">
				<div class="card-body">
					<h5 class="card-title">데이터 입력</h5>
					<form method="POST" enctype="multipart/form-data" class="filebox bs3-primary"  id="fileForm">
					    <div class="custom-file">
						  <input type="file" class="custom-file-input" id="fileName" lang="kr">
						  <label class="custom-file-label" for="uploadFile" id="fileNameText">파일 찾기</label>
						</div>
						<input multiple="multiple" type="file" name="file" id="uploadFile" class="upload-hidden" accept=".dat"/>
					
					<br>
					<table class="table table-sm table-hover" id="fileTable">
						<tbody>
						</tbody>
					</table>
					<input type="button" onClick="fileSubmit();" class="btn btn-primary float-right" value="업로드" />
					
					</form>
				</div>
			</div>
			<br> -->
			
			<div class="card">
				<div class="card-body">
					<h5 class="card-title">검증 대상 파일</h5>
					<table class="table table-sm table-hover centerTable">
						<thead>
							<tr>
								<th width="5%">번호</th>
								<th>파일명</th>	
							</tr>
						</thead>
						<tbody>
							<c:forEach var="file" items="${fileList }" varStatus="status">
								<tr>
									<td>${status.count}</td>
									<td>${file }</td>
								</tr>
							</c:forEach>
						</tbody>
					</table>
				</div>
			</div>
			<div class="card">
				<div class="card-body">
					<h5 class="card-title">검증 모델 선택</h5>
					<%-- <select class="custom-select" onchange="selectModelListener(this.value);" id="modelSelectBox">
						<option value="default">학습 모델 선택</option>
						<c:forEach var="mlModel" items="${mlModelList }">
							<option value='${mlModel.id }'>${mlModel.name }</option>
						</c:forEach>
					</select> --%>
					
					<table class="table table-sm table-hover centerTable">
						<thead>
							<tr>
								<th width="20%">모델명</th>
								<th>설명</th>
							</tr>
						</thead>
						<tbody>
						<c:forEach var="mlModel" items="${mlModelList }">
							<tr>
								<td align="left">
								<div class="form-check form-check-inline">
									<input class="form-check-input" name="mlList" type="checkbox" id="${mlModel.id }" value='${mlModel.id }'>
									<label class="form-check-label" for="${mlModel.id }">${mlModel.name }</label>
								</div>
								</td>
								<td>${mlModel.description }</td>
									
							</tr>
						</c:forEach>
						</tbody>
					</table>
					
					
					<br><br>
					<!-- <table class="table table-sm table-hover" id="featureTable">
						<thead>
							<tr>
								<th width="5%">번호</th>
								<th>검증 항목</th>
							</tr>
						</thead>
						<tbody></tbody>
					</table> -->
				</div>
			</div>
			<br>
			
			<!--
			<div class="card">
				<div class="card-body">
					<h5 class="card-title">검증 항목</h5>
					<table width="100%">
						<tr>
							<td width="45%">테스트 데이터 항목 <input type="text" value="" id="testFeatureCount" readonly style="border:none; background-color:transparent">
								<select class="custom-select" size="5" id="testDataFeature">
								</select>
							</td>
							<td width="5%"></td>
							<td width="45%">모델 항목  <input type="text" value="" id="modelFeatureCount" readonly style="border:none; background-color:transparent">
								<select class="custom-select" size="5" id="modelFeature">
									
								</select> 
							</td>
							<td width="5%" align=center>
								<a href="#" onclick="addMapping()">
									<span class="fa fa-chevron-right" font-size="20px"></span>
								</a>
							</td>
						</tr>
					</table>
					<br>
					매핑 항목
					<table class="table table-sm table-hover centerTable" id="mappingTable">
						<thead>
							<tr>
								<th>번호</th>
					    		<th scope="col">테스트 데이터</th>
					    		<th scope="col">모델</th>
					 			<th></th>
					  		</tr>
						</thead>
						<tbody>
						</tbody>
					</table>
							
				</div>
			</div>
			
		</div>	--%>
		
		
		<p>
			<!-- <button type="button" class="btn btn-lg btn-default" onclick="location.href='/execCommand'">모델 테스트</button>
			<button type="button" class="btn btn-lg btn-primary" onclick="location.href='/modelLearning'">모델 학습</button> -->
			<button type="button" class="btn btn-primary float-right" onclick="execTest();">실행</button>
			
		</p>
	</div>
	</div>
</body>
</html>