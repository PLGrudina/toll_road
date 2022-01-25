package com.toll.road.model;

/**
 * Created by Pavel Grudina on 27.05.2018.
 */

public class Road {

    public final static int[] fare = new int[10];

    public Road() {
        initFareSchema();
    }


    public int[] getFare() {
        return fare;
    }

    private void initFareSchema() {
        int sum = 0;
        while (sum < 55) {
            int i = 0;
            for (int fare : fare) {
                getFare()[i] = (int) ((Math.random() + 0.1) * 10);
                sum += getFare()[i];
                i++;
            }
        }
    }

}
