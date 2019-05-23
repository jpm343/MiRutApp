package com.example.mirutapp.Model;

import androidx.room.TypeConverter;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.ref.SoftReference;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ImageConverter {

    static Gson gson = new Gson();

    @TypeConverter
    public static Image stringToImage(String data){

        Image image = new Image("");
        if (data == null) {
            return image;
        }

        Type imageType = new TypeToken<Image>() {}.getType();

        return gson.fromJson(data, imageType);


    }

    @TypeConverter
    public static String imageToString(Image image) {
        return gson.toJson(image);
    }
}
