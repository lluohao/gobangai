package com.luohao.gobang.ai.interceptor;

import com.luohao.gobang.ai.ResultNode;
import com.luohao.gobang.ai.eval.Evaluation;
import com.luohao.gobang.ai.eval.MatrixEvaluation;

/**
 * Created by luohao on 2017/4/24.
 */
public class WorthInterceptor implements Interceptor {
    private Evaluation evaluation = new MatrixEvaluation();
    private double myp;
    private double otp;

    public WorthInterceptor(double myp, double otp) {
        this.myp = myp;
        this.otp = otp;
    }

    @Override
    public boolean intercept(ResultNode node, int i, int j) {
        int myOld = evaluation.absEval(node.getChess(), -node.getType());
        int otOld = evaluation.absEval(node.getChess(), node.getType());
        node.getChess().play(j, i, -node.getType());
        int myNew = evaluation.absEval(node.getChess(), -node.getType());
        int otNew = evaluation.absEval(node.getChess(), node.getType());
        node.getChess().play(j, i, 0);
        double mp = (myNew + 0.0) / myOld;
        double op = (otNew + 0.0) / otOld;
        return mp >= myp || op < otp;
    }
}
