package com.adventofcode2024;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

public class ResonantCollinearity {

    public static void main(String [] args){
        Map<String, List<Coordinate>> symbolVsCoordinates = new HashMap<>();

        List<String> lines = readInput("resources/input/input8.txt");

        char [][] grid = new char[lines.size()][lines.get(0).length()];


        populateGridCoordinatesMap(symbolVsCoordinates, lines, grid);

        Set<Coordinate> result = findCollinearAntinodeLocations(symbolVsCoordinates, grid, true);

        System.out.println("Total count = " + result.size());

        Set<Coordinate> result2 = findCollinearAntinodeLocations(symbolVsCoordinates, grid, false);

        List<Coordinate> list2 = new ArrayList<>(result2);
        Collections.sort(list2, (a,b)->{
            if(a.x == b.x)
                return a.y - b.y;

            return a.x - b.x;
        });

        System.out.println("$$$");

        System.out.println(list2);

        System.out.println("Total count = " + result2.size());
    }

    private static Set<Coordinate> findCollinearAntinodeLocations(Map<String, List<Coordinate>> symbolVsCoordinates, char[][] grid, boolean enableDistanceConstraint) {
        Set<Coordinate> result = new HashSet<>();

        for(Map.Entry<String, List<Coordinate>> entry : symbolVsCoordinates.entrySet()){
            String symbol = entry.getKey();
            List<Coordinate> coordinates = entry.getValue();
            //System.out.println("Symnol = " + symbol + ", coo=" + coordinates);

            for(int i = 0; i < coordinates.size() - 1; i++){
                for(int j = i+1; j < coordinates.size(); j++){
                    Coordinate c1 = coordinates.get(i);
                    Coordinate c2 = coordinates.get(j);

                    double slope = (double) (c2.y - c1.y)/(double) (c2.x - c1.x);
                    //System.out.println("line = " + c1 + "," + c2 + ", slope = " + slope);
                    for(int k = 0; k < grid.length; k++){
                        for(int l = 0; l < grid[0].length; l++){
                            boolean overlappingPoint = ((k == c1.x && l == c1.y) || (k == c2.x && l == c2.y));
                            if(enableDistanceConstraint && overlappingPoint)
                               continue;
                            else if (overlappingPoint){
                                result.add(new Coordinate(c1.x, c1.y));
                                result.add(new Coordinate(c2.x, c2.y));
                                continue;
                            }
                            //double lineEquationValue = l - slope*k + slope*c1.x - c1.y;
                            double lineEquationValue = (c2.x - c1.x)*(l - c1.y) + (c2.y - c1.y)*(c1.x - k);
                            if(lineEquationValue == 0){
                                System.out.println("line = " + c1 + "," + c2 + ", k="+k+",l="+l  +"| slope = " + slope + " , lineEquationValue = " + lineEquationValue);

                                if(enableDistanceConstraint) {
                                    boolean distanceCheckValidationResult = checkDistanceConstraint(new int []{k,l}, c1, c2);
                                    if (distanceCheckValidationResult)
                                        result.add(new Coordinate(k, l));
                                }else {
                                    result.add(new Coordinate(k, l));
                                }
                            }
                        }
                    }
                }
            }
       }
        return result;
    }

    private static boolean checkDistanceConstraint(int [] position, Coordinate c1, Coordinate c2) {
        long dist1 = (long) (Math.pow((position[0] - c1.x),2) + Math.pow((position[1] - c1.y),2));
        long dist2 = (long) (Math.pow((position[0] - c2.x),2) + Math.pow((position[1] - c2.y),2));
        if(dist1 == 4* dist2 || dist2 == 4* dist1){
            System.out.println( " k=" + position[0] + ", l = " + position[1] );
            return true;
        }
        return false;
    }

    private static void populateGridCoordinatesMap(Map<String, List<Coordinate>> symbolVsCoordinates, List<String> lines, char[][] grid) {
        for(int i = 0; i < lines.size(); i++){
            String line = lines.get(i);
            grid[i] = new char[line.length()];
            for(int j = 0 ; j < line.length(); j++){
                grid[i][j] = line.charAt(j);

                String character = grid[i][j] + "";

                if(character.equals("."))
                    continue;

                if(!symbolVsCoordinates.containsKey(character))
                    symbolVsCoordinates.put(character, new ArrayList<>());

                symbolVsCoordinates.get(character).add(new Coordinate(i,j));

            }
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

class Coordinate{

    int x,y;

    public Coordinate(int x, int y){
        this.x = x;
        this.y = y;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Coordinate that = (Coordinate) o;
        return x == that.x && y == that.y;
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y);
    }

    @Override
    public String toString() {
        return "Coordinate{" +
                "x=" + x +
                ", y=" + y +
                '}';
    }
}
