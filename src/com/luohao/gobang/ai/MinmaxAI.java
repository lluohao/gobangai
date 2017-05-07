package com.luohao.gobang.ai;

import com.luohao.gobang.ai.eval.DynamicEvaluation;
import com.luohao.gobang.ai.interceptor.AlphaInterceptor;
import com.luohao.gobang.ai.interceptor.Interceptor;
import com.luohao.gobang.ai.interceptor.PositionInterceptor;
import com.luohao.gobang.ai.interceptor.SimpleDateInterceptor;
import com.luohao.gobang.ai.record.Record;
import com.luohao.gobang.ai.record.ZobristRecord;
import com.luohao.gobang.ai.sort.Sorter;
import com.luohao.gobang.ai.sort.ThreatSorter;
import com.luohao.gobang.ai.util.ResultNodeUtils;
import com.luohao.gobang.chess.Chess;
import com.luohao.gobang.chess.Point;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by llhao on 2017/4/23.
 */
public class MinmaxAI implements AI {
    private DynamicEvaluation evaluation = new DynamicEvaluation();//估值
    private List<Interceptor> interceptors = new ArrayList<>();//过滤器
    private Sorter sorter = new ThreatSorter();//排序
    private Record record = new ZobristRecord();
    public MinmaxAI() {
        interceptors.add(new SimpleDateInterceptor());
        //interceptors.add(new TimeInterceptor(6000));
        interceptors.add(new AlphaInterceptor());
        //interceptors.add(new WorthInterceptor(1.1, 0.9));
        interceptors.add(new PositionInterceptor());
    }

    public DynamicEvaluation getEvaluation() {
        return evaluation;
    }

    public List<Interceptor> getInterceptors() {
        return interceptors;
    }

    public Sorter getSorter() {
        return sorter;
    }

    public Record getRecord() {
        return record;
    }

    public long getStart() {
        return start;
    }
    private long start = System.currentTimeMillis();
    private void countNode(ResultNode base, int type, int deep) {
        //System.out.println(base);
        if (deep == 0) {
//            if(System.currentTimeMillis()-start>100){
//                ResultNode temp = base;
//                while(temp.getParent()!=null){
//                    temp = temp.getParent();
//                }
//                System.out.println("NOW:"+ResultNodeUtils.countChildren(temp)+":"+ZobristRecord.count);
//                start=System.currentTimeMillis();
//            }
            base.setScore(evaluation.eval(base.getChess(), type, -base.getType()));
            record.addNode(base.getChess(),base.getScore());
        } else {
            //计算此节点的子节点的评价值
            countChildren(base, type, deep);
            List<ResultNode> resultNodes = base.isMax() ? ResultNodeUtils.maxNodes(base.getChildren()) : ResultNodeUtils.minNodes(base.getChildren());
            if (resultNodes.size() == 0) {
                if(base.getScore()==1) {
                    base.setScore(evaluation.eval(base.getChess(), type));
                }
            } else {
                ResultNode node = resultNodes.get((int) (Math.random() * resultNodes.size()));
                base.setScore(node.getScore());
                base.setNext(node);
            }
        }
    }
    private void countChildren(ResultNode base, int type, int deep) {
        List<ResultNode> children = new ArrayList<>();
        Chess chess = base.getChess();
        base.setChildren(children);
        List<Point> points = sorter.buildPoints(chess);
        for (Point point : points) {
            int y = point.getY();
            int x = point.getX();
            if (intercept(base, x, y)) {//如果这个位置可以下棋
                chess.play(x, y, -base.getType());
                ResultNode node = new ResultNode();
                node.setX(x);
                node.setY(y);
                node.setMax(!base.isMax());
                node.setParent(base);
                node.setChess(chess);
                node.setType(-base.getType());
                Integer re = record.find(node);
                if(re==null) {
                    countNode(node, type, deep - 1);
                    record.addNode(chess,node.getScore());
                    //System.out.println("ADD:"+node+"::::"+node.getChess().hashCode());
                }else{
                    ZobristRecord.count++;
                    node.setScore(re);
                    //System.out.println("FID:"+node+"::::"+node.getChess().hashCode());
                    //System.exit(0);
                }
                children.add(node);
                chess.play(x, y, 0);
            }
        }
        base.setChildren(children);
    }

    private boolean intercept(ResultNode node, int x, int y) {
        for (Interceptor interceptor : interceptors) {
            if (!interceptor.intercept(node, x, y)) {
                return false;
            }
        }
        return true;
    }

    public ResultNode next(Chess chess, int type, int deep) {
        ResultNode node = new ResultNode();
        node.setType(-type);
        node.setMax(true);
        node.setChess(chess);
        countNode(node, type, deep);
        return node.getNext();
    }
}
