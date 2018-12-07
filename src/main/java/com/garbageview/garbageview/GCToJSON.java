package com.garbageview.garbageview;

import com.google.gson.Gson;

/**
 * Used to convert data collected from the GC tool into a JSON/GSON compatible class.
 * To implement -> create GCToJSON object by calling constructor -> make a new GSON object -> call gsonObject.toJson(GCToJSONObject)
 * Helpful link - https://futurestud.io/tutorials/gson-getting-started-with-java-json-serialization-deserialization
 * Helpful link - https://www.mkyong.com/java/how-do-convert-java-object-to-from-json-format-gson-api/
 */
public class GCToJSON {

    String GCType;
    long GCTime; // In milliseconds
    long Id;
    // Get the GC overhead
    float GCOverhead;

    public GCToJSON(String gctype, long time, long id){
        GCType = gctype;
        GCTime = time;
        Id = id;
    };

//    public GCToJson(String gctype, long time) {
//        GCType = gctype;
//        GCTime = time;
//    }
}
