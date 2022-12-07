package com.example.elevenlabsecondtry


import android.animation.ObjectAnimator
import androidx.appcompat.app.AppCompatActivity
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.ImageView
import android.widget.TextView

@Suppress("DEPRECATION")
class SplashAcitivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_acitivity)
        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )
        val welcomeText=findViewById<TextView>(R.id.WelcomeText)
        val versionText=findViewById<TextView>(R.id.VersionText)
        val image=findViewById<ImageView>(R.id.WelcomeImage)
        val animationFirstText=ObjectAnimator.ofFloat(welcomeText,"alpha",1f)
        val animationSecondText=ObjectAnimator.ofFloat(versionText,"alpha",1f)
        val animationImage=ObjectAnimator.ofFloat(image,"alpha",1f)
        animationFirstText.duration=1500
        animationSecondText.duration=1500
        animationImage.duration=1500
        Thread.sleep(500)
        animationFirstText.start()
        animationSecondText.start()
        animationImage.start()
        Handler(Looper.getMainLooper()).postDelayed({
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }, 2500)
    }
    private fun AnimateSplashScreen(){
        val welcomeText=findViewById<TextView>(R.id.WelcomeText)
        val versionText=findViewById<TextView>(R.id.VersionText)
        val animationFirstText=ObjectAnimator.ofFloat(welcomeText,"scaleX",2f);
        animationFirstText.duration=1000
        animationFirstText.start()
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
        finish()
    }
}