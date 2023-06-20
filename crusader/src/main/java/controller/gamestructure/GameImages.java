package controller.gamestructure;

import enumeration.Paths;
import enumeration.Textures;
import javafx.scene.image.Image;
import model.menugui.game.GameTile;
import model.menugui.game.TileSensor;

import java.util.HashMap;

public class GameImages {
    public static HashMap<String, Image> imageViews = new HashMap<>();

    public static void loadImages() {
        addTextures();
        addArabianTroop();
        addCursor();
        addRed();
        addBars();
    }

    public static void addTextures() {
        addEarth();
        addBeach();
        addBoulder();
        addGrass();
        addEarthAndSand();
        addOil();
        addMarsh();
        addOasisGrass();
        addIronTexture();
        addLargePond();
        addSmallPond();
        addRiver();
        addSea();
        addRockTexture();
        addThickGrass();
        addLowDepthWater();
    }

    public static void addCursor() {
        addSelectMove();
        addCanNot();
        addAttack();
    }

    public static void addBars() {
        addUnitBar();
        addMainBar();
    }
    public static void addArabianTroop() {
        addArabianSwordsman();
        addArabianArcher();
        addSlinger();
    }

    public static void addMainBar() {
        Image image = new Image(GameImages.class.getResource
                (Paths.BAR_IMAGES.getPath()).toExternalForm() + "bar.png");
        imageViews.put("bar", image);
    }

    public static void addUnitBar() {
        Image image = new Image(GameImages.class.getResource
                (Paths.BAR_IMAGES.getPath()).toExternalForm() + "unitBar.png");
        imageViews.put("unit bar", image);
    }

    public static void addSelectMove() {
        Image image = new Image(GameTile.class.getResource(Paths.GAME_IMAGES.getPath()).toExternalForm() + "cursor/selectMove.gif");
        imageViews.put("selectMove", image);
    }

    public static void addCanNot() {
        Image image = new Image(GameTile.class.getResource(Paths.GAME_IMAGES.getPath()).toExternalForm() + "cursor/cannot.gif");
        imageViews.put("cannot", image);
    }

    public static void addAttack() {
        Image image = new Image(GameTile.class.getResource(Paths.GAME_IMAGES.getPath()).toExternalForm() + "cursor/attack.gif");
        imageViews.put("attack", image);
    }




    private static void addArabianSwordsman() {
        String[] colors = {"blue", "red", "orange", "yellow", "grey", "purple", "skyBlue", "green"};
        for (int i = 0; i < 8; i++) {
            int counter = 0;
            for (int j = 0; j < 8; j++) {
                for (int k = 0; k < 8; k++) {
                    Image troop = new Image(GameTile.class.getResource(Paths.TROOP_IMAGES.getPath()).toExternalForm() +
                            "arabianSwordsman/" + colors[i] + "/0_0img" + counter + ".png");
                    imageViews.put("arabianSwordsman_" + colors[i] + "_" + counter, troop);
                    counter++;
                }
                counter += 8;
            }
        }
    }
    private static void addArabianArcher() {
        String[] colors = {"blue", "red", "orange", "yellow", "grey", "purple", "skyBlue", "green"};
        for (int i = 0; i < 8; i++) {
            int counter = 0;
            for (int j = 0; j < 8; j++) {
                for (int k = 0; k < 8; k++) {
                    Image troop = new Image(GameTile.class.getResource(Paths.TROOP_IMAGES.getPath()).toExternalForm() +
                            "archerBow/" + colors[i] + "/0_0img" + counter + ".png");
                    imageViews.put("archerBow_" + colors[i] + "_" + counter, troop);
                    counter++;
                }
                counter += 8;
            }
        }
    }
    private static void addSlinger() {
        String[] colors = {"blue", "red", "orange", "yellow", "grey", "purple", "skyBlue", "green"};
        for (int i = 0; i < 8; i++) {
            int counter = 0;
            for (int j = 0; j < 8; j++) {
                for (int k = 0; k < 8; k++) {
                    Image troop = new Image(GameTile.class.getResource(Paths.TROOP_IMAGES.getPath()).toExternalForm() +
                            "slinger/" + colors[i] + "/0_0img" + counter + ".png");
                    imageViews.put("slinger_" + colors[i] + "_" + counter, troop);
                    counter++;
                }
                counter += 8;
            }
        }
    }

    public static void addEarth() {
        for (int i = 0; i < Textures.EARTH.getCount(); i++) {
            Image textureImg = new Image(GameTile.class.getResource(Paths.MAP_IMAGES.getPath()
                    + "textures/earth/" + i + ".png").toExternalForm());
            imageViews.put("earth" + i, textureImg);
        }
    }

    public static void addBeach() {
        for (int i = 0; i < Textures.BEACH.getCount(); i++) {
            Image textureImg = new Image(GameTile.class.getResource(Paths.MAP_IMAGES.getPath()
                    + "textures/beach/" + i + ".png").toExternalForm());
            imageViews.put("beach" + i, textureImg);
        }
    }

    public static void addBoulder() {
        for (int i = 0; i < Textures.BOULDER.getCount(); i++) {
            Image textureImg = new Image(GameTile.class.getResource(Paths.MAP_IMAGES.getPath()
                    + "textures/boulder/" + i + ".png").toExternalForm());
            imageViews.put("boulder" + i, textureImg);
        }
    }

