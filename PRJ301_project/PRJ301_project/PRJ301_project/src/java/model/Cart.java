/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

import java.util.ArrayList;
import java.util.List;

public class Cart {

    private List<Item> items;
    private int total;

    public Cart() {
        items = new ArrayList<>();
        total = 0;
    }

    public void addItem(Product product, int quantity) {
        for (Item item : items) {
            if (item.getId() == product.getId()) {
                item.setQuantity(item.getQuantity() + quantity);
                return;
            }
        }
        items.add(new Item(product.getId(), product.getName(), quantity, product.getPrice()));
        total++;
    }

    public void plus(int id) {
        for (Item item : items) {
            if (item.getId() == id) {
                item.setQuantity(item.getQuantity() + 1);
                return;
            }
        }
    }

    public void minus(int id) {
        for (Item item : items) {
            if (item.getId() == id) {
                if (item.getQuantity() == 1) {
                    removeItem(id);
                    return;
                }
                item.setQuantity(item.getQuantity() - 1);
                return;
            }
        }
    }

    public void removeItem(int id) {
        items.removeIf(item -> item.getId() == id);
        total--;
    }

    public void updateQuantity(int id, int quantity) {
        for (Item item : items) {
            if (item.getId() == id) {
                item.setQuantity(quantity);
                return;
            }
        }
    }

    public void clear() {
        items.clear();
        total = 0;
    }

    public List<Item> getItems() {
        return items;
    }

    public double getTotalPrice() {
        double total = 0.0;
        for (Item item : items) {
            total += item.getPrice() * item.getQuantity();
        }
        return total;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }
}
