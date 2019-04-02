package com.vish.example.order.beans;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by Vishwanath on 16/03/2019.
 * Class to Store Order Data
 */
@Data
public class Order extends BaseBean implements Serializable{

    private static final long serialVersionUID = 4177362772882L;

    private int orderId;
    private long orderQty;
    private Date entryDt;
    private String instrumentId;
    private double price;
    private OrderType orderType;

    public enum OrderType{
        MARKET,
        LIMIT
    }
}
