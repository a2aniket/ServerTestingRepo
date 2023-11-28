import org.junit.Test;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.GetMapping;
import static org.junit.Assert.assertEquals;

@Controller
public class HomeControllerTest {

    @Test
    public void testIndex() {
        HomeController homeController = new HomeController();
        String result = homeController.index();
        assertEquals("redirect:swagger-ui.html", result);
    }
}