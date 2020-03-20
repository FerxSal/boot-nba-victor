package com.javabetvictor.service;


import com.javabetvictor.model.DataElement;

import java.io.IOException;
import java.text.ParseException;
import java.util.List;
import java.util.Map;

public interface NbaService {

    List<DataElement> getAllNBAMatchesByDate(String Seasons,String page,String team_ids,String per_page,String date);


    List<DataElement> getAllNBATeams();


    DataElement AddCommnentsGame(Map<String,String> commentGames, Long idTeam);

    void insertMatch();


    List<String> getAllComments(Long idMatch) throws IOException, ParseException, ClassNotFoundException;


    void getMatchById(Long idMatch);


    void updateComments(Map<String,String> commentGames, Long idTeam);

}
