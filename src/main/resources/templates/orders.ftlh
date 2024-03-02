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

        hr {
            margin-top: 20px;
            margin-bottom: 20px;
            border: 1px solid #ddd;
        }

        .order {
            background-color: #fff;
            border-radius: 8px;
            box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
            padding: 15px;
            padding-bottom: 7px;
            margin-bottom: 10px;
        }

        .order-info {
            margin-bottom: 10px;
        }

        .order-info p {
            margin: 0;
        }

        .order-info a {
            color: #337ab7;
            text-decoration: none;
            margin-left: 10px;
        }

        .orders-action {
            text-align: center;
        }

        .orders-action input[type="submit"] {
            margin-top: 10px;
            background-color: #5cb85c;
            color: #fff;
            padding: 10px 20px;
            border: none;
            border-radius: 4px;
            cursor: pointer;
            width: 100%;
            transition: background-color 0.3s;
        }

        .orders-action input[type="submit"]:hover {
            background-color: #449d44;
        }

        .no-orders {
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
    <h2>Заказы</h2>
    <#list orders as order>
        <div class="order">
            <div class="order-info">
                <p>
                    <b><a href="/order/${order.id}" style="color: #337ab7; text-decoration: none;">${order.id}</a></b>
                    <#if order.orderStatus.name() == "STATUS_CREATED">
                        | Ожидает подтверждения |
                    <#elseif order.orderStatus.name() == "STATUS_ACCEPTED">
                        | Принят |
                    <#elseif order.orderStatus.name() == "STATUS_PREPARING">
                        | Готовится |
                    <#elseif order.orderStatus.name() == "STATUS_READY">
                        | Готов |
                    </#if>
                    Стоимость: ${order.totalPrice} руб.
                </p>
                <#if order.orderStatus.name() == "STATUS_CREATED">
                    <div class="orders-action">
                        <form action="/order/create/${order.id}" method="post">
                            <input type="hidden" name="id" value="${order.id}">
                            <input type="hidden" name="_csrf" value="${_csrf.token}">
                            <input type="submit" value="Подтвердить заказ"/>
                        </form>
                    </div>
                </#if>
                <#if order.orderStatus.name() == "STATUS_READY" && !order.paid>
                    <div class="orders-action">
                        <form action="/order/pay/${order.id}" method="post">
                            <input type="hidden" name="_csrf" value="${_csrf.token}">
                            <input type="submit" value="Оплатить заказ"/>
                        </form>
                    </div>
                </#if>
                <#if order.orderStatus.name() == "STATUS_READY" && order.paid && order.review == 0>
                    <div class="orders-action">
                        <form action="/order/${order.id}" method="get">
                            <input type="hidden" name="_csrf" value="${_csrf.token}">
                            <input type="submit" value="Оставить отзыв"/>
                        </form>
                    </div>
                </#if>
            </div>
        </div>
    <#else>
        <div class="no-orders">
            <h3>Заказов нет</h3>
        </div>
    </#list>
    <#if user.name??>
        <hr>
        <div class="orders-action">
            <form action="/order/create" method="post">
                <input type="hidden" name="_csrf" value="${_csrf.token}">
                <input type="submit" value="Добавить заказ"/>
            </form>
        </div>
    </#if>
</div>
<div class="menu-link">
    <a href="/">Меню</a>
</div>
</body>
</html>
