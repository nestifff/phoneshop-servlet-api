<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>

<jsp:useBean id="product" type="com.es.phoneshop.model.product.Product" scope="request"/>
<tags:master pageTitle="Product Details">
    <h1>
            ${product.description}
    </h1>


    <p>
        Cart: ${cart}
    </p>
    <c:if test="${not empty param.message and empty error}">
        <p style="color: green">
                ${param.message}
        </p>
    </c:if>
    <c:if test="${not empty error}">
        <p style="color: red">
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
        <input name="quantity" value="${not empty error ? param.quantity : 1}">
        <button>Add to cart</button>
        <c:if test="${not empty error}">
            <div style="color: red">
                    ${error}
            </div>
        </c:if>
    </form>
    <tags:home/>
</tags:master>