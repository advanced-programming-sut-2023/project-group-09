package view.controllers;

import controller.GameController;
import controller.MapController;
import controller.gamestructure.GameHumans;
import controller.gamestructure.GameImages;
import controller.human.HumanController;
import enumeration.MilitaryStates;
import enumeration.MoveStates;
import enumeration.Paths;
import enumeration.UnitMovingState;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.effect.BlurType;
import javafx.scene.effect.DropShadow;
import javafx.scene.effect.InnerShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.util.Duration;
import model.building.Building;
import model.game.Tile;
import model.human.civilian.Civilian;
import model.human.military.Engineer;
import model.human.military.Military;
import model.menugui.game.*;
import view.menus.GameMenu;
import view.menus.LoginMenu;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Objects;
import java.util.Set;

public class HumanViewController {
    //--------------------------------------------------------
    public static ArrayList<TypeBTN> typeBTNS = new ArrayList<>();
    public static ArrayList<ImageView> moveBTNS = new ArrayList<>();
    public static TypeBTN lastType;
    public static ArrayList<Military> selectedMilitaries = new ArrayList<>();

    //----------------------------------------------------------
    public static void setCenterOfUnitMenu() {
        GameMenu.barImage.setImage(GameImages.imageViews.get("unit bar"));
        addTypes();
        setSelectedUnits();
        putDisband();
        putPatrol();
        putStop();
        putStand();
        putDefensive();
        putAggressive();
        updateStateOfMilitary();

        ArrayList<Engineer> engineers = new ArrayList<>();
        for (Military military : selectedMilitaries) {
            if (military instanceof Engineer) engineers.add((Engineer) military);
        }
        if (engineers.size() != 0) {
            putBuild();
        }
    }

    public static void addTypes() {
        double translateY = 100;
        double translateX = 290;
        int count = 0;
        for (String name : GameMenu.unitsCount.keySet()) {
            if (GameMenu.unitsCount.get(name) != 0) {
                System.out.println(name);
                TypeBTN btn = new TypeBTN(name, GameMenu.menuBar, GameMenu.unitsCount.get(name), translateX, translateY);
                translateX += 62;
                GameViewController.setHoverEventForBar(btn.imageView, name);
                typeBTNS.add(btn);
                DropShadow ds = new DropShadow(20, Color.AQUA);
                btn.imageView.setOnMouseClicked(mouseEvent -> {
                    for (TypeBTN typeBTN : typeBTNS) {
                        typeBTN.imageView.setEffect(null);
                    }
                    btn.imageView.setEffect(ds);
                    lastType = btn;
                });
                count++;
                if (count > 7) {
                    return;
                }
            }
        }
    }

    public static void putDisband() {
        ImageView icon = new ImageView(LoginMenu.class.getResource(Paths.BAR_IMAGES.getPath())
                .toExternalForm() + "icons/disband.png");
        icon.setTranslateX(127);
        icon.setTranslateY(127);
        icon.setScaleY(1.1);
        icon.setScaleX(1.2);
        GameMenu.menuBar.getChildren().add(icon);
        GameViewController.setHoverEventForBar(icon, "disband");
    }

    public static void putStop() {
        ImageView icon = new ImageView(LoginMenu.class.getResource(Paths.BAR_IMAGES.getPath())
                .toExternalForm() + "icons/stop.png");
        icon.setTranslateX(177);
        icon.setTranslateY(127);
        icon.setScaleY(1.1);
        icon.setScaleX(1.2);
        GameMenu.menuBar.getChildren().add(icon);
        GameViewController.setHoverEventForBarOnUnitMenu(icon, "stop");
        System.out.println(selectedMilitaries.size());
        icon.setOnMouseClicked(mouseEvent -> {
            setSelectedUnits();
            stopTroops();
        });
        icon.setOnMousePressed(mouseEvent -> {
            GameMenu.movingState = MoveStates.PATROL.getState();
            icon.setEffect(new InnerShadow(BlurType.THREE_PASS_BOX, Color.GRAY, 10, 0, 0, 0));
        });
        icon.setOnMouseReleased(mouseEvent -> {
            GameMenu.movingState = MoveStates.PATROL.getState();
            icon.setEffect(null);
        });
    }

