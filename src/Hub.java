import java.util.HashMap;

public class Hub {
    // routing table should match each destination address
    // with an given node inside the network
    HashMap<Integer, Node> routingTable;
    Hub() {
        routingTable = new HashMap<>();
    }

}
