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
        <h1>Bem vindo Admin!</h1>
        <c:if test="${mensagens.existeErros}">
            <div id="erro">
                <ul>
                    <c:forEach var="erro" items="${mensagens.erros}">
                        <li> ${erro} </li>
                        </c:forEach>
                </ul>
            </div>
        </c:if>
        <h2>Selecione uma opção:</h2>
        <form action="admin">
                <input type="submit" name="regLoja" value="Cadastrar Loja"/> <br>
                <input type="submit" name="editLoja" value="Editar Loja"/> <br>
                <input type="submit" name="regClient" value="Cadastrar Cliente"/> <br>
                <input type="submit" name="editClient" value="Editar Cliente"/> <br>
                <input type="submit" name="rewind" value="Sair"/> <br>
        </form>
        <table>
            <form action="admin">
                <c:if test="${rudLoja}">
                    <tr>
                        <th>Cnpj da Loja</th>
                        <td><input type="text" name="idLoja"/></td>
                    </tr>
                    <tr>
                        <th>
                            <input type="submit" name="sendIdLoja" value="EDITAR"/>
                        </th>
                        <th>
                            <input type="submit" name="closeRud" value="FECHAR"/>
                        </th>
                    </tr>
                </c:if>
                <c:if test="${rudClient}">
                    <tr>
                        <th>Cpf do Cliente</th>
                        <td><input type="text" name="idClient"/></td>
                    </tr>
                    <tr>
                        <th>
                            <input type="submit" name="sendIdClient" value="EDITAR"/>
                        </th>
                        <th>
                            <input type="submit" name="closeRud" value="FECHAR"/>
                        </th>
                    </tr>
                </c:if>
            </form>
        </table>


        <c:if test="${editingStore}">
            <form action="admin">

                <table>
                    <tr>
                        <th>Email: </th>
                        <td><input type="text" name="email" value="${editableStore.getEmail()}"/></td>
                    </tr>
                    <tr>
                        <th>Senha: </th>
                        <td><input type="password" name="pass"></td>
                    </tr>
                    <tr>
                        <th>CNPJ:</th>
                        <td><input type="text" name="cnpj" value="${editableStore.getCnpj()}"/></td>
                    </tr>
                    <tr>
                        <th>Nome:</th>
                        <td><input type="text" name="nome" value="${editableStore.getNome()}"/></td>
                    </tr>
                    <tr>
                        <th>Descrição:</th>
                        <td><input type="text" name="descricao" value="${editableStore.getDescricao()}"/></td>
                    </tr>
                    <tr>
                        <th>
                            <input type="submit" name="confirmStoreEdit" value="EDITAR"/>
                        </th>
                        <th>
                            <input type="submit" name="deleteStore" value="DELETAR"/>
                        </th>
                        <th>
                            <input type="submit" name="closeStore" value="FECHAR"/>
                        </th>
                    </tr>
                </table>
            </form>
        </c:if>

        <c:if test="${editingClient}">
            <form action="admin">

                <table>
                    <tr>
                        <th>Email: </th>
                        <td><input type="text" name="email" value="${editableClient.getEmail()}"/></td>
                    </tr>
                    <tr>
                        <th>Senha: </th>
                        <td><input type="password" name="pass"></td>
                    </tr>
                    <tr>
                        <th>CPF:</th>
                        <td><input type="text" name="cpf" value="${editableClient.getCpf()}"/></td>
                    </tr>
                    <tr>
                        <th>Nome:</th>
                        <td><input type="text" name="nome" value="${editableClient.getNome()}"/></td>
                    </tr>
                    <tr>
                        <th>Telefone:</th>
                        <td><input type="text" name="telefone" value="${editableClient.getTelefone()}"/></td>
                    </tr>
                    <tr>
                        <th>Sexo:</th>
                        <td><input type="text" name="sexo" value="${editableClient.getSexo()}"/></td>
                    </tr>
                    <tr>
                        <th>Nascimento:</th>
                        <td><input type="date" name="nascimento" value="${editableClient.getNascimento()}"/></td>
                    </tr>
                    <tr>
                        <th>
                            <input type="submit" name="confirmClientEdit" value="EDITAR"/>
                        </th>
                        <th>
                            <input type="submit" name="deleteClient" value="DELETAR"/>
                        </th>
                        <th>
                            <input type="submit" name="closeClient" value="FECHAR"/>
                        </th>
                    </tr>
                </table>
            </form>
        </c:if>
        
        <c:if test="${showStoreForm}">
            <form action="admin">

                <table>
                    <tr>
                        <th>Email: </th>
                        <td><input type="text" name="email" /></td>
                    </tr>
                    <tr>
                        <th>Senha: </th>
                        <td><input type="password" name="pass"></td>
                    </tr>
                    <tr>
                        <th>CNPJ:</th>
                        <td><input type="text" name="cnpj" /></td>
                    </tr>
                    <tr>
                        <th>Nome:</th>
                        <td><input type="text" name="nome" /></td>
                    </tr>
                    <tr>
                        <th>Descrição:</th>
                        <td><input type="text" name="descricao" /></td>
                    </tr>
                    <tr>
                        <th>
                            <input type="submit" name="createStore" value="CRIAR"/>
                        </th>
                        <th>
                            <input type="submit" name="closeStore" value="FECHAR"/>
                        </th>
                    </tr>
                </table>
            </form>
        </c:if>

        <c:if test="${showClientForm}">
            <form action="admin">

                <table>
                    <tr>
                        <th>Email: </th>
                        <td><input type="text" name="email" /></td>
                    </tr>
                    <tr>
                        <th>Senha: </th>
                        <td><input type="password" name="pass"></td>
                    </tr>
                    <tr>
                        <th>CPF:</th>
                        <td><input type="text" name="cpf" /></td>
                    </tr>
                    <tr>
                        <th>Nome:</th>
                        <td><input type="text" name="nome" /></td>
                    </tr>
                    <tr>
                        <th>Telefone:</th>
                        <td><input type="text" name="telefone" /></td>
                    </tr>
                    <tr>
                        <th>Sexo:</th>
                        <td><input type="text" name="sexo" /></td>
                    </tr>
                    <tr>
                        <th>Nascimento:</th>
                        <td><input type="date" name="nascimento" /></td>
                    </tr>
                    <tr>
                        <th>
                            <input type="submit" name="createClient" value="CRIAR"/>
                        </th>
                        <th>
                            <input type="submit" name="closeClient" value="FECHAR"/>
                        </th>
                    </tr>
                </table>
            </form>
        </c:if>


    </body>
</html>