package com.example.fireoperator

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.widget.Toast
import androidx.annotation.Nullable
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth

    override fun onCreate(@Nullable savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        auth = FirebaseAuth.getInstance()

        regBtn.setOnClickListener {
            if (!TextUtils.isEmpty(login.text) && !TextUtils.isEmpty(password.text)) {
                auth.createUserWithEmailAndPassword(login.text.toString(), password.text.toString()).addOnCompleteListener  {
                    if (it.isSuccessful) Toast.makeText(this, "Регистрация успешна", Toast.LENGTH_LONG).show()
                    else Toast.makeText(this, "Неверные данные", Toast.LENGTH_SHORT).show()
                }
            } else {
                Toast.makeText(this, "Заполните все поля!", Toast.LENGTH_SHORT).show()
            }
        }

        logBtn.setOnClickListener {
            if (!TextUtils.isEmpty(login.text) && !TextUtils.isEmpty(password.text)) {
                auth.signInWithEmailAndPassword(login.text.toString(), password.text.toString()).addOnCompleteListener  {
                    if (it.isSuccessful) {
                        Toast.makeText(this, "Успешный вход", Toast.LENGTH_SHORT).show()
                        password.setText("")
                        val intent = Intent(this, MainActivity::class.java)
                        intent.putExtra("login", login.text.toString())
                        startActivity(intent)
                    }
                    else Toast.makeText(this, "Неверные данные", Toast.LENGTH_SHORT).show()
                }
            } else {
                Toast.makeText(this, "Заполните все поля!", Toast.LENGTH_LONG).show()
            }
        }
    }
}