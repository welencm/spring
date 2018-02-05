package red.mockers.mocker;

import red.mockers.common.Rate;

public interface RateProvider {
    public Rate getNextRate() throws Exception;
}
