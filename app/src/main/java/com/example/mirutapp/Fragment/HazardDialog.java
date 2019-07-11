package com.example.mirutapp.Fragment;


import android.content.Context;
import android.content.Intent;
import android.content.pm.ResolveInfo;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.mirutapp.R;

import java.util.List;
import java.util.Set;

import static com.example.mirutapp.util.SringUtil.urlEncode;

/**
 * A simple {@link DialogFragment} subclass.
 */
public class HazardDialog extends DialogFragment {


    private ConnectMapFragment connect;
    private Button buttonInfo, buttonTweet, buttonCancel;
    private String urlHazard;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_hazard_dialog, container, false);

        //Arguments
        Bundle bundle = getArguments();
        urlHazard = bundle.getString("URLHAZARD");

        //Attaching ids to widgets
        buttonInfo = view.findViewById(R.id.buttonInfo);
        buttonTweet = view.findViewById(R.id.buttonTweet);
        buttonCancel = view.findViewById(R.id.buttonCancel);

        //Cancel Dialog
        buttonCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getDialog().dismiss();
            }
        });

        //Go to UOCT Website
        buttonInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent browserIntent = new Intent(
                        Intent.ACTION_VIEW,
                        Uri.parse(urlHazard));
                startActivity(browserIntent);
            }
        });

        //Go to Twitter App
        buttonTweet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Create intent using ACTION_VIEW and a normal Twitter url:
                String tweetUrl = String.format("https://twitter.com/intent/tweet?text=%s&url=%s",
                        urlEncode("#UOCT"),
                        urlEncode(" #MiRutApp"));
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(tweetUrl));

                // Narrow down to official Twitter app, if available:
                List<ResolveInfo> matches = getActivity().getPackageManager().queryIntentActivities(intent, 0);
                for (ResolveInfo info : matches) {
                    if (info.activityInfo.packageName.toLowerCase().startsWith("com.twitter")) {
                        intent.setPackage(info.activityInfo.packageName);
                    }
                }

                startActivity(intent);
            }
        });

        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            connect = (ConnectMapFragment) getTargetFragment();
        } catch (ClassCastException e) {
            Log.e("HazardDialog", "ClassCastException in onAttach: " + e.getMessage());
        }
    }

    public interface ConnectMapFragment{
        //int sendInputs();
    }


}
