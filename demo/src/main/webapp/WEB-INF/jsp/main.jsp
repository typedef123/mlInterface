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
<link rel="stylesheet" href="/webjars/bootstrap/4.1.3/dist/css/bootstrap.min.css">
<link rel="stylesheet" href="<c:url value='/resources/css/NewFile.css'/>">
<link rel="stylesheet" href="//maxcdn.bootstrapcdn.com/font-awesome/4.3.0/css/font-awesome.min.css">
<script src="/webjars/jquery/3.3.1-1/jquery.min.js"></script>
<script src="/webjars/bootstrap/4.1.3/dist/js/bootstrap.min.js"></script>
<script src="/webjars/bootstrap/4.1.3/dist/js/bootstrap.bundle.min.js"></script>
<link href="/resources/css/logo-nav.css" rel="stylesheet">
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
			<table class="table table-sm table-hover centerTable" id="modelTable">
						<thead>
							<tr>
								<th style="width:4%;">번호</th>
								<th style="width:15%">수행 종료 시간</th>
								<th style="width:10%">총 소요 시간</th>
								<th style="width:10%">IP</th>
								<th style="width:8%">모델 명</th>
								<th>테스트 데이터</th>
								<th>결과 파일</th>
								<th style="width:4%;">보기</th>
							</tr>
						</thead>
						<tbody style="text-align: center;">
							 <c:forEach var="resultDetail" items="${resultDetailList}" varStatus="status">
								<tr id=${resultDetail.id }>
									<td><input type="text" value='${status.count}' readonly style="border:none; background-color:transparent"></td>
									<td>${localDateTimeFormat.format(resultDetail.endtime)}</td>
									<td>${resultDetail.spenttime}</td>
									<td>${resultDetail.ip}</td>
									<td>${resultDetail.modelName }</td>
									<td>${resultDetail.testfile}</td>
									<td>${resultDetail.resultfile}</td>
									<td><a href="#" title="설명" onClick="viewDetail('${resultDetail.id}')">
											<span class="fa fa-search"></span>
										</a>
									</td>
								</tr>
							</c:forEach>
						</tbody>
					</table>
		</div>
	</div>
</body>
</html>