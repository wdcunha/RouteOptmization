package pt.ipp.SupplierAlg;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Scanner;

class SupplierProcessStarter {

    private static ArrayList<DataSupplierClass> dataSupplierClasses;
    private static ArrayList<DataSupplierClass> dataCalcDistPoint;
    private static ArrayList<RouteSupplierClass> routes;
    private static ArrayList<TruckClass> truckList;
    private static ArrayList<OrderClass> orderList;
    private static ArrayList<OrderClass> allOrdersList;

    private static DataSupplierClass nextNeighbor;
    private static DataSupplierClass dcDistance;
    private static TruckClass truck;
    private static OrderClass finalOrderRes;

    private static int totalProd;
    private static int qtdTransportada;
    private static int qtdRecolhida;
    private static int totalDist;
    private static double totalCost;


    public static void main(String[] args) {

        allOrdersList = new ArrayList<>();

        initVaribles();

//        verifyLists();

        Scanner input = new Scanner(System.in);
        System.out.print("Quantidade a encomendar: ");
        totalProd = Integer.parseInt(input.nextLine());

        calcTruckDemand();

        cenario1NextDcDistance();

        cenario2NextNeighbor();

        melhorRota();

    }

    public static void initVaribles(){

        dataSupplierClasses = new ArrayList<>();
        dataCalcDistPoint = new ArrayList<>();
        truckList = new ArrayList<>();
        orderList = new ArrayList<>();
        routes = new ArrayList<>();
        nextNeighbor = new DataSupplierClass();
        dcDistance = new DataSupplierClass();
        truck = new TruckClass();
        qtdTransportada = 0;
        qtdRecolhida = 0;
        totalDist = 0;

        dataFeed();

        dcDistance = dataSupplierClasses.get(0);

    }

    private static void dataFeed() {
        dataSupplierClasses.add(new DataSupplierClass(1,8,286,95));
        dataSupplierClasses.add(new DataSupplierClass(2,24,638,99));
        dataSupplierClasses.add(new DataSupplierClass(3,43,41,114));
        dataSupplierClasses.add(new DataSupplierClass(4,44,937,69));
        dataSupplierClasses.add(new DataSupplierClass(5,49,973,44));
        dataSupplierClasses.add(new DataSupplierClass(6,68,541,116));
        dataSupplierClasses.add(new DataSupplierClass(7,192,450,72));
        dataSupplierClasses.add(new DataSupplierClass(8,220,166,67));
        dataSupplierClasses.add(new DataSupplierClass(9,260,860,102));
        dataSupplierClasses.add(new DataSupplierClass(10,271,450,71));
        dataSupplierClasses.add(new DataSupplierClass(11,313,215,61));
        dataSupplierClasses.add(new DataSupplierClass(12,328,797,100));
        dataSupplierClasses.add(new DataSupplierClass(13,336,665,28));
        dataSupplierClasses.add(new DataSupplierClass(14,403,278,37));
        dataSupplierClasses.add(new DataSupplierClass(15,436,655,106));
        dataSupplierClasses.add(new DataSupplierClass(16,457,994,40));
        dataSupplierClasses.add(new DataSupplierClass(17,476,581,43));
        dataSupplierClasses.add(new DataSupplierClass(18,491,205,42));
        dataSupplierClasses.add(new DataSupplierClass(19,510,189,113));
        dataSupplierClasses.add(new DataSupplierClass(20,555,753,60));
        dataSupplierClasses.add(new DataSupplierClass(21,574,786,74));
        dataSupplierClasses.add(new DataSupplierClass(22,595,604,21));
        dataSupplierClasses.add(new DataSupplierClass(23,631,205,99));
        dataSupplierClasses.add(new DataSupplierClass(24,635,455,30));
        dataSupplierClasses.add(new DataSupplierClass(25,643,139,90));
        dataSupplierClasses.add(new DataSupplierClass(26,673,803,90));
        dataSupplierClasses.add(new DataSupplierClass(27,706,838,116));
        dataSupplierClasses.add(new DataSupplierClass(28,749,634,56));
        dataSupplierClasses.add(new DataSupplierClass(29,750,265,46));
        dataSupplierClasses.add(new DataSupplierClass(30,759,147,90));
        dataSupplierClasses.add(new DataSupplierClass(31,767,750,107));
        dataSupplierClasses.add(new DataSupplierClass(32,821,422,105));
        dataSupplierClasses.add(new DataSupplierClass(33,876,514,87));
        dataSupplierClasses.add(new DataSupplierClass(34,880,198,41));
        dataSupplierClasses.add(new DataSupplierClass(35,887,670,43));
        dataSupplierClasses.add(new DataSupplierClass(36,919,336,84));
        dataSupplierClasses.add(new DataSupplierClass(37,928,310,41));
        dataSupplierClasses.add(new DataSupplierClass(38,972,423,112));
        dataSupplierClasses.add(new DataSupplierClass(39,976,373,91));
        dataSupplierClasses.add(new DataSupplierClass(40,987,667,26));

        orderingList(dataSupplierClasses);

    }

