package controller.human;


import javafx.util.Pair;
import model.activity.Move;
import model.human.military.Military;

import java.util.ArrayList;
import java.util.LinkedList;


public class HumanController {
    public static ArrayList<Military> militaries;

    public static boolean move(Pair<Integer, Integer> endPair) {

        Pair<Integer, Integer> startPair = getStartPair();
        LinkedList<Pair<Integer, Integer>> path = MoveController.checkHasPath(startPair, endPair);
        LinkedList<Pair<Integer, Integer>> assassinPath = checkHasLadderPath(startPair, endPair, path);
        LinkedList<Pair<Integer, Integer>> ladderPath = checkAssassinPath(startPair, endPair, path);


        if (path == null && assassinPath == null && ladderPath == null) {
            return false;
        }
        if (path == null) {
            for (Military military : militaries) {
                if (military.isUsesLadder()) {
                    Move move = new Move(military.getX(), military.getY(), endPair, true, military);
                    move.setPath(ladderPath);
                    military.setMove(move);

                }

                if (military.isUsesLadder()) {
                    Move move = new Move(military.getX(), military.getY(), endPair, true, military);
                    move.setPath(assassinPath);
                    military.setMove(move);

                }
            }
        } else {
            for (Military military : militaries) {
                Move move = new Move(military.getX(), military.getY(), endPair, true, military);
                move.setPath(path);
                military.setMove(move);
            }
        }
        return true;
    }

    public static boolean attack(Military enemy) {
        Pair<Integer, Integer> startPair = getStartPair();
        Pair<Integer, Integer> endPair = new Pair<>(enemy.getY(), enemy.getX());
        LinkedList<Pair<Integer, Integer>> path = MoveController.checkHasPath(startPair, endPair);
        LinkedList<Pair<Integer, Integer>> assassinPath = checkHasLadderPath(startPair, endPair, path);
        LinkedList<Pair<Integer, Integer>> ladderPath = checkAssassinPath(startPair, endPair, path);

        if (path == null && assassinPath == null && ladderPath == null) {
            return false;
        }

        if (path == null) {
            for (Military military : militaries) {
                if (military.isUsesLadder()) {
                    Move move = new Move(military.getX(), military.getY(), enemy, false, military);
                    move.setPath(ladderPath);
                    military.setMove(move);

                }

                if (military.isUsesLadder()) {
                    Move move = new Move(military.getX(), military.getY(), enemy, false, military);
                    move.setPath(assassinPath);
                    military.setMove(move);

                }
            }
        } else {
            for (Military military : militaries) {
                Move move = new Move(military.getX(), military.getY(), enemy, false, military);
                move.setPath(path);
                military.setMove(move);
            }
        }
        return true;
    }

    public static boolean patrolUnit(int x1, int y1, int x2, int y2) {

        Pair<Integer, Integer> patrolPair = new Pair<>(x2, y2);
        Pair<Integer, Integer> endPair = new Pair<>(x1, y1);
        Pair<Integer, Integer> startPair = getStartPair();
        LinkedList<Pair<Integer, Integer>> path = MoveController.checkHasPath(startPair, endPair);
        LinkedList<Pair<Integer, Integer>> assassinPath = checkHasLadderPath(startPair, endPair, path);
        LinkedList<Pair<Integer, Integer>> ladderPath = checkAssassinPath(startPair, endPair, path);

        if (path == null && assassinPath == null && ladderPath == null) {
            return false;
        }

        if (!checkPatrolPath(endPair, patrolPair)) {
            return false;
        }
        if (path == null) {
            for (Military military : militaries) {
                if (military.isUsesLadder()) {
                    Move move = new Move(military.getX(), military.getY(), endPair, true, military);
                    move.setPath(ladderPath);
                    move.setMovePatrol(patrolPair);
                    military.setMove(move);
                }

                if (military.isUsesLadder()) {
                    Move move = new Move(military.getX(), military.getY(), endPair, true, military);
                    move.setPath(assassinPath);
                    move.setMovePatrol(patrolPair);
                    military.setMove(move);

                }
            }
        } else {
            for (Military military : militaries) {
                Move move = new Move(military.getX(), military.getY(), endPair, true, military);
                move.setPath(path);
                move.setMovePatrol(patrolPair);
                military.setMove(move);
            }
        }
        return true;
    }

    public static LinkedList<Pair<Integer, Integer>> checkHasLadderPath(Pair<Integer, Integer> startPair, Pair<Integer, Integer> endPair, LinkedList<Pair<Integer, Integer>> path) {
        if (path != null) {
            return path;
        }
        for (Military military : militaries) {
            if (military.isUsesLadder()) {
                return MoveController.getPath(startPair, endPair, military);
            }
        }
        return null;
    }

    public static LinkedList<Pair<Integer, Integer>> checkAssassinPath(Pair<Integer, Integer> startPair, Pair<Integer, Integer> endPair, LinkedList<Pair<Integer, Integer>> path) {
        if (path != null) {
            return path;
        }
        for (Military military : militaries) {
            if (military.getName().equals("assassin")) {
                return MoveController.getPath(startPair, endPair, military);
            }
        }
        return null;
    }

    public static Pair<Integer, Integer> getStartPair() {
        Military firstMilitary = militaries.get(0);
        return new Pair<>(firstMilitary.getX(), firstMilitary.getY());
    }

    public static boolean checkPatrolPath(Pair<Integer, Integer> startPair, Pair<Integer, Integer> endPair) {
        LinkedList<Pair<Integer, Integer>> path = MoveController.checkHasPath(startPair, endPair);
        LinkedList<Pair<Integer, Integer>> assassinPath = checkHasLadderPath(startPair, endPair, path);
        LinkedList<Pair<Integer, Integer>> ladderPath = checkAssassinPath(startPair, endPair, path);
        if (path == null && assassinPath == null && ladderPath == null) {
            return false;
        }
        return true;
    }

}
