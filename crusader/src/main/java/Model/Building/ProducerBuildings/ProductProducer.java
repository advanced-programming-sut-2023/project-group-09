package Model.Building.ProducerBuildings;

import Model.Building.Building;
import Model.Government;
import Model.Human.Human;

import java.util.ArrayList;
import java.util.HashMap;

public class ProductProducer extends ProducerBuilding {
    private String productType;
    private ArrayList<String> primaryRequired = new ArrayList<>();
    private ArrayList<String> buildingRequired = new ArrayList<>();

    public ProductProducer(Government government, int numberOfRequiredWorkers, int numberOfRequiredEngineers, String type,
                           int maxHp, int startX, int startY, int endX, int endY, String productType) {
        super(government, numberOfRequiredWorkers, numberOfRequiredEngineers, type, maxHp, startX, startY, endX, endY);
        this.productType = productType;
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