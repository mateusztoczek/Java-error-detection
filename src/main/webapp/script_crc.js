var errorsIntroduced = false;
var raportFirst;

function generateReportforReceiver(expected,newData) {
        // Pobierz elementy HTML

        var crcTableElement = document.getElementById("crcTable");


        // Pobierz dane
        var inputWord = expected;
        var crcTable = getCRCTableData(crcTableElement);
        var tableValues = newData;

        // Tworzenie zawartości raportu
        var reportContent = "Oczekiwane dane: " + inputWord + "\n\n";
        reportContent += generateCRCTableString(crcTable) + "\n\n";
        reportContent += "Otrzymane: " + tableValues + "\n";
        // Utworzenie pliku raport.txt do pobrania
        var blob = new Blob([reportContent], { type: "text/plain" });
        var link = document.createElement("a");
        link.href = URL.createObjectURL(blob);
        link.download = "raport.txt";
        link.click();
}
function generateReport() {
    // Pobierz elementy HTML
    var inputWordElement = document.getElementById("inputWord");
    var crcTableElement = document.getElementById("crcTable");
    var tableValuesElement = document.getElementById("tableValues");

    // Pobierz dane
    var inputWord = inputWordElement.value;
    var crcTable = getCRCTableData(crcTableElement);
    var tableValues = tableValuesElement.innerHTML;

    // Tworzenie zawartości raportu
    var reportContent = "Wprowadzony tekst: " + inputWord + "\n\n";
    reportContent += generateCRCTableString(crcTable) + "\n\n";
    reportContent += "Wartość po błędach: " + tableValues + "\n";
    // Utworzenie pliku raport.txt do pobrania
    var blob = new Blob([reportContent], { type: "text/plain" });
    var link = document.createElement("a");
    link.href = URL.createObjectURL(blob);
    link.download = "raport.txt";
    link.click();
}

function getCRCTableData(table) {
    var data = [];
    var rows = table.rows;

    for (var i = 0; i < rows.length; i++) {
        var rowData = [];
        var cells = rows[i].cells;

        for (var j = 0; j < cells.length; j++) {
            var cell = cells[j];
            var cellValue = cell.innerText;

            if (cell.style.backgroundColor === 'red') {
                cellValue += '*';
            }

            rowData.push(cellValue);
        }

        data.push(rowData);
    }

    return data;
}

function generateCRCTableString(tableData) {
    var tableString = "";

    for (var i = 0; i < tableData.length; i++) {
        tableString += tableData[i].join(" | ") + "\n";

        if (i < tableData.length - 1) {
            tableString += "----------------------\n";
        }
    }

    return tableString;
}

function checkCRC(inputWord,inputDiv,crc){
    var table = document.getElementById("crcTable");
    var tableText = document.getElementById("tableValues");
    //var inputWord = document.getElementById("inputWord").innerHTML;
    //var inputDiv = document.getElementById("inputDiv").innerHTML;
    //var Div = document.getElementById("inputDiv").innerHTML;

    var newData = [];

        var values = [];
        for (var j = 1; j < table.rows[1].cells.length; j++) {
            values.push(table.rows[1].cells[j].innerHTML);
        }
        newData=values;

    var newCRC = [];

    var valuesCRC = [];
    for (var j = 1; j < table.rows[2].cells.length; j++) {
        valuesCRC.push(table.rows[2].cells[j].innerHTML);
    }
    newCRC=valuesCRC;


    var inputNew = newData.join();
    var finalCRC = newCRC.join();
    var requestBody = {
        newData: inputNew,
        inputWord: inputWord,
        inputDiv: inputDiv,
        crc: finalCRC,
    };

    var requestBodyString = JSON.stringify(requestBody);

    var request = {
        method: "POST",
        headers: {
            "Content-Type": "application/json"
        },
        body: requestBodyString
        //body: JSON.stringify(newData)
    };

    // Wywołanie żądania do serwera
    fetch("http://localhost:8080/Projekt_SOB_war/crc", request)
        .then(function(response) {
            var redirected = response.redirected;
            var url = response.url;

            if (redirected) {
                // Przekierowanie - przekieruj na nowy adres URL
                window.location.href = url;
            }
            if (response.ok) {
                return response.text();
            } else {
                throw new Error("Wystąpił błąd podczas wysyłania danych na serwer.");
            }
        })
        .catch(function(error) {
            // Obsługa błędów
            console.error(error);
        });


}
function sendCRC() {
    // Pobranie referencji do tabelki
    var table = document.getElementById("crcTable");


    // Utworzenie pustej tablicy dla danych
    var data = [];

    // Przechodzenie przez wiersze tabelki
    for (var i = 1; i < table.rows.length; i++) {
        var rowData = [];

        // Przechodzenie przez komórki w wierszu
        for (var j = 2; j < table.rows[i].cells.length; j++) {
            rowData.push(table.rows[i].cells[j].innerHTML);
        }

        // Dodanie wiersza danych do tablicy
        data.push(rowData);
    }

    // Utworzenie obiektu żądania
    var request = {
        method: "POST",
        headers: {
            "Content-Type": "application/json"
        },
        body: JSON.stringify(data)
    };

    // Wywołanie żądania do serwera
    fetch("http://localhost:8080/Projekt_SOB_war/crc", request)
        .then(function(response) {
            var redirected = response.redirected;
            var url = response.url;

            if (redirected) {
                // Przekierowanie - przekieruj na nowy adres URL
                window.location.href = url;
            }
            if (response.ok) {
                return response.text();
            } else {
                throw new Error("Wystąpił błąd podczas wysyłania danych na serwer.");
            }
        })
        .catch(function(error) {
            // Obsługa błędów
            console.error(error);
        });
}

