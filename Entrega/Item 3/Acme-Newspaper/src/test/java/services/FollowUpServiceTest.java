
package services;

import java.util.Date;

import javax.transaction.Transactional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.util.Assert;

import utilities.AbstractTest;
import domain.Article;
import domain.FollowUp;
import domain.User;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
	"classpath:spring/junit.xml"
})
@Transactional
public class FollowUpServiceTest extends AbstractTest {

	@Autowired
	public ActorService			actorService;
	@Autowired
	public UserService			userService;
	@Autowired
	public FollowUpService		followUpService;
	@Autowired
	public NewspaperService		newspaperService;
	@Autowired
	public ArticleService		articleService;
	@Autowired
	public ConfigurationService	configurationService;


	//******************************************Positive Methods*******************************************************************

	/*
	 * Information requirement 14: The writer of an article may write follow-ups on it. Follow-ups can be written only after an
	 * article is saved in final mode and the corresponding newspaper is published. For every follow-
	 * up, the system must store the following data: title, publication moment, summary, text,
	 * and optional pictures.
	 */

	@Test
	public void testCreateAfollowUp() {
		FollowUp followUp;
		FollowUp savedFollowUp;
		Article article;

		super.authenticate("user1");
		followUp = this.followUpService.create();
		article = (Article) this.articleService.findAll().toArray()[0];

		followUp = this.followUpService.save(followUp);
		savedFollowUp = this.followUpService.findOne(followUp.getId());
		Assert.notNull(savedFollowUp);

		super.unauthenticate();
	}

	//******************************************Negative Methods*******************************************************************

	/*
	 * Information requirement 14.
	 * The writer of an article may write follow-ups on it. Follow-ups can be written only after an
	 * article is saved in final mode and the corresponding newspaper is published. For every follow-up,
	 * the system must store the following data: title, publication moment, summary, text
	 * and optional pictures.
	 */

	//	@Test(expected = IllegalArgumentException.class)
	//	public void testCreateAfollowUpNegative() {
	//		FollowUp followUp;
	//		FollowUp savedFollowUp;
	//		Article article;
	//
	//		super.authenticate("user1");
	//		followUp = this.createStandarFollowUp();
	//		article = (Article) this.articleService.findAll().toArray()[6];
	//
	//		followUp = this.followUpService.save(followUp);
	//		savedFollowUp = this.followUpService.findOne(followUp.getId());
	//		Assert.notNull(savedFollowUp);
	//
	//		super.unauthenticate();
	//	}
	/*
	 * Information requirement 14.
	 * The writer of an article may write follow-ups on it. Follow-ups can be written only after an
	 * article is saved in final mode and the corresponding newspaper is published. For every follow-up,
	 * the system must store the following data: title, publication moment, summary, text
	 * and optional pictures.
	 */
	//	@Test(expected = IllegalArgumentException.class)
	//	public void testCreateAfollowUpNegative2() {
	//		FollowUp followUp;
	//		FollowUp savedFollowUp;
	//		Article article;
	//
	//		followUp = this.createStandarFollowUp();
	//
	//		article = (Article) this.articleService.findAll().toArray()[6];
	//
	//		followUp = this.followUpService.save(followUp);
	//		savedFollowUp = this.followUpService.findOne(followUp.getId());
	//		Assert.notNull(savedFollowUp);
	//
	//	}

	//******************************************Private Methods**************************

	private FollowUp createStandarFollowUp() {
		super.authenticate("User1");
		FollowUp followUp;
		final User creator = (User) this.actorService.findByPrincipal();

		followUp = this.followUpService.create();
		followUp.setBody("That is a example of a simple text of a followUp");
		followUp.setTitle("FollowUp 1");
		followUp.setSummary("Summary");
		followUp.setMoment(new Date());
		//followUp.setUser(creator);

		return followUp;

	}
}
