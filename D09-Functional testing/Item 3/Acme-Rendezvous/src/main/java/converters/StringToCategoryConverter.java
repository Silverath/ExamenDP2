
package converters;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import repositories.CategoryRepository;
import domain.Category;


@Transactional
@Component
public class StringToCategoryConverter implements Converter<String, Category> {

	@Autowired
	CategoryRepository	categoryRepository;


	@Override
	public Category convert(final String text) {
		Category result;
		int id;

		try {
			if (StringUtils.isEmpty(text))
				result = null;
			else {
				id = Integer.valueOf(text);
				result = this.categoryRepository.findOne(id);
			}
		} catch (Throwable error) {
			throw new IllegalArgumentException(error);
		}

		return result;
	}

}
