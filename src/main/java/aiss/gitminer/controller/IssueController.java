package aiss.gitminer.controller;

import aiss.gitminer.exception.IssueNotFoundException;
import aiss.gitminer.exception.ProjectNotFoundException;
import aiss.gitminer.model.Comment;
import aiss.gitminer.model.Issue;
import aiss.gitminer.model.Project;
import aiss.gitminer.repository.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@Tag(name = "Issue", description = "Issue management API")
@RestController
@RequestMapping("/api")
public class IssueController {
    @Autowired
    IssueRepository issueRepository;
    @Autowired
    ProjectRepository projectRepository;

    @Operation(
            summary = "Get issues by project",
            description = "Retrieve all the issues from a specified project",
            tags = {"issues", "get"}
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "List of issues from the specified project",
                    content = {@Content(schema = @Schema(implementation = Comment.class), mediaType = "application/json")}),
            @ApiResponse(responseCode = "404", description = "Could not find the project from which retrieve its issues",
                    content = {@Content(schema = @Schema())})
    })
    @GetMapping("/projects/{projectId}/issues")
    public List<Issue> findAllIssuesFromRepo(@PathVariable(value = "project id") Long projectId) throws ProjectNotFoundException {
        Optional<Project> project = projectRepository.findById(projectId.toString());
        if(project.isPresent()){
            throw new ProjectNotFoundException();
        }
        return project.get().getIssues();
    }

    @Operation(
            summary = "Get an issue by Id",
            description = "Retrieve a single issue specifying its Id",
            tags = {"issues", "get"}
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Issue with the specified Id",
                    content = {@Content(schema = @Schema(implementation = Comment.class), mediaType = "application/json")}),
            @ApiResponse(responseCode = "404", description = "Could not find the issue",
                    content = {@Content(schema = @Schema())})
    })
    @GetMapping("/issues/{issueId}")
    public Issue findOne(@PathVariable(value = "id") Long id) throws IssueNotFoundException {
        Optional<Issue> issue = issueRepository.findById(id.toString());
        if(!issue.isPresent()){
            throw new IssueNotFoundException();
        }
        return issue.get();
    }

    @Operation(
            summary = "Get issues by state",
            description = "Retrieve all the issues with a specific state",
            tags = {"issues", "get", "state"}
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "List of all the issues with the specified Id",
                    content = {@Content(schema = @Schema(implementation = Comment.class), mediaType = "application/json")}),
            @ApiResponse(responseCode = "404", description = "Could not find the issues", content = {@Content(schema = @Schema())})
    })
    @GetMapping("/issues")
    public List<Issue> findByState(@RequestParam(required = true) String state){
        List<Issue> issues = issueRepository.findByState(state);
        return issues;
    }

}
