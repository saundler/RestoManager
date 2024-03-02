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

        hr {
            margin-top: 20px;
            margin-bottom: 20px;
            border: 1px solid #ddd;
        }

        .container {
            max-width: 500px;
            margin: 0 auto;
            padding: 20px;
            background-color: #fff;
            border-radius: 8px;
            box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
        }

        label {
            color: #555;
            margin-top: 10px;
            display: block;
        }

        input[type="text"],
        input[type="number"] {
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

        .order-item-action input[type="submit"] {
            background-color: #dc3545;
            color: #fff;
            border: none;
            border-radius: 4px;
            cursor: pointer;
            transition: background-color 0.3s;
            padding: 8px 16px;
        }

        .order-item-action input[type="submit"]:hover {
            background-color: #c82333;
        }

        .error-message {
            color: red;
            font-size: 14px;
            margin-top: 10px;
            text-align: center;
        }

        .original-values {
            display: none;
        }

        .link {
            text-align: center;
            margin-top: 20px;
        }

        .link a {
            color: #337ab7;
            text-decoration: none;
            font-weight: bold;
        }

        .link a:hover {
            text-decoration: underline;
        }
    </style>
</head>
<body>
<div class="container">
    <h1>RestoManager</h1>
    <hr>
    <h4>Подробная информация о блюде</h4>
    <form id="editDishForm" action="/dish/edit/${dish.id}" method="post">
        <label for="title">Название блюда:</label>
        <input type="text" id="title" name="title" value="${dish.title}" <#if !user.roles?? || !user.roles?seq_contains(ROLE_ADMIN)>readonly</#if>>
        <label for="price">Цена:</label>
        <input type="text" id="price" name="price" value="${dish.price}" pattern="[0-9]*"
               <#if !user.roles?? || !user.roles?seq_contains(ROLE_ADMIN)>readonly</#if>>
        <label for="cookingTime">Время приготовления (мин):</label>
        <input type="text" id="cookingTime" name="cookingTimeInMinutes" value="${dish.cookingTimeInMinutes}"
               pattern="[0-9]*" <#if !user.roles?? || !user.roles?seq_contains(ROLE_ADMIN)>readonly</#if>>
        <label for="quantity">Количество:</label>
        <input type="text" id="quantity" name="quantity" value="${dish.quantity}" pattern="[0-9]*"
               <#if !user.roles?? || !user.roles?seq_contains(ROLE_ADMIN)>readonly</#if>>
        <div class="original-values">
            <input type="hidden" id="originalTitle" value="${dish.title}">
            <input type="hidden" id="originalPrice" value="${dish.price}">
            <input type="hidden" id="originalCookingTime" value="${dish.cookingTimeInMinutes}">
            <input type="hidden" id="originalQuantity" value="${dish.quantity}">
        </div>
        <#if user.roles?? && user.roles?seq_contains(ROLE_ADMIN)>
            <input type="hidden" name="_csrf" value="${_csrf.token}">
            <input type="submit" value="Внести правки"/>
        </#if>
    </form>
    <#if user.roles?? && user.roles?seq_contains(ROLE_ADMIN)>
        <div class="order-item-action">
            <form action="/dish/delete/${dish.id}" method="post">
                <input type="hidden" name="_csrf" value="${_csrf.token}">
                <input type="submit" value="Удалить блюдо"/>
            </form>
        </div>
    </#if>
    <#if errorMessage??>
        <div class="error-message">${errorMessage}</div>
    </#if>
</div>
<div class="link">
    <#if user.roles?? && user.roles?seq_contains(ROLE_ADMIN)>
        <a href="/dish">Список блюд</a>
        |
    </#if>
    <a href="/">Меню</a>
</div>
<script>
    document.getElementById('editDishForm').onsubmit = function () {
        var titleInput = this.querySelector('input[name="title"]');
        if (titleInput.value === '') {
            titleInput.value = document.getElementById('originalTitle').value;
        }
        var priceInput = this.querySelector('input[name="price"]');
        if (priceInput.value === '') {
            priceInput.value = document.getElementById('originalPrice').value;
        }
        var timeInput = this.querySelector('input[name="cookingTimeInMinutes"]');
        if (timeInput.value === '') {
            timeInput.value = document.getElementById('originalCookingTime').value;
        }
        var amountInput = this.querySelector('input[name="quantity"]');
        if (amountInput.value === '') {
            amountInput.value = document.getElementById('originalQuantity').value;
        }
    };

    document.addEventListener('DOMContentLoaded', function () {
        var priceInput = document.getElementById('price');
        var cookingTimeInput = document.getElementById('cookingTime');
        var quantityInput = document.getElementById('quantity');
        priceInput.value = priceInput.value.replace(/\s/g, '');
        cookingTimeInput.value = cookingTimeInput.value.replace(/\s/g, '');
        quantityInput.value = quantityInput.value.replace(/\s/g, '');
        var maxValue = 999999999;
        priceInput.addEventListener('input', function() {
            var value = priceInput.value.replace(/\s/g, '');
            if (parseInt(value) > maxValue) {
                priceInput.value = maxValue;
            }
        });
        cookingTimeInput.addEventListener('input', function() {
            var value = cookingTimeInput.value.replace(/\s/g, '');
            if (parseInt(value) > maxValue) {
                cookingTimeInput.value = maxValue;
            }
        });
        quantityInput.addEventListener('input', function() {
            var value = quantityInput.value.replace(/\s/g, '');
            if (parseInt(value) > maxValue) {
                quantityInput.value = maxValue;
            }
        });
    });
</script>
</body>
</html>
