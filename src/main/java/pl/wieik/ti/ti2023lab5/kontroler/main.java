package pl.wieik.ti.ti2023lab5.kontroler;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

import java.io.IOException;

@WebServlet(name="main", value = "/main")
public class main extends HttpServlet {

    @Override
    public void init() throws ServletException {
        super.init();
    }
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse res) throws IOException {
            res.sendRedirect("index.jsp");
    }
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse res) {
    }
}
