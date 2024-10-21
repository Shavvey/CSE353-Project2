
import java.util.Optional;

public class Node {
    // integer id, used by the hub to transmit frames to the node
    Integer ID;

    // assigned port number, obtained during connection via the hub
    // attempt to handle the frame, send the bytes over the
    // correct node once we have decoded the frame
    Node(Integer ID) {
        // socket used to communicate with the node
    }
    Optional<byte[]> decodeFrame(Frame frame) {
        throw new RuntimeException("[ERROR] Unimplemented!");
    }
}
