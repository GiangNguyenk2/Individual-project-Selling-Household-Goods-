/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import model.Product;

public class ProductDAO extends DBContext {
    
    public Product getProductById(int id) {
        Product product = null;
        String sql = "SELECT p.*, c.Name AS CategoryName,c.Id AS categoryId FROM Product_HE160412 p JOIN Category_HE160412 c ON p.CategoryId = c.Id WHERE p.Id = ?";
        try ( PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, id);
            ResultSet rs = statement.executeQuery();
            if (rs.next()) {
                product = new Product();
                product.setId(rs.getInt("Id"));
                product.setName(rs.getString("Name"));
                product.setPrice(rs.getDouble("Price"));
                product.setQuantity(rs.getInt("Quantity"));
                product.setDescription(rs.getString("Description"));
                product.setCategoryName(rs.getString("CategoryName"));
                product.setCategoryId(rs.getString("categoryId"));
                product.setStatus(rs.getBoolean("status"));
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return product;
    }
    
    public List<Product> getAllProducts() {
        List<Product> products = new ArrayList<>();
        String sql = "SELECT p.*, c.Name AS CategoryName FROM Product_HE160412 p JOIN Category_HE160412 c ON p.CategoryId = c.Id";
        try {
            ResultSet rs = getData(sql);
            while (rs.next()) {
                Product p = new Product();
                p.setId(rs.getInt("Id"));
                p.setName(rs.getString("Name"));
                p.setDescription(rs.getString("Description"));
                p.setImage(rs.getString("Image"));
                p.setPrice(rs.getInt("Price"));
                p.setQuantity(rs.getInt("Quantity"));
                p.setCategoryName(rs.getString("CategoryName"));
                products.add(p);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return products;
    }
    
    public int insertProduct(Product product, int cid) {
        String sql = "INSERT INTO Product_HE160412(Name, Description, Price,Quantity, Image, CategoryId) VALUES (?, ?, ?, ?, ?,?)";
        try ( PreparedStatement statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            statement.setString(1, product.getName());
            statement.setString(2, product.getDescription());
            statement.setDouble(3, product.getPrice());
            statement.setInt(4, product.getQuantity());
            statement.setString(5, product.getImage());
            statement.setInt(6, cid);
            int affectedRows = statement.executeUpdate();
            if (affectedRows == 0) {
                throw new SQLException("Creating product failed");
            }
            try ( ResultSet generatedKeys = statement.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    product.setId(generatedKeys.getInt(1));
                } else {
                    throw new SQLException("Creating product failed");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            return -1;
        }
        return product.getId();
    }
    
    public void updateProduct(Product product, int cid) {
        String sql = "UPDATE Product_HE160412 SET Name = ?, Description = ?, Price = ?, Quantity = ?, CategoryId = ?";
        if (product.getImage() != null) {
            sql += ", Image = ?";
        }
        sql += " WHERE Id = ?";
        try ( PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, product.getName());
            statement.setString(2, product.getDescription());
            statement.setDouble(3, product.getPrice());
            statement.setDouble(4, product.getQuantity());
            statement.setString(5, product.getCategoryName());
            int parameterIndex = 6;
            if (product.getImage() != null) {
                statement.setString(parameterIndex++, product.getImage());
            }
            statement.setInt(parameterIndex, product.getId());
            int affectedRows = statement.executeUpdate();
            if (affectedRows == 0) {
                throw new SQLException("Updating product failed");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public void deleteProduct(int id) {
        String sql = "DELETE FROM Product_HE160412 WHERE Id = ?";
        try ( PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, id);
            int affectedRows = statement.executeUpdate();
            if (affectedRows == 0) {
                throw new SQLException("Deleting product failed.");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public List<Product> filterProducts(String name, double price, int categoryId,
            int pageNumber, int pageSize, String sortBy, String sortOrder) {
        if (name == null) {
            name = "";
        }
        if (sortBy == null || sortBy.isEmpty()) {
            sortBy = "p.id";
        }
        List<Product> products = new ArrayList<>();
        String sql = "SELECT p.*, c.Name AS CategoryName FROM Product_HE160412 p join Category_HE160412 c on p.CategoryId=c.ID WHERE 1=1";
        if (name != null && !name.isEmpty()) {
            sql += " AND p.Name LIKE '%" + name + "%'";
        }
        if (price > 0) {
            sql += " AND Price <= " + price;
        }
        if (categoryId > 0) {
            sql += " AND p.CategoryId = " + categoryId;
        }
        sql += " ORDER BY " + sortBy;
        if (sortOrder != null && !sortOrder.isEmpty()) {
            sql += " " + sortOrder;
        }
        if (pageNumber > 0 && pageSize > 0) {
            int offset = (pageNumber - 1) * pageSize;
            sql += " OFFSET " + offset + " ROWS FETCH NEXT " + pageSize + " ROWS ONLY";
        }
        try {
            ResultSet rs = getData(sql);
            while (rs.next()) {
                Product p = new Product();
                p.setId(rs.getInt("Id"));
                p.setName(rs.getString("Name"));
                p.setDescription(rs.getString("Description"));
                p.setImage(rs.getString("Image"));
                p.setPrice(rs.getDouble("Price"));
                p.setQuantity(rs.getInt("Quantity"));
                p.setCategoryName(rs.getString("CategoryName"));
                products.add(p);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return products;
    }
    
    public int getTotalProducts(String name, double price, int categoryId) {
        int total = 0;
        String sql = "SELECT COUNT(*) as total FROM Product_HE160412 p join Category_HE160412 c on p.CategoryId=c.ID WHERE 1=1";
        if (name != null && !name.isEmpty()) {
            sql += " AND p.Name LIKE '%" + name + "%'";
        }
        if (price > 0) {
            sql += " AND Price <= " + price;
        }
        if (categoryId > 0) {
            sql += " AND p.CategoryId = " + categoryId;
        }
        try {
            ResultSet rs = getData(sql);
            if (rs.next()) {
                total = rs.getInt("total");
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return total;
    }
    public Product getById(int id) {
    Product product = null;
    try {
        String query = "SELECT * FROM Product_HE160412 WHERE id=?";
        PreparedStatement ps = connection.prepareStatement(query);
        ps.setInt(1, id);
        ResultSet rs = ps.executeQuery();
        if (rs.next()) {
            product = new Product();
            product.setId(rs.getInt("id"));
            product.setName(rs.getString("name"));
            product.setDescription(rs.getString("description"));
            product.setImage(rs.getString("image"));
            product.setPrice(rs.getDouble("price"));
            product.setQuantity(rs.getInt("quantity"));
            product.setCategoryId(rs.getString("categoryId"));
            product.setStatus(rs.getBoolean("status"));
        }
        connection.close();
    } catch (SQLException e) {
        e.printStackTrace();
    }
    return product;
}
    public static void main(String[] args) {
        ProductDAO dao = new ProductDAO();
        Product p = dao.getById(1);
        System.out.println(p);
    }

    
}
