package com.connect4.yechen.connect4.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * Created by yechen on 16/10/11.
 */

public class WinnerPath {
    int pathNum;
    private List cordinators;
    private List path;
    public static final int WIN_NUM = 4;

    public WinnerPath() {
        pathNum = 0;
        cordinators = new ArrayList();
        path = new ArrayList();
    }
    public void add(int row,int col){
        Coordinator coordinator = new Coordinator(row,col);
        cordinators.add(coordinator);
    }
    public void clearCordinators(){
        cordinators.clear();
    }
    private void newCordinators(){
        cordinators = new ArrayList();
    }
    public void addPath(){
        Collections.sort(cordinators, new Comparator() {
            @Override
            public int compare(Object o1, Object o2) {
                return ((Coordinator)o1).row - ((Coordinator)o2).row;
            }
        });
        path.addAll(cordinators);
        pathNum ++;
      //  newCordinators();
    }
    public Coordinator getPath(int i){
        return (Coordinator)path.get(i);
    }
    public List getPath(){
        return path;
    }
    public int getPathNum(){
        return pathNum;
    }
}
