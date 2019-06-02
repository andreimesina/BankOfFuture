<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>${pageTitle}</title>
    <style>
        input {
            margin-top: 2px;
            margin-bottom: 2px;
        }
    </style>
</head>
<body>
    <form action="/signIn/verify" method="post">
        <input name="username" type="text" placeholder="Username" autocomplete="true" required><br>
        <input name="password" type="password" placeholder="Password" autocomplete="false" required><br><br>
        <input name="submit" type="submit" value="Sign in"><br>
    </form>
    <c:choose>
        <c:when test = "${param.error == 'NA'}">
            <h3 style="color: red">You are not authenticated!</h3><br>
        </c:when>
        <c:when test = "${param.error == 'AF'}">
            <h3 style="color: red">Authentication failed!</h3><br>
        </c:when>
    </c:choose>
    You don't have an account?
    <a href="/register">Register here</a>
</body>
</html>
