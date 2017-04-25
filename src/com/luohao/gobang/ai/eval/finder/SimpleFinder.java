package com.luohao.gobang.ai.eval.finder;

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

    public static void main(String[] args) {
        SimpleFinder finder = new SimpleFinder();
        int[] sqe = new int[225];
        int[] target = new int[6];
        long st = System.currentTimeMillis();
        for (int i = 0; i < 1000000; i++) {
            finder.find(sqe, target);
        }
        System.out.println(System.currentTimeMillis()-st);
    }
}
