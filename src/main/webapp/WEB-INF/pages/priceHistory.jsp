<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>

<jsp:useBean id="product" type="com.es.phoneshop.model.product.domain.Product" scope="request"/>
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