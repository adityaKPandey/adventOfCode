package com.adventofcode.problem3;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MullItOver {

  public static void main(String[] args) {
    String input = readInput();
    String rootRegex =  "mul\\(\\d+,\\d+\\)";
    String numberRegex = "[^\\d]+";

    long sum1 = solvePart1(input, rootRegex, numberRegex);
    System.out.println("Part one sum = " + sum1);

    long sum2 = solvePart2(input, rootRegex, numberRegex);
    System.out.println("Part two sum = " + sum2);


  }

  private static long solvePart1(String input, String rootRegex, String numberRegex) {
    long sum = 0l;
    Pattern rootPattern = Pattern.compile(rootRegex);
    Matcher rootMatcher = rootPattern.matcher(input);

    while (rootMatcher.find()) {
        String part = rootMatcher.group();
        sum += getProduct(numberRegex, part);
    }

    return sum;
  }


  private static long solvePart2(String input, String rootRegex, String numberRegex) {
    Pattern rootPattern = Pattern.compile(rootRegex);
    Matcher rootMatcher = rootPattern.matcher(input);

    long sum = 0l;
    boolean enabled = true;
    int lastIndex = -1;

    while (rootMatcher.find()) {
      String part = rootMatcher.group();
      int endIndex = rootMatcher.end() - 1;
      int startIndex = rootMatcher.start();

      String subString = input.substring(lastIndex +1, startIndex);
      int lastIndexOfDo = subString.lastIndexOf("do()");
      int lastIndexOfDont = subString.lastIndexOf("don't()");

      if(lastIndexOfDont > lastIndexOfDo)
        enabled = false;
      else if(lastIndexOfDo > lastIndexOfDont)
        enabled = true;

      if(enabled)
        sum += getProduct(numberRegex, part);

      lastIndex = endIndex;
    }

    return sum;
  }

  private static long getProduct(String numberRegex, String part) {
    String[] numbers = part.split(numberRegex);

    int num1 = Integer.parseInt(numbers[1]);
    int num2 = Integer.parseInt(numbers[2]);

    //System.out.println("num1 = " + num1 + " , num2 = " + num2);
    long product = num1 * num2;
    return product;
  }

  private static String readInput() {
    List<String> lines = null;
    try {
      lines = Files.readAllLines(Paths.get("input3.txt"));
    } catch (IOException e) {
      e.printStackTrace();
    }
    StringBuilder inputBuilder = new StringBuilder();
    lines.stream().forEach(line -> inputBuilder.append(line));
    return inputBuilder.toString();
  }

}
