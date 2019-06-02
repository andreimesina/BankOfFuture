<%@ page contentType="text/html;charset=UTF-8"%>
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
    <h2>Create new deposit</h2>
    <form id="form" action="/member/deposits/newDeposit/create" method="post">
        <select name="currency" form="form">
            <option value="euro">Euro</option>
            <option value="usd">USD</option>
            <option value="ron">RON</option>
        </select><br>
        <input name="amount" type="text" placeholder="Amount"><br>
        <input type="submit" value="Create">
    </form>
</body>
</html>
