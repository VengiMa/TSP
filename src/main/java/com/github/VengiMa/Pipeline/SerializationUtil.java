/*
 * The MIT License (MIT)
 *
 * Copyright (c) 2017 Marco Venghaus
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */


package com.github.VengiMa.Pipeline;

import java.io.*;

/***
 * Class for transporting data between the Task Vent, Work and Sink.
 */
public class SerializationUtil {
    /***
     * Transforms the data into bytes
     * @param obj The object that is transformed
     * @return The object in bytes
     * @throws IOException
     */
    public static byte[] serialize(Object obj) throws IOException {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        ObjectOutputStream os = new ObjectOutputStream(out);
        os.writeObject(obj);
        return out.toByteArray();
    }

    /***
     * Transforms the bytes back into the data
     * @param obj The object that is transformed back
     * @return The data as the previous object
     * @throws IOException
     * @throws ClassNotFoundException
     */
    public static Object deserialize(byte[] obj) throws IOException, ClassNotFoundException {
        ByteArrayInputStream in = new ByteArrayInputStream(obj);
        ObjectInputStream is = new ObjectInputStream(in);
        return is.readObject();
    }
}
