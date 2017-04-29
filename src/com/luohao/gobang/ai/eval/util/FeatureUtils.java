package com.luohao.gobang.ai.eval.util;

import com.luohao.gobang.ai.eval.Feature;
import com.luohao.gobang.chess.Chess;

/**
 * Created by Everthing-- on 2017/4/28.
 */
public class FeatureUtils {
    public static boolean isReplace(Feature feature1, Feature feature2, Chess chess) {
        int dt1x = feature1.getType() != 1 ? 1 : 0;
        int dtly = feature1.getType() == 2 || feature1.getType() == 4 ? 1 :
                feature1.getType() == 3 ? -1 : 0;

        int dt2x = feature1.getType() != 1 ? 1 : 0;
        int dt2y = feature1.getType() == 2 || feature1.getType() == 4 ? 1 :
                feature1.getType() == 3 ? -1 : 0;

        for (int i = 0; i < 6; i++) {
            if (chess.get(feature1.getStart().x+dt1x,feature1.getStart().y+dtly)==1){
                //TODO
                ;
            }
        }
        return false;
    }

    public static int distance(Feature feature1, Feature feature2) {
        return distanceX(feature1, feature2) + distanceY(feature1, feature2);
    }

    public static int distanceX(Feature feature1, Feature feature2) {
        int dx = Math.abs(feature1.getStart().x - feature2.getStart().x);
        return dx;
    }

    public static int distanceY(Feature feature1, Feature feature2) {
        int dy = Math.abs(feature1.getStart().y - feature2.getStart().y);
        return dy;
    }

    public static boolean inLine(Feature feature1, Feature feature2) {
        int distanceX = distanceX(feature1, feature2);
        int distanceY = distanceY(feature1, feature2);
        return (distanceX == 0 || distanceY == 0 || distanceX == distanceY);
    }
}
