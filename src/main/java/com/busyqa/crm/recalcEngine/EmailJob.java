package com.busyqa.crm.recalcEngine;

import com.busyqa.crm.mail.MailClient;
import com.busyqa.crm.model.PaymentHistroy;
import com.busyqa.crm.model.User;
import com.busyqa.crm.model.UserTeamRole;
import com.busyqa.crm.repo.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

@Component
public class EmailJob {

  private static final Logger LOG = LoggerFactory.getLogger(EmailJob.class);
  private final TeamRepository teamRepository;
  private final UserTeamRoleRepository userTeamRoleRepository;
  private final PaymentRecordRepository paymentRecordRepository;
  private final PaymentStatusRepository paymentStatusRepository;
  private final PaymentHistoryRespository paymentHistoryRespository;
  private final MailClient mailClient;
  private final UserRepository userRepository;

  public EmailJob(
      final TeamRepository teamRepository,
      final UserTeamRoleRepository userTeamRoleRepository,
      final PaymentRecordRepository paymentRecordRepository,
      final PaymentStatusRepository paymentStatusRepository,
      final PaymentHistoryRespository paymentHistoryRespository,
      final MailClient mailClient,
      final UserRepository userRepository) {
    this.teamRepository = teamRepository;
    this.userTeamRoleRepository = userTeamRoleRepository;
    this.paymentRecordRepository = paymentRecordRepository;
    this.paymentStatusRepository = paymentStatusRepository;
    this.paymentHistoryRespository = paymentHistoryRespository;
    this.mailClient = mailClient;
    this.userRepository = userRepository;
  }

  @Scheduled(cron = "0 0/10 * * * ?")
  public void emailRunner() {
    LOG.info("Email Job Started");

    List<User> user = new ArrayList<>();
    List<UserTeamRole> userTeamRoleList = new ArrayList<>();

    try {
      userTeamRoleList =
              userTeamRoleRepository.findByTeam(teamRepository.findByName("TEAM_CLIENT").get());
    } catch (Exception e) {
      LOG.error("No User Found");
    }

    for (int i = 0; i < userTeamRoleList.size(); i++) {
      user.add(userTeamRoleList.get(i).getUser());
    }
    DecimalFormat df = new DecimalFormat("####.##");

    for (int i = 0; i < user.size(); i++) {
      List<PaymentHistroy> paymentHistroy =
              paymentHistoryRespository.findByuserOrderByScheduledPaymentDateAsc(user.get(i));
      List<PaymentHistroy> cleanPayHistoryList = paymentHistroy;

      try {
        for (int k = 0; k < paymentHistroy.size(); k++) {

          if (paymentHistroy.get(k).getEmailReminder().equals("No")
                  && ((paymentHistroy.get(k).getRecordStatus().equals("Late")
                  || paymentHistroy.get(k).getRecordStatus().equals("Missed")))) {

            if (paymentHistroy.get(k).getRecordStatus().equals("Missed")) {
              cleanPayHistoryList.get(k).setEmailReminder("Yes");
              paymentHistoryRespository.delete(paymentHistroy.get(k));
              PaymentHistroy emailPaymentUser = paymentHistoryRespository.save(cleanPayHistoryList.get(k));
              User emailUser = userRepository.findByid(emailPaymentUser.getUser().getId()).get();

              mailClient.prepareAndSendMissedFees(
                      emailUser.getEmail(),
                      String.valueOf(cleanPayHistoryList.get(k).getWeeklyAmountPaid()),
                      emailUser.getFirstName(), cleanPayHistoryList.get(k).getScheduledPaymentDate());
              System.out.println("Missed  Email Sent");
              LOG.info("Email Sent for %s for Missed Payment", emailUser.getFirstName());
            }
            if (paymentHistroy.get(k).getRecordStatus().equals("Late")) {
              cleanPayHistoryList.get(k).setEmailReminder("Yes");
              paymentHistoryRespository.delete(paymentHistroy.get(k));
              PaymentHistroy emailPaymentUser = paymentHistoryRespository.save(cleanPayHistoryList.get(k));
              User emailUser = userRepository.findByid(emailPaymentUser.getUser().getId()).get();

              mailClient.prepareAndSendLateFees(
                      emailUser.getEmail(),
                      String.valueOf(cleanPayHistoryList.get(k).getWeeklyAmountPaid()),
                      emailUser.getFirstName(),
                      String.valueOf(cleanPayHistoryList.get(k + 1).getWeeklyAmountPaid()), cleanPayHistoryList.get(k).getScheduledPaymentDate());
              System.out.println("Late  Email Sent");
              LOG.info("Email Sent for %s for Late Payment", emailUser.getFirstName());
            }
          }
        }
        LOG.info("Email Job Ended");
      } catch (Exception e) {
        System.out.println("Exception in  Email Job ");

      }
    }
  }
}
