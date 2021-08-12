package me.hjhng125.spreadsheet;

import java.util.List;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class SheetCreateDTO {

    private final String author;
    private final List<Long> adjustIds;
    private final String title;
}
