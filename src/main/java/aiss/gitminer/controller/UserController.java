package aiss.gitminer.controller;

import aiss.gitminer.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")

public class UserController {

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
}
