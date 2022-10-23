package db.algo;

public interface Algo {

    void doAlgo();

    void cancelTrades();

    void reverse();

    void submitToMarket();

    void performCalc();

    void setUp();

    void setAlgoParam(int param, int value);
}
