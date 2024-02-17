package com.example.atiperatask.service;

import com.example.atiperatask.exceptions.UserDoesntExistException;
import com.example.atiperatask.mapper.BranchMapper;
import com.example.atiperatask.model.*;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ApiClientService {
    private final WebClient webClient;
    private final BranchMapper branchMapper;


    public List<GitHubRepoDTO> getRepos(String username) throws UserDoesntExistException {

        String url = "https://api.github.com/users/" + username + "/repos";

        WebClient webClient = WebClient.create(url);

        ResponseEntity<List<GitHubRepo>> response;

        try {
            response = webClient.get().retrieve().toEntityList(GitHubRepo.class).block();
        } catch (WebClientResponseException e) {
            throw new UserDoesntExistException("This user doesn't exist.");
        }

        Objects.requireNonNull(response);

        List<GitHubRepo> repos = filterRepos(Objects.requireNonNull(response.getBody()));

        return getRepoDTOs(repos, username);

    }
    public List<GitHubRepoDTO> getRepoDTOs(List<GitHubRepo> repos, String username) {
        return repos.stream()
                .map(repo -> {
                    List<Branch> branches = getBranches(username, repo.getName());
                    List<BranchDTO> branchDTOS = branches.stream().map(branchMapper::createBranchDTOFromBranch).collect(Collectors.toList());

                    return GitHubRepoDTO.builder()
                            .repositoryName(repo.getName())
                            .ownerLogin(repo.getOwner().getLogin())
                            .branches(branchDTOS)
                            .build();
                }).collect(Collectors.toList());
    }
    public List<GitHubRepo> filterRepos(List<GitHubRepo> repos) {
        return repos.stream().filter(repo -> !repo.isFork()).toList();
    }
    public List<Branch> getBranches(String username, String repoName) {
        String branchUrl = "https://api.github.com/repos/" + username + "/" + repoName + "/branches";

        WebClient webClient = WebClient.create(branchUrl);
        ResponseEntity<List<Branch>> response = webClient.get().retrieve().toEntityList(Branch.class).block();

        Objects.requireNonNull(response);

        return response.getBody();
    }
}