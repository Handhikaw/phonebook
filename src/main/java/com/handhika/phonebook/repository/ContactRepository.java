package com.handhika.phonebook.repository;

import com.handhika.phonebook.model.entity.Contact;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ContactRepository extends JpaRepository<Contact,Long> {

    @Query(value = "SELECT c FROM Contact c WHERE upper(c.firstName) LIKE upper(%:value%) OR upper(c.lastName) LIKE upper(%:value%) OR upper(c.phoneNumber) LIKE upper(%:value%)")
    List<Contact> searchAll(@Param("value")String value);


}
