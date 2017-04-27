package com.luohao.gobang.chess;

import java.util.ArrayList;
import java.util.List;

public class Chess {
    private int[] data;
    private int[] dataT;
    private int[] left;
    private int[] right;
    private int[][] square;
    private List<ChessNode> nodes = new ArrayList<>();

    public Chess() {
        square = new int[15][15];
        data = new int[15 * 15 + 15];
        dataT = new int[15 * 16];
        left = new int[15 * 15 + 29];
        right = new int[15 * 15 + 29];
        for (int i = 0; i < 15; i++) {
            dataT[15 + 16 * i] = 2;
            data[15 + 16 * i] = 2;
        }
        int sum = 0;
        for (int i = 0; i < 29; i++) {
            sum += 15 - Math.abs((14 - i));
            left[sum] = 2;
            right[sum++] = 2;
        }
    }

    public boolean play(int x, int y, int type) {
        ensureIndex(x, y);
        //处理正数据
        int index = x + y * 16;
        data[index] = type;

        //处理转置数据
        int indexT = x * 16 + y;
        dataT[indexT] = type;

        //处理左下右上数据
        int indexLeft = indexOfLeft(x, y);
        left[indexLeft] = type;

        //处理左上右下数据
        int indexRight = indexOfRight(x, y);
        right[indexRight] = type;

        //处理原始数据
        square[y][x] = type;

        if(type==0){
            back();
        }else {
            //添加步骤
            ChessNode node = new ChessNode();
            node.setX(x);
            node.setY(y);
            node.setType(type);
            nodes.add(node);
        }
        return true;
    }

    private void back(){
        if(nodes.size()==0){
            return;
        }
        nodes.remove(nodes.size()-1);
    }

    private int indexOfRight(int x, int y) {
        int lineNumber = 14 - x + y;
        int sum = 0;
        for (int i = 0; i < lineNumber; i++) {
            sum += 15 - Math.abs((14 - i)) + 1;
        }
        int add = lineNumber < 15 ? y : y + 14 - lineNumber;
        return sum + add;
    }

    private int indexOfLeft(int x, int y) {
        int lineNumber = x + y;
        int sum = 0;
        for (int i = 0; i < lineNumber; i++) {
            sum += 15 - Math.abs((14 - i)) + 1;
        }
        int add = lineNumber < 15 ? x : x + 14 - lineNumber;
        return sum + add;
    }

    private void ensureIndex(int x, int y) {
        if (x < 0 || x > 15 || y < 0 || y > 15) {
            throw new RuntimeException("Out of index,the range is [0-15],but x=" + x + ",y=" + y);
        }
    }


    public int get(int x, int y) {
        return square[y][x];
    }

    public int[] getData() {
        return data;
    }

    public int[] getDataT() {
        return dataT;
    }

    public int[] getLeft() {
        return left;
    }

    public int[] getRight() {
        return right;
    }

    public int[][] getSquare() {
        return square;
    }

    public List<ChessNode> getNodes() {
        return nodes;
    }

    public static Chess fromDate(int[][] data) {
        Chess chess = new Chess();
        for (int i = 0; i < data.length; i++) {
            for (int j = 0; j < data[i].length; j++) {
                if (data[i][j] != 0) {
                    chess.play(j, i, data[i][j]);
                }
            }
        }
        return chess;
    }
}
