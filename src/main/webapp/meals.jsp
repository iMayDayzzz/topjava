<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page import="java.time.format.DateTimeFormatter" %>

<html>
<head>
    <title>Meals list</title>
</head>
<body>
<h1>MEALS!!</h1>
<table>
    <tr>
    <td>
        Calories
    </td><td>
        Description
    </td><td>
        Time
    </td></tr>
    <c:forEach var="meal" items="${meals}">
        <c:choose>
            <c:when test="${meal.excess eq true}">
                <tr style="background: green">
            </c:when>
            <c:otherwise>
                <tr style="background: red">
            </c:otherwise>
        </c:choose>

        <td>${meal.calories}</td>
        <td>
                ${meal.description}
        </td>
        <td>
                ${meal.dateTime.format( DateTimeFormatter.ofPattern("dd.MM.yyyy"))}

        </td>

        </tr>
    </c:forEach>

</table>
</body>
</html>
