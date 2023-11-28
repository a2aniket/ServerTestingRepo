import static org.junit.Assert.assertEquals;
import org.junit.Test;

public class HomeControllerTest {

    @Test
    public void testIndex() {
        HomeController homeController = new HomeController();
        String result = homeController.index();
        assertEquals("redirect:swagger-ui.html", result);
    }
}