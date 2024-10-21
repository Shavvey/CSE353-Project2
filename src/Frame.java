import java.io.ByteArrayOutputStream;

public class Frame {
    private final static int BUFFER_LIMIT_SIZE = 1 << 8 - 1;
    private final byte[] data;
    private final byte source;
    private final byte destination;
    private final byte size;
    Frame(byte[] data, byte source, byte destination, byte size) {
        this.data = data;
        this.source = source;
        this.destination = destination;
        this.size = size;
    }
    // create a byte stream that contains all the information carried by the frame
    ByteArrayOutputStream getStream() {
        ByteArrayOutputStream byteStream = new ByteArrayOutputStream(this.size);
        byteStream.write(this.source);
        byteStream.write(this.destination); // first read destination
        byteStream.write(this.size); // then read size of data
        byteStream.writeBytes(this.data); // then read the data according to reported size?
        return byteStream;
    }
}
