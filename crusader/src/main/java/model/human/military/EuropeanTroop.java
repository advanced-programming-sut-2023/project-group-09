package model.human.military;


public class EuropeanTroop extends Military {
    public EuropeanTroop(int speed, int defenseRating, int shootingRange, int attackRating, int price) {
        super(speed, defenseRating, shootingRange, attackRating, price);
    }

    public EuropeanTroop(EuropeanTroop europeanTroop) {
        super(europeanTroop.getSpeed(), europeanTroop.getDefenseRating(),
                europeanTroop.getShootingRange(), europeanTroop.getAttackRating(), europeanTroop.getPrice());
        super.setArmours(europeanTroop.getArmours());
        super.setName(europeanTroop.getName());
        super.setWeapon(europeanTroop.getWeapon());
        super.setMilitaryState(europeanTroop.getMilitaryState());
        super.setDefenseRange(europeanTroop.getDefenseRange());
        super.setAggressiveRange(europeanTroop.getAggressiveRange());
        super.setUsesLadder(europeanTroop.isUsesLadder());
        super.setDigsMoat(europeanTroop.isDigsMoat());
        super.setInvisible(europeanTroop.isInvisible());
        super.setAttackStepCount(europeanTroop.getAttackStepCount());
        super.setAirAttackStepCount(europeanTroop.getAirAttackStepCount());
    }
}
