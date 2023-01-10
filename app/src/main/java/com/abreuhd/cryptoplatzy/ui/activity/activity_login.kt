package com.abreuhd.cryptoplatzy.ui.activity

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.abreuhd.cryptoplatzy.R
import com.abreuhd.cryptoplatzy.model.User
import com.abreuhd.cryptoplatzy.network.Callback
import com.abreuhd.cryptoplatzy.network.FirestoreService
import com.abreuhd.cryptoplatzy.network.USERS_COLECTION_NAME
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.activity_login.*


const val USERNAME_KEY = "username_key"

class activity_login : AppCompatActivity() {

    private val auth: FirebaseAuth = FirebaseAuth.getInstance()
    private val TAG = "LoginActivity"
    lateinit var firestoreService: FirestoreService

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        firestoreService = FirestoreService(FirebaseFirestore.getInstance())
    }

    fun onStartClicked(view: View) {
        view.isEnabled = false
        auth.signInAnonymously()
            .addOnCompleteListener{ task ->
                if(task.isSuccessful){
                    val user = username.text.toString()
                    firestoreService.findUserById(user, object: Callback<User>{
                        override fun onSuccess(result: User?) {
                            if (result == null){
                                val userObj = User()
                                userObj.username = user
                                saveUserAndStartMainActivity(userObj, view)
                            }
                            else{
                                startMainActivity(user)
                            }
                        }

                        override fun onFailed(exception: Exception) {
                            showErrorMessage(view)
                        }

                    })

                }else{
                    showErrorMessage(view)
                    view.isEnabled = true
                }
            }
    }

    private fun saveUserAndStartMainActivity(userObj: User, view: View) {
        firestoreService.setDocument(userObj, USERS_COLECTION_NAME, userObj.username, object : Callback<Void>{
            override fun onSuccess(result: Void?) {
                startMainActivity(userObj.username)
            }

            override fun onFailed(exception: Exception) {
                showErrorMessage(view)
                Log.e(TAG, "Error", exception)
                view.isEnabled = true
             }

        })
    }

    private fun showErrorMessage(view: View) {
        Snackbar.make(view, getString(R.string.error_while_connecting_to_the_server), Snackbar.LENGTH_LONG)
            .setAction("Info", null).show()
    }

    private fun startMainActivity(username: String) {
        val intent = Intent(this@activity_login, activity_trader::class.java)
        intent.putExtra(USERNAME_KEY, username)
        startActivity(intent)
        finish()
    }

}
