<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<jsp:include page="/WEB-INF/include/sub/i_top.jsp" flush="false"></jsp:include>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<div class="position-relative padding-medium">
    <div class="container">
        <div class="section-title d-md-flex justify-content-between align-items-center mb-4">
            <h3 class="d-flex align-items-center">토론 상세보기</h3>
        </div>
        <div class="d-flex flex-wrap justify-content-md-between justify-content-center border-bottom pb-3">
            <div class="col-md-3 col-4 mb-2">
                <img class="img-fluid shadow-sm" src="/img/${vo2.img_name}" alt="${vo2.work_title} 작품 이미지">
            </div>
            <div class="col-md-8 col-12">
                <div class="d-flex justify-content-between mb-2 text-black-50">
                    <small>조회수 ${vo2.hitcount}</small>
                    <div>
                        <a href="f_reportOK.do" class="text-danger">신고<i class="svg-icon position-relative d-inline-block"><svg xmlns="http://www.w3.org/2000/svg" viewBox="0 0 512 512"><!--!Font Awesome Free 6.6.0 by @fontawesome - https://fontawesome.com License - https://fontawesome.com/license/free Copyright 2024 Fonticons, Inc.--><path d="M256 32c14.2 0 27.3 7.5 34.5 19.8l216 368c7.3 12.4 7.3 27.7 .2 40.1S486.3 480 472 480L40 480c-14.3 0-27.6-7.7-34.7-20.1s-7-27.8 .2-40.1l216-368C228.7 39.5 241.8 32 256 32zm0 128c-13.3 0-24 10.7-24 24l0 112c0 13.3 10.7 24 24 24s24-10.7 24-24l0-112c0-13.3-10.7-24-24-24zm32 224a32 32 0 1 0 -64 0 32 32 0 1 0 64 0z"/></svg></i></a>
<%--                        <c:if test="${vo2.user_id == user_id}">--%>
                            |&nbsp;<a href="f_updateOK.do?num=${vo2.num}">수정</a>
                            <a href="f_deleteOK.do?num=${vo2.num}">삭제</a>
<%--                        </c:if>--%>
                    </div>
                </div>
                <p class="mb-0">${vo2.wdate} ${vo2.user_id}님이 게시한 <span class="text-primary">${vo2.work_title}</span>에 대한 토론</p>
                <h6 class="mt-2 mb-3 py-2 fw-bold fs-3">Q. ${vo2.title}</h6>
                <p>${vo2.content}</p>
            </div>
        </div>
        <section class="forum__comments">
            <form action="c_insertOK.do" class="my-4">
                <input type="hidden" id="forum_num" name="forum_num" value="${vo2.num}">
                <input type="hidden" id="user_id" name="user_id" value="${user_id}">
                <div class="form-group d-flex flex-wrap flex-sm-nowrap justify-content-between align-items-sm-center">
                    <label for="content" class="py-2 pe-2 text-nowrap">의견 남기기</label>
                    <div class="d-flex w-100 gap-2">
                        <input type="text" name="content" id="content" class="form-control rounded-3 p-3" minlength="4" required>
                        <input type="submit" value="입력" class="btn btn-dark">
                    </div>
                    ${user_id}
                </div>
            </form>
            <p>💬 댓글(${vo2.comment_count})</p>
            <ul class="list-unstyled py-2 mb-5">
<%--                <c:forEach var="cvo" items="${clist}">--%>
                    <li class="p-3 border rounded mb-3">
                        <div class="d-flex justify-content-between pb-1">
                            <div>
                                <strong class="">
                                    ${cvo.writer}게시자님
                                </strong>
                                <c:if test="${cvo.writer == user_id}">
                                    <a href="c_deleteOK.do?num=${cvo.num}&forum_num=${vo2.num}"><i class="svg-icon d-inline-block"><svg xmlns="http://www.w3.org/2000/svg" viewBox="0 0 448 512"><!--!Font Awesome Free 6.6.0 by @fontawesome - https://fontawesome.com License - https://fontawesome.com/license/free Copyright 2024 Fonticons, Inc.--><path d="M135.2 17.7L128 32 32 32C14.3 32 0 46.3 0 64S14.3 96 32 96l384 0c17.7 0 32-14.3 32-32s-14.3-32-32-32l-96 0-7.2-14.3C307.4 6.8 296.3 0 284.2 0L163.8 0c-12.1 0-23.2 6.8-28.6 17.7zM416 128L32 128 53.2 467c1.6 25.3 22.6 45 47.9 45l245.8 0c25.3 0 46.3-19.7 47.9-45L416 128z"/></svg></i></a>
                                </c:if>
                                <a href=""><i class="svg-icon d-inline-block"><svg xmlns="http://www.w3.org/2000/svg" viewBox="0 0 512 512"><!--!Font Awesome Free 6.6.0 by @fontawesome - https://fontawesome.com License - https://fontawesome.com/license/free Copyright 2024 Fonticons, Inc.--><path d="M205 34.8c11.5 5.1 19 16.6 19 29.2l0 64 112 0c97.2 0 176 78.8 176 176c0 113.3-81.5 163.9-100.2 174.1c-2.5 1.4-5.3 1.9-8.1 1.9c-10.9 0-19.7-8.9-19.7-19.7c0-7.5 4.3-14.4 9.8-19.5c9.4-8.8 22.2-26.4 22.2-56.7c0-53-43-96-96-96l-96 0 0 64c0 12.6-7.4 24.1-19 29.2s-25 3-34.4-5.4l-160-144C3.9 225.7 0 217.1 0 208s3.9-17.7 10.6-23.8l160-144c9.4-8.5 22.9-10.6 34.4-5.4z"/></svg></i></a>
                            </div>
                            <span class="text-black-50">
                                ${cvo.wdate}2024-10-17
                            </span>
                        </div>
                        <p class="mt-2 mb-0">${cvo.content}내용ㅁㄴㅇㄹㅎㄴㅇ</p>
                    </li>
<%--                </c:forEach>--%>
            </ul>
        </section>
    </div>
<jsp:include page="/WEB-INF/include/sub/i_footer.jsp" flush="false" ></jsp:include>