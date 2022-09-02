package com.example.mvvmwithdi.model

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty

@JsonIgnoreProperties(ignoreUnknown = true)
 class FaceData {

    @JsonProperty("mobile")
    var mobile: String = ""

    @JsonProperty("password")
    var password: String = ""


    constructor()

    constructor(mobile: String, password: String) {
        this.mobile = mobile
        this.password = password
    }
}