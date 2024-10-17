package com.czertilla.project_vinaigrette.asset;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.utils.I18NBundle;
import com.badlogic.gdx.utils.Null;

import java.util.Locale;

public class Bundle extends I18NBundle{
    static private I18NBundle instance;
    static private Locale locale;


    public static I18NBundle getInstance() {
        if (instance == null)
            new Bundle();
        return instance;
    }

    public static void setLocale(Locale locale) {
        Bundle.locale = locale;
        new Bundle();
    }

    @Null
    public static Locale getCurrLocale() {
        if (instance != null)
            return instance.getLocale();
        return null;
    }

    private Bundle(){
        FileHandle baseFileHandle = Gdx.files.internal("strings/strings");
        if (locale == null)
            instance = I18NBundle.createBundle(baseFileHandle);
        else
            instance = I18NBundle.createBundle(baseFileHandle, locale);

    }
}
