package com.esprit.examen.services;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.esprit.examen.entities.Stock;
import com.esprit.examen.repositories.StockRepository;

import lombok.extern.slf4j.Slf4j;

@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
public class StockServiceImplTest {

    private IStockService stockService;

    private StockRepository stockRepository;

    @Before
    public void setup() {
        stockRepository = mock(StockRepository.class);
        stockService = new StockServiceImpl(stockRepository);
    }

    @Test
    public void testRetrieveAllStocks() {
        List<Stock> stocks = new ArrayList<>();
        Stock stock1 = new Stock("Stock1", 100, 50);
        Stock stock2 = new Stock("Stock2", 200, 100);
        stocks.add(stock1);
        stocks.add(stock2);

        when(stockRepository.findAll()).thenReturn(stocks);

        List<Stock> result = stockService.retrieveAllStocks();

        assertEquals(stocks, null);
    }

    @Test
    public void testAddStock() {
        Stock stock = new Stock("New Stock", 50, 10);

        when(stockRepository.save(stock)).thenReturn(stock);

        Stock result = stockService.addStock(stock);

        assertEquals(stock, result);
    }

    @Test
    public void testDeleteStock() {
        Long stockId = 1L;

        stockService.deleteStock(stockId);

        // Verify that the stockRepository's deleteById() method was called with the correct argument
        verify(stockRepository).deleteById(stockId);
    }

    @Test
    public void testUpdateStock() {
        Stock stock = new Stock("Stock1", 100, 50);

        when(stockRepository.save(stock)).thenReturn(stock);

        Stock result = stockService.updateStock(stock);

        assertEquals(stock, result);
    }

    @Test
    public void testRetrieveStock() {
        Long stockId = 1L;
        Stock stock = new Stock("Stock1", 100, 50);

        when(stockRepository.findById(stockId)).thenReturn(Optional.of(stock));

        Stock result = stockService.retrieveStock(stockId);

        assertEquals(stock, result);
    }

    @Test
    public void testRetrieveStatusStock() {
        List<Stock> stocks = new ArrayList<>();
        Stock stock1 = new Stock("Stock1", 5, 10);
        Stock stock2 = new Stock("Stock2", 20, 50);
        stocks.add(stock1);
        stocks.add(stock2);

        when(stockRepository.retrieveStatusStock()).thenReturn(stocks);

        String result = stockService.retrieveStatusStock();

        String expected = "le stock Stock1 a udne quantité de 5 inférieur à la quantité minimale a ne pas dépasser de 10\nle stock Stock2 a une quantité de 20 inférieur à la quantité minim";
        assertEquals(expected, result);
    }
}