    public static void putPatrol() {
        ImageView icon = new ImageView(LoginMenu.class.getResource(Paths.BAR_IMAGES.getPath())
                .toExternalForm() + "icons/patrol.png");
        icon.setTranslateX(225);
        icon.setTranslateY(127);
        icon.setScaleY(1.1);
        icon.setScaleX(1.3);
        GameMenu.menuBar.getChildren().add(icon);
        GameViewController.setHoverEventForBarOnUnitMenu(icon, "patrol");
        icon.setOnMouseClicked(mouseEvent -> {
            GameMenu.movingState = MoveStates.PATROL.getState();
        });
        icon.setOnMousePressed(mouseEvent -> {
            GameMenu.movingState = MoveStates.PATROL.getState();
            icon.setEffect(new InnerShadow(BlurType.THREE_PASS_BOX, Color.GRAY, 10, 0, 0, 0));
        });
        icon.setOnMouseReleased(mouseEvent -> {
            GameMenu.movingState = MoveStates.PATROL.getState();
            icon.setEffect(null);
        });
    }

    public static void putBuild() {
        ImageView icon = new ImageView(LoginMenu.class.getResource(Paths.BAR_IMAGES.getPath())
                .toExternalForm() + "icons/engineer.png");
        icon.setTranslateX(127);
        icon.setTranslateY(167);
        icon.setScaleY(1.1);
        icon.setScaleX(1.3);
        GameMenu.menuBar.getChildren().add(icon);
        GameViewController.setHoverEventForBarOnUnitMenu(icon, "engineer");
        icon.setOnMouseClicked(mouseEvent -> {
//            TODO
        });
        icon.setOnMousePressed(mouseEvent -> {
//            TODO
            icon.setEffect(new InnerShadow(BlurType.THREE_PASS_BOX, Color.GRAY, 10, 0, 0, 0));
        });
        icon.setOnMouseReleased(mouseEvent -> {
            icon.setEffect(null);
        });
    }

    public static void putStand() {
        ImageView icon = new ImageView(LoginMenu.class.getResource(Paths.BAR_IMAGES.getPath())
                .toExternalForm() + "icons/stand-ground.png");
        icon.setTranslateX(127);
        icon.setTranslateY(50);
        icon.setScaleY(1.3);
        icon.setScaleX(1.3);
        GameViewController.setHoverEventForBarOnUnitMenu(icon, "stand ground");
        GameMenu.standing = icon;
        icon.setOnMouseClicked(mouseEvent -> {
            setMilitaryState(MilitaryStates.STAND_GROUND.getState());
            GameViewController.unselectTiles();
        });
        GameMenu.menuBar.getChildren().add(icon);
    }

    public static void putDefensive() {
        ImageView icon = new ImageView(LoginMenu.class.getResource(Paths.BAR_IMAGES.getPath())
                .toExternalForm() + "icons/defensive-state.png");
        icon.setTranslateX(177);
        icon.setTranslateY(70);
        icon.setScaleY(1.3);
        icon.setScaleX(1.3);
        GameMenu.defensive = icon;
        GameViewController.setHoverEventForBar(icon, "defensive state");
        icon.setOnMouseClicked(mouseEvent -> {
            setMilitaryState(MilitaryStates.DEFENSIVE_STANCE.getState());
            GameViewController.unselectTiles();
        });
        GameMenu.menuBar.getChildren().add(icon);
    }

    public static void putAggressive() {
        ImageView icon = new ImageView(LoginMenu.class.getResource(Paths.BAR_IMAGES.getPath())
                .toExternalForm() + "icons/aggressive-state.png");
        icon.setTranslateX(225);
        icon.setTranslateY(65);
        icon.setScaleY(1.3);
        icon.setScaleX(1.3);
        GameMenu.aggressive = icon;
        GameViewController.setHoverEventForBar(icon, "offensive state");
        icon.setOnMouseClicked(mouseEvent -> {
            setMilitaryState(MilitaryStates.AGGRESSIVE_STANCE.getState());
            GameViewController.unselectTiles();
        });
        GameMenu.menuBar.getChildren().add(icon);
    }

    public static void setMilitaryState(String state) {
        HumanController.setState(state, selectedMilitaries);
        setSelectedUnits();
    }

