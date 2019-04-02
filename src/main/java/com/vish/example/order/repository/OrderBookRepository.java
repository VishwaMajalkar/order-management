package com.vish.example.order.repository;

import com.vish.example.order.beans.OrderBook;
import org.springframework.data.gemfire.repository.GemfireRepository;
import org.springframework.data.gemfire.repository.Query;

import java.util.List;

/**
 * This Class is gemfire repository class to persist data in a cache
 * Created by Vishwanath on 16/03/2019.
 */
public interface OrderBookRepository extends GemfireRepository<OrderBook, String> {
    @Query("SELECT * FROM /OrderBook o WHERE o.getBookId() = $1")
    List<OrderBook> findOrderBook(String bookId);

    @Query("SELECT * FROM /OrderBook o WHERE o.getStatus() = $1")
    List<OrderBook> getOrderBookStatus(String status);
}