function toggleCellContent(cell) {
    if (cell.innerHTML === "0") {
        cell.innerHTML = "1";
    } else {
        cell.innerHTML = "0";
    }
}
function changeCellColor(cell,color) {
    cell.style.backgroundColor=color;
}
function toggleCellColor(cell) {
    if (cell.style.backgroundColor === "red") {
        cell.style.backgroundColor = cell.getAttribute("data-original-color");
    } else {
        cell.setAttribute("data-original-color", cell.style.backgroundColor);
        cell.style.backgroundColor = "red";
    }
}

function sendTableValues() {
    var table = document.getElementById("crcTable");
    var values = [];
    for (var j = 1; j < table.rows[1].cells.length; j++) {
        values.push(table.rows[1].cells[j].innerHTML);
    }
    var result = values.join(""); // Konwersja tablicy na string
    document.getElementById("tableValues").innerHTML = result;
}

function convertBinaryToAscii(inputArray) {
    const indexesToRemove = [0, 1, 3, 7, inputArray.length - 1];
    // const reversedArray = inputArray.reverse();
    const shortenedArray = inputArray.filter((_, index) => !indexesToRemove.includes(index));

    const binaryString = shortenedArray.join('');
    const decimalValue = parseInt(binaryString, 2);
    const asciiChar = String.fromCharCode(decimalValue);

    return asciiChar;
}

function readFromFile() {
    var fileInput = document.getElementById('fileInput');
    var file = fileInput.files[0];
    var reader = new FileReader();

    reader.onload = function(e) {
        var contents = e.target.result;
        var lines = contents.split('\n');
        document.getElementById('inputWord').value="";
        document.getElementById('inputWord').value = lines[0].trim();
        document.getElementById('inputDiv').value = "";
        document.getElementById('inputDiv').value = lines[1].trim();
    };

    reader.readAsText(file);
}
function introduceErrors() {
    resetErrors()


    var table = document.getElementById("crcTable");

    // Przechodzenie przez wiersze tabelki

        // Przechodzenie przez komórki w wierszu
        for (var j = 1; j < table.rows[1].cells.length ; j++) {
            var cell = table.rows[1].cells[j];

            // Wprowadzanie błędu: zamiana 0 na 1 lub 1 na 0
            if (Math.random() < 0.2) {
                // cell.innerHTML = (cellContent === "0") ? "1" : "0";
                toggleCellContent(cell);
                toggleCellColor(cell);
            }
        }
    // Odświeżenie wartości w tabeli
    sendTableValues();

}

function resetErrors() {
    var table = document.getElementById("crcTable");

    // Przechodzenie przez wiersze tabelki

    // Przechodzenie przez komórki w wierszu
    for (var j = 1; j < table.rows[1].cells.length ; j++) {
        var cell = table.rows[1].cells[j];
        console.log(`zawsze!`);
        if (cell.style.backgroundColor === "red") {
            console.log(`czerwone!`);
            cell.style.backgroundColor = cell.getAttribute("data-original-color");
            toggleCellContent(cell);
        }

    }
    // Odświeżenie wartości w tabeli
    sendTableValues();

}
