
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Hub {
    private final ExecutorService threadPool;
    HashMap<Integer, Node> routingTable;
    Frame[] buffer;
    private final static int MAX_NODE_CONNECTIONS = 1 << 8 - 1;
    Hub() {
        // the routing table should be able to resolve a node address with the correct node
        // once we have the node, we should be able to preform the routing accordingly
        // if no entry in the routing table exists, flood the network and wait for ACK for the
        // intended recipient
        routingTable = new HashMap<>();
        // instantiate a new thread pool that we will used for
        // different node connections
        threadPool = Executors.newFixedThreadPool(MAX_NODE_CONNECTIONS);

    }
    public void startHub() {
        Runnable serverTask = () -> {
            try {
                ServerSocket serverSocket = new ServerSocket(8000);
                while(true) {
                    // accept a new client request
                    Socket socket = serverSocket.accept();
                    threadPool.submit(new HubTask(socket));

                }
            } catch (IOException e) {
                System.err.println("[ERROR]: Unable to preform client request!");
                throw new RuntimeException(e);
            }

        };
    }
    private class HubTask implements Runnable {
        private final Socket socket;
        HubTask(Socket socket) {
            this.socket = socket;
        }

        @Override
        public void run() {

        }
    }

}
