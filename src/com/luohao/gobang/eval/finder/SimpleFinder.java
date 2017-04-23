package com.luohao.gobang.eval.finder;

/**
 * Created by llhao on 2017/4/22.
 */
public class SimpleFinder implements Finder {
    public int find(int[] sqe, int[] target) {
        int count = 0;
        for (int i = 0; i < sqe.length - target.length + 1; i++) {
            boolean flag = true;
            for (int j = 0; j < target.length; j++) {
                if (sqe[i + j] != target[j]) {
                    flag = false;
                    break;
                }
            }
            if (flag) {
                count++;
            }
        }
        return count;
    }
}
