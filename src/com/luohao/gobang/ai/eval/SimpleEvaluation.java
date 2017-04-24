package com.luohao.gobang.ai.eval;

import com.luohao.gobang.chess.Chess;
import com.luohao.gobang.ai.eval.finder.SimpleFinder;

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

    public static final int[] SHAP_SCORE = {50000, 4320, 720, 720, 720, 720, 720, 720, 720, 720, 720, 120, 120, 120, 1, 1};

    private SimpleFinder finder = new SimpleFinder();

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
        }else if(otCount[0]>0){
            return -100000;
        }else if(myCount[1]>0){

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
}
