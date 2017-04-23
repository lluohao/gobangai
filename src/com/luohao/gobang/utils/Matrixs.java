package com.luohao.gobang.utils;

public class Matrixs {
    public static int[][] dot(int[][] data, int p) {
        for (int i = 0; i < data.length; i++) {
            for (int j = 0; j < data[i].length; j++) {
                data[i][j] = data[i][j] * p;
            }
        }
        return data;
    }
    public static void print(int[][] data){
        for (int[] datum : data) {
            for (int i : datum) {
                System.out.print(i+"\t");
            }
            System.out.println();
        }
    }
}
