package com.narxoz.rpg.chain;

import com.narxoz.rpg.arena.ArenaFighter;

public class ArmorHandler extends DefenseHandler {
    private final int armorValue;

    public ArmorHandler(int armorValue) {
        this.armorValue = armorValue;
    }

    @Override
    public void handle(int incomingDamage, ArenaFighter target) {
        int damage = Math.max(0, incomingDamage);
        int remaining = Math.max(0, damage - armorValue);
        int absorbed = damage - remaining;
        System.out.println("[Armor] Absorbed: " + absorbed);
        if (getNext() != null) {
            getNext().handle(remaining, target);
        }
    }
}
