package guru.springframework.repositories.reactive;

import guru.springframework.domain.Recipe;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

/**
 * @author yann.courtois@ippon.fr
 * @since 12/19/2017
 */
public interface RecipeReactiveRepository extends ReactiveMongoRepository<Recipe, String> {
}
