package com.example.atiperatask.service;

import com.example.atiperatask.exceptions.UserDoesntExistException;
import com.example.atiperatask.mapper.BranchMapper;
import com.example.atiperatask.model.*;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import reactor.core.publisher.Flux;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ApiClientService {
    private final WebClient webClient;
    private final BranchMapper branchMapper;

    public List<GitHubRepoDTO> getRepos(String username) throws UserDoesntExistException {

        String url = "https://api.github.com/users/" + username +"/repos";

        WebClient webClient = WebClient.create(url);

        ResponseEntity<List<GitHubRepo>> response;

        try {
            response = webClient.get().retrieve().toEntityList(GitHubRepo.class).block();
        } catch (WebClientResponseException e){
            throw new UserDoesntExistException("This user doesn't exist.");
        }

        Objects.requireNonNull(response);

        List<GitHubRepo> repos = Objects.requireNonNull(response.getBody());

        List<GitHubRepoDTO> repoDTOs = repos.stream()
                .map( repo -> {
                    List<Branch> branches = getBranches(username, repo.getName());
                    List<BranchDTO> branchDTOS = branches.stream().map(branchMapper::createBranchDTOFromBranch).collect(Collectors.toList());

                    return GitHubRepoDTO.builder()
                            .repositoryName(repo.getName())
                            .ownerLogin(repo.getOwner().getLogin())
                            .branches(branchDTOS)
                            .build();
        }).collect(Collectors.toList());

        return repoDTOs;
    }

    public List<Branch> getBranches(String username, String repoName) {
        String branchUrl = "https://api.github.com/repos/" + username + "/"+repoName+"/branches";

        WebClient webClient = WebClient.create(branchUrl);
        ResponseEntity<List<Branch>> response = webClient.get().retrieve().toEntityList(Branch.class).block();

        Objects.requireNonNull(response);

        return  response.getBody();

    }














//
//    public Flux<GitHubRepo> fetchRepos(String username) {
//
//        return webClient.get()
//                .uri("https://api.github.com/users/{username}/repos", username)
//                .retrieve()
//                .bodyToFlux(Map.class)
//                .map(repo -> GitHubRepo.builder()
//                            .name(repo.get("name").toString())
//                            .ownerLogin(repo.get("owner.login").toString())
//                            .branches(repo.get("branches_url").toString())
//                            .build())
//                .onErrorResume(error -> Flux.error(new RuntimeException("Error fetching repos", error)));
//
//    }
}