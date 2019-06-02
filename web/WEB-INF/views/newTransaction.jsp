<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <meta http-equiv="Cache-Control" content="no-cache, no-store, must-revalidate" />
    <meta http-equiv="Pragma" content="no-cache" />
    <meta http-equiv="Expires" content="0" />
    <title>${pageTitle}</title>
    <style>
        input {
            margin-top: 2px;
            margin-bottom: 2px;
        }
    </style>
</head>
<body>
    <h2>Create new transaction</h2>
    <form id="form" action="/member/transactions/newTransaction/create" method="post">
        <select name="senderDepositId" form="form">
            <c:forEach var="deposit" items="${deposits}" varStatus="loop">
                <option value="${deposit.id}">${deposit.amount} ${deposit.currency}</option>
            </c:forEach>
        </select><br>
        <input name="receiverDepositId" type="text" placeholder="Receiver deposit id" required><br>
        <input name="receiverFullName" type="text" placeholder="Receiver full name" required ><br>
        <input name="amount" type="text" placeholder="Amount" required><br>
        <input type="submit" value="Create">
    </form>
</body>
</html>
