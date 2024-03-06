package com.handhika.phonebook.controller;

import com.handhika.phonebook.model.ResponseInfo;
import com.handhika.phonebook.model.entity.Contact;
import com.handhika.phonebook.usecase.ContactUsecase;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RequestMapping("/contact")
@RestController
public class ContactController {

    final ContactUsecase contactUsecase;

    public ContactController(ContactUsecase contactUsecase) {
        this.contactUsecase = contactUsecase;
    }


    @GetMapping(value = "/search/{value}",produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> searchContact(@PathVariable("value") String value){
        ResponseInfo response = contactUsecase.searchData(value);
        return new ResponseEntity<>(response.getBody(),null, response.getHttpStatus());
    }

    @GetMapping(value = "/all",produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getAllContact(){
        ResponseInfo response = contactUsecase.getAll();
        return new ResponseEntity<>(response.getBody(),null, response.getHttpStatus());
    }
    @PostMapping(value = "/add", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> addNewContact(@RequestBody Contact contact){
        ResponseInfo response = contactUsecase.addContact(contact);
        return new ResponseEntity<>(response.getBody(),null, response.getHttpStatus());
    }

    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> updateContact(@PathVariable("id") long id,
                                                 @RequestBody Contact contact){
        ResponseInfo response = contactUsecase.updateContact(id,contact);
        return new ResponseEntity<>(response.getBody(),null, response.getHttpStatus());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteById(@PathVariable("id") long id) {
        ResponseInfo response = contactUsecase.deleteById(id);
        return new ResponseEntity<>(response.getBody(),null, response.getHttpStatus());
    }

    @DeleteMapping("/all")
    public ResponseEntity<?> deleteAllTutorials() {
        ResponseInfo response = contactUsecase.deleteAll();
        return new ResponseEntity<>(response.getBody(),null, response.getHttpStatus());
    }
}
