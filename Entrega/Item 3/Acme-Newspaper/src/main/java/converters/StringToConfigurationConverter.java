package converters;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import repositories.ConfigurationRepository;
import domain.Configuration;

@Component
@Transactional
public class StringToConfigurationConverter implements Converter<String, Configuration>{
	
	@Autowired
	private ConfigurationRepository configurationRepository;
	
	@Override
	public Configuration convert(String text) {
		Configuration result;
		int configurationId;
		
		try{
			if(StringUtils.isEmpty(text)){
				result = null;
			} else{
				configurationId = Integer.valueOf(text);
				result = configurationRepository.findOne(configurationId);
			}
		} catch (Throwable oops) {
			throw new IllegalArgumentException(oops);
		}
		
		return result;
	}

}
