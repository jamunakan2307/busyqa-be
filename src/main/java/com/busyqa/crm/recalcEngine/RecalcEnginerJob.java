package com.busyqa.crm.recalcEngine;

import com.busyqa.crm.mail.MailClient;
import com.busyqa.crm.model.PaymentHistroy;
import com.busyqa.crm.model.PaymentRecord;
import com.busyqa.crm.model.User;
import com.busyqa.crm.model.UserTeamRole;
import com.busyqa.crm.repo.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.text.DecimalFormat;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

@Component
public class RecalcEnginerJob {

    private static final Logger LOG = LoggerFactory.getLogger(RecalcEnginerJob.class);
    private final TeamRepository teamRepository;
    private final UserTeamRoleRepository userTeamRoleRepository;
    private final PaymentRecordRepository paymentRecordRepository;
    private final PaymentStatusRepository paymentStatusRepository;
    private final PaymentHistoryRespository paymentHistoryRespository;
    private final PaymentPlanRepository paymentPlanRepository;
    private final MailClient mailClient;
    private final UserRepository userRepository;

    public RecalcEnginerJob(
            final TeamRepository teamRepository,
            final UserTeamRoleRepository userTeamRoleRepository,
            final PaymentRecordRepository paymentRecordRepository,
            final PaymentStatusRepository paymentStatusRepository,
            final PaymentHistoryRespository paymentHistoryRespository,
            final PaymentPlanRepository paymentPlanRepository,
            final MailClient mailClient, final UserRepository userRepository) {
        this.teamRepository = teamRepository;
        this.userTeamRoleRepository = userTeamRoleRepository;
        this.paymentRecordRepository = paymentRecordRepository;
        this.paymentStatusRepository = paymentStatusRepository;
        this.paymentHistoryRespository = paymentHistoryRespository;
        this.paymentPlanRepository = paymentPlanRepository;
        this.mailClient = mailClient;
        this.userRepository = userRepository;
    }

