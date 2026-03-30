package com.narxoz.rpg.chain;

import com.narxoz.rpg.arena.ArenaFighter;

public abstract class DefenseHandler {
    private DefenseHandler next;

    public DefenseHandler setNext(DefenseHandler next) {
        // Provided: fluent setter so chains can be built like:
        //   dodge.setNext(block).setNext(armor).setNext(hp)
        // setNext returns the argument so the chain reads left-to-right.
        this.next = next;
        return next;
    }

    protected DefenseHandler getNext() {
        return next;
    }

    protected void passToNext(int damage, ArenaFighter target) {
        if (damage > 0 && next != null) {
            next.handle(damage, target);
        }
    }

    public abstract void handle(int incomingDamage, ArenaFighter target);
}
