package enumeration.dictionary;

public enum RawMaterials {STONE("stone"),
    IRON("iron"),
    WOOD("wood"),
    PITCH("pitch");
    private String name;
    RawMaterials(String name) {
        this.name = name;
    }
    public String getName(RawMaterials material) {
        return material.name;
    }
}
