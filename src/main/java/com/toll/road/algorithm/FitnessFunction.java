package com.toll.road.algorithm;

import com.toll.road.model.Driver;
import com.toll.road.model.Road;

/**
 * Created by Pavel Grudina on 31.05.2018.
 */
public class FitnessFunction {

    public Driver run(Driver driver) {
        int count = 0;
        int sum = 0;
        for (int checkpoint : Road.fare) {
            int payment = driver.getPaymentSchema()[count] - checkpoint;
            if (payment < 0) {
                sum += payment;
            }
            count++;
        }
        driver.setSuccess(Math.abs(sum));

        return driver;
    }

}
