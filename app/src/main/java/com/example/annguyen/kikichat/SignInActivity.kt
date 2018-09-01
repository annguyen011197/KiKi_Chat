package com.example.annguyen.kikichat

import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.app.AlertDialog
import android.text.InputType
import android.util.Log
import android.view.LayoutInflater
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import kotlinx.android.synthetic.main.activity_signin.*
import com.example.annguyen.kikichat.extensions.*

class SignInActivity : AppCompatActivity() {

    lateinit var mAuth: FirebaseAuth
    lateinit var mCurrentUser: FirebaseUser
    var isSignUp: Boolean = false
    var isShow: Boolean = false
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signin)
        mAuth = FirebaseAuth.getInstance()
        textview_signup.setOnClickListener {
            val intent = Intent(this, SignUpActivity::class.java)
            isSignUp = true
            startActivity(intent)
        }

        mCurrentUser = mAuth.currentUser!!

        btn_signin.setOnClickListener {
            var email = et_email.text.toString()
            var password = et_password.text.toString()
            if (!email.isNullOrEmpty() && !password.isNullOrEmpty()) {
                if (email.isCorrectEmail()) {
                    btn_signin.isEnabled = false
                    btn_signin.alpha = 0.5f
                    mAuth.signInWithEmailAndPassword(email, password)
                            .addOnCompleteListener(this) { task ->
                                if (task.isSuccessful) {
                                    val intent = Intent(this, MainActivity::class.java)
                                    startActivity(intent)
                                } else {
                                    this.buildErrorDialog("Error", "Email is incorrect").show()
                                    btn_signin.isEnabled = true
                                    btn_signin.alpha = 1f
                                }
                            }
                } else {
                    this.buildAlertDialog("Warning", "Email is incorrect").show()
                }
            } else {
                this.buildAlertDialog("Warning", "Email or Password is empty").show()
            }
        }

        tv_show_password.setOnClickListener {
            if (isShow) {
                et_password.inputType = InputType.TYPE_CLASS_TEXT
            } else {
                et_password.inputType = InputType.TYPE_TEXT_VARIATION_PASSWORD or InputType.TYPE_CLASS_TEXT
            }
            et_password.setSelection(et_password.length())
            isShow = !isShow
        }
    }


    override fun onResume() {
        super.onResume()
        mCurrentUser = mAuth.currentUser!!
        if (!mCurrentUser!!.isEmailVerified) {
            mCurrentUser!!.sendEmailVerification().addOnCompleteListener {
                if (it.isSuccessful) {
                    Toast.makeText(this, "Email is sent", Toast.LENGTH_SHORT).show()
                } else {
                    mCurrentUser!!.sendEmailVerification()
                }
            }
        } else {
            if (!isSignUp) {
                return
            }
//            val intent = Intent(this,MainActivity::class.java)
//            startActivity(intent)
        }
    }

    fun buildAlertDialog(title: String, message: String): AlertDialog {
        val layout = LayoutInflater.from(this).inflate(R.layout.alert_dialog, null, false)
        val builder = AlertDialog.Builder(this)
        var tvTitle = layout.findViewById<TextView>(R.id.tv_title)
        val tvMessage = layout.findViewById<TextView>(R.id.tv_message)
        val tvCancel = layout.findViewById<TextView>(R.id.tv_cancel)
        tvTitle.text = title
        tvMessage.text = message
        builder.setView(layout)
        val alert = builder.create()
        alert.window.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        tvCancel.setOnClickListener {
            alert.dismiss()
        }
        return alert
    }

    fun buildErrorDialog(title: String, message: String): AlertDialog {
        val layout = LayoutInflater.from(this).inflate(R.layout.alert_dialog, null, false)
        val builder = AlertDialog.Builder(this)
        var tvTitle = layout.findViewById<TextView>(R.id.tv_title)
        val tvMessage = layout.findViewById<TextView>(R.id.tv_message)
        val tvCancel = layout.findViewById<TextView>(R.id.tv_cancel)
        var ivAlert = layout.findViewById<ImageView>(R.id.iv_alert)
        ivAlert.setImageDrawable(resources.getDrawable(R.drawable.ic_error))
        tvTitle.text = title
        tvMessage.text = message
        builder.setView(layout)
        val alert = builder.create()
        alert.window.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        tvCancel.setOnClickListener {
            alert.dismiss()
        }
        return alert
    }
}
