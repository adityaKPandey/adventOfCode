package com.adventofcode.problem2;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class RedNosedReports {

  public static void main(String [] args){

    int count = 0;

    List<List<Integer>> inputReports = parseInput();

    for(List<Integer> list : inputReports){

      if(list.size() < 2)
        count++;
      else{
         boolean increasing = true;
         if(list.get(1) < list.get(0))
           increasing = false;

         boolean valid = true;
         for(int i = 1 ; i < list.size(); i++){
           int diff = Math.abs(list.get(i) - list.get(i-1));
           if(diff >= 1 && diff <= 3){
             if( (increasing && list.get(i-1) > list.get(i))
               || (!increasing && list.get(i-1) < list.get(i))){
               valid = false;
               break;
             }
           }else{


             valid = false;
             break;
           }
         }

         if(valid)
           count++;
      }

    }

    System.out.println("Count = " + count);

  }

  private static List<List<Integer>> parseInput()  {
    List<List<Integer>> input = new ArrayList<>();
    List<String> lines = null;
    try {
      lines = Files.readAllLines(Paths.get("input2.txt"));
    } catch (IOException e) {
      e.printStackTrace();
    }

    lines.stream().forEach(line -> {
      String [] array = line.split(" ");
      input.add(new ArrayList<>());
      int size = input.size();
      Arrays.stream(array).forEach(numStr -> input.get(size-1).add(Integer.parseInt(numStr)));
    });

    return input;
  }
}
