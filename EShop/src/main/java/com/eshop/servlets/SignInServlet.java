package com.eshop.servlets;

import com.eshop.dao.factory.DaoFactory;
import com.eshop.dao.interfaces.UserDao;
import com.eshop.entities.UserAccount.Role;
import com.eshop.entities.UserAccount.User;
import com.eshop.services.factory.ServiceFactory;
import com.eshop.services.interfaces.RoleService;
import com.eshop.services.interfaces.UserService;
import com.eshop.utils.HashUtility;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

@WebServlet(urlPatterns = "/login")
public class SignInServlet extends HttpServlet {
    private final UserService userService;
    private final RoleService roleService;

    public SignInServlet() {
        this.userService = ServiceFactory.getInstance().getUserService();
        roleService = ServiceFactory.getInstance().getRoleService();
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
            req.getRequestDispatcher("login.jsp").forward(req, resp);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        String errorMessage = null;

        String email = req.getParameter("email");
        String pass = req.getParameter("pass");

        User user = userService.signIn(email, pass);

        if (user != null) {
            HttpSession httpSession = req.getSession();
            httpSession.setAttribute("userId", user.getId());

            List<Role> userRoles = roleService.getUserRoles(user.getId());
            httpSession.setAttribute("roles", userRoles);
        }

        req.setAttribute("errmsg", errorMessage);
        req.getRequestDispatcher("login.jsp").forward(req, resp);
    }
}
