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
            <c:if test="${mensagens.existeErros}">
            <div id="erro">
                <ul>
                    <c:forEach var="erro" items="${mensagens.erros}">
                        <li> ${erro} </li>
                        </c:forEach>
                </ul>
            </div>
        </c:if>
                <input type="submit" name="regCar" value="Cadastrar Veiculo"/> <br>
                <input type="submit" name="listCars" value="Listar Veiculos"/> <br>
                <input type="submit" name="listOffers" value="Listar Propostas"/> <br>

        </form>
        <c:if test="${showOffers == true}">
            <div id="Offers">
                <form action="loja">
                    <table>
                        <thead>
                            <th>Id</th>
                            <th>Valor</th>
                            <th>Valor original</th>
                            <th>Placa do carro</th>
                            <th>Modelo do carro</th>
                            <th>Condicoes</th>
                            <th>Data</th>
                        </thead>
                        <c:forEach var="offer" items="${storeLog.getPropostas()}">
                            <c:set var="op" value ="ABERTO"></c:set>
                            <c:if test="${offer.getEstado() eq op}">
                                <tr>
                                    <td>${offer.getId()}</td>
                                    <td>R$ ${offer.getValor()}</td>
                                    <td>R$ ${offer.getValor_original()}</td>
                                    <td>${offer.getPlaca()}</td>
                                    <td>${offer.getModelo()}</td>
                                    <td>${offer.getCondicoes()}</td>
                                    <td>${offer.getData_proposta()}</td>
                                    <td>${offer.getEstado()}</td>
                                </tr>
                            </c:if>
                            <c:if test="${offer.getEstado() ne op}">
                                <tr>
                                    <td>${offer.getId()}</td>
                                    <td>R$ ${offer.getValor()}</td>
                                    <td>R$ ${offer.getValor_original()}</td>
                                    <td>${offer.getPlaca()}</td>
                                    <td>${offer.getModelo()}</td>
                                    <td>${offer.getCondicoes()}</td>
                                    <td>${offer.getData_proposta()}</td>
                                    <td>${offer.getEstado()}</td>
                                </tr>
                            </c:if>
                        </c:forEach>
                    </table>
                    <table>
                        <tr>
                            <th>Placa do carro*: </th>
                            <td><input type="text" name="offerId"/></td>
                        </tr>
                        <tr>
                            <th>Valor: </th>
                            <td><input type="number" step="any" name="counterOfferValue"/></td>
                        </tr>
                        <tr>
                            <th>Condições: </th>
                            <td><input type="password" name="counterOfferCondition" /></td>
                        </tr>
                        <tr>
                            <th>
                                <input type="submit" name="acceptOffer" value="ACEITAR"/>
                            </th>
                            <th>
                                <input type="submit" name="declineOffer" value="RECUSAR"/>
                            </th>
                            <th>
                                <input type="submit" name="closeOffers" value="FECHAR"/>
                            </th>
                        </tr>
                    </table>
                </form>
            </div>
        </c:if>
        <c:if test="${regCar == true}">
            <div id="NewCar">
                <form action="loja">
                    <table>
                        <tr>
                            <th>CNPJ: </th>
                            <td><input type="text" name="CNPJ"
                                    /></td>
                        </tr>
                        <tr>
                            <th>Placa: </th>
                            <td><input type="text" name="Placa" /></td>
                        </tr>
                        <tr>
                            <th>Modelo: </th>
                            <td><input type="text" name="Modelo" /></td>
                        </tr>
                        <tr>
                            <th>Chassi: </th>
                            <td><input type="text" name="Chassi" /></td>
                        </tr>
                        <tr>
                            <th>Ano: </th>
                            <td><input type="text" name="Ano" /></td>
                        </tr>
                        <tr>
                            <th>Quilometragem: </th>
                            <td><input type="text" name="Quilometragem" /></td>
                        </tr>
                        <tr>
                            <th>Descrição: </th>
                            <td><input type="text" name="Descricao" /></td>
                        </tr>
                        <tr>
                            <th>Valor: </th>
                            <td><input type="text" name="Valor" /></td>
                        </tr>
                        <tr>
                        <td colspan="2"> 
                            <input type="submit" name="bCLOSE" value="Fechar"/>
                        </td>
                        </tr>
                            <tr>
                                <td colspan="2"> 
                                    <input type="submit" name="bOK" value="Salvar"/>
                                </td>
                            </tr>
                    </table>
                </form>
            </div>
        </c:if>
    </body>
</html>
