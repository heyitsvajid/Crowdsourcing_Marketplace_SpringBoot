package com.freelancer.Service;

import com.freelancer.Entity.Attachments;
import com.freelancer.Repository.AttachmentsRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AttachmentsService {

    @Autowired
    private AttachmentsRepository attachmentsRepository;
    private static final Logger logger = LoggerFactory.getLogger(AttachmentsService.class);

    public Attachments findById(long id) {
        Optional<Attachments> exist = attachmentsRepository.findById(id);
        if(exist.isPresent()){
            return exist.get();
        }else{
            return null;
        }
    }

    public Attachments save(Attachments attachments) {
    Attachments att = attachmentsRepository.save(attachments);
    return att;

    }
}

