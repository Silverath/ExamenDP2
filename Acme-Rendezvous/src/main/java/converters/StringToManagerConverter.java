
package converters;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import repositories.ManagerRepository;
import domain.Manager;

@Transactional
@Component
public class StringToManagerConverter implements Converter<String, Manager> {

	@Autowired
	ManagerRepository	managerRepository;


	@Override
	public Manager convert(final String text) {
		Manager result;
		int id;

		try {
			if (StringUtils.isEmpty(text))
				result = null;
			else {
				id = Integer.valueOf(text);
				result = this.managerRepository.findOne(id);
			}
		} catch (Throwable error) {
			throw new IllegalArgumentException(error);
		}

		return result;
	}

}