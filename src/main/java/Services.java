import com.squareup.okhttp.*;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import org.json.JSONException;
import org.json.JSONObject;


import java.io.IOException;
import java.io.StringWriter;
import java.io.Writer;
import java.util.HashMap;
import java.util.Map;


/**
 * Created by enny on 14.05.19.
 */
public class Services {
    public static void getIndexPage(){
        OkHttpClient client = new OkHttpClient();

        Request request = new Request.Builder()
                .url("https://storefront.templatemonsterdev.com/wordpress-themes.php?kdjskdsfjg=")
                .get()
                .build();

         Response response = null;
        try {
            response = client.newCall(request).execute();
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            System.out.println(response.body().string());


        } catch (IOException e) {
            e.printStackTrace();
        }

    }

public static void createNewCart(){
    OkHttpClient client = new OkHttpClient();

    MediaType mediaType = MediaType.parse("multipart/form-data; boundary=----WebKitFormBoundary7MA4YWxkTrZu0gW");
    RequestBody body = RequestBody.create(mediaType, "------WebKitFormBoundary7MA4YWxkTrZu0gW\r\nContent-Disposition: form-data; name=\"currency\"\r\n\r\nUSD\r\n------WebKitFormBoundary7MA4YWxkTrZu0gW--");
    Request request = new Request.Builder()
            .url("https://service-carts.templatemonsterdev.com/api/v1/carts")
            .post(body)
            .addHeader("content-type", "multipart/form-data; boundary=----WebKitFormBoundary7MA4YWxkTrZu0gW")
            .addHeader("Cache-Control", "no-cache")
            .addHeader("Postman-Token", "c495d958-2e73-4d96-a9d6-3f8a59c76afa")
            .build();

    try {
        Response response = client.newCall(request).execute();
        System.out.println(response.body().string());

    } catch (IOException e) {
        e.printStackTrace();
    }

}
public static void addProductToCart() throws IOException, JSONException {
    OkHttpClient client = new OkHttpClient();

    Request request = new Request.Builder()
            .url("https://service-carts.templatemonsterdev.com/api/v1/carts/5cd9633586b0ab02e247ceb3/products?data[license]=20")
            .method("LINK", null)
            .addHeader("link", "http://service-products.templatemonsterdev.com/api/v2/products/47925")
            .addHeader("Cache-Control", "no-cache")
            .build();

    Response response = null;
    try {
        response = client.newCall(request).execute();
    } catch (IOException e) {
        e.printStackTrace();
    }
    String body = response.body().string();
    JSONObject object = new JSONObject(body);
    System.out.println(body);
    System.out.println("amount " + object.getJSONObject("total").get("amount"));
    System.out.println("amount " + object.getJSONObject("total").get("amount"));

}
    public static void linkProductToCart(String  url, String cartid, Product product) throws IOException, JSONException {
        Map<String, Object> map = new HashMap<String, Object>();


        map.put("url", url);
        map.put("cartid", cartid);
        map.put("product", product);

        Writer writer = getWriterTemplate(map, "src/main/resources/properties/link.ftl");

        OkHttpClient client = new OkHttpClient();

        Request request = new Request.Builder()
                .url(writer.toString())
                .method("LINK", null)
                .addHeader("link", "http://service-products.templatemonsterdev.com/api/v2/products/47925")
                .build();

        Response response = client.newCall(request).execute();
        String body = response.body().string();
        System.out.println(body);

        JSONObject object = new JSONObject(body);
        System.out.println("ID " + object.get("id"));
        System.out.println("currency " + object.get("currency"));
        System.out.println("amount " + object.getJSONObject("total").get("amount"));
        System.out.println("COUNT" + object.getJSONObject("total").get("count"));
    }
    /**
     * Шаблонизированный writer
     */

    public static StringWriter getWriterTemplate(Map map, String path) {
        freemarker.template.Configuration configuration = new freemarker.template.Configuration();
        StringWriter stringWriter = new StringWriter();
        try {
            Template template = configuration.getTemplate(path);
            template.process(map, stringWriter);
            stringWriter.flush();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (TemplateException e) {
            e.printStackTrace();
        }
        return stringWriter;
    }
}
