<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>
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
    <form action="/register/verify" method="post">
        <input name="username" type="text" placeholder="Username" autofocus autocomplete="true">&nbsp;
        <input name="password" type="password" placeholder="Password"><br>
        <input name="firstName" type="text" placeholder="First name" autocomplete="true"><br>
        <input name="lastName" type="text" placeholder="Last name" autocomplete="true"><br>
        <input name="cnp" type="text" placeholder="CNP" autocomplete="true"><br>
        <input name="email" type="text" placeholder="Email" autocomplete="true"><br>
        <input name="phone" type="text" placeholder="Phone" autocomplete="true"><br>
        <input name="address" type="text" placeholder="Address" autocomplete="true"><br><br>
        <input type="submit" value="Register">
    </form><br>
    <c:if test="${not empty error}">
        Error: ${error}
    </c:if>
</body>
</html>
