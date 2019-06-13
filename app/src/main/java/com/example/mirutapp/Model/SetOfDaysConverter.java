package com.example.mirutapp.Model;

import android.util.JsonReader;
import android.util.JsonWriter;
import android.util.Log;

import androidx.room.TypeConverter;

import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;
import java.util.HashSet;
import java.util.Set;

public class SetOfDaysConverter {
    public static final String TAG = "SetOfDaysConverter";
    @TypeConverter
    public static String fromIntSet(Set<Integer> daySet){
        if(daySet == null)
            return null;

        StringWriter result = new StringWriter();
        JsonWriter json = new JsonWriter(result);
        try {
            json.beginArray();
            for(Integer i: daySet)
                json.value(i);
            json.endArray();
            json.close();
        } catch(IOException e){
            Log.d(TAG, "Exception creating json", e);
        }
        return result.toString();
    }

    @TypeConverter
    public static Set<Integer> toIntSet(String string) {
        if(string == null)
            return null;

        StringReader reader = new StringReader(string);
        JsonReader json = new JsonReader(reader);
        HashSet<Integer> result = new HashSet<>();

        try {
            json.beginArray();
            while(json.hasNext())
                result.add(json.nextInt());

            json.endArray();
        } catch(IOException e) {
            Log.d(TAG, "Exception parsing json", e);
        }
        return result;
    }
}
