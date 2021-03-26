

import ru.pflb.mq.dummy.exception.DummyException;
import ru.pflb.mq.dummy.implementation.ConnectionImpl;
import ru.pflb.mq.dummy.implementation.DestinationImpl;
import ru.pflb.mq.dummy.implementation.ProducerImpl;
import ru.pflb.mq.dummy.implementation.SessionImpl;
import ru.pflb.mq.dummy.interfaces.Connection;
import ru.pflb.mq.dummy.interfaces.Destination;
import ru.pflb.mq.dummy.interfaces.Producer;
import ru.pflb.mq.dummy.interfaces.Session;
import java.util.ArrayList;
import java.util.List;

public class MessageProducer implements Runnable {
    private List<String> file;
    private volatile boolean going = true;

    public MessageProducer(List<String> file) {
        this.file = file;


    }

    public boolean finish() {

        this.going = false;
        return going;
    }

    public void run() {

        ArrayList<String> listMessage = (ArrayList<String>) file;

        Connection connection = new ConnectionImpl();
        connection.start();
        Session session = new SessionImpl();
        connection.createSession(true);
        String queueName = "Message";
        Destination destination = new DestinationImpl(queueName);
        Producer producer = new ProducerImpl(destination);



        try {
            session.createDestination(queueName);
        } catch (DummyException e) {
            try {
                throw new DummyException();
            } catch (DummyException dummyException) {
                dummyException.printStackTrace();
            }
        }

        while (going) {
            for (String s : listMessage) {
                producer.send(s);
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            if (going==false)
                break;
            }
        }
        session.close();
        connection.close();
    }

}

