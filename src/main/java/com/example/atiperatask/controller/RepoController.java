package com.example.atiperatask.controller;

import com.example.atiperatask.service.GitHubService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1/repos")
@RequiredArgsConstructor
public class RepoController {
    private final GitHubService gitHubService;
    @GetMapping
    public ResponseEntity<?> get(@RequestParam String username){
        return gitHubService.getRepos(username);

    }
}