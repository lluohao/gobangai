package com.luohao.gobang.ai.interceptor;

import com.luohao.gobang.ai.ResultNode;

/**
 * Created by llhao on 2017/4/24.
 */
public interface Interceptor {
    boolean intercept(ResultNode node, int i, int j);
}
