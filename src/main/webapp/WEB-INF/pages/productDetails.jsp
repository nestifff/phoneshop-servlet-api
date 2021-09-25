<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>

<jsp:useBean id="product" type="com.es.phoneshop.model.product.domain.Product" scope="request"/>
<tags:master pageTitle="Product Details">
    <h1>
            ${product.description}
    </h1>


    <div>
        ${cart}
    </div>
    <c:if test="${not empty param.message and empty error}">
        <p class="greenText">
                ${param.message}
        </p>
    </c:if>
    <c:if test="${not empty error}">
        <p class="redText">
            There was an error adding to cart: ${error}
        </p>
    </c:if>

    <table>
        <tr>
            <td>Image</td>
            <td><img src="${product.imageUrl}"></td>
        </tr>
        <tr>
            <td>Code</td>
            <td>${product.code}</td>
        </tr>
        <tr>
            <td>Stock</td>
            <td>${product.stock}</td>
        </tr>
        <tr>
            <td>Price</td>
            <td>
                <a href="${pageContext.servletContext.contextPath}/products/${product.id}?priceHistory=true">
                    <fmt:formatNumber value="${product.price}" type="currency"
                                      currencySymbol="${product.currency.symbol}"/>
                </a>
            </td>
        </tr>
    </table>
    <br>
    <form method="post">
        <p>Quantity: </p>
        <input class="inputQuantity" name="quantity" value="${not empty error ? param.quantity : 1}">
        <button>Add to cart</button>
        <c:if test="${not empty error}">
            <div class="redText">
                    ${error}
            </div>
        </c:if>
    </form>

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
    <tags:home/>
</tags:master>