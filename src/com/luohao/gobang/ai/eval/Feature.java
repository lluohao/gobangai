package com.luohao.gobang.ai.eval;

import java.awt.*;

/**
 * Created by Everthing-- on 2017/4/28.
 */
public class Feature {
    private Point start;
    private Point end;
    private int type;

    public Point getStart() {
        return start;
    }

    public Point getEnd() {
        return end;
    }

    public int getType() {
        return type;
    }

    public void setStart(Point start) {
        this.start = start;
    }

    public void setStart(int x, int y) {
        setStart(new Point(x, y));
    }

    public void setEnd(Point end) {
        this.end = end;
    }

    public void setEnd(int x, int y) {
        setEnd(new Point(x, y));
    }

    public void setType(int type) {
        this.type = type;
    }
}
