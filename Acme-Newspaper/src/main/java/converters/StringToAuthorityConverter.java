package converters;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import security.Authority;

@Component
@Transactional
public class StringToAuthorityConverter implements Converter<String, Authority> {

	@Override
	public Authority convert(String text) {
		Authority auth;
		auth=new Authority();

		try {
			if (StringUtils.isEmpty(text)) {
				auth = null;
			} else {
				auth.setAuthority(text);
			}
		} catch (Throwable oops) {
			throw new IllegalArgumentException(oops);
		}

		return auth;
	}

}
