<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="pl.wieik.ti.ti2023lab5.model.CrcCodeApi" %>
<!DOCTYPE html>
<html>
<head>
  <title>CRC</title>
  <link rel="stylesheet" type="text/css" href="style_crc.css">
  <link rel="" type="text/css" href="style_crc.css">
  <script type="text/javascript" src="script_crc.js"></script>
</head>
<body>
<h1>Kodowanie CRC</h1>

<p>Wczytaj tekst z pliku...</p>
<input type="file" id="fileInput">
<button onclick="readFromFile()">Wczytaj</button>
<br>

<form method="post" action="crc.jsp">
  <br>
  <label for="inputWord">...lub wprowadź dane:</label>
  <br>
  <input type="text" id="inputWord" name="inputWord" value="<%= (request.getParameter("inputWord") != null) ? request.getParameter("inputWord") : "" %>" required>
  <br>
  <input type="text" id="inputDiv" name="inputDiv" value="<%= (request.getParameter("inputDiv") != null) ? request.getParameter("inputDiv") : "" %>" required>
  <br>
  <br>
  <input type="submit" value="Generuj">
</form>



<%-- Sprawdzenie czy słowo zostało przesłane --%>
<% if (request.getParameter("inputWord") != null && request.getParameter("inputDiv") != null) { %>
<%
  String inputWord = request.getParameter("inputWord");
  int dataSize = inputWord.length();
  String inputDiv = request.getParameter("inputDiv");
  int divisorSize = inputDiv.length();


  int[] dataWord = CrcCodeApi.convertStrToIntArr(inputWord);
  int[] dataDiv = CrcCodeApi.convertStrToIntArr(inputDiv);
  int[] rem = CrcCodeApi.getCRC(dataWord,dataDiv);

  int[] solution = CrcCodeApi.divideDataWithDivisor(dataWord,dataDiv);

  String remainder= CrcCodeApi.convertIntArrayToStr(rem);
  String generatedCRC= inputWord+remainder;
  int[] generated = CrcCodeApi.convertStrToIntArr(generatedCRC);


%>

<h2>Wygenerowane CRC</h2>
<p>Rozmiar: <%= dataSize %></p>
<p>Dane wejściowe: <%= inputWord %></p>
<p>Rozmiar dzielnika: <%= divisorSize %></p>
<p>Dzielnik: <%= inputDiv %></p>
<p>Kod nadmiarowy CRC: <%= remainder %></p>
<p>Pełna wiadomość: <%= generatedCRC %></p>

<h2>Weryfikacja danych</h2>



<table id="crcTable">
  <tr>
    <td><%= "Wygenerowane" %></td>
  </tr>
  <tr>

    <td><%= "Dane:" %></td>
    <% for (int j = 0; j < dataWord.length; j++) { %>
      <td onclick="toggleCellContent(this);toggleCellColor(this);sendTableValues()"><%= dataWord[j] %></td>
      <% } %>

  </tr>
  <tr>
    <td><%= "CRC:" %></td>
    <% for (int j = 0; j < rem.length; j++) { %>
    <td class="blue-bg" ><%=rem[j] %></td>
    <% } %>
  </tr>
</table>
<br>
<p id="tableValues"><%= inputWord%></p>

<p>Zmodyfikuj podane wyniki...</p>
<button style="margin: 3px" onclick="introduceErrors()">Wypełnij losowo</button>
<button style="margin: 3px" onclick="resetErrors()">Reset</button>
<button style="margin: 3px" onclick="checkCRC(<%=inputWord%>,<%=inputDiv%>,<%=remainder%>)">Wyślij</button>


<% } %>
<button style="margin: 3px" onclick="window.location.href = 'http://localhost:8080/Projekt_SOB_war/main';">Strona główna</button>
<button style="margin: 3px" onclick="generateReport()">Raport</button>

</body>
</html>
