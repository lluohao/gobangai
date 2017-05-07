package com.luohao.gobang.ai.sort;

import com.luohao.gobang.chess.Chess;
import com.luohao.gobang.chess.Point;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by Everthing-- on 2017/5/5.
 */
public class SimpleMatrixSorter implements Sorter {
    @Override
    public List<Point> buildPoints(Chess chess) {
        List<Point> points = new ArrayList<>();
        int[][] data = chess.getSquare();
        for (int i = 0; i < data.length; i++) {
            for (int j = 0; j < data[i].length; j++) {
                if(data[i][j]!=0){
                    continue;
                }
                Point point = new Point(j, i);
                int sum = 0;
                for (int l = -1; l < 2; l++) {
                    for (int k = -1; k < 2; k++) {
                        if ((i + l) >= 0 && (i + l) < 15 && (j + k) >= 0 && (j + k) < 15 && data[i + l][j + k] != 0) {
                            sum++;
                        }
                    }
                }
                point.setValue(sum);
                points.add(point);
            }
        }
        Collections.sort(points);
        return points;
    }
}
