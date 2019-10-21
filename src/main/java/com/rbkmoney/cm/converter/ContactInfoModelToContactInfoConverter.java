package com.rbkmoney.cm.converter;

import com.rbkmoney.cm.model.ContactInfoModel;
import com.rbkmoney.damsel.domain.ContactInfo;
import org.springframework.stereotype.Component;

@Component
public class ContactInfoModelToContactInfoConverter implements ClaimConverter<ContactInfoModel, ContactInfo> {
    @Override
    public ContactInfo convert(ContactInfoModel contactInfoModel) {
        return new ContactInfo()
                .setEmail(contactInfoModel.getEmail())
                .setPhoneNumber(contactInfoModel.getPhoneNumber());
    }
}
