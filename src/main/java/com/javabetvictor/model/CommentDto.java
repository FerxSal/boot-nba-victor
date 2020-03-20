package com.javabetvictor.model;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

public class CommentDto implements Serializable {

    private Map<String,String> commentGames;
    private Long idTeam;

    public Map<String, String> getCommentGames() {
        return commentGames;
    }

    public void setCommentGames(Map<String, String> commentGames) {
        this.commentGames = commentGames;
    }

    public Long getIdTeam() {
        return idTeam;
    }

    public void setIdTeam(Long idTeam) {
        this.idTeam = idTeam;
    }
}
