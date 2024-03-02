<!DOCTYPE html>
<html>
<head>
    <title>Статистика</title>
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

        table {
            width: 100%;
            border-collapse: collapse;
            margin-top: 20px;
        }

        th, td {
            border: 1px solid #ddd;
            padding: 8px;
            text-align: left;
        }

        th {
            background-color: #f2f2f2;
        }

        a {
            color: blue;
            text-decoration: underline;
            cursor: pointer;
        }
    </style>
</head>
<body>
<h1>Статистика</h1>
<hr>
<h2>Выручка: ${revenue} рублей</h2>
<h2>Самые популярные блюда:</h2>
<table>
    <thead>
    <tr>
        <th>Название</th>
        <th>Цена</th>
        <th>Время приготовления (мин)</th>
        <th>Количество проданных</th>
    </tr>
    </thead>
    <tbody>
    <#list dishes as dish>
        <tr>
            <td>${dish.title}</td>
            <td>${dish.price} руб.</td>
            <td>${dish.cookingTimeInMinutes}</td>
            <td>${dish.sold}</td>
        </tr>
    </#list>
    </tbody>
</table>
<h2>Последние заказы:</h2>
<table>
    <thead>
    <tr>
        <th>ID заказа</th>
        <th>Итоговая стоимость</th>
        <th>Статус</th>
        <th>Оплачен</th>
    </tr>
    </thead>
    <tbody>
    <#list orders as order>
        <tr>
            <td><a href="/order/${order.id}">${order.id}</a></td>
            <td>${order.totalPrice} руб.</td>
            <td>
                <#if order.orderStatus == 'STATUS_CREATED'>
                    Ожидает подтверждения
                <#elseif order.orderStatus == 'STATUS_ACCEPTED'>
                    Принят
                <#elseif order.orderStatus == 'STATUS_PREPARING'>
                    Готовиться
                <#elseif order.orderStatus == 'STATUS_READY'>
                    Готов
                </#if>
            </td>
            <td>${order.paid?string('Да', 'Нет')}</td>
        </tr>
    </#list>
    </tbody>
</table>
</body>
</html>