    public static void updateStateOfMilitary() {
        if (GameMenu.aggressive == null || GameMenu.defensive == null || GameMenu.standing == null) {
            return;
        }
        if (HumanController.militaries.size() != 0) {
            Military military = HumanController.militaries.get(0);
            DropShadow ds = new DropShadow(20, Color.AQUA);
            if (military.getMilitaryState().equals(MilitaryStates.STAND_GROUND.getState())) {
                GameMenu.standing.setEffect(ds);
                GameMenu.defensive.setEffect(null);
                GameMenu.aggressive.setEffect(null);
            }

            if (military.getMilitaryState().equals(MilitaryStates.DEFENSIVE_STANCE.getState())) {
                GameMenu.standing.setEffect(null);
                GameMenu.defensive.setEffect(ds);
                GameMenu.aggressive.setEffect(null);
            }
            if (military.getMilitaryState().equals(MilitaryStates.AGGRESSIVE_STANCE.getState())) {
                GameMenu.standing.setEffect(null);
                GameMenu.defensive.setEffect(null);
                GameMenu.aggressive.setEffect(ds);
            }
        }
    }

    public static void dropUnit(int x, int y, Tile tile, Military military) {
        GameTile gameTile = GameMap.getGameTile(x, y);
        GameMap gameMap = GameMenu.gameMap;
        Troop troop = new Troop(military, tile, gameTile);
        gameMap.getChildren().add(troop);
        if (GameMap.gameTroops[y][x] == null) {
            GameMap.gameTroops[y][x] = new ArrayList<>();
        }
        GameMap.gameTroops[y][x].add(troop);
    }

    public static void dropCivilian(int x, int y, Tile tile, Civilian civilian) {
        GameTile gameTile = GameMap.getGameTile(x, y);
        GameMap gameMap = GameMenu.gameMap;
        Citizen citizen = new Citizen(civilian, tile, gameTile);
        gameMap.getChildren().add(citizen);
    }

    public static void divideTroops(Tile tile) {
        Set<String> names = GameHumans.militaries.keySet();
        for (String name : names) {
            ArrayList<Military> troops = MapController.getOneTypeOfMilitariesOfGovernment(tile.x, tile.y, name,
                    GameController.getGame().getCurrentGovernment());
            GameMenu.selectedTroops.addAll(troops);
            GameMenu.unitsCount.put(name, GameMenu.unitsCount.getOrDefault(name, 0) + troops.size());
            if (troops.size() != 0) {
                GameMenu.selectedUnit = true;
                GameMenu.selectedTilesTroop.add(tile);
            }
        }
    }

    public static void setSelectCursorImage(String state) {
        GameMenu.selectCursor.setFill(new ImagePattern(GameImages.imageViews.get(state)));
    }

    public static void setSelectCursorState(GameTile endTile) {
        canDoAction(true, endTile);
    }


    public static boolean canDoAction(boolean changeCursor, GameTile endTile) {
        String state = GameMenu.movingState;
        if (Objects.equals(state, UnitMovingState.NORMAL.getState())) {
            if (checkCanAttack(endTile) || checkCanAirAttack(endTile)) {
                if (changeCursor) {
                    setSelectCursorImage("attack");
                }
                return true;
            }
            if (GameController.validateMoveUnit(endTile.getTileX(), endTile.getTileY())) {
                if (changeCursor) {
                    setSelectCursorImage("selectMove");
                }
                return true;
            }
            if (changeCursor) {
                setSelectCursorImage("cannot");
            }
            return false;
        }
        if (Objects.equals(state, UnitMovingState.MOVE.getState())) {
            if (GameController.validateMoveUnit(endTile.getTileX(), endTile.getTileY())) {
                if (changeCursor) {
                    setSelectCursorImage("selectMove");
                }
                return true;
            }
            if (changeCursor) {
                setSelectCursorImage("cannot");
            }
            return false;
        }
        if (Objects.equals(state, UnitMovingState.AIR_ATTACK.getState())) {
            if (checkCanAirAttack(endTile)) {
                if (changeCursor) {
                    setSelectCursorImage("attack");
                }
                return true;
            }
            if (changeCursor) {
                setSelectCursorImage("cannot");
            }
            return false;
        }

        if (Objects.equals(state, UnitMovingState.ATTACK.getState())) {
            if (checkCanAttack(endTile)) {
                if (changeCursor) {
                    setSelectCursorImage("attack");
                }
                return true;
            }
            if (changeCursor) {
                setSelectCursorImage("cannot");
            }
            return false;
        }

        return false;
    }


