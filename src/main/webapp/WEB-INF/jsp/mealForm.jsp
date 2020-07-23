<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>
<head>
    <title>Meal</title>
    <link rel="stylesheet" href="css/style.css">
</head>
<body>
<section>
    <h3><a href="index.html">Home</a></h3>
    <hr>

    <c:if test="${meal.id != null}">
        <h2>Edit</h2>
    </c:if>
    <c:if test="${meal.id == null}">
        <h2>Add</h2>
    </c:if>

    <c:if test="${meal.id != null}">
    <form method="post" action="/topjava/meals/update/${meal.id}">
    </c:if>
        <c:if test="${meal.id == null}">
        <form method="post" action="/topjava/meals/create">
            </c:if>

        <input type="hidden" name="id" value="${meal.id}">
        <dl>
            <dt>DateTime:</dt>
            <dd><input type="datetime-local" value="${meal.dateTime}" name="dateTime" required></dd>
        </dl>
        <dl>
            <dt>Description:</dt>
            <dd><input type="text" value="${meal.description}" size=40 name="description" required></dd>
        </dl>
        <dl>
            <dt>Calories:</dt>
            <dd><input type="number" value="${meal.calories}" name="calories" required></dd>
        </dl>
        <button type="submit">Save</button>
        <button onclick="window.history.back()" type="button">Cancel</button>
    </form>
</section>
</body>
</html>
