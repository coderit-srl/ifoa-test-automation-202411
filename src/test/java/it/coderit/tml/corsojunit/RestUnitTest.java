package it.coderit.tml.corsojunit;

import okhttp3.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.IOException;

public class RestUnitTest {

    @Test
    public void testRestCall() throws Exception {

        OkHttpClient client = new OkHttpClient();

        Request request = new Request.Builder()
                .url("https://postman-echo.com/get")
                .get()
                .build();

        Response response = client.newCall(request).execute();
        Assertions.assertTrue(response.isSuccessful(), "Il server non ha risposto con un codice di errore OK");
        System.out.println(response.body().string());
    }


}
