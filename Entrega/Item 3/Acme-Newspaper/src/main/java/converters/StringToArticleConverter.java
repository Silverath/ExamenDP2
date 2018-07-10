package converters;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import repositories.ArticleRepository;
import domain.Article;

@Component
@Transactional
public class StringToArticleConverter implements Converter<String, Article>{
	
	@Autowired
	private ArticleRepository articleRepository;
	
	@Override
	public Article convert(String text) {
		Article result;
		int articleId;
		
		try{
			if(StringUtils.isEmpty(text)){
				result = null;
			} else{
				articleId = Integer.valueOf(text);
				result = articleRepository.findOne(articleId);
			}
		} catch (Throwable oops) {
			throw new IllegalArgumentException(oops);
		}
		
		return result;
	}

}
