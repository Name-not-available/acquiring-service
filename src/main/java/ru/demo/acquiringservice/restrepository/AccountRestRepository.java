package ru.demo.acquiringservice.restrepository;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import ru.demo.acquiringservice.object.entity.Account;

@RepositoryRestResource(path = "account")
public interface AccountRestRepository extends PagingAndSortingRepository<Account, Long> {

}
