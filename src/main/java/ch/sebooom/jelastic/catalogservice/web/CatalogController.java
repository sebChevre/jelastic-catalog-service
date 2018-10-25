package ch.sebooom.jelastic.catalogservice.web;


import lombok.extern.log4j.Log4j;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.Properties;

@Slf4j
@RestController
@RequestMapping("/catalog/login")
public class CatalogController {

    @Autowired
    private Environment env;

    @PostMapping
    public ResponseEntity login(@RequestBody Login login){

        log.info("users.service.uri : {}", System.getProperty("users.service.uri"));

        log.info("ENV_VALUES: {}",System.getenv().values());
        log.info("ENV_KEYS: {}",System.getenv().keySet());

        log.info("KEYS:: {}",System.getProperties().keys());

        while(System.getProperties().keys().hasMoreElements()){
            String key = (String) System.getProperties().keys().nextElement();
            log.info("Key: {}",key);
            log.info("Value: {}",System.getProperty(key));

        }

        log.info("VALUES: {}",System.getProperties().values());

        log.info("JAVA_HOME: {}",System.getProperties().get("JAVA_HOME"));

        //showSystemProperties();
        log.info("Java Home: {}",System.getProperties().getProperty("JAVA_HOME"));
        log.info("Java Home: {}",System.getProperty("JAVA_HOME"));

        log.info("Java Home: {}",env.getProperty("JAVA_HOME"));




        RestTemplate restTemplate = new RestTemplate();
        String userResource
                = "http://10.100.2.164/auth";

        HttpEntity<Login> request = new HttpEntity<>(login);
        AuthResult response = null;

        try{
            response
                    = restTemplate.postForObject(userResource , request, AuthResult.class);
        }catch(Exception e){
            log.error("erreur: {}",e.getMessage());
        }

        if(response == null || !response.getLogin()){
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
        }else{
            return ResponseEntity.ok().body(response);
        }

    }

    private void showSystemProperties(){

        Properties props = System.getProperties();

        while(props.keys().hasMoreElements()){
            String prop = (String) props.keys().nextElement();

            log.info("{}:{})",prop,props.getProperty(prop));
        }

    }

    @GetMapping
    public ResponseEntity loginTest(){

        return ResponseEntity.ok(new AuthResult(Boolean.TRUE));



    }

}