    public static boolean checkCanAttack(GameTile endTile) {
        if (GameController.validateAttackEnemy(endTile.getTileX(), endTile.getTileY())) {
            return true;
        }
        if (GameController.validateAttackBuilding(endTile.getTileX(), endTile.getTileY())) {
            System.out.println("check: " + endTile.getTileX() + " " + endTile.getTileY());
            return true;
        }
        return GameController.validateAttackTool(endTile.getTileX(), endTile.getTileY());
    }


    public static boolean checkCanAirAttack(GameTile endTile) {
        if (GameController.validateAirAttack(endTile.getTileX(), endTile.getTileY())) {
            return true;
        }
        if (GameController.validateAirAttackBuilding(endTile.getTileX(), endTile.getTileY())) {
            return true;
        }
        return GameController.validateAirAttackTool(endTile.getTileX(), endTile.getTileY());
    }

    public static void doAction(boolean changeCursor, GameTile endTile) {
        setSelectedUnits();
        String state = GameMenu.movingState;
        if (Objects.equals(state, UnitMovingState.NORMAL.getState())) {
            if (checkCanAttack(endTile) || checkCanAirAttack(endTile)) {
                if (changeCursor) {
                    setSelectCursorImage("attack");
                }
                attack(endTile);
                return;
            }
            if (GameController.validateMoveUnit(endTile.getTileX(), endTile.getTileY())) {
                if (changeCursor) {
                    setSelectCursorImage("selectMove");
                }
                moveUnits(endTile);
                return;
            }
            if (changeCursor) {
                setSelectCursorImage("cannot");
            }
            return;
        }
        if (Objects.equals(state, UnitMovingState.MOVE.getState())) {
            if (GameController.validateMoveUnit(endTile.getTileX(), endTile.getTileY())) {
                if (changeCursor) {
                    setSelectCursorImage("selectMove");
                }
                moveUnits(endTile);
                return;
            }
            if (changeCursor) {
                setSelectCursorImage("cannot");
            }
            return;
        }
        if (Objects.equals(state, UnitMovingState.AIR_ATTACK.getState())) {
            if (checkCanAirAttack(endTile)) {
                if (changeCursor) {
                    setSelectCursorImage("attack");
                    attack(endTile);
                }
                return;
            }
            if (changeCursor) {
                setSelectCursorImage("cannot");
            }
            return;
        }
        if (Objects.equals(state, UnitMovingState.ATTACK.getState())) {
            if (checkCanAttack(endTile)) {
                if (changeCursor) {
                    setSelectCursorImage("attack");
                }
                return;
            }
            if (changeCursor) {
                setSelectCursorImage("cannot");
            }
        }
        if (Objects.equals(state, UnitMovingState.PATROL.getState())) {
            if (GameController.validateMoveUnit(endTile.getTileX(), endTile.getTileY())) {
                if (changeCursor) {
                    setSelectCursorImage("selectMove");
                }
                patrolUnits(endTile);
                return;
            }
            if (changeCursor) {
                setSelectCursorImage("cannot");
            }
        }
    }


    public static void setSelectedUnits() {
        int count = 0;
        selectedMilitaries.clear();
        HashMap<String, Integer> unitCount = GameHumans.getUnitHashmap();
        for (TypeBTN btn : typeBTNS) {
            unitCount.put(btn.name, btn.count);
            count += btn.count;
        }
        for (Military military : GameMenu.selectedTroops) {
            if (unitCount.get(military.getName()) != 0) {
                selectedMilitaries.add(military);
                unitCount.put(military.getName(), unitCount.get(military.getName()) - 1);
                count--;
            }
            if (count == 0) {
                if (selectedMilitaries.size() != 0) {
                    HumanController.militaries.clear();
                    HumanController.militaries.add(selectedMilitaries.get(0));
                } else {
                    HumanController.militaries.clear();
                }
                updateStateOfMilitary();
                return;
            }
        }
        if (selectedMilitaries.size() != 0) {
            HumanController.militaries.clear();
            HumanController.militaries.add(selectedMilitaries.get(0));
        } else {
            HumanController.militaries.clear();
        }
        updateStateOfMilitary();
    }

