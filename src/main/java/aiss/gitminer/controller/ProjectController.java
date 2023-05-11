package aiss.gitminer.controller;

import aiss.gitminer.exception.UserNotFoundException;
import aiss.gitminer.model.Project;
import aiss.gitminer.repository.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import net.bytebuddy.build.Plugin;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.persistence.OneToOne;
import javax.persistence.OrderBy;
import javax.persistence.Table;
import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@Tag(name = "Project", description = "Project management API")
@RestController
@RequestMapping("/api")

public class ProjectController {
    @Autowired
    ProjectRepository projectRepository;

    @Operation(
            summary ="Retrieve a single project",
            description = "Find a specific project by specifying its Id",
            tags ={ "projects", "get" }
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Project with the specified Id",
                    content = {@Content(schema = @Schema(implementation = Project.class), mediaType = "application/json")}),
            @ApiResponse(responseCode = "404", description = "Project not found", content = {@Content(schema = @Schema())})
    })
    @GetMapping("/projects/{id}")
    public Project findOne(@PathVariable(value="id") Long id) throws UserNotFoundException {
        // TODO: COMPLETE
        String projectId = id.toString();
        Optional<Project> project = projectRepository.findById(projectId);
        if(!project.isPresent()){
            throw new UserNotFoundException();
        }
        return project.get();
    }

    @Operation(
            summary ="find all projects",
            description = "find all the projects in the database",
            tags = {"projects", "get"}
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "List of projects",
                    content = {@Content(schema = @Schema(implementation = Project.class), mediaType = "application/json")}),
            @ApiResponse(responseCode = "404", description = "Projects not found")
    })
    @GetMapping("/projects")
    public List<Project> findAllProjects(){
        List<Project> projects = projectRepository.findAll();
        return projects;
    }

    @Operation(
            summary = "Create a project",
            description = "Creates in the database the project specified in the request body",
            tags = {"projects", "post"}
    )
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "project created",
                    content = {@Content(schema = @Schema(implementation = Project.class), mediaType = "application/json")}),
            @ApiResponse(responseCode = "400", description = "Couldn't create the specified project", content = {@Content(schema = @Schema())})
    })
    @PostMapping("projects")
    @ResponseStatus(HttpStatus.CREATED)
    public Project createProject(@RequestBody @Valid Project project){
        Project _project = projectRepository.save(new Project(project.getName(), project.getWebUrl()));
        return _project;
    }


}
