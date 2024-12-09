package com.adventofcode.problem5;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Set;

public class PrintQueue {


  public static void main(String [] args){
    List<String> lines = readInput("src/resources/input/input5.txt");

    Map<Integer, Set<Integer>> adjacencyList = new HashMap<>();
    List<List<Integer>> inputLists = new ArrayList<>();
    Set<Integer> nodes = createAdjacencyListAndStoreInput(lines, adjacencyList, inputLists);

    System.out.println("nodes list = "+ nodes);
    System.out.println("nodes size = "+ nodes.size());

    nodes.stream().forEach(node ->{
      if(!adjacencyList.containsKey(node))
        adjacencyList.put(node, new HashSet<>());
    });

    System.out.println("Adjacency list = "+ adjacencyList);
    System.out.println("Adjacency list size = "+ adjacencyList.size());

    Map<Integer, Integer> numVsPosition = new HashMap<>();// topoSort(adjacencyList);


    int sum = 0;

    for(int i = 0; i < inputLists.size(); i++){
       List<Integer> input = inputLists.get(i);
       boolean valid = true;

       //numVsPosition = topoSort(adjacencyList);

       //int maxPosition = 0;
       for(int j = 0 ; j < input.size(); j++){
         for(int k = j+1 ; k < input.size(); k++){
             if(adjacencyList.get(input.get(j)).contains(input.get(k))
                  && !adjacencyList.get(input.get(k)).contains(input.get(j))){
                  continue;
             }else{
               valid = false;
               break;
             }
         }
       }

       if(valid){
         int size = input.size();
         sum += input.get(size/2);
       }else{
         System.out.println("invalid list : " + input);
       }
    }

    System.out.println("Sum = " + sum);

  }

  private static Map<Integer, Integer>  topoSort(Map<Integer, Set<Integer>> adjacencyList) {
    Map<Integer, Integer> numVsPosition = new LinkedHashMap<>();
    int pos = 0;

    Map<Integer, Integer> nodeVsIndegrees = new HashMap<>();

    adjacencyList.entrySet().forEach(entry -> {
       Set<Integer> adjacentNodes = entry.getValue();
      if(!nodeVsIndegrees.containsKey(entry.getKey()))
        nodeVsIndegrees.put(entry.getKey(), 0);


       adjacentNodes.stream().forEach( node -> {
           int indegree = 0;
           if(nodeVsIndegrees.containsKey(node))
             indegree = nodeVsIndegrees.get(node);

           nodeVsIndegrees.put(node, indegree + 1);
       });
    });

    Queue<Integer> queue = new LinkedList<>();

    nodeVsIndegrees.entrySet().forEach(entry -> {
      if(entry.getValue() == 0)
        queue.offer(entry.getKey());
    });

    while(!queue.isEmpty()){
      int node = queue.poll();
      pos++;
      numVsPosition.put(node, pos);

      adjacencyList.get(node).forEach( nb -> {
        int indegree = nodeVsIndegrees.get(nb);
        if(indegree == 1)
          queue.offer(nb);

        nodeVsIndegrees.put(nb, indegree - 1);
      });
    }

    System.out.println("Topo sort = " + numVsPosition);
    return numVsPosition;
  }

  private static Set<Integer> createAdjacencyListAndStoreInput(List<String> lines, Map<Integer, Set<Integer>> adjacencyList, List<List<Integer>> input) {
    Set<Integer> nodes = new HashSet<>();

    lines.stream().forEach(line -> {
        if(line.contains("|")){
           String [] array = line.split("\\|");
           Integer source = Integer.parseInt(array[0]);
           Integer target = Integer.parseInt(array[1]);

          nodes.add(source);
          nodes.add(target);

           if(!adjacencyList.containsKey(source))
             adjacencyList.put(source, new HashSet<>());

          if(!adjacencyList.containsKey(target))
            adjacencyList.put(target, new HashSet<>());

           adjacencyList.get(source).add(target);

        }else if(line.contains(",")){
          List<Integer> toCheck = new ArrayList<>();
          Arrays.stream(line.split(",")).forEach(element -> toCheck.add(Integer.parseInt(element)));
          input.add(toCheck);
        }
    });

    return nodes;
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
