package com.busyqa.crm.controller.version_2_0;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Month;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.EnumSet;
import java.util.List;
import javax.validation.Valid;

import com.busyqa.crm.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.busyqa.crm.message.response.ApiResponse;
import com.busyqa.crm.message.response.ResponseMessage;
import com.busyqa.crm.repo.UserRepository;
import com.busyqa.crm.repo.verion_2_0.BusyqaBatchRepo;
import com.busyqa.crm.repo.verion_2_0.BusyqaCourseRepo;
import com.fasterxml.jackson.databind.ObjectMapper;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/admin/batch")
public class BusyqaBatchController {

	@Autowired
	BusyqaBatchRepo busyqaBatchRepo;

	@Autowired
	UserRepository userRepository;

	@Autowired
	BusyqaCourseRepo busyqaCourseRepo;

	EnumSet<Month> spring = EnumSet.of( Month.MARCH , Month.APRIL );
	EnumSet<Month> summer = EnumSet.of( Month.MAY , Month.JUNE , Month.JULY , Month.AUGUST );
	EnumSet<Month> fall = EnumSet.of( Month.SEPTEMBER , Month.OCTOBER );
	EnumSet<Month> winter = EnumSet.of( Month.NOVEMBER , Month.DECEMBER , Month.JANUARY , Month.FEBRUARY );
	DateTimeFormatter localTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

