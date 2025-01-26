package com.placement_portal.controller;

import com.placement_portal.model.JobPost;
import com.placement_portal.payload.JobPostDTO;
import com.placement_portal.repository.JobPostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/admin/job-posts")
public class JobPostController {

    @Autowired
    private JobPostRepository jobPostRepository;

    @PostMapping
    public ResponseEntity<JobPost> createJobPost(@RequestBody JobPostDTO jobPostDTO) {
        JobPost jobPost = new JobPost();
        jobPost.setTitle(jobPostDTO.getTitle());
        jobPost.setDescription(jobPostDTO.getDescription());
        jobPost.setCompany(jobPostDTO.getCompany());
        jobPost.setEligibility(jobPostDTO.getEligibility());
        return ResponseEntity.ok(jobPostRepository.save(jobPost));
    }

    @GetMapping
    public ResponseEntity<List<JobPost>> getAllJobPosts() {
        return ResponseEntity.ok(jobPostRepository.findAll());
    }

    @PutMapping("/{id}")
    public ResponseEntity<JobPost> updateJobPost(@PathVariable Long id, @RequestBody JobPostDTO jobPostDTO) {
        JobPost jobPost = jobPostRepository.findById(id).orElseThrow(() -> new RuntimeException("JobPost not found"));
        jobPost.setTitle(jobPostDTO.getTitle());
        jobPost.setDescription(jobPostDTO.getDescription());
        jobPost.setCompany(jobPostDTO.getCompany());
        jobPost.setEligibility(jobPostDTO.getEligibility());
        return ResponseEntity.ok(jobPostRepository.save(jobPost));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteJobPost(@PathVariable Long id) {
        jobPostRepository.deleteById(id);
        return ResponseEntity.ok("Job post deleted successfully");
    }
}
