package ru.dinislam.server.repository;

import org.springframework.data.repository.CrudRepository;
import ru.dinislam.server.model.Bill;

public interface BillsRepository extends CrudRepository<Bill, Long> {

}
