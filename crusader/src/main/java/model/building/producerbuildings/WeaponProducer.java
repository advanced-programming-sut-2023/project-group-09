package model.building.producerbuildings;

import controller.gamestructure.GameGoods;
import model.goods.Goods;

import java.util.ArrayList;
import java.util.HashMap;

public class WeaponProducer extends ProducerBuilding{

    ArrayList<String> weapons = new ArrayList<>();


    public WeaponProducer(int numberOfRequiredWorkers, int numberOfRequiredEngineers, String name, int maxHp, int width, int length, int rate, String nameOfStorage, String itemType, String itemName) {
        super(numberOfRequiredWorkers, numberOfRequiredEngineers, name, maxHp, width, length, rate, nameOfStorage, itemType, itemName);
    }
  public WeaponProducer(WeaponProducer weaponProducer) {
      super(weaponProducer.getNumberOfRequiredWorkers(), weaponProducer.getNumberOfRequiredEngineers(),
              weaponProducer.getName(), weaponProducer.getMaxHp(), weaponProducer.getWidth(), weaponProducer.getLength(),
              weaponProducer.getRate(), weaponProducer.getNameOfStorage(), weaponProducer.getItemType(), weaponProducer.getItemName());
      super.setCost(weaponProducer.getCost());
      super.setRequired(weaponProducer.getRequired());
    }

    public void addWeapon(String weapon){
        weapons.add(weapon);
    }

    public void changeItemName(String name){
        this.setItemName(name);
        this.setRequired(getRequired(name));
    }

    public HashMap<String, Integer> getRequired(String name){
        Goods product = GameGoods.getProduct(name);
        return product.getRequired();
    }
    public ArrayList<String> getWeapons() {
        return weapons;
    }
}
