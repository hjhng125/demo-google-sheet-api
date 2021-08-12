package me.hjhng125.adjust;

import static javax.persistence.EnumType.STRING;
import static javax.persistence.GenerationType.IDENTITY;
import static lombok.AccessLevel.PACKAGE;
import static lombok.AccessLevel.PROTECTED;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Builder
@NoArgsConstructor(access = PROTECTED)
@AllArgsConstructor(access = PACKAGE)
public class Adjust {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long adjustId;
    private Long orderSheetId;

    @Enumerated(STRING)
    private AdjustType adjustType;
    private LocalDate revenueDate;

    private long originSalePrice;
    private long salePrice;
    private long originDepositPrice;
    private long depositPrice;
    private long payWithinAmt;
    private long discountWithinAmt;
    private long discountPartnerAmt;

    public List<Object> getFieldList() {
        return Arrays.asList(
            this.adjustId.toString(),
            this.orderSheetId.toString(),
            this.adjustType.getDescription(),
            this.revenueDate.toString(),
            String.valueOf(this.originSalePrice),
            String.valueOf(this.salePrice),
            String.valueOf(this.originDepositPrice),
            String.valueOf(this.depositPrice),
            String.valueOf(this.payWithinAmt),
            String.valueOf(this.discountWithinAmt),
            String.valueOf(this.discountPartnerAmt));
    }
}
