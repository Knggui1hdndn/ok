package laptrinhandroid.fpoly.dnnhm3.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import laptrinhandroid.fpoly.dnnhm3.R;

public class PlashActivity extends AppCompatActivity {
ImageView imggiaohang,imgnhanhang;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_plash);
        imggiaohang=findViewById(R.id.thangshiper);
        imgnhanhang=findViewById(R.id.habangchu);
        Animation animation1 = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.translate);
        imggiaohang.startAnimation(animation1);
        Animation animation2 = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.translate2);
        imgnhanhang.startAnimation(animation2);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                startActivity(new Intent(PlashActivity.this,login.class));
                finish();
            }
        },3500);

    }
}