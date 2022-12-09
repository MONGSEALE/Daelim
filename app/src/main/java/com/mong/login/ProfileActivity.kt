package com.mong.login

import android.content.Context
import android.graphics.Bitmap
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import kotlinx.android.synthetic.main.activity_profile.view.*
import java.io.File
import java.net.URI

class ProfileActivity(context: Context) : BottomSheetDialogFragment() {
    lateinit var infoProfile: TextView
    lateinit var photoProfile: ImageView
    var name: String? = null
    var job: String? = null
    var photo: Uri? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        val view = inflater.inflate(R.layout.activity_profile, container, false)

        infoProfile = view.findViewById(R.id.textView)
        photoProfile = view.findViewById(R.id.imageView)

        val shared = activity?.getSharedPreferences("Register", AppCompatActivity.MODE_PRIVATE)
        name = shared?.getString("name", "null")
        job = shared?.getString("job", "null")
        photo = Uri.parse("${context?.filesDir}/image/user_image.png")

        infoProfile.text = "$name\n$job"
        photoProfile.setImageURI(photo)

        return view
    }

    public fun setProfile(name: String, job: String, photo: Uri) {
        this.name = name
        this.job = job
        this.photo = photo
    }

}