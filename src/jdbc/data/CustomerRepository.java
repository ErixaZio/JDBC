package jdbc.data;

import jdbc.entities.Customer;

import java.sql.SQLException;
import java.util.Optional;

public interface CustomerRepository {
    Iterable<Customer> getAll() throws DataExeption;
    Optional<Customer> findById(int id) throws DataExeption;
    boolean delete(int id) throws DataExeption;
    boolean update(Customer c) throws DataExeption;
    void insert(Customer c) throws DataExeption;
}
