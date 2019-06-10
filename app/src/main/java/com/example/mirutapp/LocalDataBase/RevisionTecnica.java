package com.example.mirutapp.LocalDataBase;

import android.util.SparseIntArray;

public class RevisionTecnica {
    public static final SparseIntArray rules;
    static {
        //key: month index
        //value: ending vehicle number
        SparseIntArray map = new SparseIntArray();
        map.append(0, 9);
        map.append(1, 0);
        map.append(3, 1);
        map.append(4, 2);
        map.append(5, 3);
        map.append(6, 4);
        map.append(7, 5);
        map.append(8, 6);
        map.append(9, 7);
        map.append(10, 8);
        rules = map;
    }
}
