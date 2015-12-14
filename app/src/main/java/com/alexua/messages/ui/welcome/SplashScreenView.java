package com.alexua.messages.ui.welcome;

import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.alexua.messages.R;
import com.alexua.messages.utils.EffectUtils;

/**
 * @author Mirash
 */
public class SplashScreenView extends FrameLayout {
    private TextView mLogoTextView;

    public SplashScreenView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    private void init(Context context) {
        inflate(context, R.layout.welcome_splash, this);
        mLogoTextView = (TextView) findViewById(R.id.welcome_splash_logo_text);
    }

    public void show(final SplashListener showListener) {
        EffectUtils.changeBackgroundColor(this, Color.TRANSPARENT, Color.argb(125, 0, 0, 0), 1000);
        Animation logoAlphaAnimation = new AlphaAnimation(0, 1);
        logoAlphaAnimation.setDuration(1000);
        logoAlphaAnimation.setStartOffset(250);
        logoAlphaAnimation.setAnimationListener(new EffectUtils.AnimationListenerForLazy() {
            @Override
            public void onAnimationEnd(Animation animation) {
                if (showListener != null) {
                    showListener.onShowEnd();
                }
            }
        });
        mLogoTextView.startAnimation(logoAlphaAnimation);
    }

    public interface SplashListener {
        void onShowEnd();
    }
}
