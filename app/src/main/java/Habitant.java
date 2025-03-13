import java.util.List;

public class Habitant {
    int id;
    String residentName;
    int floor;
    double area;
    List<Appliance> appliances;

    public Habitant(int id, String residentName, int floor, double area) {
        this.id = id;
        this.residentName = residentName;
        this.floor = floor;
        this.area = area;
    }
}