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
@RequestMapping("/gitminer")
public class IssueController {
    @Autowired
    IssueRepository issueRepository;

    @Operation(
            summary = "Get all stored issues ",
            description = "Retrieve all the issues",
            tags = {"issues", "get"}
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "List of issues",
                    content = {@Content(schema = @Schema(implementation = Issue[].class), mediaType = "application/json")}),
            @ApiResponse(responseCode = "404", description = "Could not find all the issues",
                    content = {@Content(schema = @Schema())})
    })
    @GetMapping("/issues")
    public List<Issue> findAllIssuesFromRepo(@RequestParam(required = false) String authorId,
                                             @RequestParam(required = false) String state) {
        if (authorId == null && state == null) {
            return issueRepository.findAll();
        } else if(authorId!=null && state==null) {
            return issueRepository.findByAuthorId(authorId);
        } else if(authorId == null && state != null){
            return issueRepository.findByState(state);
        } else {
            return issueRepository.findByAuthorIdAndState(authorId, state);
        }
    }

    @Operation(
            summary = "Get an issue by Id",
            description = "Retrieve a single issue specifying its Id",
            tags = {"issues", "get"}
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Issue with the specified Id",
                    content = {@Content(schema = @Schema(implementation = Issue.class), mediaType = "application/json")}),
            @ApiResponse(responseCode = "404", description = "Could not find the issue",
                    content = {@Content(schema = @Schema())})
    })
    @GetMapping("/issues/{issueId}")
    public Issue findOne(@PathVariable(value = "issueId") Long id) throws IssueNotFoundException {
        Optional<Issue> issue = issueRepository.findById(id.toString());
        if(!issue.isPresent()){
            throw new IssueNotFoundException();
        }
        return issue.get();
    }

}
