package com.luohao.gobang.chess;

import com.luohao.gobang.ai.eval.DynamicEvaluation;

import java.util.Arrays;

/**
 * Created by Everthing-- on 2017/5/4.
 */
public class DynamicChess extends Chess {
    private int[][] shapLine = new int[15][15];
    private int[][] shapRow = new int[15][15];
    private int[][] shapLeft = new int[15][15];
    private int[][] shapRight = new int[15][15];
    private DynamicEvaluation evaluation;

    public DynamicChess(DynamicEvaluation evaluation) {
        this.evaluation = evaluation;
    }

    public boolean play(int x, int y, int type){
        super.play(x,y,type);
        update(x,y);
        return true;
    }
    private void update(int x,int y){
        //update line
        for(int ix = Math.max(0,x-6);ix<=Math.min(x,9);ix++){
            shapLine[y][ix] = evaluation.shapType(this,ix,y,1,0);
        }
        //update row
        for(int iy = Math.max(0,y-6);iy<=Math.min(y,9);iy++){
            shapRow[iy][x] = evaluation.shapType(this,x,iy,0,1);
        }
        //update right
        for(int i = 0;i<6;i++){
            int rx = x-i;
            int ry = y-i;
            if(rx>=0&&ry>=0) {
                shapRight[ry][rx] = evaluation.shapType(this, rx, ry, 1, 1);
            }
        }
        //update left
        for(int i = 0;i<6;i++){
            int rx = x-i;
            int ry = y+i;
            if(rx>=0&&ry<=14) {
                shapLeft[ry][rx] = evaluation.shapType(this, rx, ry, 1, -1);
            }
        }
    }

    public int[][] getShapLine() {
        return shapLine;
    }

    public int[][] getShapRow() {
        return shapRow;
    }

    public int[][] getShapLeft() {
        return shapLeft;
    }

    public int[][] getShapRight() {
        return shapRight;
    }

    public static DynamicChess fromDate(int[][] data,DynamicEvaluation evaluation) {
        DynamicChess chess = new DynamicChess(evaluation);
        for (int i = 0; i < data.length; i++) {
            for (int j = 0; j < data[i].length; j++) {
                if (data[i][j] != 0) {
                    chess.play(j, i, data[i][j]);
                }
            }
        }
        return chess;
    }

    public static final int[][] TYPE_BLACK = {
            {1, 1, 1, 1, 1}, {0, 1, 1, 1, 1, 0},
            {0, 1, 1, 1, 0, 0}, {0, 0, 1, 1, 1, 0}, {0, 1, 0, 1, 1, 0}, {0, 1, 1, 0, 1, 0},
            {1, 1, 1, 1, 0}, {0, 1, 1, 1, 1}, {1, 1, 0, 1, 1}, {1, 0, 1, 1, 1}, {1, 1, 1, 0, 1},
            {0, 0, 1, 1, 0, 0}, {0, 0, 1, 0, 1, 0}, {0, 1, 0, 1, 0, 0}, {0, 0, 0, 1, 0, 0}, {0, 0, 1, 0, 0, 0}
    };
    public static final int[][] TYPE_WHITE = {
            {-1, -1, -1, -1, -1}, {0, -1, -1, -1, -1, 0},
            {0, -1, -1, -1, 0, 0}, {0, 0, -1, -1, -1, 0}, {0, -1, 0, -1, -1, 0}, {0, -1, -1, 0, -1, 0},
            {-1, -1, -1, -1, 0}, {0, -1, -1, -1, -1}, {-1, -1, 0, -1, -1}, {-1, 0, -1, -1, -1}, {-1, -1, -1, 0, -1},
            {0, 0, -1, -1, 0, 0}, {0, 0, -1, 0, -1, 0}, {0, -1, 0, -1, 0, 0}, {0, 0, 0, -1, 0, 0}, {0, 0, -1, 0, 0, 0}
    };
    public static void main(String[] args) {
        DynamicEvaluation evaluation = new DynamicEvaluation();
        DynamicChess chess = DynamicChess.fromDate(new int[][]{
                {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 1, -1, 1, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
        },evaluation);
        int[] mycount = new int[16];
        int[] otcount = new int[16];
        evaluation.count(chess,mycount,otcount,1);
        System.err.println(Arrays.toString(mycount));
        System.out.println(Arrays.toString(otcount));
//        System.out.println("*\t0\t1\t2\t3\t4\t5\t6\t7\t8\t9\t10\t11\t12\t13\t14\t");
//        Matrixs.print(chess.getSquare());
//        System.out.println();
//        System.out.println("*\t0\t1\t2\t3\t4\t5\t6\t7\t8\t9\t10\t11\t12\t13\t14\t");
//        Matrixs.print(chess.shapLine);
//        System.out.println();
//        System.out.println("*\t0\t1\t2\t3\t4\t5\t6\t7\t8\t9\t10\t11\t12\t13\t14\t");
//        Matrixs.print(chess.shapRow);
//        System.out.println();
//        System.out.println("*\t0\t1\t2\t3\t4\t5\t6\t7\t8\t9\t10\t11\t12\t13\t14\t");
//        Matrixs.print(chess.shapLeft);
//        System.out.println();
//        System.out.println("*\t0\t1\t2\t3\t4\t5\t6\t7\t8\t9\t10\t11\t12\t13\t14\t");
//        Matrixs.print(chess.shapRight);
    }
}
