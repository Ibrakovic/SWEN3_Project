package org.openapitools.service;

import org.openapitools.model.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.elasticsearch.core.ElasticsearchRestTemplate;
import org.springframework.stereotype.Service;
import java.util.List;
import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.data.elasticsearch.core.query.Query;
import org.springframework.data.elasticsearch.core.query.StringQuery;

@Service
public class ElasticSearchService {

    private final ElasticsearchRestTemplate elasticsearchRestTemplate;

    @Autowired
    public ElasticSearchService(ElasticsearchRestTemplate elasticsearchRestTemplate) {
        this.elasticsearchRestTemplate = elasticsearchRestTemplate;
    }

    public Document saveDocument(Document document) {
        return elasticsearchRestTemplate.save(document);
    }

    public List<Document> searchDocuments(String query) {
        // Beispiel für eine Volltextsuche mit einer StringQuery
        Query searchQuery = new StringQuery(query);
        SearchHits<Document> searchHits = elasticsearchRestTemplate.search(searchQuery, Document.class);

        // Ergebnisliste extrahieren
        return searchHits.getSearchHits()
                .stream()
                .map(hit -> hit.getContent())
                .toList();
    }
}
