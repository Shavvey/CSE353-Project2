import java.io.ByteArrayOutputStream;

public class Frame {
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
        byteStream.write(source);
        byteStream.write(destination);
        // since we only the amount of data specified by the size,
        // the limit of each frame is only 255 bytes!
        byteStream.write(size);
        byteStream.writeBytes(data);
        return byteStream;
    }
}
