<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<jsp:include page="/WEB-INF/include/sub/i_top.jsp" flush="false"></jsp:include>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Freeboard</title>
    <link href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css" rel="stylesheet">
    <style>
        .board-box {
            padding: 20px;
            margin-bottom: 20px;
            border: 1px solid #ddd;
            border-radius: 5px;
            text-align: center;
            cursor: pointer;
            max-width: 150px;
        }

        .board-box.active {
            background-color: #007bff;
            color: white;
        }

        .board-container {
            .board-container {
                display: flex;
                flex-direction: column;
                align-items: center;
            }
    </style>
</head>

<body>
<div class="container mt-5">
    <div class="board-container">
        <div class="board-box ${pageContext.request.requestURI.contains('/freeboard') ? 'active' : ''}"
             onclick="location.href='/freeboard'">
            자유 게시판
        </div>
    </div>
    <div class="board-container">
        <div class="board-box ${pageContext.request.requestURI.contains('/qnaboard') ? 'active' : ''}"
             onclick="location.href='/qnaboard'">
            질문 게시판
        </div>
    </div>
    <table class="table table-bordered table-hover">
        <thead class="thead-dark">
        <tr>
            <th>번호</th>
            <th>제목</th>
            <th>작성자</th>
            <th>작성일</th>
            <th>이미지</th>
            <th>카테고리</th>
            <th>조회수</th>
        </tr>
        </thead>
        <tbody>
        <c:forEach var="board" items="${boardList}">
            <tr onclick="location.href='/b_selectOne?num=${board.num}'" style="cursor:pointer;">
                <td>${board.num}</td>
                <td>${board.title}</td>
                <td>${board.user_id}</td>
                <td>${board.wdate}</td>
                <td><img src="resources/upload_img/thumb_${board.img_name}" class="img-thumbnail"></td>
                <td>${board.category}</td>
                <td>${board.boardhitcount}</td>
            </tr>
        </c:forEach>
        </tbody>
    </table>

    <!-- 페이징 -->
    <nav aria-label="Page navigation">
        <ul class="pagination justify-content-center">
            <c:if test="${currentPage > 1}">
                <li class="page-item">
                    <a class="page-link" href="?page=${currentPage - 1}" aria-label="Previous">
                        <span aria-hidden="true">&laquo;</span>
                    </a>
                </li>
            </c:if>
            <c:forEach begin="1" end="${totalPages}" var="i">
                <li class="page-item ${i == currentPage ? 'active' : ''}">
                    <a class="page-link" href="?page=${i}">${i}</a>
                </li>
            </c:forEach>
            <c:if test="${currentPage < totalPages}">
                <li class="page-item">
                    <a class="page-link" href="?page=${currentPage + 1}" aria-label="Next">
                        <span aria-hidden="true">&raquo;</span>
                    </a>
                </li>
            </c:if>
        </ul>
    </nav>

    <div class="mt-3">
        <a href="/b_insert" class="btn btn-primary">글작성</a>
    </div>
</div>

<script src="https://code.jquery.com/jquery-3.5.1.slim.min.js"></script>
<script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.5.4/dist/umd/popper.min.js"></script>
<script src="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>
</body>

<jsp:include page="/WEB-INF/include/sub/i_footer.jsp" flush="false"></jsp:include>