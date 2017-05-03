package com.luohao.gobang.ai.eval;

import com.luohao.gobang.chess.Chess;
import com.luohao.gobang.chess.ChessNode;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Everthing-- on 2017/4/28.
 */
public class Feature {
    private int x;
    private int y;
    private int size = 6;
    private int type;
    private int dx;
    private int dy;
    public boolean contains(int x,int y){
        int len = dx==0?y-this.y:x-this.x;
        if(len<0||len>=size){
            return false;
        }
        return dx*y == dy*x+this.y*dx-this.x*dy;
    }

    public List<ChessNode> noChessPosition(Chess chess){
        List<ChessNode> points = new ArrayList<>();
        for(int i = 0;i<size;i++){
            if(chess.get(x+dx*i,y+dy*i)==0){
                points.add(new ChessNode(x+dx*i,y+dy*i));
            }
        }
        return points;
    }

    public static void main(String[] args) {
        Feature feature = new Feature();
        feature.setX(6);
        feature.setY(7);
        feature.setDx(1);
        feature.setDy(-1);
        for(int i = 0;i<20;i++) {
            for(int j = 0;j<20;j++) {
                if(feature.contains(i, j)){
                    System.out.println(i+","+j);
                }
            }
        }
    }

    public int getDx() {
        return dx;
    }

    public int getDy() {
        return dy;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getSize() {
        return size;
    }

    public int getType() {
        return type;
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public void setType(int type) {
        this.type = type;
    }

    public void setDx(int dx) {
        this.dx = dx;
    }

    public void setDy(int dy) {
        this.dy = dy;
    }
}
