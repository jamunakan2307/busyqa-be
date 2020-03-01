package com.busyqa.crm.controller;

import com.busyqa.crm.message.response.ApiResponse;
import com.busyqa.crm.model.User;
import com.busyqa.crm.repo.UserRepository;
import com.busyqa.crm.security.services.FileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

@RestController
@RequestMapping("/download")
public class FileController {
  private final FileService fileService;
  @Autowired UserRepository userRepository;

  @Autowired
  public FileController(FileService fileService) {
    this.fileService = fileService;
  }

  @RequestMapping(value = "/agree/{filename2}", method = RequestMethod.GET)
  public ResponseEntity<Object> downloadAgreeFile(@PathVariable String filename2)
      throws IOException {
    FileWriter filewriter = null;
    User user = userRepository.findByUsername(filename2).get();
    String path = FileController.class.getResource("/").getFile();
    Path pathed = Paths.get(path.substring(1, path.length() - 25));
    String FILE_DIRECTORY = pathed.toString() + "\\src\\main\\resources\\agreement";

    try {
      String filename3 = user.getUsername() + "_Registration_AGREE.pdf";
      String filename = FILE_DIRECTORY + "\\" + filename3;

      File file = new File(filename);

      InputStreamResource resource = new InputStreamResource(new FileInputStream(file));
      HttpHeaders headers = new HttpHeaders();
      headers.add(
          "Content-Disposition", String.format("attachment; filename=\"%s\"", file.getName()));
      headers.add("Cache-Control", "no-cache, no-store, must-revalidate");
      headers.add("Pragma", "no-cache");
      headers.add("Expires", "0");

      ResponseEntity<Object> responseEntity =
          ResponseEntity.ok()
              .headers(headers)
              .contentLength(file.length())
              .contentType(MediaType.parseMediaType("application/pdf"))
              .body(resource);
      return responseEntity;
    } catch (Exception e) {
      return new ResponseEntity<>("error occurred", HttpStatus.INTERNAL_SERVER_ERROR);
    } finally {
      if (filewriter != null) filewriter.close();
    }
  }

  @GetMapping("/agreement")
  public ResponseEntity<Object> downloadAgreeFormatFile() throws IOException {
    FileWriter filewriter = null;
    String path = FileController.class.getResource("/").getFile();
    Path pathed = Paths.get(path.substring(1, path.length() - 25));
    String FILE_DIRECTORY = pathed.toString() + "\\src\\main\\resources\\agreement";
    try {
      String filename2 = "Registration_Payment_plan_agreement.pdf";
      String filename = FILE_DIRECTORY + "\\" + filename2;

      File file = new File(filename);

      InputStreamResource resource = new InputStreamResource(new FileInputStream(file));
      HttpHeaders headers = new HttpHeaders();
      headers.add(
          "Content-Disposition", String.format("attachment; filename=\"%s\"", file.getName()));
      headers.add("Cache-Control", "no-cache, no-store, must-revalidate");
      headers.add("Pragma", "no-cache");
      headers.add("Expires", "0");

      ResponseEntity<Object> responseEntity =
          ResponseEntity.ok()
              .headers(headers)
              .contentLength(file.length())
              .contentType(MediaType.parseMediaType("application/pdf"))
              .body(resource);
      return responseEntity;
    } catch (Exception e) {
      return new ResponseEntity<>("error occurred", HttpStatus.INTERNAL_SERVER_ERROR);
    } finally {
      if (filewriter != null) filewriter.close();
    }
  }

  @GetMapping("/batachmatch")
  public ResponseEntity<Object> downloadBatchFormatFile() throws IOException {
    FileWriter filewriter = null;
    String path = FileController.class.getResource("/").getFile();
    Path pathed  = Paths.get(path.substring(1, path.length() - 24));
    String FILE_DIRECTORY = pathed.toString() + "\\src\\main\\resources\\batch";
    try {
      String filename2 = "SampleBatch.txt";
      String filename = FILE_DIRECTORY + "\\" + filename2;

      File file = new File(filename);

      InputStreamResource resource = new InputStreamResource(new FileInputStream(file));
      HttpHeaders headers = new HttpHeaders();
      headers.add(
          "Content-Disposition", String.format("attachment; filename=\"%s\"", file.getName()));
      headers.add("Cache-Control", "no-cache, no-store, must-revalidate");
      headers.add("Pragma", "no-cache");
      headers.add("Expires", "0");
      ResponseEntity<Object> responseEntity =
          ResponseEntity.ok()
              .headers(headers)
              .contentLength(file.length())
              .contentType(MediaType.parseMediaType("application/txt"))
              .body(resource);
      return responseEntity;
    } catch (Exception e) {
      return new ResponseEntity<>("error occurred", HttpStatus.INTERNAL_SERVER_ERROR);
    } finally {
      if (filewriter != null) filewriter.close();
    }
  }

