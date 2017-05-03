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
}
