package com.luohao.gobang.chess;

/**
 * Created by llhao on 2017/4/26.
 */
public class ChessNode {
    private int x;
    private int y;
    private int type;
    private ChessNode next;

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
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

    public void setType(int type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return "ChessNode{" +
                "x=" + x +
                ", y=" + y +
                ", type=" + type +
                '}';
    }

    public ChessNode getNext() {
        return next;
    }

    public void setNext(ChessNode next) {
        this.next = next;
    }
}
