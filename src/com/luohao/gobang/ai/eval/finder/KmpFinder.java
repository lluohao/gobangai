package com.luohao.gobang.ai.eval.finder;
import java.util.Arrays;

/**
 * Created by llhao on 2017/4/22.
 */
public class KmpFinder implements Finder {
    public int find(int[] sqe, int[] target) {
        int[] next = getNext(target);
        int count = 0;
        System.out.println(Arrays.toString(next));
        out:for (int i = 0; i <= sqe.length - target.length;) {
            for (int j = 0; j < target.length; j++) {
                if (sqe[i] != target[j]) {
                    i+=j-next[j];
                    continue out;
                }
            }
            count++;
            i+=target.length;
        }
        return count;
    }

    private int[] getNext(int[] t) {
        int[] next = new int[t.length];
        next[0] = -1;
        int suffix = 0;  // 后缀
        int prefix = -1;  // 前缀
        while (suffix < t.length - 1) {
            //若前缀索引为-1或相等，则前缀后缀索引均+1
            if (prefix == -1 || t[prefix] == t[suffix]) {
                ++prefix;
                ++suffix;
                next[suffix] = prefix;  //1
            } else {
                prefix = next[prefix];  //2
            }
        }
        return next;
    }
}
