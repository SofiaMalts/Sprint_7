package ru.practicum_services.qa_scooter.base;
import io.restassured.config.RedirectConfig;
import io.restassured.config.RestAssuredConfig;
import io.restassured.config.SSLConfig;
import io.restassured.response.Response;

import static io.restassured.RestAssured.given;

public class BaseMethods {
    private static final RestAssuredConfig config = RestAssuredConfig.newConfig()
            .sslConfig(new SSLConfig().relaxedHTTPSValidation())
            .redirect(new RedirectConfig().followRedirects(true));

    public static Response getRequest(String uri){
        return given()
                .config(config)
                .header("Content-type", "application/json")
                .get(uri);
    }
    public static Response postRequest(String uri, Object body){
        return given()
                .config(config)
                .header("Content-type", "application/json")
                .body(body)
                .post(uri);
    }
    public static Response postRequestWithJson(String uri, String json){
        return given()
                .config(config)
                .header("Content-type", "application/json")
                .body(json)
                .post(uri);
    }
    public static Response deleteRequest(String uri, int id){
        return given()
                .config(config)
                .header("Content-type", "application/json")
                .delete(uri+"/"+id);
    }

}
