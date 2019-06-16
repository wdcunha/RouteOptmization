package pt.ipp.SupplierAlg;

import java.util.ArrayList;

public class OrderClass {

    private ArrayList<RouteSupplierClass> routes = new ArrayList<>();
    private final double cost = 0.3;
    private String nomeCenario;
    private int totalPercorrido;
    private double totalCost;

    public OrderClass() {
    }

    public OrderClass(ArrayList<RouteSupplierClass> routes) {
        this.routes = routes;
        calcDistCost();
    }

    private void calcDistCost() {

        for (RouteSupplierClass route : routes) {
            totalPercorrido += route.getDist();
            totalCost += route.getDist() * cost;
        }
        totalCost += routes.get(0).getTruckCost();
    }

    public double getTotalCost() {
        return totalCost;
    }

    public int getTotalPercorrido() {
        return totalPercorrido;
    }

    public double getCost() {
        return cost;
    }

    public ArrayList<RouteSupplierClass> getRoutes() {
        return routes;
    }

    public void setRoutes(ArrayList<RouteSupplierClass> routes) {
        this.routes = routes;
    }

    public void setTotalPercorrido(int totalPercorrido) {
        this.totalPercorrido = totalPercorrido;
    }

    public void setTotalCost(double totalCost) {
        this.totalCost = totalCost;
    }

    public String getNomeCenario() {
        return nomeCenario;
    }

    public void setNomeCenario(String nomeCenario) {
        this.nomeCenario = nomeCenario;
    }
}
