package com.abreuhd.cryptoplatzy.model

class Crypto(var name: String = "",
             var imgURL: String = "",
             var available: Int = 0) {

    fun getDocumentId(): String{
        return name.toLowerCase()
    }
}