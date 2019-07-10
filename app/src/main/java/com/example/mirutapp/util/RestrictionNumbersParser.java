package com.example.mirutapp.util;

import android.util.Log;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class RestrictionNumbersParser {
    public static final String TAG = "RestrictionNumbersParser";
    public static final String noRestriction = "NO HAY";

    public static List<Integer> StringToIntList(String string) {
        if(string == null || string.equals(noRestriction))
            return null;

        List<String> numbers = Arrays.asList(string.split("-"));
        List<Integer> numbersInt = new ArrayList<>();
        for(String number : numbers) {
            numbersInt.add(Integer.valueOf(number));
            Log.d(TAG, "'"+number+"'");
        }

        return numbersInt;
    }
}
