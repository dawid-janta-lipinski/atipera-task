package com.example.atiperatask.mapper;

import com.example.atiperatask.model.Branch;
import com.example.atiperatask.model.BranchDTO;
import org.springframework.stereotype.Component;

@Component
public class BranchMapper {
    public BranchDTO createBranchDTOFromBranch(Branch branch){
        return BranchDTO.builder()
                .name(branch.getName())
                .lastCommitSha(branch.getCommit().getSha())
                .build();
    }
}
