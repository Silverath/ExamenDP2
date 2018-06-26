
package services;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.ConfigurationRepository;
import domain.Configuration;

@Service
@Transactional
public class ConfigurationService {

	@Autowired
	private ConfigurationRepository	configurationRepository;


	public ConfigurationService() {
		super();
	}

	public Collection<Configuration> findAll() {
		Collection<Configuration> result;

		result = this.configurationRepository.findAll();
		Assert.notNull(result);

		return result;
	}

	public Configuration findOne() {
		Configuration res;

		res = this.findAll().iterator().next();
		Assert.notNull(res);

		return res;

	}

	public Configuration save(final Configuration configuration) {
		Assert.notNull(configuration);

		Configuration result;

		result = this.configurationRepository.save(configuration);

		return result;
	}

	public void flush() {
		this.configurationRepository.flush();
	}

}
