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
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Tag(name = "Commit", description = "Commit management API")
@RestController
@RequestMapping("/gitminer")
public class CommitController {
    @Autowired
    CommitRepository commitRepository;
    @Autowired
    ProjectRepository projectRepository;


    @Operation(
            summary = "Get all commits",
            description = "Retrieve all the commits",
            tags = {"commit", "get"}
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "List of commits",
                    content = {@Content(schema = @Schema(implementation = Commit[].class), mediaType = "application/json")}),
            @ApiResponse(responseCode = "404", description = "Could not retrieve all the commits",
                    content = {@Content(schema = @Schema())})
    })
    @GetMapping("/commits")
    public List<Commit> findAllCommits(@RequestParam(required = false) String email){
        if(email==null){
            return  commitRepository.findAll();
        } else {
            return commitRepository.findByAuthorEmail(email);
        }
    }

    @Operation(
            summary = "Get a commit by Id",
            description = "Find a single commit specifying its Id",
            tags = {"commit", "get"}
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Commit with the specified Id",
                    content = {@Content(schema = @Schema(implementation = Commit.class), mediaType = "application/json")}),
            @ApiResponse(responseCode = "404", description = "Could not find the commit",
                    content = {@Content(schema = @Schema())})
    })

    @GetMapping("/commits/{commitId}")
    public Commit findOne(@PathVariable(value="commitId") String commitId) throws CommitNotFoundException {
        Optional<Commit> commit = commitRepository.findById(commitId);
        if(!commit.isPresent()){
            throw new CommitNotFoundException();
        }
        return commit.get();
    }



}
