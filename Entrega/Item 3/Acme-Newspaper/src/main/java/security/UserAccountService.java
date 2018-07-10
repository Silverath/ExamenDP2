
package security;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import domain.Actor;

@Service
@Transactional
public class UserAccountService {

	@Autowired
	private UserAccountRepository	userAccountRepository;


	public UserAccountService() {
		super();
	}
	public UserAccount create() {
		final UserAccount u = new UserAccount();
		return u;
	}
	public UserAccount save(final UserAccount u) {
		Assert.notNull(u);
		this.userAccountRepository.save(u);
		return u;

	}
	public UserAccount findByActor(final Actor actor) {
		Assert.notNull(actor);

		UserAccount result;

		result = this.userAccountRepository.findByActorId(actor.getId());

		return result;
	}
}
