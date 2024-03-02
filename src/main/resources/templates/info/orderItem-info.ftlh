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
            margin-bottom: 20px;
        }

        h4 {
            color: #555;
            text-align: center;
            margin-top: 0;
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
            margin-top: 5px;
            display: block;
        }

        input[type="number"] {
            margin-top: 5px;
            margin-bottom: 5px;
            border: 1px solid #ddd;
            border-radius: 4px;
            padding: 10px;
            width: calc(100% - 22px);
        }

        input[type="submit"] {
            background-color: #5cb85c;
            color: #fff;
            padding: 10px 20px;
            border: none;
            border-radius: 4px;
            margin-top: 10px;
            margin-bottom: 10px;
            cursor: pointer;
            width: 100%;
            transition: background-color 0.3s;
        }

        input[type="submit"].delete-button {
            background-color: #dc3545;
        }

        input[type="submit"]:hover {
            background-color: #449d44;
        }

        input[type="submit"].delete-button:hover {
            background-color: #9c2929;
        }

        .error-message {
            color: red;
            font-size: 14px;
            margin-top: 10px;
            margin-bottom: 10px;
            text-align: center;
        }

        .order-link {
            text-align: center;
            margin-top: 20px;
        }

        .order-link a {
            color: #337ab7;
            text-decoration: none;
            font-weight: bold;
        }

        .order-link a:hover {
            text-decoration: underline;
        }

        .quantity-control {
            display: flex;
            align-items: center;
            justify-content: space-between;
            margin-bottom: 10px;
        }

        .quantity-label {
            width: 80px;
            text-align: left;
            margin-right: 10px;
        }

        .quantity-input {
            flex: 1;
            width: calc(100% - 140px);
            padding: 10px;
            margin-right: 10px;
            border: 1px solid #ddd;
            border-radius: 4px;
        }

        .quantity-button {
            width: 30px;
            height: 30px;
            border: none;
            background-color: #5cb85c;
            color: #fff;
            cursor: pointer;
            border-radius: 30%;
        }

        .quantity-button:hover {
            background-color: #449d44;
        }
    </style>
</head>
<body>
<div class="container">
    <h1>RestoManager</h1>
    <hr>
    <h4>Подробная информация об элементе заказа</h4>
    <b>Название блюда: </b>${orderItem.dish.title}<br>
    <b>Цена блюда: </b>${orderItem.dish.price}<br>
    <b>Время приготовления: </b>${orderItem.dish.cookingTimeInMinutes}<br>
    <b>Количество блюд: </b>${orderItem.quantity}<br>
    <b>Общая стоимость: </b>${orderItem.totalCost}<br>
    <hr>

    <#if user.name?? && user == orderItem.order.user && orderItem.order.orderStatus != 'STATUS_READY'>
        <form action="/order/${orderItem.order.id}/orderItem/delete/${orderItem.id}" method="post">
            <input type="hidden" name="_csrf" value="${_csrf.token}">
            <input type="submit" class="delete-button" value="Удалить элемент заказа"/>
        </form>
        <hr>
        <h3>Редактировать количество блюд</h3>
        <form id="editOrderItemQuantityForm" action="/order/${orderItem.order.id}/orderItem/editQuantity/${orderItem.id}" method="post">
            <div class="quantity-control">
                <span class="quantity-label">Количество блюд:</span>
                <input type="text" id="quantity"  name="quantity" class="quantity-input" value="1" readonly/>
                <button type="button" class="quantity-button" onclick="decrementQuantity()">-</button>
                <button type="button" class="quantity-button" onclick="incrementQuantity()">+</button>
            </div><br><br>
            <input type="hidden" id="originalQuantity" value="${orderItem.quantity}">
            <input type="hidden" name="_csrf" value="${_csrf.token}">
            <input type="submit" value="Внести правки"/>
        </form>
    </#if>
    <#if errorMessage??>
        <div class="error-message">${errorMessage}</div>
    </#if>
</div>
<div class="order-link">
    <a href="/order/${orderItem.order.id}">Заказ</a>
</div>
<script>
    function incrementQuantity() {
        const quantityInput = document.querySelector('.quantity-input');
        quantityInput.value = parseInt(quantityInput.value) + 1;
    }

    function decrementQuantity() {
        const quantityInput = document.querySelector('.quantity-input');
        if (parseInt(quantityInput.value) > 0) {
            quantityInput.value = parseInt(quantityInput.value) - 1;
        }
    }
</script>
</body>
</html>
