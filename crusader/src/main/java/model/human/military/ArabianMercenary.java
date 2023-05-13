package model.human.military;

import enumeration.AttackRating;
import enumeration.DefenseRating;
import model.Government;

public class ArabianMercenary extends Military {
    public ArabianMercenary(int speed, int defenseRating, int shootingRange, int attackRating, int price) {
        super(speed, defenseRating, shootingRange, attackRating, price);
    }
    public ArabianMercenary(ArabianMercenary arabianMercenary) {
        super(arabianMercenary.getSpeed(), arabianMercenary.getDefenseRating(),
                arabianMercenary.getShootingRange(), arabianMercenary.getShootingRange(), arabianMercenary.getPrice());
        super.setArmours(arabianMercenary.getArmours());
        super.setName(arabianMercenary.getName());
        super.setWeapon(arabianMercenary.getWeapon());
        super.setMilitaryState(arabianMercenary.getMilitaryState());
        super.setDefenseRange(arabianMercenary.getDefenseRange());
        super.setAggressiveRange(arabianMercenary.getAggressiveRange());
        super.setUsesLadder(arabianMercenary.isUsesLadder());
        super.setDigsMoat(arabianMercenary.isDigsMoat());
        super.setInvisible(arabianMercenary.isInvisible());
    }
}
