package com.luohao.gobang.ai.record;

import com.luohao.gobang.ai.ResultNode;
import com.luohao.gobang.chess.Chess;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Everthing-- on 2017/5/5.
 */
public class ZobristRecord implements Record {
    public static int count = 0;
    private Map<Integer,Integer> data = new HashMap<>();
    @Override
    public Integer find(ResultNode node) {
        return data.get(node.getChess().hashCode());
    }

    @Override
    public void addNode(Chess chess, int score) {
        data.put(chess.hashCode(),score);
    }

    public int size(){
        return count;
    }

    public Map<Integer, Integer> getData() {
        return data;
    }
}
