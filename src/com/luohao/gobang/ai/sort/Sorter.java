package com.luohao.gobang.ai.sort;

import com.luohao.gobang.chess.Chess;
import com.luohao.gobang.chess.Point;

import java.util.List;

/**
 * Created by Everthing-- on 2017/5/5.
 */
public interface Sorter {
    List<Point> buildPoints(Chess chess);
}
