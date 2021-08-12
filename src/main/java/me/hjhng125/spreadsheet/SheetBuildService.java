package me.hjhng125.spreadsheet;

import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.services.sheets.v4.Sheets;
import com.google.api.services.sheets.v4.Sheets.Builder;
import java.io.IOException;
import lombok.RequiredArgsConstructor;
import me.hjhng125.auth.AuthService;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SheetBuildService {

    private final AuthService authService;
    private final JsonFactory jsonFactory;
    private final NetHttpTransport httpTransport;

    private static final String APPLICATION_NAME = "Google-SheetsSample/0.1";

    public Sheets build() throws IOException {

        Credential credential = authService.getGoogleClientSecrets();

        return new Builder(httpTransport, jsonFactory, credential)
            .setApplicationName(APPLICATION_NAME)
            .build();
    }
}
