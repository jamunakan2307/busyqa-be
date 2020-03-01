package com.busyqa.crm.controller;

import java.io.IOException;
import java.text.DecimalFormat;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.busyqa.crm.mail.MailClient;
import com.busyqa.crm.message.response.ApiResponse;
import com.busyqa.crm.model.BusyqaBatch;
import com.busyqa.crm.model.Client;
import com.busyqa.crm.model.PaymentHistroy;
import com.busyqa.crm.model.PaymentRecord;
import com.busyqa.crm.model.User;
import com.busyqa.crm.model.UserTeamRole;
import com.busyqa.crm.repo.ClientCourseRepo;
import com.busyqa.crm.repo.ClientStatusRepository;
import com.busyqa.crm.repo.PaymentHistoryRespository;
import com.busyqa.crm.repo.PaymentPlanRepository;
import com.busyqa.crm.repo.PaymentRecordRepository;
import com.busyqa.crm.repo.PaymentStatusRepository;
import com.busyqa.crm.repo.RoleRepository;
import com.busyqa.crm.repo.TeamRepository;
import com.busyqa.crm.repo.TrainerRespository;
import com.busyqa.crm.repo.UserRepository;
import com.busyqa.crm.repo.UserTeamRoleRepository;
import com.busyqa.crm.repo.verion_2_0.BusyqaBatchRepo;
import com.busyqa.crm.repo.verion_2_0.BusyqaCourseRepo;
import com.busyqa.crm.security.JwtProvider;
import com.fasterxml.jackson.databind.ObjectMapper;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/pay-student")
public class StudentPaymentController {
	@Autowired
	AuthenticationManager authenticationManager;

	@Autowired
	TeamRepository teamRepository;

	@Autowired
	UserRepository userRepository;

	@Autowired
	RoleRepository roleRepository;

	@Autowired
	PasswordEncoder encoder;

	@Autowired
	UserTeamRoleRepository userTeamRoleRepository;

	@Autowired
	JwtProvider jwtProvider;

	@Autowired
	PaymentRecordRepository paymentRecordRepository;

	@Autowired
	ClientCourseRepo clientCourseRepo;

	@Autowired
	ClientStatusRepository clientStatusRepository;

	@Autowired
	PaymentPlanRepository paymentPlanRepository;

	@Autowired
	PaymentStatusRepository paymentStatusRepository;

	@Autowired
	MailClient mailClient;

	@Autowired
	TrainerRespository trainerRespository;

	@Autowired
	PaymentHistoryRespository paymentHistoryRespository;
	@Autowired
	BusyqaBatchRepo busyqaBatchRepo;

	@Autowired
	BusyqaCourseRepo busyqaCourseRepo;

	@GetMapping("/{id}")
	public ApiResponse<PaymentHistroy> getOne(@PathVariable int id) {
		ApiResponse<com.busyqa.crm.model.User> userApiResponse = new ApiResponse<>(HttpStatus.BAD_REQUEST.value(),
				"Users Not found", null);
		PaymentRecord paymentRecordList = paymentRecordRepository
				.findByUser(userRepository.findById(Long.valueOf(id)).get()).get();
		User user = userRepository.findById(Long.valueOf(id)).get();

		List<PaymentHistroy> paymentHistroy = paymentHistoryRespository.findByuserOrderByScheduledPaymentDateAsc(user);
		List<PaymentHistroy> paymentHistroycleaned = new ArrayList<>();
		int j = 1 ;
		int z = 0;
		for (int i = 0; i < paymentHistroy.size(); i++) {

			LocalDate lscheduledDate = LocalDate.parse(paymentHistroy.get(i).getScheduledPaymentDate());
			LocalDate toDay = LocalDate.now();

			if (paymentHistroy.get(i).getRecordStatus().equals("UnPaid") || paymentHistroy.get(i).getRecordStatus().equals("Missed")
					 || paymentHistroy.get(i).getRecordStatus().equals("Hold") || paymentHistroy.get(i).getRecordStatus().equals("Drop")) {
				paymentHistroycleaned.add(paymentHistroy.get(i));
				if (paymentHistroy.get(i).getRecordStatus().equals("Drop")){
					z = 1 ;
				}
			}

		}

		for (int k = 0 ; k < paymentHistroycleaned.size(); k++ ){
			if (paymentHistroycleaned.get(k).getRecordStatus().equals("UnPaid")){
				j = j + 1;
			}
		}

		if (paymentHistroycleaned.isEmpty()){
			PaymentHistroy dummyPaymentHistory = new PaymentHistroy();
			return new ApiResponse<>(HttpStatus.OK.value(), "User fetched successfully.",dummyPaymentHistory);
		}

		else {
			return new ApiResponse<>(HttpStatus.OK.value(), "User fetched successfully.", paymentHistroycleaned.get(z));
		}
	}

