package com.example.annguyen.kikichat

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.CountDownTimer
import android.os.Handler
import android.support.design.widget.Snackbar
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.widget.Toast
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser

import kotlinx.android.synthetic.main.activity_signup.*

class SignUpActivity : AppCompatActivity() {
    lateinit var mAuth:FirebaseAuth
    var TAG:String  = this.javaClass.simpleName
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signup)
        mAuth = FirebaseAuth.getInstance()
        textview_signin.setOnClickListener{
            this.onBackPressed()
        }
        btn_signup.setOnClickListener{
            var email = et_email.text.toString()
            var password = et_password.text.toString()
            var rePassword = et_repassword.text.toString()
            if(!email.isNullOrEmpty() && !password.isNullOrEmpty() && !rePassword.isNullOrEmpty()){
                if (password == rePassword){
                    btn_signup.isEnabled = false
                    val createUser = mAuth.createUserWithEmailAndPassword(email, password)
                    createUser.addOnCompleteListener(this){task->
                        if(task.isSuccessful){
                            Toast.makeText(this,"Sign up succesful",Toast.LENGTH_SHORT).show()
                            Handler().postDelayed({
                                onBackPressed()
                            },100)
                        }else{
                            Toast.makeText(this,"Authentication failed",Toast.LENGTH_SHORT).show()
                            btn_signup.isEnabled = true
                        }
                    }

                }
            }
        }
    }

}
