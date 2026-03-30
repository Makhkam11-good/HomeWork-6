package com.narxoz.rpg.chain;

import com.narxoz.rpg.arena.ArenaFighter;

public class BlockHandler extends DefenseHandler {
    private final double blockPercent;

    public BlockHandler(double blockPercent) {
        this.blockPercent = blockPercent;
    }

    @Override
    public void handle(int incomingDamage, ArenaFighter target) {
        int damage = Math.max(0, incomingDamage);
        int blocked = (int) (damage * blockPercent);
        int remaining = Math.max(0, damage - blocked);
        blocked = damage - remaining;
        System.out.println("[Block] Blocked: " + blocked);
        if (getNext() != null) {
            getNext().handle(remaining, target);
        }
    }
}
