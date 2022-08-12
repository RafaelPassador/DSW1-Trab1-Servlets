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
        <h1> Bem vindo ${clientLog.getNome()}! </h1>
        <h2> Propostas já cadastradas: </h2>
        <table class="table">
            <thead>
                <th>Modelo</th>
                <th>Placa</th>
                <th>Condições</th>
                <th>Estado</th>
                <th>Valor Original</th>
                <th>Valor proposto</th>
                <th>Data da Proposta</th>
            </thead>
            <c:forEach items="${clientLog.getPropostas()}" var="proposta">
                <tr>
                    <td>${proposta.getModelo()}</td>
                    <td>${proposta.getPlaca()}</td>
                    <td>${proposta.getCondicoes()}</td>
                    <td>${proposta.getEstado()}</td>
                    <td>R$ ${proposta.getValor_original()}</td>
                    <td>R$ ${proposta.getValor()}</td>
                    <td>${proposta.getData_proposta()}</td>
                </tr>
            </c:forEach>
        </table>
        <p> </p>
        <form action="cliente">
                <table class="table">
                    <tr>
                        <th> Envie uma nova proposta: </th>
                    </tr>
                    <tr>
                        <th> Valor de Oferta: </th>
                        <td><input type="text" name="valorOfertado"/></td>
                    </tr>
                    <tr>
                        <th> Condições de Pagamento: </th>
                        <td>
                            <input type="text" name="condicoes"/>
                        </td>
                    </tr>
                    <tr>
                        <th> Placa: </th>
                        <td>
                            <c:choose>
                                <c:when test="${proposta != null}">
                                    <input type="text" name="placa" value="${proposta}"/>
                                </c:when>
                                <c:otherwise>
                                    <input type="text" name="placa" value="${proposta}"/>
                                </c:otherwise>
                            </c:choose>
                        </td>
                    </tr>
                    <tr>                
                        <td> 
                            <input type="submit" name="botaoEnviar" value="Enviar Proposta"/>
                        </td>
                    </tr>
                </table>
                <input type="submit" name="botaoVisualizar" value="Visualizar as propostas"/> <br>
                <input type="submit" name="rewind" value="Sair"/> <br>
        </form>
    </body>
</html>