package pl.wieik.ti.ti2023lab5.kontroler;

import com.google.gson.*;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

import java.io.BufferedReader;
import java.io.IOException;

import pl.wieik.ti.ti2023lab5.model.CrcResponse;

@WebServlet(name="crc", value = "/crc")
public class crc extends HttpServlet {
    @Override
    public void init() throws ServletException {
        super.init();
    }
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse res) throws IOException {
        res.sendRedirect("crc.jsp");
    }
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        HttpSession session = request.getSession();
        session.setAttribute("inputWord", request.getHeader("inputWord"));
        String inputWord,inputDiv,newData,crc;
        String newDataType;
        String newCRCType;

        //zczytaj linie z request.Body do Readera
        try {
            BufferedReader reader = request.getReader();
            StringBuilder requestBody = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                requestBody.append(line);
            }

            //Zdobądź dane z JSON
            JsonObject jsonObject = JsonParser.parseString(requestBody.toString()).getAsJsonObject();
            newData = jsonObject.get("newData").getAsString();
            inputWord = jsonObject.get("inputWord").getAsString();
            inputDiv = jsonObject.get("inputDiv").getAsString();
            crc= jsonObject.get("crc").getAsString();
            newDataType = newData.replace(",", "");
            newCRCType = crc.replace(",", "");


        } catch (JsonSyntaxException e) {
            // Obsługa błędu w przypadku niepoprawnego formatu JSON
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            response.getWriter().write("Błąd: Nieprawidłowy format danych.");
            return;
        }

        //przypisanie wartośći do składowych obiektu newResp
        CrcResponse newResp = new CrcResponse();
        newResp.setNewData(newDataType);
        newResp.setDiv(inputDiv);
        newResp.setExpected(inputWord);
        newResp.setCrc(newCRCType);
        session.setAttribute("notatka", request.getHeader("notatka"));
        session.setAttribute("newCrcReponse",newResp);

        // Odczytanie raportu z nagłówka "Report"
        String report = request.getHeader("Report");
        session.setAttribute("raportReceiver", report);

        // Ustawienie typu zawartości odpowiedzi
        response.setContentType("application/json");
        response.sendRedirect("crcReceiver.jsp");
    }

}
