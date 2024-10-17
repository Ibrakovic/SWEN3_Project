package org.openapitools.service;

import org.openapitools.model.Document;
import org.openapitools.repository.DocumentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.elasticsearch.core.ElasticsearchRestTemplate;
import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.data.elasticsearch.core.query.Query;
import org.springframework.data.elasticsearch.core.query.StringQuery;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class DocumentService {

    private final DocumentRepository documentRepository;
    private final ElasticsearchRestTemplate elasticsearchRestTemplate;

    @Autowired
    public DocumentService(DocumentRepository documentRepository, ElasticsearchRestTemplate elasticsearchRestTemplate) {
        this.documentRepository = documentRepository;
        this.elasticsearchRestTemplate = elasticsearchRestTemplate;
    }

    public List<Document> getAllDocuments() {
        return documentRepository.findAll();
    }

    public Optional<Document> getDocumentById(Long id) {
        return documentRepository.findById(id);
    }

    public Document createDocument(Document document) {
        return documentRepository.save(document);
    }

    public Document updateDocument(Long id, Document documentDetails) {
        return documentRepository.findById(id)
                .map(document -> {
                    document.setContent(documentDetails.getContent());
                    document.setTags(documentDetails.getTags());
                    document.setStatus(documentDetails.getStatus());
                    return documentRepository.save(document);
                })
                .orElseThrow(() -> new RuntimeException("Document not found with id " + id));
    }

    public void deleteDocument(Long id) {
        documentRepository.deleteById(id);
    }

    // Neue Methode zum Suchen von Dokumenten basierend auf einem Query-String
    public List<Document> searchDocuments(String query) {
        // Erstelle eine StringQuery basierend auf dem übergebenen Query-String
        Query searchQuery = new StringQuery(query);

        // Führe die Suche in Elasticsearch durch und erhalte die Treffer
        SearchHits<Document> searchHits = elasticsearchRestTemplate.search(searchQuery, Document.class);

        // Extrahiere die Treffer und sammle die Ergebnisse als Liste von Dokumenten
        return searchHits.getSearchHits()
                .stream()
                .map(hit -> hit.getContent()) // Hole das tatsächliche Dokument-Objekt aus jedem Treffer
                .collect(Collectors.toList());
    }
}
