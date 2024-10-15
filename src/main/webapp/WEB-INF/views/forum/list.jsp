<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<jsp:include page="/WEB-INF/include/sub/i_top.jsp" flush="false"></jsp:include>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<div class="position-relative padding-medium">
    <div class="container">
        <div class="section-title d-md-flex justify-content-between align-items-center mb-4">
            <h3 class="d-flex align-items-center">토론 게시판</h3>
            <form action="" method="GET" class="search-list--custom d-flex w-100">
                <select name="searchKey" id="searchKey" class="form-select p-2 w-25 rounded-0">
                    <option value="title">토론 제목</option>
                    <option value="work_title">작품 제목</option>
                    <option value="content">내용</option>
                    <option value="writer">게시자명</option>
                </select>
                <input type="text" minlength="2" name="searchWord" placeholder="검색어를 입력해 주세요"
                       class="form-control p-2 w-75 rounded-0">
                <input type="submit" class="btn btn-dark rounded-0" value="검색">
            </form>
        </div>
        <div class="d-flex justify-content-between align-items-center w-100">
            <p class="f-3">총 ${totalList}건</p>
            <div class="sort-wrapper position-relative">
                <button class="nav-link dropdown-toggle">정렬</button>
                <div class="dropdown-menu animate slide border vstack text-center">
                    <a class="p-1 text-black" href="/forum/list?page=${currentPage}&size=${pageSize}&sortField=wdate&sortDir=desc"><small>최신순</small></a>
                    <a class="p-1 text-black" href="/forum/list?page=${currentPage}&size=${pageSize}&sortField=hitcount&sortDir=desc"><small>조회수순</small></a>
                    <a class="p-1 text-black" href="/forum/list?page=${currentPage}&size=${pageSize}&sortField=comment_count&sortDir=desc"><small>댓글이 많은 순</small></a>
                </div>
            </div>
        </div>
        <ul class="list-unstyled row-cols-4 padding-small d-flex flex-wrap" id="forumList">
            <c:forEach var="forum" items="${list}">
                <li class="p-3">
                    <a href="#" class="card position-relative p-4 border rounded-3 fs-6">
                        <img class="img-fluid shadow-sm" src="/img/${forum.img_name}" alt="${forum.work_title} 작품 이미지">
                        <p class="mt-4 mb-0">${forum.work_title}</p>
                        <h6 class="my-3 fw-bold">${forum.title}</h6>
                        <div class="d-flex flex-wrap gap-3">
                            <p class="mb-0">댓글수 ${forum.comment_count}</p>
                            <p class="mb-0">조회수 ${forum.hitcount}</p>
                            <p class="mb-0">게시자명 ${forum.user_id}</p>
                            <p class="mb-0">게시일 ${forum.wdate}</p>
                        </div>
                        <div class="card-concern position-absolute start-0 end-0 vstack gap-2 p-2">
                            <button type="button" href="#" class="btn btn-dark">읽고 싶은 작품에 추가</button>
                            <button type="button" href="#" class="btn btn-dark">읽고 있는 작품에 추가</button>
                            <button type="button" href="#" class="btn btn-dark">읽은 작품에 추가</button>
                        </div>
                    </a>
                </li>
            </c:forEach>
        </ul>
        <div>
<%--            <c:if test="${currentPage > 1}">--%>
<%--                <a href="?page=${currentPage - 1}&sortField=${sortField}&sortDir=${sortDir}">Previous</a>--%>
<%--            </c:if>--%>

            <c:forEach var="i" begin="1" end="${totalPages}">
                <a href="?page=${i}&sortField=${sortField}&sortDir=${sortDir}">${i}</a>
            </c:forEach>

<%--            <c:if test="${currentPage < totalPages}">--%>
<%--                <a href="?page=${currentPage + 1}&sortField=${sortField}&sortDir=${sortDir}">Next</a>--%>
<%--            </c:if>--%>
        </div>
    </div>
</div>
<jsp:include page="/WEB-INF/include/sub/i_footer.jsp" flush="false" ></jsp:include>