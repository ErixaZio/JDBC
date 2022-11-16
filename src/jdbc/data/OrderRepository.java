package jdbc.data;

import jdbc.entities.Order;

import java.util.Optional;

public interface OrderRepository {
    Iterable<Order> findByCustomerId(int custId) throws DataExeption;
}
