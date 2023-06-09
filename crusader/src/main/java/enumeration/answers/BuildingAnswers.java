package enumeration.answers;


public enum BuildingAnswers {INVALID_NUMBER_INPUT("Invalid number in your input!"),
    INVALID_RANGE_FOR_TAX_RATE("Tax rate must be between -3 and 8!"),
    TAX_RATE_SUCCESSFULLY_CHANGED("Tax rate successfully changed!"),
    TAX_RATE_SHOWING("Your tax rate is : "),
    OPEN_CLOSE_SUCCESSFULLY_DONE("your order successfully done!"),
    INVALID_UNIT_NAME("Invalid unit name"),
    INSUFFICIENT_RESOURCE("your resource is not enough!"),
    INSUFFICIENT_MONEY("your money is not enough!")
    ;
    private String message;

    private BuildingAnswers(String message) {
        this.message = message;
    }
    public static String getMessage(BuildingAnswers answer){
        return answer.message;
    }
}
