/**
 * Copyright 2013 Adam Speakman

 Licensed under the Apache License, Version 2.0 (the "License");
 you may not use this file except in compliance with the License.
 You may obtain a copy of the License at

 http://www.apache.org/licenses/LICENSE-2.0

 Unless required by applicable law or agreed to in writing, software
 distributed under the License is distributed on an "AS IS" BASIS,
 WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 See the License for the specific language governing permissions and
 limitations under the License.
 */

package speakman.whatsshakingnz.ui;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.webkit.WebView;
import android.widget.ProgressBar;

import com.google.android.gms.common.GoogleApiAvailability;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import speakman.whatsshakingnz.R;

/**
 * Created by Adam Speakman on 24/09/13.
 * http://speakman.net.nz
 */
@SuppressWarnings("SameParameterValue")
public class LicensesFragment extends DialogFragment {

    private AsyncTask<Void, Void, String> mLicenseLoader;

    private static final String FRAGMENT_TAG = "nz.net.speakman.androidlicensespage.LicensesFragment";
    private static final String KEY_SHOW_CLOSE_BUTTON = "keyShowCloseButton";
    private static final String KEY_SHOW_GOOGLE_LICENSES = "keyShowGoogleLicenses";

    /**
     * Creates a new instance of LicensesFragment with no Close button.
     *
     * @return A new licenses fragment.
     */
    public static LicensesFragment newInstance() {
        return new LicensesFragment();
    }

    /**
     * Creates a new instance of LicensesFragment with an optional Close button.
     *
     * @param showCloseButton Whether to show a Close button at the bottom of the dialog.
     *
     * @return A new licenses fragment.
     */
    public static LicensesFragment newInstance(boolean showCloseButton) {
        return newInstance(showCloseButton, false);
    }

    private static LicensesFragment newInstance(boolean showCloseButton, boolean showGoogleLicenses) {
        LicensesFragment fragment = new LicensesFragment();

        Bundle bundle = new Bundle();
        bundle.putBoolean(KEY_SHOW_CLOSE_BUTTON, showCloseButton);
        bundle.putBoolean(KEY_SHOW_GOOGLE_LICENSES, showGoogleLicenses);
        fragment.setArguments(bundle);

        return fragment;
    }

    /**
     * Builds and displays a licenses fragment with no Close button. Requires
     * "/res/raw/licenses.html" and "/res/layout/licenses_fragment.xml" to be
     * present.
     *
     * @param fm A fragment manager instance used to display this LicensesFragment.
     */
    @SuppressWarnings("unused")
    public static void displayLicensesFragment(FragmentManager fm) {
        FragmentTransaction ft = fm.beginTransaction();
        Fragment prev = fm.findFragmentByTag(FRAGMENT_TAG);
        if (prev != null) {
            ft.remove(prev);
        }
        ft.addToBackStack(null);

        // Create and show the dialog.
        DialogFragment newFragment = LicensesFragment.newInstance();
        newFragment.show(ft, FRAGMENT_TAG);
    }

    /**
     * Builds and displays a licenses fragment with or without a Close button.
     * Requires "/res/raw/licenses.html" and "/res/layout/licenses_fragment.xml"
     * to be present.
     *
     * @param fm A fragment manager instance used to display this LicensesFragment.
     * @param showCloseButton Whether to show a Close button at the bottom of the dialog.
     */
    public static void displayLicensesFragment(FragmentManager fm, boolean showCloseButton) {
        FragmentTransaction ft = fm.beginTransaction();
        Fragment prev = fm.findFragmentByTag(FRAGMENT_TAG);
        if (prev != null) {
            ft.remove(prev);
        }
        ft.addToBackStack(null);

        // Create and show the dialog.
        DialogFragment newFragment = LicensesFragment.newInstance(showCloseButton);
        newFragment.show(ft, FRAGMENT_TAG);
    }

    public static void displayGooglePlayServicesLicensesFragment(FragmentManager fm, boolean showCloseButton) {
        FragmentTransaction ft = fm.beginTransaction();
        Fragment prev = fm.findFragmentByTag(FRAGMENT_TAG);
        if (prev != null) {
            ft.remove(prev);
        }
        ft.addToBackStack(null);

        // Create and show the dialog.
        DialogFragment newFragment = LicensesFragment.newInstance(showCloseButton, true);
        newFragment.show(ft, FRAGMENT_TAG);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        loadLicenses();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mLicenseLoader != null) {
            mLicenseLoader.cancel(true);
        }
    }

    private WebView mWebView;
    private ProgressBar mIndeterminateProgress;

    @SuppressLint("InflateParams")
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Context context = getActivity();
        View content = LayoutInflater.from(context).inflate(R.layout.fragment_licenses, null);
        mWebView = (WebView) content.findViewById(R.id.licensesFragmentWebView);
        mIndeterminateProgress = (ProgressBar) content.findViewById(R.id.licensesFragmentIndeterminateProgress);

        boolean showCloseButton = false, showGoogleLicenses = false;
        Bundle arguments = getArguments();
        if (arguments != null) {
            showCloseButton = arguments.getBoolean(KEY_SHOW_CLOSE_BUTTON);
            showGoogleLicenses = arguments.getBoolean(KEY_SHOW_GOOGLE_LICENSES);
        }

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        String title = showGoogleLicenses ? context.getString(R.string.dialog_title_google_licenses) : context.getString(R.string.dialog_title_open_source_licenses);
        builder.setTitle(title);
        builder.setView(content);
        if (showCloseButton) {
            builder.setNegativeButton(R.string.dialog_button_close,
                    new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int id) {
                            dialog.dismiss();
                        }
                    });
        }

        return builder.create();
    }

    private void loadLicenses() {
        // Load asynchronously in case of a very large file.
        mLicenseLoader = new AsyncTask<Void, Void, String>() {

            @Override
            protected String doInBackground(Void... params) {
                boolean showGoogleLicenses = false;
                Bundle arguments = getArguments();
                if (arguments != null) {
                    showGoogleLicenses = arguments.getBoolean(KEY_SHOW_GOOGLE_LICENSES);
                }

                String licenses;
                if (showGoogleLicenses) {
                    licenses = GoogleApiAvailability.getInstance().getOpenSourceSoftwareLicenseInfo(getActivity());
                } else {


                    InputStream rawResource = getActivity().getResources().openRawResource(R.raw.licenses);
                    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(rawResource));

                    String line;
                    StringBuilder sb = new StringBuilder();

                    try {
                        while ((line = bufferedReader.readLine()) != null) {
                            sb.append(line);
                            sb.append("\n");
                        }
                        bufferedReader.close();
                    } catch (IOException e) {
                        // TODO You may want to include some logging here.
                    }

                    licenses = sb.toString();
                }
                return licenses;
            }

            @Override
            protected void onPostExecute(String licensesBody) {
                super.onPostExecute(licensesBody);
                if (getActivity() == null || isCancelled()) {
                    return;
                }
                mIndeterminateProgress.setVisibility(View.INVISIBLE);
                mWebView.setVisibility(View.VISIBLE);
                mWebView.loadDataWithBaseURL(null, licensesBody, "text/html", "utf-8", null);
                mLicenseLoader = null;
            }

        }.execute();
    }
}