package praktikum;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Arrays;
import java.util.Collection;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class BurgerTest {

    @Mock
    Bun bunMock;
    @Mock
    Ingredient fillingIngredientMock;
    @Mock
    Ingredient sauceIngredientMock;

    private Burger burger;

    @Before
    public void setUp() {
        burger = new Burger();
        when(bunMock.getName()).thenReturn("Краторная булка");
        when(bunMock.getPrice()).thenReturn(100f);
        when(fillingIngredientMock.getName()).thenReturn("Говяжий метеорит");
        when(fillingIngredientMock.getPrice()).thenReturn(300f);
        when(fillingIngredientMock.getType()).thenReturn(IngredientType.FILLING);
        when(sauceIngredientMock.getName()).thenReturn("Соус классический");
        when(sauceIngredientMock.getPrice()).thenReturn(50f);
        when(sauceIngredientMock.getType()).thenReturn(IngredientType.SAUCE);
    }

    @Test
    public void setBunsTest() {
        burger.setBuns(bunMock);
        assertEquals(bunMock, burger.bun);
    }

    @Test
    public void addIngredientShouldIncreaseIngredientsSizeTest() {
        burger.addIngredient(fillingIngredientMock);
        assertEquals(1, burger.ingredients.size());
    }

    @Test
    public void addIngredientShouldAddCorrectIngredientTest() {
        burger.addIngredient(fillingIngredientMock);
        assertEquals(fillingIngredientMock, burger.ingredients.get(0));
    }

    @Test
    public void removeIngredientShouldDecreaseIngredientsSizeTest() {
        burger.addIngredient(fillingIngredientMock);
        burger.addIngredient(sauceIngredientMock);
        burger.removeIngredient(1);
        assertEquals(1, burger.ingredients.size());
    }

    @Test
    public void removeIngredientShouldRemoveCorrectIngredientTest() {
        burger.addIngredient(fillingIngredientMock);
        burger.addIngredient(sauceIngredientMock);
        burger.removeIngredient(1);
        assertEquals(fillingIngredientMock, burger.ingredients.get(0));
    }

    @Test
    public void moveIngredientShouldSwapIngredientsTest() {
        burger.addIngredient(fillingIngredientMock);
        burger.addIngredient(sauceIngredientMock);
        burger.moveIngredient(1, 0);
        assertEquals(sauceIngredientMock, burger.ingredients.get(0));
        assertEquals(fillingIngredientMock, burger.ingredients.get(1));
    }

    @Test
    public void getPriceShouldReturnCorrectSumTest() {
        burger.setBuns(bunMock);
        burger.addIngredient(fillingIngredientMock);
        burger.addIngredient(sauceIngredientMock);
        float expectedPrice = 100f * 2 + 300f + 50f;
        assertEquals(expectedPrice, burger.getPrice(), 0.01f);
    }

    @Test
    public void getReceiptShouldReturnCorrectStringTest() {
        burger.setBuns(bunMock);
        burger.addIngredient(fillingIngredientMock);
        burger.addIngredient(sauceIngredientMock);
        String receipt = burger.getReceipt();
        assertTrue(receipt.contains("Говяжий метеорит"));
        assertTrue(receipt.contains("Соус классический"));
        assertTrue(receipt.contains("Краторная булка"));
    }

    @RunWith(Parameterized.class)
    public static class MoveIngredientParameterizedTest {

        @Mock
        Ingredient firstIngredient;
        @Mock
        Ingredient secondIngredient;
        @Mock
        Ingredient thirdIngredient;

        private Burger burger;
        private int fromIndex;
        private int toIndex;

        public MoveIngredientParameterizedTest(int fromIndex, int toIndex) {
            this.fromIndex = fromIndex;
            this.toIndex = toIndex;
        }

        @Parameters
        public static Collection<Object[]> data() {
            return Arrays.asList(new Object[][]{
                    {2, 0},
                    {0, 2},
                    {1, 2}
            });
        }

        @Before
        public void setUp() {
            burger = new Burger();
            when(firstIngredient.getName()).thenReturn("Первый ингредиент");
            when(secondIngredient.getName()).thenReturn("Второй ингредиент");
            when(thirdIngredient.getName()).thenReturn("Третий ингредиент");
            burger.addIngredient(firstIngredient);
            burger.addIngredient(secondIngredient);
            burger.addIngredient(thirdIngredient);
        }

        @Test
        public void moveIngredientTest() {
            burger.moveIngredient(fromIndex, toIndex);
            assertEquals(3, burger.ingredients.size());
        }
    }
}