    public static void moveUnits(GameTile end) {
        System.out.println(selectedMilitaries.size());
        for (Military military : selectedMilitaries) {
            HumanController.militaries.clear();
            HumanController.militaries.add(military);
            GameController.moveUnit(end.getTileX(), end.getTileY());
        }
        GameMenu.unitsCount = new HashMap<>();
        GameMenu.selectedTroops.clear();
        GameMenu.selectedTilesTroop.clear();
        selectedMilitaries.clear();
        GameViewController.setCenterOfBar(null);
        GameViewController.currentItem = null;
        GameViewController.currentCategory = null;
    }

    public static void patrolUnits(GameTile end) {
        for (Military military : selectedMilitaries) {
            HumanController.militaries.clear();
            HumanController.militaries.add(military);
            GameController.patrolUnit(end.getTileX(), end.getTileY());
        }
        GameMenu.unitsCount = new HashMap<>();
        GameMenu.selectedTroops.clear();
        GameMenu.selectedTilesTroop.clear();
        selectedMilitaries.clear();
        GameViewController.setCenterOfBar(null);
        GameViewController.currentItem = null;
        GameViewController.currentCategory = null;
    }

    public static void setFlagOfPatrol(int x1, int y1, int x2, int y2) {
        ImageView flag1 = new ImageView(new Image(LoginMenu.class.getResource(Paths.MAP_IMAGES.getPath())
                .toExternalForm() + "patrol-flag.png"));
        ImageView flag2 = new ImageView(new Image(LoginMenu.class.getResource(Paths.MAP_IMAGES.getPath())
                .toExternalForm() + "patrol-flag.png"));
        GameTile start = GameMap.getGameTile(x1, y1);
        GameTile end = GameMap.getGameTile(x2, y2);
        flag1.setTranslateX(start.getTextureImage().getTranslateX());
        flag1.setTranslateY(start.getTextureImage().getTranslateY());
        flag2.setTranslateX(end.getTextureImage().getTranslateX());
        flag2.setTranslateY(end.getTextureImage().getTranslateY());
        flag1.setFitWidth(GameMap.tileWidth);
        flag2.setFitWidth(GameMap.tileWidth);
        flag2.setFitHeight(GameMap.tileHeight);
        flag1.setFitHeight(GameMap.tileHeight);
        Timeline timeline = new Timeline(new KeyFrame(Duration.millis(5000), actionEvent -> {
            GameMenu.gameMap.getChildren().remove(flag2);
            GameMenu.gameMap.getChildren().remove(flag1);
        }));
        timeline.setCycleCount(1);
        timeline.play();

        GameMenu.gameMap.getChildren().addAll(flag1, flag2);
    }

    public static void stopTroops() {
        for (Military military : selectedMilitaries) {
            if (military.getMove() != null) {
                military.getMove().stopMove();
            }
        }
        GameViewController.unselectTiles();
    }

    public static void attack(GameTile end) {
        for (Military military : selectedMilitaries) {
            HumanController.militaries.clear();
            HumanController.militaries.add(military);
            if (GameController.validateAttackEnemy(end.getTileX(), end.getTileY())) {
                GameController.attackEnemy(end.getTileX(), end.getTileY());
                continue;
            }
            if (GameController.validateAttackBuilding(end.getTileX(), end.getTileY())) {
                GameController.attackBuilding(end.getTileX(), end.getTileY());
                continue;
            }
            if (GameController.validateAttackTool(end.getTileX(), end.getTileY())) {
                GameController.attackTool(end.getTileX(), end.getTileY());
                continue;
            }
            if (GameController.validateAirAttack(end.getTileX(), end.getTileY())) {
                GameController.airAttack(end.getTileX(), end.getTileY());
                continue;
            }
            if (GameController.validateAirAttackBuilding(end.getTileX(), end.getTileY())) {
                GameController.airAttackBuilding(end.getTileX(), end.getTileY());
                continue;
            }
            if (GameController.validateAirAttackTool(end.getTileX(), end.getTileY())) {
                GameController.airAttackTool(end.getTileX(), end.getTileY());
                continue;
            }

            GameController.moveUnit(end.getTileX(), end.getTileY());
        }
        GameMenu.unitsCount = new HashMap<>();
        GameMenu.selectedTroops.clear();
        GameMenu.selectedTilesTroop.clear();
        selectedMilitaries.clear();
        GameViewController.setCenterOfBar(null);
        GameViewController.currentItem = null;
        GameViewController.currentCategory = null;
    }

