<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<jsp:include page="/WEB-INF/include/sub/i_top.jsp" flush="false"></jsp:include>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>게시글 수정 페이지</title>
    <link href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css" rel="stylesheet">
</head>
<body>
<div class="container mt-5">
    <h1 class="mb-4">게시글 수정 페이지</h1>
    <div class="card p-4">
        <form action="b_updateOK" method="post" enctype="multipart/form-data">
            <div class="form-group">
                <label for="file">사진첨부</label>
                <input type="file" class="form-control-file" id="file" name="file">
            </div>
            <div class="form-group">
                <label for="category">카테고리</label>
                <div class="form-check">
                    <input class="form-check-input" type="radio" name="category" id="category" value="free" checked>
                    <label class="form-check-label" for="category">
                        자유
                    </label>
                </div>
                <div class="form-check">
                    <input class="form-check-input" type="radio" name="category" id="qna" value="qna">
                    <label class="form-check-label" for="qna">
                        질문
                    </label>
                </div>
                <div class="form-group">
                    <label for="num">글번호</label>
                    <p>${param.num}</p>
                    <input type="hidden" id="num" name="num" value="${param.num}">
                </div>
                <div class="form-group">
                    <label for="title">제목</label>
                    <input type="text" class="form-control" id="title" name="title" value="${vo2.title}"
                           placeholder="제목을 입력하세요">
                </div>
                <div class="form-group">
                    <label for="content">내용</label>
                    <textarea class="form-control" name="content" id="content" cols="30"
                              rows="10">${vo2.content}</textarea>
                </div>
                <div class="form-group">
                    <label for="user_id">작성자</label>
                    <p>admin1</p>
                    <input type="hidden" id="user_id" name="user_id" value="${vo2.user_id}">
                </div>
                <button type="submit" class="btn btn-primary">수정완료</button>
        </form>
    </div>
</div>
<script src="https://code.jquery.com/jquery-3.5.1.slim.min.js"></script>
<script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.5.4/dist/umd/popper.min.js"></script>
<script src="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>
</body>

<jsp:include page="/WEB-INF/include/sub/i_footer.jsp" flush="false"></jsp:include>