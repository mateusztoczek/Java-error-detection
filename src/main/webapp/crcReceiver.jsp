<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="pl.wieik.ti.ti2023lab5.model.*" %>
<jsp:useBean id="newCrcReponse" class="pl.wieik.ti.ti2023lab5.model.CrcResp" scope="session" />

<!DOCTYPE html>
<html>
<head>
    <title>Porównanie CRC</title>
    <link rel="stylesheet" type="text/css" href="style_crc.css">
    <script type="text/javascript" src="script_crc.js"></script>
</head>
<body>
<h1>Wykrywanie błędów CRC</h1>

<% CrcResp resp= newCrcReponse;%>
<%
String expected= resp.getExpected();
String newData=resp.getNewData();
String OldCRC = resp.getCrc();
String div = resp.getDiv();
boolean isNoErrorMess = CrcCodeApi.receiveData(CrcCodeApi.convertStrToIntArr(newData),CrcCodeApi.convertStrToIntArr(div),CrcCodeApi.convertStrToIntArr(OldCRC));

%>

<% if (!isNoErrorMess) {%>


<h2>Błąd! Brak zgodności!</h2>
<p>Orginalne dane: <%=expected  %></p>
<p>Otrzymane dane: <%=newData  %></p>


<br>
<table id="crcTable">
    <tr>
        <td>Porównanie</td>
    </tr>
    <tr>
        <td>CRC: </td>
        <% for (int j = 0; j < OldCRC.length(); j++) { %>
        <td><%=OldCRC.charAt(j) %></td>
        <% } %>
    </tr>

    <tr>
        <td>Prawidłowe: </td>
        <% for (int j = 0; j < expected.length(); j++) { %>
        <% if (newData.charAt(j) != expected.charAt(j)) {
            if (expected.charAt(j)=='n') { %>
        <td style="background-color: green;"><%= "-" %></td>
        <%} else {%>
        <td style="background-color: #12af12;"><%= expected.charAt(j) %></td>
        <% }} else { %>
        <td><%= expected.charAt(j) %></td>
        <% } %>
        <% } %>

    </tr>


    <tr>
        <td>Otrzymane: </td>
        <% for (int j = 0; j < newData.length(); j++) { %>
        <% if (newData.charAt(j) != expected.charAt(j)) {
            if (newData.charAt(j)=='n') { %>
        <td style="background-color: red;"><%= "-" %></td>
        <%} else {%>
        <td style="background-color: red;"><%= newData.charAt(j) %></td>
        <% }} else { %>
        <td><%= newData.charAt(j) %></td>
        <% } %>
        <% } %>
    </tr>
</table>




<%} else {
    %>
<h2>Dane poprawne.</h2>
<% }%>

<button style="margin: 3px" onclick="window.location.href = 'http://localhost:8080/Projekt_SOB_war/main';">Strona główna</button>
<button style="margin: 3px" onclick="window.location.href = 'http://localhost:8080/Projekt_SOB_war/crc';">Ponów</button>
<button onclick="generateReportforReceiver(<%=expected%>,<%=newData%>)">Raport końcowy</button>

</body>
</html>
