package com.example.login_app

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.activity_register_.*

class Register_Activity : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth
    private lateinit var db:FirebaseFirestore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register_)

        auth = FirebaseAuth.getInstance()
        db= FirebaseFirestore.getInstance()
        Continue_signup.setOnClickListener {
            if (checking() == "Successful") {
                var email = Email_id_person.text.toString()
                var password = confirm_Password.text.toString()
                var name = Name.text.toString()
                var phoneNumber = phone_number.text.toString()

                var user = hashMapOf(
                    "Name" to name,
                    "Phone" to phoneNumber,
                    "email" to email,
                )
                val Users = db.collection("USERS")
                val query = Users.whereEqualTo("email", email).get()
                    .addOnSuccessListener(this) {
                            tasks ->
                        if (tasks.isEmpty) {
                            auth.createUserWithEmailAndPassword(email,password)
                                .addOnCompleteListener(this) { task ->
                                    if (task.isSuccessful) {
                                        Users.document(email).set(user)
                                        val intent=Intent(this, Logdin::class.java)
                                        intent.putExtra("email",email)
                                        startActivity(intent)
                                        finish()
                                    }
                                    else {
                                        Toast.makeText(
                                            this,
                                            "Authentication Failed",
                                            Toast.LENGTH_SHORT
                                        ).show()
                                    }
                                }
                        }
                            else{
                                Toast.makeText(this, "user alerady Registered", Toast.LENGTH_SHORT)
                                    .show()
                                var intent = Intent(this, MainActivity::class.java)
                                startActivity(intent)
                            }
                    }
            }
            else {
                Toast.makeText(this, "${checking()}",Toast.LENGTH_SHORT).show()
            }
        }
    }

        private fun checking(): String {
            if (Name.text.toString().trim { it <= ' ' }.isEmpty())
                return "Enter NAME"
            if (phone_number.text.toString().trim { it <= ' ' }.isEmpty())
                return "Enter the PhoneNumber"
            if (Email_id_person.text.toString().trim { it <= ' ' }.isEmpty())
                return "Enter Email"
            if (password_first.text.toString().trim { it <= ' ' }.isEmpty())
                return "Enter password"
            if (confirm_Password.text.toString().trim { it <= ' ' }.isEmpty())
                return "Enter the confirm_password"
            if (confirm_Password.text.toString().trim { it <= ' ' }
                    .compareTo(password_first.text.toString().trim { it <= ' ' }) != 0)
                return "Password Doesn't matches"
            return "Successful"
        }
}
