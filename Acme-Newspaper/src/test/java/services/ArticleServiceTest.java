
package services;

import java.util.ArrayList;
import java.util.Collection;
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
import domain.Article;
import domain.Newspaper;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
	"classpath:spring/junit.xml"
})
@Transactional
public class ArticleServiceTest extends AbstractTest {

	@Autowired
	public NewspaperService	newspaperService;

	@Autowired
	public ArticleService	articleService;

	//---------------------------------------- Service under test ----------------------------------------
	@PersistenceContext
	public EntityManager	entityManager;


	//Requirement 6.3: Write an article and attach it to any newspaper that has not been published, yet. Note that articles may be saved in draft mode, which allows to modify them later, or final model, which freezes them forever. (parte 1)

	@Test
	public void driverListAndEdit() {

		final Long day = TimeUnit.DAYS.toMillis(1);
		final Date currentDate = new Date(System.currentTimeMillis() + day);

		final Object testingData[][] = {

			{
				//Se edita el article1 para el newspaper1 (publico y no publicado) correctamente
				"user1", "article1", "newspaper1", "title test", currentDate, "summary test", "body test", "http://www.image2.com", true, null
			}, {
				//Se edita el article3 para el newspaper1 (publico y no publicado) incorrectamente porque el user1 no es el creador del article1
				"user1", "article1", "newspaper1", "title test", null, "summary test", "body test", "http://www.image2.com", true, javax.validation.ConstraintViolationException.class
			}, {
				//Se edita el article3 para el newspaper3 (publico y no publicado) incorrectamente porque tiene el title en blank
				"user1", "article1", "newspaper1", "", currentDate, "summary test", "body test", "http://www.image2.com", true, javax.validation.ConstraintViolationException.class
			}, {
				//Se edita el article3 para el newspaper3 (publico y no publicado) incorrectamente porque tiene el summary en blank
				"user1", "article1", "newspaper1", "title test", currentDate, "", "body test", "http://www.image2.com", true, javax.validation.ConstraintViolationException.class
			}, {
				//Se edita el article3 para el newspaper3 (publico y no publicado) incorrectamente porque tiene el body en blank
				"user1", "article1", "newspaper1", "title test", currentDate, "summary test", "", "http://www.image2.com", true, javax.validation.ConstraintViolationException.class
			}, {
				//Se edita el article3 para el newspaper3 (publico y no publicado) correctamente con la lista de pictures vacia
				"user1", "article3", "newspaper3", "title test", currentDate, "summary test", "body test", "", true, null
			},
		};
		for (int i = 0; i < testingData.length; i++)
			this.templateListAndEdit((String) testingData[i][0], (Integer) super.getEntityId((String) testingData[i][1]), (Integer) super.getEntityId((String) testingData[i][2]), (String) testingData[i][3], (Date) testingData[i][4],
				(String) testingData[i][5], (String) testingData[i][6], (String) testingData[i][7], (boolean) testingData[i][8], (Class<?>) testingData[i][9]);
	}
	private void templateListAndEdit(final String username, final Integer articleId, final Integer newspaperId, final String title, final Date moment, final String summary, final String body, final String pictures, final boolean draftMode,
		final Class<?> expected) {
		Article article;
		Newspaper newspaper;
		//final Collection<Newspaper> newspapersNotPublished;
		Class<?> caught;

		caught = null;
		try {
			super.authenticate(username);
			article = this.articleService.findOne(articleId);
			newspaper = this.newspaperService.findOne(newspaperId);
			article.setNewspaper(newspaper);

			article.setTitle(title);
			article.setSummary(summary);
			article.setBody(body);
			article.setMoment(moment);
			article.setPictures(pictures);
			article.setDraftMode(draftMode);
			article = this.articleService.save(article);
			this.articleService.flush();

		} catch (final Throwable oops) {
			caught = oops.getClass();
			//Se borra la cache para que no salte siempre el error del primer objeto que ha fallado en el test
			this.entityManager.clear();
		}

		this.checkExceptions(expected, caught);

		super.unauthenticate();
	}

