package uk.co.xlabsystems.timetracker;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import uk.co.xlabsystems.timetracker.R;
import uk.co.xlabsystems.timetracker.security.CredentialStore;

public class SetupCredentials extends Activity {
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.setup_credentials);
    }

    public void saveCredentials(View view) {
        CredentialStore store = CredentialStore.getInstance();
        store.setUsername(((EditText) findViewById(R.id.username)).getText().toString());
        store.setPassword(((EditText) findViewById(R.id.password)).getText().toString());
        startActivity(new Intent(this,MainActivity.class));
        finish();
    }
}