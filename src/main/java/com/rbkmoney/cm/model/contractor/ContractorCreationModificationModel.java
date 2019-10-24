package com.rbkmoney.cm.model.contractor;

public class ContractorCreationModificationModel extends ContractorModificationModel {

    @Override
    public boolean canEqual(final Object that) {
        return that instanceof ContractorCreationModificationModel
                && super.canEqual(that);
    }

}
