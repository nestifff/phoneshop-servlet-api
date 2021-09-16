<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>

<jsp:useBean id="product" type="com.es.phoneshop.model.product.Product" scope="request"/>
<tags:master pageTitle="Price History">
    <br>
    <h1>${product.description}</h1>
    <table>
        <tr>
            <td>Date</td>

            <td>Price</td>
        </tr>
        <c:forEach var="priceHistoryItem" items="${product.priceHistory}">
            <tr>
                <td>
                        ${priceHistoryItem.date.toString()}
                </td>
                <td>
                    <fmt:formatNumber value="${priceHistoryItem.price}" type="currency"
                                      currencySymbol="${priceHistoryItem.currency.symbol}"/>
                </td>
            </tr>
        </c:forEach>
    </table>
    <br>
    <tags:home/>
</tags:master>