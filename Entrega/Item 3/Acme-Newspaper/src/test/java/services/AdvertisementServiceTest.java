
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
import domain.Advertisement;
import domain.CreditCard;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
	"classpath:spring/junit.xml"
})
@Transactional
public class AdvertisementServiceTest extends AbstractTest {

	@Autowired
	public NewspaperService		newspaperService;

	@Autowired
	public AdvertisementService	advertisementService;

	//---------------------------------------- Service under test ----------------------------------------
	@PersistenceContext
	public EntityManager		entityManager;


	//	//Requirement 
	//	@Test
	//	public void driverListAndSave() {
	//
	//		final Object testingData[][] = {
	//
	//			{
	//				//Se crea un advertisement correctamente
	//				"agent1", "tituloTest", "https://www.test.jpg", "https://www.testTarget.jpg", "Lidia", "VISA", "4254236698841678", 12, 2021, 666, null
	//			},
	//		};
	//		for (int i = 0; i < testingData.length; i++)
	//			this.templateListAndSave((String) testingData[i][0], ((String) testingData[i][1]), ((String) testingData[i][2]), (String) testingData[i][3], (String) testingData[i][4], (String) testingData[i][5], (String) testingData[i][6],
	//				(int) testingData[i][7], (int) testingData[i][8], (int) testingData[i][9], (Class<?>) testingData[i][10]);
	//	}

	@Test
	public void CreateTestPositive() {
		this.authenticate("agent1");
		final Advertisement a = this.advertisementService.create();
		a.setTitle("titulo test");
		a.setBanner("https://www.test.jpg");
		a.setTargetPage("https://www.targetTest.jpg");
		final CreditCard cred = new CreditCard();
		cred.setHolderName("Lidia");
		cred.setBrandName("VISA");
		cred.setNumber("4353102989904411");
		cred.setExpirationMonth(4);
		cred.setExpirationYear(2019);
		cred.setCVV(672);
		a.setCreditCard(cred);
		this.advertisementService.save(a);

	}

	@Test(expected = IllegalArgumentException.class)
	public void CreateTestNegative() {
		this.authenticate("user1");
		final Advertisement a = this.advertisementService.create();
		a.setTitle("titulo test");
		a.setBanner("https://www.test.jpg");
		a.setTargetPage("https://www.targetTest.jpg");
		final CreditCard cred = new CreditCard();
		cred.setHolderName("Lidia");
		cred.setBrandName("VISA");
		cred.setNumber("4353102989904411");
		cred.setExpirationMonth(4);
		cred.setExpirationYear(2019);
		cred.setCVV(672);
		a.setCreditCard(cred);
		this.advertisementService.save(a);

		this.advertisementService.flush();

	}

	@Test
	public void driverDelete() {
		final Object testingData[][] = {
			{
				//Se elimina el advertisement1 correctamente
				"admin", "advertisement1", null
			}, {
				//Se elimina el advertisement2 correctamente
				"admin", "advertisement2", null
			}, {
				//Se elimina el advertisement3 correctamente
				"admin", "advertisement3", null
			}, {
				//Se elimina el advertisement4 correctamente
				"admin", "advertisement4", null
			}, {
				//Se intenta eliminar con un usuario que no debería dejar
				"user1", "advertisement1", IllegalArgumentException.class
			}, {
				//Se intenta eliminar con un usuario que no debería dejar
				"agent1", "advertisement2", IllegalArgumentException.class
			}, {
				//Se intenta eliminar con un usuario que no debería dejar
				"customer1", "advertisement2", IllegalArgumentException.class
			}, {
				//Se intenta eliminar con un usuario que no debería dejar
				"", "advertisement1", IllegalArgumentException.class
			}, {
				//Se intenta eliminar con un usuario que no debería dejar
				null, "advertisement1", IllegalArgumentException.class
			},
		};
		for (int i = 0; i < testingData.length; i++)
			this.templateDelete((String) testingData[i][0], super.getEntityId((String) testingData[i][1]), (Class<?>) testingData[i][2]);
	}
	private void templateDelete(final String username, final int advertisementId, final Class<?> expected) {
		Advertisement advertisement;
		Class<?> caught;

		caught = null;
		try {
			super.authenticate(username);
			advertisement = this.advertisementService.findOne(advertisementId);
			this.advertisementService.deleteAdmin(advertisementId);

			this.advertisementService.flush();
		} catch (final Throwable oops) {
			caught = oops.getClass();
			//Se borra la cache para que no salte siempre el error del primer objeto que ha fallado en el test
			this.entityManager.clear();
		}

		this.checkExceptions(expected, caught);

		super.unauthenticate();
	}

	//	private void templateListAndSave(final String username, final String title, final String banner, final String targetPage, final String holderName, final String brandName, final String number, final int expirationMonth, final int expirationYear,
	//		final int cvv, final Class<?> expected) {
	//		Advertisement advertisement;
	//		Class<?> caught;
	//
	//		caught = null;
	//		try {
	//			super.authenticate(username);
	//			advertisement = this.advertisementService.create();
	//
	//			advertisement.setTitle(title);
	//			advertisement.setBanner(banner);
	//			advertisement.setTargetPage(targetPage);
	//
	//			final CreditCard creditCardTest = new CreditCard();
	//			creditCardTest.setHolderName(holderName);
	//			creditCardTest.setBrandName(brandName);
	//			creditCardTest.setNumber(number);
	//			creditCardTest.setCVV(cvv);
	//			creditCardTest.setExpirationMonth(expirationMonth);
	//			creditCardTest.setExpirationYear(expirationYear);
	//			advertisement.setCreditCard(creditCardTest);
	//			advertisement = this.advertisementService.save(advertisement);
	//			this.advertisementService.flush();
	//
	//		} catch (final Throwable oops) {
	//			caught = oops.getClass();
	//			//Se borra la cache para que no salte siempre el error del primer objeto que ha fallado en el test
	//			this.entityManager.clear();
	//		}
	//
	//		this.checkExceptions(expected, caught);
	//
	//		super.unauthenticate();
	//	}

}
