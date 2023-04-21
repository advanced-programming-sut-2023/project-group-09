package model.building.producerbuildings;

import model.Government;

import java.util.ArrayList;

public class ProductProducer extends model.building.producerbuildings.ProducerBuilding {
    private String productType;
    private ArrayList<String> primaryRequired = new ArrayList<>();
    private ArrayList<String> buildingRequired = new ArrayList<>();

    public ProductProducer(int numberOfRequiredWorkers, int numberOfRequiredEngineers,
                           String type, int maxHp, int width, int length, int rate,
                           String nameOfStorage, String itemType, String itemName,
                           String productType, ArrayList<String> primaryRequired,
                           ArrayList<String> buildingRequired) {
        super(numberOfRequiredWorkers, numberOfRequiredEngineers, type,
                maxHp, width, length, rate, nameOfStorage, itemType, itemName);
        this.productType = productType;
        this.primaryRequired = primaryRequired;
        this.buildingRequired = buildingRequired;
    }

    public String getProductType() {
        return productType;
    }

    public void setProductType(String productType) {
        this.productType = productType;
    }

    public ArrayList<String> getPrimaryRequired() {
        return primaryRequired;
    }

    public void setPrimaryRequired(ArrayList<String> primaryRequired) {
        this.primaryRequired = primaryRequired;
    }

    public ArrayList<String> getBuildingRequired() {
        return buildingRequired;
    }

    public void setBuildingRequired(ArrayList<String> buildingRequired) {
        this.buildingRequired = buildingRequired;
    }
}