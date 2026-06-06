package praktikum;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class BurgerTest {

    @Mock
    Bun bunMock;
    @Mock
    Ingredient ingredientMock1;
    @Mock
    Ingredient ingredientMock2;

    private Burger burger;

    @Before
    public void setUp() {
        burger = new Burger();
        when(bunMock.getName()).thenReturn("Краторная булка");
        when(bunMock.getPrice()).thenReturn(100f);
        when(ingredientMock1.getName()).thenReturn("Говяжий метеорит");
        when(ingredientMock1.getPrice()).thenReturn(300f);
        when(ingredientMock1.getType()).thenReturn(IngredientType.FILLING);
        when(ingredientMock2.getName()).thenReturn("Соус классический");
        when(ingredientMock2.getPrice()).thenReturn(50f);
        when(ingredientMock2.getType()).thenReturn(IngredientType.SAUCE);
    }

    @Test
    public void setBunsTest() {
        burger.setBuns(bunMock);
        assertEquals(bunMock, burger.bun);
    }

    @Test
    public void addIngredientTest() {
        burger.addIngredient(ingredientMock1);
        assertEquals(1, burger.ingredients.size());
        assertEquals(ingredientMock1, burger.ingredients.get(0));
    }

    @Test
    public void removeIngredientTest() {
        burger.addIngredient(ingredientMock1);
        burger.addIngredient(ingredientMock2);
        burger.removeIngredient(1);
        assertEquals(1, burger.ingredients.size());
        assertEquals(ingredientMock1, burger.ingredients.get(0));
    }

    @Test
    public void moveIngredientTest() {
        burger.addIngredient(ingredientMock1);
        burger.addIngredient(ingredientMock2);
        burger.moveIngredient(1, 0);
        assertEquals(ingredientMock2, burger.ingredients.get(0));
        assertEquals(ingredientMock1, burger.ingredients.get(1));
    }

    @Test
    public void getPriceTest() {
        burger.setBuns(bunMock);
        burger.addIngredient(ingredientMock1);
        burger.addIngredient(ingredientMock2);
        float expectedPrice = 100f * 2 + 300f + 50f;
        assertEquals(expectedPrice, burger.getPrice(), 0.01f);
    }

    @Test
    public void getReceiptTest() {
        burger.setBuns(bunMock);
        burger.addIngredient(ingredientMock1);
        burger.addIngredient(ingredientMock2);
        String receipt = burger.getReceipt();
        String expected = "(==== Краторная булка ====)\n= начинка Говяжий метеорит =\n= " +
                "соус Соус классический =\n(==== Краторная булка ====)\n\nPrice: 550,000000\n";
    }
}