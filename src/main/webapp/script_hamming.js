var errorsIntroduced = false;
var raportFirst;

function generateReportforReceiver() {
    // Pobierz elementy HTML
    var hammingTables = document.querySelectorAll("table#hammingTable");
    var tableValuesElement = document.getElementById("tableValues");

    // Tworzenie zawartości raportu
    var reportContent = "";

    hammingTables.forEach(function (table, index) {
        var tableRows = table.rows;

        reportContent += raportFirst;
        reportContent += "Dla ID " + (index + 1) + ":\n";
        for (var i = 0; i < tableRows.length; i++) {
            var cells = tableRows[i].cells;

            for (var j = 0; j < cells.length; j++) {
                var cell = cells[j];
                var cellValue = cell.innerText;

                if (cell.style.backgroundColor === 'red') {
                    cellValue += '*';
                }

                reportContent += cellValue + " | ";
            }

            reportContent += "\n";
            if (i < tableRows.length - 1) {
                reportContent += "----------------------\n";
            }
        }

        reportContent += "\n";
    });

    reportContent += "Wartość tableValues: " + tableValuesElement.innerHTML + "\n";

    // Utworzenie pliku raport.txt do pobrania
    var blob = new Blob([reportContent], { type: "text/plain" });
    var link = document.createElement("a");
    link.href = URL.createObjectURL(blob);
    link.download = "raportReceiver.txt";
    link.click();
}
function generateReport() {
    // Pobierz elementy HTML
    var inputWordElement = document.getElementById("inputWord");
    var hammingTableElement = document.getElementById("hammingTable");
    var tableValuesElement = document.getElementById("tableValues");

    // Pobierz dane
    var inputWord = inputWordElement.value;
    var hammingTable = getHammingTableData(hammingTableElement);
    var tableValues = tableValuesElement.innerHTML;

    // Tworzenie zawartości raportu
    var reportContent = "Wprowadzony tekst: " + inputWord + "\n\n";
    reportContent += "Tablica hammingTable:\n";
    reportContent += generateHammingTableString(hammingTable) + "\n\n";
    reportContent += "Wartość po błędach: " + tableValues + "\n";
    // Utworzenie pliku raport.txt do pobrania
    var blob = new Blob([reportContent], { type: "text/plain" });
    var link = document.createElement("a");
    link.href = URL.createObjectURL(blob);
    link.download = "raport.txt";
    link.click();
}

function getHammingTableData(table) {
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

function generateHammingTableString(tableData) {
    var tableString = "";

    for (var i = 0; i < tableData.length; i++) {
        tableString += tableData[i].join(" | ") + "\n";

        if (i < tableData.length - 1) {
            tableString += "----------------------\n";
        }
    }

    return tableString;
}

function sendHamming() {
    // Pobranie referencji do tabelki
    var table = document.getElementById("hammingTable");

    errorsIntroduced = false;

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
    fetch("http://localhost:8080/Projekt_SOB_war/hamming", request)
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

function toggleCellColor(cell) {
    if (cell.style.backgroundColor === "red") {
        cell.style.backgroundColor = cell.getAttribute("data-original-color");
    } else {
        cell.setAttribute("data-original-color", cell.style.backgroundColor);
        cell.style.backgroundColor = "red";
    }
}

function sendTableValues() {
    var table = document.getElementById("hammingTable");
    var valuesAscii = [];
    var values = [];
    for (var i = 1; i < table.rows.length; i++) {
        var rowValues = [];
        for (var j = 2; j < table.rows[i].cells.length; j++) {
            rowValues.push(table.rows[i].cells[j].innerHTML);
        }
        values.push(rowValues);
        var asciiChar = convertBinaryToAscii(rowValues);
        valuesAscii.push(asciiChar);
    }
    var result = valuesAscii.join(""); // Konwersja tablicy na string
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
        document.getElementById('inputWord').value = contents;
    };

    reader.readAsText(file);
}
function introduceErrors() {
    if (errorsIntroduced) {
        return; // Jeśli błędy zostały już wprowadzone, zakończ funkcję
    }

    var table = document.getElementById("hammingTable");

    // Przechodzenie przez wiersze tabelki
    for (var i = 1; i < table.rows.length; i++) {
        var row = table.rows[i];
        var rowData = [];

        // Przechodzenie przez komórki w wierszu
        for (var j = 2; j < row.cells.length - 1; j++) {
            var cell = row.cells[j];
            var cellContent = cell.innerHTML;

            // Wprowadzanie błędu: zamiana 0 na 1 lub 1 na 0
            if (Math.random() < 0.1) {
                // cell.innerHTML = (cellContent === "0") ? "1" : "0";
                toggleCellContent(cell);
                toggleCellColor(cell);
            }
        }
    }

    // Odświeżenie wartości w tabeli
    sendTableValues();

    errorsIntroduced = true; // Ustawienie flagi na true po wprowadzeniu błędów
}

