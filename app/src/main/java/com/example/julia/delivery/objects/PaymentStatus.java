package com.example.julia.delivery.objects;

/**
 * Created by Lavrov on 18.05.2018.
 */

public enum PaymentStatus {
    Unpaid (0), Paid(1), PaymentOnReceipt(2);
    private int mValue;
    PaymentStatus(int value) { this.mValue = value;} // Constructor
    public int id(){return mValue;}                  // Return enum index

    public static PaymentStatus fromId(int value) {
        for(PaymentStatus color : values()) {
            if (color.mValue == value) {
                return color;
            }
        }
        return Unpaid;
    }
}
