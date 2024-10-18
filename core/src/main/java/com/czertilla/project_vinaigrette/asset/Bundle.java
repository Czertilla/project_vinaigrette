package com.czertilla.project_vinaigrette.asset;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.utils.I18NBundle;
import java.util.Locale;

public class Bundle extends I18NBundle{
    static private I18NBundle instance;
    static private final String[] langCodes = {"en", "ru"};
    static private String[] langList;
    static final FileHandle baseFileHandle = Gdx.files.internal("strings/strings");

    public static I18NBundle getInstance() {
        if (instance == null)
            new Bundle();
        return instance;
    }

    public static String[] getLangLIst(){
        if (langList == null){
            langList = new String[langCodes.length];
            for (int i=0; i < langCodes.length; i++){
                langList[i] = I18NBundle.createBundle(
                        baseFileHandle,
                        new Locale(langCodes[i])
                ).get("/");
            }
        }
        return langList;
    }
    private Bundle(){
        instance = I18NBundle.createBundle(baseFileHandle);
    }
}
