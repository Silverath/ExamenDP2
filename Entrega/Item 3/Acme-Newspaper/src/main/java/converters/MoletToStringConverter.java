
package converters;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import domain.Molet;

@Component
@Transactional
public class MoletToStringConverter implements Converter<Molet, String> {

	@Override
	public String convert(final Molet audit) {
		String result;

		if (audit == null)
			result = null;
		else
			result = String.valueOf(audit.getId());
		return result;
	}

}
