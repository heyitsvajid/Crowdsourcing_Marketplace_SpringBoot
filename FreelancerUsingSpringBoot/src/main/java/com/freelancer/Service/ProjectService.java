package com.freelancer.Service;
import com.freelancer.Entity.Bid;
import com.freelancer.Entity.Project;
import com.freelancer.Repository.ProjectRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;


@Service
public class ProjectService{

    @Autowired
    private ProjectRepository projectRepository;
    private static final Logger logger = LoggerFactory.getLogger(BidService.class);

    public Project findById(long id) {
        Optional<Project> exist = projectRepository.findById(id);
        if(exist.isPresent()){
            return exist.get();
        }else{
            return null;
        }
    }

    public Project save(Project project) {
        Project p = projectRepository.save(project);
        return p;
    }

    public Iterable<Project> getAllProjects(){
        return projectRepository.findAll();
    }

}

