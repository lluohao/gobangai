package com.luohao.gobang.ai.eval;

import com.luohao.gobang.chess.Chess;
import com.luohao.gobang.chess.DynamicChess;
import com.luohao.gobang.utils.Matrixs;

/**
 * Created by llhao on 2017/4/26.
 */
public class DynamicEvaluation extends MatrixEvaluation {

    private boolean attack = false;
    public void setAttack(boolean attack) {
        this.attack = attack;
    }
    public static final int[] SHAP_SCORE = {50000, 4320, 720, 720, 720, 720, 720, 720, 720, 720, 720, 120, 120, 120, 5, 5};
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

    public static final int[] TYPE_COUNT = new int[TYPE_BLACK.length];
    static {
        for (int i = 0; i < TYPE_BLACK.length; i++) {
            TYPE_COUNT[i] = Matrixs.count(TYPE_BLACK[i]);
        }
    }


    @Override
    public int eval(Chess c, int type, int next) {
        if(c instanceof DynamicChess){
            DynamicChess chess = (DynamicChess)c;
            int[] myCount = new int[TYPE_BLACK.length];
            int[] otCount = new int[TYPE_WHITE.length];
            count(chess,myCount,otCount,type);
            int myScore = 0;
            int otScore = 0;
            for (int i = 0; i < myCount.length; i++) {
                myScore += myCount[i] * SHAP_SCORE[i];
                otScore += otCount[i] * SHAP_SCORE[i];
            }
            int p = isPositive(myCount,otCount,type,next);
            if(p==0) {
                if(attack) {
                    return myScore > otScore ? myScore : myScore == otScore ? 0 : -otScore;
                }else {
                    return -otScore;
                }
            }else if(p==1){
                return myScore;
            }else {
                return -otScore;
            }
        }else{
            throw new RuntimeException("chess must be DynamicChess");
        }
    }

    public void count(DynamicChess chess,int[] mycount,int[] otcount,int type){
        count(chess.getShapLine(),mycount,otcount,type);
        count(chess.getShapRow(),mycount,otcount,type);
        count(chess.getShapLeft(),mycount,otcount,type);
        count(chess.getShapRight(),mycount,otcount,type);
    }

    private void count(int[][] data,int[] mycount,int[] otcount,int type){
        for (int[] datum : data) {
            for (int i : datum) {
                if(i==0){
                    continue;
                }else if(i*type==100){
                    mycount[0]++;
                }else if(i*type==-100){
                    otcount[0]++;
                }else if((i>0&&type==1)||(i<0&&type==-1)){
                    mycount[i*type]++;
                }else{
                    otcount[-i*type]++;
                }
            }
        }
    }

    public int shapType(Chess chess,int x,int y,int dx,int dy){
        for (int i = 0; i < TYPE_BLACK.length; i++) {
            boolean flag = true;
            for(int j = 0; j < TYPE_BLACK[i].length;j++){
                if((x+dx*j)>14||(y+dy*j>14)||(x+dx*j)<0||(y+dy*j<0)){
                    flag = false;
                    break;
                }
                if(TYPE_BLACK[i][j]!=chess.get(x+dx*j,y+dy*j)){
                    flag = false;
                    break;
                }
            }
            if(flag){
                if(i==0){
                    return 100;
                }
                return i;
            }
            flag = true;
            for(int j = 0; j < TYPE_WHITE[i].length;j++){
                if((x+dx*j)>14||(y+dy*j>14)||(x+dx*j)<0||(y+dy*j<0)){
                    flag = false;
                    break;
                }
                ;
                if(TYPE_WHITE[i][j]!=chess.get(x+dx*j,y+dy*j)){
                    flag = false;
                    break;
                }
            }
            if(flag){
                if(i==0){
                    return -100;
                }
                return -i;
            }
        }
        return 0;
    }

    public static void main(String[] args) {
        DynamicEvaluation evaluation = new DynamicEvaluation();
        MatrixEvaluation evaluation1 = new MatrixEvaluation();
        DynamicChess chess = DynamicChess.fromDate(new int[][]{
                {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 1, 1, 1, 1, 1, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},

        },evaluation);
        int score = evaluation.eval(chess,1,1);
        int score1 = evaluation1.eval(chess,1,1);
        System.out.println(score);
        System.out.println(score1);
        System.out.println();
    }
}
