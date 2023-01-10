package com.abreuhd.cryptoplatzy.network

import com.abreuhd.cryptoplatzy.model.Crypto
import com.abreuhd.cryptoplatzy.model.User
import com.google.firebase.firestore.FirebaseFirestore

const val CRYPTO_COLECTION_NAME = "cryptos"
const val USERS_COLECTION_NAME = "users"

class FirestoreService(val firebaseFirestore: FirebaseFirestore) {

    fun setDocument(data: Any, collectionName: String, id: String, callback: Callback<Void>){
        firebaseFirestore.collection(collectionName).document(id).set(data)
            .addOnSuccessListener { callback.onSuccess(null) }
            .addOnFailureListener { exeption -> callback.onFailed(exeption) }
    }

    fun updateUser(user: User, callback: Callback<User>){
        firebaseFirestore.collection(USERS_COLECTION_NAME).document(user.username)
            .update("cryptoList", user.cryptoList)
            .addOnSuccessListener { result ->
                if(callback != null){
                    callback.onSuccess(user)
                }
            }
            .addOnFailureListener { exception -> callback.onFailed(exception) }
    }

    fun updateCrypto(crypto: Crypto){
        firebaseFirestore.collection(CRYPTO_COLECTION_NAME).document(crypto.getDocumentId())
            .update("available", crypto.available)
    }

    fun getCryptos(callback: Callback<List<Crypto>>){
        firebaseFirestore.collection(CRYPTO_COLECTION_NAME)
            .get()
            .addOnSuccessListener { result ->
                val cryptoList = result.toObjects(Crypto::class.java)
                callback?.onSuccess(cryptoList)
            }
            .addOnFailureListener { exception ->
                callback?.onFailed(exception)
            }
    }

    fun findUserById(id: String, callback: Callback<User>){
        firebaseFirestore.collection(USERS_COLECTION_NAME).document(id)
            .get()
            .addOnSuccessListener { result ->
                if (result.data != null)
                    callback.onSuccess(result.toObject(User::class.java))
                else
                    callback.onSuccess(null)
            }
            .addOnFailureListener { exception ->
                callback.onFailed(exception)
            }

    }
}