	@GetMapping("/myself/{id}")
	public ApiResponse<PaymentHistroy> getMyTransctions(@PathVariable int id) {
		ApiResponse<com.busyqa.crm.model.User> userApiResponse = new ApiResponse<>(HttpStatus.BAD_REQUEST.value(),
				"Users Not found", null);

		User user = userRepository.findById(Long.valueOf(id)).get();

		List<PaymentHistroy> paymentHistroy = paymentHistoryRespository.findByuserOrderByScheduledPaymentDateAsc(user);

		return new ApiResponse<>(HttpStatus.OK.value(), "User fetched successfully.", paymentHistroy);
	}

	@GetMapping("/myself2/{userName}")
	public ApiResponse<PaymentHistroy> getMyTransctions2(@PathVariable String userName) {
		ApiResponse<com.busyqa.crm.model.User> userApiResponse = new ApiResponse<>(HttpStatus.BAD_REQUEST.value(),
				"Users Not found", null);

		User user = userRepository.findByUsername(jwtProvider.getUserNameFromJwtToken(userName)).get();

		List<PaymentHistroy> paymentHistroy = paymentHistoryRespository.findByuserOrderByScheduledPaymentDateAsc(user);

		return new ApiResponse<>(HttpStatus.OK.value(), "User fetched successfully.", paymentHistroy);
	}

