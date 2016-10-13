package kz;

import java.io.File;
import java.util.*;

/**
 * Created by buffalobill571 on 10/13/2016.
 */
public class Print {
    public static void print(Object obj) {
        System.out.println(obj);
    }
    public static void print() {
        System.out.println();
    }
    public static void print2D(int[][] myArray) {
        for (int[] c:
             myArray) {
            print(Arrays.toString(c));
        }
    }
    public static int[][] copyArray(int[][] myArray) {
        int[][] array = new int[9][9];
        for (int i = 0; i < 9; i++) {
            array[i] = myArray[i].clone();
        }
        return array;
    }
    public static int[][] buildMatrice(String path) throws Exception {
        Scanner scan = new Scanner(new File(path));
        int[][] solution = new int[9][9];
        for (int i = 0; i < 9; i++) {
            String[] current = scan.nextLine().split(" ");
            for (int j = 0; j < 9; j++) {
                solution[i][j] = Integer.parseInt(current[j]);
            }
        }
        return solution;
    }
    public static int[][] solve(int[][] original) {
        int[][] solution = copyArray(original);
        if (solver(solution)) {
            return solution;
        }
        return new int[1][1];
    }
    public static boolean solver(int[][] solution) {
        MyContain<int[], Set<Integer>> minValue = null;
        while (true) {
            minValue = null;
            for (int i = 0; i < solution.length; i++) {
                for (int j = 0; j < solution[i].length; j++) {
                    if (solution[i][j] != 0) {
                        continue;
                    }
                    Set<Integer> possible = findValues(i, j, solution);
                    int possibleCount = possible.size();
                    switch (possibleCount) {
                        case 0:
                            return false;
                        case 1:
                            solution[i][j] = new ArrayList<>(possible).get(0);
                    }
                    if (minValue == null) {
                        minValue = new MyContain<>(new int[] {i, j}, possible);
                    } else {
                        if (possibleCount < minValue.b.size()) {
                            minValue = new MyContain<>(new int[] {i, j}, possible);
                        }
                    }
                }
            }
            if (minValue == null) {
                return true;
            } else if (1 < minValue.b.size()) {
                break;
            }
        }
        int r, c;
        r = minValue.a[0];
        c = minValue.a[1];
        for (Integer elem:
             minValue.b) {
            int[][] solutionCopy = copyArray(solution);
            solutionCopy[r][c] = elem;
            if (solver(solutionCopy)) {
                for (int i = 0; i < 9; i++) {
                    for (int j = 0; j < 9; j++) {
                        solution[i][j] = solutionCopy[i][j];
                    }
                }
                return true;
            }
        }
        return false;
    }

    public static Set<Integer> findValues(int row, int column, int[][] solution) {
        Integer[] digits = {1, 2, 3, 4, 5, 6, 7, 8, 9};
        List<Integer> list = Arrays.asList(digits);
        Set<Integer> values = new HashSet<Integer>(list);
        values.removeAll(rowValues(row, solution));
        values.removeAll(columnValues(column, solution));
        values.removeAll(blockValues(row, column, solution));
        return values;
    }
    public static List<Integer> rowValues(int row, int[][] solution){
        Integer[] list = new Integer[9];
        for (int i = 0; i < 9; i++) list[i] = solution[row][i];
        return Arrays.asList(list);
    }
    public static List<Integer> columnValues(int column, int[][] solution) {
        Integer[] list = new Integer[9];
        for (int i = 0; i < 9; i ++) {
            list[i] = solution[i][column];
        }
        return Arrays.asList(list);
    }
    public static Set<Integer> blockValues(int row, int column, int[][] solution) {
        Set<Integer> set = new HashSet<>(9);
        int rowStart = 3 * (row / 3);
        int columnStart = 3 * (column / 3);
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                set.add(solution[rowStart + i][columnStart + j]);
            }
        }
        return set;
    }
}

class MyContain <T, F> {
    T a;
    F b;
    MyContain(T a, F b) {
        this.a = a;
        this.b = b;
    }
}