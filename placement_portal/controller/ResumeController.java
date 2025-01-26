//package com.placement_portal.controller;
//
//
//import com.placement_portal.service.BucketService;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.*;
//import org.springframework.web.multipart.MultipartFile;
//
//import java.io.IOException;
//import java.net.URL;
//
//@RestController
//@RequestMapping("/api/resume")
//public class ResumeController {
//    private final BucketService bucketService;
//
//    public ResumeController(BucketService bucketService) {
//        this.bucketService = bucketService;
//    }
//
//    // API to upload a resume  // /api/resume/upload
//    @PostMapping("/upload")
//    public ResponseEntity<String> uploadResume(@RequestParam("file") MultipartFile file) {
//        try {
//            String response = bucketService.uploadFile(file);
//            return ResponseEntity.status(HttpStatus.OK).body(response);
//        } catch (IOException e) {
//            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("File upload failed: " + e.getMessage());
//        }
//    }
//
//    // API to generate a download link for a resume
//    @GetMapping("/download/{filename}")
//    public ResponseEntity<String> generateDownloadLink(@PathVariable String filename) {
//        String key = "resumes/" + filename;
//        URL downloadLink = bucketService.generateDownloadLink(key);
//        return ResponseEntity.status(HttpStatus.OK).body(downloadLink.toString());
//    }
//
//    // API to delete a resume
//    @DeleteMapping("/delete/{filename}")
//    public ResponseEntity<String> deleteResume(@PathVariable String filename) {
//        String key = "resumes/" + filename;
//        String response = bucketService.deleteFile(key);
//        return ResponseEntity.status(HttpStatus.OK).body(response);
//    }
//}