    private static void orderingList(ArrayList listName){
        // order ascendently the list by distance field
        Collections.sort (listName, new Comparator() {
            public int compare(Object o1, Object o2) {
                DataSupplierClass p1 = (DataSupplierClass) o1;
                DataSupplierClass p2 = (DataSupplierClass) o2;
                return p1.getDistance() < p2.getDistance() ? -1 : (p1.getDistance() > p2.getDistance() ? +1 : 0);
            }
        });
    }


    public static int calcDistance(int p1x, int p1y, int p2x, int p2y) {

//        return (Math.abs(p1x - p2x) + Math.abs(p1y - p2y));
        return (int) Math.sqrt(Math.pow((p1x - p2x),2) + Math.pow((p1y - p2y),2));
    }

    private static TruckClass checkFreeTruck(){

        truck = truckList.stream()
                .filter(v -> v.getCapDisp() > 0)
                .findFirst()
                .orElse(null);
//        System.out.println("checkFreeTruck: "+truck.getTruckId());
        return truck;
    }

    private static void calcTruckDemand(){

        int totalTrucks = (int) Math.ceil(totalProd / 200.0);
        System.out.println("totalTrucks: "+totalTrucks);

        int capacidadeRest = totalProd;

        for (int i = 0; i < totalTrucks; i++) {
            truckList.add(new TruckClass());
        }

        for (TruckClass trck : truckList) {

            if (capacidadeRest < 200){
                trck.setCapDisp(capacidadeRest);
//                System.out.println("calcTruckDemand: "+trck.getTruckId()+"," + trck.getCapDisp()+"," + trck.getcapacidadeTotal()+"," + trck.getTruckCost());
                continue;
            }
            capacidadeRest -= 200;

//            System.out.println("calcTruckDemand: "+trck.getTruckId()+"," + trck.getCapDisp()+"," + trck.getcapacidadeTotal()+"," + trck.getTruckCost());
        }
    }

    public static void cenario1NextDcDistance(){


        while (qtdTransportada < totalProd){

            truck = checkFreeTruck();
            routes.add(new RouteSupplierClass(truck.getTruckId(),500, 500, dcDistance.getX(), dcDistance.getY(), dcDistance.getDistance(), truck.getTruckCost()));

            System.out.println("* --------- * DcDistance * --------- *");
            System.out.println("| truck free: "+truck.getTruckId()+", Capac. disp: "+truck.getCapDisp());

            int count = 0;

            while (truck.getCapDisp() > 0){

                if (dcDistance.getProd() <= truck.getCapDisp()){

                    DataSupplierClass posAtual = dcDistance;
                    qtdRecolhida += dcDistance.getProd();
                    int qtd = truck.getCapDisp()- dcDistance.getProd();
                    count++;
                    truck.setCapDisp(qtd);
                    System.out.println("| Posição atual ("+count+"):  "+ dcDistance.getId()+"," + dcDistance.getX()+"," + dcDistance.getY()+"," + dcDistance.getProd()+"," + dcDistance.getDistance());
                    System.out.println("| Qtd recolhida:  "+qtdRecolhida+", Capac. disp: " + truck.getCapDisp());
                    dcDistance.setProd(0);
                    dataSupplierClasses.get(searchDataList(dcDistance.getId())).setProd(0);
                    if(truck.getCapDisp() <= 0){
                        qtdTransportada += qtdRecolhida;
//                        listRouts();
                        continue;
                    } else {
                        if (nextDistance() != null){
                            dcDistance = nextDistance();
                            routes.add(new RouteSupplierClass(truck.getTruckId(),posAtual.getX(), posAtual.getY(), dcDistance.getX(), dcDistance.getY(), dcDistance.getDistance(), truck.getTruckCost()));
                        }
                    }
                } else {
                    DataSupplierClass posAtual = dcDistance;
                    int qtd = dcDistance.getProd()-truck.getCapDisp();
                    count++;
                    qtdRecolhida += truck.getCapDisp();
                    qtdTransportada += qtdRecolhida;
                    dcDistance.setProd(qtd);
                    dataSupplierClasses.get(searchDataList(dcDistance.getId())).setProd(qtd);
                    truck.setCapDisp(0);
                    System.out.println("| Posição atual else("+count+"):  "+ dcDistance.getId()+"," + dcDistance.getX()+"," + dcDistance.getY()+"," + dcDistance.getProd()+"," + dcDistance.getDistance());
                    System.out.println("| Qtd recolhida else:  "+qtdRecolhida+", Capac. disp: " + truck.getCapDisp());
                }
            }

            //regresso
            routes.add(new RouteSupplierClass(truck.getTruckId(), dcDistance.getX(), dcDistance.getY(), 500, 500, getDcDist(dcDistance.getId()), truck.getTruckCost()));
            orderList.add(new OrderClass(routes));
            calcListOrdersParcial(truck.getTruckId());
//            listOrders();
//            listRouts();
            totalDist = 0;
            totalCost = 0;
            qtdRecolhida = 0;
            routes = new ArrayList<>();

        }
        System.out.println("* ---------------- * ---------------- *");
        System.out.println("| >> qtd total transp:  "+qtdTransportada);
        calcListOrders("Distância do DC");

    }

