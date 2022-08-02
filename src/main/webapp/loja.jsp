<%@ page contentType="text/html" pageEncoding="UTF-8"%>
<%@ page isELIgnored="false"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Autenticação de Usuário</title>
        <link href="${pageContext.request.contextPath}/layout.css" rel="stylesheet" type="text/css"/>
    </head>
    <body>
        <form action="loja">
            <h1>Bem vindo ${storeLog.getNome()}</h1>
                <input type="submit" name="regCar" value="Cadastrar Veiculo"/> <br>
                <input type="submit" name="listCars" value="Listar Veiculos"/> <br>
                <input type="submit" name="listOffers" value="Listar Propostas"/> <br>

        </form>
        <c:if test="${showOffers == true}">
            <div id="Offers">
                <form action="loja">
                    <table>
                        <thead>
                            <th>Valor</th>
                            <th>Condicoes</th>
                            <th>Data</th>
                        </thead>
                        <c:forEach var="offer" items="${storeLog.getPropostas()}">
                            <c:set var="op" value ="ABERTO"></c:set>
                            <c:if test="${offer.getEstado() eq op}">
                                <tr>
                                    <td>R$ ${offer.getValor()}</td>
                                    <td>${offer.getCondicoes()}</td>
                                    <td>${offer.getData_proposta()}</td>
                                    <td><input type="submit" name="closeOffers" value="ACEITAR"/></td>
                                    <td><input type="submit" name="decline" value="RECUSAR"/></td>
                                </tr>
                            </c:if>
                            <c:if test="${offer.getEstado() ne op}">
                                <tr>
                                    <td>R$ ${offer.getValor()}</td>
                                    <td>${offer.getCondicoes()}</td>
                                    <td>${offer.getData_proposta()}</td>
                                    <td>${offer.getEstado()}</td>
                                </tr>
                            </c:if>
                            </c:forEach>
                    </table>
                        <input type="submit" name="closeOffers" value="FECHAR"/>
                </form>
            </div>
        </c:if>
    </body>
</html>