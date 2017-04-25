package com.luohao.gobang.ai;

import com.luohao.gobang.chess.Chess;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by llhao on 2017/4/23.
 */
public class ResultNode {
    private int x;
    private int y;
    private int type;
    private Integer score;
    private ResultNode parent;
    private List<ResultNode> children = new ArrayList<>();
    private boolean max;
    private Chess chess;
    private ResultNode next;

    public void setNext(ResultNode next) {
        this.next = next;
    }

    public void setChess(Chess chess) {
        this.chess = chess;
    }

    public void setMax(boolean max) {
        this.max = max;
    }

    public void setType(int type) {
        this.type = type;
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }

    public void setScore(Integer score) {
        this.score = score;
    }

    public void setParent(ResultNode parent) {
        this.parent = parent;
    }

    public void setChildren(List<ResultNode> children) {
        this.children = children;
    }

    public boolean isMax() {
        return max;
    }

    public ResultNode getNext() {
        return next;
    }

    public Chess getChess() {
        return chess;
    }

    public int getType() {
        return type;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public Integer getScore() {
        return score;
    }

    public ResultNode getParent() {
        return parent;
    }

    public List<ResultNode> getChildren() {
        return children;
    }
}
