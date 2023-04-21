package controller.gamestructure;

import model.goods.Goods;

import java.util.HashMap;

public class GameGoods {
    public static HashMap<String, Goods> goods = new HashMap<>();
    public static HashMap<String, Goods> foods = new HashMap<>();
    public static HashMap<String, Goods> weapons = new HashMap<>();
    public static HashMap<String, Goods> resources = new HashMap<>();

    public static Goods getProduct(String name) {
        return goods.get(name);
    }

    public static HashMap<String,Integer> getHashMapOfGovernment(){
        HashMap<String,Integer> result = new HashMap<>();
        for(String key : goods.keySet()){
            result.put(key,0);
        }
        result.put("cow",0);
        result.put("horse",0);
        result.put("ox tether",0);
        return result;
    }

    public static HashMap<String,Integer> getHashMapOfGoods(){
        HashMap<String,Integer> result = new HashMap<>();
        for(String key : goods.keySet()){
            result.put(key,0);
        }
        return result;
    }

    public static HashMap<String,Integer> getHashMapOfFoods(){
        HashMap<String,Integer> result = new HashMap<>();
        for(String key : foods.keySet()){
            result.put(key,0);
        }
        return result;
    }
    public static HashMap<String,Integer> getHashMapOfWeapons(){
        HashMap<String,Integer> result = new HashMap<>();
        for(String key : weapons.keySet()){
            result.put(key,0);
        }
        return result;
    }

    public static HashMap<String,Integer> getHashMapOfResources(){
        HashMap<String,Integer> result = new HashMap<>();
        for(String key : resources.keySet()){
            result.put(key,0);
        }
        return result;
    }

    public static void addGoods() {
        GameFoods.addFoods();
        GameResources.addResources();
        GameWeapons.addWeapons();
    }
}
