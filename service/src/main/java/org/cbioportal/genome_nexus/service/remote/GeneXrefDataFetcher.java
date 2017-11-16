package org.cbioportal.genome_nexus.service.remote;

import org.cbioportal.genome_nexus.model.GeneXref;
import org.cbioportal.genome_nexus.service.exception.ResourceMappingException;
import org.cbioportal.genome_nexus.service.internal.ExternalResourceTransformer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.ResourceAccessException;

import java.util.List;
import java.util.Map;

@Component
public class GeneXrefDataFetcher extends BaseExternalResourceFetcher<GeneXref>
{
    private static final String MAIN_QUERY_PARAM = "accession";
    private static final String PLACEHOLDER = "ACCESSION";

    private final ExternalResourceTransformer transformer;

    @Autowired
    public GeneXrefDataFetcher(ExternalResourceTransformer transformer,
                               @Value("${genexrefs.url}") String geneXrefsUrl)
    {
        super(geneXrefsUrl, MAIN_QUERY_PARAM, PLACEHOLDER);
        this.transformer = transformer;
    }

    @Override
    public List<GeneXref> fetchInstances(Map<String, String> queryParams)
        throws HttpClientErrorException, ResourceAccessException, ResourceMappingException
    {
        return this.transformer.transform(this.fetchStringValue(queryParams), GeneXref.class);
    }

    public ExternalResourceTransformer getTransformer() {
        return transformer;
    }
}