package com.mong.login

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.net.Uri
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.mong.login.databinding.ActivityMemberDataBinding
import kotlinx.android.synthetic.main.activity_member_data.*
import java.io.*

class MemberDataActivity : AppCompatActivity() {
    private val binding by lazy { ActivityMemberDataBinding.inflate(layoutInflater) }

    private val CAMERA_PERMISSION = arrayOf(Manifest.permission.CAMERA)
    private val STORAGE_PERMISSION = arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE,Manifest.permission.WRITE_EXTERNAL_STORAGE)

    val getContent = registerForActivityResult(ActivityResultContracts.GetContent()) { uri: Uri? ->

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        if(checkPermission(STORAGE_PERMISSION,FLAG_PERM_STORAGE)){
            setViews()
        }
        val builder: AlertDialog.Builder = this.let { AlertDialog.Builder(it) }

        binding.btnNext.setOnClickListener(){
            val name = binding.txtName.text.toString()
            val job = binding.txtJob.text.toString()


            if (name.isEmpty()||job.isEmpty()){
                builder.setTitle(R.string.register_fail)
                builder.setMessage("입력란을 모두 입력해주세요")
                builder.setPositiveButton("확인"){ dialog, id -> }
            }
            else {
                val shared = getSharedPreferences("Register", MODE_PRIVATE)
                val editor = shared.edit()
                editor.putString("name", name)
                editor.putString("job", job)
                editor.apply()

                builder.setTitle(R.string.register_success)
                builder.setMessage("다음 페이지로 넘어갑니다")
                builder.setPositiveButton("확인"){ dialog, id ->
                    val intent = Intent(this, MapActivity::class.java)
                    startActivity(intent)
                    finish()
                }
            }
            val dialog: AlertDialog = builder.create()
            dialog.show()
        }
    }

    private fun setViews() {
        binding.btnCamera.setOnClickListener {
            openCamera()
        }
    }

    private fun openCamera() {
        if(checkPermission(CAMERA_PERMISSION,FLAG_PERM_CAMERA)){
            //권한이 있으면 카메라를 실행시킵니다.
            val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
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
            FLAG_PERM_STORAGE -> {
                for(grant in grantResults) {
                    if(grant != PackageManager.PERMISSION_GRANTED){
                        //권한이 승인되지 않았다면 return 을 사용하여 메소드를 종료시켜 줍니다
                        Toast.makeText(this,"저장소 권한을 승인해야지만 앱을 사용할 수 있습니다..", Toast.LENGTH_SHORT).show()
                        finish()
                        return
                    }
                }
                //카메라 호출 메소드
                setViews()
            }
            FLAG_PERM_CAMERA -> {
                for(grant in grantResults) {
                    if(grant != PackageManager.PERMISSION_GRANTED){
                        Toast.makeText(this,"카메라 권한을 승인해야지만 카메라를 사용할 수 있습니다.", Toast.LENGTH_SHORT).show()
                        return
                    }
                }
                openCamera()
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK) {
            when(requestCode) {
                FLAG_REQ_CAMERA -> {
                    if(data?.extras?.get("data") != null) {
                        //카메라로 방금 촬영한 이미지를 미리 만들어 놓은 이미지뷰로 전달 합니다.
                        val user_image = data.extras?.get("data") as Bitmap
                        binding.imgProfile.setImageBitmap(user_image)
                        saveImageFile(user_image)
                    }
                }
            }
        }
    }

    private fun saveImageFile(bitmap: Bitmap) {
        val storageDir = File("$filesDir/image")
        if (!storageDir.exists())
            storageDir.mkdirs()
        val file = File("$storageDir/user_image.png")
        try {
            file.createNewFile()
            var fos = FileOutputStream(file)
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, fos)
            fos.close()
        } catch (e: FileNotFoundException) {
            e.printStackTrace()
        } catch (e: IOException) {
            e.printStackTrace()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    companion object {
        //카메라와 갤러리를 호출하는 플래그
        private const val FLAG_REQ_CAMERA = 101
        //권한 플래그값 정의
        private const val FLAG_PERM_CAMERA = 98
        private const val FLAG_PERM_STORAGE = 99
        private const val FLAG_REA_STORAGE = 102
    }
}