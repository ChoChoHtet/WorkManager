package com.android.workmanagerexample

import com.google.gson.Gson

class Utils{
    companion object {
        fun serializeToJson(bmp:Joke):String{
            return Gson().toJson(bmp)
        }
        fun deserializeJson(jsonStr:String):Joke {
            return Gson().fromJson(jsonStr,Joke::class.java)
        }
    }
}
