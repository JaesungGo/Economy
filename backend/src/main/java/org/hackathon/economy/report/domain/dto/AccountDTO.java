package org.hackathon.economy.report.domain.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hackathon.economy.account.domain.Account;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

@Getter @Setter
@NoArgsConstructor
public class AccountDTO {
    private Long accountNo;
    private Long memberNo;
    private Long accountBalance;
    private Double accountRate;
    private Date createDate;
    private Date closedDate;
    private Boolean accountStatus;
    private List<DailyInterestDTO> dailyInterests;

    public static AccountDTO from(Account entity) {
        AccountDTO dto = new AccountDTO();
        dto.setAccountNo(entity.getAccountNo());
        dto.setMemberNo(entity.getMember().getMemberNo());
        dto.setAccountBalance(entity.getAccountBalance());
        dto.setAccountRate(entity.getAccountRate());
        dto.setCreateDate(entity.getCreateDate());
        dto.setClosedDate(entity.getClosedDate());
        dto.setAccountStatus(entity.getAccountStatus() == true);
        return dto;
    }
}