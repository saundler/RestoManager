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

        hr {
            margin-top: 20px;
            margin-bottom: 20px;
            border: 1px solid #ddd;
        }

        .container {
            max-width: 800px;
            margin: 0 auto;
            background-color: #fff;
            border-radius: 8px;
            box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
            padding: 20px;
        }

        h3 {
            color: #555;
            text-align: center;
            margin-top: 20px;
        }

        form {
            margin-bottom: 20px;
        }

        label {
            color: #555;
            display: block;
            margin-bottom: 5px;
        }

        input[type="text"],
        input[type="number"],
        input[type="submit"] {
            width: 100%;
            padding: 10px;
            margin-top: 5px;
            margin-bottom: 10px;
            border: 1px solid #ddd;
            border-radius: 4px;
            box-sizing: border-box;
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

        .dish-item {
            margin-bottom: 10px;
            padding: 10px;
            border: 1px solid #ddd;
            border-radius: 4px;
        }

        .dish-item a {
            color: #337ab7;
            text-decoration: none;
            font-weight: bold;
        }

        .dish-item a:hover {
            text-decoration: underline;
        }

        .no-dishes {
            text-align: center;
            margin-top: 20px;
            color: #555;
        }

        .error-message {
            color: red;
            font-size: 14px;
            margin-top: 10px;
            text-align: center;
        }

        .menu-link {
            text-align: center;
            margin-top: 20px;
        }

        .menu-link a {
            color: #337ab7;
            text-decoration: none;
            font-weight: bold;
        }

        .menu-link a:hover {
            text-decoration: underline;
        }

    </style>
</head>
<body>
<div class="container">
    <h1>RestoManager</h1>
    <hr>
    <#if user.name?? && user.roles?seq_contains(ROLE_ADMIN)>
        <form action="/dish" method="get">
            Поиск по названию блюда: <input type="text" name="title">
            <input type="submit" value="Поиск">
        </form>
        <h3>Cписок блюд</h3>
        <#list dishes as dish>
            <div class="dish-item">
                <p><a href="/dish/${dish.id}"><b>${dish.title}</b></a> ${dish.price} руб, Осталось: ${dish.quantity}</p>
            </div>
        <#else>
            <div class="no-dishes">
                <h3>Блюд нет</h3>
            </div>
        </#list>
        <hr>
        <h3>Добавить новое блюдо</h3>
        <form id="addDishForm" action="/dish/create" method="post" onsubmit="return validateForm()">
            <label for="title">Название блюда:</label>
            <input type="text" name="title" id="title"><br><br>
            <label for="price">Цена:</label>
            <input type="number" name="price" id="price" min="0" max="999999999"><br><br>
            <label for="cookingTimeInMinutes">Время приготовления в минутах:</label>
            <input type="number" name="cookingTimeInMinutes" id="cookingTimeInMinutes" min="0" max="999999999"><br><br>
            <label for="quantity">Количество:</label>
            <input type="number" name="quantity" id="quantity"  min="0" max="999999999"><br><br>
            <input type="hidden" name="_csrf" value="${_csrf.token}">
            <input type="submit" value="Добавить блюдо">
            <div id="errorMessage" class="error-message"></div>
        </form>
    <#else>
        <div class="no-dishes">
            У вас недостаточно прав доступа
        </div>
    </#if>
    <#if errorMessage??>
        <div class="error-message">${errorMessage}</div>
    </#if>
</div>
<div class="menu-link">
    <a href="/">Меню</a>
</div>
<script>
    function validateForm() {
        var title = document.getElementById('title').value;
        var price = document.getElementById('price').value;
        var cookingTimeInMinutes = document.getElementById('cookingTimeInMinutes').value;
        var quantity = document.getElementById('quantity').value;
        var errorMessage = document.getElementById('errorMessage');

        if (title === '' || price === '' || cookingTimeInMinutes === '' || quantity === '') {
            alert("Пожалуйста, заполните все поля.");
            return false;
        }
        return true;
    }
</script>
</body>
</html>
