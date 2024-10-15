<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!-- 회원가입 모달 -->
<div class="modal fade" id="registerModal" tabindex="-1" aria-labelledby="registerModalLabel" aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header border-bottom-0">
                <h5 class="modal-title" id="registerModalLabel">회원가입</h5>
                <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
            </div>
            <div class="modal-body">
                <form action="/insertMember" method="post" enctype="multipart/form-data">
                    <div class="form-group py-3">
                        <label class="mb-2" for="userId">아이디 *</label>
                        <input type="text" name="id" placeholder="아이디" class="form-control w-100 rounded-3 p-3" required>
                    </div>
                    <div class="form-group pb-3">
                        <label class="mb-2" for="password">비밀번호 *</label>
                        <input type="password" name="pw" placeholder="비밀번호" class="form-control w-100 rounded-3 p-3" required>
                    </div>
                    <div class="form-group pb-3">
                        <label class="mb-2" for="name">이름 *</label>
                        <input type="text" name="name" placeholder="이름" class="form-control w-100 rounded-3 p-3" required>
                    </div>
                    <div class="form-group pb-3">
                        <label class="mb-2" for="tel">전화번호 *</label>
                        <input type="tel" name="tel" placeholder="전화번호" class="form-control w-100 rounded-3 p-3" required>
                    </div>
                    <div class="form-group pb-3">
                        <label class="mb-2" for="likeCategories">관심 카테고리 *</label>
                        <input type="text" name="like_categories" placeholder="관심 카테고리" class="form-control w-100 rounded-3 p-3" required>
                    </div>
                    <div class="form-group pb-3">
                        <label class="mb-2" for="introduce">자기소개</label>
                        <textarea name="introduce" placeholder="자기소개" class="form-control w-100 rounded-3 p-3"></textarea>
                    </div>
                    <div class="form-group pb-3">
                        <label class="mb-2" for="imgFile">프로필 이미지 업로드</label>
                        <input type="file" name="img_name" class="form-control w-100 rounded-3 p-3">
                    </div>
                    <button type="submit" class="btn btn-dark w-100 my-3">회원가입</button>
                </form>
            </div>
        </div>
    </div>
</div>
