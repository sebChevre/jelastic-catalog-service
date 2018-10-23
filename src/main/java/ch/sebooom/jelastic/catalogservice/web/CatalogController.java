package ch.sebooom.jelastic.catalogservice.web;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

@RestController
@RequestMapping("/catalog/login")
public class CatalogController {

    @PostMapping
    public ResponseEntity login(@RequestBody Login login){

        RestTemplate restTemplate = new RestTemplate();
        String userResource
                = "http://http://env-1024481.jcloud.ik-server.com//auth";

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
