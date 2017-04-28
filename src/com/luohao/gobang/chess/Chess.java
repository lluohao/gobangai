package com.luohao.gobang.chess;

import java.util.ArrayList;
import java.util.List;

public class Chess {
    private int[][] square;
    private List<ChessNode> nodes = new ArrayList<>();

    public Chess() {
        square = new int[15][15];
    }

    public boolean play(int x, int y, int type) {
        ensureIndex(x, y);
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

    private void ensureIndex(int x, int y) {
        if (x < 0 || x > 15 || y < 0 || y > 15) {
            throw new RuntimeException("Out of index,the range is [0-15],but x=" + x + ",y=" + y);
        }
    }


    public int get(int x, int y) {
        return square[y][x];
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
