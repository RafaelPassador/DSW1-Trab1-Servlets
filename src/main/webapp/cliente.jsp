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
        <h1>Bem vindo Cliente!</h1>
        <form action="cliente">
                <table>
                    <tr>
                        <th> Envie uma nova proposta: </th>
                    </tr>
                    <tr>
                        <th> Valor Ofertado: </th>
                        <td><input type="text" name="valorOfertado"/></td>
                    </tr>
                    <tr>
                        <th> Condições: </th>
                        <td>
                            <input type="text" name="condicoes"/>
                        </td>
                    </tr>
                    <tr>
                        <th> Placa: </th>
                        <td>
                            <input type="text" name="placa"/>
                        </td>
                    </tr>
                    <tr>
                        <th> Modelo: </th>
                        <td>
                            <input type="text" name="modelo"/>
                        </td>
                    </tr>
                    <tr>                
                        <td> 
                            <input type="submit" name="botaoEnviar" value="Enviar Proposta"/>
                        </td>
                    </tr>
                </table>
                <input type="submit" name="botaoVisualizar" value="Visualizar as propostas"/> <br>
        </form>
    </body>
</html>