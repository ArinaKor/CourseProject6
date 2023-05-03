package com.example.servercurs.Singleton;


/*public class EnumSingletom {
    public static void main(String[] args) {
        SingletonEnum se = SingletonEnum.INSTANCE;
        ArrayList<String> result = se.getDbDataField();
                System.out.println(result);
    }
}*/

import com.example.servercurs.entities.Group;

import java.util.ArrayList;
import java.util.List;

public enum SingletonEnum {
    INSTANCE;
    private List<Group> planeList = null;
    private void loadData() {
        planeList = new ArrayList<>();
        /*planeList.add("load data from DB once");*/
    }
    public List<Group> getDbDataField() {
        if(planeList == null) {
            loadData();
        }
        return planeList;
    }
}

