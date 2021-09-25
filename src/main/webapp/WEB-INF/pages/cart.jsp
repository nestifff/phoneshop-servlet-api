<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>

<jsp:useBean id="cart" type="com.es.phoneshop.model.cart.domain.Cart" scope="request"/>
<tags:master pageTitle="Cart">
    <h1> Your cart </h1>

    <c:if test="${not empty cart.items}">
        <table>
            <tr>
                <td>Image</td>
                <td>Description
                </td>
                <td class="price">
                    Price
                </td>
                <td>Quantity</td>
            </tr>
            <c:forEach var="item" items="${cart.items}">
                <tr>
                    <td>
                        <img class="product-tile" src="${item.product.imageUrl}">
                    </td>
                    <td>
                        <a href="${pageContext.servletContext.contextPath}/products/${item.product.id}">${item.product.description}</a>
                    </td>
                    <td class="price">
                        <fmt:formatNumber value="${item.product.price}" type="currency"
                                          currencySymbol="${item.product.currency.symbol}"/>
                    </td>
                    <td>
                            ${item.quantity}
                    </td>
                </tr>
            </c:forEach>
        </table>
        <h4>Total cost: ${cart.totalCost}</h4>
        <br>
    </c:if>

    <c:if test="${empty cart.items}">
        <p> Cart is empty</p>
    </c:if>

    <h3>Recently viewed products:</h3>
    <table class="recentlyViewedTable">
        <tr>
            <c:forEach var="product" items="${recentlyViewed}">
                <td>
                    <img class="product-tile" src="${product.imageUrl}">
                    <p>
                        <a href="${pageContext.servletContext.contextPath}/products/${product.id}">${product.description}</a>
                    </p>
                    <fmt:formatNumber value="${product.price}" type="currency"
                                      currencySymbol="${product.currency.symbol}"/>

                </td>
            </c:forEach>
        </tr>
    </table>
    <br>
    <br>
    <br>
    <tags:home/>
</tags:master>