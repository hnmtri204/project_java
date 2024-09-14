/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package tri.dev;

import tri.dev.data.dao.DatabaseDao;
import tri.dev.data.dao.UserDAO;
import tri.dev.data.model.User;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;
import tri.dev.data.dao.CategoryDao;
import tri.dev.data.model.Category;

/**
 *
 * @author MSI
 */
public class RegisterServlet extends BaseServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Lấy danh mục
        CategoryDao categoryDao = DatabaseDao.getInstance().getCategoryDao();
        List<Category> categoryList = categoryDao.findAll();

        request.setAttribute("categoryList", categoryList); // Thêm danh mục vào request
        request.getRequestDispatcher("register.jsp").include(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession();

        String email = request.getParameter("email");
        String password = request.getParameter("password");

        UserDAO userDao = DatabaseDao.getInstance().getUserDao();
        User user = userDao.find(email);

        if (user != null) {
            session.setAttribute("error", "Email existed");
            request.getRequestDispatcher("register.jsp").forward(request, response);
        } else {
            user = new User(email, password, "user");
            userDao.insert(user);
            response.sendRedirect("LoginServlet");
        }
    }
}
