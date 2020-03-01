package com.busyqa.crm.controller;

import java.io.IOException;

import com.busyqa.crm.model.PaymentRecord;
import com.busyqa.crm.repo.PaymentRecordRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.busyqa.crm.message.response.ApiResponse;
import com.busyqa.crm.model.UpdateUserDto;
import com.busyqa.crm.model.UserDtoSingle;
import com.busyqa.crm.repo.UserRepository;
import com.busyqa.crm.repo.UserTeamRoleRepository;
import com.busyqa.crm.security.services.UserDetailsServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;

import javax.swing.tree.ExpandVetoException;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/admin/user")
public class AdminManageUserController {
	@Autowired
	UserRepository userRepository;

	@Autowired
	UserDetailsServiceImpl userDetailsService;

	@Autowired
	UserTeamRoleRepository userTeamRoleRepository;

	@Autowired
	PaymentRecordRepository paymentRecordRepository;

	@GetMapping("/{id}")
	public ApiResponse<User> getOne(@PathVariable int id) {
		return new ApiResponse<>(HttpStatus.OK.value(), "User fetched successfully.",
				userRepository.findById(Long.valueOf(id)));
	}

	@PutMapping("/{id}")
	public ApiResponse<User> update(@RequestBody String userDto) throws IOException {
		ObjectMapper mapper = new ObjectMapper();
		UpdateUserDto userDtoUpdated = new UpdateUserDto();
		try {
			userDtoUpdated = mapper.readValue(userDto, UpdateUserDto.class);
		} catch (IOException e) {
			}
		try{

			PaymentRecord updaDatePaymentRecord = paymentRecordRepository.findByUser(userRepository.findById(userDtoUpdated.getId()).get()).get();
			PaymentRecord newPaymentRecord = updaDatePaymentRecord;
			userDetailsService.update(userDtoUpdated);
			com.busyqa.crm.model.User payuser = userRepository.findByUsername(userDtoUpdated.getUsername()).get();
				newPaymentRecord.setUser(payuser);
				paymentRecordRepository.delete(updaDatePaymentRecord);
				paymentRecordRepository.save(newPaymentRecord);
			}catch (Exception e2){

			}

			return new ApiResponse<>(HttpStatus.OK.value(), "User updated successfully.",null);
		}


	@DeleteMapping("/{id}")
	public ApiResponse<Void> delete(@PathVariable int id) {
		PaymentRecord updaDatePaymentRecord = new PaymentRecord();
		try{
			updaDatePaymentRecord = paymentRecordRepository.findByUser(userRepository.findById(Long.valueOf(id)).get()).get();

		}catch (Exception e1){

		}
		userDetailsService.delete(id);

		try{
			paymentRecordRepository.delete(updaDatePaymentRecord);

		}catch (Exception e){

		}
		return new ApiResponse<>(HttpStatus.OK.value(), "User fetched successfully.", null);
	}

}
