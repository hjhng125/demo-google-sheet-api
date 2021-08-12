package me.hjhng125.auth;

import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.extensions.java6.auth.oauth2.AuthorizationCodeInstalledApp;
import com.google.api.client.extensions.jetty.auth.oauth2.LocalServerReceiver;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow.Builder;
import com.google.api.client.googleapis.auth.oauth2.GoogleClientSecrets;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.services.sheets.v4.SheetsScopes;
import java.io.IOException;
import java.util.Collections;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuthService {

    private final GoogleClientSecrets clientSecrets;
    private final NetHttpTransport httpTransport;
    private final JsonFactory jsonFactory;

    private static final String REDIRECT_PATH = "/auth/add_oauth_token";

    public Credential getGoogleClientSecrets() throws IOException {
        final GoogleAuthorizationCodeFlow flow = new Builder(
            httpTransport, jsonFactory, clientSecrets, Collections.singletonList(SheetsScopes.SPREADSHEETS)
        )
            .build();

        final LocalServerReceiver localServerReceiver = new LocalServerReceiver.Builder()
            .setPort(8081)
            .setCallbackPath(REDIRECT_PATH)
            .build();
        return new AuthorizationCodeInstalledApp(
            flow, localServerReceiver
        )
            .authorize(clientSecrets.getDetails().getClientId());
    }
}
