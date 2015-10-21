package co.com.importcolex.tejeduria.domain.util;

import java.util.Random;

import org.springframework.stereotype.Component;

@Component
public class CustomRandomGenerator {


	public long generateRandomTicket(Long range){		
		Random r = new Random();
		return (long)(r.nextDouble()*range);	 
	}
}
