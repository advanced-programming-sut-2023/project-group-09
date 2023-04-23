package enumeration.answers;


public enum BuildingAnswers {INVALID_X_COORD_ERROR("invalid x coordinate!"),
    INVALID_Y_COORD_ERROR("invalid y coordinate!"),
    INVALID_TYPE_ERROR("invalid type of building!"),
    ERROR_FOR_DROP_BUILDING("sorry, you cannot drop this building here!"),
    DROP_BUILDING_SUCCESSFULLY_DONE("drop building successfully done!");
    private String message;

    private BuildingAnswers(String message) {
        this.message = message;
    }
    public static String getMessage(BuildingAnswers answer){
        return answer.message;
    }
}
