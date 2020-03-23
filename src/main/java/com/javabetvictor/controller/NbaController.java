package com.javabetvictor.controller;

import com.javabetvictor.model.CommentDto;
import com.javabetvictor.model.DataElement;
import com.javabetvictor.service.NbaServices;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.text.ParseException;
import java.util.List;

@Slf4j
@RestController
@RequestMapping(path = "/nba")
public class NbaController {

    private static final org.slf4j.Logger LOGGER = org.slf4j.LoggerFactory.getLogger(NbaController.class);


    private NbaServices nbaService;

    @Autowired
    public NbaController(NbaServices nbaService) {
        this.nbaService = nbaService;
    }


    @GetMapping(path = "")
    public ResponseEntity<List<DataElement>> getAllNBATeams(){

        LOGGER.info("NbaController: getAllNBATeams ");
        List<DataElement> allTeams= nbaService.getAllNBATeams();
        return new ResponseEntity<>(allTeams, HttpStatus.OK);

    }

    @GetMapping(path = "/geteam/{season}/{page}/{team_ids}/{per_page}/{date}")
    public ResponseEntity<List<DataElement>> getMathesByDate(@PathVariable String season,@PathVariable String page,@PathVariable String team_ids,@PathVariable String per_page,@PathVariable String date){

        LOGGER.info("NbaController: getTeamByDate ");
        List<DataElement> teamsByDate = nbaService.getAllNBAMatchesByDate(season,page,team_ids, per_page,date);

        return new  ResponseEntity<List<DataElement>>(teamsByDate, HttpStatus.OK);
    }


    @GetMapping(path = "/{idTeam}")
    public ResponseEntity<DataElement> getIdTeam(@PathVariable Long idTeam){

        LOGGER.info("NbaController: getIdTeam");
        DataElement getTeam = nbaService.getIdTeam(idTeam);

        return new ResponseEntity<>(getTeam,HttpStatus.OK);
    }



    @PostMapping(path = "/insertAll")
    public ResponseEntity<Long> insertBulkMatch() {

        LOGGER.info("NbaController: insertBulkMatch");
        nbaService.insertMatch();

        return new ResponseEntity<>(HttpStatus.OK);

    }

    @PostMapping(path = "/updateComments")
    public ResponseEntity<Long> updateComments(@RequestBody CommentDto deliveryDto){

        LOGGER.info("NbaController: updateComments");
        nbaService.updateComments(deliveryDto.getCommentGames(),deliveryDto.getIdTeam());
        return new ResponseEntity<>(HttpStatus.OK);

    }


    @GetMapping(path = "/getComments/{idMatch}")
    public ResponseEntity<List<String>> getAllComnents(@PathVariable Long idMatch) throws ParseException, IOException, ClassNotFoundException {

        LOGGER.info("NbaController: getAllComnents");
        List<String> commentsOrdered = nbaService.getAllComments(idMatch);

        return new ResponseEntity<>(commentsOrdered,HttpStatus.OK);
    }


}
