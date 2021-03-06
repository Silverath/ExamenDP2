
package converters;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import repositories.AuditRepository;
import domain.Audit;

@Component
@Transactional
public class StringToAuditConverter implements Converter<String, Audit> {

	@Autowired
	private AuditRepository	auditRepository;


	@Override
	public Audit convert(final String text) {
		Audit result;
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
