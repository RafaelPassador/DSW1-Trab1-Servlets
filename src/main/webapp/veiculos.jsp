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
        <marquee>CLIQUE <a href="Login">AQUI</a> PARA FAZER LOGIN</marquee>
        <h1>Listagem dos Carros:</h1>
        <input id="filtro" type="text" onkeyup="filter()" placeholder="filtrar">
        <form action="Login" method="post">
            <table id="veiculos" class="table">
                <tr>
                    <th>Modelo</th>
                    <th>Ano</th>
                    <th>Quilometragem</th>
                    <th>Placa</th>
                    <th>Chassi</th>
                    <th>Descricao</th>
                    <th>Valor</th>
                    <th>Proposta</th>
                </tr>
                <c:forEach var="car" items="${cars}">
                    <tr>
                        <td>${car.getModelo()}</td>
                        <td>${car.getAno()}</td>
                        <td>${car.getQuilometragem()}</td>
                        <td>${car.getPlaca()}</td>
                        <td>${car.getChassi()}</td>
                        <td>${car.getDescricao()}</td>
                        <td>R$ ${car.getValor()}</td>
                        <td><input type="submit" name="proposta" value="${car.getPlaca()}"></td>
                        <c:forEach var="image" items="${car.getImagens()}">
                            <td><img src="${image}" alt="" style="width: 25%;"></td>
                        </c:forEach>
                    </tr>
                </c:forEach>
            </table>
        </form>
        <script>
            function filter() {
                let input = document.getElementById("filtro");
                let filtro = input.value.toUpperCase();
                let table = document.getElementById("veiculos");
                let tr = table.getElementsByTagName("tr");
                for (i = 0; i < tr.length; i++) {
                    let td = tr[i].getElementsByTagName("td")[0];
                    if (td) {
                        let modelo = td.textContent || td.innerText;
                        if (modelo.toUpperCase().indexOf(filtro) > -1) {
                            tr[i].style.display = "";
                        } else {
                            tr[i].style.display = "none";
                        }
                    }
                }
            }
        </script>
    </body>
</html>
