package com.mong.application

import android.content.DialogInterface
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.os.Build
import android.provider.MediaStore
import android.widget.ImageView
import android.widget.Toast
import androidx.core.app.ActivityCompat
import coil.load

class MemberData : AppCompatActivity() {


    val CAMERA_PERMISSION = arrayOf(Manifest.permission.CAMERA)
    val STORAGE_PERMISSION = arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE,Manifest.permission.WRITE_EXTERNAL_STORAGE)

    //권한 플래그값 정의
    val FLAG_PERM_CAMERA = 98
    val FLAG_PERM_STORAGE = 99

    //카메라와 갤러리를 호출하는 플래그
    val FLAG_REQ_CAMERA = 101
    val FLAG_REA_STORAGE = 102



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.member_data)


        if(checkPermission(STORAGE_PERMISSION,FLAG_PERM_STORAGE)){
            setViews()
        }


        val TAG:String = "로그"

       val txt_name= findViewById<EditText>(R.id.txtName)
        val txt_job= findViewById<EditText>(R.id.txtJob)
        val btn_next=findViewById<Button>(R.id.btnNext)

        var isBlank:Boolean =false


       btn_next.setOnClickListener(){
           var name = txt_name.text.toString()
           var job = txt_job.text.toString()

           if (name.isEmpty()||job.isEmpty()){
               isBlank=true
               dialog(isBlank)
           }
           else {
               val shared = getSharedPreferences("Register", MODE_PRIVATE)
               val editor = shared.edit()
               editor.putString("name", name)
               editor.putString("job", job)
               editor.apply()
               isBlank=false
               dialog(isBlank)
               /*val intent = Intent(this, MapActivity::class.java)
               startActivity(intent)
               finish()*/
           }

       }


    }

    fun dialog(status:Boolean){
        val dialog = AlertDialog.Builder(this)

        if(status==true){
            dialog.setTitle("실패")
            dialog.setMessage("입력란을 모두 입력해주세요")

        }

        else {
            dialog.setTitle("성공")
            dialog.setMessage("다음 페이지로 넘어갑니다")
        }

        dialog.setPositiveButton("확인", DialogInterface.OnClickListener { dialog, id ->
            Log.d("Register","다이얼로그")
        })
        dialog.show()

    }

    private fun setViews() {

        val btn_camera=findViewById<Button>(R.id.btnCamera)
        //카메라 버튼 클릭
        btn_camera.setOnClickListener {
            //카메라 호출 메소드
            openCamera()
        }
    }


    private fun openCamera() {
        //카메라 권한이 있는지 확인
        if(checkPermission(CAMERA_PERMISSION,FLAG_PERM_CAMERA)){
            //권한이 있으면 카메라를 실행시킵니다.
            val intent: Intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
            startActivityForResult(intent,FLAG_REQ_CAMERA)
        }
    }

    fun checkPermission(permissions:Array<out String>,flag:Int):Boolean{
        //안드로이드 버전이 마쉬멜로우 이상일때
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
            for(permission in permissions){
                //만약 권한이 승인되어 있지 않다면 권한승인 요청을 사용에 화면에 호출합니다.
                if(ContextCompat.checkSelfPermission(this,permission) != PackageManager.PERMISSION_GRANTED){
                    ActivityCompat.requestPermissions(this,permissions,flag)
                    return false
                }
            }
        }
        return true
    }


    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        when(requestCode){
            FLAG_PERM_STORAGE ->{
                for(grant in grantResults){
                    if(grant != PackageManager.PERMISSION_GRANTED){
                        //권한이 승인되지 않았다면 return 을 사용하여 메소드를 종료시켜 줍니다
                        Toast.makeText(this,"저장소 권한을 승인해야지만 앱을 사용할 수 있습니다..",Toast.LENGTH_SHORT).show()
                        finish()
                        return
                    }
                }
                //카메라 호출 메소드
                setViews()
            }
            FLAG_PERM_CAMERA ->{
                for(grant in grantResults){
                    if(grant != PackageManager.PERMISSION_GRANTED){
                        Toast.makeText(this,"카메라 권한을 승인해야지만 카메라를 사용할 수 있습니다.",Toast.LENGTH_SHORT).show()
                        return
                    }
                }
                openCamera()
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(resultCode == Activity.RESULT_OK){
            when(requestCode){
                FLAG_REQ_CAMERA ->{
                    if(data?.extras?.get("data") != null){
                        //카메라로 방금 촬영한 이미지를 미리 만들어 놓은 이미지뷰로 전달 합니다.
                        val bitmap = data?.extras?.get("data") as Bitmap
                        val img_Profile=findViewById<ImageView>(R.id.imgProfile)
                        img_Profile.setImageBitmap(bitmap)
                    }
                }
            }
        }
    }






}