package com.toll.road.algorithm;

import com.toll.road.model.Driver;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by Pavel Grudina on 31.05.2018.
 */
public class GeneticAlgorithm {

    private FitnessFunction fitnessFunction;
    private int numberOfIndivid = 1000;
    private int crossingCount = 3;

    //    from 1 to 100
    private int mutationIndexPercent = 10;
    private int selectionIndexPercent = 50;

    private List<Driver> generation = new ArrayList<>();


    public GeneticAlgorithm(FitnessFunction fitnessFunction,
                            int numberOfIndivid,
                            int crossingCount,
                            int mutationIndexPercent,
                            int selectionIndexPercent) {

        this.fitnessFunction = fitnessFunction;
        this.numberOfIndivid = numberOfIndivid;
        this.crossingCount = crossingCount;
        this.mutationIndexPercent = mutationIndexPercent;
        this.selectionIndexPercent = selectionIndexPercent;
    }

    public GeneticAlgorithm(FitnessFunction fitnessFunction) {
        this.fitnessFunction = fitnessFunction;
    }


    public Driver run() {
        createGeneration(numberOfIndivid);
        while (crossingCount > 0) {
            selection();
            crossing();
            crossingCount--;
            mutation();
        }
        generation.parallelStream().sorted(new Comparator<Driver>() {
            @Override
            public int compare(Driver o1, Driver o2) {
                if (o1.getSuccess() > o2.getSuccess()) {
                    return 1;
                }
                if (o1.getSuccess() < o2.getSuccess()) {
                    return -1;
                }
                return 0;
            }
        });

        return generation.get(0);
    }

    private void createGeneration(int numberOfIndivid) {
        try {
            if (numberOfIndivid <= 0) {
                throw new Exception();
            }
        } catch (Exception e) {
            System.out.println("Wrong number of individual. Use default 1000.");
            numberOfIndivid = 1000;
        }
        for (; numberOfIndivid > 0; numberOfIndivid--) {
            generation.add(new Driver());
        }
    }

    private void selection() {

        try {
            if (selectionIndexPercent <= 0 || selectionIndexPercent > 95) {
                throw new ArithmeticException();
            }
        } catch (Exception e) {
            System.out.println("Wrong selection index. Use default 50%.");
            selectionIndexPercent = 50;
        }

        generation.parallelStream().
                forEach(driver -> fitnessFunction.run(driver));

        generation.sort(new Comparator<Driver>() {
            @Override
            public int compare(Driver o1, Driver o2) {
                if (o1.getSuccess() > o2.getSuccess()) {
                    return 1;
                }
                if (o1.getSuccess() < o2.getSuccess()) {
                    return -1;
                }
                return 0;
            }
        });

        int survivorsIndivid = (int) (generation.size() * ((selectionIndexPercent) * 0.01));

//        if (survivorsIndivid > generation.size()) {
//            throw new ArithmeticException("Wrong selection index. Use default 50%.");
//        } else {
        generation = generation.subList(0, survivorsIndivid);
//        }
    }

    private void crossing() {
        int crossPoint = (int) (Math.random() * 6) + 3;
        int crossingCount = generation.size();

        for (int i = 0; i < crossingCount; i++) {

            try {
                List<Integer> firstParent = Arrays.stream(generation.get(i).getPaymentSchema())
                        .boxed().collect(Collectors.toList());
                List<Integer> secondParent = Arrays.stream(generation.get(i + 1).getPaymentSchema())
                        .boxed().collect(Collectors.toList());

                List<Integer> buffer = new ArrayList<>();
                buffer.addAll(firstParent);
                buffer.addAll(secondParent);

                List<Integer> child = new ArrayList<>();

                for (int j = crossPoint; j < buffer.size(); j++) {
                    if (!child.contains(buffer.get(j)) && child.size() < 11) {
                        child.add(buffer.get(j));
                    }

                }

                Driver first = new Driver(child.stream().mapToInt(j -> j).toArray());
                fitnessFunction.run(first);
                generation.add(first);

                i++;
            } catch (Exception e) {

            }
        }
    }

    private void mutation() {
        try {
            if (mutationIndexPercent <= 0 || mutationIndexPercent > 95) {
                throw new ArithmeticException();
            }
        } catch (ArithmeticException e) {
            mutationIndexPercent = 10;
            System.out.println("Wrong mutation index. Use default 10%.");

        }
        int mutationFrequency = (int) (generation.size() * ((mutationIndexPercent) * 0.1));

        for (int i = mutationFrequency; i < generation.size(); i += mutationFrequency) {

            int geneNumber = (int) ((Math.random() + 0.1) * 9);
            int tmp = generation.get(i).getPaymentSchema()[geneNumber];
            generation.get(i).getPaymentSchema()[geneNumber] = generation.get(i).getPaymentSchema()[geneNumber - 1];
            generation.get(i).getPaymentSchema()[geneNumber - 1] = tmp;
        }
    }


//    get/set


    public FitnessFunction getFitnessFunction() {
        return fitnessFunction;
    }

    public void setFitnessFunction(FitnessFunction fitnessFunction) {
        this.fitnessFunction = fitnessFunction;
    }

    public int getNumberOfIndivid() {
        return numberOfIndivid;
    }

    public void setNumberOfIndivid(int numberOfIndivid) {
        this.numberOfIndivid = numberOfIndivid;
    }

    public int getMutationIndexPercent() {
        return mutationIndexPercent;
    }

    public void setMutationIndexPercent(int mutationIndexPercent) {
        this.mutationIndexPercent = mutationIndexPercent;
    }

    public int getSelectionIndexPercent() {
        return selectionIndexPercent;
    }

    public void setSelectionIndexPercent(int selectionIndexPercent) {
        this.selectionIndexPercent = selectionIndexPercent;
    }

    public List<Driver> getGeneration() {
        return generation;
    }

    public void setGeneration(List<Driver> generation) {
        this.generation = generation;
    }

    public int getCrossingCount() {
        return crossingCount;
    }

    //    generate random number 1 to 10.
    public int randomValue() {
        return (int) ((Math.random() + 0.1) * 10);
    }
}


