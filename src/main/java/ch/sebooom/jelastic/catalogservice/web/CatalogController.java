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

    

    @PostMapping
    public ResponseEntity login(@RequestBody Login login){

        
        RestTemplate restTemplate = new RestTemplate();
        String userResource
                = "http://10.100.2.149:8080/auth";

        HttpEntity<Login> request = new HttpEntity<>(login);

        AuthResult response
                = restTemplate.postForObject(userResource , request, AuthResult.class);

        if(response.getLogin()){
            return ResponseEntity.ok().body(response);
        }else{
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
        }



    }

    @GetMapping
    public ResponseEntity loginTest(){

        return ResponseEntity.ok(new AuthResult(Boolean.TRUE));



    }

}
