
package converters;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import repositories.MoletRepository;
import domain.Molet;

@Component
@Transactional
public class StringToMoletConverter implements Converter<String, Molet> {

	@Autowired
	private MoletRepository	auditRepository;


	@Override
	public Molet convert(final String text) {
		Molet result;
		int auditId;

		try {
			if (StringUtils.isEmpty(text))
				result = null;
			else {
				auditId = Integer.valueOf(text);
				result = this.auditRepository.findOne(auditId);
			}
		} catch (final Throwable oops) {
			throw new IllegalArgumentException(oops);
		}

		return result;
	}

}
