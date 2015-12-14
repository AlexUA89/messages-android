package com.alexua.messages.ui.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.ViewStub;

import com.alexua.messages.R;
import com.alexua.messages.ui.welcome.SplashScreenView;
import com.alexua.messages.utils.LogUtils;
import com.alexua.messages.utils.UserUtils;

/**
 * @author Mirash
 */
public class WelcomeActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
        if (UserUtils.isShowSplash()) {
            showSplash();
        } else {
            checkLaunchStrategy();
        }
    }

    private void checkLaunchStrategy() {
        if (UserUtils.isLoggedUserExist()) {
            launchMainActivity();
        } else {
            launchLoginActivity();
        }
        overridePendingTransition(R.anim.welcome_activity_fade_in, R.anim.welcome_activity_fade_out);
        finish();
    }

    private void showSplash() {
        LogUtils.log("showSplash");
        ViewStub stubSplashView = (ViewStub) findViewById(R.id.welcome_splash_stub);
        SplashScreenView splashView = (SplashScreenView) stubSplashView.inflate();
        splashView.show(new SplashScreenView.SplashListener() {
            @Override
            public void onShowEnd() {
                checkLaunchStrategy();
            }
        });
    }

    private void launchLoginActivity() {
        LogUtils.log("launchLoginActivity");
        Intent intent = new Intent(WelcomeActivity.this, LoginActivityOld.class);
        startActivity(intent);
    }

    private void launchMainActivity() {
        LogUtils.log("launchMainActivity");
        Intent intent = new Intent(WelcomeActivity.this, MainActivity.class);
        startActivity(intent);
    }
}