  @GetMapping("/trainermatch")
  public ResponseEntity<Object> downloadTrainerFormatFile() throws IOException {
    FileWriter filewriter = null;
    String path = FileController.class.getResource("/").getFile();
    Path pathed = Paths.get(path.substring(1, path.length() - 25));
    String FILE_DIRECTORY = pathed.toString() + "\\src\\main\\resources\\trainer";
    try {
      String filename2 = "SampleTrainer.txt";
      String filename = FILE_DIRECTORY + "\\" + filename2;

      File file = new File(filename);

      InputStreamResource resource = new InputStreamResource(new FileInputStream(file));
      HttpHeaders headers = new HttpHeaders();
      headers.add(
          "Content-Disposition", String.format("attachment; filename=\"%s\"", file.getName()));
      headers.add("Cache-Control", "no-cache, no-store, must-revalidate");
      headers.add("Pragma", "no-cache");
      headers.add("Expires", "0");

      ResponseEntity<Object> responseEntity =
          ResponseEntity.ok()
              .headers(headers)
              .contentLength(file.length())
              .contentType(MediaType.parseMediaType("application/txt"))
              .body(resource);
      return responseEntity;
    } catch (Exception e) {
      return new ResponseEntity<>("error occurred", HttpStatus.INTERNAL_SERVER_ERROR);
    } finally {
      if (filewriter != null) filewriter.close();
    }
  }

  @RequestMapping(value = "/resume/{id}", method = RequestMethod.GET)
  public ResponseEntity<Object> downloadResumeFile(@PathVariable int id) throws IOException {
    FileWriter filewriter = null;
    User user = userRepository.findById(Long.valueOf(id)).get();
    String filename2;
    String path = FileController.class.getResource("/").getFile();
    Path pathed = Paths.get(path.substring(1, path.length() - 25));
    String FILE_DIRECTORY = pathed.toString() + "\\src\\main\\resources\\resume";
    try {
      filename2 = user.getUsername() + "_Resume.pdf";
      String filename = FILE_DIRECTORY + "\\" + filename2;

      File file = new File(filename);

      InputStreamResource resource = new InputStreamResource(new FileInputStream(file));
      HttpHeaders headers = new HttpHeaders();
      headers.add(
          "Content-Disposition", String.format("attachment; filename=\"%s\"", file.getName()));
      headers.add("Cache-Control", "no-cache, no-store, must-revalidate");
      headers.add("Pragma", "no-cache");
      headers.add("Expires", "0");

      ResponseEntity<Object> responseEntity =
          ResponseEntity.ok()
              .headers(headers)
              .contentLength(file.length())
              .contentType(MediaType.parseMediaType("application/pdf"))
              .body(resource);
      return responseEntity;
    } catch (Exception e) {
      return new ResponseEntity<>("error occurred", HttpStatus.INTERNAL_SERVER_ERROR);
    } finally {
      if (filewriter != null) filewriter.close();
    }
  }

  @PostMapping("/trainerPic/{id}")
  public ApiResponse<Void> handleFileUpload(
      @PathVariable String id, @RequestParam("file") MultipartFile file) throws IOException {
    try {
      User user = userRepository.findById(Long.valueOf(id)).get();
      if (file.getContentType().equals("image/png")) {
        fileService.storeTrainerPicFile(file, user.getUsername() + "_Pic", ".png");
        return new ApiResponse<>(HttpStatus.OK.value(), "Pic.. uploaded", null);
      } else {
        return new ApiResponse<>(
            HttpStatus.BAD_REQUEST.value(), "Please upload Files in PNG format  ...", null);
      }

    } catch (Exception e) {
      return new ApiResponse<>(HttpStatus.BAD_REQUEST.value(), "Error in uploading file ...", null);
    }
  }
}
