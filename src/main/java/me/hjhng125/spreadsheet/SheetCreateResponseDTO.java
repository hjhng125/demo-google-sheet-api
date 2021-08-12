package me.hjhng125.spreadsheet;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class SheetCreateResponseDTO {

    private final String sheetTitle;
    private final Integer rowCnt;
    private final String spreadSheetId;
    private final String spreadSheetUrl;
}
