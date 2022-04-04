package ru.demo.acquiringservice.restrepository;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import ru.demo.acquiringservice.object.entity.Transaction;

@RepositoryRestResource(collectionResourceRel = "transaction", path = "transaction")
public interface TransactionRestRepository extends PagingAndSortingRepository<Transaction, Long> {

}
