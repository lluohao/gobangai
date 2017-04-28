package com.luohao.gobang.ai.eval;

import com.luohao.gobang.chess.Chess;

/**
 * Created by llhao on 2017/4/26.
 */
public class MatrixEvaluation implements Evaluation {

    public static final int[] SHAP_SCORE = {50000, 4320, 720, 720, 720, 720, 720, 720, 720, 720, 720, 120, 120, 120, 5, 5};

    @Override
    public int eval(Chess chess, int type) {
        int[][] myType = type == 1 ? TYPE_BLACK : TYPE_WHITE;
        int[][] otType = type == 1 ? TYPE_WHITE : TYPE_BLACK;
        int[] myCount = new int[myType.length];
        int[] otCount = new int[otType.length];
        for (int i = 0; i < myCount.length; i++) {
            myCount[i] = find(chess.getSquare(), myType[i]);
            otCount[i] = find(chess.getSquare(), otType[i]);
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
            myCount[i] = find(chess.getSquare(), myType[i]);
        }
        ;
        int myScore = 0;
        for (int i = 0; i < myCount.length; i++) {
            myScore += myCount[i] * SHAP_SCORE[i];
        }
        return myScore;
    }

    private int find(int[][] data, int[] target) {
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

    private int findAll(int[][] data,int[] target){
        return 0;
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

    @Override
    public int eval(Chess chess, int type, int next) {
        int[][] type_me = type==1?TYPE_BLACK:TYPE_WHITE;
        int[][] type_ot = type==1?TYPE_WHITE:TYPE_BLACK;
        int[][] data = chess.getSquare();
        if(next==type){
            int myFive = find(data,type_me[0]);
            if(myFive>0){
                return 100000;
            }
            int otFive = find(data,type_ot[0]);
            if(otFive>0){
                return -100000;
            }
            int myFour = find(data,type_me[1])+find(data,type_me[6])+find(data,type_me[7])+find(data,type_me[8])+find(data,type_me[9])+find(data,type_me[10]);
            if(myFour>0){
                return 10000;
            }
            int otFour = find(data,type_ot[1]);
            if(otFour>0){
                return -10000;
            }

            int otFour2 = find(data,type_ot[6])+find(data,type_ot[7])+find(data,type_ot[8])+find(data,type_ot[9])+find(data,type_ot[10]);
            int otThree = find(data,type_ot[2])+find(data,type_ot[3])+find(data,type_ot[4])+find(data,type_ot[5]);
            if(otFour2>0&&otThree>0){
                return -10000;
            }
            if(otFour2>0){
                return -720;
            }
            int myThree = find(data,type_me[1])+find(data,type_me[2])+find(data,type_me[3])+find(data,type_me[4])+find(data,type_me[5]);
            if(myThree>0){
                return 4320;
            }
            if(otThree>1){
                return -4320;
            }
            if(otThree>0){
                return -720;
            }
            int myTwo = find(data,type_me[11])+find(data,type_me[12])+find(data,type_me[13]);
            int myOne = find(data,type_me[14])+find(data,type_me[15]);

            int otTwo = find(data,type_ot[11])+find(data,type_ot[12])+find(data,type_ot[13]);
            int otOne = find(data,type_ot[14])+find(data,type_ot[15]);
            int myScore = myTwo*20+myOne;
            int otScore = otTwo*20+otOne;
            return myScore>otScore?myScore:otScore;
        }else{
            return -eval(chess,-type,next);
        }
    }

    @Override
    public int win(Chess chess) {
        if(find(chess.getSquare(),TYPE_BLACK[0])>0){
            return 1;
        }else if(find(chess.getSquare(),TYPE_WHITE[0])>0){
            return -1;
        }
        return 0;
    }

    public static void main(String[] args) {
        MatrixEvaluation matrixEvaluation = new MatrixEvaluation();
        Chess chess = Chess.fromDate(new int[][]{
                {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},

        });
        System.out.println(matrixEvaluation.eval(chess, 1,-1));
    }
}
