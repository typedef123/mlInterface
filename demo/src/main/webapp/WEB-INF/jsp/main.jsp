<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%> 
<%@ taglib prefix="tf" tagdir="/WEB-INF/tags" %>

<!DOCTYPE html>
<html>
<head>
<meta charset="EUC-KR">
<title>홈</title>

</head>
<script>

</script>
<script src="http://code.jquery.com/jquery-latest.min.js"></script>
<body>
	<%@include  file="header.jsp" %>
	<c:if test="${executing eq 'true'}">
    	<div class="container border card">
		<div class="card-body">
			<h5 class="card-title">테스트 실행 중</h5>
			<div class="progress">
				<div class="progress-bar progress-bar-striped progress-bar-animated" role="progressbar" aria-valuenow="100" aria-valuemin="0" aria-valuemax="100" style="width: 100%">
					<span class="sr-only">45% Complete</span>
				</div>
			</div>
			
			<table class="table table-sm table-hover centerTable">
				<tr>
					<td>사용자 IP</td>
					<td>${nowIp }</td>
				</tr>
				<tr>
					<td>파일 명</td>
					<td>${nowFile }</td>
				</tr>
				<tr>
					<td>모델 명</td>
					<td>${nowModel }</td>
				</tr>
				<tr>
					<td>수행 시작 시간</td>
					<td>${startTest}</td>
				</tr>
			</table>
			
		</div>
	</div>
	</c:if>

	
	
	<div class="container border card">
		<div class="card-body">
			<h5 class="card-title">최근 수행된 파일</h5>
			<table class="table table-sm table-hover centerTable">
				<thead>
					<tr>
						<th>수행 시작 시간</th>
						<th>수행 종료 시간</th>
						<th>총 소요 시간</th>
						<th>IP</th>
						<th>모델 명</th>
						<th>테스트 데이터</th>
						<th>결과 파일</th>
					</tr>
				</thead>
				<tbody>
					
				</tbody>
			</table>
		</div>
	</div>
</body>
</html>