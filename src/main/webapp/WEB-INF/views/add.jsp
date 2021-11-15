<!DOCTYPE html>

<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<html>
<body>
	<h1>Add Book</h1>

	<%--@elvariable id="bookForm" type="com.manifestcorp.techreads.model.Book"--%>
	<form:form method="post" modelAttribute="bookForm" action="${pageContext.request.contextPath}/books/submit">
		<label>Title: <form:input path="title" type="text" /></label>
		<label>Author: <form:input path="author" type="text" /></label>
		<label>Rating: <form:input path="rating" type="text" /></label>
		<label>Cover Image URL: <form:input path="coverURL" type="text" /></label>
		<button type="submit">Add</button>
	</form:form>

</body>
</html>