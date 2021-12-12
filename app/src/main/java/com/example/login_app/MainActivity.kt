package com.example.login_app

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        auth= FirebaseAuth.getInstance()

        Register.setOnClickListener {
            var intent=Intent(this, Register_Activity::class.java)
            startActivity(intent )
            finish()
        }


        Login.setOnClickListener {
            if(checking()){
                var email=Email.text.toString()
                var password=Password.text.toString()

                auth.signInWithEmailAndPassword(email, password)
                    .addOnCompleteListener(this) { task ->
                        if (task.isSuccessful) {
                            Toast.makeText(this, "Logged In Successfully", Toast.LENGTH_SHORT).show()
                            // Sign in success, update UI with the signed-in user's information
                            var intent=Intent(this, Logdin::class.java)
                            intent.putExtra("email",email)
                            startActivity(intent)
                        } else {
                            // If sign in fails, display a message to the user.
                            Toast.makeText(this, "Unsuccessful", Toast.LENGTH_LONG).show()

                        }
                    }
            }
                else{
                    Toast.makeText(this, "Enter the Details", Toast.LENGTH_LONG).show()
            }
        }
    }
    private fun checking():Boolean{
        var email=Email.text.toString()
        var password=Password.text.toString()
        if(email.trim().isNotEmpty() && password.trim().isNotEmpty()){
            return true
        }
        return false
    }
}