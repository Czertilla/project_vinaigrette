package com.czertilla.project_vinaigrette.utils;

import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.utils.I18NBundle;

public class R {
    public static class path {
        private static final I18NBundle bundle = I18NBundle.createBundle(
            new FileHandle("strings/paths")
        );

        public static final String MAIN_MENU_BACKGROUND = bundle.get("MAIN_MENU_BACKGROUND");
        public static final String BUTTON_DRAWABLE_REGION = bundle.get("BUTTON_DRAWABLE_REGION");
        public static final String PIXEL_FONT = bundle.get("PIXEL_FONT");
        public static final String STRINGS_HANDLE = bundle.get("STRINGS_HANDLE");
        public static final String CHARSET_HANDLE = bundle.get("CHARSET_HANDLE");
        public static final String PATHS_HANDLE = bundle.get("PATHS_HANDLE");
    }

    public static final class id {
        public static final String CONTINUE = "BUTTONS/MENU/CONTINUE";
        public static final String NEW_GAME = "BUTTONS/MENU/NEW_GAME";
        public static final String SETTINGS = "BUTTONS/MENU/SETTINGS";
        public static final String BACK = "BUTTONS/MENU/BACK";
        public static final String QUIT = "BUTTONS/MENU/QUIT";
        public static final String VIDEO = "BUTTONS/MENU/VIDEO";
        public static final String DEBUG = "BUTTONS/MENU/DEBUG";
        public static final String FULLSCREEN = "BUTTONS/MENU/FULLSCREEN";
        public static final String SWITCH_ON = "BUTTONS/MENU/SWITCH/ON";
        public static final String SWITCH_OFF = "BUTTONS/MENU/SWITCH/OFF";
        public static final String SCALE = "BUTTONS/MENU/SCALE";
        public static final String CHARSET = "CHARSET";
    }
}
