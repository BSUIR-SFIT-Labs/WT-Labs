package com.eshop.servlets;

import com.eshop.services.exception.ServiceException;
import com.eshop.services.factory.ServiceFactory;
import com.eshop.services.interfaces.RoleService;
import com.eshop.services.interfaces.UserService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet(urlPatterns = "/signup")
public class SignUpServlet extends HttpServlet {
    private final UserService userService;
    private final RoleService roleService;

    public SignUpServlet() {
        this.userService = ServiceFactory.getInstance().getUserService();
        this.roleService = ServiceFactory.getInstance().getRoleService();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        boolean isError = false;
        HttpSession httpSession = req.getSession(false);
        if (httpSession != null) {
            Object userIdObj = httpSession.getAttribute("userId");

            if (userIdObj != null) {
                isError = true;
            }
        }

        if (isError) {
            resp.sendRedirect(req.getContextPath());
        } else {
            req.setAttribute("regRes", 0);
            req.getRequestDispatcher("signup.jsp").forward(req, resp);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        String errorMessage = null;

        String email = req.getParameter("email");
        String password = req.getParameter("password");
        String firstName = req.getParameter("first-name");
        String lastName = req.getParameter("last-name");

        try {
            int userId = userService.signUp(email, password, firstName, lastName);
            roleService.addUserToRole(userId, 1);
        } catch (ServiceException ex) {
            errorMessage = "An error occurred during registration. Please enter correct data.";
        }

        req.setAttribute("errorMessage", errorMessage);
        req.getRequestDispatcher("signup.jsp").forward(req, resp);
    }
}
