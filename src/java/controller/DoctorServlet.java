package controller;

import dal.DoctorDAO;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import model.Doctor;

@WebServlet(name = "DoctorServlet", urlPatterns = {"/DoctorServlet"})
public class DoctorServlet extends HttpServlet {

    DoctorDAO dao;

    @Override
    public void init() throws ServletException {
        dao = new DoctorDAO();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession();
        Doctor sessionDoctor = (Doctor) session.getAttribute("d");

        if (sessionDoctor == null) {
            System.out.println("Không tìm thấy bác sĩ trong session → chuyển về login.jsp");
            response.sendRedirect("login.jsp");
            return;
        }

        Doctor doctor = dao.getDoctorNameById(sessionDoctor.getId());

        request.setAttribute("doctor", doctor);
        request.getRequestDispatcher("doctorhome.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doGet(request, response);
    }

    @Override
    public String getServletInfo() {
        return "DoctorServlet handles doctor dashboard loading and info retrieval.";
    }
}
