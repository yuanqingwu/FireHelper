package com.wyq.firehelper.ui.android.animation;


import android.view.animation.Interpolator;

public class CollisionInterpolator implements Interpolator {
    @Override
    public float getInterpolation(float input) {
        if (input <= 0.2) return getBinomial(25.0f, 0.0f, 0.0f, input);
        else if (input <= 0.4) return getBinomial(10f, -8.5f, 2.3f, input);
        else if (input <= 0.55) return getBinomial(32.66667f, -27.7f, 6.353333f, input);
        else if (input <= 0.7) return getBinomial(20f, -27f, 9.8f, input);
        else if (input <= 0.8) return getBinomial(40.0f, -57.0f, 21f, input);
        else if (input <= 0.9) return getBinomial(6f, -11.2f, 6.12f, input);
        else return getBinomial(6f, -10.4f, 5.4f, input);
    }

    private float getBinomial(float a, float b, float c, float t) {
        return a * t * t + b * t + c;
    }
}
