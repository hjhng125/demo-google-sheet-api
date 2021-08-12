package me.hjhng125.spreadsheet;

import com.google.api.services.sheets.v4.Sheets;
import com.google.api.services.sheets.v4.model.BatchUpdateSpreadsheetRequest;
import com.google.api.services.sheets.v4.model.CopySheetToAnotherSpreadsheetRequest;
import com.google.api.services.sheets.v4.model.GridRange;
import com.google.api.services.sheets.v4.model.Request;
import com.google.api.services.sheets.v4.model.SetDataValidationRequest;
import com.google.api.services.sheets.v4.model.SheetProperties;
import com.google.api.services.sheets.v4.model.Spreadsheet;
import com.google.api.services.sheets.v4.model.SpreadsheetProperties;
import com.google.api.services.sheets.v4.model.UpdateValuesResponse;
import com.google.api.services.sheets.v4.model.ValueRange;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.hjhng125.adjust.Adjust;
import me.hjhng125.adjust.AdjustRepository;
import me.hjhng125.exception.CustomException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class SheetService {

    private final SheetBuildService sheetBuildService;
    private final AdjustRepository adjusts;

    @Value("${spread-sheet.id}")
    private String TEMPLATE_SPREAD_SHEET_ID;
    private static final int TEMPLATE_SHEET_ID = 0;

    private static final String VALUE_INPUT_OPTION = "RAW"; // "USER_ENTERED"
    private static final String INSERT_OPTION = "INSERT_ROWS";

    public List<List<Object>> getSpreadSheetRecord(String spreadSheetId, SheetSelectDTO selectDTO) {
        String range = selectDTO.getSheetTitle() + "!A2:K" + (selectDTO.getRowCnt() + 1);
        try {
            Sheets sheetsService = sheetBuildService.build();
            ValueRange result =
                sheetsService.spreadsheets()
                    .values()
                    .get(spreadSheetId, range)
                    .execute();
            return result.getValues();

        } catch (IOException e) {
            log.error(e.getMessage());
            throw new CustomException(e);
        }
    }

    public SheetCreateResponseDTO createSpreadSheet(SheetCreateDTO createDTO) {
        Spreadsheet requestBody = new Spreadsheet()
            .setProperties(new SpreadsheetProperties()
                .setTitle(createDTO.getAuthor() + "의 Sheet"));

        try {
            Sheets sheetsService = sheetBuildService.build();
            Spreadsheet createdSpreadSheet =
                sheetsService
                    .spreadsheets()
                    .create(requestBody)
                    .execute();

            SheetProperties sheet = copyTemplate(sheetsService, createdSpreadSheet);
            Integer updatedRows = writeRecord(sheetsService,
                sheet.getTitle(),
                createdSpreadSheet.getSpreadsheetId(),
                createDTO.getAdjustIds());

            return SheetCreateResponseDTO.builder()
                .spreadSheetUrl(createdSpreadSheet.getSpreadsheetUrl())
                .spreadSheetId(createdSpreadSheet.getSpreadsheetId())
                .sheetTitle(sheet.getTitle())
                .rowCnt(updatedRows)
                .build();

        } catch (IOException e) {
            log.error(e.getMessage());
            throw new CustomException(e);
        }
    }

    private SheetProperties copyTemplate(Sheets sheetsService, Spreadsheet createdSpreadSheet) throws IOException {
        CopySheetToAnotherSpreadsheetRequest copyRequestBody = new CopySheetToAnotherSpreadsheetRequest();
        copyRequestBody.setDestinationSpreadsheetId(createdSpreadSheet.getSpreadsheetId());

        SheetProperties result = sheetsService.spreadsheets().sheets()
            .copyTo(TEMPLATE_SPREAD_SHEET_ID, TEMPLATE_SHEET_ID, copyRequestBody)
            .execute();

        log.info(result.toPrettyString());
        return result;
    }

    private Integer writeRecord(Sheets sheetsService, String sheetTitle, String spreadsheetId, List<Long> adjustIds) throws IOException {
        List<List<Object>> all = adjusts.findAllById(adjustIds).stream()
            .map(Adjust::getFieldList)
            .collect(Collectors.toList());

        ValueRange body = new ValueRange()
            .setValues(all);

        UpdateValuesResponse result = sheetsService.spreadsheets()
            .values()
            .update(spreadsheetId, sheetTitle + "!A2:K" + (all.size() + 1), body)
            .setValueInputOption(VALUE_INPUT_OPTION)
            .execute();

        Integer updatedRows = result.getUpdatedRows();
        log.info("updatedRows: {}", updatedRows);
        return updatedRows;
    }
}
