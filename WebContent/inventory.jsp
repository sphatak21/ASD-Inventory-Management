<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri = "http://java.sun.com/jsp/jstl/core" prefix = "c"%>

<!DOCTYPE html>
<html>
	<head>
		
		<meta charset="UTF-8">
		<title>Inventory Management</title>
		<style type = "text/css">
			<%@ include file = "css/styles.css"%>
		</style>
	</head>
	<body>
		<div>
			<h1>Inventory Management</h1>
			
			<div class = "header">
				<a href = "${pageContext.request.contextPath }/" class="header-button">VIEW ALL</a>
				<a href = "${pageContext.request.contextPath }/add" class = "header-button">ADD A PRODUCT</a>
				
			</div>
		</div>
		<div>
			
			<table border = "1">
			<tr>
				<form action = "search" method = "post">
				<td><label>
					<select name = "searchParameter">			
						<option>ID</option>
						<option>Name</option>
						<option>Unit Price</option>
						<option>Brand</option>		
					</select>
				</label></td>
				<td><label>
					<input type = "text" name = "keyword"/>		
				</label></td>
				<td><input type = "submit" value = "Search" name = "submit"/></td>
			</form>
			</tr>
				<tr>
					<td>ID</td>
					<td>Name</td>
					<td>Unit Price</td>
					<td>Brand</td>
					<td>Description</td>
					<td>Stock</td>
					<td>Actions</td>
				</tr>
				
				<c:forEach var = "product" items = "${products}">
					<tr>
						<td><c:out value = "${product.id }"/></td>
						<td><c:out value = "${product.name}"/></td>
						<td><c:out value = "${product.unit_price}"/></td>
						<td><c:out value = "${product.brand}"/></td>
						<td><c:out value = "${product.description}"/></td>
						<td><c:out value = "${product.stock }"/></td>
						<td>
							<!-- <a href = "${pageContext.request.contextPath }/update?action=rent&id=${book.id }" class = "button">RENT</a>
							<a href = "${pageContext.request.contextPath }/update?action=return&id=${book.id }" class = "button">RETURN</a>
							 -->
							 <a href = "${pageContext.request.contextPath }/edit?id=${product.id }" class = "button">EDIT</a>
							 <a href = "${pageContext.request.contextPath }/update?action=delete&id=${product.id }" class = "button">DELETE</a>
						</td>
					</tr>
				</c:forEach>
			</table>
		</div>
	</body>	
</html>