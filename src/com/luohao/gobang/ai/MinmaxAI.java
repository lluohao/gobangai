package com.luohao.gobang.ai;

import com.luohao.gobang.ai.eval.Evaluation;
import com.luohao.gobang.ai.eval.SimpleEvaluation;
import com.luohao.gobang.ai.interceptor.AlphaInterceptor;
import com.luohao.gobang.ai.interceptor.Interceptor;
import com.luohao.gobang.ai.interceptor.SimpleDateInterceptor;
import com.luohao.gobang.ai.util.ResultNodeUtils;
import com.luohao.gobang.chess.Chess;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by llhao on 2017/4/23.
 */
public class MinmaxAI implements AI {
    private Evaluation evaluation = new SimpleEvaluation();
    private List<Interceptor> interceptors = new ArrayList<>();

    public MinmaxAI() {
        interceptors.add(new SimpleDateInterceptor());
        interceptors.add(new AlphaInterceptor());
    }

    private void countNode(ResultNode base, int type, int deep) {
        if (deep == 0) {
            base.setScore(evaluation.eval(base.getChess(), type));
        } else {
            //计算此节点的子节点的评价值
            countChildren(base, type, deep);
            List<ResultNode> resultNodes = base.isMax() ? ResultNodeUtils.maxNodes(base.getChildren()) : ResultNodeUtils.minNodes(base.getChildren());
            ResultNode node = resultNodes.get((int) (Math.random() * resultNodes.size()));
            base.setScore(node.getScore());
            base.setNext(node);
        }
    }

    private void countChildren(ResultNode base, int type, int deep) {
        List<ResultNode> children = new ArrayList<>();
        Chess chess = base.getChess();
        for (int i = 0; i < 15; i++) {
            for (int j = 0; j < 15; j++) {
                if (intercept(base, i, j)) {//如果这个位置可以下棋
                    chess.play(j, i, -base.getType());
                    ResultNode node = new ResultNode();
                    node.setX(j);
                    node.setY(i);
                    node.setMax(!base.isMax());
                    node.setParent(base);
                    node.setChess(chess);
                    node.setType(-base.getType());
                    countNode(node, type, deep - 1);
                    children.add(node);
                    chess.play(j, i, 0);
                }
            }
        }
        base.setChildren(children);
    }

    private boolean intercept(ResultNode node, int i, int j) {
        for (Interceptor interceptor : interceptors) {
            if (!interceptor.intercept(node, i, j)) {
                return false;
            }
        }
        return true;
    }

    public ResultNode next(Chess chess, int type, int deep) {
        ResultNode node = new ResultNode();
        node.setType(-type);
        node.setMax(true);
        node.setChess(chess);
        countNode(node, type, deep);
        return node.getNext();
    }
}
