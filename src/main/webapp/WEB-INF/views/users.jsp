<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<jsp:include page="/WEB-INF/include/sub/i_top.jsp" flush="false"></jsp:include>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<table border="1">
    <tr>
        <th>ID</th>
        <th>Name</th>
    </tr>
    <c:forEach var="user" items="${users}">
        <tr>
            <td>${user.id}</td>
            <td>${user.name}</td>
        </tr>
    </c:forEach>
</table>
<jsp:include page="/WEB-INF/include/sub/i_footer.jsp" flush="false" ></jsp:include>