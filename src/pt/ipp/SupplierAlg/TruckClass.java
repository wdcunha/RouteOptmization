package pt.ipp.SupplierAlg;

import java.util.concurrent.atomic.AtomicInteger;

public class TruckClass {

    private static final AtomicInteger count_id  = new AtomicInteger(0);
    private int truckId;
    private int capDisp;
    private final int capacidadeTotal = 200;
    private final int truckCost = 50;

    public TruckClass() {
        truckId = count_id.incrementAndGet();
        capDisp = capacidadeTotal;
    }


    public int getTruckId() {
        return truckId;
    }

    public int getCapDisp() {
        return capDisp;
    }

    public void setCapDisp(int capDisp) {
        this.capDisp = capDisp;
    }

    public int getcapacidadeTotal() {
        return capacidadeTotal;
    }

    public int getTruckCost() {
        return truckCost;
    }
}
