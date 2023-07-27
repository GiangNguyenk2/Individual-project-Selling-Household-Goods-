/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import model.Category;

public class CategoryDAO extends DBContext {

    public List<Category> getAllCategories() {
        List<Category> categories = new ArrayList<>();
        String sql = "SELECT * FROM Category_HE160412";
        try {
            ResultSet rs = getData(sql);
            while (rs.next()) {
                Category c = new Category();
                c.setId(rs.getInt("Id"));
                c.setName(rs.getString("Name"));
                c.setDescription(rs.getString("Description"));
                categories.add(c);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return categories;
    }

}
