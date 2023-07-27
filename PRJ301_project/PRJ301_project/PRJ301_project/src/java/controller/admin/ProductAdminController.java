/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller.admin;

import DAO.CategoryDAO;
import DAO.ProductDAO;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Part;
import java.io.File;
import java.util.List;
import java.util.UUID;
import model.Product;

@MultipartConfig(fileSizeThreshold = 1024 * 1024,
        maxFileSize = 1024 * 1024 * 5,
        maxRequestSize = 1024 * 1024 * 5 * 5)
@WebServlet(name = "ProductAdminController", urlPatterns = {"/admin/products"})
public class ProductAdminController extends HttpServlet {

    ProductDAO dao = new ProductDAO();
    CategoryDAO daoCate = new CategoryDAO();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String name = request.getParameter("name");
        int cid = getIntOrDefault("cid", 0, request);
        int page = 1;
        int pageSize = 10;
        if (name == null) {
            name = "";
        }
        if (request.getParameter("page") != null) {
            page = Integer.parseInt(request.getParameter("page"));
        }

        List<Product> products = dao.filterProducts(name, Double.MAX_VALUE, cid, page, pageSize, "", "");
        int totalProducts = dao.getTotalProducts(name, Double.MAX_VALUE, cid);
        int totalPages = (int) Math.ceil((double) totalProducts / pageSize);

        request.setAttribute("name", name);
        request.setAttribute("products", products);
        request.setAttribute("currentPage", page);
        request.setAttribute("totalPages", totalPages);
        setAttribute("lscate", daoCate.getAllCategories(), request);
        request.getRequestDispatcher("manageProduct.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String action = request.getParameter("action");
        switch (action) {
            case "create":
                createProduct(request, response);
                break;
            case "update":
                updateProduct(request, response);
                break;
            case "delete":
                deleteProduct(request, response);
                break;
        }
    }

    private void createProduct(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String name = request.getParameter("name");
        String description = request.getParameter("des");
        String image = "img/" + uploadImage("image", request);
        double price = Double.parseDouble(request.getParameter("price"));
        int quantity = Integer.parseInt(request.getParameter("quantity"));
        String categoryId = request.getParameter("categoryId");

        dao.insertProduct(new Product(quantity, name, description, image, price, quantity, ""), Integer.parseInt(categoryId));
        response.sendRedirect("products");
    }

    private void updateProduct(HttpServletRequest request, HttpServletResponse response) throws IOException {
        int id = Integer.parseInt(request.getParameter("id"));
        String name = request.getParameter("name");
        String description = request.getParameter("des");
        String image = "img/" + uploadImage("image", request);
        double price = Double.parseDouble(request.getParameter("price"));
        int quantity = Integer.parseInt(request.getParameter("quantity"));
        String categoryId = request.getParameter("categoryId");
        
        dao.updateProduct(new Product(id, name, description, image, price, quantity, categoryId), quantity);
        response.sendRedirect("products");
    }

    private void deleteProduct(HttpServletRequest request, HttpServletResponse response) throws IOException {
        int id = Integer.parseInt(request.getParameter("id"));
        dao.deleteProduct(id);
        response.sendRedirect("products");

    }

    private String uploadImage(String param, HttpServletRequest request) {
        String uploadPath = getServletContext().getRealPath("") + File.separator + "img";
        File uploadDir = new File(uploadPath);
        if (!uploadDir.exists()) {
            uploadDir.mkdir();
        }
        String filename;
        try {
            Part part = request.getPart(param);
            filename = part.getSubmittedFileName();
            System.out.println(filename);
            String uniname = UUID.randomUUID() + "_" + filename;
            part.write(uploadPath + File.separator + uniname);
            return uniname;
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    private String getFileName(Part part) {
        for (String content : part.getHeader("content-disposition").split(";")) {
            if (content.trim().startsWith("filename")) {
                return content.substring(content.indexOf("=") + 2, content.length() - 1);
            }
        }
        return "default.jpg";
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
