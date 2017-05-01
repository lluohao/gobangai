package com.luohao.gobang.test;

import com.luohao.gobang.ai.MinmaxAI;
import com.luohao.gobang.ai.ResultNode;
import com.luohao.gobang.ai.eval.Evaluation;
import com.luohao.gobang.ai.eval.MatrixEvaluation;
import com.luohao.gobang.ai.util.ResultNodeUtils;
import com.luohao.gobang.chess.Chess;
import com.luohao.gobang.utils.Matrixs;

import java.util.Scanner;

/**
 * Created by llhao on 2017/4/23.
 */
public class Test {
    public static void main(String[] args) {
        Chess chess = Chess.fromDate(new int[][]{
                {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},

        });
        chess.play(8,8,1);
        chess.play(7,7,-1);
        chess.play(9,7,1);
        MinmaxAI ai = new MinmaxAI();
        Scanner in = new Scanner(System.in);
        Evaluation evaluation = new MatrixEvaluation();
        for (int i = 0; i < 100; i++) {
            MinmaxAI.count=0;
            long start = System.currentTimeMillis();
            ResultNode next = null;
            if(i%2==0){
                next = ai.next(chess, -1, 4);
//                System.out.print("input the next : ");
//                int x = in.nextInt();
//                int y = in.nextInt();
//                chess.play(x, y, -1);
                chess.play(next.getX(), next.getY(), -1);
                System.out.println("本次搜索用时："+(System.currentTimeMillis()-start)+"毫秒"+",搜索結點數："+ ResultNodeUtils.countChildren(next.getParent())+",置换表加速："+MinmaxAI.count);
                //System.out.println(next.getX() + "," + next.getY() + "," + next.getScore()+",搜索节点数："+ ResultNodeUtils.countChildren(next.getParent()));
            }else{
                System.out.print("input the next : ");
                int x = in.nextInt();
                int y = in.nextInt();
                chess.play(x, y, 1);
//                next = ai.next(chess, 1, 3);
//                chess.play(next.getX(), next.getY(), 1);
//                System.out.println(next.getX() + "," + next.getY() + "," + next.getScore()+",搜索节点数："+ ResultNodeUtils.countChildren(next.getParent()));
            }
            System.out.println(ai.mapSize());
            System.out.println("*\t0\t1\t2\t3\t4\t5\t6\t7\t8\t9\t10\t11\t12\t13\t14\t");
            Matrixs.print(chess.getNodes());
            System.out.println();
            if(evaluation.win(chess)==1){
                System.out.println("BLACK WIN");
                break;
            }else if(evaluation.win(chess)==-1){
                System.out.println("WHITE WIN");
                break;
            }
        }
    }
}
