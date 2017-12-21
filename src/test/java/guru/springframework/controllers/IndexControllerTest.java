package guru.springframework.controllers;

import guru.springframework.domain.Recipe;
import guru.springframework.services.RecipeService;
import lombok.extern.slf4j.Slf4j;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.thymeleaf.ThymeleafAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.reactive.server.EntityExchangeResult;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Flux;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@WebFluxTest(IndexController.class)
@Import(ThymeleafAutoConfiguration.class)
@Slf4j
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class IndexControllerTest {

    @Autowired
    private WebTestClient webTestClient;

    @MockBean
    RecipeService recipeService;

//    @MockBean
//    Model model;

//    IndexController controller;

//    WebTestClient webTestClient;

//    @Before
//    public void setUp() throws Exception {
////        MockitoAnnotations.initMocks(this);
////        controller = new IndexController(recipeService);
////        webTestClient = WebTestClient.bindToController(controller).build();
//    }

    @Test
    public void testMockMVC() throws Exception {
//        MockMvc mockMvc = MockMvcBuilders.standaloneSetup(controller).build();

        when(recipeService.getRecipes()).thenReturn(Flux.empty());

//        mockMvc.perform(get("/"))
//                .andExpect(status().isOk())
//                .andExpect(view().name("index"));


        final EntityExchangeResult<Recipe> entityExchangeResult = webTestClient.get().uri("/")
                .exchange()
                .expectStatus().isOk()
                .expectBody(Recipe.class).returnResult();
    }

    @Test
    public void getIndexPage() throws Exception {

        //given
        Set<Recipe> recipes = new HashSet<>();
        recipes.add(new Recipe());

        Recipe recipe = new Recipe();
        recipe.setId("1");

        recipes.add(recipe);

        when(recipeService.getRecipes()).thenReturn(Flux.fromIterable(recipes));

        ArgumentCaptor<List<Recipe>> argumentCaptor = ArgumentCaptor.forClass(List.class);

        //when

        final EntityExchangeResult<Recipe> entityExchangeResult = webTestClient.get().uri("/")
                .exchange()
                .expectStatus().isOk()
                .expectBody(Recipe.class)
                .returnResult();

        //then
//        assertEquals("index", viewName);
        Mockito.verify(recipeService).getRecipes();
//        Mockito.verify(model).addAttribute(eq("recipes"), argumentCaptor.capture());
//        List<Recipe> fluxInController = argumentCaptor.getValue();
//        Assert.assertEquals(2, fluxInController.size());
    }

}