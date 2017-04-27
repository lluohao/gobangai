package com.luohao.gobang.ai.eval;

import com.luohao.gobang.chess.Chess;

/**
 * Created by llhao on 2017/4/26.
 */
public class MatrixEvaluation implements Evaluation {
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

    public static final int[] SHAP_SCORE = {50000, 4320, 720, 720, 720, 720, 720, 720, 720, 720, 720, 120, 120, 120, 5, 5};

    @Override
    public int eval(Chess chess, int type) {
        int[][] myType = type == 1 ? TYPE_BLACK : TYPE_WHITE;
        int[][] otType = type == 1 ? TYPE_WHITE : TYPE_BLACK;
        int[] myCount = new int[myType.length];
        int[] otCount = new int[otType.length];
        for (int i = 0; i < myCount.length; i++) {
            myCount[i] = find(chess, myType[i]);
            otCount[i] = find(chess, otType[i]);
        }
        ;
        int myScore = 0;
        int otScore = 0;
        for (int i = 0; i < myCount.length; i++) {
            myScore += myCount[i] * SHAP_SCORE[i];
            otScore += otCount[i] * SHAP_SCORE[i];
        }
        return myScore > otScore ? myScore : myScore == otScore ? 0 : -otScore;
    }

    @Override
    public int absEval(Chess chess, int type) {
        int[][] myType = type == 1 ? TYPE_BLACK : TYPE_WHITE;
        int[] myCount = new int[myType.length];
        for (int i = 0; i < myCount.length; i++) {
            myCount[i] = find(chess, myType[i]);
        }
        ;
        int myScore = 0;
        for (int i = 0; i < myCount.length; i++) {
            myScore += myCount[i] * SHAP_SCORE[i];
        }
        return myScore;
    }

    public int find(Chess chess, int[] target) {
        int[][] data = chess.getSquare();
        int count = 0;
        //横向查找
        for (int y = 0; y < data.length; y++) {
            for (int x = 0; x <= data[y].length - target.length; x++) {
                boolean flag = true;
                for (int l = 0; l < target.length; l++) {
                    if (data[y][x + l] != target[l]) {
                        flag = false;
                        break;
                    }
                }
                if (flag) {
                    count++;
                }
            }
        }
        //纵向查找
        for (int y = 0; y <= data.length - target.length; y++) {
            for (int x = 0; x < data[y].length; x++) {
                boolean flag = true;
                for (int l = 0; l < target.length; l++) {
                    if (data[y + l][x] != target[l]) {
                        flag = false;
                        break;
                    }
                }
                if (flag) {
                    count++;
                }
            }
        }
        //左斜查找查找
        for (int y = target.length - 1; y < data.length; y++) {
            for (int x = 0; x <= data[y].length - target.length; x++) {
                boolean flag = true;
                for (int l = 0; l < target.length; l++) {
                    if (data[y - l][x + l] != target[l]) {
                        flag = false;
                        break;
                    }
                }
                if (flag) {
                    count++;
                }
            }
        }
        //右斜查找查找
        for (int y = 0; y <= data.length - target.length; y++) {
            for (int x = 0; x <= data[y].length - target.length; x++) {
                boolean flag = true;
                for (int l = 0; l < target.length; l++) {
                    if (data[y + l][x + l] != target[l]) {
                        flag = false;
                        break;
                    }
                }
                if (flag) {
                    count++;
                }
            }
        }
        return count;
    }

    @Override
    public int eval(Chess chess, int type, int next) {
        return 0;
    }

    public static void main(String[] args) {
        SimpleEvaluation simpleEvaluation = new SimpleEvaluation();
        MatrixEvaluation matrixEvaluation = new MatrixEvaluation();
        Chess chess = Chess.fromDate(new int[][]{
                {1, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                {1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0},
                {1, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0},
                {0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0},
                {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1},

        });
        System.out.println(simpleEvaluation.eval(chess, 1));
        System.out.println(matrixEvaluation.eval(chess, 1));
    }
}
