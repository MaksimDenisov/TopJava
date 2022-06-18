<%@ page contentType="text/html;charset=UTF-8" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib uri="http://ru.javawebinar.topjava/functions" prefix="f" %>
<html lang="ru">
<head>
	<title>Meals</title>
</head>
<body>
<h3><a href="index.html">Home</a></h3>
<hr>
<h2>Meals</h2>
<style>
    .normal {
        color: green;
    }

    .exceed {
        color: red;
    }
</style>
<table width="100%" border="1">
	<thead>
	<tr>
		<td>DateTime</td>
		<td>Description</td>
		<td>Calories</td>
		<td>Edit</td>
		<td>Delete</td>
	</tr>
	</thead>
	<tbody>
	<a href="meals?action=add">Add meals</a>
	<br>
	<jsp:useBean id="meals" scope="request" type="java.util.List"/>
	<c:forEach var="meal" items="${meals}">
		<jsp:useBean id="meal" type="ru.javawebinar.topjava.model.MealTo"/>
		<tr class="${meal.excess ? 'exceed' : 'normal'}">
			<td>${f:formatLocalDateTime(meal.dateTime)}</td>
			<td>${meal.description}</td>
			<td>${meal.calories}</td>
			<td><a href="meals?action=edit&id=${meal.id}">Edit</a></td>
			<td><a href="meals?action=delete&id=${meal.id}">Delete</a></td>
		</tr>
	</c:forEach>
	</tbody>
</table>

</body>
</html>