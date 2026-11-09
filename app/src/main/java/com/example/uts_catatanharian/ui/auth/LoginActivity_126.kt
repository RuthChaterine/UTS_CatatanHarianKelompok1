package com.example.uts_catatanharian.ui.auth

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.uts_catatanharian.R
import com.example.uts_catatanharian.data.database.AppDatabase_126
import com.example.uts_catatanharian.data.repository.UserRepository_126
import com.example.uts_catatanharian.ui.dashboard.DashboardActivity_126
import com.example.uts_catatanharian.utils.HashUtils_126
import com.example.uts_catatanharian.utils.SessionManager_126
import com.google.android.material.button.MaterialButton
import com.google.android.material.textfield.TextInputEditText
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class LoginActivity_126 : AppCompatActivity() {

    private lateinit var userRepository: UserRepository_126
    private lateinit var sessionManager: SessionManager_126

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        val userDao = AppDatabase_126.getDatabase_126(this).userDao_126()
        userRepository = UserRepository_126(userDao)
        sessionManager = SessionManager_126(this)

        val etUsername = findViewById<TextInputEditText>(R.id.et_username_126)
        val etPassword = findViewById<TextInputEditText>(R.id.et_password_126)
        val btnLogin = findViewById<MaterialButton>(R.id.btn_login_126)
        val tvToRegister = findViewById<android.widget.TextView>(R.id.tv_to_register_126)

        btnLogin.setOnClickListener {
            val username = etUsername.text.toString().trim()
            val password = etPassword.text.toString().trim()

            if (username.isEmpty() || password.isEmpty()) {
                Toast.makeText(this, "Username dan password tidak boleh kosong", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            CoroutineScope(Dispatchers.IO).launch {
                val user = userRepository.getUserByUsername_126(username)
                withContext(Dispatchers.Main) {
                    if (user != null && HashUtils_126.verifyPassword(password, user.salt, user.passwordHash)) {
                        sessionManager.createLoginSession(user.id, user.username)
                        Toast.makeText(this@LoginActivity_126, "Login Berhasil", Toast.LENGTH_SHORT).show()
                        val intent = Intent(this@LoginActivity_126, DashboardActivity_126::class.java)
                        startActivity(intent)
                        finish()
                    } else {
                        Toast.makeText(this@LoginActivity_126, "Username atau password salah", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }

        tvToRegister.setOnClickListener {
            val intent = Intent(this, RegisterActivity_126::class.java)
            startActivity(intent)
        }
    }
}
