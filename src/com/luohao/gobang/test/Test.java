package com.luohao.gobang.test;

import com.luohao.gobang.ai.MinmaxAI;
import com.luohao.gobang.ai.ResultNode;
import com.luohao.gobang.ai.eval.DynamicEvaluation;
import com.luohao.gobang.ai.record.ZobristRecord;
import com.luohao.gobang.ai.util.ResultNodeUtils;
import com.luohao.gobang.chess.DynamicChess;
import com.luohao.gobang.utils.Matrixs;

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
                {0, 0, 0, 0, 0, 0, 0, -1, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 1, -1, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
        },evaluation);
        MinmaxAI ai = new MinmaxAI();
        evaluation.setAttack(false);
        ResultNode next = null;
//        next = ai.next(chess,1,4);
//        System.out.println(ResultNodeUtils.deep(next)+"::::"+ResultNodeUtils.countChildren(next.getParent()));
//        System.out.println(next);
//        ZobristRecord record = ((ZobristRecord)ai.getRecord());
//        System.out.println(record.size()+"="+ResultNodeUtils.countChildren(next.getParent().getChildren().get(0)));
        for (int i = 0; i < 10; i++) {
            ZobristRecord.count=0;
            long start = System.currentTimeMillis();
            next = null;
            if(i%2==1){
                next = ai.next(chess, -1, 4);
                chess.play(next.getX(), next.getY(), -1);
                System.out.println("本次搜索用时："+(System.currentTimeMillis()-start)+"毫秒"+",搜索結點數："+ ResultNodeUtils.countChildren(next.getParent())+",置换表加速："+ZobristRecord.count);
                ResultNode temp = next;
                while (temp.getNext()!=null){
                    temp = temp.getNext();
                }
                System.out.println(temp);
            }else{
                next = ai.next(chess, 1, 4);
                chess.play(next.getX(), next.getY(), 1);
                System.out.println(next.getX() + "," + next.getY() + "," + next.getScore()+",搜索节点数："+ ResultNodeUtils.countChildren(next.getParent()));
            }
            ResultNode temp = next;
            while (temp.getNext()!=null){
                temp = temp.getNext();
            }
            System.out.println(next);
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
