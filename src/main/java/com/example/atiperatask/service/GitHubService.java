package com.example.atiperatask.service;

import com.example.atiperatask.exceptions.UserDoesntExistException;
import com.example.atiperatask.model.FailureResponse;
import com.example.atiperatask.model.GitHubRepoDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class GitHubService {
    private final ApiClientService apiClientService;
    public ResponseEntity<?> getRepos(String accept, String username) {
        if (!"application/json".equals(accept)){
            return ResponseEntity.status(406).body(new FailureResponse(406, "header 'Accept: application/json' required"));
        }
        try {
            List<GitHubRepoDTO> repoNames = apiClientService.getRepos(username);
            return ResponseEntity.ok(repoNames);
        }catch (UserDoesntExistException e){
            return ResponseEntity.status(404).body(new FailureResponse(404, "This user doesn't exist"));
        }
    }
}
