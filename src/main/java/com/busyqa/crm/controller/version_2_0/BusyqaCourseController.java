package com.busyqa.crm.controller.version_2_0;

import java.util.List;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.busyqa.crm.message.response.ApiResponse;
import com.busyqa.crm.message.response.ResponseMessage;
import com.busyqa.crm.model.BusyqaCourse;
import com.busyqa.crm.repo.verion_2_0.BusyqaBatchRepo;
import com.busyqa.crm.repo.verion_2_0.BusyqaCourseRepo;
import com.fasterxml.jackson.databind.ObjectMapper;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/admin/course")
public class BusyqaCourseController {

	@Autowired
	BusyqaBatchRepo busyqaBatchRepo;

	@Autowired
	BusyqaCourseRepo busyqaCourseRepo;

	@GetMapping("/getAll")
	public ApiResponse<List<BusyqaCourse>> getAllCourses() {
		List<BusyqaCourse> busyqaCourseList = busyqaCourseRepo.findAll();
		return new ApiResponse<>(HttpStatus.OK.value(), "Courses fetched successfully.", busyqaCourseList);
	}

	@GetMapping("/{id}")
	public ApiResponse<BusyqaCourse> getOneCourses(@PathVariable int id) {
		BusyqaCourse busyqaCourse = busyqaCourseRepo.findById(Long.valueOf(id)).get();
		return new ApiResponse<>(HttpStatus.OK.value(), "Courses fetched successfully.", busyqaCourse);
	}

	@DeleteMapping("/{id}")
	public ApiResponse<Void> deleteOne(@PathVariable int id) {
		busyqaCourseRepo.deleteById(Long.valueOf(id));
		return new ApiResponse<>(HttpStatus.OK.value(), "Course successfully Deleted.", null);
	}

	@PostMapping("/create")
	public ResponseEntity<?> createCourse(@Valid @RequestBody String busyqaCourseDto) {
		ObjectMapper mapper = new ObjectMapper();

		try {
			BusyqaCourse busyqaCourse = mapper.readValue(busyqaCourseDto, BusyqaCourse.class);
			busyqaCourse.setStatus("Active");
			busyqaCourseRepo.save(busyqaCourse);
		} catch (Exception e) {
			return new ResponseEntity<>(new ResponseMessage("Couses not created "), HttpStatus.BAD_REQUEST);
		}

		return new ResponseEntity<>(new ResponseMessage("Course Created Successfully"), HttpStatus.OK);
	}

	@PutMapping("/update")
	public ResponseEntity<?> updateCourse(@Valid @RequestBody String busyqaCourseDto) {
		ObjectMapper mapper = new ObjectMapper();

		try {
			BusyqaCourse busyqaCourse = mapper.readValue(busyqaCourseDto, BusyqaCourse.class);
			BusyqaCourse updatedBusyqaCourse = busyqaCourse;
			BusyqaCourse reterived = busyqaCourseRepo.findById(busyqaCourse.getId()).get();
			if (reterived == null) {
				return new ResponseEntity<>(new ResponseMessage("Couses not Updated "), HttpStatus.BAD_REQUEST);
			} else {
				busyqaCourseRepo.delete(updatedBusyqaCourse);
				busyqaCourse.setId(null);
				busyqaCourseRepo.save(busyqaCourse);
			}
		} catch (Exception e) {
			return new ResponseEntity<>(new ResponseMessage("Couses not Updated "), HttpStatus.BAD_REQUEST);
		}

		return new ResponseEntity<>(new ResponseMessage("Course Updated Successfully"), HttpStatus.OK);
	}
}
