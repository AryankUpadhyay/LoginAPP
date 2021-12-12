package com.example.login_app

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.activity_logdin.*

class Logdin : AppCompatActivity() {
    private lateinit var db:FirebaseFirestore


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_logdin)

        val sharePref=this?.getPreferences(Context.MODE_PRIVATE)?:return
        val email=intent.getStringExtra("email")
        if(email!=null)
        setText(email)

        Log_Out.setOnClickListener {
            sharePref.edit().remove("email").apply()
            var intent=Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }

        val islogin=sharePref.getString("email", "1")
        if(islogin=="1") {
           Toast.makeText(this, "Logged In Successfully", Toast.LENGTH_SHORT).show()
//            if(email!=null) {
//                setText(email)
                with(sharePref.edit()) {
                    putString("email", email)
                    apply()
                }
            if(email!=null) {
                setText(email)
            }
           // }
            else{
                Toast.makeText(this, "Email.is.null", Toast.LENGTH_SHORT).show()
            }
        }
//        else{
//            var intent=Intent(this, MainActivity::class.java)
//            startActivity(intent)
//            finish()
//        }
    }

    private fun setText(email:String){
        db= FirebaseFirestore.getInstance()
        db.collection("USERS").document(email).get()
                .addOnSuccessListener {
                    Task->
                    Name_welcome.text=Task.get("Name").toString()
                    Phone_Welcome.text=Task.get("Phone").toString()
                    Email_Welcome.text=Task.get("email").toString()
                }
    }
}