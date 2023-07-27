/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller;

import DAO.ProductDAO;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import model.Cart;
import model.Product;

/**
 *
 * @author duong
 */
@WebServlet(name = "CartController", urlPatterns = {"/cart"})
public class CartController extends HttpServlet {

    ProductDAO dao = new ProductDAO();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession();
        Cart cart;
        if (session.getAttribute("cart") != null) {
            cart = (Cart) session.getAttribute("cart");
        } else {
            cart = new Cart();
        }
        String action = getParameter("action", request);
        if (action == null) {
            forward(request, response, "cart.jsp");
            return;
        }
        switch (action) {
            case "add2cart":
                int id = Integer.parseInt(getParameter("id", request));
                cart.addItem(dao.getProductById(id), 1);
                break;
            case "plus":
                id = Integer.parseInt(getParameter("id", request));
                cart.plus(id);
                break;
            case "minus":
                id = Integer.parseInt(getParameter("id", request));
                cart.minus(id);
                break;
            case "remove":
                id = Integer.parseInt(getParameter("id", request));
                cart.removeItem(id);
                break;
        }
        session.setAttribute("cart", cart);
        forward(request, response, "cart.jsp");

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
    }

    private String getParameter(String name, HttpServletRequest request) {
        return request.getParameter(name);
    }

    private void setAttribute(String name, Object o, HttpServletRequest request) {
        request.setAttribute(name, o);
    }

    private void forward(HttpServletRequest request, HttpServletResponse response, String path) throws ServletException, IOException {
        request.getRequestDispatcher(path).forward(request, response);
    }

}
