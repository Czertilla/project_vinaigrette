package com.czertilla.project_vinaigrette.handler;

import com.czertilla.project_vinaigrette.stage.scene.actor.BaseActor;
import com.czertilla.project_vinaigrette.stage.scene.actor.PlayerActor;

public class AnimationHandler{
    public AnimationHandler(){
    }
    public void down( BaseActor actor){
        if (actor instanceof PlayerActor) {
            actor.Animation("player_down.atlas","walking_dude_Sheet",6);
        }

    }
}
