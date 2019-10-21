package com.rbkmoney.cm.converter;

import com.rbkmoney.cm.model.ArticlesOfAssociationRepresentativeDocumentModel;
import com.rbkmoney.damsel.domain.ArticlesOfAssociation;
import com.rbkmoney.damsel.domain.RepresentativeDocument;
import org.springframework.stereotype.Component;

@Component
public class ArticlesOfAssociationRepresentativeDocumentModelToRepresentativeDocumentConverter implements ClaimConverter<ArticlesOfAssociationRepresentativeDocumentModel, RepresentativeDocument> {
    @Override
    public RepresentativeDocument convert(ArticlesOfAssociationRepresentativeDocumentModel articlesOfAssociationRepresentativeDocumentModel) {
        return RepresentativeDocument.articles_of_association(new ArticlesOfAssociation());
    }
}
