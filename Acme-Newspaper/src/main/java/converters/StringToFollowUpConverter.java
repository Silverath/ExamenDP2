package converters;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import repositories.FollowUpRepository;
import domain.FollowUp;

@Component
@Transactional
public class StringToFollowUpConverter implements Converter<String, FollowUp>{
	
	@Autowired
	private FollowUpRepository followUpRepository;
	
	@Override
	public FollowUp convert(String text) {
		FollowUp result;
		int followUpId;
		
		try{
			if(StringUtils.isEmpty(text)){
				result = null;
			} else{
				followUpId = Integer.valueOf(text);
				result = followUpRepository.findOne(followUpId);
			}
		} catch (Throwable oops) {
			throw new IllegalArgumentException(oops);
		}
		
		return result;
	}

}
