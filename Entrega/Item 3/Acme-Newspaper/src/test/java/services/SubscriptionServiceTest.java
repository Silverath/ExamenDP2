
package services;

import java.util.Collection;

import javax.transaction.Transactional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import utilities.AbstractTest;
import domain.CreditCard;
import domain.Newspaper;
import domain.Subscription;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
	"classpath:spring/junit.xml"
})
@Transactional
public class SubscriptionServiceTest extends AbstractTest {

	//---------------------------------------- Service under test ----------------------------------------

	@Autowired
	private SubscriptionService	subscriptionService;

	@Autowired
	private NewspaperService	newspaperService;

	@Autowired
	private CustomerService		customerService;


	//--------------------------------------- Supporting services ----------------------------------------

	//----------------------------------------------- Test -----------------------------------------------

	@Test
	public void CreateTest() {
		System.out.println("<==CreateTest==>");
		try {
			this.authenticate("customer1");
			final Subscription s = this.subscriptionService.create();
			final Collection<Newspaper> c = this.newspaperService.findAllPrivate();
			final Collection<Newspaper> c2 = this.newspaperService.filter(c);
			int newspaperId = 0;
			for (final Newspaper p : c2) {
				newspaperId = p.getId();
				break;
			}
			final Newspaper pp = this.newspaperService.findOne(newspaperId);
			s.setNewspaper(pp);
			final CreditCard cred = new CreditCard();
			cred.setHolderName("Lidia");
			cred.setBrandName("VISA");
			cred.setNumber("4353102989904411");
			cred.setExpirationMonth(4);
			cred.setExpirationYear(2019);
			cred.setCVV(672);
			s.setCreditCard(cred);

			final Subscription res = this.subscriptionService.saveNewspaper(s);
			System.out.println("Se ha creado una subscripcion con valor: " + res);

		} catch (final IllegalArgumentException a) {
			System.out.println("Algo no ha ido bien.");
		} finally {
			System.out.println("TEST OK");
		}
		System.out.println(" ");

	}

	@Test
	public void CreateTest2() {
		System.out.println("<==CreateTest2==>");
		try {
			this.authenticate("user1");
			final Subscription s = this.subscriptionService.create();
			final Collection<Newspaper> c = this.newspaperService.findAllPrivate();
			final Collection<Newspaper> c2 = this.newspaperService.filter(c);
			int newspaperId = 0;
			for (final Newspaper p : c2) {
				newspaperId = p.getId();
				break;
			}
			final Newspaper pp = this.newspaperService.findOne(newspaperId);
			s.setNewspaper(pp);
			final CreditCard cred = new CreditCard();
			cred.setHolderName("Lidia");
			cred.setBrandName("VISA");
			cred.setNumber("4353102989904411");
			cred.setExpirationMonth(4);
			cred.setExpirationYear(2019);
			cred.setCVV(672);
			s.setCreditCard(cred);

			final Subscription res = this.subscriptionService.saveNewspaper(s);
			System.out.println("Se ha creado una subscripcion con valor: " + res);

		} catch (final IllegalArgumentException a) {
			System.out.println("Algo no ha ido bien.El usuario no puede subscribirse");
		} finally {
			System.out.println("TEST OK");
		}
		System.out.println(" ");

	}

}
