package com.luohao.gobang.ai.eval;

import com.luohao.gobang.ai.eval.finder.Finder;
import com.luohao.gobang.ai.eval.finder.SimpleFinder;
import com.luohao.gobang.chess.Chess;

/**
 * Created by llhao on 2017/4/22.
 */
public class SimpleEvaluation implements Evaluation {
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

    public static final int[] SHAP_SCORE = {50000, 4320, 720, 720, 700, 700, 720, 720, 700, 700, 700, 120, 100, 100, 3, 3};

    private Finder finder = new SimpleFinder();

    public int win(Chess chess){
        int black = find(chess,TYPE_BLACK[0]);
        if(black>0){
            return 1;
        }
        int white = find(chess,TYPE_WHITE[0]);
        if(white>0){
            return -1;
        }
        return 0;
    }

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
        int myScore = 0;
        for (int i = 0; i < myCount.length; i++) {
            myScore += myCount[i] * SHAP_SCORE[i];
        }
        return myScore;
    }

    @Override
    public int eval(Chess chess, int type, int next) {
        int[][] myType = next == 1 ? TYPE_BLACK : TYPE_WHITE;
        int[][] otType = next == 1 ? TYPE_WHITE : TYPE_BLACK;
        int[] myCount = new int[myType.length];
        int[] otCount = new int[otType.length];
        for (int i = 0; i < myCount.length; i++) {
            myCount[i] = find(chess, myType[i]);
            otCount[i] = find(chess, otType[i]);
        }
        //判断是否包含己方活五
        if (myCount[0] > 0) {
            return 100000;
        } else if (otCount[0] > 0) {
            return -100000;
        } else if (myCount[1] > 0) {

        }
        return 0;
    }

    public int find(Chess chesss, int[] target) {
        int count = finder.find(chesss.getData(), target);
        count += finder.find(chesss.getDataT(), target);
        count += finder.find(chesss.getLeft(), target);
        count += finder.find(chesss.getRight(), target);
        return count;
    }

    public static void main(String[] args) {
        SimpleEvaluation evaluation = new SimpleEvaluation();
        MatrixEvaluation matrixEvaluation = new MatrixEvaluation();
        Chess chess = Chess.fromDate(new int[][]{
                {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 1, -1, 1, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},

        });
        long st = System.currentTimeMillis();
        for(int i = 0;i<100000;i++){
            matrixEvaluation.eval(chess,1);
        }
        System.out.println(System.currentTimeMillis()-st);
    }
}
