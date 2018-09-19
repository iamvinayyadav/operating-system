import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Admin
 */
public class Producer_consumer_Syn {
    public static void main(String[] args) {
        ArrayList<Integer> b = new ArrayList<>();
        Thread ob1 = new Thread(new Producer(b));
        Thread ob2 = new Thread(new Consumer(b));
        ob2.start();
        ob1.start();
    }
    /*
    OUTPUT: 
        
    */
}

class Producer implements Runnable {
    
    List<Integer> b; 
    final int limit = 5;
    public Producer(List<Integer> b) {
        this.b = b;
    }
    
    @Override
    public void run() {
        while(true)
            try {
                Produce();
            } catch (InterruptedException ex) {
                System.out.println("error");
            }
    }   
    int i = 0;
    void Produce() throws InterruptedException {
        synchronized(b) {
            while(b.size() == limit){
                System.out.println("producer is waiting"); b.wait();
            }
        }
        synchronized(b) {
            System.out.println("producer is adding");
            b.add(i++);
            Thread.sleep(100);
            b.notify();
        }
    }
}


class Consumer implements Runnable {
    
    List<Integer> b; 
    final int empty = 0;
    public Consumer(List<Integer> b) {
        this.b = b;
    }
    
    @Override
    public void run() {
        while(true)
            try {
                Consume();
            } catch (InterruptedException ex) {
                System.out.println("exception");
            }
    }   
    int i = 0;
    void Consume() throws InterruptedException {
        synchronized(b) {
            while(b.size() == empty){
                System.out.println("Consumer is Waiting");
                b.wait();
            }
        }
        synchronized(b) {
            System.out.println("Consumer is consuming");
            b.remove(new Integer(i--));
            try {
                Thread.sleep(100);
            } catch (InterruptedException ex) {
                Logger.getLogger(Producer.class.getName()).log(Level.SEVERE, null, ex);
            }
            b.notify();
        }
    }
}
