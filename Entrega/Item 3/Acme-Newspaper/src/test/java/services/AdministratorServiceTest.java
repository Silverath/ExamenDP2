
package services;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import utilities.AbstractTest;
import domain.Administrator;
import domain.Article;
import domain.Chirp;
import domain.Newspaper;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
	"classpath:spring/junit.xml"
})
@Transactional
public class AdministratorServiceTest extends AbstractTest {

	//---------------------------------------- Service under test ----------------------------------------
	// Supporting services ----------------------------------------------------

	@Autowired
	private AdministratorService	administratorService;

	@Autowired
	private ArticleService			articleService;

	@Autowired
	private NewspaperService		newspaperService;

	@Autowired
	private ChirpService			chirpService;

	@PersistenceContext
	EntityManager					entityManager;


	//Login  with admin
	@Test
	public void driveLoginAdmin() {

		final Object testingData[][] = {
			//Admin is registered
			{
				"admin", null
			},
			//Admin isn't registered
			{
				"adminNotCreated", IllegalArgumentException.class
			}
		};

		for (int i = 0; i < testingData.length; i++)
			this.templateLoginAdmin((String) testingData[i][0], (Class<?>) testingData[i][1]);

	}

	public void templateLoginAdmin(final String username, final Class<?> expected) {

		Class<?> caught;

		caught = null;

		try {
			this.authenticate(username);
			this.unauthenticate();
		} catch (final Throwable oops) {
			caught = oops.getClass();
			this.entityManager.clear();
		}

		this.checkExceptions(expected, caught);
	}

	//Test to edit all administrator attributes
	@Test
	public void driveEditNameAdministrator() {

		final Object testingData[][] = {
			//Try entering all the data of an admin with the null address and null telephone, positive case 
			{
				"admin", "admin", "adminPrueba", "surnamePrueba", null, null, "prueba@gmail.com", null
			},
			//Try entering a null phone number, this case positive
			{
				"admin", "admin", "adminPrueba", "surnamePrueba", "c/prueba", null, "prueba@gmail.com", null
			},
			//Try entering all the data of an admin, positive case
			{
				"admin", "admin", "adminPrueba", "surnamePrueba", "c/prueba", "+34657896576", "prueba@gmail.com", null
			},
			//Try entering a blank name, negative case
			{
				"admin", "admin", "", "surnamePrueba", null, null, "prueba@gmail.com", javax.validation.ConstraintViolationException.class
			},
			//Try entering a null name, negative case
			{
				"admin", "admin", null, "surnamePrueba", null, null, "prueba@gmail.com", javax.validation.ConstraintViolationException.class
			},
			//Try entering a blank surname, negative case
			{
				"admin", "admin", "adminPrueba", "", null, null, "prueba@gmail.com", javax.validation.ConstraintViolationException.class
			},
			//Try entering a null surname, negative case
			{
				"admin", "admin", "adminPrueba", null, null, null, "prueba@gmail.com", javax.validation.ConstraintViolationException.class
			}
		};

		for (int i = 0; i < testingData.length; i++)
			this.templateEditAdministrator((String) testingData[i][0], (String) testingData[i][1], (String) testingData[i][2], (String) testingData[i][3], (String) testingData[i][4], (String) testingData[i][5], (String) testingData[i][6],
				(Class<?>) testingData[i][7]);

	}

	public void templateEditAdministrator(final String entity, final String username, final String name, final String surname, final String address, final String phone, final String email, final Class<?> expected) {

		Class<?> caught;
		Administrator administrator;

		caught = null;
		administrator = this.administratorService.findOne(super.getEntityId(entity));

		try {
			super.authenticate(username);
			administrator.setName(name);
			administrator.setSurname(surname);
			administrator.setAddress(address);
			administrator.setPhone(phone);
			administrator.setEmail(email);
			this.administratorService.save(administrator);
			this.unauthenticate();
			this.administratorService.flush();
		} catch (final Throwable oops) {
			caught = oops.getClass();
			this.entityManager.clear();
		}

		this.checkExceptions(expected, caught);

	}

