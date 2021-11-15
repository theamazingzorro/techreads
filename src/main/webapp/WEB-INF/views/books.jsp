<!DOCTYPE html>

<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<html>
<body>

	<h1>Books</h1>

	<a href="<c:url value='/books/add' />">Add Books</a>

	<c:if test="${not empty books}">
		<ul>
			<c:forEach var="book" items="${books}">
				<li><c:out value="${book.title}" />
					<a href="books/details/<c:out value="${book.id}" />">Details</a>
					<a href="books/edit/<c:out value="${book.id}" />">Edit</a>
					<a href="books/remove/<c:out value="${book.id}" />">Delete</a>
				</li>
			</c:forEach>
		</ul>
	</c:if>

</body>
</html>