package com.rbkmoney.cm.service;

import com.rbkmoney.cm.config.ConverterConfig;
import com.rbkmoney.cm.model.ClaimModel;
import com.rbkmoney.cm.model.ClaimModificationModel;
import com.rbkmoney.cm.model.PartyModificationModel;
import com.rbkmoney.cm.model.comment.CommentModificationModel;
import com.rbkmoney.cm.model.contract.ContractModificationModel;
import com.rbkmoney.cm.model.contractor.ContractorModificationModel;
import com.rbkmoney.cm.model.document.DocumentModificationModel;
import com.rbkmoney.cm.model.external.info.ExternalInfoModificationModel;
import com.rbkmoney.cm.model.file.FileModificationModel;
import com.rbkmoney.cm.model.shop.ShopModificationModel;
import com.rbkmoney.cm.model.status.StatusModificationModel;
import com.rbkmoney.cm.util.MockUtil;
import com.rbkmoney.damsel.claim_management.*;
import com.rbkmoney.geck.common.util.TypeUtil;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.convert.ConversionService;
import org.springframework.test.annotation.Repeat;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.util.CollectionUtils;

import java.time.Instant;
import java.util.List;
import java.util.stream.Collectors;

import static junit.framework.TestCase.assertEquals;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = {ConverterConfig.class, ConversionService.class})
public class ConversionServiceTest {

    @Autowired
    private ConversionService conversionService;

    @Test
    @Repeat(10)
    public void testCommentModificationConverters() {
        CommentModificationUnit commentModificationUnit = MockUtil.generateTBase(CommentModificationUnit.class);

        assertEquals(
                commentModificationUnit,
                conversionService.convert(
                        conversionService.convert(commentModificationUnit, CommentModificationModel.class),
                        ClaimModification.class
                ).getCommentModification()
        );
    }

    @Test
    @Repeat(10)
    public void testFileModificationConverters() {
        FileModificationUnit fileModificationUnit = MockUtil.generateTBase(FileModificationUnit.class);

        assertEquals(
                fileModificationUnit,
                conversionService.convert(
                        conversionService.convert(fileModificationUnit, FileModificationModel.class),
                        ClaimModification.class
                ).getFileModification()
        );
    }

    @Test
    @Repeat(10)
    public void testDocumentModificationConverters() {
        DocumentModificationUnit documentModificationUnit = MockUtil.generateTBase(DocumentModificationUnit.class);
        documentModificationUnit.setType(null);
        assertEquals(
                documentModificationUnit,
                conversionService.convert(
                        conversionService.convert(documentModificationUnit, DocumentModificationModel.class),
                        ClaimModification.class
                ).getDocumentModification()
        );
    }

    @Test
    @Repeat(10)
    public void testStatusModificationConverters() {
        StatusModificationUnit statusModificationUnit = MockUtil.generateTBase(StatusModificationUnit.class);

        assertEquals(
                statusModificationUnit,
                conversionService.convert(
                        conversionService.convert(statusModificationUnit, StatusModificationModel.class),
                        ClaimModification.class
                ).getStatusModification()
        );
    }

    @Test
    @Repeat(10)
    public void testExternalInfoModificationConverters() {
        ExternalInfoModificationUnit externalInfoModificationUnit =
                MockUtil.generateTBase(ExternalInfoModificationUnit.class);
        externalInfoModificationUnit.setDocumentId("123");
        assertEquals(
                externalInfoModificationUnit,
                conversionService.convert(
                        conversionService.convert(externalInfoModificationUnit, ExternalInfoModificationModel.class),
                        ClaimModification.class
                ).getExternalInfoModification()
        );
    }

    @Test
    @Repeat(10)
    public void testClaimModificationConverters() {
        ClaimModification claimModification = MockUtil.generateTBase(ClaimModification.class);
        if (claimModification.isSetDocumentModification()) {
            claimModification.getDocumentModification().setType(null);
        }
        assertEquals(
                claimModification,
                conversionService.convert(
                        conversionService.convert(claimModification, ClaimModificationModel.class),
                        ClaimModification.class
                )
        );
    }

    @Test
    @Repeat(10)
    public void testShopModificationConverters() {
        ShopModification shopModification = MockUtil.generateTBase(ShopModification.class);
        assertEquals(
                shopModification,
                conversionService.convert(
                        conversionService.convert(shopModification, ShopModificationModel.class),
                        ShopModification.class
                )
        );
    }

    @Test
    @Repeat(10)
    public void testContractModificationConverters() {
        ContractModification contractModification = MockUtil.generateTBase(ContractModification.class);
        assertEquals(
                contractModification,
                conversionService.convert(
                        conversionService.convert(contractModification, ContractModificationModel.class),
                        ContractModification.class
                )
        );
    }

    @Test
    @Repeat(10)
    public void testContractorModificationConverters() {
        ContractorModification contractorModification = MockUtil.generateTBase(ContractorModification.class);
        assertEquals(
                contractorModification,
                conversionService.convert(
                        conversionService.convert(contractorModification, ContractorModificationModel.class),
                        ContractorModification.class
                )
        );
    }

    @Test
    @Repeat(10)
    public void testPartyModificationConverters() {
        PartyModification partyModification = MockUtil.generateTBase(PartyModification.class);
        assertEquals(
                partyModification,
                conversionService.convert(
                        conversionService.convert(partyModification, PartyModificationModel.class),
                        PartyModification.class
                )
        );
    }

    @Test
    @Repeat(20)
    public void testAllClaimConverters() {
        Claim claim = MockUtil.generateTBase(Claim.class);
        claim.getChangeset().stream().forEach(changeset -> {
            if (changeset.getModification().isSetClaimModification()
                    && changeset.getModification().getClaimModification().isSetDocumentModification()) {
                changeset.getModification().getClaimModification().getDocumentModification().setType(null);
            }
        });
        if (CollectionUtils.isEmpty(claim.getMetadata())) {
            claim.setMetadata(null);
        }
        List<ModificationUnit> modificationUnits = claim.getChangeset().stream()
                .peek(mod -> {
                    mod.setChangedAt(TypeUtil.temporalToString(Instant.now()));
                    mod.setRemovedAt(null);
                })
                .collect(Collectors.toList());
        claim.setChangeset(modificationUnits);
        assertEquals(
                claim,
                conversionService.convert(
                        conversionService.convert(claim, ClaimModel.class),
                        Claim.class
                )
        );
    }

}