    public static void cenario2NextNeighbor(){

        initVaribles();

        calcTruckDemand();


        while (qtdTransportada < totalProd){

            truck = checkFreeTruck();
            routes.add(new RouteSupplierClass(truck.getTruckId(),500, 500, nextNeighbor.getX(), nextNeighbor.getY(), nextNeighbor.getDistance(), truck.getTruckCost()));

            System.out.println("* --------- * NextNeighbor * --------- *");
            System.out.println("| truck free: "+truck.getTruckId()+", Capac. disp: "+truck.getCapDisp());

            int count = 0;

            while (truck.getCapDisp() > 0){

                if (nextNeighbor.getProd() <= truck.getCapDisp()){

                    DataSupplierClass posAtual = nextNeighbor;
                    qtdRecolhida += nextNeighbor.getProd();
                    int qtd = truck.getCapDisp()-nextNeighbor.getProd();
                    count++;
                    truck.setCapDisp(qtd);
                    System.out.println("| Posição atual ("+count+"):  "+nextNeighbor.getId()+"," + nextNeighbor.getX()+"," + nextNeighbor.getY()+"," + nextNeighbor.getProd()+"," + nextNeighbor.getDistance());
                    System.out.println("| Qtd recolhida:  "+qtdRecolhida+", Capac. disp: " + truck.getCapDisp());
                    nextNeighbor.setProd(0);
                    dataSupplierClasses.get(searchDataList(nextNeighbor.getId())).setProd(0);
                    if(truck.getCapDisp() <= 0){
                        qtdTransportada += qtdRecolhida;
//                        listRouts();
                        continue;
                    } else {
                        if (nextNeighbor(nextNeighbor.getX(),nextNeighbor.getY()) != null){
                            nextNeighbor = nextNeighbor(nextNeighbor.getX(),nextNeighbor.getY());
                            routes.add(new RouteSupplierClass(truck.getTruckId(),posAtual.getX(), posAtual.getY(), nextNeighbor.getX(), nextNeighbor.getY(), nextNeighbor.getDistance(), truck.getTruckCost()));
                        }
                    }
                } else {
                    DataSupplierClass posAtual = nextNeighbor;
                    int qtd = nextNeighbor.getProd()-truck.getCapDisp();
                    count++;
                    qtdRecolhida += truck.getCapDisp();
                    qtdTransportada += qtdRecolhida;
                    nextNeighbor.setProd(qtd);
                    dataSupplierClasses.get(searchDataList(nextNeighbor.getId())).setProd(qtd);
                    truck.setCapDisp(0);
                    System.out.println("| Posição atual else("+count+"):  "+nextNeighbor.getId()+"," + nextNeighbor.getX()+"," + nextNeighbor.getY()+"," + nextNeighbor.getProd()+"," + nextNeighbor.getDistance());
                    System.out.println("| Qtd recolhida else:  "+qtdRecolhida+", Capac. disp: " + truck.getCapDisp());
                }
            }

            //regresso

            routes.add(new RouteSupplierClass(truck.getTruckId(), nextNeighbor.getX(), nextNeighbor.getY(), 500, 500, getDcDist(nextNeighbor.getId()), truck.getTruckCost()));
            orderList.add(new OrderClass(routes));
            calcListOrdersParcial(truck.getTruckId());
//            listOrders();
//            listRouts();
            totalDist = 0;
            totalCost = 0;
            qtdRecolhida = 0;
            routes = new ArrayList<>();

        }
        System.out.println("* ---------------- * ---------------- *");
        System.out.println(">> qtd total transp:  "+qtdTransportada);
        calcListOrders("Next Neighbor");

    }


    static DataSupplierClass nextDistance(){

        return dataSupplierClasses.stream().filter(v -> v.getProd() > 0).findFirst().orElse(null);
    }


