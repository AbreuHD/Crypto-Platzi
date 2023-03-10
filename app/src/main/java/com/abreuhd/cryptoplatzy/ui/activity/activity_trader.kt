package com.abreuhd.cryptoplatzy.ui.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.abreuhd.cryptoplatzy.R
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.activity_trader.*

class activity_trader : AppCompatActivity() {
//a
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_trader)

        fab.setOnClickListener { view ->
            Snackbar.make(view, getString(R.string.generating_new_cryptos), Snackbar.LENGTH_SHORT)
                .setAction("Info", null).show()
        }

    }

    fun showGeneralServerErrorMessage() {
        Snackbar.make(fab, getString(R.string.error_while_connecting_to_the_server), Snackbar.LENGTH_LONG)
            .setAction("Info", null).show()
    }
}