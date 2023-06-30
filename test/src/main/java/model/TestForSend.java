package model;

import java.io.Serializable;

public class TestForSend implements Serializable {
    private String name;

    public TestForSend(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
