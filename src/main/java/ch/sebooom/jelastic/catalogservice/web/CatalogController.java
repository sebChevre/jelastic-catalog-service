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

@Slf4j
@RestController
@RequestMapping("/catalog/login")
public class CatalogController {

    @Autowired
    private Environment env;

    @PostMapping
    public ResponseEntity login(@RequestBody Login login){

        log.info("Users Service uri: {}",System.getenv("JAVA_HOME"));
        log.info("Service uri: {}",env.getProperty("$USERS_MASTER_IP"));

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

    @GetMapping
    public ResponseEntity loginTest(){

        return ResponseEntity.ok(new AuthResult(Boolean.TRUE));



    }

}
