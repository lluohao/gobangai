package com.luohao.gobang.ai.sort;

import com.luohao.gobang.ai.eval.DynamicEvaluation;
import com.luohao.gobang.chess.Chess;
import com.luohao.gobang.chess.DynamicChess;
import com.luohao.gobang.chess.Point;
import com.luohao.gobang.utils.Matrixs;

import java.util.*;

/**
 * Created by Everthing-- on 2017/5/5.
 */
public class ThreatSorter implements Sorter {
    private SimpleMatrixSorter simpleMatrixSorter = new SimpleMatrixSorter();
    @Override
    public List<Point> buildPoints(Chess c) {
        List<Point> points = new ArrayList<>();
        if(c instanceof DynamicChess){
            DynamicChess chess = (DynamicChess) c;
            Map<Point,Integer> maps = new HashMap<>();
            build(chess.getShapLine(),1,0,maps);
            build(chess.getShapRow(),0,1,maps);
            build(chess.getShapLeft(),1,-1,maps);
            build(chess.getShapRight(),1,1,maps);

            List<Point> all = simpleMatrixSorter.buildPoints(c);
            for (Point point : all) {
                Integer old = maps.get(point);
                if(old==null){
                    maps.put(point,point.getValue());
                }else {
                    point.setValue(Math.max(old,point.getValue()));
                    maps.put(point,point.getValue());
                }
            }
            points.addAll(maps.keySet());
            Collections.sort(points);
            return points;
        }
        return null;
    }

    public static final int[][] TYPE_BLACK = {
            {1, 1, 1, 1, 1}, {0, 1, 1, 1, 1, 0},
            {0, 1, 1, 1, 0, 0}, {0, 0, 1, 1, 1, 0}, {0, 1, 0, 1, 1, 0}, {0, 1, 1, 0, 1, 0},
            {1, 1, 1, 1, 0}, {0, 1, 1, 1, 1}, {1, 1, 0, 1, 1}, {1, 0, 1, 1, 1}, {1, 1, 1, 0, 1},
            {0, 0, 1, 1, 0, 0}, {0, 0, 1, 0, 1, 0}, {0, 1, 0, 1, 0, 0}, {0, 0, 0, 1, 0, 0}, {0, 0, 1, 0, 0, 0}
    };

    public static final int[] COUNT = {5,4,3,3,3,3,4,4,4,4,4,2,2,2,1,1};

    private void build(int[][] data,int dx,int dy,Map<Point,Integer> points){
        for(int y = 0;y<data.length;y++){
            for(int x = 0;x<data[y].length;x++){
                int value = Math.abs(data[y][x]);
                if(value==0||value==100){
                    continue;
                }
                int count = COUNT[value];
                for(int i = 0;i<TYPE_BLACK[value].length;i++){
                    if(TYPE_BLACK[value][i]==0){
                        Point point = new Point(x+dx*i,y+dy*i);
                        Integer old = points.get(point);
                        if(old==null){
                            points.put(point,count*1000);
                            point.setValue(count*1000);
                        }else{
                            points.put(point,Math.max(old,count*1000));
                            point.setValue(Math.max(old,count*1000));
                        }
                    }
                }
            }
        }
    }

    public static void main(String[] args) {
        DynamicEvaluation evaluation = new DynamicEvaluation();
        DynamicChess chess = DynamicChess.fromDate(new int[][]{
                {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 1, 1, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 1, 0, 1, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
        },evaluation);
        Matrixs.print(chess.getShapLeft());
        System.out.println();
        Sorter sorter = new ThreatSorter();
        List<Point> points = sorter.buildPoints(chess);
        Collections.sort(points, new Comparator<Point>() {
            @Override
            public int compare(Point o1, Point o2) {
                if(o1.getX()==o2.getX()){
                    return o1.getY()-o2.getY();
                }else {
                    return (o1.getX()-o2.getX())*15;
                }
            }
        });
        Matrixs.print(chess.getSquare());
        for (Point point : points) {
            System.out.println(point);
        }
    }

}
