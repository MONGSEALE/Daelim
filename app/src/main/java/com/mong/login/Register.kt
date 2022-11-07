package com.mong.login

import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.Settings.Global.getString
import android.provider.Settings.Secure.getString
import android.provider.Settings.System.getString
import android.util.Log
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.core.content.res.TypedArrayUtils.getString
import kotlinx.android.synthetic.main.activity_register.*

class Register : AppCompatActivity() {

    val TAG:String = "Register"
    var isExistBlack = false
    var isPWSame = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        val btn_Register2=findViewById<Button>(R.id.btnRegister2)



        btn_Register2.setOnClickListener{

            val id2 = txtId2.text.toString()
            val pw2 = txtPw2.text.toString()
            val pw_re = txtRePw.text.toString()

            if(id2.isEmpty()|| pw2.isEmpty()||pw_re.isEmpty()){
                isExistBlack= true
            }

            else{
                if(pw2==pw_re){
                    isPWSame=true
                }
            }

            if(! isExistBlack && isPWSame) {
                Toast.makeText(this,"회원가입 성공",Toast.LENGTH_SHORT).show()

                val sharedPreference = getSharedPreferences(".Register", 0)
                val editor = sharedPreference.edit()
                editor.putString("id",id2)
                editor.putString("pw",pw2)
                editor.apply()

                Log.d(TAG,"저장된 아이디:"+sharedPreference.getString("id",""))


                val intent= Intent(this,LoginActivity::class.java)
                startActivity(intent)

            }

            else {

                if(isExistBlack){
                  dialog("blank")
                }

                else if (!isPWSame){
                    dialog("not the same")
                }




            }




        }
    }


    fun dialog(status:String){
        val dialog = AlertDialog.Builder(this)

        if(status=="blank"){
            dialog.setTitle("회원가입 실패")
            dialog.setMessage("입력란을 모두 작성해주세요")
        }

        else if(status=="not the same"){
            dialog.setTitle("회원가입 실패")
            dialog.setMessage("비밀번호가 다릅니다")
        }

        val dialog_listener = object: DialogInterface.OnClickListener{
           override fun onClick(dialog:DialogInterface?,which :Int){
               when(which){
                   DialogInterface.BUTTON_POSITIVE-> Log.d(TAG,"다이얼로그")
               }

            }

        }
        dialog.setPostiveButton("확인",dialog_listener)
        dialog.show()


    }
}

private fun AlertDialog.Builder.setPostiveButton(s: String, dialogListener: DialogInterface.OnClickListener) {

}
