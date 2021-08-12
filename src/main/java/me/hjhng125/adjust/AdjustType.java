package me.hjhng125.adjust;

import java.util.Arrays;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum AdjustType {

    MISTAKE("객실요금 등록실수"),
    HARD_BLOCK("하드블럭 입금가 변경"),
    PAY_WITHIN_AMT("수수료 변경"),
    OFFSET_PARTNER("업체 부담 상계"),
    BACK_COMMISSION("백커미션 차감"),
    FORECLOSURE("채권 압류 차감"),
    REFUND_DIFF("차액 환불 차감"),
    ETC("기타");

    private final String description;
}
