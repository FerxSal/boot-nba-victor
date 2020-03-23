package com.javabetvictor;

import com.javabetvictor.model.DataElement;
import com.javabetvictor.service.NbaServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.ApplicationContext;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;


@SpringBootApplication
@EnableAutoConfiguration
@EnableCaching
public class SpringBootJdbcApplication {
	
	@Autowired
	NbaServices nbaService;

	public static void main(String[] args) {
		ApplicationContext context = SpringApplication.run(SpringBootJdbcApplication.class, args);
		NbaServices nbaService = context.getBean(NbaServices.class);

		nbaService.getAllNBAMatchesByDate("","1","","10","2018-02-08");

		System.out.println("Getting list of all people:");

		nbaService.getAllNBATeams();

		DataElement delm = nbaService.getIdTeam(47179L);

		System.out.println(delm.toString());

		SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
		String stringDate  = dateFormat.format(new Date());
		Date date = new Date();
		String anotherDate = dateFormat.format(date.getDate()+1);

		delm.getComments().put(stringDate,"this game is awsome");
		delm.getComments().put(anotherDate,"another good game");

		//DataElement withcomments = nbaService.AddCommnentsGame(delm.getComments(),25L);

		nbaService.insertMatch();

		nbaService.getMatchById(25L);

		DataElement withcomments = nbaService.AddCommnentsGame(delm.getComments(),25L);

		nbaService.updateComments(delm.getComments(),25L);

		List<String> res = null;
		try {
			res = nbaService.getAllComments(25L);
		} catch (ParseException | ClassNotFoundException e) {
			e.printStackTrace();
		}
		catch (IOException e) {
			e.printStackTrace();
		}


		System.out.println(res.toString());

	}
}