<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="pl.wieik.ti.ti2023lab5.model.HammingCodeApi" %>
<!DOCTYPE html>
<html>
<head>
    <title>Hamming</title>
    <link rel="stylesheet" type="text/css" href="style_hamming.css">
    <link rel="" type="text/css" href="style_hamming.css">
    <script type="text/javascript" src="script_hamming.js"></script>
</head>
<body>
<h1>Kodowanie Hamminga</h1>

<form method="post" action="hamming.jsp">
    <label for="inputWord">Wprowadź słowo:</label>
    <input type="text" id="inputWord" name="inputWord" value="<%= (request.getParameter("inputWord") != null) ? request.getParameter("inputWord") : "" %>" required>
    <input type="submit" value="Akceptuj">
</form>

<p>Wczytaj tekst z pliku:</p>
<input type="file" id="fileInput">
<button onclick="readFromFile()">Wczytaj</button>

<%-- Sprawdzenie czy słowo zostało przesłane --%>
<% if (request.getParameter("inputWord") != null) { %>
<%
    String inputWord = request.getParameter("inputWord");
    int[][] hammingCodes = new int[inputWord.length()][];
    for (int i = 0; i < inputWord.length(); i ++){
        hammingCodes[i] = HammingCodeApi.getHammingCode(HammingCodeApi.charToBinaryIntArray(inputWord.charAt(i)));
    }
%>
<h2>Wygenerowane kody Hamminga:</h2>
<button style="margin: 3px" onclick="introduceErrors()">Wprowadź błędy</button>
<table id="hammingTable">
    <tr>
        <th>ID</th>
        <th>Słowo</th>
        <%-- Wygenerowanie nagłówków dla 12 kolumn --%>
        <% for (int i = 1; i <= 11; i++) { %>
        <%-- Sprawdzenie, czy kolumna ma mieć jasnoniebieskie tło --%>
        <% if (i == 3 || i == 4 || i == 6 || i == 10) { %>
        <th class="blue-bg"><%= i %></th>
        <% } else { %>
        <th><%= i %></th>
        <% } %>
        <% } %>
    </tr>
    <% for (int i = 0; i < inputWord.length(); i++) { %>
    <tr>
        <td><%= i+1 %></td>
        <td><%= inputWord.charAt(i) %></td>
        <%-- Wygenerowanie kodów Hamminga dla każdej litery --%>
        <% for (int j = 0; j < hammingCodes[i].length; j++) { %>
        <%-- Sprawdzenie, czy komórka ma mieć jasnoniebieskie tło --%>
        <% if (j == 3 || j == 4 || j == 6 || j == 10) { %>
        <td class="blue-bg" onclick="toggleCellContent(this);toggleCellColor(this);sendTableValues()"><%= hammingCodes[i][j] %></td>
        <% } else if (j == hammingCodes[i].length - 1) { %>
        <td class="blue-bg"><%= hammingCodes[i][j] %></td>
        <% } else { %>
        <td onclick="toggleCellContent(this);toggleCellColor(this);sendTableValues()"><%= hammingCodes[i][j] %></td>
        <% } %>
        <% } %>
    </tr>
    <% } %>
</table>
<br>
<p id="tableValues"><%= inputWord%></p>
<button style="margin: 3px" onclick="sendHamming()">Wyślij</button>
<% } %>
<button style="margin: 3px" onclick="window.location.href = 'http://localhost:8080/Projekt_SOB_war/main';">Powrót do strony głównej</button>
<button style="margin: 3px" onclick="generateReport()">Generuj Raport</button>

</body>
</html>
