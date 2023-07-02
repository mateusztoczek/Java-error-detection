package pl.wieik.ti.ti2023lab5.kontroler;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.google.gson.JsonSyntaxException;
import pl.wieik.ti.ti2023lab5.model.HammingCodeApi;
import pl.wieik.ti.ti2023lab5.model.HammingCodeText;
import pl.wieik.ti.ti2023lab5.model.HammingResponse;

@WebServlet(name="hamming", value = "/hamming")
public class hamming extends HttpServlet {
    @Override
    public void init() throws ServletException {
        super.init();
    }
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        res.sendRedirect("hamming.jsp");
    }
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Odczytanie danych z żądania
        HttpSession session = request.getSession();
        String requestBody = request.getReader().lines().collect(Collectors.joining(System.lineSeparator()));
        session.setAttribute("inputWord", request.getHeader("inputWord"));

        // Przetworzenie danych
        List<List<Integer>> data = new ArrayList<>();
        try {
            JsonArray jsonArray = JsonParser.parseString(requestBody).getAsJsonArray();
            for (JsonElement rowElement : jsonArray) {
                JsonArray rowArray = rowElement.getAsJsonArray();
                List<Integer> rowData = new ArrayList<>();
                for (JsonElement cellElement : rowArray) {
                    rowData.add(cellElement.getAsInt());
                }
                data.add(rowData);
            }
        } catch (JsonSyntaxException e) {
            // Obsługa błędu w przypadku niepoprawnego formatu JSON
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            response.getWriter().write("Błąd: Nieprawidłowy format danych.");
            return;
        }

        session.setAttribute("notatka", request.getHeader("notatka"));

        HammingResponse responseData[] = new HammingResponse[data.size()];
        int tableInput[];
        for (int i = 0; i < data.size(); i++) {
            tableInput = new int[data.get(i).size()];
            for (int x = 0; x < tableInput.length; x++) {
                tableInput[x] = data.get(i).get(x);
            }
            responseData[i] = HammingCodeApi.receiveData(tableInput, 4);
        }

        HammingCodeText x = new HammingCodeText();
        x.setTable(responseData);
        session.setAttribute("tablicaReponse", (HammingCodeText) x);

        // Odczytanie raportu z nagłówka "Report"
        String report = request.getHeader("Report");
        session.setAttribute("raportReceiver", report);

        // Ustawienie typu zawartości odpowiedzi
        response.setContentType("application/json");

        response.sendRedirect("hammingReceiver.jsp");
    }

}
