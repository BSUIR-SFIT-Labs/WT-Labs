package com.eshop.servlets;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet(urlPatterns = "/logout")
public class SignOutServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        HttpSession httpSession = req.getSession(false);

        if (httpSession != null) {
            httpSession.removeAttribute("userId");
            httpSession.removeAttribute("roles");
            httpSession.invalidate();
        }

        resp.sendRedirect(req.getContextPath());
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        HttpSession httpSession = req.getSession(false);

        if (httpSession != null) {
            httpSession.removeAttribute("userId");
            httpSession.removeAttribute("roles");
            httpSession.invalidate();
        }

        resp.sendRedirect(req.getContextPath());
    }
}
