
package converters;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import repositories.FollowerRepository;
import domain.Follower;

@Component
@Transactional
public class StringToFollowerConverter implements Converter<String, Follower> {

	@Autowired
	private FollowerRepository	followerRepository;


	@Override
	public Follower convert(final String text) {
		Follower result;
		int id;
		try {
			if (StringUtils.isEmpty(text))
				result = null;
			else {
				id = Integer.valueOf(text);
				result = this.followerRepository.findOne(id);
			}
		} catch (final Throwable oops) {
			throw new IllegalArgumentException(oops);
		}

		return result;
	}
}
