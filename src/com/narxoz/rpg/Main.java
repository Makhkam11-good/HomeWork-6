package com.narxoz.rpg;

import com.narxoz.rpg.arena.ArenaFighter;
import com.narxoz.rpg.arena.ArenaOpponent;
import com.narxoz.rpg.arena.TournamentResult;
import com.narxoz.rpg.chain.ArmorHandler;
import com.narxoz.rpg.chain.BlockHandler;
import com.narxoz.rpg.chain.DefenseHandler;
import com.narxoz.rpg.chain.DodgeHandler;
import com.narxoz.rpg.chain.HpHandler;
import com.narxoz.rpg.command.ActionQueue;
import com.narxoz.rpg.command.AttackCommand;
import com.narxoz.rpg.command.DefendCommand;
import com.narxoz.rpg.command.HealCommand;
import com.narxoz.rpg.tournament.TournamentEngine;

public class Main {
    public static void main(String[] args) {
        System.out.println("=== Homework 6 Demo: Chain of Responsibility + Command ===\n");

        ArenaFighter hero = new ArenaFighter("Hero", 100, 0.20, 25, 5, 18, 3);
        ArenaOpponent opponent = new ArenaOpponent("Champion", 90, 14);

        System.out.println("--- Command Queue Demo ---");

        ActionQueue queue = new ActionQueue();
        queue.enqueue(new AttackCommand(opponent, hero.getAttackPower()));
        queue.enqueue(new HealCommand(hero, 20));
        queue.enqueue(new DefendCommand(hero, 0.15));

        printDescriptions("Queued actions:", queue);
        System.out.println("\nUndoing last queued action...");
        queue.undoLast();
        printDescriptions("Queue after undo:", queue);

        System.out.println("\nExecuting all queued commands...");
        queue.executeAll();
        System.out.println("Hero HP after queue: " + hero.getHealth());
        System.out.println("Opponent HP after queue: " + opponent.getHealth());

        System.out.println("\n--- Defense Chain Demo ---");

        ArenaFighter chainHero = new ArenaFighter("Sentinel", 100, 0.20, 25, 5, 18, 3);
        DefenseHandler chain = buildDefenseChain(chainHero, 99L);

        System.out.println("Sending 20 incoming damage through the defense chain...");
        System.out.println("Hero HP before: " + chainHero.getHealth());
        chain.handle(20, chainHero);
        System.out.println("Hero HP after:  " + chainHero.getHealth());

        System.out.println("\n--- Full Arena Tournament ---");

        ArenaFighter tournamentHero = new ArenaFighter("Erlan", 120, 0.25, 25, 8, 22, 3);
        ArenaOpponent tournamentOpponent = new ArenaOpponent("Iron Vane", 100, 16);

        TournamentResult result = new TournamentEngine(tournamentHero, tournamentOpponent)
                .setRandomSeed(42L)
                .runTournament();

        System.out.println("Winner : " + result.getWinner());
        System.out.println("Rounds : " + result.getRounds());
        System.out.println("Battle log:");
        for (String line : result.getLog()) {
            System.out.println("  " + line);
        }

        System.out.println("\n=== Demo Complete ===");
    }

    private static DefenseHandler buildDefenseChain(ArenaFighter hero, long seed) {
        DefenseHandler dodge = new DodgeHandler(hero.getDodgeChance(), seed);
        DefenseHandler block = new BlockHandler(hero.getBlockRating() / 100.0);
        DefenseHandler armor = new ArmorHandler(hero.getArmorValue());
        DefenseHandler hp = new HpHandler();
        dodge.setNext(block).setNext(armor).setNext(hp);
        return dodge;
    }

    private static void printDescriptions(String title, ActionQueue queue) {
        System.out.println(title);
        for (String description : queue.getCommandDescriptions()) {
            System.out.println("  " + description);
        }
    }
}
