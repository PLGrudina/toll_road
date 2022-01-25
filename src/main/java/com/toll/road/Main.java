package com.toll.road;

import com.toll.road.algorithm.FitnessFunction;
import com.toll.road.algorithm.GeneticAlgorithm;
import com.toll.road.model.Road;

import java.util.Arrays;

/**
 * Created by Pavel Grudina on 29.05.2018.
 */
public class Main {
    public static void main(String[] args) {

        Road road = new Road();
        FitnessFunction ff = new FitnessFunction();
        GeneticAlgorithm geneticAlgorithm = new GeneticAlgorithm(ff, 10000, 3, 10, 45);
        System.out.println("Road check point: " + Arrays.toString(road.getFare()));
        System.out.println("Best result: " + Arrays.toString(geneticAlgorithm.run().getPaymentSchema()));
    }

}
