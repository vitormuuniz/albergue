package br.com.albergue.suite;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;

import br.com.albergue.tests.get.CustomerGetTests;
import br.com.albergue.tests.get.ReservationGetTests;
import br.com.albergue.tests.get.RoomGetTests;
import br.com.albergue.tests.post.CustomerPostAndDeleteTests;
import br.com.albergue.tests.post.ReservationPostAndDeleteTests;
import br.com.albergue.tests.post.RoomPostAndDeleteTests;

@RunWith(Suite.class)
@Suite.SuiteClasses({
        ReservationPostAndDeleteTests.class,
        ReservationGetTests.class,
        CustomerPostAndDeleteTests.class,
        CustomerGetTests.class,
        RoomPostAndDeleteTests.class,
        RoomGetTests.class
})
public class DataApplicationTests {
	
    @Test
    public void contextLoads() {}
    
}