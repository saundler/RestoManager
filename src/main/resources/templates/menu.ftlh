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

        h3 {
            color: #555;
            text-align: center;
            margin-top: 10px;
        }

        h4 {
            margin-top: 20px;
            color: #555;
            text-align: center;
        }

        hr {
            margin-top: 20px;
            margin-bottom: 20px;
            border: 1px solid #ddd;
        }

        form.menuForm {
            display: flex;
            justify-content: center;
            margin-bottom: 10px;
        }

        input[type="text"],
        input[type="submit"] {
            padding: 10px;
            border: 1px solid #ddd;
            border-radius: 4px;
        }

        input[type="submit"] {
            background-color: #5cb85c;
            color: #fff;
            cursor: pointer;
            transition: background-color 0.3s;
        }

        input[type="submit"]:hover {
            background-color: #449d44;
        }

        .menu-item {
            margin-bottom: 10px;
            padding: 10px;
            border: 1px solid #ddd;
            border-radius: 4px;
            background-color: #fff;
        }

        .menu-item a {
            color: #337ab7;
            text-decoration: none;
            font-weight: bold;
        }

        .menu-item a:hover {
            text-decoration: underline;
        }

        .user-info {
            text-align: center;
            margin-bottom: 10px;
        }

        .user-info span {
            font-weight: bold;
            color: #555;
        }

        .btn-container {
            display: flex;
            justify-content: center;
            margin-bottom: 10px;
        }

        .btn-container form {
            margin-right: 10px;
        }
    </style>
</head>
<body>
<h1>RestoManager</h1>
<hr>
<#if user.name??>
    <div class="user-info">
        <h3><span>Имя пользователя:</span> ${user.name}</h3>
    </div>
    <div class="btn-container">
        <form action="/logout" method="post" class="menuForm">
            <input type="hidden" name="_csrf" value="${_csrf.token}">
            <input type="submit" value="Выйти"/>
        </form>
        <form action="/order" method="get" class="menuForm">
            <input type="hidden" name="_csrf" value="${_csrf.token}">
            <input type="submit" value="Заказы"/>
        </form>
        <#if user.roles?seq_contains(ROLE_ADMIN)>
            <form action="/dish" method="get" class="menuForm">
                <input type="hidden" name="_csrf" value="${_csrf.token}">
                <input type="submit" value="Список всех блюд"/>
            </form>
            <form action="/statistics" method="get" class="menuForm">
                <input type="hidden" name="_csrf" value="${_csrf.token}">
                <input type="submit" value="Cтатистика"/>
            </form>
        </#if>
    </div>
<#else>
    <form action="/login" method="get" class="menuForm">
        <input type="hidden" name="_csrf" value="${_csrf.token}">
        <input type="submit" value="Войти"/>
    </form>
</#if>
<hr>
<h4>Меню ресторана: </h4>
<form action="/" method="get" class="menuForm">
    Поиск по названию блюда: <input type="text" name="title">
    <input type="submit" value="Поиск"/>
</form>
<#list menu as dish>
    <div class="menu-item">
        <p><b><a href="/dish/${dish.id}">${dish.title}</a></b> ${dish.price} руб.
    </div>
<#else>
    <h3>Блюда нет</h3>
</#list>
</body>
</html>
