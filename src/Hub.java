
import java.io.IOException;
import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Optional;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Hub {
    private final int HUB_PORT = 8000;
    private final ExecutorService threadPool;
    HashMap<Integer, Node> routingTable;
    private final ArrayList<Frame> buffer;
    private final static int MAX_NODE_CONNECTIONS = 1 << 8 - 1; // 255 max node connections
    Hub() {
        // the routing table should be able to resolve a node address with the correct node
        // once we have the node, we should be able to preform the routing accordingly
        // if no entry in the routing table exists, flood the network and wait for ACK for the
        // intended recipient
        routingTable = new HashMap<>();
        // instantiate a new thread pool that we will used for
        // different node connections
        threadPool = Executors.newFixedThreadPool(MAX_NODE_CONNECTIONS);
        // create a frame buffer using an arraylist (could use priority queue to implement node priority later)
        buffer = new ArrayList<>();

    }
    public void startHub() {
        Runnable serverTask = () -> {
            try {
                ServerSocket serverSocket = new ServerSocket(HUB_PORT);
                while (true) {
                    // accept a new client request
                    Socket socket = serverSocket.accept();
                    // bring up thread from thread pool to handle request
                    // use private inner class to handle hub routine
                    threadPool.submit(new HubReceive(socket));

                }
            } catch (IOException e) {
                System.err.println("[ERROR]: Unable to preform client request!");
                throw new RuntimeException(e);
            }
        };
    }
    private class HubReceive implements Runnable {
        private final Socket socket;
        HubReceive(Socket socket) {
            this.socket = socket;
        }
        Optional<Frame> getFrame() {
            try {
                InputStream is = this.socket.getInputStream();
                int destination = is.read();
                int size = is.read();
                byte[] data = is.readNBytes(size);
                Frame frame = new Frame(data, destination, size);
                buffer.add(frame);
                return Optional.of(frame);
            } catch (IOException e) {
                System.err.println("[ERROR]: Could not decode frame!");
                return Optional.empty();
            }

        }

        @Override
        public void run() {
            // get frame data
            Optional<Frame> frame = this.getFrame();
            // send to node via its id
        }
    }

}
