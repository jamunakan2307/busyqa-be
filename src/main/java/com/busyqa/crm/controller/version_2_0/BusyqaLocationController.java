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
import com.busyqa.crm.model.BusyqaLocation;
import com.busyqa.crm.repo.verion_2_0.BusyqaLocationRepo;
import com.fasterxml.jackson.databind.ObjectMapper;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/admin/location")
public class BusyqaLocationController {

	@Autowired
	BusyqaLocationRepo busyqaLocationRepo;

	@GetMapping("/getAll")
	public ApiResponse<List<BusyqaLocation>> getAllLocations() {
		List<BusyqaLocation> busyqaLocationList = busyqaLocationRepo.findAll();
		return new ApiResponse<>(HttpStatus.OK.value(), "Locations fetched successfully.", busyqaLocationList);
	}

	@GetMapping("/{id}")
	public ApiResponse<BusyqaLocation> getOneLocations(@PathVariable int id) {
		BusyqaLocation busyqaLocation = busyqaLocationRepo.findById(Long.valueOf(id)).get();
		return new ApiResponse<>(HttpStatus.OK.value(), "Locations fetched successfully.", busyqaLocation);
	}

	@DeleteMapping("/{id}")
	public ApiResponse<Void> deleteOne(@PathVariable int id) {
		busyqaLocationRepo.deleteById(Long.valueOf(id));
		return new ApiResponse<>(HttpStatus.OK.value(), "location successfully Deleted.", null);
	}

	@PostMapping("/create")
	public ResponseEntity<?> createCourse(@Valid @RequestBody String busyqaLocationDto) {
		ObjectMapper mapper = new ObjectMapper();

		try {
			BusyqaLocation busyqaLocation = mapper.readValue(busyqaLocationDto, BusyqaLocation.class);
			busyqaLocationRepo.save(busyqaLocation);
		} catch (Exception e) {
			return new ResponseEntity<>(new ResponseMessage("location not created "), HttpStatus.BAD_REQUEST);
		}

		return new ResponseEntity<>(new ResponseMessage("location Created Successfully"), HttpStatus.OK);
	}

	@PutMapping("/update")
	public ResponseEntity<?> updateCourse(@Valid @RequestBody String busyqaLocationDto) {
		ObjectMapper mapper = new ObjectMapper();

		try {
			BusyqaLocation busyqaLocation = mapper.readValue(busyqaLocationDto, BusyqaLocation.class);
			BusyqaLocation updatedBusyqaLocation = busyqaLocation;
			BusyqaLocation reterived = busyqaLocationRepo.findById(busyqaLocation.getId()).get();
			if (reterived == null) {
				return new ResponseEntity<>(new ResponseMessage("location not Updated "), HttpStatus.BAD_REQUEST);
			} else {
				busyqaLocationRepo.delete(updatedBusyqaLocation);
				busyqaLocation.setId(null);
				busyqaLocationRepo.save(busyqaLocation);
			}
		} catch (Exception e) {
			return new ResponseEntity<>(new ResponseMessage("location not Updated "), HttpStatus.BAD_REQUEST);
		}

		return new ResponseEntity<>(new ResponseMessage("location Updated Successfully"), HttpStatus.OK);
	}
}
