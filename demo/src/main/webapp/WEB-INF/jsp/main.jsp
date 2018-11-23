<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="EUC-KR">
<title>Insert title here</title>
</head>
<body>
	<%@include  file="header.jsp" %>
	
	<div class="container border card">
		<div class="card-body">
			<h5 class="card-title">테스트 실행 중</h5>
			
		</div>
	</div>
	
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
			</table>
		</div>
	</div>
</body>
</html>