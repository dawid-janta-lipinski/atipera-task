package com.example.atiperatask.service;

import com.example.atiperatask.exceptions.UserDoesntExistException;
import com.example.atiperatask.model.FailureResponse;
import com.example.atiperatask.model.GitHubRepo;
import com.example.atiperatask.model.GitHubRepoDTO;
import com.example.atiperatask.model.RepoName;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class GitHubService {
    private final ApiClientService apiClientService;
    public ResponseEntity<?> getRepos(String username) {
        try {
            List<GitHubRepoDTO> repoNames = apiClientService.getRepos(username);
            return ResponseEntity.ok(repoNames);
        }catch (UserDoesntExistException e){
            return ResponseEntity.status(404).body(new FailureResponse(404, "This user doesn't exist"));
        }
    }
}
