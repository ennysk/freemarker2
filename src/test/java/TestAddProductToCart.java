import org.json.JSONException;
import org.testng.annotations.Test;

import java.io.IOException;

/**
 * Created by enny on 14.05.19.
 */
public class TestAddProductToCart {
    @Test
    public void testCreateNewCart(){
        Services.createNewCart();
    }
    @Test
    public void testAddProductToCart(){
        try {
            Services.addProductToCart();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
    @Test
    public void testLinkProductToCart() throws IOException, JSONException {
        Product product = new Product("/products/47925");

        Services.linkProductToCart("http://service-products.templatemonsterdev.com/api/v2","5cd9633586b0ab02e247ceb3", product);


    }
}
