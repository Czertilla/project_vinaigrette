package com.czertilla.project_vinaigrette.asset;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.utils.I18NBundle;

public class Bundle extends I18NBundle{
    static private I18NBundle instance;


    public static I18NBundle getInstance() {
        if (instance == null)
            new Bundle();
        return instance;
    }

    private Bundle(){
        FileHandle baseFileHandle = Gdx.files.internal("strings/strings");
        instance = I18NBundle.createBundle(baseFileHandle);
    }
}
