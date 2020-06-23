package oscP5;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectInputStream;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;
import java.io.Serializable;

public class OscUtils {

    static final public byte[] serialize(Object o) {
        if (o instanceof Serializable) {
            return serialize((Serializable) o);
        }
        return new byte[0];
    }

    static final public byte[] serialize(Serializable o) {
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        ObjectOutput out = null;
        byte[] bytes = new byte[0];
        try {
            out = new ObjectOutputStream(bos);
            out.writeObject(o);
            bytes = bos.toByteArray();
        } catch (Exception e) {
        } finally {
            try {
                out.close();
                bos.close();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
        return bytes;
    }

    static final public Object deserialize(byte[] theBytes) {
        ByteArrayInputStream bis = new ByteArrayInputStream(theBytes);
        ObjectInput in = null;
        Object o = null;
        try {
            in = new ObjectInputStream(bis);
            o = in.readObject();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } finally {
            try {
                bis.close();
                in.close();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
        return o;
    }
}