	@GetMapping("/recalculateAll")
	public ApiResponse<PaymentHistroy> getReCalculateAl() {
		ApiResponse<com.busyqa.crm.model.User> userApiResponse = new ApiResponse<>(HttpStatus.BAD_REQUEST.value(),
				"Users Not found", null);

		List<UserTeamRole> userTeamRoleList = userTeamRoleRepository
				.findByTeam(teamRepository.findByName("TEAM_CLIENT").get());
		List<User> user = new ArrayList<>();

		for (int i = 0; i < userTeamRoleList.size(); i++) {
			user.add(userTeamRoleList.get(i).getUser());
		}
		DecimalFormat df = new DecimalFormat("####.##");

		for (int i = 0; i < user.size(); i++) {
			List<PaymentHistroy> paymentHistroy = paymentHistoryRespository
					.findByuserOrderByScheduledPaymentDateAsc(user.get(i));
			List<PaymentHistroy> cleanPayHistoryList = paymentHistroy;
			List<PaymentHistroy> checkPayHistoryList = new ArrayList<>();
			try {
				for (int k = 0; k < paymentHistroy.size(); k++) {
					LocalDate lscheduledDate = LocalDate.parse(paymentHistroy.get(k).getScheduledPaymentDate());

					LocalDate toDay = LocalDate.now();
					if (toDay.isAfter(lscheduledDate) && paymentHistroy.get(k).getPayLock().equals("No") && ((paymentHistroy.get(k).getRecordStatus().equals("UnPaid")
							|| paymentHistroy.get(k).getRecordStatus().equals("Missed")))) {
						PaymentRecord paymentRecordList = paymentRecordRepository.findByUser(user.get(i)).get();
						PaymentRecord updatedPaymentRecord = paymentRecordList;
						if (paymentRecordList.getPaymentPlan().getName().equals("Automated_Weekly")) {
							int nuberOfDays = Math.toIntExact(ChronoUnit.DAYS.between(lscheduledDate, toDay));

							if (nuberOfDays > 3 && nuberOfDays <= 7) {
								cleanPayHistoryList.get(k).setRecordStatus("Missed");
								paymentRecordList
										.setPaymentStatus(paymentStatusRepository.findByname("DUE_FOR_A_WEEK").get());

								for (int j = k + 1; j < cleanPayHistoryList.size(); j++) {
									cleanPayHistoryList.get(j).setDifferenceAmount(Double.parseDouble(
											df.format(paymentRecordList.getTotalCourseAmountAfterDiscount())));
								}
								paymentHistoryRespository.delete(paymentHistroy.get(k));
								paymentHistoryRespository.save(cleanPayHistoryList.get(k));
								paymentRecordRepository.delete(paymentRecordList);
								paymentRecordRepository.save(updatedPaymentRecord);
							} else if (nuberOfDays > 7) {
								cleanPayHistoryList.get(k).setRecordStatus("Late");
								double penalityAmount = cleanPayHistoryList.get(k).getWeeklyAmountPaid() * 0.025;
								double actualAmount = cleanPayHistoryList.get(k + 1).getWeeklyAmountPaid();
								cleanPayHistoryList.get(k).setPaymentNotes(String.format("Late Fee Charged : %.2f  CAD",
										Double.parseDouble(df.format(penalityAmount))));

								updatedPaymentRecord
										.setPaymentStatus(paymentStatusRepository.findByname("SERIOUS_OVER_DUE").get());
								updatedPaymentRecord.setTotalCourseAmountAfterDiscount(
										paymentRecordList.getTotalCourseAmountAfterDiscount() + penalityAmount);

								cleanPayHistoryList.get(k + 1).setWeeklyAmountPaid(Double.parseDouble(df.format(
										penalityAmount + actualAmount + cleanPayHistoryList.get(k)
												.getWeeklyAmountPaid())));
								for (int j = k + 1; j < cleanPayHistoryList.size(); j++) {
									cleanPayHistoryList.get(j).setDifferenceAmount(Double.parseDouble(
											df.format(paymentRecordList.getTotalCourseAmountAfterDiscount())));
								}

								paymentHistoryRespository.delete(paymentHistroy.get(k));
								paymentHistoryRespository.save(cleanPayHistoryList.get(k));
								paymentRecordRepository.delete(paymentRecordList);
								paymentRecordRepository.save(updatedPaymentRecord);
							}

						}
						//Biwekkly / Monthly
						else if (paymentRecordList.getPaymentPlan().getName().equals("Automated_Bi_Weekly")) {
							int nuberOfDays = Math.toIntExact(ChronoUnit.DAYS.between(lscheduledDate, toDay));

							if (nuberOfDays > 7 && nuberOfDays <= 14) {

								cleanPayHistoryList.get(k).setRecordStatus("Missed");
								paymentRecordList
										.setPaymentStatus(paymentStatusRepository.findByname("DUE_FOR_A_WEEK").get());

								for (int j = k + 1; j < cleanPayHistoryList.size(); j++) {
									cleanPayHistoryList.get(j).setDifferenceAmount(Double.parseDouble(
											df.format(paymentRecordList.getTotalCourseAmountAfterDiscount())));
								}
								paymentHistoryRespository.delete(paymentHistroy.get(k));
								paymentHistoryRespository.save(cleanPayHistoryList.get(k));
								paymentRecordRepository.delete(paymentRecordList);
								paymentRecordRepository.save(updatedPaymentRecord);
							} else if (nuberOfDays > 14) {
								cleanPayHistoryList.get(k).setRecordStatus("Late");
								double penalityAmount = cleanPayHistoryList.get(k).getWeeklyAmountPaid() * 0.025;
								double actualAmount = cleanPayHistoryList.get(k + 1).getWeeklyAmountPaid();
								cleanPayHistoryList.get(k).setPaymentNotes(String.format("Late Fee Charged : %.2f  CAD",
										Double.parseDouble(df.format(penalityAmount))));

								updatedPaymentRecord.setTotalCourseAmountAfterDiscount(
										paymentRecordList.getTotalCourseAmountAfterDiscount() + penalityAmount);
								updatedPaymentRecord
										.setPaymentStatus(paymentStatusRepository.findByname("SERIOUS_OVER_DUE").get());

								cleanPayHistoryList.get(k + 1).setWeeklyAmountPaid(Double.parseDouble(df.format(
										penalityAmount + actualAmount + cleanPayHistoryList.get(k)
												.getWeeklyAmountPaid())));

								for (int j = k + 1; j < cleanPayHistoryList.size(); j++) {
									cleanPayHistoryList.get(j).setDifferenceAmount(Double.parseDouble(
											df.format(paymentRecordList.getTotalCourseAmountAfterDiscount())));
								}

								paymentHistoryRespository.delete(paymentHistroy.get(k));
								paymentHistoryRespository.save(cleanPayHistoryList.get(k));
								paymentRecordRepository.delete(paymentRecordList);
								paymentRecordRepository.save(updatedPaymentRecord);
							}

						}

						//Monthly
						else if (paymentRecordList.getPaymentPlan().getName().equals("Automated_Monthly")) {
							int nuberOfDays = Math.toIntExact(ChronoUnit.DAYS.between(lscheduledDate, toDay));

							if (nuberOfDays > 7 && nuberOfDays <= 31) {

								cleanPayHistoryList.get(k).setRecordStatus("Missed");
								paymentRecordList
										.setPaymentStatus(paymentStatusRepository.findByname("DUE_FOR_A_WEEK").get());

								for (int j = k + 1; j < cleanPayHistoryList.size(); j++) {
									cleanPayHistoryList.get(j).setDifferenceAmount(Double.parseDouble(
											df.format(paymentRecordList.getTotalCourseAmountAfterDiscount())));
								}
								paymentHistoryRespository.delete(paymentHistroy.get(k));
								paymentHistoryRespository.save(cleanPayHistoryList.get(k));
								paymentRecordRepository.delete(paymentRecordList);
								paymentRecordRepository.save(updatedPaymentRecord);
							} else if (nuberOfDays > 31) {
								cleanPayHistoryList.get(k).setRecordStatus("Late");
								double penalityAmount = cleanPayHistoryList.get(k).getWeeklyAmountPaid() * 0.025;
								double actualAmount = cleanPayHistoryList.get(k + 1).getWeeklyAmountPaid();
								cleanPayHistoryList.get(k).setPaymentNotes(String.format("Late Fee Charged : %.2f  CAD",
										Double.parseDouble(df.format(penalityAmount))));

								updatedPaymentRecord.setTotalCourseAmountAfterDiscount(
										paymentRecordList.getTotalCourseAmountAfterDiscount() + penalityAmount);
								updatedPaymentRecord
										.setPaymentStatus(paymentStatusRepository.findByname("SERIOUS_OVER_DUE").get());

								cleanPayHistoryList.get(k + 1).setWeeklyAmountPaid(Double.parseDouble(df.format(
										penalityAmount + actualAmount + cleanPayHistoryList.get(k)
												.getWeeklyAmountPaid())));

								for (int j = k + 1; j < cleanPayHistoryList.size(); j++) {
									cleanPayHistoryList.get(j).setDifferenceAmount(Double.parseDouble(
											df.format(paymentRecordList.getTotalCourseAmountAfterDiscount())));
								}

								paymentHistoryRespository.delete(paymentHistroy.get(k));
								paymentHistoryRespository.save(cleanPayHistoryList.get(k));
								paymentRecordRepository.delete(paymentRecordList);
								paymentRecordRepository.save(updatedPaymentRecord);
							}

						}

					}
				}

				//hold
				List<PaymentHistroy> verifyPaymentHistroy = paymentHistoryRespository
						.findByuserOrderByScheduledPaymentDateAsc(user.get(i));
				for (int m = 0; m < verifyPaymentHistroy.size(); m++) {
					if (verifyPaymentHistroy.get(m).getRecordStatus().equals("Late")) {
						if (verifyPaymentHistroy.get(m).getRecordStatus().equals("Late") && verifyPaymentHistroy.get(m + 1)
								.getRecordStatus().equals("Late")) {
							for (int h = m + 1; h < verifyPaymentHistroy.size(); h++){
								verifyPaymentHistroy.get(h).setRecordStatus("Hold");
								verifyPaymentHistroy.get(h).setPayLock("Yes");
								paymentHistoryRespository.delete(verifyPaymentHistroy.get(h));
								paymentHistoryRespository.save(verifyPaymentHistroy.get(h));
							}

						}
					}
				}

				//drop
				List<PaymentHistroy> verifyDropHistroy = paymentHistoryRespository
						.findByuserOrderByScheduledPaymentDateAsc(user.get(i));
				for (int m = 0; m < verifyDropHistroy.size(); m++) {
					if (verifyDropHistroy.get(m).getRecordStatus().equals("Hold")) {
						if (verifyDropHistroy.get(m).getRecordStatus().equals("Hold") && verifyDropHistroy.get(m + 1)
								.getRecordStatus().equals("Hold")) {
							for (int h = m + 1; h < verifyDropHistroy.size(); h++){
								verifyDropHistroy.get(h).setRecordStatus("Drop");
								paymentHistoryRespository.delete(verifyDropHistroy.get(h));
								paymentHistoryRespository.save(verifyDropHistroy.get(h));
							}

						}
					}
				}



			} catch (Exception e) {
				System.out.println("No Payment Record Found ");
			}



		}

		return new ApiResponse<>(HttpStatus.OK.value(), "Calculation Engine Success", null);
	}

