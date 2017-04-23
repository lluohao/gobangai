package com.luohao.gobang.test;

import com.luohao.gobang.ai.AI;
import com.luohao.gobang.ai.MinmaxAI;
import com.luohao.gobang.ai.ResultNode;
import com.luohao.gobang.chess.Chess;
import com.luohao.gobang.eval.Evaluation;
import com.luohao.gobang.eval.SimpleEvaluation;
import com.luohao.gobang.utils.Matrixs;

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
        Evaluation evaluation = new SimpleEvaluation();
        AI ai = new MinmaxAI();
        for (int i = 0; i < 20; i++) {
            ResultNode next = ai.next(chess, i % 2 == 0 ? -1 : 1, 2);
            chess.play(next.getX(), next.getY(), i % 2 == 0 ? -1 : 1);
            System.out.println(next.getX() + "," + next.getY() + "," + next.getScore());
            Matrixs.print(chess.getSquare());
        }
    }
}
