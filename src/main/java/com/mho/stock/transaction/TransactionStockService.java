package com.mho.stock.transaction;

import com.mho.stock.service.StockService;

public class TransactionStockService {

    private final StockService stockService;

    public TransactionStockService(StockService stockService) {
        this.stockService = stockService;
    }

    /**
     * synchronized가 선언된 경우 @Transaction에 의해 재고의 수가 맞지 않게 된다.
     * @Transaction은 AOP의 개념으로 설계가 된것이다.
     * 아래와 같은 방식으로 작동을 하는데, synchronized를 사용할 경우
     * endTransaction();(commit)가 끝나기 전에 DB의 재고데이터에 접근할 경우 update가 되지 않은 정보를 이용해 업데이트가 된다.
     */
    public void decrease(Long id, Long quantity) {
        startTransaction();

        stockService.decrease(id, quantity);
        
        endTransaction();
    }

    private void endTransaction() {
    }

    private void startTransaction() {
    }
}
