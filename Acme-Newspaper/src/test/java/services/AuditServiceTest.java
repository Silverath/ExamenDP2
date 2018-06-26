
package services;

import java.util.Date;
import java.util.concurrent.TimeUnit;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import utilities.AbstractTest;
import domain.Audit;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
	"classpath:spring/junit.xml"
})
@Transactional
public class AuditServiceTest extends AbstractTest {

	@PersistenceContext
	public EntityManager	entityManager;

	@Autowired
	public AuditService		auditService;


	@Test
	public void driverListAndEdit() {

		final Long day = TimeUnit.DAYS.toMillis(1);
		final Date currentDate = new Date(System.currentTimeMillis() + day);

		final Object testingData[][] = {

			{
				//Se crea y se edita correctamente
				"admin", currentDate, "title1", "description1", 3, null
			}, {
				//Campos title, description y gauge nulos
				"admin2", null, null, null, null, javax.validation.ConstraintViolationException.class
			},
		};
		for (int i = 0; i < testingData.length; i++)
			this.templateEdit((String) testingData[i][0], (Date) testingData[i][1], (String) testingData[i][2], (String) testingData[i][3], (Integer) testingData[i][4], (Class<?>) testingData[i][5]);
	}
	private void templateEdit(final String username, final Date moment, final String title, final String description, final Integer gauge, final Class<?> expected) {
		//final Collection<Newspaper> newspapersNotPublished;
		Class<?> caught;

		caught = null;
		try {
			super.authenticate(username);
			final Audit audit = this.auditService.create();
			audit.setTitle(title);
			audit.setDescription(description);
			audit.setMoment(moment);
			audit.setGauge(gauge);
			final Audit res = this.auditService.save(audit);
			res.setTitle("new");
			res.setFinalMode(true);
			this.auditService.onlySave(res);
			this.auditService.flush();

		} catch (final Throwable oops) {
			caught = oops.getClass();
			//Se borra la cache para que no salte siempre el error del primer objeto que ha fallado en el test
			this.entityManager.clear();
		}

		this.checkExceptions(expected, caught);

		super.unauthenticate();
	}
}
