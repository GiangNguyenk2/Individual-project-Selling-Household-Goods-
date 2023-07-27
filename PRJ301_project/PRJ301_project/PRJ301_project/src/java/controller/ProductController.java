/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller;

import DAO.CategoryDAO;
import DAO.ProductDAO;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet(name = "ProductController", urlPatterns = {"/search"})

public class ProductController extends HttpServlet {

    private final static int PAGE_SIZE = 6;

    ProductDAO dao = new ProductDAO();
    CategoryDAO categoryDao = new CategoryDAO();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String keyword = getParameter("keyword", request);
        double price = getIntOrDefault("price", Integer.MAX_VALUE, request);
        int page = 1;
        int cid = 0;
        String pageStr = request.getParameter("page");
        if (pageStr != null) {
            page = Integer.parseInt(pageStr);
        }
        String sortBy = getParameter("sortBy", request);
        String sortOrder = getParameter("sortOrder", request);

        int totalProducts = dao.getTotalProducts(keyword, 0, 0);
        int totalPages = (int) Math.ceil((double) totalProducts / PAGE_SIZE);

        request.setAttribute("totalPages", totalPages);
        request.setAttribute("currentPage", page);
        setAttribute("keyword", keyword, request);
        setAttribute("sortBy", sortBy, request);
        setAttribute("sortOrder", sortOrder, request);
        setAttribute("price", price, request);
        setAttribute("cid", cid, request);
        setAttribute("lsCategory", categoryDao.getAllCategories(), request);
        setAttribute("lsProduct", dao.filterProducts(keyword, price, cid, page, PAGE_SIZE, sortBy, sortOrder), request);
        forward(request, response, "product-list.jsp");
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        int cid = getIntOrDefault("cid", 0, request);
        double price = getIntOrDefault("price", Integer.MAX_VALUE, request);
        String keyword = getParameter("keyword", request);
        String sortBy = getParameter("sortBy", request);
        String sortOrder = getParameter("sortOrder", request);
        int page = Integer.parseInt(request.getParameter("page"));

        int totalProducts = dao.getTotalProducts(keyword, price, cid);
        int totalPages = (int) Math.ceil((double) totalProducts / PAGE_SIZE);

        request.setAttribute("totalPages", totalPages);
        request.setAttribute("currentPage", page);
        setAttribute("keyword", keyword, request);
        setAttribute("sortBy", sortBy, request);
        setAttribute("sortOrder", sortOrder, request);
        setAttribute("price", price, request);
        setAttribute("cid", cid, request);
        setAttribute("lsCategory", categoryDao.getAllCategories(), request);
        setAttribute("lsProduct", dao.filterProducts(keyword, price, cid, 1, PAGE_SIZE, sortBy, sortOrder), request);
        forward(request, response, "product-list.jsp");
    }

    private String getParameter(String name, HttpServletRequest request) {
        return request.getParameter(name);
    }

    private int getIntOrDefault(String name, int returnNum, HttpServletRequest request) {
        String param = request.getParameter(name);
        if (param == null || param == "none") {
            return returnNum;
        }
        try {
            return Integer.parseInt(param);
        } catch (Exception e) {
            e.printStackTrace();
            return returnNum;
        }
    }

    private void setAttribute(String name, Object o, HttpServletRequest request) {
        request.setAttribute(name, o);
    }

    private void forward(HttpServletRequest request, HttpServletResponse response, String path) throws ServletException, IOException {
        request.getRequestDispatcher(path).forward(request, response);
    }
}
