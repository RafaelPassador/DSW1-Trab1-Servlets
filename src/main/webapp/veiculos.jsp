<%@ page contentType="text/html" pageEncoding="UTF-8"%>
<%@ page isELIgnored="false"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Veículos</title>
        <link href="${pageContext.request.contextPath}/layout.css" rel="stylesheet" type="text/css"/>
    </head>
    <body>
        <h1>Listagem dos Carros:</h1>
        <table>
            <tr>
                <th>Ano</th>
                <th>Quilometragem</th>
                <th>Placa</th>
                <th>Chassi</th>
                <th>Descricao</th>
                <th>Valor</th>
            </tr>
            <c:forEach var="car" items="${cars}">
                <tr>
                    <td>${car.getAno()}</td>
                    <td>${car.getQuilometragem()}</td>
                    <td>${car.getPlaca()}</td>
                    <td>${car.getChassi()}</td>
                    <td>${car.getDescricao()}</td>
                    <td>R$ ${car.getValor()}</td>
                </tr>
            </c:forEach>
        </table>
    </body>
</html>
