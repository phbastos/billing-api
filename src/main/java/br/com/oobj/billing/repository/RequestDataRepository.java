package br.com.oobj.billing.repository;

import br.com.oobj.billing.domain.RequestData;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RequestDataRepository extends MongoRepository<RequestData, String> {
}
