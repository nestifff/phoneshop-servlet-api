<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ tag trimDirectiveWhitespaces="true" %>
<%@ attribute name="name" required="true" %>
<%@ attribute name="label" required="true" %>
<%@ attribute name="order" required="true" type="com.es.phoneshop.model.order.Order" %>
<%@ attribute name="errors" required="true" type="java.util.Map" %>

<tr>
    <td>${label}<span class="redText">*</span></td>
    <c:set var="error" value="${errors[name]}"/>
    <td><input name="${name}" value="${not empty error ? param[name] : order[name]}"/>
        <c:if test="${not empty error}">
            <p class="redText">${error}</p>
        </c:if>
    </td>
</tr>


