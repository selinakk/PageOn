<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<jsp:include page="/WEB-INF/include/sub/i_top.jsp" flush="false"></jsp:include>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<div class="position-relative padding-medium">
    <div class="container">
        <div class="section-title d-md-flex justify-content-between align-items-center mb-4">
            <h3 class="d-flex align-items-center">토론 게시판</h3>
            <form action="" method="GET" class="search-list--custom d-flex w-100">
                    <select name="searchKey" id="searchKey" class="form-select p-2 w-25 rounded-0">
                        <option value="title">제목</option>
                        <option value="content">내용</option>
                        <option value="work_title">작품명</option>
                    </select>
                    <input type="text" minlength="2" name="searchWord" placeholder="검색어를 입력해 주세요"
                           class="form-control p-2 w-75 rounded-0">
                <input type="submit" class="btn btn-dark rounded-0" value="검색">
            </form>
        </div>
        <ul class="list-unstyled padding-small">
            <li>
                <a href="#">
                <c:forEach var="forum" items="${list}">
                    <div>
                        ${forum.title}
                        ${forum.like}
                    </div>
                </c:forEach>

                </a>
            </li>
        </ul>
    </div>
</div>

<jsp:include page="/WEB-INF/include/sub/i_footer.jsp" flush="false" ></jsp:include>