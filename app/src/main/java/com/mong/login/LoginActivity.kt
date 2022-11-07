package com.mong.login

import android.content.ContentValues.TAG
import android.content.Context
import android.content.DialogInterface
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.content.Intent
import android.util.Log
import androidx.appcompat.app.AlertDialog

class LoginActivity : AppCompatActivity() {
    val TAG:String ="로그"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)








        val btn_Login= findViewById<Button>(R.id.btnLogin)
        val btn_Register = findViewById<Button>(R.id.btnRegister)

        val txt_Id=findViewById<EditText>(R.id.txtId)
        val txt_Pw=findViewById<EditText>(R.id.txtPw)

        btn_Login.setOnClickListener(){

            var id = txt_Id.text.toString()
            var pw = txt_Pw.text.toString()

            val sharedPreferences= getSharedPreferences(".Register", 0)
            val  savedId = sharedPreferences.getString("id","")
            val savedPw = sharedPreferences.getString("Pw","")



            if(id==savedId&& pw==savedPw){
                dialog("success")

            }
            else{
                dialog("fail")
            }
        }


        btn_Register.setOnClickListener(){
            val intent = Intent(this,Register::class.java)
         startActivity(intent)

        }


    }
    fun dialog(status:String){
        var dialog = AlertDialog.Builder(this)

        if(status=="success"){
            dialog.setTitle("로그인 성공")
            dialog.setMessage("로그인 되셨습니다.")

        }

        else if (status=="fail"){
            dialog.setTitle("로그인 실패")
            dialog.setMessage("아이디와 비밀번호를 확인하세요")
        }

        var dialogListener= object:DialogInterface.OnCancelListener{
            fun onClick(dialog:DialogInterface?,which:Int){
                when(which){
                    DialogInterface.BUTTON_POSITIVE->
                        Log.d(TAG,"")
                }
            }

            override fun onCancel(p0: DialogInterface?) {
                TODO("Not yet implemented")
            }


        }

      dialog.setPositiveButton("확인",dialogListener)


    }



    private fun AlertDialog.Builder.setPositiveButton(
        s: String,
        dialogListener: DialogInterface.OnCancelListener
    ) {
        TODO("Not yet implemented")
    }





}




