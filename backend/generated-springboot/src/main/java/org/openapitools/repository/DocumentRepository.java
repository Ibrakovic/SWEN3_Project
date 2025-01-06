package org.openapitools.repository;

import java.util.List;

import org.openapitools.entity.Document;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface DocumentRepository extends JpaRepository<Document, Long> {

	@Query(nativeQuery = true, value = "Select * from document where file_name = :fileName")
	List<Document> findByFileName(String fileName);

	@Query(nativeQuery = true, value = "Select * from document where file_name = :fileName and id = :id")
	List<Document> findByFileNameAndId(String fileName, long id);

}
