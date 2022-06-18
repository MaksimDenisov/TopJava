<%@ page contentType="text/html;charset=UTF-8" %>
<%@taglib uri="http://ru.javawebinar.topjava/functions" prefix="f" %>

<html lang="ru">
<head>
	<title>Users</title>
</head>
<body>
<h3><a href="index.html">Home</a></h3>
<hr>
<h2>${meal.id == null ? 'Add' : 'Update'} meals</h2>
<form method="POST" action='meals'>
	<jsp:useBean id="meal" type="ru.javawebinar.topjava.model.Meal" scope="request"/>
	<input type="hidden" name="id" value="${meal.id}"/><br/>
	DateTime : <input
		type="datetime-local" name="dateTime"
		value="${meal.dateTime}"><br/>
	Description : <input
		type="text" name="description"
		value="${meal.description}"/><br/>
	Calories : <input
		type="integer" name="calories"
		value="${meal.calories}"/><br/>
	<button type="submit">${meal.id == null ? 'Add' : 'Update'}</button>
</form>
</body>
</html>