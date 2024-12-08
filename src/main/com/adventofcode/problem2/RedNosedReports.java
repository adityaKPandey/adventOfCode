package com.adventofcode.problem2;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class RedNosedReports {

  public static void main(String[] args) {

    int count = 0, minSize = Integer.MAX_VALUE;

    List<List<Integer>> inputReports = parseInput();

    for (List<Integer> list : inputReports) {

      minSize = Math.min(minSize, list.size());

      if (list.size() < 2) {
        count++;
      } else {
        boolean increasing = isIncreasingSequence(list);

        boolean valid = true, skipCurrent = false;
        int prev = list.get(0), unsafeLevels = 0;


        for (int i = 1; i < list.size(); i++) {
          int current = list.get(i);
          skipCurrent = false;

          int diff = Math.abs(current - prev);
          if (diff >= 1 && diff <= 3) {

            if ((increasing && prev > current)
                || (!increasing && prev < current)) {

              unsafeLevels++;

              if(unsafeLevels == 1){
                skipCurrent = true;
              }else{
                valid = false;
                break;
              }


            }
          } else {

            unsafeLevels++;
            if(unsafeLevels == 1){
              skipCurrent = true;
            }else{
              valid = false;
              break;
            }
          }

          if(!skipCurrent)
           prev = current;
        }

        if (valid) {
          System.out.println("Valid : " + list);
          count++;
        }
        else
          System.out.println("InVlid : " + list);

      }

    }

    System.out.println("Count = " + count);
    System.out.println("min size list = " + minSize);

  }

  private static boolean isIncreasingSequence(List<Integer> list) {
    int countOfIncrease = 0;

    for (int i = 1; i < 4; i++) {
      if (list.get(i) > list.get(i - 1)) {
        countOfIncrease++;
      }
    }

    if (countOfIncrease > 1) {
      return true;
    }

    return false;
  }

  private static List<List<Integer>> parseInput() {
    List<List<Integer>> input = new ArrayList<>();
    List<String> lines = null;
    try {
      lines = Files.readAllLines(Paths.get("src/resources/input/input2.txt"));
    } catch (IOException e) {
      e.printStackTrace();
    }

    lines.stream().forEach(line -> {
      String[] array = line.split(" ");
      input.add(new ArrayList<>());
      int size = input.size();
      Arrays.stream(array).forEach(numStr -> input.get(size - 1).add(Integer.parseInt(numStr)));
    });

    return input;
  }
}