	//Requirement 6.3: Write an article and attach it to any newspaper that has not been published, yet. Note that articles may be saved in draft mode, which allows to modify them later, or final model, which freezes them forever. (parte 2)
	@Test
	public void driverCreateAndSave() {

		final Long day = TimeUnit.DAYS.toMillis(1);
		final Date currentDate = new Date(System.currentTimeMillis() + day);

		final Object testingData[][] = {

			{
				//Se crea un article para el newspaper3 (publico y no publicado) correctamente
				"user1", "newspaper1", "title test", currentDate, "summary test", "body test", "http://www.image2.com", false, null
			}, {
				//Se crea un article para el newspaper9 (privado y no publicado) correctamente

				"user1", "newspaper2", "title test", currentDate, "summary test", "body test", "http://www.image2.com", true, null
			}, {
				//Se crea un article para el newspaper5 (publico y publicado) incorrectamente porque ya esta publicado
				"user4", "newspaper9", "title test", currentDate, "summary test", "body test", "http://www.image2.com", true, IllegalArgumentException.class
			}, {
				//Se crea un article para el newspaper1 (privado y publicado) incorrectamente porque solo lo puede crear el user
				"customer1", "newspaper1", "title test", null, "summary test", "body test", "http://www.image2.com", true, IllegalArgumentException.class
			}
		};
		for (int i = 0; i < testingData.length; i++)
			this.templateCreateAndSave((String) testingData[i][0], (String) testingData[i][1], (String) testingData[i][2], (Date) testingData[i][3], (String) testingData[i][4], (String) testingData[i][5], (String) testingData[i][6],
				(boolean) testingData[i][7], (Class<?>) testingData[i][8]);
	}
	private void templateCreateAndSave(final String username, final String newspaperBean, final String title, final Date moment, final String summary, final String body, final String pictures, final boolean draftMode, final Class<?> expected) {
		Article article;
		Newspaper newspaper;
		Class<?> caught;

		caught = null;
		try {
			super.authenticate(username);
			newspaper = this.newspaperService.findOne(super.getEntityId(newspaperBean));
			article = this.articleService.create(newspaper.getId());

			article.setTitle(title);
			article.setSummary(summary);
			article.setMoment(moment);
			article.setBody(body);
			article.setPictures(pictures);
			article.setDraftMode(draftMode);
			article = this.articleService.save(article);
			this.articleService.flush();

		} catch (final Throwable oops) {
			caught = oops.getClass();
			//Se borra la cache para que no salte siempre el error del primer objeto que ha fallado en el test
			this.entityManager.clear();
		}

		this.checkExceptions(expected, caught);

		super.unauthenticate();
	}

	//Requirement 7.1: Remove an article that he or she thinks is inappropriate.
	@Test
	public void driverDelete() {
		final Object testingData[][] = {
			{
				//Se elimina el article2 correctamente
				"admin", "article2", null
			}, {
				//Se elimina el article3 correctamente
				"admin", "article3", null
			}, {
				//Se elimina el article4 incorrectamente porque solo lo puede eliminar el admin
				"user1", "article4", IllegalArgumentException.class
			}, {
				//Se elimina el article4 correctamente
				"admin", "article4", null
			}, {
				//Se elimina el article1 incorrectamente porque su newspaper ya esta publicado
				"admin", "article1", null
			}
		};
		for (int i = 0; i < testingData.length; i++)
			this.templateDelete((String) testingData[i][0], super.getEntityId((String) testingData[i][1]), (Class<?>) testingData[i][2]);
	}
	private void templateDelete(final String username, final int articleId, final Class<?> expected) {
		Article article;
		Class<?> caught;

		caught = null;
		try {
			super.authenticate(username);
			article = this.articleService.findOne(articleId);
			this.articleService.deleteAdmin1(article);

			this.articleService.flush();
		} catch (final Throwable oops) {
			caught = oops.getClass();
			//Se borra la cache para que no salte siempre el error del primer objeto que ha fallado en el test
			this.entityManager.clear();
		}

		this.checkExceptions(expected, caught);

		super.unauthenticate();
	}

	//	//Other methods --------------------------------------------------

	private Collection<String> addPictures() {
		Collection<String> pictures;
		pictures = new ArrayList<String>();
		pictures.add("http://www.image1.com");
		pictures.add("http://www.image2.com");
		return pictures;
	}
	private Collection<String> addPicturesWrongUrl() {
		Collection<String> picturesWrongUrl;
		picturesWrongUrl = new ArrayList<String>();
		picturesWrongUrl.add("http://www.image1.com");
		picturesWrongUrl.add("Bad picture url");
		return picturesWrongUrl;
	}
}
