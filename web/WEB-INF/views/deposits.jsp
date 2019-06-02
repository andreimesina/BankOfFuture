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
    <form action="/member/deposits/newDeposit">
        <input type="submit" value="Create deposit">
    </form>
    <c:if test="${error == 'FCD'}">
        <h2 style="color: red">Failed to create deposit!</h2>
    </c:if>
    <table>
        <tr>
            <th>Id</th>
            <th>Currency</th>
            <th>Amount</th>
        </tr>
        <c:forEach var = "deposit" items="${depositsList}" varStatus="loop">
            <tr>
                <td>
                    ${deposit.id}
                </td>
                <td>
                    ${deposit.currency}
                </td>
                <td>
                    ${deposit.amount}
                </td>
            </tr>
        </c:forEach>
    </table>
</body>
</html>
