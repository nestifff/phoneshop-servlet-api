<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>

<jsp:useBean id="products" type="java.util.ArrayList" scope="request"/>
<tags:master pageTitle="Product List">
  <br>
  <form>
    <input name="query" value="${param.query}">
    <button>Search</button>
  </form>
  <table>
    <thead>
      <tr>
        <td>Image</td>
        <td>Description
        <a href="?query=${param.query}&sort=description&order=asc">asc</a>
        <a href="?query=${param.query}&sort=description&order=desc">desc</a>
        </td>
        <td class="price">
          Price
          <a href="?query=${param.query}&sort=price&order=asc">asc</a>
          <a href="?query=${param.query}&sort=price&order=desc">desc</a>
        </td>
      </tr>
    </thead>
    <c:forEach var="product" items="${products}">
      <tr>
        <td>
          <img class="product-tile" src="${product.imageUrl}">
        </td>
        <td>${product.description}</td>
        <td class="price">
          <fmt:formatNumber value="${product.price}" type="currency" currencySymbol="${product.currency.symbol}"/>
        </td>
      </tr>
    </c:forEach>
  </table>
</tags:master>