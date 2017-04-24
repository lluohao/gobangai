package com.luohao.gobang.ai.interceptor;

import com.luohao.gobang.ai.ResultNode;
import com.luohao.gobang.chess.Chess;

/**
 * Created by llhao on 2017/4/24.
 */
public class SimpleDateInterceptor implements Interceptor {
    @Override
    public boolean intercept(ResultNode node, int i, int j) {
        Chess chess = node.getChess();
        return chess.get(j, i) == 0;
    }
}
