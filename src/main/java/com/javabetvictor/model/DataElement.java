package com.javabetvictor.model;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class DataElement implements Serializable {


    private Long id;
    private String date;
    private NbaTeam home_team;
    private int home_team_score;

    private int period;
    private boolean postseason;
    private int season;
    private String status;
    private String time;

    private NbaTeam visitor_team;
    private int visitor_team_score;

    private Map<String,String> comments = new HashMap<String,String>();


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public NbaTeam getHome_team() {
        return home_team;
    }

    public void setHome_team(NbaTeam home_team) {
        this.home_team = home_team;
    }

    public int getHome_team_score() {
        return home_team_score;
    }

    public void setHome_team_score(int home_team_score) {
        this.home_team_score = home_team_score;
    }

    public int getPeriod() {
        return period;
    }

    public void setPeriod(int period) {
        this.period = period;
    }

    public boolean isPostseason() {
        return postseason;
    }

    public void setPostseason(boolean postseason) {
        this.postseason = postseason;
    }

    public int getSeason() {
        return season;
    }

    public void setSeason(int season) {
        this.season = season;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public NbaTeam getVisitor_team() {
        return visitor_team;
    }

    public void setVisitor_team(NbaTeam visitor_team) {
        this.visitor_team = visitor_team;
    }

    public int getVisitor_team_score() {
        return visitor_team_score;
    }

    public void setVisitor_team_score(int visitor_team_score) {
        this.visitor_team_score = visitor_team_score;
    }

    public Map<String,String> getComments() {
        return comments;
    }


    public void setComments(Map<String,String> comments) {

          this.comments = comments;
    }

    @Override
    public String toString() {
        return "DataElement{" +
                "id=" + id +
                ", date='" + date + '\'' +
                ", home_team=" + home_team +
                ", home_team_score=" + home_team_score +
                ", period=" + period +
                ", postseason=" + postseason +
                ", season=" + season +
                ", status='" + status + '\'' +
                ", time='" + time + '\'' +
                ", visitor_team=" + visitor_team +
                ", visitor_team_score=" + visitor_team_score +
                ", comments="+comments+
                '}';
    }


}
