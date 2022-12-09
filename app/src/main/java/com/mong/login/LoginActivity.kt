
package com.mong.login

import android.content.DialogInterface
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.content.Intent
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.mong.login.databinding.ActivityLoginBinding

class LoginActivity : AppCompatActivity() {
    private val binding by lazy { ActivityLoginBinding.inflate(layoutInflater) }
    private lateinit var id: String
    private lateinit var pw: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        binding.btnLogin.setOnClickListener {
            id = binding.txtId.text.toString()
            pw = binding.txtPw.text.toString()

            val shared = getSharedPreferences("Register", MODE_PRIVATE)
            val savedId = shared.getString("id", null)
            val savedPw = shared.getString("pw", null)
            val builder: AlertDialog.Builder = this.let { AlertDialog.Builder(it) }

            Toast.makeText(this,"id = ${id}, savedId = $savedId \n" +
                    " pw = ${pw}, savedPw = $savedPw", Toast.LENGTH_SHORT).show()

            if (id != savedId && pw != savedPw) {
                builder.setTitle(R.string.login_fail)
                builder.setMessage(R.string.login_fail_not_correct)
                builder.setPositiveButton("확인"){ dialog, id -> }
            } else {
                builder.setTitle(R.string.login_success)
                builder.setMessage(R.string.login_success_correct)
                builder.setPositiveButton("확인"){ dialog, id ->
                    val intent = Intent(this, MapActivity::class.java)
                    startActivity(intent)
                    finish()
                }
            }
            val dialog: AlertDialog = builder.create()
            dialog.show()
        }

        binding.btnRegister.setOnClickListener {
            val intentRegister = Intent(this, RegisterActivity::class.java)
            startActivity(intentRegister)
        }
    }
}
