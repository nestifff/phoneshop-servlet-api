<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>

<jsp:useBean id="cart" type="com.es.phoneshop.model.cart.domain.Cart" scope="request"/>
<tags:master pageTitle="Cart">
    <h1> Your cart </h1>

    <c:if test="${not empty param.message}">
        <p class="greenText">
                ${param.message}
        </p>
    </c:if>
    <c:if test="${not empty errors}">
        <p class="redText">
            There were some errors updating cart
        </p>
    </c:if>

    <c:if test="${not empty cart.items}">
        <form method="post" action="${pageContext.servletContext.contextPath}/cart">
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
                <c:forEach var="item" items="${cart.items}" varStatus="status">
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
                            <c:set var="error" value="${errors[item.product.id]}"/>
                            <input class="inputQuantity" name="quantity"
                                   value="${not empty error ? paramValues['quantity'][status.index] : item.quantity}"/>
                            <c:if test="${not empty error}">
                                <p class="redText">${errors[item.product.id]}</p>
                            </c:if>
                            <input type="hidden" value="${item.product.id}" name="productId">
                        </td>
                        <td>
                            <button form="deleteCartItem"
                                    formaction="${pageContext.servletContext.contextPath}/cart/deleteCartItem/${item.product.id}">
                                Delete
                            </button>
                        </td>
                    </tr>
                </c:forEach>
            </table>
            <p>
                <button>Update cart</button>
            </p>
        </form>

        <form id="deleteCartItem" method="post"></form>

        <c:if test="${not empty cart.items}">
            <h4>Total cost: <fmt:formatNumber value="${cart.totalCost}" type="currency"
                                              currencySymbol="${cart.items[0].product.currency.symbol}"/></h4>
        </c:if>
        <c:if test="${not empty cart.items}">
            <h4>Total number of products: ${cart.totalQuantity}</h4>
        </c:if>
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