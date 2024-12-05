package com.adventofcode.problem4;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

public class CeresSearch {

  public static void main(String[] args) {
    char[][] input = readInput("input4.txt");
    int count = searchWord1(input, "XMAS");
    System.out.println("Count 1 = " + count);
  }

  //2573
  private static int searchWord1(char[][] input, String wordToSearch) {
    int rows = input.length, cols = input[0].length, count = 0;

    for (int i = 0; i < rows; i++) {
      for (int j = 0; j < cols; j++) {
        char character = input[i][j];
        if (character == wordToSearch.charAt(0)) {
          boolean foundLeft = search(input, i, j, wordToSearch, Direction.LEFT);
          boolean foundRight = search(input, i, j, wordToSearch, Direction.RIGHT);
          boolean foundUp = search(input, i, j, wordToSearch, Direction.UP);
          boolean foundDown = search(input, i, j, wordToSearch, Direction.DOWN);

          boolean foundLeadingDiagStartsTop = search(input, i, j, wordToSearch,
              Direction.DIAGONAL_LEADING_STARTING_AT_TOP);
          boolean foundTrailingDiagStartsTop = search(input, i, j, wordToSearch,
              Direction.DIAGONAL_TRAILING_STARTING_AT_TOP);
          boolean foundLeadingDiagStartsBottom = search(input, i, j, wordToSearch,
              Direction.DIAGONAL_LEADING_STARTING_AT_BOTTOM);
          boolean foundTrailingDiagStartsBottom = search(input, i, j, wordToSearch,
              Direction.DIAGONAL_TRAILING_STARTING_AT_BOTTOM);

          if (foundLeft) {
            System.out.println("Found left starting from " + i + "," + j);
            count++;
          }

          if (foundRight) {
            System.out.println("Found right starting from " + i + "," + j);
            count++;
          }

          if (foundDown) {
            System.out.println("Found down starting from " + i + "," + j);
            count++;
          }

          if (foundUp) {
            System.out.println("Found up starting from " + i + "," + j);
            count++;
          }

          if (foundLeadingDiagStartsTop) {
            System.out.println("Found leading diagonal starting from top " + i + "," + j);
            count++;
          }

          if (foundTrailingDiagStartsTop) {
            System.out.println("Found trailing diagonal starting from top " + i + "," + j);
            count++;
          }

          if (foundLeadingDiagStartsBottom) {
            System.out.println("Found leading diagonal starting from bottom " + i + "," + j);
            count++;
          }

          if (foundTrailingDiagStartsBottom) {
            System.out.println("Found trailing diagonal starting from bottom " + i + "," + j);
            count++;
          }
        }
      }
    }
    return count;
  }

  private static boolean search(char[][] input, int row, int col, String wordToSearch,
      Direction direction) {

    for (int i = 0; i < wordToSearch.length(); i++) {

      if (checkIfPositionOutOfBounds(input, row, col)) {
        return false;
      }

      if (input[row][col] != wordToSearch.charAt(i)) {
        return false;
      }

      if (direction == Direction.LEFT) {
        col--;
      } else if (direction == Direction.RIGHT) {
        col++;
      } else if (direction == Direction.UP) {
        row--;
      } else if (direction == Direction.DOWN) {
        row++;
      } else if (direction == Direction.DIAGONAL_LEADING_STARTING_AT_TOP) {
        row++;
        col++;
      } else if (direction == Direction.DIAGONAL_TRAILING_STARTING_AT_TOP) {
        row++;
        col--;
      } else if (direction == Direction.DIAGONAL_LEADING_STARTING_AT_BOTTOM) {
        row--;
        col--;
      } else if (direction == Direction.DIAGONAL_TRAILING_STARTING_AT_BOTTOM) {
        row--;
        col++;
      }
    }
    return true;
  }

  private static boolean checkIfPositionOutOfBounds(char[][] input, int row, int col) {
    return  (col < 0 || col >= input[0].length || row < 0 || row >= input.length);
  }


  private static char[][] readInput(String file) {
    List<String> lines = null;
    try {
      lines = Files.readAllLines(Paths.get(file));
    } catch (IOException e) {
      e.printStackTrace();
    }

    int rows = lines.size(), cols = lines.get(0).length();
    char[][] input = new char[rows][cols];

    for (int i = 0; i < rows; i++) {
      input[i] = lines.get(i).toCharArray();
    }

    return input;
  }

}

enum Direction {
  UP, DOWN, LEFT, RIGHT, DIAGONAL_LEADING_STARTING_AT_TOP, DIAGONAL_TRAILING_STARTING_AT_TOP,
  DIAGONAL_LEADING_STARTING_AT_BOTTOM, DIAGONAL_TRAILING_STARTING_AT_BOTTOM;
}

// DIAGONAL_LEADING  \