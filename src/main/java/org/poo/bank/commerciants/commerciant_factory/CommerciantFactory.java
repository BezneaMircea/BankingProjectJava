package org.poo.bank.commerciants.commerciant_factory;

import org.poo.bank.commerciants.Commerciant;

public interface CommerciantFactory {
    /**
     * Method used to create a commerciant;
     * @return the created commerciant;
     */
    Commerciant createCommerciant();
}
