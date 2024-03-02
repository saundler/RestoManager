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

        .container {
            max-width: 500px;
            margin: 0 auto;
            padding: 20px;
            background-color: #fff;
            border-radius: 8px;
            box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
        }

        h1 {
            color: #333;
            text-align: center;
            margin-bottom: 20px;
        }

        h2 {
            color: #333;
            text-align: center;
            margin-top: 20px;
        }

        h3 {
            color: #555;
            text-align: center;
            margin-top: 20px;
        }

        hr {
            margin-top: 20px;
            margin-bottom: 20px;
            border: 1px solid #ddd;
        }

        .order-items {
            margin-bottom: 20px;
        }

        .order-item {
            padding: 15px;
            background-color: #f9f9f9;
            border-radius: 8px;
            box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);
            margin-bottom: 10px;
        }

        .order-item-info {
            margin-bottom: 10px;
        }

        .order-item-info p {
            margin: 0;
        }

        .order-item-info a {
            color: #337ab7;
            text-decoration: none;
            margin-left: 10px;
        }

        .order-item-action form {
            margin: 0;
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

        form {
            margin-bottom: 20px;
        }

        label {
            color: #555;
            display: block;
            margin-bottom: 5px;
        }

        input[type="text"],
        select {
            width: calc(100% - 22px);
            padding: 10px;
            margin-bottom: 10px;
            border: 1px solid #ddd;
            border-radius: 4px;
        }

        input[type="submit"] {
            background-color: #5cb85c;
            color: #fff;
            padding: 10px 20px;
            border: none;
            border-radius: 4px;
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

        .orders-link {
            text-align: center;
            margin-top: 20px;
        }

        .orders-link a {
            color: #337ab7;
            text-decoration: none;
            font-weight: bold;
        }

        .orders-link a:hover {
            text-decoration: underline;
        }

        .quantity-control {
            display: flex;
            align-items: center;
            justify-content: space-between; /* Расположение элементов справа и слева */
            margin-bottom: 10px; /* Добавил отступ снизу для выравнивания */
        }

        .quantity-input {
            flex: 1;
            width: calc(100% - 140px); /* Уменьшил ширину поля для отображения числа */
            padding: 10px;
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
    <h2>Подробная информация о заказе</h2>
    <h3>Номер заказа ${order.id}</h3>
    <div class="order-items">
        <#if order.items?size gt 0>
            <#list order.items as orderItem>
                <div class="order-item">
                    <div class="order-item-info">
                        <p>
                            <b><a href="/order/${order.id}/orderItem/${orderItem.id}"
                                  style="color: #337ab7; text-decoration: none;">${orderItem.dish.title}</a></b>
                            ${orderItem.dish.price} * ${orderItem.quantity} = ${orderItem.totalCost} руб.
                        </p>
                    </div>

                    <#if order.orderStatus.name() != "STATUS_READY" && order.user == user>
                        <div class="order-item-action">
                            <form action="/order/${order.id}/orderItem/delete/${orderItem.id}" method="post">
                                <input type="hidden" name="_csrf" value="${_csrf.token}">
                                <input type="submit" value="Удалить элемент заказа"/>
                            </form>
                        </div>
                    </#if>
                </div>
            </#list>
        <#else>
            <h3>Элементов нет</h3>
        </#if>
    </div>
    <hr>
    <#if order.user == user>
        <#if order.orderStatus.name() == "STATUS_READY">
            <#if !order.paid>
                <form action="/order/pay/${order.id}" method="post">
                    <input type="hidden" name="_csrf" value="${_csrf.token}">
                    <input type="submit" value="Оплатить"/>
                </form>
            </#if>
            <#if order.review == 0>
                <form action="/order/review/${order.id}" method="post">
                    <label for="review">Оценка:</label>
                    <div class="range-slider">
                        <input type="range" name="review" min="1" max="5" step="1" id="reviewRange"/>
                        <span id="rangeValue">3</span>
                    </div><br><br>
                    <label for="comment">Отзыв:</label>
                    <input type="text" name="comment"/><br><br>
                    <input type="hidden" name="_csrf" value="${_csrf.token}">
                    <input type="submit" value="Оставить отзыв"/>
                </form>
            </#if>
        </#if>
        <#if order.orderStatus.name() != "STATUS_READY">
            <#if menu?size != 0 >
            <form id="addItemForm" action="/order/${order.id}/orderItem/create" method="post">
                <label for="dishSelect">Выберите блюдо:</label>
                <select id="dishSelect" name="dish">
                    <#list menu as dish>
                        <option value="${dish.id}" data-price="${dish.price}">${dish.title} - ${dish.price}руб.
                        </option>
                    </#list>
                </select><br><br>
                <div class="quantity-control">
                    <label for="quantity">Количество:</label>
                    <input type="text" id="quantity"  name="quantity" class="quantity-input" value="1" readonly/>
                    <button type="button" class="quantity-button" onclick="decrementQuantity()">-</button>
                    <button type="button" class="quantity-button" onclick="incrementQuantity()">+</button>
                </div><br><br>
                <input type="hidden" name="order" value="${order.id}">
                <input type="hidden" name="_csrf" value="${_csrf.token}">
                <input type="submit" value="Добавить к заказу"/>
            </form>
            <hr>
            </#if>
            <#if order.orderStatus.name() == "STATUS_CREATED">
                <form action="/order/create/${order.id}" method="post">
                    <input type="hidden" name="id" value="${order.id}">
                    <input type="hidden" name="_csrf" value="${_csrf.token}">
                    <input type="submit" value="Подтвердить заказ"/>
                </form>
            </#if>
            <div class="order-item-action">
                <form action="/order/delete/${order.id}" method="post">
                    <input type="hidden" name="_csrf" value="${_csrf.token}">
                    <input type="submit" value="Отменить заказ"/>
                </form>
            </div>
        </#if>
    </#if>

    <#if errorMessage??>
        <div class="error-message">${errorMessage}</div>
    </#if>
</div>
<div class="orders-link">
    <a href="/order">Заказы</a>
</div>

<script>
    const range = document.getElementById('reviewRange');
    const rangeValue = document.getElementById('rangeValue');

    range.addEventListener('input', () => {
        rangeValue.textContent = range.value;
    });

    function incrementQuantity() {
        const quantityInput = document.querySelector('input[name="quantity"]');
        const currentValue = parseInt(quantityInput.value);
        quantityInput.value = currentValue + 1;
    }

    function decrementQuantity() {
        const quantityInput = document.querySelector('input[name="quantity"]');
        const currentValue = parseInt(quantityInput.value);
        if (currentValue > 1) {
            quantityInput.value = currentValue - 1;
        }
    }
</script>

</body>
</html>
