package com.luohao.gobang.ai.interceptor;

import com.luohao.gobang.ai.ResultNode;

/**
 * Created by llhao on 2017/4/26.
 */
public class PositionInterceptor implements Interceptor {
    @Override
    public boolean intercept(ResultNode node, int x, int y) {
        int[][] data = node.getChess().getSquare();
        int sum = 0;
        for (int i = -3; i <= 3; i++) {
            for (int j = -3; j <= 3; j++) {
                if ((i + x) >= 0 && (i + x) < 15 && (y + j) >= 0 && (y + j) < 15 && data[y + j][x + i] != 0) {
                    sum++;
                }
            }
        }
        return sum != 0;
    }
}
