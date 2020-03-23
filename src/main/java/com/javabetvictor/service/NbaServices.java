package com.javabetvictor.service;


import com.javabetvictor.model.Data;
import com.javabetvictor.model.DataElement;

import com.javabetvictor.repo.Match;
import com.javabetvictor.repo.MatchRepository;

import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;


import java.io.IOException;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

import java.util.stream.Collectors;



@Slf4j
@Service
public class NbaServices implements NbaService{

    private static final org.slf4j.Logger LOGGER = org.slf4j.LoggerFactory.getLogger(NbaServices.class);

    @Value("${nba.endpoint}")
    public String ROOT_URL;


    RestTemplate restTemplate;

    private MatchRepository matchRepository;

    @Autowired
    public NbaServices(RestTemplate restTemplate, MatchRepository matchRepository) {
        this.restTemplate = restTemplate;
        this.matchRepository = matchRepository;
    }


    @Cacheable("nba")
    public List<DataElement> getAllNBAMatchesByDate(String Seasons,String page,String team_ids,String per_page,String date){

        LOGGER.info("NbaServices : getAllNBAMatchesByDate");
        UriComponentsBuilder builder = UriComponentsBuilder
                .fromUriString(ROOT_URL)
                // Add query parameter
                .queryParam("Seasons", "")
                .queryParam("page", "1")
                .queryParam("team_ids", "")
                .queryParam("per_page","10")
                .queryParam("date","2019-02-08");


        ResponseEntity<Data> responseEntity = restTemplate.exchange(
                builder.toUriString() , HttpMethod.GET, new HttpEntity<Data>(null,createHttpHeaders()) ,Data.class);

         Data response = responseEntity.getBody();
         System.out.println(response.getData().size());

        DateTimeFormatter inputFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
        DateTimeFormatter outputFormatter = DateTimeFormatter.ofPattern("dd-MM-yyy");

        LocalDate dat = LocalDate.parse("2018-04-10T04:00:00.000Z", inputFormatter);
        String formattedDate = outputFormatter.format(dat);
        System.out.println(formattedDate);

        List<DataElement> result = response.getData().stream().filter(dataElement ->  datesFormat(dataElement.getDate()).equals("2019-02-08")).collect(Collectors.toList());

        return result;

    }


    public List<DataElement> getAllNBATeams(){

        LOGGER.info("NbaServices :getAllTeams");
        ResponseEntity<Data> responseEntity = restTemplate.exchange(
                ROOT_URL , HttpMethod.GET, new HttpEntity<Data>(null,createHttpHeaders()) ,Data.class);

        Data response = responseEntity.getBody();

        return response.getData();

    }

    public DataElement getIdTeam(Long idTeam){

        LOGGER.info("NbaServices: getIdTeam");
        ResponseEntity<DataElement> responseEntity = restTemplate.exchange(
                ROOT_URL+"/"+idTeam , HttpMethod.GET,
                new HttpEntity<DataElement>(null, createHttpHeaders()), DataElement.class);

        DataElement response = responseEntity.getBody();

        return response;

    }


     public DataElement AddCommnentsGame(Map<String,String> commentGames, Long idTeam){

         LOGGER.info("NbaServices: AddCommnentsGame");
         ResponseEntity<DataElement> responseEntity = restTemplate.exchange(
                 ROOT_URL+"/"+idTeam , HttpMethod.GET,
                 new HttpEntity<DataElement>(null, createHttpHeaders()), DataElement.class);

         DataElement response = responseEntity.getBody();
         response.setComments(commentGames);

         return response;

     }

    @Override
    public void insertMatch() {

        LOGGER.info("NbaServices: insertMatch");
        List<DataElement>  matches = getAllNBATeams();
        Match m = new Match();
        for (DataElement mt : matches) {

            m.setMatchId(mt.getId());
            m.setDateMatch(mt.getDate());
            m.setAway_team(mt.getVisitor_team().getName());
            m.setHome_team(mt.getHome_team().getName());

            m.setAway_team_score(mt.getVisitor_team_score());
            m.setHome_team_score(mt.getHome_team_score());
            m.setComments(mt.getComments().toString());

            matchRepository.save(m);

        }

    }

     @Override
     public void updateComments(Map<String,String> commentGames, Long idTeam){

         LOGGER.info("NbaServices: updateComments");
         DataElement delm = AddCommnentsGame(commentGames,idTeam);

         Match m =  matchRepository.findOne(idTeam);
         m.setComments(delm.getComments().toString());

         matchRepository.save(m);

     }



    @Override
    public List<String> getAllComments(Long idMatch) throws IOException, ParseException, ClassNotFoundException {

         LOGGER.info("NbaServices: getAllComments");
         SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");  //01-01-1970 01:00:00
         Match mt = matchRepository.findOne(idMatch);

         Map<Date, String> map = new TreeMap<Date, String>(new Comparator<Date>() {
            public int compare(Date date1, Date date2) {
                return date2.compareTo(date1);
            }
        });

        for (Map.Entry<Date,String> entry :toHashMap(mt.getComments()).entrySet() ){
            map.put(entry.getKey(),entry.getValue());
        }

      return new ArrayList<String>(map.values());
    }

    @Override
    public void getMatchById(Long idMatch) {

        Match mt =  matchRepository.findOne(idMatch);
        System.out.println(mt.toString());

    }




    private HttpHeaders createHttpHeaders()
    {
        HttpHeaders headers = new HttpHeaders();

        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
        headers.set("X-RapidAPI-Host","free-nba.p.rapidapi.com");
        headers.set("X-RapidAPI-Key","39984d95c3msh7d622673e47e047p13d454jsnd7427ec8f40f");
        headers.setContentType(MediaType.APPLICATION_JSON);
        return headers;
    }


    private String datesFormat(String dt){
        return dt.split("T")[0];
    }


    private Map<Date,String>  toHashMap(String str) throws ParseException {

        LOGGER.info("NbaServices: toHashMap");
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");  //01-01-1970 01:00:00

        String value = str.substring(1, str.length() - 1); //remove curly brackets
        String[] keyValuePairs = value.split(",");  //split the string to creat key-value pairs
        Map<Date, String> map = new HashMap<>();

        for (String pair : keyValuePairs)
        {
            String[] entry = pair.split("=");  //split the pairs to get key and value
            map.put(dateFormat.parse(entry[0].trim()), entry[1].trim());
        }

        return map;
    }

}
