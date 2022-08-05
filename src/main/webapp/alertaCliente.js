function alerta()
{
    alert("Não é possivel criar uma nova proposta pois voce ja possui uma proposta em aberto");
}

var button = document.getElementById("EnviarPropostaButton")

if (document.getElementById("recado").value == " ")
{
    button.addEventListener("click", alerta);
}