package com.czertilla.project_vinaigrette.handler;

import com.czertilla.project_vinaigrette.stage.scene.actor.BaseActor;
import com.czertilla.project_vinaigrette.stage.scene.actor.PlayerActor;

public class AnimationHandler{
    private String name;
    public AnimationHandler(String name){
        this.name = name;
    }
    public void down( BaseActor actor){
        actor.Animation(name,"down",6);
    }
    public void up( BaseActor actor){
        actor.Animation(name,"up",6);
    }
    public void left( BaseActor actor){
        actor.Animation(name,"left",6);
    }
    public void right( BaseActor actor){
        actor.Animation(name,"right",6);
    }
    public void idle_down( BaseActor actor){
        actor.Animation(name, "idle_down", 6);
    }
    public void idle_up( BaseActor actor){
        actor.Animation(name,"idle_up",6);
    }
    public void idle_left( BaseActor actor){
        actor.Animation(name,"idle_left",6);
    }
    public void idle_right( BaseActor actor){
        actor.Animation(name,"idle_right",6);
    }

}
