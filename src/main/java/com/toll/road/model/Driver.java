package com.toll.road.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by Pavel Grudina on 27.05.2018.
 */

public class Driver {

    private int[] paymentSchema = new int[10];

    //    fitness function success rate
    private int success;

    public Driver() {
        initPaymentSchema();
    }

    public Driver(int[] paymentSchema) {
        this.paymentSchema = paymentSchema;
    }


    public void initPaymentSchema() {
        List<Integer> fill = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            fill.add(i + 1);
        }
        Collections.shuffle(fill);
        paymentSchema = fill.stream().mapToInt(i -> i).toArray();
    }

    public int[] getPaymentSchema() {
        return paymentSchema;
    }

    public void setPaymentSchema(int[] paymentSchema) {
        this.paymentSchema = paymentSchema;
    }

    public int getSuccess() {
        return success;
    }

    public void setSuccess(int success) {
        this.success = success;
    }

}
