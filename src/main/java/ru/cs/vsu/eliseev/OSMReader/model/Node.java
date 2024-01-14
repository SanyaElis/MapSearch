package ru.cs.vsu.eliseev.OSMReader.model;

public class Node extends ElementOnMap {
    private double lat;
    private double lon;

    public Node(long id, double lat, double lon) {
        super(id);
        this.lat = lat;
        this.lon = lon;
    }

    @Override
    public String getId() {
        return "N" + id;
    }

    public double getLat() {
        return lat;
    }

    public double getLon() {
        return lon;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public void setLon(double lon) {
        this.lon = lon;
    }
}
