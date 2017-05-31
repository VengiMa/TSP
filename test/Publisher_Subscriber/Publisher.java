package Publisher_Subscriber;

import org.zeromq.ZMQ;

/**
 * Created by Admin on 21.05.2017.
 */
public class Publisher {
    public static void main (String [] args) throws InterruptedException {
        final ZMQ.Context ctx = ZMQ.context(1);
        final ZMQ.Socket pub = ctx.socket(ZMQ.PUB);
        pub.bind("tcp://*:6001");

        int counter = 0;
        while (true) {
            System.out.println("Publishing");
            pub.send("Test message " + counter);
            counter++;
            Thread.sleep(1000);
        }
    }
}
