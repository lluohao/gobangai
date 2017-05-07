package com.luohao.gobang.ai.record;

import com.luohao.gobang.ai.ResultNode;
import com.luohao.gobang.chess.Chess;

/**
 * Created by Everthing-- on 2017/5/5.
 */
public interface Record {
    void addNode(Chess chess,int score);
    Integer find(ResultNode node);
}
