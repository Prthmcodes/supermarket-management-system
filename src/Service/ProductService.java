package Service;

import datastructures.MyLinkedList;
import model.Product;
import model.Activity;
import java.util.ArrayList;

public class ProductService {

    private ArrayList<Product> products = new ArrayList<>();

    public void addProduct(Product p) {
        products.add(p);
    }

    public ArrayList<Product> getAll() {
        return products;
    }

    public boolean deleteProduct(String id) {
        int index = linearSearch(id);
        if (index != -1) {
            products.remove(index);
            return true;
        }
        return false;
    }

    private int linearSearch(String id) {
        for (int i = 0; i < products.size(); i++) {
            if (products.get(i).getProductId().equals(id))
                return i;
        }
        return -1;
    }

    public void updateStock(String id, String type, int quantity) {
        int index = linearSearch(id);
        if (index == -1) return;

        Product p = products.get(index);

        if (type.equals("AddToStock")) {
            p.setQuantity(p.getQuantity() + quantity);
        } else {
            if (p.getQuantity() < quantity) return;
            p.setQuantity(p.getQuantity() - quantity);
        }

        // correct activity handling
        p.addActivity(new Activity(
                id + System.nanoTime(),
                type,
                quantity
        ));
    }

    // returns activities for GUI
    public MyLinkedList<Activity> getActivities(Product p) {
        return p.getActivities();
    }

    // find product by ID
    public Product findProduct(String id) {
        int index = linearSearch(id);
        if (index != -1) {
            return products.get(index);
        }
        return null;
    }
}