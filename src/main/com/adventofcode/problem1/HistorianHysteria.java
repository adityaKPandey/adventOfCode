package com.adventofcode.problem1;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class HistorianHysteria {

  public static void main(String [] args){
      List<Integer> list1 = new ArrayList<>() , list2 = new ArrayList<>();

      Map<Integer,Integer> frequency2 = new HashMap<>();

      parseInputAndPopulateFrequency(list1, list2, frequency2);

      Collections.sort(list1);
      Collections.sort(list2);

      calculateDifferenceSum(list1, list2);

      calculateSimilarityScore(list1, frequency2);
  }

  private static void calculateSimilarityScore(List<Integer> list1, Map<Integer, Integer> frequency2) {
    int similarityScore = 0;

    for(int num1 : list1) {
       int count = 0;
       if(frequency2.containsKey(num1))
        count =  frequency2.get(num1);

       similarityScore += (num1 * count);
    }

    System.out.println("Sim = " + similarityScore);
  }

  private static void calculateDifferenceSum(List<Integer> list1, List<Integer> list2) {
    int diffSum = 0;

    for(int i = 0 ; i < list1.size(); i++){
      diffSum += Math.abs(list2.get(i) - list1.get(i));
    }

    System.out.println("Diff sum : " + diffSum);
  }

  private static void parseInputAndPopulateFrequency(List<Integer> list1, List<Integer> list2,
      Map<Integer, Integer> frequency2)  {
    List<String> lines = null;
    try {
      lines = Files.readAllLines(Paths.get("src/resources/input/input1.txt")); //src/resources/input/input1.txt
    } catch (IOException e) {
      e.printStackTrace();
    }
    lines.stream().forEach(line -> {
      String [] array = line.split(" ");
      list1.add(Integer.parseInt(array[0]));
      int num2 = Integer.parseInt(array[3]);
      list2.add(num2);

      int count2 = 0;
      if(frequency2.containsKey(num2))
        count2 = frequency2.get(num2);

      frequency2.put(num2, count2 + 1);
    });
  }

}
