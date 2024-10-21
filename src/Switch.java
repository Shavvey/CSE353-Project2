
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Switch {
    public static final int SWITCH_PORT = 8000;
    private final ExecutorService threadPool;
    HashMap<Integer, Node> switchingTable;
    private final ArrayList<Frame> buffer;
    private final static int MAX_NODE_CONNECTIONS = 1 << 8 - 1; // 255 max node connections
    Switch() {
        // the routing table should be able to resolve a node address with the correct node
        // once we have the node, we should be able to preform the routing accordingly
        // if no entry in the routing table exists, flood the network and wait for ACK for the
        // intended recipient
        switchingTable = new HashMap<>();
        // instantiate a new thread pool that we will used for
        // different node connections
        threadPool = Executors.newFixedThreadPool(MAX_NODE_CONNECTIONS);
        // create a frame buffer using an arraylist (could use priority queue to implement node priority later)
        buffer = new ArrayList<>();

    }
    public void startSwitch() {
        Runnable serverTask = () -> {
            try {
                ServerSocket serverSocket = new ServerSocket(SWITCH_PORT);
                while (true) {
                    // accept a new client request
                    Socket socket = serverSocket.accept();
                    // bring up thread from thread pool to handle new node connection
                    // use private inner class to handle hub routine
                    threadPool.submit(new SwitchConnect(socket));

                }
            } catch (IOException e) {
                System.err.println("[ERROR]: Unable to preform client request!");
                throw new RuntimeException(e);
            }
        };
    }
    private class SwitchConnect implements Runnable {
        private final Socket socket;
        SwitchConnect(Socket socket) {
            this.socket = socket;
        }
        // perform connection routine to set-up port number for further node communication
        private void connect() {
            try {
                // try to open input stream, get id of the newly created node
                InputStream is = socket.getInputStream();
                int ID = is.read();
                OutputStream os = socket.getOutputStream();
                // give a new port num to serve the node connection
                os.write(ID + SWITCH_PORT);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

        }

        @Override
        public void run() {
        }
    }

}
