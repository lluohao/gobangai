package com.luohao.gobang.ai.util;

import com.luohao.gobang.ai.ResultNode;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by llhao on 2017/4/23.
 */
public class ResultNodeUtils {

    public static int deep(ResultNode node){
        if(node==null){
            return 0;
        }
        List<ResultNode> nodes = node.getChildren();
        if(nodes==null||nodes.size()==0){
            return 1;
        }
        int max = 0;
        for (ResultNode resultNode : nodes) {
            int temp = deep(resultNode);
            if(temp>max){
                max = temp;
            }
        }
        return max+1;
    }

    public static List<ResultNode> serchNode(List<ResultNode> nodes, int p) {
        int max = Integer.MIN_VALUE;
        for (int i = 0; i < nodes.size(); i++) {
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

    public static ResultNode serchOneNode(List<ResultNode> nodes, int p) {
        int max = Integer.MIN_VALUE;
        ResultNode node = nodes.get(0);
        for (int i = 1; i < nodes.size(); i++) {
            if (nodes.get(i).getScore() * p > max * p) {
                max = nodes.get(i).getScore();
                node = nodes.get(i);
            }
        }
        return node;
    }

    public static ResultNode maxNode(List<ResultNode> nodes) {
        return serchOneNode(nodes, 1);
    }

    public static ResultNode minNode(List<ResultNode> nodes) {
        return serchOneNode(nodes, -1);
    }

    public static List<ResultNode> maxNodes(List<ResultNode> nodes) {
        return serchNode(nodes, 1);
    }

    public static List<ResultNode> minNodes(List<ResultNode> nodes) {
        return serchNode(nodes, -1);
    }

    public static int countChildren(ResultNode root) {
        List<ResultNode> children = root.getChildren();
        if (children == null) {
            return 0;
        }
        int count = children.size();
        for (ResultNode child : children) {
            count += countChildren(child);
        }
        return count;
    }
}
