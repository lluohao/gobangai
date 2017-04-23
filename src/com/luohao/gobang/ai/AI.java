package com.luohao.gobang.ai;

import com.luohao.gobang.chess.Chess;

/**
 * Created by llhao on 2017/4/23.
 */
public interface AI {
    ResultNode next(Chess chess, int type, int deep);
}