	private static void writeUsingOutputStream(String data, String filePathName) {
		OutputStream os = null;
		try {
			os = new FileOutputStream(new File(filePathName));
			os.write(data.getBytes(), 0, data.length());
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				os.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	@GetMapping("/getAll")
	public ApiResponse<List<BusyqaBatchDto>> getAll() {
		List<BusyqaBatch> busyqaBatches = busyqaBatchRepo.findAll();
		List<BusyqaBatchDto> reterivedBusyqaBatches = new ArrayList<>();

		for (int i = 0; i < busyqaBatches.size(); i++) {
			BusyqaBatchDto busyqaBatchDto = new BusyqaBatchDto();
			busyqaBatchDto.setId(busyqaBatches.get(i).getId());
			busyqaBatchDto.setCourseName(busyqaBatches.get(i).getBusyqaCourse().getCourseName());
			busyqaBatchDto.setTrainerName(busyqaBatches.get(i).getUser().getFirstName());
			busyqaBatchDto.setStartDate(busyqaBatches.get(i).getStartDate());
			busyqaBatchDto.setEndDate(busyqaBatches.get(i).getEndDate());
			busyqaBatchDto.setCourseLocation(busyqaBatches.get(i).getCourseLocation());
			busyqaBatchDto.setBatchDetails(busyqaBatches.get(i).getBatchDetails());
			busyqaBatchDto.setTaxPercentage(busyqaBatches.get(i).getTaxPercentage());
			busyqaBatchDto.setNetCourseAmount(busyqaBatches.get(i).getNetCourseAmount());
			busyqaBatchDto.setCourseLenght(busyqaBatches.get(i).getBusyqaCourse().getCourseLenght());
			busyqaBatchDto.setBatchStatus(busyqaBatches.get(i).getBatchStatus());
			busyqaBatchDto.setCourseAmount(busyqaBatches.get(i).getBusyqaCourse().getCourseAmount());
			busyqaBatchDto.setSeason(busyqaBatches.get(i).getSeason());
			busyqaBatchDto.setRegistrationFees(busyqaBatches.get(i).getRegistrationFees());
			reterivedBusyqaBatches.add(busyqaBatchDto);
		}
		return new ApiResponse<>(HttpStatus.OK.value(), "Courses fetched successfully.", reterivedBusyqaBatches);
	}

	@GetMapping("/{id}")
	public ApiResponse<BusyqaBatchDto> getOne(@PathVariable int id) {
		BusyqaBatch busyqaBatches = busyqaBatchRepo.findById(Long.valueOf(id)).get();

		BusyqaBatchDto busyqaBatchDto = new BusyqaBatchDto();
		busyqaBatchDto.setId(busyqaBatches.getId());
		busyqaBatchDto.setCourseName(busyqaBatches.getBusyqaCourse().getCourseName());
		busyqaBatchDto.setTrainerName(busyqaBatches.getUser().getFirstName());
		busyqaBatchDto.setStartDate(busyqaBatches.getStartDate());
		busyqaBatchDto.setEndDate(busyqaBatches.getEndDate());
		busyqaBatchDto.setCourseLocation(busyqaBatches.getCourseLocation());
		busyqaBatchDto.setBatchDetails(busyqaBatches.getBatchDetails());
		busyqaBatchDto.setTaxPercentage(busyqaBatches.getTaxPercentage());
		busyqaBatchDto.setNetCourseAmount(busyqaBatches.getNetCourseAmount());
		busyqaBatchDto.setCourseLenght(busyqaBatches.getBusyqaCourse().getCourseLenght());
		busyqaBatchDto.setBatchStatus(busyqaBatches.getBatchStatus());
		busyqaBatchDto.setCourseAmount(busyqaBatches.getBusyqaCourse().getCourseAmount());
		busyqaBatchDto.setSeason(busyqaBatches.getSeason());
		busyqaBatchDto.setRegistrationFees(busyqaBatches.getRegistrationFees());
		return new ApiResponse<>(HttpStatus.OK.value(), "Batch fetched successfully.", busyqaBatchDto);
	}

	@GetMapping("/status/scheduled/{courseName}")
	public ApiResponse<List<BusyqaBatchDto>> getByCourseName(@PathVariable String courseName) {

		List<BusyqaBatch> busyqaBatches = busyqaBatchRepo
				.findBybusyqaCourse(busyqaCourseRepo.findBycourseName(courseName).get());

		List<BusyqaBatchDto> busyqaBatchDtoList = new ArrayList<>();
		for (int i = 0; i < busyqaBatches.size(); i++) {
			BusyqaBatchDto busyqaBatchDto = new BusyqaBatchDto();

			if (busyqaBatches.get(i).getBatchStatus().equals("Scheduled")) {
				busyqaBatchDto.setId(busyqaBatches.get(i).getId());
				busyqaBatchDto.setCourseName(busyqaBatches.get(i).getBusyqaCourse().getCourseName());
				busyqaBatchDto.setTrainerName(busyqaBatches.get(i).getUser().getFirstName());
				busyqaBatchDto.setStartDate(busyqaBatches.get(i).getStartDate());
				busyqaBatchDto.setEndDate(busyqaBatches.get(i).getEndDate());
				busyqaBatchDto.setCourseLocation(busyqaBatches.get(i).getCourseLocation());
				busyqaBatchDto.setBatchDetails(busyqaBatches.get(i).getBatchDetails());
				busyqaBatchDto.setTaxPercentage(busyqaBatches.get(i).getTaxPercentage());
				busyqaBatchDto.setNetCourseAmount(busyqaBatches.get(i).getNetCourseAmount());
				busyqaBatchDto.setCourseLenght(busyqaBatches.get(i).getBusyqaCourse().getCourseLenght());
				busyqaBatchDto.setBatchStatus(busyqaBatches.get(i).getBatchStatus());
				busyqaBatchDto.setSeason(busyqaBatches.get(i).getSeason());
				busyqaBatchDto.setRegistrationFees(busyqaBatches.get(i).getRegistrationFees());
				busyqaBatchDto.setCourseAmount(busyqaBatches.get(i).getBusyqaCourse().getCourseAmount());
				busyqaBatchDtoList.add(busyqaBatchDto);
			}
		}

		return new ApiResponse<>(HttpStatus.OK.value(), "Batche fetched successfully.", busyqaBatchDtoList);
	}

	@GetMapping("/status/{status}")
	public ApiResponse<List<BusyqaBatchDto>> getByCourseStatus(@PathVariable String status) {

		List<BusyqaBatch> busyqaBatches = busyqaBatchRepo.findBybatchStatus(status);
		List<BusyqaBatchDto> reterivedBusyqaBatches = new ArrayList<>();

		for (int i = 0; i < busyqaBatches.size(); i++) {
			BusyqaBatchDto busyqaBatchDto = new BusyqaBatchDto();
			busyqaBatchDto.setId(busyqaBatches.get(i).getId());
			busyqaBatchDto.setCourseName(busyqaBatches.get(i).getBusyqaCourse().getCourseName());
			busyqaBatchDto.setTrainerName(busyqaBatches.get(i).getUser().getFirstName());
			busyqaBatchDto.setStartDate(busyqaBatches.get(i).getStartDate());
			busyqaBatchDto.setEndDate(busyqaBatches.get(i).getEndDate());
			busyqaBatchDto.setCourseLocation(busyqaBatches.get(i).getCourseLocation());
			busyqaBatchDto.setBatchDetails(busyqaBatches.get(i).getBatchDetails());
			busyqaBatchDto.setTaxPercentage(busyqaBatches.get(i).getTaxPercentage());
			busyqaBatchDto.setNetCourseAmount(busyqaBatches.get(i).getNetCourseAmount());
			busyqaBatchDto.setCourseLenght(busyqaBatches.get(i).getBusyqaCourse().getCourseLenght());
			busyqaBatchDto.setBatchStatus(busyqaBatches.get(i).getBatchStatus());
			busyqaBatchDto.setCourseAmount(busyqaBatches.get(i).getBusyqaCourse().getCourseAmount());
			busyqaBatchDto.setSeason(busyqaBatches.get(i).getSeason());
			busyqaBatchDto.setRegistrationFees(busyqaBatches.get(i).getRegistrationFees());
			reterivedBusyqaBatches.add(busyqaBatchDto);
		}
		return new ApiResponse<>(HttpStatus.OK.value(), "Courses fetched successfully.", reterivedBusyqaBatches);
	}

	@PostMapping("/create")
	public ResponseEntity<?> createBatch(@Valid @RequestBody String busyqaBatchDto) throws IOException {
		try {
			ObjectMapper mapper = new ObjectMapper();
			BusyqaBatchDto reterivedBusyQsBatchDto = mapper.readValue(busyqaBatchDto, BusyqaBatchDto.class);
			BusyqaBatch busyqaBatch = new BusyqaBatch();

			BusyqaCourse busyqaCourse = busyqaCourseRepo.findBycourseName(reterivedBusyQsBatchDto.getCourseName())
					.get();
			User busyqaTrainer = userRepository.findByfirstName(reterivedBusyQsBatchDto.getTrainerName()).get();

			busyqaBatch.setBusyqaCourse(busyqaCourse);
			busyqaBatch.setUser(busyqaTrainer);

			busyqaBatch.setStartDate(reterivedBusyQsBatchDto.getStartDate());
			LocalDate endLocalDate = LocalDate.parse(reterivedBusyQsBatchDto.getStartDate());
			busyqaBatch.setEndDate(String.valueOf(endLocalDate.plusWeeks(busyqaCourse.getCourseLenght())));
			busyqaBatch.setCourseLocation(reterivedBusyQsBatchDto.getCourseLocation());
			busyqaBatch.setBatchDetails(reterivedBusyQsBatchDto.getBatchDetails());
			busyqaBatch.setBatchStatus("Scheduled");
			busyqaBatch.setTaxPercentage(reterivedBusyQsBatchDto.getTaxPercentage());
			double tax = (busyqaCourse.getCourseAmount() * reterivedBusyQsBatchDto.getTaxPercentage()) / 100;
			busyqaBatch.setNetCourseAmount(busyqaCourse.getCourseAmount() + tax);
			String path = BusyqaBatchController.class.getResource("/").getFile();
			Path pathed = Paths.get(path.substring(1, path.length() - 25));

			///Set Registration Fees and Season
			LocalDate localDate = LocalDate.parse(reterivedBusyQsBatchDto.getStartDate());

			ZonedDateTime zdt = localDate.atStartOfDay(ZoneId.systemDefault());

			Month month = Month.from( zdt );

			if ( spring.contains( month ) ) {
				busyqaBatch.setSeason("spring");

			} else if ( summer.contains( month ) ) {
				busyqaBatch.setSeason("summer");


			} else if ( fall.contains( month ) ) {
				busyqaBatch.setSeason("fall");


			}  else {
				busyqaBatch.setSeason("winter");
			}

			busyqaBatch.setRegistrationFees(reterivedBusyQsBatchDto.getRegistrationFees());
			BusyqaBatch busyqaBatch1 = busyqaBatchRepo.save(busyqaBatch);

			String OS = System.getProperty("os.name").toLowerCase();
			if (OS.indexOf("win") >= 0){
				writeUsingOutputStream(busyqaBatch1.getBatchDetails(),
						String.format(pathed.toString() + "\\src\\main\\resources\\templates\\%s.html",
								busyqaBatch.getUser().getEmail().toUpperCase() + busyqaBatch1.getId()+"_batch"));
			}
			else{
				writeUsingOutputStream(busyqaBatch1.getBatchDetails(),
						String.format(pathed.toString() + "/src/main/resources/templates/%s.html",
								busyqaBatch.getUser().getEmail().toUpperCase() + busyqaBatch1.getId()+"_batch"));
			}
		} catch (Exception e) {
			return new ResponseEntity<>(new ResponseMessage("Batch  not created "), HttpStatus.BAD_REQUEST);
		}

		return new ResponseEntity<>(new ResponseMessage("Batch  Created Successfully"), HttpStatus.OK);
	}

	@PostMapping("/createTrainer/{id}")
	public ResponseEntity<?> createTrainerInforh(@PathVariable String id, @Valid @RequestBody String busyqaTrainerDto) throws IOException {
		try {
			User user = userRepository.findById(Long.valueOf(id)).get();

			String path = BusyqaBatchController.class.getResource("/").getFile();
			Path pathed = Paths.get(path.substring(1, path.length() - 25));

			String OS = System.getProperty("os.name").toLowerCase();


			if (OS.indexOf("win") >= 0){
				writeUsingOutputStream(busyqaTrainerDto,
						String.format(pathed.toString() + "\\src\\main\\resources\\templates\\%s.html",
								user.getEmail()).toUpperCase());
			}
			else{
				writeUsingOutputStream(busyqaTrainerDto,
						String.format(pathed.toString() + "/src/main/resources/templates/%s.html",
								user.getEmail()).toUpperCase());
			}
		} catch (Exception e) {
			return new ResponseEntity<>(new ResponseMessage("Not created "), HttpStatus.BAD_REQUEST);
		}

		return new ResponseEntity<>(new ResponseMessage("Successfully Created"), HttpStatus.OK);
	}

	@PutMapping("/update")
	public ResponseEntity<?> updateBatch(@Valid @RequestBody String busyqaBatchDto) throws IOException {
		ObjectMapper mapper = new ObjectMapper();

		BusyqaBatchDto reterivedBusyQsBatchDto = mapper.readValue(busyqaBatchDto, BusyqaBatchDto.class);

		BusyqaBatch toUpdateBusqaBatch = busyqaBatchRepo.findById(reterivedBusyQsBatchDto.getId()).get();

		if (toUpdateBusqaBatch == null) {
			return new ResponseEntity<>(new ResponseMessage("Batch  Not Found "), HttpStatus.BAD_REQUEST);

		} else {
			BusyqaBatch busyqaBatch = new BusyqaBatch();

			BusyqaCourse busyqaCourse = busyqaCourseRepo.findBycourseName(reterivedBusyQsBatchDto.getCourseName())
					.get();
			User busyqaTrainer = userRepository.findByfirstName(reterivedBusyQsBatchDto.getTrainerName()).get();

			busyqaBatch.setBusyqaCourse(busyqaCourse);
			busyqaBatch.setUser(busyqaTrainer);

			busyqaBatch.setStartDate(reterivedBusyQsBatchDto.getStartDate());
			LocalDate endLocalDate = LocalDate.parse(reterivedBusyQsBatchDto.getStartDate());
			busyqaBatch.setEndDate(String.valueOf(endLocalDate.plusWeeks(busyqaCourse.getCourseLenght())));
			busyqaBatch.setCourseLocation(reterivedBusyQsBatchDto.getCourseLocation());
			busyqaBatch.setBatchDetails(reterivedBusyQsBatchDto.getBatchDetails());
			busyqaBatch.setBatchStatus(reterivedBusyQsBatchDto.getBatchStatus());
			busyqaBatch.setTaxPercentage(reterivedBusyQsBatchDto.getTaxPercentage());
			double tax = (busyqaCourse.getCourseAmount() * reterivedBusyQsBatchDto.getTaxPercentage()) / 100;
			busyqaBatch.setNetCourseAmount(busyqaCourse.getCourseAmount() + tax);

			///Set Registration Fees and Season
			LocalDate localDate = LocalDate.parse(reterivedBusyQsBatchDto.getStartDate());

			ZonedDateTime zdt = localDate.atStartOfDay(ZoneId.systemDefault());

			Month month = Month.from( zdt );

			if ( spring.contains( month ) ) {
				busyqaBatch.setSeason("spring");

			} else if ( summer.contains( month ) ) {
				busyqaBatch.setSeason("summer");


			} else if ( fall.contains( month ) ) {
				busyqaBatch.setSeason("fall");


			}  else {
				busyqaBatch.setSeason("winter");
			}

			busyqaBatch.setRegistrationFees(reterivedBusyQsBatchDto.getRegistrationFees());
			busyqaBatchRepo.deleteById(toUpdateBusqaBatch.getId());
			BusyqaBatch busyqaBatch1 = busyqaBatchRepo.save(busyqaBatch);

			String path = BusyqaBatchController.class.getResource("/").getFile();
			Path pathed = Paths.get(path.substring(1, path.length() - 25));
			String OS = System.getProperty("os.name").toLowerCase();

			if (OS.indexOf("win") >= 0){
				writeUsingOutputStream(busyqaBatch1.getBatchDetails(),
						String.format(pathed.toString() + "\\src\\main\\resources\\templates\\%s.html",
								busyqaBatch.getUser().getEmail().toUpperCase() + busyqaBatch1.getId()+"_batch"));
			}
			else{
				writeUsingOutputStream(busyqaBatch1.getBatchDetails(),
						String.format(pathed.toString() + "/src/main/resources/templates/%s.html",
								busyqaBatch.getUser().getEmail().toUpperCase() + busyqaBatch1.getId()+"_batch"));
			}

		}

		return new ResponseEntity<>(new ResponseMessage("Batch  Updated  Successfully"), HttpStatus.OK);
	}



}
