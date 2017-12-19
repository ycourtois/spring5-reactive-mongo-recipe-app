package guru.springframework.services;

import guru.springframework.commands.RecipeCommand;
import guru.springframework.converters.RecipeCommandToRecipe;
import guru.springframework.converters.RecipeToRecipeCommand;
import guru.springframework.domain.Recipe;
import guru.springframework.exceptions.NotFoundException;
import guru.springframework.repositories.reactive.RecipeReactiveRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Slf4j
@Service
@RequiredArgsConstructor
public class RecipeServiceImpl implements RecipeService {

    private final RecipeReactiveRepository recipeRepository;
    private final RecipeCommandToRecipe recipeCommandToRecipe;
    private final RecipeToRecipeCommand recipeToRecipeCommand;

    @Override
    public Flux<Recipe> getRecipes() {
        log.debug("I'm in the service");
        return recipeRepository.findAll();
    }

    @Override
    public Mono<Recipe> findById(String id) {

        Mono<Recipe> recipeMono = recipeRepository.findById(id);

        if (!recipeMono.blockOptional().isPresent()) {
            throw new NotFoundException("Recipe Not Found. For ID value: " + id );
        }

        return recipeMono;
    }

    @Override
    public Mono<RecipeCommand> findCommandById(String id) {

        return recipeRepository.findById(id)
                .map(recipe -> {
                    RecipeCommand recipeCommand = recipeToRecipeCommand.convert(recipe);
                    recipeCommand.getIngredients().forEach(rc -> rc.setRecipeId(recipeCommand.getId()));
                    return recipeCommand;
                });

//        RecipeCommand recipeCommand = recipeToRecipeCommand.convert(findById(id).block());
//
//        //enhance command object with id value
//        if(recipeCommand.getIngredients() != null && recipeCommand.getIngredients().size() > 0){
//            recipeCommand.getIngredients().forEach(rc -> {
//                rc.setRecipeId(recipeCommand.getId());
//            });
//        }
//
//        return Mono.just(recipeCommand);
    }

    @Override
    public Mono<RecipeCommand> saveRecipeCommand(RecipeCommand command) {
//        Recipe detachedRecipe = recipeCommandToRecipe.convert(command);

//        Recipe savedRecipe = recipeRepository.save(detachedRecipe).block();
//        log.debug("Saved RecipeId:" + savedRecipe.getId());
//        return Mono.just(recipeToRecipeCommand.convert(savedRecipe));

        return recipeRepository.save(recipeCommandToRecipe.convert(command))
                .map(recipeToRecipeCommand::convert);
    }

    @Override
    public Mono<Void> deleteById(String idToDelete) {
       return recipeRepository.deleteById(idToDelete);
    }
}
