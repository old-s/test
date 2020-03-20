package com.demo.ctw.utils;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by admin on 2017/11/23.
 */

public class GsonTools {
    public static String createGsonString(Object object) {
        Gson gson = new Gson();
        String gsonString = gson.toJson(object);
        return gsonString;
    }

    public static <T> T changeGsonToBean(String gsonString, Class<T> cls) {
        Gson gson = new Gson();
        if (gsonString.equals("{}")) {
            return null;
        }
        T t = gson.fromJson(gsonString, cls);
        return t;
    }
    public static <T> List<T> changeGsonToList(String gsonString, Class<T> cls) {
        // TODO: 2016/7/4 判断 java.lang.IllegalStateException: This is not a JSON Array.
        if (!gsonString.startsWith("["))
            return null;
        Gson gson = new Gson();
        List<T> list = null;
        JsonArray array = new JsonParser().parse(gsonString).getAsJsonArray();
        try {
            list = new ArrayList<>();
            for (JsonElement element : array) {
                list.add(gson.fromJson(element, cls));
            }
        } catch (Error e) {

        }
        return list;
    }
}
