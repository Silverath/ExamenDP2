
package services;

import java.util.Collection;
import java.util.HashSet;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import repositories.CategoryRepository;
import domain.Administrator;
import domain.Category;

@Service
@Transactional
public class CategoryService {

	@Autowired
	private CategoryRepository		categoryRepository;

	@Autowired
	private AdministratorService	administratorService;


	public CategoryService() {
		super();
	}

	public Category create() {
		final Administrator principal = this.administratorService.findByPrincipal();
		Assert.notNull(principal);
		final Collection<Category> childrens = new HashSet<Category>();
		final Category res = new Category();
		res.setChildrens(childrens);

		return res;
	}

	public Category save(final Category category) {
		Category res;
		Assert.notNull(category);
		final Administrator principal = this.administratorService.findByPrincipal();
		Assert.notNull(principal);

		res = this.categoryRepository.save(category);
		return res;
	}

	public void delete(final Category c) {
		final Administrator principal = this.administratorService.findByPrincipal();
		Assert.notNull(principal);
		this.categoryRepository.delete(c);
	}

	public Category findOne(final Integer categoryId) {
		final Category res = this.categoryRepository.findOne(categoryId);
		Assert.notNull(res);
		return res;
	}

	public Collection<Category> findAll() {
		final Collection<Category> res = this.categoryRepository.findAll();
		Assert.notNull(res);
		return res;
	}

	public Collection<Category> findChildrenCategoriesByCategoryId(final Integer categoryId) {
		Assert.notNull(categoryId);
		final Collection<Category> res = this.categoryRepository.findDaughterCategoriesByCategoryId(categoryId);
		Assert.notNull(res);
		return res;
	}

	public void saveCategoryOnParent(final Category c) {
		final Administrator m = this.administratorService.findByPrincipal();
		Assert.notNull(m);
		final Category res = this.categoryRepository.findOne(c.getId());
		Assert.notNull(res);
		final Category father = res.getParent();
		final Collection<Category> categories = father.getChildrens();
		categories.add(c);
		father.setChildrens(categories);
		this.categoryRepository.save(res);
	}

	public void deleteCategoryOnParent(final Category c) {
		final Administrator m = this.administratorService.findByPrincipal();
		Assert.notNull(m);
		final Category res = this.categoryRepository.findOne(c.getId());
		Assert.notNull(res);
		final Category father = res.getParent();
		final Collection<Category> categories = father.getChildrens();
		if (categories.contains(c)) {
			categories.remove(c);
			father.setChildrens(categories);
			this.categoryRepository.save(res);
		}
	}

	public void deleteCategoryOnBenefit(final Category c) {
		final Administrator m = this.administratorService.findByPrincipal();
		Assert.notNull(m);
		final Category res = this.categoryRepository.findOne(c.getId());
		Assert.notNull(res);
		final Category father = res.getParent();
		final Collection<Category> categories = father.getChildrens();
		categories.remove(c);
		father.setChildrens(categories);
		this.categoryRepository.save(res);
	}

	public Collection<Category> findByBenefitId(final int benefitId) {
		return this.categoryRepository.findByBenefitId(benefitId);
	}

	public void flush() {
		this.categoryRepository.flush();
	}

}
