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

    public void giveBonus(BonusType bonusType) {
        bonuses.put(bonusType, BonusState.HAVE);
    }

    public boolean hasBonus(BonusType bonusType) {
        return bonuses.get(bonusType) == BonusState.HAVE;
    }

    public boolean usedBonus(BonusType bonusType) {
        return bonuses.get(bonusType) == BonusState.USED;
    }

    public void setBonusUsed(BonusType bonusType) {
        bonuses.put(bonusType, BonusState.USED);
    }

}