    static DataSupplierClass nextNeighbor(int px, int py){

        DataSupplierClass dt = new DataSupplierClass();

        dataCalcDistPoint = new ArrayList<>();

        for (int i = 0; i < dataSupplierClasses.size(); i++) {
            dt = new DataSupplierClass(dataSupplierClasses.get(i));
//            System.out.println("dt:  "+dt.getId()+"," + dt.getOrigX()+"," + dt.getOrigY()+"," + dt.getProd()+"," + dt.getDistance());

            dataCalcDistPoint.add(dt);
            dataCalcDistPoint.get(i).setDistance(calcDistance(px,py,dt.getX(),dt.getY()));

        }
        orderingList(dataCalcDistPoint);

        return dataCalcDistPoint.stream().filter(v -> v.getProd() > 0).findFirst().orElse(null);
    }


    static void calcListOrders(String nomeCenario){

        for (OrderClass order : orderList) {
            totalCost += order.getTotalCost();
            totalDist += order.getTotalPercorrido();
        }

        System.out.println("| > > Total Percorrido: " + totalDist);
        System.out.println("| > > Custo Total: " + totalCost);

        finalOrderRes = new OrderClass();
        finalOrderRes.setTotalPercorrido(totalDist);
        finalOrderRes.setTotalCost(totalCost);
        finalOrderRes.setNomeCenario(nomeCenario);
        allOrdersList.add(finalOrderRes);

    }

    public static void melhorRota() {

        int melhorTrajeto = allOrdersList.get(0).getTotalPercorrido();
        double melhorCusto = allOrdersList.get(0).getTotalPercorrido();

        OrderClass bestOrder = new OrderClass();


        System.out.println("* ---------------- * ---------------- *");
        System.out.println("*                                     *");

        for (OrderClass order : allOrdersList) {

            if (order.getTotalCost() < melhorCusto && order.getTotalPercorrido() < melhorTrajeto){
                bestOrder = order;
            }

            System.out.println("* Cenário calculado: "+order.getNomeCenario());
            System.out.println("* Total percorrido: "+order.getTotalPercorrido());
            System.out.println("* Custo total: "+order.getTotalCost());
            System.out.println("*                  *                  *");
        }
        System.out.println("* ---------------- * ---------------- *");
        System.out.println("*        M E L H O R   R O T A        *");
        System.out.println("* Cenário calculado: "+bestOrder.getNomeCenario());
        System.out.println("* Total percorrido: "+bestOrder.getTotalPercorrido());
        System.out.println("* Custo total: "+bestOrder.getTotalCost());

        System.out.println("* ---------------- * ---------------- *");

    }

    static void calcListOrdersParcial(int truckId){

        for (OrderClass order : orderList) {
            if (truckId == order.getRoutes().get(0).getTruckId()){
                totalCost += order.getTotalCost();
                totalDist += order.getTotalPercorrido();
            }
        }

        System.out.println("| > > Dist Truck"+truck.getTruckId()+" percorrida: " + totalDist);
        System.out.println("| > > Custo parcial: " + totalCost);
    }

    static int getDcDist(int dataId){

        int dcDist = 0;

        for (DataSupplierClass data : dataSupplierClasses) {
            if (data.getId() == dataId){

                dcDist = data.getDistance();

            }
//            System.out.println(calcDistance());
        }
        return dcDist;
    }

    static int searchDataList(int dataId){

        int indexOfVar = 0;

        for (int i = 0; i < dataSupplierClasses.size(); i++) {

            if(dataSupplierClasses.get(i).getId() == dataId){
                indexOfVar = dataSupplierClasses.indexOf(dataSupplierClasses.get(i));
            }
        }
        return indexOfVar;
    }

    static void verifyLists(){
        for (DataSupplierClass data : dataSupplierClasses) {

            System.out.println("dataSupplierClasses: "+data.getId()+"," + data.getX()+"," + data.getY()+"," + data.getProd()+"," + data.getDistance());
//            System.out.println(calcDistance());
        }
    }

    static void listRouts(){

        for (RouteSupplierClass route : routes) {

            System.out.println("RoutesList: "+route.getTruckId()+"," +route.getOrigX()+"," + route.getOrigY()+"," + route.getDestX()+"," + route.getDestY()+"," + route.getDist()+"," + route.getTruckCost());
        }
    }

    static void calcTotalProd(){

        for (DataSupplierClass total : dataSupplierClasses) {
            totalProd += total.getProd();
        }
        System.out.println("Total Prod: "+totalProd);
    }

    static void listOrders(){

        System.out.println("orderList: origX, origY, destX, destY, dist");

        for (OrderClass order : orderList) {

            for (int i = 0; i < order.getRoutes().size(); i++) {

                System.out.println("orderList: " + order.getRoutes().get(i).getTruckId() + ", " + order.getRoutes().get(i).getOrigX() + ", "
                        + order.getRoutes().get(i).getOrigY() + ", " + order.getRoutes().get(i).getDestX() + ", " + order.getRoutes().get(i).getDestY() + ", "
                        + order.getRoutes().get(i).getDist() + ", " + order.getRoutes().get(i).getTruckCost());
            }
        }
    }


}