	@PutMapping("/{id}")
	public ApiResponse<PaymentHistroy> UpdateOne(@PathVariable int id, @RequestBody String clientDto)
			throws IOException {

		ObjectMapper mapper = new ObjectMapper();

		PaymentHistroy paymentHistroy2 = mapper.readValue(clientDto, PaymentHistroy.class);

		User user = userRepository.findById(Long.valueOf(id)).get();

		List<PaymentHistroy> paymentHistroy = paymentHistoryRespository.findByuserOrderByScheduledPaymentDateAsc(user);
		PaymentRecord paymentRecordList = paymentRecordRepository.findByUser(user).get();
		PaymentRecord updatedPaymentRecordList = paymentRecordList;

		for (int i = 0; i < paymentHistroy.size(); i++) {

			if (paymentHistroy.get(i).getScheduledPaymentDate().equals(paymentHistroy2.getScheduledPaymentDate())) {

				if (paymentHistroy2.getPaymentMode().equals("Credit/Debit")){

					double paidAmount = paymentHistroy2.getAmountPaid();
					double expectedAmount = paymentHistroy.get(i).getWeeklyAmountPaid()  + (paymentHistroy.get(i).getWeeklyAmountPaid() * 0.03);

					if (paidAmount <=  paymentHistroy.get(i).getWeeklyAmountPaid()){
						return new ApiResponse<>(HttpStatus.BAD_REQUEST.value(),
								"Match the Amount as per Scheduled Payment with 3% Transactional Charges ", null);
					}
					DecimalFormat df = new DecimalFormat("####.##");
					if ( Double.parseDouble(df.format(expectedAmount)) !=  Double.parseDouble(df.format(paidAmount))) {
						return new ApiResponse<>(HttpStatus.BAD_REQUEST.value(),
								"Match the Amount as per Scheduled Payment with 3% Transactional Charges ", null);
					}

					updatedPaymentRecordList
							.setAmountPaid(paymentRecordList.getAmountPaid() + paidAmount);
					updatedPaymentRecordList.setLastPaymentTimeStamp(LocalDate.now().toString());
					paymentHistroy.get(i).setAmountPaid(paidAmount);
					paymentHistroy.get(i).setPaymentNotes(paymentHistroy2.getPaymentNotes() + " Paid " + paymentHistroy2.getAmountPaid() * 0.03 + " [CAD} as transactional fees");
					paymentHistroy.get(i).setRecordStatus("Paid");
					paymentHistroy.get(i).setPaymentDate(LocalDate.now().toString());
					paymentHistroy.get(i).setPayLock("Yes");
					paymentHistroy.get(i).setPaymentMode("Credit/Debit");
					paymentHistroy.get(i).setEmailReminder("Yes");
					paymentRecordRepository.delete(paymentRecordList);
					PaymentRecord  mepayment = paymentRecordRepository.save(updatedPaymentRecordList);
					for (int k = i; k < paymentHistroy.size(); k++) {
						try{
							paymentHistroy.get(k + 1).setPayLock("No");
							paymentHistroy.get(k + 1).setRecordStatus("UnPaid");
						}catch (Exception e){

						}

					}
				}

				else {
					if (paymentHistroy.get(i).getWeeklyAmountPaid() != paymentHistroy2.getAmountPaid()) {
						return new ApiResponse<>(HttpStatus.BAD_REQUEST.value(),
								"Match the Amount as per Scheduled Payment ", null);
					}

					updatedPaymentRecordList
							.setAmountPaid(paymentRecordList.getAmountPaid() + paymentHistroy2.getAmountPaid());
					updatedPaymentRecordList.setLastPaymentTimeStamp(LocalDate.now().toString());
					paymentHistroy.get(i).setAmountPaid(paymentHistroy2.getAmountPaid());
					paymentHistroy.get(i).setPaymentNotes(paymentHistroy2.getPaymentNotes());
					paymentHistroy.get(i).setRecordStatus("Paid");
					paymentHistroy.get(i).setPaymentDate(LocalDate.now().toString());
					paymentHistroy.get(i).setPayLock("Yes");
					paymentHistroy.get(i).setPaymentMode("Cash");
					paymentHistroy.get(i).setEmailReminder("Yes");
					paymentRecordRepository.delete(paymentRecordList);
					PaymentRecord  mepayment = paymentRecordRepository.save(updatedPaymentRecordList);
					for (int k = i; k < paymentHistroy.size(); k++) {
						try{
							paymentHistroy.get(k + 1).setPayLock("No");
							paymentHistroy.get(k + 1).setRecordStatus("UnPaid");
						}catch (Exception e){

						}

					}

				}


			}
		}


		//Delete previous
		List<PaymentHistroy> paymentHistroyDelete = paymentHistoryRespository
				.findByuserOrderByScheduledPaymentDateAsc(user);
		for (int j = 0; j < paymentHistroy.size(); j++) {
			paymentHistoryRespository.deleteById(paymentHistroyDelete.get(j).getId());

		}

		List<PaymentHistroy>   paymentHistroy1 = new ArrayList<>();
		//add New
		for (int i = 0; i < paymentHistroy.size(); i++) {
			paymentHistroy1.add(paymentHistoryRespository.save(paymentHistroy.get(i)));
		}

		PaymentRecord paymentRecordList2 = paymentRecordRepository.findByUser(user).get();
		double amount = 0 ;
		for (int h = 0; h < paymentHistroy1.size(); h++ ){
			amount = amount + paymentHistroy1.get(h).getAmountPaid();
		}
		amount = amount + paymentRecordList2.getBusyqaBatch().getRegistrationFees();
		PaymentRecord newPayment = paymentRecordList2;
		newPayment.setAmountPaid(amount);

		paymentRecordRepository.delete(paymentRecordList2);
		paymentRecordRepository.save(newPayment);

		return new ApiResponse<>(HttpStatus.OK.value(), "Payment successfully Posted ", null);
	}