    public static Troop getTroopFromMilitary(Military military) {
        ArrayList<Troop> militaries = GameMap.gameTroops[military.getY()][military.getX()];
        for (Troop troop : militaries) {
            if (troop.getMilitary().equals(military)) {
                return troop;
            }
        }
        return null;
    }

    public static void attackToEnemy(Military military, Military enemy) {
        Troop troop = getTroopFromMilitary(military);
        GameTile start = GameMap.getGameTile(military.getX(), military.getY());
        GameTile end = GameMap.getGameTile(enemy.getX(), enemy.getY());
        double x1 = start.getX();
        double x2 = end.getX();
        double y1 = start.getY();
        double y2 = end.getY();
        int dir = getDirection(x1, y1, x2, y2);
        troop.setAttackDirection(dir);
        troop.setAttackStep();
        troop.updateImageAttack();
    }

    public static void airAttackToEnemy(Military military, Military enemy) {
        Troop troop = getTroopFromMilitary(military);
        GameTile start = GameMap.getGameTile(military.getX(), military.getY());
        GameTile end = GameMap.getGameTile(enemy.getX(), enemy.getY());
        double x1 = start.getX();
        double x2 = end.getX();
        double y1 = start.getY();
        double y2 = end.getY();
        int dir = getDirection(x1, y1, x2, y2);
        troop.setAirAttackDirection(dir);
        troop.setAirAttackStep();
        troop.updateImageAirAttack();
    }

    public static void attackToBuilding(Military military, Building building) {
        Troop troop = getTroopFromMilitary(military);
        GameTile start = GameMap.getGameTile(military.getX(), military.getY());
        GameTile end = null;
        ArrayList<Tile> troopNeighborTiles = HumanController.getNeighbor(military.getX(), military.getY());

        for (Tile tile : troopNeighborTiles) {
            if (tile.getBuilding() != null && tile.getBuilding().equals(building)) {
                end = GameMap.getGameTile(tile.x, tile.y);
                break;
            }
        }
        if (end == null) {
            return;
        }

        double x1 = start.getX();
        double x2 = end.getX();
        double y1 = start.getY();
        double y2 = end.getY();
        int dir = getDirection(x1, y1, x2, y2);
        troop.setAttackDirection(dir);
        troop.setAttackStep();
        troop.updateImageAttack();
    }

    public static void airAttackToBuilding(Military military, Building building) {
        Troop troop = getTroopFromMilitary(military);
        GameTile start = GameMap.getGameTile(military.getX(), military.getY());
        GameTile end = GameMap.getGameTile(building.getEndX(), building.getEndY());
        double x1 = start.getX();
        double x2 = end.getX();
        double y1 = start.getY();
        double y2 = end.getY();
        int dir = getDirection(x1, y1, x2, y2);
        troop.setAirAttackDirection(dir);
        troop.setAirAttackStep();
        troop.updateImageAirAttack();
    }

    public static int getDirection(double x1, double y1, double x2, double y2) {
        if (Math.abs(x1 - x2) < 0.5) {
            if ((y2 - y1) > 0 || Math.abs(y2 - y1) < 0.5) {
                return 3;
            }
            return 7;
        }
        double slop = (y2 - y1) / (x2 - x1);
        if (Math.abs(slop) < 0.5) {
            if ((x2 - x1) > 0) {
                return 1;
            }
            return 5;
        }
        if (slop >= 0) {
            if ((y2 - y1) > 0) {
                return 2;
            }
            return 6;
        } else {
            if ((y2 - y1) > 0) {
                return 4;
            }
            return 0;
        }
    }
}
