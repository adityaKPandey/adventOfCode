package com.adventofcode.problem7;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

public class BridgeRepair {

    public static void main(String[] args) {
        List<String> input = readInput("resources/input/input7.txt");

        AtomicLong sum = new AtomicLong(0);
        input.stream().forEach((line) -> {
            String[] parts = line.split(": ");
            long target = Long.parseLong(parts[0]);
            boolean solved = solve(parts[1], target);

            if (solved) {
                sum.getAndAdd(target);
                System.out.println("line = " + line + " ,sum = " + target);
            }
        });

        System.out.println("FINAL OUTPUT : " + sum);

    }

    private static boolean solve(String expression, long target) {
        String[] operands = expression.split(" ");
        return solve(operands, target, 1, Long.parseLong(operands[0]));
    }

    private static boolean solve(String[] operands, long target, int index, long currentSum) {
        if (index == operands.length) {
            if (target == currentSum) {
                System.out.println("FOUND MATCH");
                return true;
            } else {
                System.out.println("NO MATCH");
                return false;
            }
        } else {
            long newOperand = Long.parseLong(operands[index]);
            boolean result1 = solve(operands, target, index + 1, currentSum + newOperand);
            if(result1)
                return true;

            boolean result2 = solve(operands, target, index + 1, currentSum * newOperand);

            if(result2)
                return true;

            int len = (int)Math.pow(10,operands[index].length());
            boolean result3 = solve(operands, target, index + 1, (currentSum * len)+newOperand);

            return result3;
        }
    }

    private static List<String> readInput(String file) {
        List<String> lines = null;
        try {
            lines = Files.readAllLines(Paths.get(file));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return lines;
    }
}
