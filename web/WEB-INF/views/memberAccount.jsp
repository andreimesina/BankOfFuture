<%@ page contentType="text/html;charset=UTF-8"%>
<html>
<head>
    <meta http-equiv="Cache-Control" content="no-cache, no-store, must-revalidate" />
    <meta http-equiv="Pragma" content="no-cache" />
    <meta http-equiv="Expires" content="0" />
    <title>${pageTitle}</title>
</head>
<body>
    <h3>Account info</h3><br>
    <form action="/member/updateAccount" method="post">
        First name: <input name="firstName" type="text" value="${memberDetails.firstName}" autofocus autocomplete="true"><br>
        Last name: <input name="lastName" type="text" value="${memberDetails.lastName}" autocomplete="true"><br>
        CNP: <input name="cnp" type="text" value="${memberDetails.cnp}" autocomplete="true"><br>
        Email: <input name="email" type="text" value="${memberDetails.email}" autocomplete="true"><br>
        Phone: <input name="phone" type="text" value="${memberDetails.phone}" autocomplete="true"><br>
        Address: <input name="address" type="text" value="${memberDetails.address}" autocomplete="true"><br>
        <input type="submit" value="Modify">
    </form>
    <br>
    <form action="/member/removeAccount">
        <input type="submit" value="DELETE ACCOUNT" style="color: firebrick">
    </form>
</body>
</html>
