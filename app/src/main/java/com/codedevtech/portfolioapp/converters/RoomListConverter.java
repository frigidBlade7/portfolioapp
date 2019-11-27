package com.codedevtech.portfolioapp.converters;

import androidx.room.TypeConverter;

import com.squareup.moshi.JsonAdapter;
import com.squareup.moshi.Moshi;
import com.squareup.moshi.Types;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class RoomListConverter {


    static Moshi moshi = new Moshi.Builder().build();
    static Type type = Types.newParameterizedType(List.class,String.class);
    static JsonAdapter<List<String>> listJsonAdapter = moshi.adapter(type);


    @TypeConverter
    public static List<String> fromJsonToListString(String json) throws IOException {
        if(json==null)
            return new ArrayList<>();

        return listJsonAdapter.fromJson(json);
    }

    @TypeConverter
    public static String fromListToJson(List<String> list){

        return listJsonAdapter.toJson(list);

    }
}
