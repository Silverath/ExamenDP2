
package services;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import utilities.AbstractTest;
import domain.Agent;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
	"classpath:spring/junit.xml"
})
@Transactional
public class AgentServiceTest extends AbstractTest {

	@Autowired
	private ActorService	actorService;

	@Autowired
	private AgentService	agentService;


	//******************************************Positive Methods*******************************************************************

	/**
	 * 3.1 An actor who is not authenticated must be able to: Register to the system as an agent.
	 */
	@Test
	public void testRegisterAgent() {
		Agent agent;

		agent = this.agentService.create();

		agent.setName("Eduardo");
		agent.setSurname("Luna Zayas");
		agent.setEmail("edubsteep@gmail.com");
		agent.setPhone("646665954");
		agent.setAddress("Calle Reina mercedes 21");

		this.agentService.save(agent);

	}

	/**
	 * This test checks that an agent can edit his profile correctly
	 * 
	 */
	@Test
	public void testEdit() {
		super.authenticate("agent1");
		Agent agent;

		agent = (Agent) this.actorService.findByPrincipal();
		agent.setPhone("658555444");
		agent.setEmail("editagent@gmail.com");
		agent.setAddress("Maria de la O 23");

		this.agentService.save(agent);

	}

	//******************************************Negative Methods*******************************************************************
	/**
	 * 3.1 An actor who is not authenticated must be able to: Register to the system as an agent.
	 * 
	 */
	@Test(expected = javax.validation.ConstraintViolationException.class)
	public void testRegisterAgentInvalidName() {
		Agent agent;

		agent = this.agentService.create();

		agent.setName("");
		agent.setSurname("Luna Zayas");
		agent.setEmail("edubsteep@gmail.com");
		agent.setPhone("646665954");
		agent.setAddress("Calle Reina mercedes 21");

		this.agentService.save(agent);
		this.agentService.flush();

	}

	/**
	 * 4.1 An actor who is not authenticated must be able to: Register to the system as a agent.
	 */
	@Test(expected = javax.validation.ConstraintViolationException.class)
	public void testRegisterAgentInvalidSurname() {
		Agent agent;

		agent = this.agentService.create();

		agent.setName("Eduardo");
		agent.setSurname("");
		agent.setEmail("edubsteep@gmail.com");
		agent.setPhone("646665954");
		agent.setAddress("Calle Reina mercedes 21");

		this.agentService.save(agent);
		this.agentService.flush();

	}
	/**
	 * 4.1 An actor who is not authenticated must be able to: Register to the system as a agent.
	 * 
	 * This test checks that a not registered agent cannot register himself in the system,without a valid email
	 * 
	 */
	@Test(expected = javax.validation.ConstraintViolationException.class)
	public void testRegisterAgentInvalidEmail() {
		Agent agent;

		agent = this.agentService.create();

		agent.setName("Eduardo");
		agent.setSurname("Luna Zayas");
		agent.setEmail("edubsteep");
		agent.setPhone("646665954");
		agent.setAddress("Calle Reina mercedes 21");

		this.agentService.save(agent);
		this.agentService.flush();

	}

	/**
	 * 
	 * This test checks that an agent cannot edit the profile of other agent
	 * 
	 */
	@Test(expected = IllegalArgumentException.class)
	public void testEditProfileOfOtherUser() {
		super.authenticate("agent1");

		Agent agent;
		Integer userId;

		userId = super.getEntityId("Agent 2");
		agent = this.agentService.findOne(userId);

		agent.setPhone("654789123");
		agent.setEmail("editemail@gmail.com");
		agent.setAddress("Calle Mendoza 5");

		this.agentService.save(agent);

		super.unauthenticate();

	}

	/**
	 * 
	 * 
	 * This test checks that unauthenticated agents cannot edit the profile of other agent
	 * 
	 */
	@Test(expected = IllegalArgumentException.class)
	public void testNoLoggedUserEditProfileOfOtherUser() {
		super.authenticate(null);

		Agent agent;
		Integer userId;

		userId = super.getEntityId("Agent 2");
		agent = this.agentService.findOne(userId);

		agent.setPhone("654789123");
		agent.setEmail("editemail@gmail.com");
		agent.setAddress("Calle Mendoza 5");

		this.agentService.save(agent);

		super.unauthenticate();

	}

}
