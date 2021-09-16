<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>

<jsp:useBean id="productId" type="java.lang.String" scope="request"/>
<tags:master pageTitle="Product not found">
    <h1>Error 404</h1>
    <p>Product with id = ${productId} not found</p>
    <br>
    <tags:home/>
</tags:master>