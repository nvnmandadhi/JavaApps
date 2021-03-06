/**
 * 
 */
package deadlock;

import java.util.Random;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author Naveen
 *
 */
public class Runner {

    Account acc1 = new Account();
    Account acc2 = new Account();

    private Lock lock1 = new ReentrantLock();
    private Lock lock2 = new ReentrantLock();

    Random random = new Random();

    public void firstThread() {

	for (int i = 0; i < 10000; i++) {

	    lock1.lock();
	    lock2.lock();

	    try {
		Account.transfer(acc1, acc2, random.nextInt(100));
	    } finally {
		lock1.unlock();
		lock2.unlock();
	    }
	}

    }

    public void secondThread() {

	for (int i = 0; i < 10000; i++) {

	    lock1.lock();
	    lock2.lock();

	    try {
		Account.transfer(acc2, acc1, random.nextInt(100));
	    } finally {
		lock1.unlock();
		lock2.unlock();
	    }
	}
    }

    public void finished() {
	System.out.println("Account 1 balance: " + acc1.getBalance());
	System.out.println("Account 2 balance: " + acc2.getBalance());
	System.out.println("Total balance: " + (acc1.getBalance() + acc2.getBalance()));
    }
}
