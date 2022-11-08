package com.mong.login

import android.content.DialogInterface
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.mong.login.databinding.ActivityRegisterBinding

class RegisterActivity : AppCompatActivity() {
    private val binding by lazy { ActivityRegisterBinding.inflate(layoutInflater) }
    private var isExistBlack = false
    private var isPWSame = false
    private lateinit var id2: String
    private lateinit var pw2: String
    private lateinit var pw_re: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        binding.btnRegister2.setOnClickListener{
            id2 = binding.txtId2.text.toString()
            pw2 = binding.txtPw2.text.toString()
            pw_re = binding.txtRePw.text.toString()

            if (id2.isEmpty() || pw2.isEmpty() || pw_re.isEmpty())
                isExistBlack = true
            else
                if (pw2 == pw_re)
                    isPWSame = true

            if (!isExistBlack && isPWSame) {
                Toast.makeText(this,"회원가입 성공", Toast.LENGTH_SHORT).show()

                val shared = getSharedPreferences("Register", MODE_PRIVATE)
                val editor = shared.edit()
                editor.putString("id", id2)
                editor.putString("pw", pw2)
                editor.apply()

                Log.d("Register", "저장된 아이디: "
                        + getSharedPreferences("Register", MODE_PRIVATE).getString("id", null)
                        + ", 저장된 비밀번호: "
                        + getSharedPreferences("Register", MODE_PRIVATE).getString("pw", null))

                val intent = Intent(this, LoginActivity::class.java)
                startActivity(intent)
                finish()
            } else
                if (isExistBlack)
                    dialog("blank")
                else
                    dialog("not the same")
        }
    }

    fun dialog(status:String) {
        val dialog = AlertDialog.Builder(this)

        if (status=="blank") {
            dialog.setTitle("회원가입 실패")
            dialog.setMessage("입력란을 모두 작성해주세요")
        }

        else if (status=="not the same") {
            dialog.setTitle("회원가입 실패")
            dialog.setMessage("비밀번호가 다릅니다")
        }
        dialog.setPositiveButton("확인", DialogInterface.OnClickListener { dialog, id ->
            Log.d("Register","다이얼로그")
        })
        dialog.show()
    }
}