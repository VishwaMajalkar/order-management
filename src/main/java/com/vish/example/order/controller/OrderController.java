package com.vish.example.order.controller;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.google.common.collect.Lists;
import com.vish.example.order.beans.Order;
import com.vish.example.order.beans.OrderBook;
import com.vish.example.order.repository.OrderBookRepository;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.MediaType;
import org.springframework.util.StopWatch;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * Created by Vishwanath on 16/03/2019.
 * Class to Expose Rest APIs for Order Book Data
 */
@RestController
public class OrderController {

    private static final Logger log = LoggerFactory.getLogger(OrderController.class);

    @Autowired
    @Lazy
    private OrderBookRepository orderBookRepository;

    /**
     * Method to Find Order Book by id
     * @param bookId
     * @return
     * @throws Exception
     */
    @GetMapping(value="/findBook{bookId}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public String findOrderBook(@PathVariable String bookId) throws Exception {
        StopWatch stopWatch = new StopWatch("Get Book Data from Cache");
        stopWatch.start();
        String response= null;
        List<OrderBook> orderBookInCache = orderBookRepository.findOrderBook(bookId);
        if(CollectionUtils.isNotEmpty(orderBookInCache)) {
            response =  getJsonResponse(orderBookInCache);
        }else{
            throw new Exception("Order Book Not Found!");
        }
        stopWatch.stop();
        log.info(stopWatch.prettyPrint());
        return response;
    }

    /**
     * Method to Save/Close Order Book
     * @param orderBook
     * @return
     * @throws Exception
     */
    @PostMapping(value="/saveOrderBook", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public String saveOrderBook(@RequestBody OrderBook orderBook) throws Exception{
        StopWatch stopWatch = new StopWatch("Save Book Data in Cache");
        stopWatch.start();
        if(orderBook != null && StringUtils.isNotEmpty(orderBook.getBookId())){
            List<OrderBook> orderBookInCache = orderBookRepository.findOrderBook(orderBook.getBookId());
            if(CollectionUtils.isNotEmpty(orderBookInCache)) {
                for (OrderBook orderBk : orderBookInCache) {
                    //If book status is Open then add orders
                    if (validateOrderBook(orderBk)) {
                        orderBk.setOrderList(orderBook.getOrderList());
                        orderBk.setOrderStatus(orderBook.getOrderStatus());
                        orderBookRepository.save(orderBk);
                    }
                }
            }else{
                log.info("Creating New Orderbook!");
                orderBookRepository.save(orderBook);
            }
        }
        stopWatch.stop();
        log.info(stopWatch.prettyPrint());
        return getJsonResponse("SUCCESS!");
    }

    /**
     * Method to find Order with biggest qty
     * @return
     * @throws Exception
     */
    @GetMapping(value="/findBiggestQtyOrder", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public String findOrderWithBiggestQty() throws Exception {
        StopWatch stopWatch = new StopWatch("Find Order With Biggest Qty");
        stopWatch.start();
        List<Order> orderList = getAllOrders();
        Order order = null;
        if(CollectionUtils.isNotEmpty(orderList)) {
            order = Collections.max(orderList, Comparator.comparing(Order::getOrderQty));
        }
        stopWatch.stop();
        log.info(stopWatch.prettyPrint());
        return getJsonResponse(order);
    }

    /**
     * Method to find Order with smallest qty
     * @return
     * @throws Exception
     */
    @GetMapping(value="/findSmallestQtyOrder", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public String findOrderWithSmallestQty() throws Exception {
        StopWatch stopWatch = new StopWatch("Find Order With Smallest Qty");
        stopWatch.start();
        List<Order> orderList = getAllOrders();
        Order order = null;
        if(CollectionUtils.isNotEmpty(orderList)) {
            order = Collections.min(orderList, Comparator.comparing(Order::getOrderQty));
        }
        stopWatch.stop();
        log.info(stopWatch.prettyPrint());
        return getJsonResponse(order);
    }

    /**
     * Method to find Order Book with given status
     * @param bookStatus
     * @return
     * @throws Exception
     */
    @GetMapping(value="/findBooksWithStatus{bookStatus}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public String findBooksWithStatus(@PathVariable String bookStatus) throws Exception {
        return getJsonResponse(orderBookRepository.getOrderBookStatus(bookStatus));
    }

    /**
     * Method to map Json response
     * @param object
     * @return
     * @throws Exception
     */
    private String getJsonResponse(Object object)throws Exception{
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS,false);
        objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        return objectMapper.writeValueAsString(object);
    }

    /**
     * Method to validate Order Book
     * @param orderBook
     * @return
     * @throws Exception
     */
    private boolean validateOrderBook(OrderBook orderBook)throws Exception{
        if("OPEN".equals(orderBook.getOrderStatus())){
            return true;
        }
        return false;
    }

    /**
     * Method to get all orders from cache
     * @return
     * @throws Exception
     */
    private List<Order> getAllOrders()throws Exception{
        List<OrderBook> orderBookInCache = (List<OrderBook>) orderBookRepository.findAll();
        List<Order> orderList = Lists.newArrayList();
        if(CollectionUtils.isNotEmpty(orderBookInCache)) {
            for (OrderBook orderBk : orderBookInCache) {
                orderList.addAll(orderBk.getOrderList());
            }
        }
        return orderList;
    }
}