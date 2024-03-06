package com.handhika.phonebook.usecase;

import com.handhika.phonebook.exception.CommonException;
import com.handhika.phonebook.model.ResponseInfo;
import com.handhika.phonebook.model.entity.Contact;
import com.handhika.phonebook.repository.ContactRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Component
public class ContactUsecase {
    final ContactRepository contactRepository;

    public ContactUsecase(ContactRepository contactRepository) {
        this.contactRepository = contactRepository;
    }

    /**
     * get Contact by Search any Param
     *
     * @param value searching value (name or phone number)
     */
    public ResponseInfo searchData(String value) {
        ResponseInfo responseInfo = new ResponseInfo();
        try {
            List<Contact> contacts = new ArrayList<>(contactRepository.searchAll(value));

            if (contacts.isEmpty()) {
                responseInfo.setNoContent();
            }else {
                responseInfo.setSuccess(contacts);
            }
        }catch (Exception e){
            responseInfo.setException(e);
        }
        return responseInfo;
    }

    /**
     * get All Contact
     */
    public ResponseInfo getAll() {
        ResponseInfo responseInfo = new ResponseInfo();
        try {
            List<Contact> contacts = new ArrayList<>(contactRepository.findAll());

            if (contacts.isEmpty()) {
                responseInfo.setNoContent();
            }else {
                responseInfo.setSuccess(contacts);
            }
        }catch (Exception e){
            responseInfo.setException(e);
        }
        return responseInfo;
    }

    /**
     * Add New Contact
     *
     * @param contact Request data want to insert
     * flow:
     * Check valid phone number
     * Save to DB
     */
    public ResponseInfo addContact(Contact contact) {
        ResponseInfo responseInfo = new ResponseInfo();
        try {
            if(!contact.getPhoneNumber().matches("(08|628|62)[\\s\\)\\-]*(\\s|(\\d){3,})")){
                throw new CommonException(HttpStatus.BAD_REQUEST,"Phone number is not valid");
            }

            Contact contactSaved = contactRepository.save(new Contact(contact));
            responseInfo.setCreated(contactSaved);
        } catch (Exception e) {
            responseInfo.setException(e);
        }
        return responseInfo;
    }

    /**
     * Update Contact
     *
     * @param id id contact
     * @param contact Request data to update
     * flow :
     * Check data existing by id
     * Replace data if exist as request
     */
    public ResponseInfo updateContact(long id, Contact contact) {
        ResponseInfo responseInfo = new ResponseInfo();
        try {
            Optional<Contact> contactData = contactRepository.findById(id);

            if (contactData.isEmpty()){
                throw new CommonException(HttpStatus.NOT_FOUND,"Data Tidak Ditemukan!");
            }

            Contact updateContact = contactData.get();
            updateContact.setFirstName(contact.getFirstName() == null ? updateContact.getFirstName() : contact.getFirstName());
            updateContact.setLastName(contact.getLastName() == null ? updateContact.getLastName() : contact.getLastName());
            updateContact.setPhoneNumber(contact.getPhoneNumber() == null ? updateContact.getPhoneNumber() : contact.getPhoneNumber());
            responseInfo.setSuccess(contactRepository.save(updateContact));

        } catch (Exception e) {
            responseInfo.setException(e);
        }
        return responseInfo;
    }

    /**
     * Delete Data by Id
     *
     * @param id id want to delete
     * flow :
     * Check data existing by id
     * Delete by Id
     */
    public ResponseInfo deleteById(long id) {
        ResponseInfo responseInfo = new ResponseInfo();
        try {
            Optional<Contact> contactData = contactRepository.findById(id);

            if (contactData.isEmpty()){
                throw new CommonException(HttpStatus.NOT_FOUND,"Data Tidak Ditemukan!");
            }

            contactRepository.deleteById(id);
            responseInfo.setNoContent();
        } catch (Exception e) {
            responseInfo.setException(e);
        }
        return responseInfo;
    }

    /**
     * Delete All Data
     */
    public ResponseInfo deleteAll() {
        ResponseInfo responseInfo = new ResponseInfo();
        try {
            contactRepository.deleteAll();
            responseInfo.setNoContent();
        } catch (Exception e) {
            responseInfo.setException(e);
        }
        return responseInfo;
    }
}
