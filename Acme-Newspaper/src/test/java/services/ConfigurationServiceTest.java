
package services;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.util.Assert;

import utilities.AbstractTest;
import domain.Configuration;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
	"classpath:spring/junit.xml"
})
@Transactional
public class ConfigurationServiceTest extends AbstractTest {

	//---------------------------------------- Service under test ----------------------------------------

	@Autowired
	private ConfigurationService	configurationService;

	@PersistenceContext
	EntityManager					entityManager;


	//B17.1 A admin manage a list of taboo words
	@Test
	public void driveListConfiguration() {

		final Object testingData[][] = {
			//admin trys introducing a taboo word, positive case
			{
				"admin", 4, null
			}

		};

		for (int i = 0; i < testingData.length; i++)
			this.templateListConfiguration((String) testingData[i][0], (Integer) testingData[i][1], (Class<?>) testingData[i][2]);

	}

	public void templateListConfiguration(final String username, final Integer numWords, final Class<?> expected) {

		Class<?> caught;
		Configuration configuration;

		caught = null;
		configuration = this.configurationService.findOne();

		try {
			this.authenticate(username);
			Assert.isTrue(configuration.getTaboo().size() == numWords);
			this.unauthenticate();
		} catch (final Throwable oops) {
			caught = oops.getClass();
			//Se borra la cache para que no salte siempre el error del primer objeto que ha fallado en el test
			this.entityManager.clear();
		}

		this.checkExceptions(expected, caught);
	}

	//B17.1 A admin manage a list of taboo words
	@Test
	public void driveEditConfiguration() {

		final Object testingData[][] = {
			//admin trys introducing a taboo word, positive case
			{
				"admin", "configuration", "palabraPrueba", null
			},

		};

		for (int i = 0; i < testingData.length; i++)
			this.templateEditConfiguration((String) testingData[i][0], (String) testingData[i][1], (String) testingData[i][2], (Class<?>) testingData[i][3]);

	}

	public void templateEditConfiguration(final String username, final String entity, final String taboo, final Class<?> expected) {

		Class<?> caught;
		Configuration configuration;

		caught = null;
		configuration = this.configurationService.findOne();

		try {
			this.authenticate(username);
			configuration.getTaboo().add(taboo);
			this.configurationService.save(configuration);
			this.unauthenticate();
		} catch (final Throwable oops) {
			caught = oops.getClass();
			//Se borra la cache para que no salte siempre el error del primer objeto que ha fallado en el test
			this.entityManager.clear();
		}

		this.checkExceptions(expected, caught);
	}
}
