package com.company;

import com.company.tests.Tests;

public class Main {

    public static void main(String[] args) {
        TestingScenarios tests = new TestingScenarios();
        String[] testingScenarios = new String[9];
        testingScenarios[0] = tests.generateFirstScenario();
        testingScenarios[1] = tests.generateWithSmallOpenTextWeight();
        testingScenarios[2] = tests.generateWithHeavyOpenTextWeight();
        testingScenarios[3] = tests.generateWithSmallKeyWeight();
        testingScenarios[4] = tests.generateWithHeavyKeyWeight();
        testingScenarios[5] = tests.errorIncreaseByKey();
        testingScenarios[6] = tests.errorIncreaseByOpenText();
        testingScenarios[7] = tests.correlation();
        testingScenarios[8] = tests.chainProcessing();

        for (int i = 0; i < 9; ++i)
        {
            System.out.println("Frequency test: " + Tests.frequencyTest(testingScenarios[i]));
        }

        for (int i = 0; i < 9; ++i)
        {
            System.out.println("Frequency within a block test: " + Tests.frequencyTestWithinABlock(testingScenarios[i]));
        }

        for (int i = 0; i < 9; ++i)
        {
            System.out.println("Runs test: " + Tests.runsTest(testingScenarios[i]));
        }
    }
}
