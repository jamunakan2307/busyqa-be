package com.busyqa.crm.controller.version_2_0;

import com.busyqa.crm.message.response.ApiResponse;
import com.busyqa.crm.model.Client;
import com.busyqa.crm.model.PaymentRecord;
import com.busyqa.crm.model.UserTeamRole;
import com.busyqa.crm.repo.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/admin/client")
public class ClientDisplayController {

  @Autowired AuthenticationManager authenticationManager;

  @Autowired UserRepository userRepository;

  @Autowired TeamRepository teamRepository;

  @Autowired RoleRepository roleRepository;

  @Autowired UserTeamRoleRepository userTeamRoleRepository;

  @Autowired PasswordEncoder passwordEncoder;

  @Autowired ClientStatusRepository clientStatusRepository;

  @Autowired PaymentHistoryRespository paymentHistoryRespository;

  @Autowired PaymentStatusRepository paymentStatusRepository;

  @Autowired PaymentRecordRepository paymentRecordRepository;

  @GetMapping("/{allclients}")
  public ApiResponse<List<Client>> getAllUserbyclient(@PathVariable String teamName) {
    ApiResponse<com.busyqa.crm.model.User> userApiResponse =
        new ApiResponse<>(HttpStatus.BAD_REQUEST.value(), "Users not found", null);
    boolean clientAccess = true;
    com.busyqa.crm.model.Team requestTeam = teamRepository.findByName(teamName).get();
    List<com.busyqa.crm.model.User> requestorUser = userRepository.findByTeams(teamName);
    List<UserTeamRole> requestorTeamRole = userTeamRoleRepository.findByTeam(requestTeam);
    List<Client> clientList = new ArrayList<>();

    for (int i = 0; i < requestorTeamRole.size(); i++) {

      if ((requestorTeamRole.get(i).getTeam().getName().equals("TEAM_ADMIN"))
          && (requestorTeamRole.get(i)).getRole().getName().equals("ROLE_ADMIN"))
      {clientAccess = true;}
      else {clientAccess = false;}
}
      if(clientAccess)
      {
          List<PaymentRecord> paymentRecordList = paymentRecordRepository.findAll();

          for(int k = 0;k<paymentRecordList.size();k++){
              Client client = new Client();

              client.setId(paymentRecordList.get(k).getUser().getId());
              client.setFirstName(paymentRecordList.get(k).getUser().getFirstName());
              client.setLastName(paymentRecordList.get(k).getUser().getLastName());
              client.setCourseName(paymentRecordList.get(k).getBusyqaBatch().getBusyqaCourse().getCourseName());
              client.setCountry(paymentRecordList.get(k).getUser().getCountry());
              client.setClientStatus(paymentRecordList.get(k).getClientStatus().getName());
              clientList.add(client);
          }

      }
      return new ApiResponse<>(HttpStatus.OK.value(),"User fetched successfully",clientList);
    }
  }

