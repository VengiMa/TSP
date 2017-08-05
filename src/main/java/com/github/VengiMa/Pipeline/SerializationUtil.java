package com.github.VengiMa.Pipeline;

import java.io.*;

/**
 * Created by Admin on 23.05.2017.
 */
public class SerializationUtil {
    /***
     *
     * @param obj
     * @return
     * @throws IOException
     */
    public static byte[] serialize(Object obj) throws IOException {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        ObjectOutputStream os = new ObjectOutputStream(out);
        os.writeObject(obj);
        return out.toByteArray();
    }

    /***
     *
     * @param obj
     * @return
     * @throws IOException
     * @throws ClassNotFoundException
     */
    public static Object deserialize(byte[] obj) throws IOException, ClassNotFoundException {
        ByteArrayInputStream in = new ByteArrayInputStream(obj);
        ObjectInputStream is = new ObjectInputStream(in);
        return is.readObject();
    }
}
