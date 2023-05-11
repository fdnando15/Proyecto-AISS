package aiss.gitminer.controller;

import aiss.gitminer.exception.CommitNotFoundException;
import aiss.gitminer.exception.ProjectNotFoundException;
import aiss.gitminer.model.Comment;
import aiss.gitminer.model.Commit;
import aiss.gitminer.model.Project;
import aiss.gitminer.repository.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.tags.Tags;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@Tag(name = "Commit", description = "Commit management API")
@RestController
@RequestMapping("/api")
public class CommitController {

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

    @Operation(
            summary = "Get commits by project",
            description = "Retrieve all the commit from a specified project",
            tags = {"commit", "get"}
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "List of commit from the specified project",
                    content = {@Content(schema = @Schema(implementation = Comment.class), mediaType = "application/json")}),
            @ApiResponse(responseCode = "404", description = "Could not find the project from which retrieve its comments",
                    content = {@Content(schema = @Schema())})
    })
    @GetMapping("/projects/{projectId}/commits")
    public List<Commit> findAllCommitsByProjectId(@PathVariable(value="projectId") Long projectId) throws ProjectNotFoundException {
        Optional<Project> project = projectRepository.findById(projectId.toString());
        if(!project.isPresent()){
            throw new ProjectNotFoundException();
        }
        return project.get().getCommits();
    }

    @Operation(
            summary = "Get a commit by Id",
            description = "Find a single commit specifying its Id",
            tags = {"commit", "get"}
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Commit with the specified Id",
                    content = {@Content(schema = @Schema(implementation = Comment.class), mediaType = "application/json")}),
            @ApiResponse(responseCode = "404", description = "Could not find the commit",
                    content = {@Content(schema = @Schema())})
    })
    // GET http://localhost:8080/api/commits/{id}
    @GetMapping("/commits/{id}")
    public Commit findOne(@PathVariable(value="id") Long id) throws CommitNotFoundException {
        Optional<Commit> commit = commitRepository.findById(id.toString());
        if(!commit.isPresent()){
            throw new CommitNotFoundException();
        }
        return commit.get();
    }

    // TODO Habría que implementar más búsquedas de listados de commits (filtrando por user o title, por ejemplo)
}
