package com.czertilla.project_vinaigrette.utils;

import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.utils.I18NBundle;

public class C {
    private static final I18NBundle
        stringBundle = I18NBundle.createBundle(new FileHandle(R.path.STRINGS_HANDLE)),
        layoutBundle = I18NBundle.createBundle(new FileHandle(R.path.LAYOUT_HANDLE)),
        videoBundle = I18NBundle.createBundle(new FileHandle(R.path.VIDEO_HANDLE));
    public static final String[] LANG_CODES = stringBundle.get(R.id.LANG_CODES).split(";");
    public static final float
        FRAMES_LOCK = Float.parseFloat((videoBundle.get(R.id.FRAMES_LOCK))),
        MENU_BUTTONS_WIDTH = Float.parseFloat(layoutBundle.get(R.id.MENU_BUTTONS_WIDTH)),
        MENU_BUTTONS_HEIGHT = Float.parseFloat(layoutBundle.get(R.id.MENU_BUTTONS_HEIGHT));
    public static final int
        FONT_SIZE = Integer.parseInt(layoutBundle.get(R.id.FONT_SIZE)),
        DEFAULT_WIDTH = Integer.parseInt(videoBundle.get(R.id.DEFAULT_WIDTH)),
        DEFAULT_HEIGHT = Integer.parseInt(videoBundle.get(R.id.DEFAULT_HEIGHT));
    public static final float[] SCALE_ARRAY = getScaleArray();
    private static float[] getScaleArray() {
        String[] values = layoutBundle.get(R.id.SCALE_ARRAY).split(",");
        float[] data = new float[values.length];
        for (int i=0; i < values.length; i++)
            data[i] = Float.parseFloat(values[i]);
        return data;
    }

}
