package com.example.mediqueue.ui.splash;

import android.animation.ObjectAnimator;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.LinearInterpolator;
import android.widget.ProgressBar;

import androidx.appcompat.app.AppCompatActivity;

import com.example.mediqueue.R;
import com.example.mediqueue.core.SessionManager;
import com.example.mediqueue.ui.login.LoginActivity;

import javax.inject.Inject;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class SplashActivity extends AppCompatActivity {

    @Inject
    SessionManager sessionManager;

    private static final long SPLASH_DELAY = 1500L;
    private final Handler handler = new Handler(Looper.getMainLooper());

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        animateUi();

        handler.postDelayed(this::navigateToNext, SPLASH_DELAY);
    }

    private void animateUi() {
        View logoContainer = findViewById(R.id.centralContent);
        View heartbeat = findViewById(R.id.ivHeartbeat);
        ProgressBar progressBar = findViewById(R.id.progressBar);

        if (logoContainer != null) {
            logoContainer.setAlpha(0f);
            logoContainer.setTranslationY(50f);
            logoContainer.animate()
                    .alpha(1f)
                    .translationY(0f)
                    .setDuration(800)
                    .setInterpolator(new DecelerateInterpolator())
                    .start();
        }

        if (heartbeat != null) {
            AlphaAnimation alphaAnimation = new AlphaAnimation(0.3f, 0.8f);
            alphaAnimation.setDuration(600);
            alphaAnimation.setRepeatMode(Animation.REVERSE);
            alphaAnimation.setRepeatCount(Animation.INFINITE);
            heartbeat.startAnimation(alphaAnimation);
        }

        if (progressBar != null) {
            progressBar.setProgress(0);
            ObjectAnimator animator = ObjectAnimator.ofInt(progressBar, "progress", 100);
            animator.setDuration(SPLASH_DELAY);
            animator.setInterpolator(new LinearInterpolator());
            animator.start();
        }
    }

    private void navigateToNext() {
        if (isFinishing()) return;

        // Force logout to ensure the user is asked to log in every time
        if (sessionManager != null) {
            sessionManager.logout();
        }

        // Always navigate to LoginActivity
        startActivity(new Intent(this, LoginActivity.class));
        finish();
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
    }
}
