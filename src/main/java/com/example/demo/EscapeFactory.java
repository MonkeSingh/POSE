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
    public Entity newBat(SpawnData data) {
        return entityBuilder(data)
//                .at(100, 100)
                .viewWithBBox("test2.png")
                .with(new CollidableComponent(true))
                .scale(0.2, 0.2)
                .type(EntityTypes.PLAYER)
                .build();
    }

}
