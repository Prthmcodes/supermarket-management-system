package model;

import java.time.LocalDate;

public class Activity {

    // basic details about the activity
    private String activityId;
    private String activityName;   // example: AddToStock or RemoveFromStock
    private int quantity;
    private LocalDate date;

    // constructor
    public Activity(String activityId, String activityName, int quantity) {
        this.activityId = activityId;
        this.activityName = activityName;
        this.quantity = quantity;

        // date is set automatically when activity is created
        this.date = LocalDate.now();
    }

    // getters
    public int getQuantity() {
        return quantity;
    }

    public String getActivityName() {
        return activityName;
    }

    public LocalDate getDate() {
        return date;
    }

    // used when printing activity info
    @Override
    public String toString() {
        return activityName +
                " | Qty: " + quantity +
                " | Date: " + date;
    }
}
