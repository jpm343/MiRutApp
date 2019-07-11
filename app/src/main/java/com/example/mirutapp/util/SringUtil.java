package com.example.mirutapp.util;

import android.util.Log;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import static androidx.constraintlayout.widget.Constraints.TAG;

public class SringUtil {
    public static String urlEncode(String s) {
        try {
            return URLEncoder.encode(s, "UTF-8");
        }
        catch (UnsupportedEncodingException e) {
            Log.wtf(TAG, "UTF-8 should always be supported", e);
            throw new RuntimeException("URLEncoder.encode() failed for " + s);
        }
    }
}
