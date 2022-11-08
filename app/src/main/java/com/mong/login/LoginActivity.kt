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

            Toast.makeText(this,"id = ${id}, savedId = $savedId \n" +
                    " pw = ${pw}, savedPw = $savedPw", Toast.LENGTH_SHORT).show()

            if (id == savedId && pw == savedPw) {
                dialog("success")
            } else {
                dialog("fail")
            }
        }

        binding.btnRegister.setOnClickListener {
            val intentRegister = Intent(this, RegisterActivity::class.java)
            startActivity(intentRegister)
        }
    }

    fun dialog(status:String) {
        val dialog = AlertDialog.Builder(this)

        if (status=="success") {
            dialog.setTitle("로그인 성공")
            dialog.setMessage("로그인 되셨습니다.")
        } else if (status=="fail") {
            dialog.setTitle("로그인 실패")
            dialog.setMessage("아이디와 비밀번호를 확인하세요")
        }

        dialog.setPositiveButton("확인", DialogInterface.OnClickListener { dialog, id ->
            if (status == "success") {
                val intentMap = Intent(this, MapActivity::class.java)
                startActivity(intentMap)
                finish()
            }
        })
        dialog.show()
    }
}
