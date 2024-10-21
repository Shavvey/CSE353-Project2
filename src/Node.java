
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

public class Node {
    // integer id, used by the hub to transmit frames to the node
    Integer ID;
    private int nodePortNum;

    // assigned port number, obtained during connection via the hub
    // attempt to handle the frame, send the bytes over the
    // correct node once we have decoded the frame
    Node(Integer ID) {
        // socket used to communicate with the node
        this.ID = ID;

    }
    public void connect() {
        try {
            Socket socket = new Socket("localhost", Switch.SWITCH_PORT);
            // attempt to receive the new client port num to after successful connection
            OutputStream os = socket.getOutputStream();
            os.write(ID);
            InputStream is = socket.getInputStream();
            nodePortNum = is.read();
            socket.close();
            System.out.println("New port number: " + nodePortNum);
        } catch (IOException e) {
            System.err.println("[ERROR]: Could not setup socket connection for switch-hub communication!");
            throw new RuntimeException(e);
        }
    }
}
