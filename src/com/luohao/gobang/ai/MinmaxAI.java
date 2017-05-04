package com.luohao.gobang.ai;

import com.luohao.gobang.ai.eval.DynamicEvaluation;
import com.luohao.gobang.ai.interceptor.AlphaInterceptor;
import com.luohao.gobang.ai.interceptor.Interceptor;
import com.luohao.gobang.ai.interceptor.PositionInterceptor;
import com.luohao.gobang.ai.interceptor.SimpleDateInterceptor;
import com.luohao.gobang.ai.util.ResultNodeUtils;
import com.luohao.gobang.chess.Chess;

import java.util.*;

/**
 * Created by llhao on 2017/4/23.
 */
public class MinmaxAI implements AI {
    private DynamicEvaluation evaluation = new DynamicEvaluation();
    private List<Interceptor> interceptors = new ArrayList<>();
    private Map<String,Integer> scoreMap = new HashMap<>();
    public MinmaxAI() {
        interceptors.add(new SimpleDateInterceptor());
        //interceptors.add(new TimeInterceptor(6000));
        interceptors.add(new AlphaInterceptor());
        //interceptors.add(new WorthInterceptor(1.1, 0.9));
        interceptors.add(new PositionInterceptor());
    }

    private void countNode(ResultNode base, int type, int deep) {
        if (deep == 0) {
            base.setScore(evaluation.eval(base.getChess(), type, -base.getType()));
            scoreMap.put(base.getChess().hashString(),base.getScore());
        } else {
            //计算此节点的子节点的评价值
            countChildren(base, type, deep);
            List<ResultNode> resultNodes = base.isMax() ? ResultNodeUtils.maxNodes(base.getChildren()) : ResultNodeUtils.minNodes(base.getChildren());
            if (resultNodes.size() == 0) {
                base.setScore(evaluation.eval(base.getChess(), type));
            } else {
                ResultNode node = resultNodes.get((int) (Math.random() * resultNodes.size()));
                base.setScore(node.getScore());
                base.setNext(node);
            }
        }
    }
    public static int count = 0;
    private boolean serchFromMap(ResultNode node){
        Chess chess = node.getChess();
        String key = chess.hashString();
        Integer value = scoreMap.get(key);
        if(value==null){
//            System.out.println("add:"+node+"---:::::"+key);
//            Matrixs.print(node.getChess().getSquare());
//            System.out.println();
            return false;
        }else {
            node.setScore(value);
//            System.out.println(node+"---:::::"+key);
//            Matrixs.print(node.getChess().getSquare());
//            System.out.println();
            count++;
            return true;
        }
    }

    private void countChildren(ResultNode base, int type, int deep) {
        List<ResultNode> children = new ArrayList<>();
        Chess chess = base.getChess();
        base.setChildren(children);
        List<Point> points = buildPoints(chess);
        for (Point point : points) {
            int i = point.x;
            int j = point.y;
            if (intercept(base, i, j)) {//如果这个位置可以下棋
                chess.play(j, i, -base.getType());
                ResultNode node = new ResultNode();
                node.setX(j);
                node.setY(i);
                node.setMax(!base.isMax());
                node.setParent(base);
                node.setChess(chess);
                node.setType(-base.getType());
                if(!serchFromMap(node)) {
                    countNode(node, type, deep - 1);
                    scoreMap.put(node.getChess().hashString(),node.getScore());
                }
                children.add(node);
                chess.play(j, i, 0);
            }
        }
        base.setChildren(children);
    }

    private List<Point> buildPoints(Chess chess) {
        List<Point> points = new ArrayList<>();
        int[][] data = chess.getSquare();
        for (int i = 0; i < data.length; i++) {
            for (int j = 0; j < data[i].length; j++) {
                Point point = new Point(j, i);
                int sum = 0;
                for (int l = -1; l < 2; l++) {
                    for (int k = -1; k < 2; k++) {
                        if ((i + l) >= 0 && (i + l) < 15 && (j + k) >= 0 && (j + k) < 15 && data[i + l][j + k] != 0) {
                            sum++;
                        }
                    }
                }
                point.value = sum;
                points.add(point);
            }
        }
        Collections.sort(points);
        return points;
    }

    private boolean intercept(ResultNode node, int i, int j) {
        for (Interceptor interceptor : interceptors) {
            if (!interceptor.intercept(node, i, j)) {
                return false;
            }
        }
        return true;
    }

    public int mapSize(){
        return scoreMap.size();
    }

    public void clearMap(){
        scoreMap.clear();
    }

    public ResultNode next(Chess chess, int type, int deep) {
        ResultNode node = new ResultNode();
        node.setType(-type);
        node.setMax(true);
        node.setChess(chess);
        countNode(node, type, deep);
        return node.getNext();
    }

    private class Point implements Comparable<Point> {
        private int x;
        private int y;
        private int value;

        public Point(int x, int y) {
            this.x = x;
            this.y = y;
        }

        @Override
        public int compareTo(Point o) {
            return o.value - this.value;
        }
    }

}
