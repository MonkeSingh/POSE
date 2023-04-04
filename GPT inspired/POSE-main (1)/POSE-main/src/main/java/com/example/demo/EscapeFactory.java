package com.example.demo;

import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.entity.EntityFactory;
import com.almasb.fxgl.entity.SpawnData;
import com.almasb.fxgl.entity.Spawns;
import com.almasb.fxgl.entity.components.CollidableComponent;

import static com.almasb.fxgl.dsl.FXGLForKtKt.entityBuilder;

public class EscapeFactory implements EntityFactory {

    @Spawns("player")
    public Entity newPlayer(SpawnData data) {
        return entityBuilder(data)
                .viewWithBBox("larry.gif") // Replace with your player image filename
                .with(new CollidableComponent(true))
                .scale(0.2, 0.2)
                .type(EntityTypes.PLAYER)
                .build();
    }

    @Spawns("guard")
    public Entity newGuard(SpawnData data) {
        return entityBuilder(data)
                .viewWithBBox("larryB.gif") // Replace with your guard image filename
                .with(new CollidableComponent(true))
                .scale(0.2, 0.2)
                .type(EntityTypes.GUARD)
                .build();
    }

    @Spawns("key")
    public Entity newKey(SpawnData data) {
        return entityBuilder(data)
                .viewWithBBox("key.png") // Replace with your key image filename
                .with(new CollidableComponent(true))
                .scale(0.5, 0.5)
                .type(EntityTypes.KEY)
                .build();
    }

    @Spawns("door")
    public Entity newDoor(SpawnData data) {
        return entityBuilder(data)
                .viewWithBBox("door.jpg") // Replace with your door image filename
                .with(new CollidableComponent(true))
                .scale(0.2, 0.2)
                .type(EntityTypes.DOOR)
                .build();
    }

}
