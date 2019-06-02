<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <meta http-equiv="Cache-Control" content="no-cache, no-store, must-revalidate" />
    <meta http-equiv="Pragma" content="no-cache" />
    <meta http-equiv="Expires" content="0" />
    <title>${pageTitle}</title>
    <style>
        table {
            font-family: arial, sans-serif;
            border-collapse: collapse;
            width: 100%;
        }

        td, th {
            border: 1px solid #dddddd;
            text-align: left;
            padding: 8px;
        }

        tr:nth-child(even) {
            background-color: #dddddd;
        }
    </style>
</head>
<body>
    <form action="/member/transactions/newTransaction">
        <input type="submit" value="New transaction">
    </form><br>
    <h2>Transactions</h2>
    <c:if test="${error == 'FCT'}">
        <h3 style="color: firebrick">Failed to create transaction!</h3>
    </c:if>
    <h3>Sent</h3>
        <table>
            <tr>
                <th>Id</th>
                <th>Sender deposit ID</th>
                <th>Receiver deposit ID</th>
                <th>Amount</th>
                <th>Accepted</th>
                <th>Pending</th>
                <th>Actions</th>
            </tr>
            <c:forEach var = "transaction" items="${senderTransactionsList}" varStatus="loop">
                <tr>
                    <td>
                        ${transaction.id}
                    </td>
                    <td>
                        ${transaction.senderDepositId}
                    </td>
                    <td>
                        ${transaction.receiverDepositId}
                    </td>
                    <td>
                        ${transaction.amount}
                    </td>
                    <td>
                        ${transaction.isAccepted}
                    </td>
                    <td>
                        ${transaction.isPending}
                    </td>
                    <td>
                    </td>
                </tr>
            </c:forEach>
        </table>
    <h3>Received</h3>
    <table>
        <tr>
            <th>Id</th>
            <th>Sender deposit ID</th>
            <th>Receiver deposit ID</th>
            <th>Amount</th>
            <th>Accepted</th>
            <th>Pending</th>
            <th>Actions</th>
        </tr>
        <c:forEach var = "transaction" items="${receiverTransactionsList}" varStatus="loop">
            <tr>
                <td>
                    ${transaction.id}
                </td>
                <td>
                    ${transaction.senderDepositId}
                </td>
                <td>
                    ${transaction.receiverDepositId}
                </td>
                <td>
                    ${transaction.amount}
                </td>
                <td>
                    ${transaction.isAccepted}
                </td>
                <td>
                    ${transaction.isPending}
                </td>
                <td>
                    <c:if test="${transaction.isPending == true}">
                        <form action="/member/transactions/acceptTransaction" method="post">
                            <input name="id" type="text" value="${transaction.id}" hidden>
                            <input name="senderDepositId" type="text" value="${transaction.senderDepositId}" hidden>
                            <input name="receiverDepositId" type="text" value="${transaction.receiverDepositId}" hidden>
                            <input name="amount" type="text" value="${transaction.amount}" hidden>
                            <input name="isAccepted" type="text" value="${transaction.isAccepted}" hidden>
                            <input name="isPending" type="text" value="${transaction.isPending}" hidden>
                            <input type="submit" value="Accept">
                        </form>
                        <form action="/member/transactions/denyTransaction" method="post">
                            <input name="id" type="text" value="${transaction.id}" hidden>
                            <input name="senderDepositId" type="text" value="${transaction.senderDepositId}" hidden>
                            <input name="receiverDepositId" type="text" value="${transaction.receiverDepositId}" hidden>
                            <input name="amount" type="text" value="${transaction.amount}" hidden>
                            <input name="isAccepted" type="text" value="${transaction.isAccepted}" hidden>
                            <input name="isPending" type="text" value="${transaction.isPending}" hidden>
                            <input type="submit" value="Deny">
                        </form>
                    </c:if>
                </td>
            </tr>
        </c:forEach>
    </table>
</body>
</html>
