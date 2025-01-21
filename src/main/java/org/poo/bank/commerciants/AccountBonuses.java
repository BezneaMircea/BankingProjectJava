package org.poo.bank.commerciants;

import lombok.Getter;
import lombok.Setter;

import java.util.HashMap;
import java.util.Map;

@Getter
@Setter
public final class AccountBonuses {
    private final Map<BonusType, BonusState> bonuses;

    public enum BonusType {
        FOOD, CLOTHES, TECH
    }

    public enum BonusState {
        CAN_HAVE, HAVE, USED
    }

    public AccountBonuses() {
        bonuses = new HashMap<>();
        for (BonusType bonusType : BonusType.values()) {
            bonuses.put(bonusType, BonusState.CAN_HAVE);
        }
    }

    /**
     * Method used to give a bonus to
     * @param bonusType the bonus type
     */
    public void giveBonus(final BonusType bonusType) {
        bonuses.put(bonusType, BonusState.HAVE);
    }

    /**
     * Method used to check if bonus is present
     * @param bonusType the bonusType that is checked
     * @return true if present, false otherwise
     */
    public boolean hasBonus(final BonusType bonusType) {
        return bonuses.get(bonusType) == BonusState.HAVE;
    }

    /**
     * Method used to check if a bonus was used
     * @param bonusType the bonusType that is checked
     * @return ture if used, false otherwise
     */
    public boolean usedBonus(final BonusType bonusType) {
        return bonuses.get(bonusType) == BonusState.USED;
    }

    /**
     * Method used to set a bonus as used
     * @param bonusType the bonusType corresponding to the bonus
     *                  that is to be set as used
     */
    public void setBonusUsed(final BonusType bonusType) {
        bonuses.put(bonusType, BonusState.USED);
    }

}
