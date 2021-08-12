package me.hjhng125.spreadsheet;

import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
public class SheetController {

    private final SheetService service;

    @GetMapping("/sheets/{spreadSheetId}")
    public ResponseEntity<List<List<Object>>> get(@PathVariable String spreadSheetId, SheetSelectDTO selectDTO){
        List<List<Object>> spreadSheetRecord = service.getSpreadSheetRecord(spreadSheetId, selectDTO);
        return ResponseEntity.ok(spreadSheetRecord);
    }

    @PostMapping("/sheets")
    public ResponseEntity<SheetCreateResponseDTO> create(@RequestBody SheetCreateDTO createDTO) {

        SheetCreateResponseDTO responseDTO = service.createSpreadSheet(createDTO);

        return ResponseEntity.ok(responseDTO);
    }

}
