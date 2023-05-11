package aiss.gitminer.controller;

import aiss.gitminer.model.Comment;
import aiss.gitminer.model.Project;
import aiss.gitminer.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/projects")

public class ProjectController {

    @Autowired
    CommentRepository commentRepository;
    @Autowired
    CommitRepository commitRepository;
    @Autowired
    IssueRepository issueRepository;
    @Autowired
    ProjectRepository projectRepository;
    @Autowired
    UserRepository userRepository;

    // Revisar los repositorios

    @GetMapping("/commits/{id}")
    public Project findOne(@PathVariable(value="id") Long id) {
        // TODO: COMPLETE
        Optional<Project> project = projectRepository.findById(id);
        return project.get();
    }
    @GetMapping("/commits/{id}")
    public List<Project> findOne(@PathVariable(value="id") Long id) {
        // TODO: COMPLETE
        Optional<Project> project = projectRepository.findById(id);
        return project.get();
    }






}
