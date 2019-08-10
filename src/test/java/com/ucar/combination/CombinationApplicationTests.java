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

	}
}
class tests extends Thread implements Runnable {

	@Override
	public void run() {
		System.out.println("this is run()");
	}

}
