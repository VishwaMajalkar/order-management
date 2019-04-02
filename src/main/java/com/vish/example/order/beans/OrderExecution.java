package com.vish.example.order.beans;

import lombok.Data;

import java.io.Serializable;

/**
 * This Class is for Order Book Execution
 * Created by Vishwanath on 18/03/2019.
 */
@Data
public class OrderExecution implements Serializable {

    private static final long serialVersionUID = 28839938772882L;

    private int executionId;
    private int executionQty;
    private double executionPrice;
}
