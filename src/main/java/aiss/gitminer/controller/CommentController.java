package aiss.gitminer.controller;

import aiss.gitminer.exception.CommentNotFoundException;
import aiss.gitminer.exception.IssueNotFoundException;
import aiss.gitminer.exception.UserNotFoundException;
import aiss.gitminer.model.Comment;
import aiss.gitminer.model.Issue;
import aiss.gitminer.model.User;
import aiss.gitminer.repository.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.tags.Tags;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Tag(name= "Comment", description = "Comment management API")
@RestController
@RequestMapping("/api")
public class CommentController {
    @Autowired
    CommentRepository commentRepository;
    @Autowired
    IssueRepository issueRepository;
    @Autowired
    UserRepository userRepository;

    @Operation(
            summary = "Get comments from an issue",
            description = "Retrieve all the comments from an issue specified by its Id",
            tags = {"comment", "get"}
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "List of comments from the specified issue",
                    content = {@Content(schema = @Schema(implementation = Comment.class), mediaType = "application/json")}),
            @ApiResponse(responseCode = "404", description = "Could not find the issue from which retrieve its comments",
                    content = {@Content(schema = @Schema())})
    })
    @GetMapping("/issue/{issueId}/comments")
    public List<Comment> findAllCommentsFromIssue(@PathVariable(value = "issueId") Long issueId) throws IssueNotFoundException {
        Optional<Issue> issue = issueRepository.findById(issueId.toString());
        if(!issue.isPresent()){
            throw new IssueNotFoundException();
        }
        return issue.get().getComments();
    }

    @Operation(
            summary = "Get a single comments",
            description = "Retrieve a single comment specifying its Id",
            tags = {"comment", "get"}
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Comment with the specified Id",
                    content = {@Content(schema = @Schema(implementation = Comment.class), mediaType = "application/json")}),
            @ApiResponse(responseCode = "404", description = "Could not find the comment with the specified Id",
                    content = {@Content(schema = @Schema())})
    })
    @GetMapping("/comments/{commentId}")
    public Comment findOne(@PathVariable(value = "commentId") Long commentId) throws CommentNotFoundException {
        Optional<Comment> comment = commentRepository.findById(commentId.toString());
        if(!comment.isPresent()){
            throw new CommentNotFoundException();
        }
        return comment.get();
    }

        @Operation(
                summary = "Get comments by author",
                description = "Retrieve all the comments from a specified User",
                tags = {"comment", "get"}
        )
        @ApiResponses({
                @ApiResponse(responseCode = "200", description = "List of comments from the specified user",
                        content = {@Content(schema = @Schema(implementation = Comment.class), mediaType = "application/json")}),
                @ApiResponse(responseCode = "404", description = "Could not find the User from which retrieve its comments",
                        content = {@Content(schema = @Schema())})
        })
    @GetMapping("/user/{userId}/comments")
    public List<Comment> findByAuthor(@PathVariable(value = "userId") Long id) throws UserNotFoundException {
        // TODO: revisar este método (mirar si el .findByAuthor devuelve una lista)
        Optional<User> author = userRepository.findById(id.toString());
        if(!author.isPresent()){
            throw new UserNotFoundException();
        }
        List<Comment> comment = commentRepository.findByAuthor(author.get());
        return comment;
    }
}