	//Use case 7.1. An admin removes an article that he or she thinks is inappropriate.
	@Test
	public void driveRemoveArticleAdmin() {

		final Object testingData[][] = {
			//Admin remove article, positive case
			{
				"admin", "article2", null
			},
			//User can't remove an article. Negative case
			{
				"user1", "article1", java.lang.IllegalArgumentException.class
			},
			//Customer can't remove an article. Negative case
			{
				"customer1", "article1", java.lang.IllegalArgumentException.class
			}
		};

		for (int i = 0; i < testingData.length; i++)
			this.templateRemoveArticleAdmin((String) testingData[i][0], (String) testingData[i][1], (Class<?>) testingData[i][2]);
	}

	public void templateRemoveArticleAdmin(final String username, final String articleEntity, final Class<?> expected) {

		Class<?> caught;
		Article article;

		caught = null;
		article = this.articleService.findOne(super.getEntityId(articleEntity));

		try {
			super.authenticate(username);
			this.articleService.deleteAdmin1(article);
			this.unauthenticate();
			this.articleService.flush();
		} catch (final Throwable oops) {
			caught = oops.getClass();
			this.entityManager.clear();
		}

		this.checkExceptions(expected, caught);

	}

	//Use case 7.2. An admin removes a newspaper that he or she thinks is inappropriate.
	@Test
	public void driveRemoveNewspaperAdmin() {

		final Object testingData[][] = {
			//Admin remove newspaper, positive case
			{
				"admin", "newspaper2", null
			},
			//Admin can't remove newspaper 1 because it have subscriptions. Negative case
			{
				"admin", "newspaper1", java.lang.IllegalArgumentException.class
			},
			//User can't remove a newspaper. Negative case
			{
				"user1", "newspaper3", java.lang.IllegalArgumentException.class
			},
			//Customer can't remove a newspaper. Negative case
			{
				"customer1", "newspaper3", java.lang.IllegalArgumentException.class
			}
		};

		for (int i = 0; i < testingData.length; i++)
			this.templateRemoveNewspaperAdmin((String) testingData[i][0], (String) testingData[i][1], (Class<?>) testingData[i][2]);

	}

	public void templateRemoveNewspaperAdmin(final String username, final String newspaperEntity, final Class<?> expected) {

		Class<?> caught;
		Newspaper newspaper;

		caught = null;
		newspaper = this.newspaperService.findOne(super.getEntityId(newspaperEntity));

		try {
			super.authenticate(username);
			this.newspaperService.delete(newspaper);
			this.unauthenticate();
			this.newspaperService.flush();
		} catch (final Throwable oops) {
			caught = oops.getClass();
			this.entityManager.clear();
		}

		this.checkExceptions(expected, caught);

	}

	//Use case 7.2. An admin removes a chirp that he or she thinks is inappropriate.
	@Test
	public void driveRemoveChirpAdmin() {

		final Object testingData[][] = {
			//Admin remove chirp, positive case
			{
				"admin", "chirp2", null
			},
			//					
			//User can't remove a chirp. Negative case
			{
				"user1", "chirp1", java.lang.IllegalArgumentException.class
			},
			//Customer can't remove a chirp. Negative case
			{
				"customer1", "chirp1", java.lang.IllegalArgumentException.class
			}

		};

		for (int i = 0; i < testingData.length; i++)
			this.templateRemoveChirpAdmin((String) testingData[i][0], (String) testingData[i][1], (Class<?>) testingData[i][2]);

	}

	public void templateRemoveChirpAdmin(final String username, final String chirpEntity, final Class<?> expected) {

		Class<?> caught;
		Chirp chirp;

		caught = null;
		chirp = this.chirpService.findOne(super.getEntityId(chirpEntity));

		try {
			super.authenticate(username);
			this.chirpService.deleteAdmin(chirp);
			this.unauthenticate();
			this.chirpService.flush();
		} catch (final Throwable oops) {
			caught = oops.getClass();
			this.entityManager.clear();
		}

		this.checkExceptions(expected, caught);

	}
}
