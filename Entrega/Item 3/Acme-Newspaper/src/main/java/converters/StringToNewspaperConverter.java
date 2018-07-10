package converters;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import repositories.NewspaperRepository;
import domain.Newspaper;

@Component
@Transactional
public class StringToNewspaperConverter implements Converter<String, Newspaper>{
	
	@Autowired
	private NewspaperRepository newspaperRepository;
	
	@Override
	public Newspaper convert(String text) {
		Newspaper result;
		int newspaperId;
		
		try{
			if(StringUtils.isEmpty(text)){
				result = null;
			} else{
				newspaperId = Integer.valueOf(text);
				result = newspaperRepository.findOne(newspaperId);
			}
		} catch (Throwable oops) {
			throw new IllegalArgumentException(oops);
		}
		
		return result;
	}

}
