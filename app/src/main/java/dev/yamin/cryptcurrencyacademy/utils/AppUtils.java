package dev.yamin.cryptcurrencyacademy.utils;

import android.graphics.Color;
import android.text.TextUtils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.stream.JsonReader;

import java.io.StringReader;
import java.lang.reflect.Type;
import java.text.SimpleDateFormat;
import java.util.Locale;
import java.util.Random;

/**
 * Created by Yamin on 3/8/2018.
 */

public class AppUtils {

    private static final Gson gson;
    private static final Random random;
    private static SimpleDateFormat formater;

    private AppUtils() {    }

    static {
        GsonBuilder builder = new GsonBuilder();
        gson = builder.create();
        random = new Random(System.currentTimeMillis());
        formater = new SimpleDateFormat("EEE", Locale.getDefault());
    }


    public static String objToJsonStr(Object object) {
        return gson.toJson(object);
    }

    public static <T> T getObjectFromStr(String jsonStr, final Class<T> classOfT) throws com.google.gson.JsonSyntaxException {
        JsonReader reader = new JsonReader(new StringReader(jsonStr));
        reader.setLenient(true);
        return gson.fromJson(reader, classOfT);
    }

    public static <T> T getObjectFromStr(String jsonStr, Type typeOfT) throws com.google.gson.JsonSyntaxException {
        JsonReader reader = new JsonReader(new StringReader(jsonStr));
        reader.setLenient(true);
        return gson.fromJson(reader, typeOfT);
    }

    public static int randomColor() {
        int alpha = random.nextInt(255), red = random.nextInt(255), green = random.nextInt(255), blue = random.nextInt(255);
        return Color.argb(alpha, red, green, blue);
    }

    public static int randomInt(int min, int max) {
        return random.nextInt(max - min + 1) + min;
    }

    public long StringToLong(String longStr) {
        if (TextUtils.isEmpty(longStr))
            return -1;
        try {
            return Long.parseLong(longStr);
        } catch (NumberFormatException e) {
            return -1;
        }
    }

    public int StringToInt(String intStr) {
        if (TextUtils.isEmpty(intStr))
            return -1;
        try {
            return Integer.parseInt(intStr);
        } catch (NumberFormatException e) {
            return -1;
        }
    }

    public float StringToFloat(String floatStr) {
        if (TextUtils.isEmpty(floatStr))
            return -1;
        try {
            return Float.parseFloat(floatStr);
        } catch (NumberFormatException e) {
            return -1;
        }
    }

    public static String getFullDateTime(long timeInMillis) {
        timeInMillis *= 60;
        timeInMillis *= 1000;
        formater.applyPattern("dd-MMM");
        return formater.format(timeInMillis);
    }
}
