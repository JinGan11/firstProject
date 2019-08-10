package com.ucar.combination;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class CombinationApplicationTests {

	@Test
	public void contextLoads() {
		Integer i1 = 100;
		Integer i2 = 100;
		Long l = 200l;
		Integer i3 = 200;
		Integer i4 = 200;
		System.out.println(i1==i2);//true
		System.out.println(l==(i1+i2));//true
		System.out.println(i3==i4);//false
		System.out.println(i1);
		System.out.println(i2);
		System.out.println(i3);
		System.out.println(i4);
	}

	public static void main(String[] args) {

	}

}
class tests extends Thread implements Runnable {

	@Override
	public void run() {
		System.out.println("this is run()");
	}

	public static void main(String[] args) {
	Thread t = new Thread(new tests());
		t.start();
	}
}
