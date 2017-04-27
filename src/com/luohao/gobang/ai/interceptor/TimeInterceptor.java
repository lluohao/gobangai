package com.luohao.gobang.ai.interceptor;

import com.luohao.gobang.ai.ResultNode;

/**
 * Created by llhao on 2017/4/26.
 */
public class TimeInterceptor implements Interceptor {
    private long start;
    private int millis;

    public TimeInterceptor(int mills) {
        this.millis = mills;
        start = System.currentTimeMillis();
    }

    @Override
    public boolean intercept(ResultNode node, int i, int j) {
        long end = System.currentTimeMillis();
        return end - start <= millis;
    }

}
