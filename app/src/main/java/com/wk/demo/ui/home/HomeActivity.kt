package com.wk.demo.ui.home

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.util.DisplayMetrics
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.wk.demo.R
import kotlinx.android.synthetic.main.custom_dialog_alert.view.*
import timber.log.Timber


class HomeActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

    // Load country list fragment
        loadFragment(CountriesListFragment())
    }

    private fun loadFragment(fragment: Fragment?) {
        try {
            //switching fragment
            if (fragment != null) {
                val fragmentTransaction =
                    supportFragmentManager.beginTransaction()
                fragmentTransaction.replace(R.id.fragment, fragment)
                fragmentTransaction.addToBackStack(null)
                fragmentTransaction.commit()
            }
        } catch (e: Exception) {

            Timber.e("Exception%s", e.message)
        }
    }

    override fun onBackPressed() {

        showPopup()
    }

    // function is for exit app alert popup
    private fun showPopup() {
        val alertDialog: AlertDialog
        val builder =
            AlertDialog.Builder(this)

        val viewGroup = findViewById<ViewGroup>(android.R.id.content)
        val dialogView: View =
            LayoutInflater.from(applicationContext)
                .inflate(R.layout.custom_dialog_alert, viewGroup, false)
        builder.setView(dialogView)
        alertDialog = builder.create()
        alertDialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        dialogView.buttonOk.setOnClickListener {
            isDetailFragment = false
            alertDialog.dismiss()
            finishAffinity()
        }
        dialogView.cancel.setOnClickListener {
            alertDialog.dismiss()
        }
        alertDialog.show()
        val displayMetrics = DisplayMetrics()
        windowManager.defaultDisplay.getMetrics(displayMetrics)
        val height = displayMetrics.heightPixels
        val width = displayMetrics.widthPixels
        alertDialog.window!!.setLayout((width/1.5).toInt(), 400)
    }

    companion object {
        // variable to handle fragments orientation change
        var isDetailFragment = false
    }

}
