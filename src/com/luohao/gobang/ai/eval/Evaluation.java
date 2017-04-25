package com.luohao.gobang.ai.eval;

import com.luohao.gobang.chess.Chess;

/**
 * Created by llhao on 2017/4/22.
 */
public interface Evaluation {
    int eval(Chess chess, int type);

    int absEval(Chess chess, int type);

    int eval(Chess chess, int type, int next);
}