	@PutMapping("/populate/{id}")
	public ApiResponse<PaymentHistroy> update(@PathVariable int id, @RequestBody String clientDto) throws IOException {
		try {
			ObjectMapper mapper = new ObjectMapper();

			Client clientDtoUpdated = mapper.readValue(clientDto, Client.class);

			User user = userRepository.findById(Long.valueOf(id)).get();

			PaymentRecord paymentRecordList = paymentRecordRepository.findByUser(user).get();

			PaymentRecord toBeUpdatedPaymentRecord = paymentRecordList;
			BusyqaBatch busyqaBatch = busyqaBatchRepo.findById(paymentRecordList.getBusyqaBatch().getId()).get();
			double registartionFees = busyqaBatch.getRegistrationFees();


			List<PaymentHistroy> paymentHistroy = paymentHistoryRespository
					.findByuserOrderByScheduledPaymentDateAsc(user);
			for (int i = 0; i < paymentHistroy.size(); i++) {
				paymentHistoryRespository.deleteById(paymentHistroy.get(i).getId());
			}

			List<PaymentHistroy> paymentHistroyToBeSent = new ArrayList<>();
			toBeUpdatedPaymentRecord
					.setPaymentPlan(paymentPlanRepository.findByname(clientDtoUpdated.getPaymentPlan()).get());
			toBeUpdatedPaymentRecord
					.setPaymentStatus(paymentStatusRepository.findByname(clientDtoUpdated.getPaymentStatus()).get());
			toBeUpdatedPaymentRecord.setAmountPaid(registartionFees);
			toBeUpdatedPaymentRecord.setAnchorDate(clientDtoUpdated.getAnchorDate());
			toBeUpdatedPaymentRecord.setTotalCourseAmount(busyqaBatch.getNetCourseAmount());
			toBeUpdatedPaymentRecord.setTotaDiscountAmount(clientDtoUpdated.getTotaDiscountAmount());
			toBeUpdatedPaymentRecord.setTotalCourseAmountAfterDiscount(
					busyqaBatch.getNetCourseAmount() - registartionFees - clientDtoUpdated.getTotaDiscountAmount());
			paymentRecordRepository.delete(paymentRecordList);

			PaymentRecord updatePaymentRecord = paymentRecordRepository.save(toBeUpdatedPaymentRecord);
			DecimalFormat df = new DecimalFormat("####.##");

			if (clientDtoUpdated.getPaymentPlan().equals("Automated_Weekly")) {
				LocalDate achorDate = LocalDate.parse(clientDtoUpdated.getAnchorDate());
				LocalDate endDate = LocalDate.parse(updatePaymentRecord.getBusyqaBatch().getEndDate());
				int nuberOfWeeks = Math.toIntExact(ChronoUnit.WEEKS.between(achorDate, endDate));
				double totalEmi = updatePaymentRecord.getTotalCourseAmountAfterDiscount() / (nuberOfWeeks - 3);
				for (int i = 1; i <= (nuberOfWeeks - 3); i++) {
					PaymentHistroy paymentHistroyupdated = new PaymentHistroy();
					paymentHistroyupdated.setTotalAmount(updatePaymentRecord.getTotalCourseAmountAfterDiscount());
					paymentHistroyupdated.setScheduledPaymentDate(achorDate.plusWeeks(i).toString());
					paymentHistroyupdated.setWeeklyAmountPaid(Double.parseDouble(df.format(totalEmi)));
					paymentHistroyupdated.setDifferenceAmount(updatePaymentRecord.getTotalCourseAmountAfterDiscount());
					paymentHistroyupdated.setUser(user);
					paymentHistroyupdated.setRecordStatus("UnPaid");
					paymentHistroyupdated.setPayLock("No");
					paymentHistroyupdated.setEmailReminder("No");
					paymentHistoryRespository.save(paymentHistroyupdated);
					paymentHistroyToBeSent.add(paymentHistroyupdated);
				}
			} else if (clientDtoUpdated.getPaymentPlan().equals("Automated_Bi_Weekly")) {
				LocalDate achorDate = LocalDate.parse(clientDtoUpdated.getAnchorDate());

				LocalDate endDate = LocalDate.parse(updatePaymentRecord.getBusyqaBatch().getEndDate());
				int nuberOfWeeks = Math.toIntExact(ChronoUnit.WEEKS.between(achorDate, endDate));
				double totalEmi = updatePaymentRecord.getTotalCourseAmountAfterDiscount() / ((nuberOfWeeks - 3) / 2);
				int k = 0;
				for (int i = 1; i <= ((nuberOfWeeks - 3) / 2); i++) {
					PaymentHistroy paymentHistroyupdated = new PaymentHistroy();
					paymentHistroyupdated.setTotalAmount(updatePaymentRecord.getTotalCourseAmountAfterDiscount());
					paymentHistroyupdated.setScheduledPaymentDate(achorDate.plusWeeks(i + k).toString());
					paymentHistroyupdated.setWeeklyAmountPaid(Double.parseDouble(df.format(totalEmi)));
					paymentHistroyupdated.setUser(user);
					paymentHistroyupdated.setRecordStatus("UnPaid");
					paymentHistroyupdated.setPayLock("No");
					paymentHistroyupdated.setEmailReminder("No");
					paymentHistroyupdated.setDifferenceAmount(updatePaymentRecord.getTotalCourseAmountAfterDiscount());
					paymentHistoryRespository.save(paymentHistroyupdated);
					paymentHistroyToBeSent.add(paymentHistroyupdated);
					k = k + 1;
				}
			} else if (clientDtoUpdated.getPaymentPlan().equals("Automated_Monthly")) {
				LocalDate achorDate = LocalDate.parse(clientDtoUpdated.getAnchorDate());
				LocalDate endDate = LocalDate.parse(updatePaymentRecord.getBusyqaBatch().getEndDate());
				int nuberOfWeeks = Math.toIntExact(ChronoUnit.WEEKS.between(achorDate, endDate));
				double totalEmi = updatePaymentRecord.getTotalCourseAmountAfterDiscount() / ((nuberOfWeeks - 3) / 4);
				int k = 0;
				for (int i = 1; i <= ((nuberOfWeeks - 3) / 4); i++) {
					PaymentHistroy paymentHistroyupdated = new PaymentHistroy();
					paymentHistroyupdated.setTotalAmount(updatePaymentRecord.getTotalCourseAmountAfterDiscount());
					paymentHistroyupdated.setScheduledPaymentDate(achorDate.plusWeeks(i + k).toString());
					paymentHistroyupdated.setWeeklyAmountPaid(Double.parseDouble(df.format(totalEmi)));
					paymentHistroyupdated.setUser(user);
					paymentHistroyupdated.setRecordStatus("UnPaid");
					paymentHistroyupdated.setPayLock("No");
					paymentHistroyupdated.setEmailReminder("No");
					paymentHistroyupdated.setDifferenceAmount(updatePaymentRecord.getTotalCourseAmountAfterDiscount());
					paymentHistoryRespository.save(paymentHistroyupdated);
					paymentHistroyToBeSent.add(paymentHistroyupdated);
					k = k + 3;

				}
			} else {
				PaymentHistroy paymentHistroyupdated = new PaymentHistroy();
				paymentHistroyupdated.setTotalAmount(updatePaymentRecord.getTotalCourseAmountAfterDiscount());
				paymentHistroyupdated.setScheduledPaymentDate(busyqaBatch.getEndDate());
				paymentHistroyupdated.setWeeklyAmountPaid(updatePaymentRecord.getTotalCourseAmountAfterDiscount());
				paymentHistroyupdated.setUser(user);
				paymentHistroyupdated.setRecordStatus("UnPaid");
				paymentHistroyupdated.setDifferenceAmount(updatePaymentRecord.getTotalCourseAmountAfterDiscount());
				paymentHistoryRespository.save(paymentHistroyupdated);
				paymentHistroyToBeSent.add(paymentHistroyupdated);
			}

			return new ApiResponse<>(HttpStatus.OK.value(), "Payment Transactions Populated", paymentHistroyToBeSent);

		} catch (Exception e) {
			return new ApiResponse<>(HttpStatus.BAD_REQUEST.value(), "Error in generating payment tranactions ", null);

		}

	}

