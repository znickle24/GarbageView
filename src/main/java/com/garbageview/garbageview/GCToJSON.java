package com.garbageview.garbageview;

import com.google.gson.Gson;

/**
 * Used to convert data collected from the GC tool into a JSON/GSON compatible class.
 * To implement -> create GCToJSON object by calling constructor -> make a new GSON object -> call gsonObject.toJson(GCToJSONObject)
 * Helpful link - https://futurestud.io/tutorials/gson-getting-started-with-java-json-serialization-deserialization
 * Helpful link - https://www.mkyong.com/java/how-do-convert-java-object-to-from-json-format-gson-api/
 */
public class GCToJSON {

    long Id;
    String GCType;
    long GCTime; // In milliseconds
    String GCName;
    String GCCause;
//    String dbMUBGc;
//    String dbMUAGc;
    Float GCOverhead;
    // Get the GC overhead
//    float GCOverhead;

    public GCToJSON(String gctype, long time, long id, String name, String cause, String memBefore, String memAfter, String overhead){
        String dbMUBGc;
        String dbMUAGc;

        GCType = gctype;
        GCTime = time;
        Id = id;
        GCName = name;
        GCCause = cause;
        dbMUBGc = memBefore;
        dbMUAGc = memAfter;
        GCOverhead = Float.parseFloat(overhead);
//        DecimalFormat df = new DecimalFormat();
//        df.setMaximumFractionDigits(2);
//        df.format(decimalNumber)
    }
}
