package com.example.loginscreen

import android.animation.ObjectAnimator
import android.animation.ValueAnimator
import android.annotation.SuppressLint
import android.os.Bundle
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.Button
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.Toast
import android.widget.VideoView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import android.net.Uri


class MainActivity : AppCompatActivity() {
    @SuppressLint("MissingInflatedId")
    private  lateinit var videoView: VideoView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        videoView = findViewById(R.id.login)
        val mainLayout = findViewById<LinearLayout>(R.id.main)
        val glowAnimator = ObjectAnimator.ofFloat(mainLayout,"alpha",0.95f,1f)
        glowAnimator.duration = 1000
        glowAnimator.repeatCount = ValueAnimator.INFINITE
        glowAnimator.repeatMode = ValueAnimator.REVERSE
        glowAnimator.start()

       val Username = findViewById<EditText>(R.id.Username)
        val Password = findViewById<EditText>(R.id.Password)
        val btnLogin = findViewById<Button>(R.id.btnLogin)

        val videoUri = Uri.parse("android.resource://${packageName}/${R.raw.login}")
        videoView.setVideoURI(videoUri)
        videoView.setOnPreparedListener { mediaPlayer ->
            mediaPlayer.isLooping = true
            mediaPlayer.setVolume(0f, 0f) // Mute the video
            val videoProportion = mediaPlayer.videoWidth.toFloat() / mediaPlayer.videoHeight.toFloat()
            val screenWidth = videoView.width
            val screenHeight = videoView.height
            val screenProportion = screenWidth.toFloat() / screenHeight.toFloat()

            val layoutParams = videoView.layoutParams

            if (videoProportion > screenProportion) {
                layoutParams.height = screenHeight
                layoutParams.width = (screenHeight * videoProportion).toInt()
            } else {
                layoutParams.width = screenWidth
                layoutParams.height = (screenWidth / videoProportion).toInt()
            }

            videoView.layoutParams = layoutParams
        }
        videoView.start()

        btnLogin.setOnClickListener {
            val scaleAnimation = AnimationUtils.loadAnimation(this,R.anim.scale_up)
            btnLogin.startAnimation(scaleAnimation)
            val username = Username.text.toString()
            val password = Password.text.toString()

            if(username.isNotEmpty()&& password.isNotEmpty()){
                Toast.makeText(this,"Login Successful", Toast.LENGTH_SHORT).show()
            }
            else{
                Toast.makeText(this,"Please fill all fields",Toast.LENGTH_SHORT).show()
            }
        }
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }

override fun onResume(){
    super.onResume()
    videoView.start()
}
}