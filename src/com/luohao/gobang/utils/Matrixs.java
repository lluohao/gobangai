package com.luohao.gobang.utils;

import com.luohao.gobang.chess.ChessNode;

import java.util.List;

public class Matrixs {
    public static int[][] dot(int[][] data, int p) {
        for (int i = 0; i < data.length; i++) {
            for (int j = 0; j < data[i].length; j++) {
                data[i][j] = data[i][j] * p;
            }
        }
        return data;
    }

    public static int count(int[] data){
        int count = 0;
        for (int datum : data) {
            count+=datum;
        }
        return count;
    }

    public static void print(int[][] data) {
        int l=0;
        for (int[] datum : data) {
            System.out.print(l+++"\t");
            for (int i : datum) {
                System.out.print(i + "\t");
            }
            System.out.println();
        }
    }

    public static void print(List<ChessNode> nodes) {
        int[][] data = new int[15][15];
        for (int i = 0; i < nodes.size(); i++) {
            ChessNode node = nodes.get(i);
            data[node.getY()][node.getX()] = node.getType()*(i+1);
        }
        print(data);
    }

    public static int[] subVector(int[][] data,int x,int y,int dx,int dy,int size){
        int[] arr = new int[size];
        for (int i = 0; i < size; i++) {
            arr[i] = data[y+dy*i][x+dx*i];
        }
        return arr;
    }

    public static int[] subVector(int[][] data,int x,int y,int dx,int dy,int size,int[] result){
        for (int i = 0; i < size; i++) {
            result[i] = data[y+dy*i][x+dx*i];
        }
        return result;
    }


    public static boolean same(int[][] data,int[][] data2){
        for (int i = 0; i < data.length; i++) {
            for (int j = 0; j < data[i].length; j++) {
                if(data[i][j]!=data2[i][j]){
                    return false;
                }

            }
        }
        return true;
    }


    public static void main(String[] args) {
        int[][] data = new int[][]{
                {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0,0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 1, -1, 1, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},

        };
        long start = System.currentTimeMillis();
        int[] arr = new int[6];
        for(int i = 0;i<100000000;i++){
            arr = subVector(data,5,8,1,0,6,arr);
        }
        System.out.println(System.currentTimeMillis()-start);
    }
}
