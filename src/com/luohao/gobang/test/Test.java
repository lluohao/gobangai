package com.luohao.gobang.test;

import com.luohao.gobang.ai.MinmaxAI;
import com.luohao.gobang.ai.ResultNode;
import com.luohao.gobang.ai.eval.DynamicEvaluation;
import com.luohao.gobang.ai.util.ResultNodeUtils;
import com.luohao.gobang.chess.DynamicChess;
import com.luohao.gobang.utils.Matrixs;

import java.util.Scanner;

/**
 * Created by llhao on 2017/4/23.
 */
public class Test {
    public static void main(String[] args) {
        DynamicEvaluation evaluation = new DynamicEvaluation();
        DynamicChess chess = DynamicChess.fromDate(new int[][]{
                {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0,0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 1, -1, 1, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},

        },evaluation);
        MinmaxAI ai = new MinmaxAI();
        Scanner in = new Scanner(System.in);
        for (int i = 0; i < 100; i++) {
            ai.clearMap();
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
//                System.out.print("input the next : ");
//                int x = in.nextInt();
//                int y = in.nextInt();
//                chess.play(x, y, 1);
                next = ai.next(chess, 1, 4);
                chess.play(next.getX(), next.getY(), 1);
                System.out.println(next.getX() + "," + next.getY() + "," + next.getScore()+",搜索节点数："+ ResultNodeUtils.countChildren(next.getParent()));
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
