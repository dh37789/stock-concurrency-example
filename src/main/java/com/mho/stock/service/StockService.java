package com.mho.stock.service;

import com.mho.stock.domain.Stock;
import com.mho.stock.repository.StockRepository;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
public class StockService {

    private StockRepository stockRepository;

    public StockService(StockRepository stockRepository) {
        this.stockRepository = stockRepository;
    }

    @Transactional
    public synchronized void decrease(Long id, Long quantity) {
        Stock stock = stockRepository.findById(id).orElseThrow();

        stock.decrease(quantity);

        stockRepository.saveAndFlush(stock);
    }

    /**
     * synchronized를 사용했을 경우 문제점은 서버가 1대일 경우는 상관이 없지만, 서버가 2대 이상이 될경우 문제가 될 수있다.
     * 서버1에서 재고의 수를 update 하는 동안 서버1외의 서버에서 해당 메소드를 접근했을 경우는 재고가 감소되지 않은 정보를 가져와 update를 할 수 있기 때문에
     * race condition이 발생할 수 있다.
     */
    public synchronized void decreaseBySynchronized(Long id, Long quantity) {
        Stock stock = stockRepository.findById(id).orElseThrow();

        stock.decrease(quantity);

        stockRepository.saveAndFlush(stock);
    }


}
