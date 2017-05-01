package com.luohao.gobang.ai.eval.util;

import com.luohao.gobang.ai.eval.Feature;
import com.luohao.gobang.chess.Chess;

/**
 * Created by Everthing-- on 2017/4/28.
 */
public class FeatureUtils {
    public static boolean isReplace(Feature feature1, Feature feature2) {
        for(int i = 0;i<feature1.getSize();i++){
            int x = feature1.getX()+feature1.getDx()*i;
            int y = feature1.getY()+feature1.getDy()*i;
            if(feature2.contains(x,y)){
                return true;
            }
        }
        return false;
    }

    public static int isReplaceCount(Feature feature1, Feature feature2, Chess chess) {
        int count = 0;
        for(int i = 0;i<feature1.getSize();i++){
            int x = feature1.getX()+feature1.getDx()*i;
            int y = feature1.getY()+feature1.getDy()*i;
            if(feature2.contains(x,y)){
                count+=chess.get(x,y);
            }
        }
        return count;
    }

    public static int distance(Feature feature1, Feature feature2) {
        return distanceX(feature1, feature2) + distanceY(feature1, feature2);
    }

    public static int distanceX(Feature feature1, Feature feature2) {
        int dx = Math.abs(feature1.getX() - feature2.getX());
        return dx;
    }

    public static int distanceY(Feature feature1, Feature feature2) {
        int dy = Math.abs(feature1.getY() - feature2.getY());
        return dy;
    }

    public static boolean inLine(Feature feature1, Feature feature2) {
        int distanceX = distanceX(feature1, feature2);
        int distanceY = distanceY(feature1, feature2);
        return (distanceX == 0 || distanceY == 0 || distanceX == distanceY);
    }
}
