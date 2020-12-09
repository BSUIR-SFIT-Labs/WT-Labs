package com.eshop.servlets;

import com.eshop.entities.Order.Order;
import com.eshop.services.factory.ServiceFactory;
import com.eshop.services.interfaces.OrderService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@WebServlet(urlPatterns = "/makeorder")
public class OrdersServlet extends HttpServlet {
    private final OrderService orderService;

    public OrdersServlet() {
        this.orderService = ServiceFactory.getInstance().getOrderService();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        List<Order> orders = new ArrayList<Order>();
        boolean isError = false;
        HttpSession httpSession = req.getSession(false);
        if (httpSession == null) {
            isError = true;
        } else {
            Object userIdObj = httpSession.getAttribute("userId");

            if (userIdObj == null) {
                isError = true;
            } else {
                try {
                    int userId = (int) userIdObj;
                    orders = orderService.getAllUserOrders(userId);
                } catch (Exception ex) {
                    isError = true;
                }
            }
        }

        if (isError) {
            resp.sendRedirect(req.getContextPath());
        } else {
            req.setAttribute("orders", orders);
            req.getRequestDispatcher("makeorder.jsp").forward(req, resp);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        int result = 0;
        boolean isError = false;
        String errorMessage = null;
        List<Order> cards = new ArrayList<Order>();

        HttpSession httpSession = req.getSession(false);
        if (httpSession == null) {
            isError = true;
        } else {
            Object userIdObj = httpSession.getAttribute("userId");

            if (userIdObj == null) {
                isError = true;
            } else {
                try {
                    int userId = (int) userIdObj;


                } catch (Exception ex) {
                    isError = true;
                }
            }
        }

        if (isError) {
            resp.sendRedirect(req.getContextPath());
        } else {
            req.setAttribute("result", result);
            req.setAttribute("errorMessage", errorMessage);
            req.getRequestDispatcher("makeorder.jsp").forward(req, resp);
        }
    }
}
