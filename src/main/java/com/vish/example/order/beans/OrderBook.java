package com.vish.example.order.beans;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.PersistenceConstructor;
import org.springframework.data.gemfire.mapping.annotation.Region;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Vishwanath on 16/03/2019.
 * Class to Store Order Book Data
 */
@Data
@Region("OrderBook")
public class OrderBook extends BaseBean implements Serializable{

    private static final long serialVersionUID = 2677388772882L;

    @Id
    private String bookId;
    private List<Order> orderList;
    private String orderStatus;

    @PersistenceConstructor
    public OrderBook(String bookId, List<Order> orderList, String orderStatus) {
        this.bookId = bookId;
        this.orderList = orderList;
        this.orderStatus = orderStatus;
    }
}
