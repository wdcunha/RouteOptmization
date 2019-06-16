package pt.ipp.SupplierAlg;

public class RouteSupplierClass {

    private int truckId;
    private int origX;
    private int origY;
    private int destX;
    private int destY;
    private int dist;
    private int truckCost;


    public RouteSupplierClass(int truckId, int origX, int origY, int destX, int destY, int dist, int truckCost) {
        this.truckId = truckId;
        this.origX = origX;
        this.origY = origY;
        this.destX = destX;
        this.destY = destY;
        this.truckCost = truckCost;
        this.dist = dist;
    }

    public int getTruckId() {
        return truckId;
    }

    public void setTruckId(int truckId) {
        this.truckId = truckId;
    }

    public int getTruckCost() {
        return truckCost;
    }

    public void setTruckCost(int truckCost) {
        this.truckCost = truckCost;
    }

    public int getOrigX() {
        return origX;
    }

    public void setOrigX(int origX) {
        this.origX = origX;
    }

    public int getOrigY() {
        return origY;
    }

    public void setOrigY(int origY) {
        this.origY = origY;
    }

    public int getDestX() {
        return destX;
    }

    public void setDestX(int destX) {
        this.destX = destX;
    }

    public int getDestY() {
        return destY;
    }

    public void setDestY(int destY) {
        this.destY = destY;
    }

    public int getDist() {
        return dist;
    }

    public void setDist(int dist) {
        this.dist = dist;
    }
}
