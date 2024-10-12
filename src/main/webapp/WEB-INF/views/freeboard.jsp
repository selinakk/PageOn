<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<jsp:include page="/WEB-INF/include/sub/i_top.jsp" flush="false"></jsp:include>

<table border="1">
    <tr>
        <th>title</th>
        <th>id</th>
    </tr>
    <c:forEach var="" items="${}">
        <tr>
            <td>${board.}</td>
            <td>${board.}</td>
        </tr>
    </c:forEach>
</table>



<jsp:include page="/WEB-INF/include/sub/i_footer.jsp" flush="false" ></jsp:include>