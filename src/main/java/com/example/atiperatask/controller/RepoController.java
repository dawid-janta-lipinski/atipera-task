package com.example.atiperatask.controller;

import com.example.atiperatask.service.GitHubService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/repos")
@RequiredArgsConstructor
public class RepoController {
    private final GitHubService gitHubService;
    @GetMapping
    public ResponseEntity<?> get(@RequestHeader("Accept") String accept, @RequestParam String username){

        return gitHubService.getRepos(accept, username);
    }
}