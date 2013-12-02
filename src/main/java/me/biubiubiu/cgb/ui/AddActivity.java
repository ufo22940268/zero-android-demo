package me.biubiubiu.cgb.ui;

import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.*;

import me.biubiubiu.cgb.BootstrapApplication;
import me.biubiubiu.cgb.R;
import me.biubiubiu.cgb.core.PauseTimerEvent;
import me.biubiubiu.cgb.core.ResumeTimerEvent;
import me.biubiubiu.cgb.core.StopTimerEvent;
import me.biubiubiu.cgb.core.TimerPausedEvent;
import me.biubiubiu.cgb.core.TimerService;
import me.biubiubiu.cgb.core.TimerTickEvent;
import javax.inject.Inject;
import com.squareup.otto.Bus;
import com.squareup.otto.Subscribe;

import butterknife.InjectView;
import butterknife.Views;
import com.actionbarsherlock.app.*;
import com.actionbarsherlock.view.*;

public class AddActivity extends SherlockFragmentActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.bootstrap_add);
        setTitle("新签商户");

        Spinner s1 = (Spinner) findViewById(R.id.cowork);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
                this, R.array.coworks, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        s1.setAdapter(adapter);

        getActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View view) {
    }

    public void handleSubmit(View view) {
        finish();
    }
}
