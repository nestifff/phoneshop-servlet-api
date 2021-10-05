<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>

<jsp:useBean id="order" type="com.es.phoneshop.model.order.Order" scope="request"/>
<jsp:useBean id="paymentMethods" type="java.util.List" scope="request"/>
<tags:master pageTitle="Checkout">
    <h1> Checkout </h1>

    <c:if test="${not empty param.message}">
        <p class="greenText">
                ${param.message}
        </p>
    </c:if>
    <c:if test="${not empty errors}">
        <p class="redText">
            There were some errors while placing order
        </p>
    </c:if>

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
        <form action="${pageContext.servletContext.contextPath}/checkout" method="post">
            <table>
                <tags:orderPlaceRow name="firstName" label="First name" order="${order}" errors="${errors}"/>
                <tags:orderPlaceRow name="lastName" label="Last name" order="${order}" errors="${errors}"/>
                <tags:orderPlaceRow name="phone" label="Phone, please, use only numbers and (optional) \"+\" at the beginning" order="${order}" errors="${errors}"/>
                <tags:orderPlaceRow name="deliveryDate" label="Delivery date in format yyyy-mm-dd" order="${order}" errors="${errors}"/>
                <tags:orderPlaceRow name="deliveryAddress" label="Address" order="${order}" errors="${errors}"/>

                <tr>
                    <td>Payment method<span class="redText">*</span></td>
                    <td>
                        <select name="paymentMethod">
                            <c:forEach var="paymentMethod" items="${paymentMethods}">
                                <option ${order.paymentMethod == paymentMethod ? 'selected="selected"' : ''}>${paymentMethod}</option>
                            </c:forEach>
                        </select>
                    </td>
                </tr>
            </table>
            <br>
            <button>Place order</button>
        </form>
    </c:if>

    <c:if test="${empty order.items}">
        <p> Cart is empty</p>
    </c:if>

    <br>
    <br>
    <tags:home/>
</tags:master>