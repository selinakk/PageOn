<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<jsp:include page="/WEB-INF/include/sub/i_top.jsp" flush="false"></jsp:include>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<div class="position-relative padding-medium">
    <div class="container">
        <h3 class="d-flex align-items-center">토론 게시하기</h3>
        <form action="/forum/updateOK.do" method="POST">
            <input type="hidden" name="num" id="num" value="${vo2.num}">
            <input type="hidden" name="work_num" id="work_num" value="123">
            <input type="hidden" name="user_id" id="user_id" value="${user_id}">
            <div class="form-group py-3">
                작품명
            </div>
            <div class="form-group py-3">
                <label for="title" class="mb-2">제목</label>
                <input type="text" name="title" id="title" class="form-control w-100 rounded-3 p-3" placeholder="제목은 최대 100바이트까지 작성 가능합니다">
            </div>
            <div class="form-group py-3">
                <label for="content" class="mb-2">내용</label>
                <textarea name="content" id="content" rows="10" class="form-control">내용을 입력하세요</textarea>
            </div>
            <div class="d-flex justify-content-center py-3">
                <input type="submit" value="게시하기" class="btn btn-dark">
            </div>
        </form>
    </div>
</div>
<jsp:include page="/WEB-INF/include/sub/i_footer.jsp" flush="false" ></jsp:include>