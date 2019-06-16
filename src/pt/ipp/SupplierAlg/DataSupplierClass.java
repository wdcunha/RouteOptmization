package pt.ipp.SupplierAlg;

public class DataSupplierClass {

    private int id;
    private int x;
    private int y;
    private int prod;
    private final int dcX = 500;
    private final int dcY = 500;
    private int distance;

    SupplierProcessStarter sup = new SupplierProcessStarter();

    public DataSupplierClass() {
    }

    public DataSupplierClass(int id, int x, int y, int prod) {
        this.id = id;
        this.x = x;
        this.y = y;
        this.prod = prod;
        this.distance = sup.calcDistance(dcX,dcY,x,y);
    }

    public DataSupplierClass(DataSupplierClass dt) {
        this.id = dt.id;
        this.x = dt.x;
        this.y = dt.y;
        this.prod = dt.prod;
        this.distance = dt.distance;
    }

    public int getDistance() {
        return distance;
    }

    public void setDistance(int distance) {
        this.distance = distance;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getProd() {
        return prod;
    }

    public void setProd(int prod) {
        this.prod = prod;
    }
}
