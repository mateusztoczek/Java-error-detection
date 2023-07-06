<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <title>SOB</title>
    <link rel="stylesheet" type="text/css" href="style.css">
</head>
<body>
<div class="kontener">
    <header id="naglowek">
        <h1>Wykrywanie błędów</h1>
        <p>Witaj na stronie sprawdzającej poprawność wysyłanych danych.</p>
        <p>Wybierz metodę:</p>
    </header>
    <div id="srodek">
        <a class="wybor" href="hamming.jsp">
            <img src="hamming.jpg" alt="Hamming" />
            <span class="podpis">Hamming</span>
        </a>
        <a class="wybor" href="crc.jsp">
            <img src="crc.jpg" alt="CRC" />
            <span class="podpis">CRC</span>
        </a>
    </div>
</div>
</body>
</html>
