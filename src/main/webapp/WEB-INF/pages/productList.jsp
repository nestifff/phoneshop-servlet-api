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
      <tr>
        <td>Image</td>
        <td>Description
          <tags:sorting sort="description" order="asc"/>
          <tags:sorting sort="description" order="desc"/>
        </td>
        <td class="price">
          Price
          <tags:sorting sort="price" order="asc"/>
          <tags:sorting sort="price" order="desc"/>
        </td>
      </tr>
    <c:forEach var="product" items="${products}">
      <tr>
        <td>
          <img class="product-tile" src="${product.imageUrl}">
        </td>
        <td>
            ${product.description}
        </td>
        <td class="price">
          <a href="${pageContext.servletContext.contextPath}/products/${product.id}?priceHistory=true">
            <fmt:formatNumber value="${product.price}" type="currency" currencySymbol="${product.currency.symbol}"/>
          </a>
        </td>
        <td>
          <a href="${pageContext.servletContext.contextPath}/products/${product.id}">Go to details</a>
        </td>
      </tr>
    </c:forEach>
  </table>
  <br>
  <h3>Recently viewed products:</h3>
  <table class="recentlyViewedTable">
    <tr>
      <c:forEach var="product" items="${recentlyViewed}">
        <td>
          <img class="product-tile" src="${product.imageUrl}">
          <p><a href="${pageContext.servletContext.contextPath}/products/${product.id}">${product.description}</a></p>
          <fmt:formatNumber value="${product.price}" type="currency"
                            currencySymbol="${product.currency.symbol}"/>

        </td>
      </c:forEach>
    </tr>
  </table>
  <br>
  <br>
  <br>
</tags:master>