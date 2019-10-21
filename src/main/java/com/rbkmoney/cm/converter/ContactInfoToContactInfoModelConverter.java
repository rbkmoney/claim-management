package com.rbkmoney.cm.converter;

import com.rbkmoney.cm.model.ContactInfoModel;
import com.rbkmoney.damsel.domain.ContactInfo;
import org.springframework.stereotype.Component;

@Component
public class ContactInfoToContactInfoModelConverter implements ClaimConverter<ContactInfo, ContactInfoModel> {
    @Override
    public ContactInfoModel convert(ContactInfo contactInfo) {
        ContactInfoModel contactInfoModel = new ContactInfoModel();
        contactInfoModel.setEmail(contactInfo.getEmail());
        contactInfoModel.setPhoneNumber(contactInfo.getPhoneNumber());
        return contactInfoModel;
    }
}
