package com.busyqa.crm.security.services;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import javax.servlet.http.HttpServletResponse;

import com.busyqa.crm.controller.FileController;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

@Component
public class FileService {


	public void storeAgreeFile(MultipartFile file, String clientId, String fileType) throws IOException {
		String path = FileService.class.getResource("/").getFile();
		Path pathed = Paths.get(path.substring(1, path.length() - 25));
		String OS = System.getProperty("os.name").toLowerCase();
		String FILE_DIRECTORY;
		if (OS.indexOf("win") >= 0){
			FILE_DIRECTORY = pathed.toString() + "\\src\\main\\resources\\agreement";

		}
		else {
			FILE_DIRECTORY = "/" + pathed.toString() + "/src/main/resources/agreement";

		}
		Path filePath = Paths.get(FILE_DIRECTORY + "/" + clientId + fileType);
		Files.copy(file.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);
	}

	public void storeResumeFile(MultipartFile file, String clientId, String fileType) throws IOException {
		String path = FileService.class.getResource("/").getFile();
		Path pathed = Paths.get(path.substring(1, path.length() - 25));
		String OS = System.getProperty("os.name").toLowerCase();
		String FILE_DIRECTORY;
		if (OS.indexOf("win") >= 0){
			FILE_DIRECTORY = pathed.toString() + "\\src\\main\\resources\\resume";
			Path filePath = Paths.get(FILE_DIRECTORY + "/" + clientId + fileType);
			Files.copy(file.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);


		}
		else {

			FILE_DIRECTORY = "/" + pathed.toString() + "/src/main/resources/resume/";
			String realPathtoUploads =  FILE_DIRECTORY;
			if(! new File(realPathtoUploads).exists())
			{
				new File(realPathtoUploads).mkdir();
			}

			String orgName = clientId + fileType;
			String filePath = realPathtoUploads + orgName;
			File dest = new File(filePath);
			file.transferTo(dest);
		}

	}

	public void storeTrainerPicFile(MultipartFile file, String clientId, String fileType) throws IOException {
		String path = FileService.class.getResource("/").getFile();
		Path pathed = Paths.get(path.substring(1, path.length() - 25));

		String OS = System.getProperty("os.name").toLowerCase();
		String FILE_DIRECTORY;
		if (OS.indexOf("win") >= 0){
			FILE_DIRECTORY = pathed.toString() + "\\src\\main\\resources\\templates\\images";

		}
		else {
			FILE_DIRECTORY = "/" + pathed.toString() + "/src/main/resources/templates/images";

		}

		Path filePath = Paths.get(FILE_DIRECTORY + "/" + clientId + fileType);
		Files.copy(file.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);
	}
	public Resource getFileSystem(String filename, HttpServletResponse response) {
		return getResource(filename, response, ResourceType.FILE_SYSTEM);
	}

	/**
	 * @param filename filename
	 * @param response Http response.
	 * @return file from classpath.
	 */
	public Resource getClassPathFile(String filename, HttpServletResponse response) {
		return getResource(filename, response, ResourceType.CLASSPATH);
	}

	private Resource getResource(String filename, HttpServletResponse response, ResourceType resourceType) {
		String path = FileService.class.getResource("/").getFile();
		Path pathed = Paths.get(path.substring(1, path.length() - 25));

		String OS = System.getProperty("os.name").toLowerCase();
		String FILE_DIRECTORY;
		if (OS.indexOf("win") >= 0){
			FILE_DIRECTORY = pathed.toString() + "\\src\\main\\resources";
		}
		else {
			FILE_DIRECTORY = "/" + pathed.toString() + "/src/main/resources";
		}


		response.setContentType("text/csv; charset=utf-8");
		response.setHeader("Content-Disposition", "attachment; filename=" + filename);
		response.setHeader("filename", filename);

		Resource resource = null;
		switch (resourceType) {
			case FILE_SYSTEM:
				resource = new FileSystemResource(FILE_DIRECTORY + filename);
				break;
			case CLASSPATH:
				resource = new ClassPathResource("data/" + filename);
				break;
		}

		return resource;
	}

	private enum ResourceType {
		FILE_SYSTEM, CLASSPATH
	}
}