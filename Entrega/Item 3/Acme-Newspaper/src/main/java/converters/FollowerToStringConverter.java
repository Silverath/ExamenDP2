
package converters;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import domain.Follower;

@Component
@Transactional
public class FollowerToStringConverter implements Converter<Follower, String> {

	@Override
	public String convert(final Follower source) {
		String result;

		if (source == null)
			result = null;
		else
			result = String.valueOf(source.getId());

		return result;
	}
}
