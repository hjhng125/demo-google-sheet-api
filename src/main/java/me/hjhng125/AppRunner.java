package me.hjhng125;

import java.time.LocalDate;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import me.hjhng125.adjust.Adjust;
import me.hjhng125.adjust.AdjustType;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
public class AppRunner implements ApplicationRunner {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    @Transactional
    public void run(ApplicationArguments args) {

//        for (int i = 0; i < 10000; ++i) {
//            Adjust adjust = Adjust.builder()
//                .adjustType(AdjustType.ETC)
//                .orderSheetId(111111L * (i + 1))
//                .revenueDate(LocalDate.now())
//                .originSalePrice(10)
//                .salePrice(10)
//                .originDepositPrice(1)
//                .depositPrice(1)
//                .discountPartnerAmt(0)
//                .discountWithinAmt(0)
//                .build();
//            entityManager.persist(adjust);
//        }
    }
}
