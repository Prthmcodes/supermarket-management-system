package ui;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import model.Product;
import model.Activity;
import Service.ProductService;
import datastructures.MyLinkedList;

import java.time.LocalTime;

public class SupermarketGUI extends Application {

    private final ProductService service = new ProductService();
    private final TextArea log = new TextArea();

    @Override
    public void start(Stage stage) {

        // ===== INPUT FIELDS =====
        TextField idField = field("product_id");
        TextField nameField = field("product_name");
        TextField qtyField = field("quantity");

        // ===== BUTTONS =====
        Button addBtn = button("ADD PRODUCT");
        Button listBtn = ghost("LIST PRODUCTS");
        Button deleteBtn = ghost("DELETE PRODUCT");
        Button addStockBtn = ghost("ADD STOCK");
        Button removeStockBtn = ghost("REMOVE STOCK");
        Button activityBtn = ghost("SHOW ACTIVITIES");

        // ===== LOG AREA =====
        log.setEditable(false);
        log.setWrapText(true);
        log.setPrefHeight(220);
        log.setStyle("""
                        -fx-control-inner-background: #020617;
                        -fx-font-family: Consolas;
                        -fx-text-fill: #e5e7eb;
                """);

        // ===== ACTIONS =====

        // Add product
        addBtn.setOnAction(e -> {
            try {
                String id = idField.getText().trim();
                String name = nameField.getText().trim();
                int qty = Integer.parseInt(qtyField.getText().trim());

                if (id.isEmpty() || name.isEmpty()) {
                    log("error :: empty fields");
                    return;
                }

                service.addProduct(new Product(id, name, qty));
                log("added :: " + name + " (" + qty + ")");

                clear(idField, nameField, qtyField);

            } catch (NumberFormatException ex) {
                log("error :: quantity not numeric");
            }
        });

        // List products
        listBtn.setOnAction(e -> {
            log.clear();
            if (service.getAll().isEmpty()) {
                log("inventory empty");
            } else {
                service.getAll().forEach(p -> log(p.toString()));
            }
        });

        // Delete product
        deleteBtn.setOnAction(e -> {
            String id = idField.getText().trim();
            boolean removed = service.deleteProduct(id);
            log(removed ? "deleted :: " + id : "error :: product not found");
        });

        // Add stock
        addStockBtn.setOnAction(e ->
                updateStock(idField, qtyField, "AddToStock")
        );

        // Remove stock
        removeStockBtn.setOnAction(e ->
                updateStock(idField, qtyField, "RemoveFromStock")
        );

        // Show activities
        activityBtn.setOnAction(e -> {
            String id = idField.getText().trim();
            Product p = service.findProduct(id);

            if (p == null) {
                log("error :: product not found");
                return;
            }

            log.clear();
            MyLinkedList<Activity> activities = service.getActivities(p);

            if (activities.size() == 0) {
                log("no activities for " + id);
            } else {
                log("activities for " + id + ":");
                for (int i = 0; i < activities.size(); i++) {
                    Activity a = activities.get(i);
                    log(a.toString());
                }
            }
        });

        // ===== LAYOUT =====
        VBox root = new VBox(15);
        root.setPadding(new Insets(25));
        root.setStyle("-fx-background-color: #0f172a;");

        Label title = new Label("SUPERMARKET INVENTORY");
        title.setFont(Font.font("Arial", 24));
        title.setTextFill(Color.web("#f1f5f9"));

        HBox fields = new HBox(10, idField, nameField, qtyField);
        HBox row1 = new HBox(10, addBtn, listBtn, deleteBtn);
        HBox row2 = new HBox(10, addStockBtn, removeStockBtn, activityBtn);

        root.getChildren().addAll(title, fields, row1, row2, log);

        Scene scene = new Scene(root, 720, 520);
        stage.setScene(scene);
        stage.setTitle("Supermarket Manager");
        stage.show();
    }

    // ===== HELPER METHODS =====

    private TextField field(String prompt) {
        TextField tf = new TextField();
        tf.setPromptText(prompt);
        tf.setStyle("""
                -fx-background-color: #1e293b;
                -fx-text-fill: #e5e7eb;
                -fx-prompt-text-fill: #64748b;
                -fx-padding: 8;
                """);
        return tf;
    }

    private Button button(String text) {
        Button btn = new Button(text);
        btn.setStyle("""
                -fx-background-color: #3b82f6;
                -fx-text-fill: white;
                -fx-padding: 8 16;
                -fx-cursor: hand;
                """);
        return btn;
    }

    private Button ghost(String text) {
        Button btn = new Button(text);
        btn.setStyle("""
                -fx-background-color: transparent;
                -fx-border-color: #475569;
                -fx-border-width: 1;
                -fx-text-fill: #cbd5e1;
                -fx-padding: 8 16;
                -fx-cursor: hand;
                """);
        return btn;
    }

    private void log(String msg) {
        String time = LocalTime.now().toString().substring(0, 8);
        log.appendText("[" + time + "] " + msg + "\n");
    }

    private void clear(TextField... fields) {
        for (TextField f : fields) f.clear();
    }

    private void updateStock(TextField idField, TextField qtyField, String type) {
        try {
            String id = idField.getText().trim();
            int qty = Integer.parseInt(qtyField.getText().trim());

            if (id.isEmpty()) {
                log("error :: product id required");
                return;
            }

            service.updateStock(id, type, qty);
            log(type + " :: " + id + " (" + qty + ")");

        } catch (NumberFormatException ex) {
            log("error :: quantity not numeric");
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}