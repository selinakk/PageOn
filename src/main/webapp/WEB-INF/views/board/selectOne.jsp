<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<jsp:include page="/WEB-INF/include/sub/i_top.jsp" flush="false"></jsp:include>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>게시글 상세 페이지</title>
    <link href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css" rel="stylesheet">
</head>
<body>
<div class="container mt-5">
    <h1 class="mb-4">게시글 상세 페이지</h1>
    <div class="card p-4">
        <table class="table table-bordered">
            <thead class="thead-light">
            <tr>
                <th>글번호</th>
                <th>글제목</th>
                <th>글내용</th>
                <th>작성자</th>
                <th>작성일자</th>
                <th>사진</th>
            </tr>
            </thead>
            <tbody>
            <tr>
                <td>${vo2.num}</td>
                <td>${vo2.title}</td>
                <td>${vo2.content}</td>
                <td>${vo2.user_id}</td>
                <td>${vo2.wdate}</td>
                <td><img src="resources/upload_img/${vo2.img_name}" class="img-thumbnail"></td>
            </tr>
            </tbody>
        </table>
        <div class="d-flex justify-content-between">
            <a href="b_update?num=${vo2.num}" class="btn btn-primary">게시글수정</a>
            <a href="b_delete?num=${vo2.num}&category=${vo2.category}" class="btn btn-danger">게시글삭제</a>
        </div>
    </div>
</div>
<script src="https://code.jquery.com/jquery-3.5.1.slim.min.js"></script>
<script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.5.4/dist/umd/popper.min.js"></script>
<script src="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>
</body>

<jsp:include page="/WEB-INF/include/sub/i_footer.jsp" flush="false" ></jsp:include>