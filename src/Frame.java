import java.io.ByteArrayOutputStream;

public class Frame {
    private final static int BUFFER_LIMIT_SIZE = 1 << 8 - 1;
    private final byte[] data;
    private final int destination;
    private final int size;
    Frame(byte[] data, int destination, int size) {
        if(data.length + 4 + 4 >= BUFFER_LIMIT_SIZE) throw new IllegalArgumentException("[ERROR]: Exceeded frame size!");
        this.data = data;
        this.destination = destination;
        this.size = size;
    }
    // create a byte stream that contains all the information carried by the frame
    ByteArrayOutputStream getStream() {
        ByteArrayOutputStream byteStream = new ByteArrayOutputStream(this.size);
        byteStream.write(this.destination); // first read destination
        byteStream.write(this.size); // then read size of data
        byteStream.writeBytes(this.data); // then read the data according to reported size?
        return byteStream;
    }
}
