<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>포럼글 댓글 테스트 페이지</title>
    <script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
    <style>
        body {
            font-family: Arial, sans-serif;
            margin: 20px;
        }
        .comment, .childComment {
            border: 1px solid #ccc;
            padding: 10px;
            margin-bottom: 10px;
            border-radius: 5px;
        }
        .replyForm {
            margin-top: 10px;
        }
        .editForm {
            margin-top: 10px;
            display: none;
        }
    </style>
</head>
<body>
<h1>포럼글 제목</h1>
<p>포럼글 내용입니다. 이곳에 포럼글의 본문 내용이 들어갑니다.</p>
<p>작성자: user123 | 작성일: 2024-10-16</p>

<h2>댓글 목록</h2>
<div id="commentList">
    <c:forEach items="${comments}" var="comment">
        <div class="comment" id="comment-${comment.num}">
            <p><strong>${comment.user_id}:</strong> <span class="comment-content">${comment.content}</span></p>
            <p>작성일: ${comment.wdate}</p>
            <p>
                <button onclick="showReplyForm(${comment.num})">대댓글 작성</button>
                <button onclick="fetchChildComments(${comment.num})">대댓글 보기</button>
                <button onclick="editComment(${comment.num})">수정</button>
                <button onclick="deleteComment(${comment.num})">삭제</button>
            </p>

            <!-- 대댓글 입력 폼 -->
            <div class="replyForm" id="replyForm-${comment.num}" style="display:none;">
                <textarea id="replyText-${comment.num}" rows="2" placeholder="대댓글을 입력하세요..."></textarea>
                <button onclick="submitReply(${comment.num})">작성</button>
                <button onclick="cancelReply(${comment.num})">취소</button>
            </div>

            <div class="editForm" id="editForm-${comment.num}">
                <textarea id="editText-${comment.num}" rows="2">${comment.content}</textarea>
                <button onclick="submitEdit(${comment.num})">수정 완료</button>
                <button onclick="cancelEdit(${comment.num})">취소</button>
            </div>

            <div class="childComments" id="childComments-${comment.num}">
                <!-- 대댓글을 가져올 공간 -->
            </div>
        </div>
    </c:forEach>
</div>

<h2>댓글 작성</h2>
<textarea id="commentText" rows="4" placeholder="댓글을 입력하세요..."></textarea>
<button onclick="submitComment()">작성</button>

<script>
    function fetchChildComments(commentId) {
        const childCommentsDiv = $('#childComments-' + commentId);

        if (childCommentsDiv.is(':visible')) {
            childCommentsDiv.hide();
            return;
        }

        $.ajax({
            type: "GET",
            url: "/comments/child/" + commentId,
            success: function(childComments) {
                if (childCommentsDiv.children().length === 0) {
                    childComments.forEach(function(childComment) {
                        const childCommentElem = `
                        <div class="childComment" id="childComment-\${childComment.num}">
                            <p><strong>\${childComment.user_id}:</strong> \${childComment.content}</p>
                            <p>작성일: \${childComment.wdate}</p>
                            <p>
                                <button onclick="showReplyForm(\${childComment.num}, true)">대댓글 작성</button>
                                <button onclick="editComment(\${childComment.num}, true)">수정</button>
                                <button onclick="deleteComment(\${childComment.num}, true)">삭제</button>
                            </p>
                            <div class="replyForm" id="replyForm-\${childComment.num}" style="display:none;">
                                <textarea id="replyText-\${childComment.num}" rows="2" placeholder="대댓글을 입력하세요..."></textarea>
                                <button onclick="submitReply\${childComment.num})">작성</button>
                                <button onclick="cancelReply(\${childComment.num})">취소</button>
                            </div>
                        </div>`;
                        childCommentsDiv.append(childCommentElem);
                    });
                }
                childCommentsDiv.show();
            },
            error: function() {
                alert("대댓글을 가져오는 데 오류가 발생했습니다.");
            }
        });
    }

    function submitComment() {
        const commentText = $('#commentText').val().trim();
        if (!commentText) {
            alert("댓글 내용을 입력하세요.");
            return;
        }

        $.ajax({
            type: "POST",
            url: "/comments",
            contentType: "application/json",
            data: JSON.stringify({
                content: commentText,
                type: "forum",
                fnum: 1, // 포럼글 ID
                user_id: "user123" // 작성자 ID
            }),
            success: function() {
                location.reload();
            },
            error: function() {
                alert("댓글 작성에 실패했습니다.");
            }
        });
    }

    function submitReply(parentId) {
        const replyText = $(`#replyText-\${parentId}`).val().trim();
        if (!replyText) {
            alert("대댓글 내용을 입력하세요.");
            return;
        }

        $.ajax({
            type: "POST",
            url: "/comments",
            contentType: "application/json",
            data: JSON.stringify({
                content: replyText,
                type: "forum",
                fnum: 1, // 포럼글 ID
                cnum: parentId, // 부모 댓글 ID
                user_id: "user123" // 작성자 ID
            }),
            success: function() {
                location.reload();
            },
            error: function() {
                alert("대댓글 작성에 실패했습니다.");
            }
        });
    }

    function showReplyForm(commentId, isChild = false) {
        const formId = isChild ? 'replyForm-' + commentId : 'replyForm-' + commentId;
        document.getElementById(formId).style.display = 'block';
    }

    function cancelReply(commentId) {
        document.getElementById('replyForm-' + commentId).style.display = 'none';
    }

    function editComment(commentId, isChild = false) {
        const editForm = $('#editForm-' + commentId);
        const commentElem = isChild ? $(`#childComment-\${commentId} p:first`) : $(`#comment-\${commentId} p:first`);
        const commentText = commentElem.find('.comment-content').text().trim();

        // 기존 내용으로 텍스트 에어리어를 업데이트
        $(`#editText-${commentId}`).val(commentText);
        editForm.show();
    }

    function submitEdit(commentId) {
        const newCommentText = $(`#editText-\${commentId}`).val().trim();

        $.ajax({
            type: "PUT",
            url: "/comments/" + commentId,
            contentType: "application/json",
            data: JSON.stringify({
                content: newCommentText,
                user_id: "user123" // 작성자 ID
            }),
            success: function() {
                location.reload();
            },
            error: function() {
                alert("댓글 수정에 실패했습니다.");
            }
        });
    }

    function cancelEdit(commentId) {
        $('#editForm-' + commentId).hide();
    }

    function deleteComment(commentId, isChild = false) {
        if (confirm("정말로 이 댓글을 삭제하시겠습니까?")) {
            $.ajax({
                type: "DELETE",
                url: "/comments/" + commentId,
                success: function() {
                    const commentSelector = isChild ? `#childComment-\${commentId}` : `#comment-\${commentId}`;
                    $(commentSelector).remove();
                },
                error: function() {
                    alert("댓글 삭제에 실패했습니다.");
                }
            });
        }
    }
</script>
</body>
</html>
