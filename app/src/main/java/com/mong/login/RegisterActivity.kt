package com.mong.login

import android.content.DialogInterface
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.mong.login.databinding.ActivityRegisterBinding

class RegisterActivity : AppCompatActivity() {
    private val binding by lazy { ActivityRegisterBinding.inflate(layoutInflater) }
    private lateinit var id: String
    private lateinit var pw: String
    private lateinit var pw_re: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        binding.btnRegister.setOnClickListener{
            id = binding.txtId.text.toString()
            pw = binding.txtPw.text.toString()
            pw_re = binding.txtPwRe.text.toString()

            val builder: AlertDialog.Builder = this.let { AlertDialog.Builder(it) }

            if (id.isEmpty() || pw.isEmpty() || pw_re.isEmpty()) {
                builder.setTitle(R.string.register_fail)
                builder.setMessage("입력란을 모두 작성 해주세요")
                builder.setPositiveButton("확인"){ dialog, id -> }
                val dialog: AlertDialog = builder.create()
                dialog.show()
            } else if (pw != pw_re) {
                builder.setTitle(R.string.register_fail)
                builder.setMessage("비밀번호가 다릅니다")
                builder.setPositiveButton("확인"){ dialog, id -> }
                val dialog: AlertDialog = builder.create()
                dialog.show()
            } else {
                Toast.makeText(this,R.string.register_success, Toast.LENGTH_SHORT).show()

                val shared = getSharedPreferences("Register", MODE_PRIVATE)
                val editor = shared.edit()

                editor.putString("id", id)
                editor.putString("pw", pw)
                editor.apply()

                Log.d("Register", "저장된 아이디: "
                        + getSharedPreferences("Register", MODE_PRIVATE).getString("id", null)
                        + ", 저장된 비밀번호: "
                        + getSharedPreferences("Register", MODE_PRIVATE).getString("pw", null))

                val intent = Intent(this, MemberDataActivity::class.java)
                startActivity(intent)
                finish()

            }

        }
    }
}