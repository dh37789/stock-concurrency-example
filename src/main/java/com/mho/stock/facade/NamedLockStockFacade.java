package com.mho.stock.facade;

import com.mho.stock.repository.LockRepository;
import com.mho.stock.service.StockService;
import org.springframework.stereotype.Service;

@Service
public class NamedLockStockFacade {

    private final StockService stockService;
    private final LockRepository lockRepository;

    public NamedLockStockFacade(StockService stockService, LockRepository lockRepository) {
        this.stockService = stockService;
        this.lockRepository = lockRepository;
    }

    public void decrease(Long id, Long quantity) {
        try {
            lockRepository.getLock(id.toString());
            stockService.decreaseWithNamedLock(id, quantity);
        } finally {
            lockRepository.releaseLock(id.toString());
        }
    }
}