	@PutMapping("/update2/{id}")
	public ApiResponse<PaymentHistroy> updatePayments(@PathVariable int id, @RequestBody String clientDto)
			throws IOException {
		try {
			ObjectMapper mapper = new ObjectMapper();

			Client clientDtoUpdated = mapper.readValue(clientDto, Client.class);

			User user = userRepository.findById(Long.valueOf(id)).get();

			PaymentRecord paymentRecordList = paymentRecordRepository.findByUser(user).get();

			PaymentRecord toBeUpdatedPaymentRecord = paymentRecordList;

			toBeUpdatedPaymentRecord.setPaySetupDone("Yes");
			paymentRecordRepository.delete(paymentRecordList);

			PaymentRecord updatePaymentRecord = paymentRecordRepository.save(toBeUpdatedPaymentRecord);
			return new ApiResponse<>(HttpStatus.OK.value(), "Client Updated", null);
		} catch (Exception e) {
			return new ApiResponse<>(HttpStatus.BAD_REQUEST.value(), "Error in generating payment tranactions ", null);

		}

	}

	@PutMapping("/updateStatus/{id}")
	public ApiResponse<PaymentHistroy> updateStatuss(@PathVariable int id, @RequestBody String clientDto)
			throws IOException {
		try {
			ObjectMapper mapper = new ObjectMapper();

			Client clientDtoUpdated = mapper.readValue(clientDto, Client.class);

			User user = userRepository.findById(Long.valueOf(id)).get();

			PaymentRecord paymentRecordList = paymentRecordRepository.findByUser(user).get();

			PaymentRecord toBeUpdatedPaymentRecord = paymentRecordList;

			toBeUpdatedPaymentRecord
					.setPaymentStatus(paymentStatusRepository.findByname(clientDtoUpdated.getPaymentStatus()).get());
			paymentRecordRepository.delete(paymentRecordList);

			PaymentRecord updatePaymentRecord = paymentRecordRepository.save(toBeUpdatedPaymentRecord);
			return new ApiResponse<>(HttpStatus.OK.value(), "Client Updated", null);
		} catch (Exception e) {
			return new ApiResponse<>(HttpStatus.BAD_REQUEST.value(), "Error in generating payment tranactions ", null);

		}

	}

}
