package com.edms.repository;
import org.springframework.stereotype.Repository;

import com.edms.entity.DocumentEntity;

import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;
@Repository
public interface DocumentRepository extends MongoRepository<DocumentEntity, String> {
//	void deleteByVersionAndId(String documentId, int versions);
}
