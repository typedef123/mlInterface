<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="ko">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>Insert title here</title>
<link rel="stylesheet" href="/webjars/bootstrap/4.1.3/dist/css/bootstrap.min.css">
<link rel="stylesheet" href="<c:url value='/resources/css/NewFile.css'/>">
<link rel="stylesheet" href="//maxcdn.bootstrapcdn.com/font-awesome/4.3.0/css/font-awesome.min.css">
<!-- Custom styles for this template -->
<link href="/resources/css/logo-nav.css" rel="stylesheet">
</head>
<style>
body {margin: 10px; background-color: #ebebeb;}
</style>
<body>
<!-- Navigation -->
    <nav class="navbar navbar-expand-lg navbar-dark fixed-top" style="background-color: #a5bab5;">
      <div class="container">
        <a class="navbar-brand" href="#">
			<img src="<c:url value='/resources/image/logo3.png'/>" width="120" height="35" alt="">
        </a>
        <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarResponsive" aria-controls="navbarResponsive" aria-expanded="false" aria-label="Toggle navigation">
          <span class="navbar-toggler-icon"></span>
        </button>
        <div class="collapse navbar-collapse" id="navbarResponsive">
          <ul class="navbar-nav ml-auto">
            <li class="nav-item active">
              <a class="nav-link" href="#">검증</a>
              <span class="sr-only">(current)</span>
            </li>
            <li class="nav-item">
              <a class="nav-link" href="#">수행이력</a>
            </li>
          </ul>
        </div>
      </div>
    </nav>
</body>
</html>