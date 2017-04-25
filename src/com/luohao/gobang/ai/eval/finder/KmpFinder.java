package com.luohao.gobang.ai.eval.finder;

/**
 * Created by llhao on 2017/4/22.
 */
public class KmpFinder implements Finder {
    public int find(int[] sqe, int[] target) {
        int[] next = getNext(target);
        int count = 0;
        for (int i = 0; i <= sqe.length - target.length; ) {
            boolean flag = true;
            int j = 0;
            for (; j < target.length; j++) {
                if (sqe[i + j] != target[j]) {
                    flag = false;
                    break;
                }
            }
            if (flag) {
                count++;
                i++;
            } else {
                i += j - next[j];
            }
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

    public static void main(String[] args) {
        KmpFinder finder = new KmpFinder();
        System.out.println(finder.find(new int[]{1,2,3,4,5,6,7,1,0,1,1,1,2,3,4,1,0,1,1,1},new int[]{1,0,1,1,1}));
    }
}
