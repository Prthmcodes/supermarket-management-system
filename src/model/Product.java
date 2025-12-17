package model;

import datastructures.MyLinkedList;
import java.time.LocalDate;

public class Product {

    private String productId;
    private String name;
    private int quantity;
    private LocalDate entryDate;

    // stores only last 4 activities
    private MyLinkedList<Activity> activities = new MyLinkedList<>();

    public Product(String productId, String name, int quantity) {
        this.productId = productId;
        this.name = name;
        this.quantity = quantity;
        this.entryDate = LocalDate.now();
    }

    public String getProductId() {
        return productId;
    }

    public String getName() {
        return name;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int q) {
        this.quantity = q;
    }

    public MyLinkedList<Activity> getActivities() {
        return activities;
    }

    // keeps only last 4 activities
    public void addActivity(Activity activity) {

        if (activities.size() == 4) {
            activities.remove(0);
        }

        activities.add(activity);
    }

    @Override
    public String toString() {
        return productId + " | " +
                name + " | Qty: " + quantity +
                " | Added: " + entryDate;
    }
}
