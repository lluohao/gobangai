package com.luohao.gobang.ai.util;

import com.luohao.gobang.ai.ResultNode;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by llhao on 2017/4/23.
 */
public class ResultNodeUtils {
    public static List<ResultNode> serchNode(List<ResultNode> nodes, int p) {
        int max = Integer.MIN_VALUE;
        for (int i = 1; i < nodes.size(); i++) {
            if (nodes.get(i).getScore() * p > max * p) {
                max = nodes.get(i).getScore();
            }
        }
        List<ResultNode> maxNodes = new ArrayList<>();
        for (int i = 0; i < nodes.size(); i++) {
            if (nodes.get(i).getScore() == max) {
                maxNodes.add(nodes.get(i));
            }
        }
        return maxNodes;
    }

    public static List<ResultNode> maxNodes(List<ResultNode> nodes) {
        return serchNode(nodes, 1);
    }

    public static List<ResultNode> minNodes(List<ResultNode> nodes) {
        return serchNode(nodes, -1);
    }
}
