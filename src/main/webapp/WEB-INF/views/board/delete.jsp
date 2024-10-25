<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<jsp:include page="/WEB-INF/include/sub/i_top.jsp" flush="false"></jsp:include>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>게시글 삭제 페이지</title>
    <link href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css" rel="stylesheet">
</head>
<body>
<div class="container mt-5">
    <h1 class="mb-4">게시글 삭제 페이지</h1>
    <div class="card p-4">
        <form action="b_deleteOK" method="post">
            <input type="hidden" name="num" value="${param.num}">
            <input type="hidden" name="category" value="${param.category}">
            <div class="form-group">
                <label>${param.num}번 글을 정말 삭제하시겠습니까?</label>
            </div>
            <button type="submit" class="btn btn-danger">삭제완료</button>
        </form>
    </div>
</div>
<script src="https://code.jquery.com/jquery-3.5.1.slim.min.js"></script>
<script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.5.4/dist/umd/popper.min.js"></script>
<script src="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>
</body>

<jsp:include page="/WEB-INF/include/sub/i_footer.jsp" flush="false" ></jsp:include>