    @Scheduled(cron = "0 0/2 * * * ?")
    public void recalcJobRunner() {

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
            List<PaymentHistroy> checkPayHistoryList = new ArrayList<>();
            try {
                for (int k = 0; k < paymentHistroy.size(); k++) {
                    LocalDate lscheduledDate =
                            LocalDate.parse(paymentHistroy.get(k).getScheduledPaymentDate());

                    LocalDate toDay = LocalDate.now();
                    if (toDay.isAfter(lscheduledDate)
                            && paymentHistroy.get(k).getPayLock().equals("No")
                            && ((paymentHistroy.get(k).getRecordStatus().equals("UnPaid")
                            || paymentHistroy.get(k).getRecordStatus().equals("Missed")))) {
                        PaymentRecord paymentRecordList = paymentRecordRepository.findByUser(user.get(i)).get();
                        PaymentRecord updatedPaymentRecord = paymentRecordList;
                        String paymenPLan = paymentPlanRepository.findById(paymentRecordList.getPaymentPlan().getId()).get().getName();
                        if (paymenPLan.equals("Automated_Weekly")) {
                            int nuberOfDays = Math.toIntExact(ChronoUnit.DAYS.between(lscheduledDate, toDay));

                            if (nuberOfDays > 3 && nuberOfDays <= 7) {
                                cleanPayHistoryList.get(k).setRecordStatus("Missed");
                                paymentRecordList.setPaymentStatus(
                                        paymentStatusRepository.findByname("DUE_FOR_A_WEEK").get());

                                for (int j = k + 1; j < cleanPayHistoryList.size(); j++) {
                                    cleanPayHistoryList
                                            .get(j)
                                            .setDifferenceAmount(
                                                    Double.parseDouble(
                                                            df.format(paymentRecordList.getTotalCourseAmountAfterDiscount())));
                                }
                                paymentHistoryRespository.delete(cleanPayHistoryList.get(k));
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
                                        Double.parseDouble(
                                                df.format(paymentRecordList.getTotalCourseAmountAfterDiscount() + penalityAmount)));

                                try {
                                    cleanPayHistoryList.get(k + 1).setWeeklyAmountPaid(Double.parseDouble(df.format(
                                            penalityAmount + actualAmount + cleanPayHistoryList.get(k)
                                                    .getWeeklyAmountPaid())));
                                    paymentHistoryRespository.save(cleanPayHistoryList.get(k + 1));

                                } catch (Exception e) {
                                    cleanPayHistoryList.get(k).setWeeklyAmountPaid(Double.parseDouble(df.format(
                                            penalityAmount + actualAmount + cleanPayHistoryList.get(k)
                                                    .getWeeklyAmountPaid())));
                                    paymentHistoryRespository.save(cleanPayHistoryList.get(k));
                                }

                                try {
                                    for (int j = k + 1; j < cleanPayHistoryList.size(); j++) {
                                        cleanPayHistoryList.get(j).setDifferenceAmount(Double.parseDouble(
                                                df.format(paymentRecordList.getTotalCourseAmountAfterDiscount())));
                                        paymentHistoryRespository.save(cleanPayHistoryList.get(j));

                                    }
                                } catch (Exception e) {
                                    for (int j = k; j < cleanPayHistoryList.size(); j++) {
                                        cleanPayHistoryList.get(j).setDifferenceAmount(Double.parseDouble(
                                                df.format(paymentRecordList.getTotalCourseAmountAfterDiscount())));
                                        paymentHistoryRespository.save(cleanPayHistoryList.get(j));

                                    }
                                }


                                paymentHistoryRespository.save(cleanPayHistoryList.get(k));
                                paymentRecordRepository.delete(paymentRecordList);
                                paymentRecordRepository.save(updatedPaymentRecord);

                            }

                        }
                        // Biwekkly / Monthly
                        else if (paymenPLan.equals("Automated_Bi_Weekly")) {
                            int nuberOfDays = Math.toIntExact(ChronoUnit.DAYS.between(lscheduledDate, toDay));

                            if (nuberOfDays > 7 && nuberOfDays <= 14) {

                                cleanPayHistoryList.get(k).setRecordStatus("Missed");
                                paymentRecordList.setPaymentStatus(
                                        paymentStatusRepository.findByname("DUE_FOR_A_WEEK").get());

                                for (int j = k + 1; j < cleanPayHistoryList.size(); j++) {
                                    cleanPayHistoryList
                                            .get(j)
                                            .setDifferenceAmount(
                                                    Double.parseDouble(
                                                            df.format(paymentRecordList.getTotalCourseAmountAfterDiscount())));
                                }
                                paymentHistoryRespository.delete(cleanPayHistoryList.get(k));
                                paymentHistoryRespository.save(cleanPayHistoryList.get(k));
                                paymentRecordRepository.delete(paymentRecordList);
                                paymentRecordRepository.save(updatedPaymentRecord);

                            } else if (nuberOfDays > 14) {
                                cleanPayHistoryList.get(k).setRecordStatus("Late");
                                double penalityAmount = cleanPayHistoryList.get(k).getWeeklyAmountPaid() * 0.025;
                                double actualAmount = cleanPayHistoryList.get(k + 1).getWeeklyAmountPaid();
                                cleanPayHistoryList.get(k).setPaymentNotes(String.format("Late Fee Charged : %.2f  CAD",
                                        Double.parseDouble(df.format(penalityAmount))));

                                updatedPaymentRecord
                                        .setPaymentStatus(paymentStatusRepository.findByname("SERIOUS_OVER_DUE").get());
                                updatedPaymentRecord.setTotalCourseAmountAfterDiscount(
                                        Double.parseDouble(
                                                df.format(paymentRecordList.getTotalCourseAmountAfterDiscount() + penalityAmount)));

                                try {
                                    cleanPayHistoryList.get(k + 1).setWeeklyAmountPaid(Double.parseDouble(df.format(
                                            penalityAmount + actualAmount + cleanPayHistoryList.get(k)
                                                    .getWeeklyAmountPaid())));
                                    paymentHistoryRespository.save(cleanPayHistoryList.get(k + 1));

                                } catch (Exception e) {
                                    cleanPayHistoryList.get(k).setWeeklyAmountPaid(Double.parseDouble(df.format(
                                            penalityAmount + actualAmount + cleanPayHistoryList.get(k)
                                                    .getWeeklyAmountPaid())));
                                    paymentHistoryRespository.save(cleanPayHistoryList.get(k));
                                }

                                try {
                                    for (int j = k + 1; j < cleanPayHistoryList.size(); j++) {
                                        cleanPayHistoryList.get(j).setDifferenceAmount(Double.parseDouble(
                                                df.format(paymentRecordList.getTotalCourseAmountAfterDiscount())));
                                        paymentHistoryRespository.save(cleanPayHistoryList.get(j));

                                    }
                                } catch (Exception e) {
                                    for (int j = k; j < cleanPayHistoryList.size(); j++) {
                                        cleanPayHistoryList.get(j).setDifferenceAmount(Double.parseDouble(
                                                df.format(paymentRecordList.getTotalCourseAmountAfterDiscount())));
                                        paymentHistoryRespository.save(cleanPayHistoryList.get(j));

                                    }
                                }


                                paymentHistoryRespository.save(cleanPayHistoryList.get(k));
                                paymentRecordRepository.delete(paymentRecordList);
                                paymentRecordRepository.save(updatedPaymentRecord);

                            }

                        }

                        // Monthly
                        else if (paymenPLan.equals("Automated_Monthly")) {
                            int nuberOfDays = Math.toIntExact(ChronoUnit.DAYS.between(lscheduledDate, toDay));

                            if (nuberOfDays > 7 && nuberOfDays <= 31) {

                                cleanPayHistoryList.get(k).setRecordStatus("Missed");
                                paymentRecordList.setPaymentStatus(
                                        paymentStatusRepository.findByname("DUE_FOR_A_WEEK").get());

                                for (int j = k + 1; j < cleanPayHistoryList.size(); j++) {
                                    cleanPayHistoryList
                                            .get(j)
                                            .setDifferenceAmount(
                                                    Double.parseDouble(
                                                            df.format(paymentRecordList.getTotalCourseAmountAfterDiscount())));
                                }
                                paymentHistoryRespository.delete(cleanPayHistoryList.get(k));
                                paymentHistoryRespository.save(cleanPayHistoryList.get(k));
                                paymentRecordRepository.delete(paymentRecordList);
                                paymentRecordRepository.save(updatedPaymentRecord);

                            } else if (nuberOfDays > 31) {
                                cleanPayHistoryList.get(k).setRecordStatus("Late");
                                double penalityAmount = cleanPayHistoryList.get(k).getWeeklyAmountPaid() * 0.025;
                                double actualAmount = cleanPayHistoryList.get(k + 1).getWeeklyAmountPaid();
                                cleanPayHistoryList.get(k).setPaymentNotes(String.format("Late Fee Charged : %.2f  CAD",
                                        Double.parseDouble(df.format(penalityAmount))));

                                updatedPaymentRecord
                                        .setPaymentStatus(paymentStatusRepository.findByname("SERIOUS_OVER_DUE").get());
                                updatedPaymentRecord.setTotalCourseAmountAfterDiscount(
                                        Double.parseDouble(
                                                df.format(paymentRecordList.getTotalCourseAmountAfterDiscount() + penalityAmount)));

                                try {
                                    cleanPayHistoryList.get(k + 1).setWeeklyAmountPaid(Double.parseDouble(df.format(
                                            penalityAmount + actualAmount + cleanPayHistoryList.get(k)
                                                    .getWeeklyAmountPaid())));
                                    paymentHistoryRespository.save(cleanPayHistoryList.get(k + 1));

                                } catch (Exception e) {
                                    cleanPayHistoryList.get(k).setWeeklyAmountPaid(Double.parseDouble(df.format(
                                            penalityAmount + actualAmount + cleanPayHistoryList.get(k)
                                                    .getWeeklyAmountPaid())));
                                    paymentHistoryRespository.save(cleanPayHistoryList.get(k));
                                }

                                try {
                                    for (int j = k + 1; j < cleanPayHistoryList.size(); j++) {
                                        cleanPayHistoryList.get(j).setDifferenceAmount(Double.parseDouble(
                                                df.format(paymentRecordList.getTotalCourseAmountAfterDiscount())));
                                        paymentHistoryRespository.save(cleanPayHistoryList.get(j));

                                    }
                                } catch (Exception e) {
                                    for (int j = k; j < cleanPayHistoryList.size(); j++) {
                                        cleanPayHistoryList.get(j).setDifferenceAmount(Double.parseDouble(
                                                df.format(paymentRecordList.getTotalCourseAmountAfterDiscount())));
                                        paymentHistoryRespository.save(cleanPayHistoryList.get(j));

                                    }
                                }


                                paymentHistoryRespository.save(cleanPayHistoryList.get(k));
                                paymentRecordRepository.delete(paymentRecordList);
                                paymentRecordRepository.save(updatedPaymentRecord);

                            }
                        }
                    }
                }

                // hold
                List<PaymentHistroy> verifyPaymentHistroy =
                        paymentHistoryRespository.findByuserOrderByScheduledPaymentDateAsc(user.get(i));
                for (int m = 0; m < verifyPaymentHistroy.size(); m++) {
                    if (verifyPaymentHistroy.get(m).getRecordStatus().equals("Late")) {
                        if (verifyPaymentHistroy.get(m).getRecordStatus().equals("Late")
                                && verifyPaymentHistroy.get(m + 1).getRecordStatus().equals("Late")) {
                            for (int h = m + 1; h < verifyPaymentHistroy.size(); h++) {
                                verifyPaymentHistroy.get(h).setRecordStatus("Hold");
                                verifyPaymentHistroy.get(h).setPayLock("Yes");
                                paymentHistoryRespository.delete(verifyPaymentHistroy.get(h));
                                paymentHistoryRespository.save(verifyPaymentHistroy.get(h));
                            }
                        }
                    }
                }

                // drop
                List<PaymentHistroy> verifyDropHistroy =
                        paymentHistoryRespository.findByuserOrderByScheduledPaymentDateAsc(user.get(i));
                for (int m = 0; m < verifyDropHistroy.size(); m++) {
                    if (verifyDropHistroy.get(m).getRecordStatus().equals("Hold")) {
                        if (verifyDropHistroy.get(m).getRecordStatus().equals("Hold")
                                && verifyDropHistroy.get(m + 1).getRecordStatus().equals("Hold")) {
                            for (int h = m + 1; h < verifyDropHistroy.size(); h++) {
                                verifyDropHistroy.get(h).setRecordStatus("Drop");
                                paymentHistoryRespository.delete(verifyDropHistroy.get(h));
                                paymentHistoryRespository.save(verifyDropHistroy.get(h));
                            }
                        }
                    }
                }

            } catch (Exception e) {
                System.out.println("Caught Exception in Calc Engine Job ");
            }
        }
    }
}
