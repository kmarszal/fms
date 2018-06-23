package com.example.mapwithmarker;

import android.view.View;
import android.widget.Button;

public class ActivationView {
    private Button activationOkButton;
    private MapsMarkerActivity mainActivity;

    public ActivationView(Button activationOkButton, MapsMarkerActivity mainActivity) {
        this.activationOkButton = activationOkButton;
        this.mainActivity = mainActivity;
    }

    public void init(){
        activationOkButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mainActivity.setView(ViewCode.LOGIN);
            }
        });
    }
}
