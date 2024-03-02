<!DOCTYPE html>
<html>
<head>
    <title>RestoManager</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            background-color: #f4f4f4;
            margin: 0;
            padding: 20px;
        }

        h1 {
            color: #333;
            text-align: center;
        }

        h4 {
            margin-top: 20px;
            color: #555;
            text-align: center;
        }

        form {
            background-color: #ffffff;
            box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
            padding: 20px;
            border-radius: 8px;
            max-width: 300px;
            margin: 20px auto;
        }

        label {
            color: #555;
            margin-top: 10px;
            display: block;
        }

        input[type="name"],
        input[type="password"] {
            margin-top: 5px;
            margin-bottom: 10px;
            border: 1px solid #ddd;
            border-radius: 4px;
            padding: 10px;
            width: calc(100% - 22px);
        }

        input[type="submit"] {
            background-color: #5cb85c;
            color: #fff;
            padding: 10px;
            border: none;
            border-radius: 4px;
            margin-top: 10px;
            cursor: pointer;
            width: 100%;
            transition: background-color 0.3s;
        }

        input[type="submit"]:hover {
            background-color: #449d44;
        }

        .error-message {
            color: red;
            font-size: 14px;
            margin-top: 10px;
            text-align: center;
        }

        .login-link {
            text-align: center;
            margin-top: 20px;
        }

        .login-link a {
            color: #337ab7;
            text-decoration: none;
            font-weight: bold;
        }

        .login-link a:hover {
            text-decoration: underline;
        }
    </style>
</head>
<body>
<h1>RestoManager</h1>
<hr>
<h4>Регистрация</h4>
<form action="/registration" method="post" onsubmit="return validateForm()">
    <label for="name">Имя пользователя:</label>
    <input type="name" id="name" name="name" placeholder="Введите ваше имя">
    <label for="password">Пароль:</label>
    <input type="password" id="password" name="password" placeholder="Введите ваш пароль">
    <input type="hidden" name="_csrf" value="${_csrf.token}">
    <input type="submit" value="Зарегистрироваться"/>
</form>
<#if errorMessage??>
    <div class="error-message">${errorMessage}</div>
</#if>
<div class="login-link">
    <a href="/login">Вход</a>
</div>

<script>
    function validateForm() {
        var name = document.getElementById("name").value;
        var password = document.getElementById("password").value;

        if (name === "" || password === "") {
            alert("Пожалуйста, заполните все поля.");
            return false;
        }
        return true;
    }
</script>
</body>
</html>
