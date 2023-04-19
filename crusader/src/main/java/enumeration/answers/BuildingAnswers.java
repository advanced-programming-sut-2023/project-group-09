package enumeration.answers;


public enum BuildingAnswers {INVALID_X_COORD_ERROR("invalid x coordinate!"),
    INVALID_Y_COORD_ERROR("invalid y coordinate!"),
    INVALID_TYPE_ERROR("invalid type of building!");
    private String message;

    private BuildingAnswers(String message) {
        this.message = message;
    }
    public static String getMessage(BuildingAnswers answer){
        return answer.message;
    }
}
