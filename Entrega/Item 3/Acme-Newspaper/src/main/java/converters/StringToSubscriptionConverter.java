package converters;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import repositories.SubscriptionRepository;
import domain.Subscription;

@Component
@Transactional
public class StringToSubscriptionConverter implements Converter<String, Subscription>{
	
	@Autowired
	private SubscriptionRepository subscriptionRepository;
	
	@Override
	public Subscription convert(String text) {
		Subscription result;
		int subscriptionId;
		
		try{
			if(StringUtils.isEmpty(text)){
				result = null;
			} else{
				subscriptionId = Integer.valueOf(text);
				result = subscriptionRepository.findOne(subscriptionId);
			}
		} catch (Throwable oops) {
			throw new IllegalArgumentException(oops);
		}
		
		return result;
	}

}
