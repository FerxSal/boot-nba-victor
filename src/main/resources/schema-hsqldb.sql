
DROP TABLE IF EXISTS MATCH;

CREATE TABLE MATCH (
  matchId INT AUTO_INCREMENT  PRIMARY KEY,
  dateMatch VARCHAR(100) NOT NULL,
  home_team VARCHAR(100) NOT NULL,
  away_team VARCHAR(100) NOT NULL,
  home_team_score INT NOT NULL,
  away_team_score  INT NOT NULL,
  comments VARCHAR(50000)
);