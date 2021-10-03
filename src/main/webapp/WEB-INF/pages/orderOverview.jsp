<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>

<jsp:useBean id="order" type="com.es.phoneshop.model.order.Order" scope="request"/>
<tags:master pageTitle="Order Overview">
    <h1> Order Overview </h1>

    <c:if test="${not empty order.items}">
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
            <c:forEach var="item" items="${order.items}" varStatus="status">
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


        <c:if test="${not empty order.items}">
            <p>Subtotal: <fmt:formatNumber value="${order.subtotal}" type="currency"
                                           currencySymbol="${order.items[0].product.currency.symbol}"/></p>
            <p>Delivery cost: <fmt:formatNumber value="${order.deliveryCost}" type="currency"
                                                currencySymbol="${order.items[0].product.currency.symbol}"/></p>
            <h3>Total cost: <fmt:formatNumber value="${order.totalCost}" type="currency"
                                              currencySymbol="${order.items[0].product.currency.symbol}"/></h3>
        </c:if>
        <br>

        <h2>Order details</h2>
        <table>
            <tags:orderOverviewRow name="firstName" label="First name" order="${order}"/>
            <tags:orderOverviewRow name="lastName" label="Last name" order="${order}"/>
            <tags:orderOverviewRow name="phone" label="Phone" order="${order}"/>
            <tags:orderOverviewRow name="deliveryDate" label="Delivery date" order="${order}"/>
            <tags:orderOverviewRow name="deliveryAddress" label="Delivery address" order="${order}"/>
            <tags:orderOverviewRow name="paymentMethod" label="Payment method" order="${order}"/>
        </table>
        <br>
    </c:if>

    <c:if test="${empty order.items}">
        <p> Cart is empty</p>
    </c:if>

    <br>
    <br>
    <tags:home/>
</tags:master>