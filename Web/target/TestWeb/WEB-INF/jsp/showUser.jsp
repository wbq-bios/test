<%--
  Created by IntelliJ IDEA.
  User: wbq
  Date: 2017/6/19
  Time: 20:06
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<html>
<head>
    <title>用户信息列表</title>
</head>
<body>
<c:if test="${!empty userList}">
    <c:forEach var="user" items="${userList}">
        姓名：${user.userName} &nbsp;&nbsp;id：${user.id} &nbsp;&nbsp;密码：${user.passWord} &nbsp;&nbsp;<br>
    </c:forEach>
</c:if>
</body>
</html>
