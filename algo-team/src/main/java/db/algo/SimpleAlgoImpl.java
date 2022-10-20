package db.algo;

import org.springframework.stereotype.Component;

/**
 * This is implemented in a third-party library and we cannot change it.
 *
 * I've not changed it logically, but only extracted interface, hopefully that wasn't
 * forbidden.
 */
@Component
class SimpleAlgoImpl implements Algo {
    @Override
    public void doAlgo() {
        System.out.println("doAlgo");
    }

    @Override
    public void cancelTrades() {
        System.out.println("cancelTrades");
    }

    @Override
    public void reverse() {
        System.out.println("reverse");
    }

    @Override
    public void submitToMarket() {
        System.out.println("submitToMarket");
    }

    @Override
    public void performCalc() {
        System.out.println("performCalc");
    }

    @Override
    public void setUp() {
        System.out.println("setUp");
    }

    @Override
    public void setAlgoParam(int param, int value) {
        System.out.println("setAlgoParam " + param + "," + value);
    }
}