package com.javabetvictor.repo;


import javax.persistence.Id;

import javax.persistence.*;


@Entity
@Table(name = "MATCH")
@Access(value= AccessType.FIELD)
public class Match {

    @Id
    @GeneratedValue
    @Column(name = "matchId", unique = true, nullable = false)
    private Long matchId;

    private String dateMatch;

    private String home_team;

    private String away_team;

    private int home_team_score;

    private int away_team_score;

    private String  comments;

    public Long getMatchId() {
        return matchId;
    }

    public void setMatchId(Long matchId) {
        this.matchId = matchId;
    }

    public String getDateMatch() {
        return dateMatch;
    }

    public void setDateMatch(String dateMatch) {
        this.dateMatch = dateMatch;
    }

    public String getHome_team() {
        return home_team;
    }

    public void setHome_team(String home_team) {
        this.home_team = home_team;
    }

    public String getAway_team() {
        return away_team;
    }

    public void setAway_team(String away_team) {
        this.away_team = away_team;
    }

    public int getHome_team_score() {
        return home_team_score;
    }

    public void setHome_team_score(int home_team_score) {
        this.home_team_score = home_team_score;
    }

    public int getAway_team_score() {
        return away_team_score;
    }

    public void setAway_team_score(int away_team_score) {
        this.away_team_score = away_team_score;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    @Override
    public String toString() {
        return "Match{" +
                "matchId=" + matchId +
                ", dateMatch='" + dateMatch + '\'' +
                ", home_team='" + home_team + '\'' +
                ", away_team='" + away_team + '\'' +
                ", home_team_score=" + home_team_score +
                ", away_team_score=" + away_team_score +
                ", comments='" + comments + '\'' +
                '}';
    }
}
