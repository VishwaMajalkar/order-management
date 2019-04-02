package com.vish.example.order.controller;

/**
 * Unit test class for rest APIs
 * Created by Vishwanath on 19/03/2019.
 */

import com.google.common.collect.Lists;
import com.vish.example.order.beans.Order;
import com.vish.example.order.beans.OrderBook;
import com.vish.example.order.repository.OrderBookRepository;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Date;

@RunWith(MockitoJUnitRunner.class)
public class OrderControllerTest {

    private static final Logger log = LoggerFactory.getLogger(OrderControllerTest.class);

    @InjectMocks
    OrderController orderController;

    @Mock
    OrderBookRepository orderBookRepository;

    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    private OrderBook orderBook = null;

    @Before
    public void init()throws Exception{
        Order order = new Order();
        order.setEntryDt(new Date());
        order.setInstrumentId("SEDOL123");
        order.setOrderId(123456);
        order.setOrderQty(2566);
        order.setOrderType(Order.OrderType.MARKET);
        order.setPrice(12.34D);
        order.setCreatedBy("Vishwa");
        order.setCreatedDt(new Date());
        order.setModifiedBy("Vishwa");
        order.setModifiedDt(new Date());

        orderBook = new OrderBook("12345678", Lists.newArrayList(order), "OPEN");
    }

    @Test
    public void findOrderBookTest() throws Exception{
        Mockito.when(orderBookRepository.findOrderBook("12345678")).thenReturn(Lists.newArrayList(orderBook));
        orderController.findOrderBook("12345678");
        Mockito.verify(orderBookRepository).findOrderBook("12345678");
    }

    @Test
    public void findOrderBookWhenExceptionTest() throws Exception{
        Mockito.when(orderBookRepository.findOrderBook("12345678")).thenReturn(null);
        expectedException.expect(Exception.class);
        expectedException.expectMessage("Order Book Not Found!");
        orderController.findOrderBook("12345678");
        Mockito.verifyZeroInteractions(orderBookRepository);
    }

    @Test
    public void saveOrderBookTest() throws Exception{
        Mockito.when(orderBookRepository.findOrderBook("12345678")).thenReturn(Lists.newArrayList(orderBook));
        orderController.saveOrderBook(orderBook);
        Mockito.verify(orderBookRepository).findOrderBook("12345678");
    }

    @Test
    public void findOrderWithBiggestQtyTest() throws Exception{
        Mockito.when(orderBookRepository.findAll()).thenReturn(Lists.newArrayList(orderBook));
        orderController.findOrderWithBiggestQty();
        Mockito.verify(orderBookRepository).findAll();
    }

    @Test
    public void findOrderWithSmallestQtyTest() throws Exception{
        Mockito.when(orderBookRepository.findAll()).thenReturn(Lists.newArrayList(orderBook));
        orderController.findOrderWithSmallestQty();
        Mockito.verify(orderBookRepository).findAll();
    }

    @Test
    public void findBooksWithStatusTest() throws Exception{
        Mockito.when(orderBookRepository.getOrderBookStatus("OPEN")).thenReturn(Lists.newArrayList(orderBook));
        orderController.findBooksWithStatus("OPEN");
        Mockito.verify(orderBookRepository).getOrderBookStatus("OPEN");
    }
}
