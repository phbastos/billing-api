package br.com.oobj.billing.repository;

import br.com.oobj.billing.domain.DocumentData;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DocumentoDataRepository extends MongoRepository<DocumentData, String> {
}