    public static void addEarthAndSand() {
        for (int i = 0; i < Textures.EARTH_AND_SAND.getCount(); i++) {
            Image textureImg = new Image(GameTile.class.getResource(Paths.MAP_IMAGES.getPath()
                    + "textures/earthAndSand/" + i + ".png").toExternalForm());
            imageViews.put("earthAndSand" + i, textureImg);
        }
    }

    public static void addGrass() {
        for (int i = 0; i < Textures.GRASS.getCount(); i++) {
            Image textureImg = new Image(GameTile.class.getResource(Paths.MAP_IMAGES.getPath()
                    + "textures/grass/" + i + ".png").toExternalForm());
            imageViews.put("grass" + i, textureImg);
        }
    }

    public static void addOil() {
        for (int i = 0; i < Textures.OIL.getCount(); i++) {
            Image textureImg = new Image(GameTile.class.getResource(Paths.MAP_IMAGES.getPath()
                    + "textures/oil/" + i + ".png").toExternalForm());
            imageViews.put("oil" + i, textureImg);
        }
    }

    public static void addMarsh() {
        for (int i = 0; i < Textures.MARSH.getCount(); i++) {
            Image textureImg = new Image(GameTile.class.getResource(Paths.MAP_IMAGES.getPath()
                    + "textures/marsh/" + i + ".png").toExternalForm());
            imageViews.put("marsh" + i, textureImg);
        }
    }

    public static void addLargePond() {
        for (int i = 0; i < Textures.LARGE_POND.getCount(); i++) {
            Image textureImg = new Image(GameTile.class.getResource(Paths.MAP_IMAGES.getPath()
                    + "textures/largePond/" + i + ".png").toExternalForm());
            imageViews.put("largePond" + i, textureImg);
        }
    }

    public static void addSmallPond() {
        for (int i = 0; i < Textures.SMALL_POND.getCount(); i++) {
            Image textureImg = new Image(GameTile.class.getResource(Paths.MAP_IMAGES.getPath()
                    + "textures/smallPond/" + i + ".png").toExternalForm());
            imageViews.put("smallPond" + i, textureImg);
        }
    }

    public static void addIronTexture() {
        for (int i = 0; i < Textures.IRON_TEXTURE.getCount(); i++) {
            Image textureImg = new Image(GameTile.class.getResource(Paths.MAP_IMAGES.getPath()
                    + "textures/ironTexture/" + i + ".png").toExternalForm());
            imageViews.put("ironTexture" + i, textureImg);
        }
    }

    public static void addOasisGrass() {
        for (int i = 0; i < Textures.OASIS_GRASS.getCount(); i++) {
            Image textureImg = new Image(GameTile.class.getResource(Paths.MAP_IMAGES.getPath()
                    + "textures/oasisGrass/" + i + ".png").toExternalForm());
            imageViews.put("oasisGrass" + i, textureImg);
        }
    }

    public static void addRiver() {
        for (int i = 0; i < Textures.RIVER.getCount(); i++) {
            Image textureImg = new Image(GameTile.class.getResource(Paths.MAP_IMAGES.getPath()
                    + "textures/river/" + i + ".png").toExternalForm());
            imageViews.put("river" + i, textureImg);
        }
    }

    public static void addRockTexture() {
        for (int i = 0; i < Textures.ROCK_TEXTURE.getCount(); i++) {
            Image textureImg = new Image(GameTile.class.getResource(Paths.MAP_IMAGES.getPath()
                    + "textures/rockTexture/" + i + ".png").toExternalForm());
            imageViews.put("rockTexture" + i, textureImg);
        }
    }

    public static void addSea() {
        for (int i = 0; i < Textures.SEA.getCount(); i++) {
            Image textureImg = new Image(GameTile.class.getResource(Paths.MAP_IMAGES.getPath()
                    + "textures/sea/" + i + ".png").toExternalForm());
            imageViews.put("sea" + i, textureImg);
        }
    }

    public static void addThickGrass() {
        for (int i = 0; i < Textures.THICK_GRASS.getCount(); i++) {
            Image textureImg = new Image(GameTile.class.getResource(Paths.MAP_IMAGES.getPath()
                    + "textures/thickGrass/" + i + ".png").toExternalForm());
            imageViews.put("thickGrass" + i, textureImg);
        }
    }

    public static void addLowDepthWater() {
        for (int i = 0; i < Textures.LOW_DEPTH_WATER.getCount(); i++) {
            Image textureImg = new Image(GameTile.class.getResource(Paths.MAP_IMAGES.getPath()
                    + "textures/lowDepthWater/" + i + ".png").toExternalForm());
            imageViews.put("lowDepthWater" + i, textureImg);
        }
    }

    public static void addRed() {
        Image image = new Image(GameTile.class.getResource(Paths.MAP_IMAGES.getPath() + "textures/red.png").toExternalForm());
        imageViews.put("red", image);
    }

    public static TileSensor getRedImageView(double x, double y, GameTile gameTile) {
        TileSensor tileSensor = new TileSensor(imageViews.get("red"), gameTile);
        tileSensor.setTranslateX(x);
        tileSensor.setTranslateY(y);
        tileSensor.setFitWidth(30);
        tileSensor.setFitHeight(18);
        tileSensor.setOpacity(0);
        return tileSensor;
    }
}
