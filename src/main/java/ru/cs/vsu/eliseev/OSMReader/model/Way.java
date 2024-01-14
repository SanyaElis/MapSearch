package ru.cs.vsu.eliseev.OSMReader.model;

import java.util.ArrayList;
import java.util.List;

public class Way extends ElementOnMap {
    private final List<Node> nodes;

    public Way(long id) {
        super(id);
        this.nodes = new ArrayList<>();
    }

    public Way(long id, List<Node> nodes) {
        super(id);

        if (nodes == null) {
            throw new NullPointerException("Nodes list can't be null");
        }
        if (nodes.size() < 2) {
            throw new RuntimeException("A way should have at least two nodes");
        }

        this.nodes = nodes;
    }

    public List<Node> getNodes() {
        return List.copyOf(nodes);
    }

    public void addNode(Node node) {
        if (node != null)
            nodes.add(node);
    }

    public void removeNode(int index) {
        if(nodes.size() == 2) {
            throw new RuntimeException("Can't remove node, only two remaining");
        }
        nodes.remove(index);
    }

    @Override
    public String getId() {
        return "W" + id;
    }
}
