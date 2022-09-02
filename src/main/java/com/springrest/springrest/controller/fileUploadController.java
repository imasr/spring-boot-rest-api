package com.springrest.springrest.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.springrest.springrest.shared.helper.Utils;
import com.springrest.springrest.ui.model.response.Response;

@RestController
public class FileUploadController {

    @Autowired
    public Utils utils;

    @PostMapping("/upload-file")
    public ResponseEntity<Response> uploadImage(@RequestParam("file") MultipartFile file) {
        try {
            if (file.isEmpty()) {
                Response response = new Response(null, HttpStatus.BAD_REQUEST, "Request must contain file");
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
            }
            // type checking

            // if (!file.getContentType().equals("image/jpeg")) {
            // Response response = new Response(null, HttpStatus.BAD_REQUEST, "Requested
            // file must be of image type");
            // return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
            // }

            boolean f = utils.uploadFile(file);
            if (f) {
                Response response = new Response(null, HttpStatus.OK,
                        file.getOriginalFilename() + " File uploaded successfully");
                return ResponseEntity.status(HttpStatus.OK).body(response);
            }

            Response response = new Response(null, HttpStatus.INTERNAL_SERVER_ERROR,
                    " Something went wrong. Please Try Again!");

            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        } catch (Exception e) {
            e.printStackTrace();
            Response response = new Response(null, HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }

    }

}
