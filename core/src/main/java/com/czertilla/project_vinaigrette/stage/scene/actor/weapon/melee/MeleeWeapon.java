package com.czertilla.project_vinaigrette.stage.scene.actor.weapon.melee;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.TimeUtils;
import com.czertilla.project_vinaigrette.stage.scene.BaseScene;
import com.czertilla.project_vinaigrette.stage.scene.actor.AttackBoxActor;
import com.czertilla.project_vinaigrette.stage.scene.actor.BaseActor;
import com.czertilla.project_vinaigrette.stage.scene.actor.weapon.Weapon;

public class MeleeWeapon extends BaseActor implements Weapon {

    protected boolean attacking = false;
    protected long lastAttackTime = 0;
    protected long attackDuration = 200;   // длительность атаки (мс)
    protected long attackCooldown = 500;   // кулдаун между атаками (мс)

    protected AttackBoxActor attackBox;
    protected float attackBoxWidth = 170f;
    protected float attackBoxHeight = 100f;

    // 8 направлений
    protected enum Direction {N, NE, E, SE, S, SW, W, NW}
    protected Direction currentDirection = Direction.E;

    public MeleeWeapon(TextureRegion region, BaseScene stage) {
        super(region);
        TextureRegion em = new TextureRegion(new Texture(Gdx.files.internal("ui/empty.png")));
        TextureRegion boxRegion = new TextureRegion(em, 0,0,1,1);
        attackBox = new AttackBoxActor(boxRegion, attackBoxWidth, attackBoxHeight);
        attackBox.setOrigin(attackBoxWidth/2f, attackBoxHeight/2f);
        stage.addActor(attackBox);
        attackBox.setVisible(false);
    }

    @Override
    public void mainAttack(Vector3 destination) {
        if (canAttack()) {
            startAttack();
        }
    }

    @Override
    public void secondaryAttack(Vector3 destination) {
        if (canAttack()) {
            startAttack();
        }
    }

    @Override
    public void reload() {}

    @Override
    public void update(float delta) {
        Vector3 dest = ((BaseScene)getStage()).getMousePos();
        rotateTowards(dest.x, dest.y);

        float angle = getRotation();
        angle = normalizeAngle(angle);

        Direction dir = getDirectionFromAngle(angle);
        setDirection(dir);

        if (attacking) {
            long currentTime = TimeUtils.millis();
            if (currentTime - lastAttackTime > attackDuration) {
                attacking = false;
                attackBox.setVisible(false);
            }
        }

        updateAttackBoxPosition();
    }

    private boolean canAttack() {
        long currentTime = TimeUtils.millis();
        return !attacking && (currentTime - lastAttackTime > attackCooldown);
    }

    private void startAttack() {
        attacking = true;
        lastAttackTime = TimeUtils.millis();
        attackBox.setVisible(true);
    }

    private void updateAttackBoxPosition() {
        float weaponX = getX();
        float weaponY = getY();
        float weaponW = getWidth();
        float weaponH = getHeight();

        float boxX = weaponX;
        float boxY = weaponY;
        float offset = 5f; // смещение, чтобы не было наложения

        switch (currentDirection) {
            case E:
                // Справа от оружия, с небольшим отступом
                boxX = weaponX + weaponW + offset;
                boxY = weaponY + (weaponH - attackBoxHeight) / 2f;
                break;
            case W:
                // Слева от оружия, с отступом
                boxX = weaponX - attackBoxWidth - offset;
                boxY = weaponY + (weaponH - attackBoxHeight) / 2f;
                break;
            case N:
                // Сверху от оружия, с отступом
                boxY = weaponY + weaponH + offset;
                boxX = weaponX + (weaponW - attackBoxWidth) / 2f;
                break;
            case S:
                // Снизу от оружия, с отступом
                boxY = weaponY - attackBoxHeight - offset;
                boxX = weaponX + (weaponW - attackBoxWidth) / 2f;
                break;
            case NE:
                // По диагонали сверху-справа, добавляем отступ
                boxX = weaponX + weaponW - attackBoxWidth / 2f + offset;
                boxY = weaponY + weaponH - attackBoxHeight / 2f + offset;
                break;
            case NW:
                // По диагонали сверху-слева, отступ
                boxX = weaponX - attackBoxWidth + attackBoxWidth / 2f - offset;
                boxY = weaponY + weaponH - attackBoxHeight / 2f + offset;
                break;
            case SE:
                // По диагонали снизу-справа, отступ
                boxX = weaponX + weaponW - attackBoxWidth / 2f + offset;
                boxY = weaponY - attackBoxHeight + attackBoxHeight / 2f - offset;
                break;
            case SW:
                // По диагонали снизу-слева, отступ
                boxX = weaponX - attackBoxWidth + attackBoxWidth / 2f - offset;
                boxY = weaponY - attackBoxHeight + attackBoxHeight / 2f - offset;
                break;
        }


        attackBox.setPosition(boxX, boxY);
        float dirAngle = getRotation();
        float boxAngle = dirAngle + 90; // поворачиваем на 90°, чтобы большая сторона была обращена к игроку

        boxAngle = normalizeAngle(boxAngle);
        attackBox.setRotation(boxAngle);
    }

    private Direction getDirectionFromAngle(float angle) {
        if (angle > -22.5 && angle <= 22.5) return Direction.E;
        if (angle > 22.5 && angle <= 67.5) return Direction.NE;
        if (angle > 67.5 && angle <= 112.5) return Direction.N;
        if (angle > 112.5 && angle <= 157.5) return Direction.NW;
        if (angle > 157.5 || angle <= -157.5) return Direction.W;
        if (angle > -157.5 && angle <= -112.5) return Direction.SW;
        if (angle > -112.5 && angle <= -67.5) return Direction.S;
        if (angle > -67.5 && angle <= -22.5) return Direction.SE;

        return Direction.E;
    }

    private void setDirection(Direction dir) {
        this.currentDirection = dir;
        switch (dir) {
            case E:  setRotation(0); break;
            case NE: setRotation(45); break;
            case N:  setRotation(90); break;
            case NW: setRotation(135); break;
            case W:  setRotation(180); break;
            case SW: setRotation(-135); break;
            case S:  setRotation(-90); break;
            case SE: setRotation(-45); break;
        }
    }

    private float normalizeAngle(float angle) {
        while (angle > 180) angle -= 360;
        while (angle <= -180) angle += 360;
        return angle;
    }
}
