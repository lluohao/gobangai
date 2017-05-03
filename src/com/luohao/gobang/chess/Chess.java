package com.luohao.gobang.chess;

import com.luohao.gobang.ai.eval.Feature;
import com.luohao.gobang.utils.Matrixs;

import java.util.ArrayList;
import java.util.List;

public class Chess {
    private int[][] square;
    private List<ChessNode> nodes = new ArrayList<>();
    private int[] hashCodes = {15,15,15,15,15,15,15,15,15,15,15,15,15,15,15};
    private List<List<Feature>> features = new ArrayList<>();
    public Chess() {
        square = new int[15][15];
        for(int i = 0;i<6;i++){
            features.add(new ArrayList<Feature>());
        }
    }

    public boolean play(int x, int y, int type) {
        ensureIndex(x, y);
        resetHashCode(x,y,type);
        //处理原始数据
        square[y][x] = type;
        if(type==0){
            back();
        }else {
            //添加步骤
            ChessNode node = new ChessNode();
            node.setX(x);
            node.setY(y);
            node.setType(type);
            nodes.add(node);
        }
        resetHashCode(x,y,type);
        return true;
    }

    private void resetHashCode(int x,int y,int type){
        hashCodes[y] += Math.pow(3,x)*(type==-1?2:type)- Math.pow(3,x)*(square[y][x]==-1?2:square[y][x]);
    }

    private void back(){
        if(nodes.size()==0){
            return;
        }
        nodes.remove(nodes.size()-1);
    }

    private void ensureIndex(int x, int y) {
        if (x < 0 || x >= 15 || y < 0 || y >= 15) {
            throw new RuntimeException("Out of index,the range is [0-15],but x=" + x + ",y=" + y);
        }
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }

    public int get(int x, int y) {
        return square[y][x];
    }

    public int[][] getSquare() {
        return square;
    }

    public List<ChessNode> getNodes() {
        return nodes;
    }

    public static Chess fromDate(int[][] data) {
        Chess chess = new Chess();
        for (int i = 0; i < data.length; i++) {
            for (int j = 0; j < data[i].length; j++) {
                if (data[i][j] != 0) {
                    chess.play(j, i, data[i][j]);
                }
            }
        }
        return chess;
    }

    public void addFeature(Feature feature,int index){
        features.get(index).add(feature);
    }
    public void addFeatures(List<Feature> es,int index){
        List<Feature> features1 = features.get(index);
        features1.addAll(es);
    }

    public List<Feature> getFeatures(int index){
        return features.get(index);
    }

    public void cleanFeatures(){
        this.features = new ArrayList<>();
    }

    public String hashString(){
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < hashCodes.length; i++) {
            sb.append(hashCodes[i]);
        }
        return sb.toString();
    }

    public static void main(String[] args) {
        Chess chess = Chess.fromDate(new int[][]{
                {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},

        });
        chess.play(8,8,1);
        chess.play(7,7,-1);
        chess.play(9,7,1);

        chess.play(7,8,-1);
        chess.play(8,7,1);
        chess.play(7,9,-1);
        String s79 = chess.hashString();
        System.out.println(s79);
        Matrixs.print(chess.getSquare());
        System.out.println();
        chess.play(7,9,0);
        chess.play(8,9,-1);
        String s89 = chess.hashString();
        System.out.println(s89);
        Matrixs.print(chess.getSquare());
        System.out.println();

        System.out.println(s79.equals(s89));
    }
}
