package com.luohao.gobang.chess;

import com.luohao.gobang.ai.eval.Feature;
import com.luohao.gobang.utils.Matrixs;

import java.util.ArrayList;
import java.util.List;

public class Chess {
    private int hashCode = 0;
    private int[][][] hashRandom = new int[2][15][15];
    private int[][] square;
    private List<ChessNode> nodes = new ArrayList<>();
    private List<List<Feature>> features = new ArrayList<>();
    public Chess() {
        square = new int[15][15];
        for(int i = 0;i<6;i++){
            features.add(new ArrayList<Feature>());
        }
        for(int i = 0;i<hashRandom.length;i++){
            for(int j = 0;j<hashRandom[i].length;j++){
                for (int k = 0;k<hashRandom[i][j].length;k++){
                    hashRandom[i][j][k] = (int)(Math.random()*100000000);
                    //hashRandom[i][j][k] = k+15*j;
                }
            }
        }
    }

    public boolean play(int x, int y, int type) {
        //处理原始数据
        resetHashCode(x,y,type);
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
        return true;
    }

    private void resetHashCode(int x,int y,int type){
        int old = square[y][x];
        if(old!=0){
            if(old==1){
                hashCode-=hashRandom[1][y][x];
            }else{
                hashCode-=hashRandom[0][y][x];
            }
        }
        if(type!=0){
            if(type==1){
                hashCode += hashRandom[1][y][x];
            }else{
                hashCode += hashRandom[0][y][x];
            }
        }
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
        int code = 0;
        for (int i = 0; i < square.length; i++) {
            for (int j = 0; j < square[i].length; j++) {
                if(square[i][j]==0){
                    continue;
                }else if(square[i][j]==1){
                    code += hashRandom[0][i][j];
                }else {
                    code += hashRandom[1][i][j];
                }
            }
        }
        return code;
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

        Matrixs.print(chess.hashRandom[0]);
        System.out.println();
        Matrixs.print(chess.hashRandom[1]);
        chess.play(7,7,1);
        System.out.println(chess.hashCode());
        chess.play(7,8,-1);
        System.out.println(chess.hashCode());
        chess.play(7,8,0);
        System.out.println(chess.hashCode());
    }
}
