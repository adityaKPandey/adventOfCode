package com.adventofcode.problem1;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class HistorianHysteria {

  public static void main(String [] args){
    /*

3   4
4   3
2   5
1   3
3   9
3   3

1 3 2
2 3 1
3 3 0
3 4 1
3 5 2
4 9 5
     */

    try {
      List<String> lines = Files.readAllLines(Paths.get("input.txt"));
      List<Integer> list1 = new ArrayList<>() , list2 = new ArrayList<>();

      Map<Integer,Integer> frequency2 = new HashMap<>();

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

      Collections.sort(list1);
      Collections.sort(list2);

      int diffSum = 0;

      for(int i = 0 ; i < list1.size(); i++){
        diffSum += Math.abs(list2.get(i) - list1.get(i));
      }

      System.out.println("Diff sum : " + diffSum);

      int similarityScore = 0;

      for(int num1 : list1) {
         int count = 0;
         if(frequency2.containsKey(num1))
          count =  frequency2.get(num1);

         similarityScore += (num1 * count);
      }

      System.out.println("Sim = " + similarityScore);

    } catch (Exception e) {
      e.printStackTrace();
    }

  }

}
