package com.example.mvvmwithdi.utils

import com.example.mvvmwithdi.model.User
import com.fasterxml.jackson.core.type.TypeReference
import com.fasterxml.jackson.databind.DeserializationFeature
import com.fasterxml.jackson.databind.ObjectMapper
import okhttp3.ResponseBody
import org.json.JSONObject
import retrofit2.Response
import kotlin.reflect.KClass

object Utils {

    //Json to Array
  /*  fun <A, U> Response<ResponseBody>.getArrayBody(className: Class<U>): ArrayList<A> {
        val body = this.body()?.string()
        val objectMapper = ObjectMapper();
        objectMapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
        val arrayList: ArrayList<A> =
            objectMapper.readerForListOf(className).readValue(body)
        return arrayList;
    }*/

    fun <A, U> Response<ResponseBody>.getArrayBody(className: Class<U>): ArrayList<A> {
        val body = this.body()?.string()
        val objectMapper = ObjectMapper();
        objectMapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
        val arrayList: ArrayList<A> =
            objectMapper.readerForListOf(className).readValue(body)

        return arrayList;
    }

    //Json to Object
    fun <T> Response<ResponseBody>.getObjectBody(className: Class<T>): T {
        val body = this.body()!!.string()
        val json = JSONObject(body)
        val user = json.optJSONObject("data")/*.optJSONObject("user")*/
        val objectMapper = ObjectMapper();
        val model: T =
            objectMapper.readValue(user.toString(), className)
        return model;
    }
}