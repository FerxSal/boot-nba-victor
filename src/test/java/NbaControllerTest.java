import com.javabetvictor.controller.NbaController;
import com.javabetvictor.model.DataElement;
import com.javabetvictor.model.MatchDto;
import com.javabetvictor.repo.MatchRepository;
import com.javabetvictor.service.Config;
import com.javabetvictor.service.NbaServices;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

@RunWith(MockitoJUnitRunner.class)
@ContextConfiguration(classes = Config.class)
@Import(Config.class)
@SpringBootTest
@TestPropertySource(locations="classpath:application-test.properties")
public class NbaControllerTest {

    @Mock
    private NbaController nbaControllerTest;

    @Mock
    private NbaServices nbaServices;

    @Mock
    RestTemplate restTemplate;

    @Mock
    MatchRepository matchRepository;

    @Mock
    private JdbcTemplate jdbcTemplate;


    @Value("${nba.endpoint}")
    private String ROOT_URL;


    @Before
    public void init() {

        restTemplate = new RestTemplate();
        nbaServices = new NbaServices(restTemplate, matchRepository);
        nbaServices.ROOT_URL = "https://free-nba.p.rapidapi.com/games";
        nbaControllerTest = new NbaController(nbaServices);
        jdbcTemplate = new JdbcTemplate();


    }


    @Test
    public void testServiceGettingAllTeams() {

        //When
        ResponseEntity<List<DataElement>> response = nbaControllerTest.getAllNBATeams();

        //Then
        assertThat(response.getStatusCode().is2xxSuccessful());
        assertTrue(!response.getBody().isEmpty());
    }


    @Test
    public void testServiceGetFullInfoNbaTeam() {

        //Given
        Long idTeam = 47179L;

        //When
        ResponseEntity<DataElement> response = nbaControllerTest.getIdTeam(idTeam);

        //Then
        assertThat(response.getStatusCode().is2xxSuccessful());
        assertEquals(response.getBody().getId(), new Long(47179L));

    }

    @Test
    public void testServiceGetTeamByDate() {
        //Seasons;Page;team_ids;per_page;date
        //Given
        String seasons = "";
        String page = "1";
        String team_ids = "";
        String per_page = "10";
        String date = "2018-02-08";

        //When
        ResponseEntity<List<DataElement>> response = nbaControllerTest.getMathesByDate(seasons, page, team_ids, per_page, date);  //("","1","","10","2018-02-08");

        //Then
        assertThat(response.getStatusCode().is2xxSuccessful());
        assertEquals(response.getBody().size(), 4);

    }



    private List<MatchDto> resultSql(SqlRowSet rs){

        List<MatchDto> matchList = new ArrayList<MatchDto>();

        while(rs.next())

        {
         MatchDto mt = new MatchDto();
         mt.setMatchId(rs.getInt("matchId"));
         mt.setDateMatch(rs.getString("dateMatch"));
         mt.setHome_team(rs.getString("home_team"));
         mt.setAway_team(rs.getString("away_team"));
         matchList.add(mt);
        }
     return matchList;
  }


}
