/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package cl.usach.pingeso.taa2.managedbeans;

import cl.usach.pingeso.taa2.entityclasses.Team;
import cl.usach.pingeso.taa2.sessionbeans.TeamFacadeLocal;
import cl.usach.pingeso.taa2.utils.General;
import javax.faces.application.FacesMessage;
import java.util.List;
import javax.ejb.EJB;
import javax.inject.Inject;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Nico
 */
public class TeamMBTest {
    @EJB
    private TeamFacadeLocal teamFacade;
    private List<Team> list;
    private List<Team> filteredTeams;
    @Inject 
    private TeamConversationMB mantTeam;
    public TeamMBTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }

       @Test
    public void testGetList() {
        TeamMB instance = new TeamMB();
        List<Team> expResult = null;
        List<Team> result = instance.getList();
        assertEquals(expResult, result);
    }

    @Test
    public void testSetList() {
        List<Team> newList = null;
        TeamMB instance = new TeamMB();
        instance.setList(newList);
    }

    @Test
    public void testGetFilteredTeams() {
        TeamMB instance = new TeamMB();
        List<Team> expResult = null;
        List<Team> result = instance.getFilteredTeams();
        assertEquals(expResult, result);
    }

    @Test
    public void testSetFilteredTeams() {
        List<Team> filteredTeams = null;
        TeamMB instance = new TeamMB();
        instance.setFilteredTeams(filteredTeams);
    }
/*
    @Test
    public void testViewDetailsTeam() {
        String teamId = "1";
        TeamMB instance = new TeamMB();
        instance.viewDetailsTeam(teamId);
    }

    @Test
    public void testEdit() {
        System.out.println("edit");
        String teamId = "1";
        TeamMB instance = new TeamMB();
        instance.edit(teamId);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    @Test
    public void testDelete() {
        System.out.println("delete");
        String teamId = "1";
        TeamMB instance = new TeamMB();
        instance.delete(teamId);
    }

*/
    
}
