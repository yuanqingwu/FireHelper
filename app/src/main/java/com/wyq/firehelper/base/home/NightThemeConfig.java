package com.wyq.firehelper.base.home;

import android.content.Context;
import android.content.SharedPreferences;
import androidx.appcompat.app.AppCompatDelegate;

public class NightThemeConfig {
    private SharedPreferences preferences = null;
    private final String NIGHT_THEME_CONFIG = "NIGHT_THEME_CONFIG";
    private final String NIGHT_MODE = "NIGHT_MODE";

    private static NightThemeConfig themeConfig;

    private NightThemeConfig(Context context) {
        preferences = context.getSharedPreferences(NIGHT_THEME_CONFIG, Context.MODE_PRIVATE);
    }

    public static NightThemeConfig getInstance(Context context) {
        if (themeConfig == null) {
            synchronized (NightThemeConfig.class) {
                if (themeConfig == null) {
                    themeConfig = new NightThemeConfig(context);
                }
            }
        }
        return themeConfig;
    }


    public int getNightMode() {
        return preferences.getInt(NIGHT_MODE, AppCompatDelegate.getDefaultNightMode());
    }

    public void setNightMode(@AppCompatDelegate.NightMode int nightMode){
        SharedPreferences.Editor editor = preferences.edit();
        editor.putInt(NIGHT_MODE,nightMode);
        editor.commit();
    }
}
