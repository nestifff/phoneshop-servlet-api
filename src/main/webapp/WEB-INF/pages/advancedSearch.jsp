<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>

<jsp:useBean id="products" type="java.util.ArrayList" scope="request"/>
<jsp:useBean id="searchOptions" type="java.util.List" scope="request"/>
<jsp:useBean id="errors" type="java.util.Map" scope="request"/>
<tags:master pageTitle="Advanced search">

    <h1>
        Advanced search
    </h1>
    <br>

    <c:if test="${not empty errors}">
        <p class="redText">
            There were some errors while searching
        </p>
        <br>
    </c:if>

    <form>
        <p>Description: <input name="query" value="${param.query}">
            <select name="searchOption">
                <c:forEach var="searchOption" items="${searchOptions}">
                    <option ${empty errors['searchOption'] && param.searchOption == searchOption ? 'selected="selected"' : ''}>${searchOption}</option>
                </c:forEach>
            </select>
        </p>
        <c:if test="${not empty errors['searchOption']}">
            <p class="redText">${errors['searchOption']}: ${param.searchOption}</p>
        </c:if>
        <p>Min price: <input type="number" name="minPrice" value="${param.minPrice}"></p>
        <c:if test="${not empty errors['minPrice']}">
            <p class="redText">${errors['minPrice']}: ${param.minPrice}</p>
        </c:if>
        <p>Max price: <input type="number" name="maxPrice" value="${param.maxPrice}"></p>
        <c:if test="${not empty errors['maxPrice']}">
            <p class="redText">${errors['maxPrice']}: ${param.maxPrice}</p>
        </c:if>
        <button>Search</button>
    </form>

    <c:if test="${not empty products}">
        <table>
            <tr>
                <td>Image</td>
                <td>Description
                </td>
                <td class="price">
                    Price
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
                            <fmt:formatNumber value="${product.price}" type="currency"
                                              currencySymbol="${product.currency.symbol}"/>
                        </a>
                    </td>
                    <td>
                        <a href="${pageContext.servletContext.contextPath}/products/${product.id}">Go to details</a>
                    </td>
                </tr>
            </c:forEach>
        </table>
        <br>
    </c:if>
    <br>
    <br>
    <br>
    <tags:home/>
</tags:master>