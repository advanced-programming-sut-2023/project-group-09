package model.building.producerbuildings;

import enumeration.Textures;

public class Quarry extends ProducerBuilding{

    public Quarry() {
        super(3, 0, "quarry", 60, 6, 6, 12, "stockPile","resource","stone");
        this.addCost("wood",20);
        this.enableHasSpecialTexture();
        this.addTexture(Textures.BOULDER);
    }

    @Override
    public void addProduct(){
        //
    }
}
