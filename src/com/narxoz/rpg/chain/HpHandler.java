package com.narxoz.rpg.chain;

import com.narxoz.rpg.arena.ArenaFighter;

public class HpHandler extends DefenseHandler {

    @Override
    public void handle(int incomingDamage, ArenaFighter target) {
        int damage = Math.max(0, incomingDamage);
        target.takeDamage(damage);
        System.out.println("[HP] Damage landed: " + damage);
    }
}
