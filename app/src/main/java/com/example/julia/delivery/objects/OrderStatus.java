package com.example.julia.delivery.objects;

/**
 * Created by Lavrov on 18.05.2018.
 */

public enum OrderStatus {
    None (-1), New (0), InProcess(1), Completed(2), Canceled(3);
    private int mValue;
    OrderStatus(int value) { this.mValue = value;} // Constructor
    public int id(){return mValue;}                  // Return enum index

    public static OrderStatus fromId(int value) {
        for(OrderStatus color : values()) {
            if (color.mValue == value) {
                return color;
            }
        }
        return New;
    }
}
