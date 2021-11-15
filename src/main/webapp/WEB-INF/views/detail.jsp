<!DOCTYPE html>

<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<html>
<body>

	<h1>Title: <c:out value="${book.title}" /></h1> <br />

	<img src="<c:out value="${book.coverURL}" />" /> <br />
	<label>Author: <c:out value="${book.author}" /></label> <br />
	<label>Rating: <c:out value="${book.rating}" /></label> <br />

</body>
</html>