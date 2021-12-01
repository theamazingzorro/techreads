<!DOCTYPE html>

<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<html>
<body>
	<h1>Add Book</h1>

	<%--@elvariable id="bookForm" type="com.manifestcorp.techreads.model.Book"--%>
	<form:form method="post" modelAttribute="bookForm" action="${pageContext.request.contextPath}/books/edit">
		<form:input path="id" type="hidden" />
		<label>Title: <form:input path="title" type="text" /> <form:errors path="title"/> </label> <br />
		<label>Author: <form:input path="author" type="text" /> <form:errors path="author"/> </label> <br />
		<label>Rating: <form:input path="rating" type="number" /> <form:errors path="rating"/> </label> <br />
		<label>Cover Image URL: <form:input path="coverURL" type="text" /> <form:errors path="coverURL"/> </label> <br/>
		<button type="submit">Add</button>
	</form:form>

</body>
</html>