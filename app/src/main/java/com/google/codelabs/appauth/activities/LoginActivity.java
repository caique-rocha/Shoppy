package com.google.codelabs.appauth.activities;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.SignInButton;
import com.google.codelabs.appauth.R;
import com.google.codelabs.appauth.fragments.HomeFragment;
import com.squareup.picasso.Picasso;

import net.openid.appauth.AuthState;
import net.openid.appauth.AuthorizationException;
import net.openid.appauth.AuthorizationRequest;
import net.openid.appauth.AuthorizationResponse;
import net.openid.appauth.AuthorizationService;
import net.openid.appauth.AuthorizationServiceConfiguration;
import net.openid.appauth.TokenResponse;

import org.json.JSONException;
import org.json.JSONObject;

import de.hdodenhof.circleimageview.CircleImageView;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import timber.log.Timber;

import static com.google.codelabs.appauth.fragments.ProfileFragment.IMAGE_REQUEST_CODE;


public class LoginActivity extends AppCompatActivity {

    private static final String SHARED_PREFERENCES_NAME = "AuthStatePreference";
    private static final String AUTH_STATE = "AUTH_STATE";
    private static final String USED_INTENT = "USED_INTENT";
   String TAG_DATA = "LoginActivity";


    // state
    AuthState mAuthState;

    // views
   SignInButton mAuthorize;
    Button mMakeApiCall;
    Button mSignOut;
    Button mUseInfo;
    TextView mGivenName;
    TextView mFamilyName;
    TextView mFullName;
    CircleImageView mProfileView;

    static String imageUri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mAuthorize = findViewById(R.id.authorize);
        mMakeApiCall = findViewById(R.id.makeApiCall);

        mSignOut = findViewById(R.id.signOut);
        mGivenName = findViewById(R.id.givenName);
        mFamilyName = findViewById(R.id.familyName);
        mFullName = findViewById(R.id.fullName);
        mProfileView = findViewById(R.id.profileImage);
        mUseInfo=findViewById(R.id.use_info);

        enablePostAuthorizationFlows();

        // wire click listeners
        mAuthorize.setOnClickListener(new AuthorizeListener());

    }
        private void enablePostAuthorizationFlows() {
            mAuthState = restoreAuthState();
            if (mAuthState != null && mAuthState.isAuthorized()) {
                if (mMakeApiCall.getVisibility() == View.GONE) {
                    mMakeApiCall.setVisibility(View.VISIBLE);
                    mMakeApiCall.setOnClickListener(new MakeApiCallListener(this,
                                                     mAuthState,
                                                     new AuthorizationService(this)));
                }
                if (mSignOut.getVisibility() == View.GONE) {
                    mSignOut.setVisibility(View.VISIBLE);
                    mSignOut.setOnClickListener(new SignOutListener
                                                   (LoginActivity.this));
                }
                if (mUseInfo.getVisibility()== View.GONE) {
                    mUseInfo.setVisibility(View.VISIBLE);
                    mUseInfo.setOnClickListener(v -> {
                        Intent intent=new Intent(LoginActivity.this,
                                                   MainActivity.class);
                        intent.putExtra("image",imageUri);
                        Toast.makeText(this, imageUri, Toast.LENGTH_SHORT).show();
                        intent.setAction(MainActivity.ACTION);
                        startActivity(intent);
                    });
                }

            } else {
                mMakeApiCall.setVisibility(View.GONE);
                mSignOut.setVisibility(View.GONE);
            }
        }

        private void handleAuthorizationResponse(@NonNull Intent intent) {

            // code from the step 'Handle the Authorization Response' goes here.
            AuthorizationResponse response = AuthorizationResponse.fromIntent(intent);
            AuthorizationException error = AuthorizationException.fromIntent(intent);
            final AuthState authState = new AuthState(response, error);

            if (response != null) {
                Log.i("MainActivity", String.format("Handle Authorization Response %s", authState.toJsonString()));
                AuthorizationService service = new AuthorizationService(this);
                service.performTokenRequest(response.createTokenExchangeRequest(), new AuthorizationService.TokenResponseCallback() {

                    @Override
                    public void onTokenRequestCompleted(@Nullable TokenResponse tokenResponse, @Nullable AuthorizationException e) {
                        if (e != null) {
                            Log.w("MainActivity", "Token Exchange Failed", e);
                        } else {
                            if (tokenResponse != null) {
                                authState.update(tokenResponse, e);
                                persistAuthState(authState);
                                Log.i("MainActivity", String.format("Token Response [Access Token:%s,ID Token :%s]", tokenResponse.accessToken, tokenResponse.idToken));
                            }
                        }
                    }
                });
            }


        }

        @Override
        protected void onNewIntent(Intent intent) {
            super.onNewIntent(intent);
            checkIntent(intent);
        }

        private void checkIntent(Intent intent) {
            if (intent != null) {
                String action = intent.getAction();
                switch (action) {
                    case "com.google.codelabs.appauth.HANDLE_AUTHORIZATION_RESPONSE":
                        if (!intent.hasExtra(USED_INTENT)) {
                            handleAuthorizationResponse(intent);
                            intent.putExtra(USED_INTENT, true);
                        }
                        break;

                    default:
                        //do nothing
                }

            }

        }

        @Override
        protected void onStart() {
            super.onStart();
            checkIntent(getIntent());
        }

        private void persistAuthState(@NonNull AuthState authState) {
            getSharedPreferences(SHARED_PREFERENCES_NAME, Context.MODE_PRIVATE).edit()
                    .putString(AUTH_STATE, authState.toJsonString())
                    .apply();
            enablePostAuthorizationFlows();
        }

        private void clearAuthState() {
            getSharedPreferences(SHARED_PREFERENCES_NAME, Context.MODE_PRIVATE)
                    .edit()
                    .remove(AUTH_STATE)
                    .apply();
        }

        @Nullable
        private AuthState restoreAuthState() {
            String jsonString = getSharedPreferences(SHARED_PREFERENCES_NAME, Context.MODE_PRIVATE)
                    .getString(AUTH_STATE, null);
            if (!TextUtils.isEmpty(jsonString)) {
                try {
                    return AuthState.fromJson(jsonString);
                } catch (JSONException jsonException) {
                    // should never happen
                }
            }
            return null;
        }

        /**
         * Kicks off the authorization flow.
         */
        public static class AuthorizeListener implements Button.OnClickListener {
            @Override
            public void onClick(View view) {
                AuthorizationServiceConfiguration serviceConfiguration = new AuthorizationServiceConfiguration(
                        Uri.parse("https://accounts.google.com/o/oauth2/v2/auth") /* auth endpoint */,
                        Uri.parse("https://www.googleapis.com/oauth2/v4/token") /* token endpoint */

                );
                String clientId = "511828570984-fuprh0cm7665emlne3rnf9pk34kkn86s.apps.googleusercontent.com";
                Uri redirectUri = Uri.parse("com.google.codelabs.appauth:/oauth2callback");
                AuthorizationRequest.Builder builder = new AuthorizationRequest.Builder(
                        serviceConfiguration,
                        clientId,
                        AuthorizationRequest.RESPONSE_TYPE_CODE,
                        redirectUri
                );
                builder.setScopes("profile");
                AuthorizationRequest request = builder.build();
                // and the step 'Perform the Authorization Request' goes here.
                AuthorizationService authorizationService = new AuthorizationService(view.getContext());
                String action = "com.google.codelabs.appauth.HANDLE_AUTHORIZATION_RESPONSE";
                Intent postAuthorizationIntent = new Intent(action);
                PendingIntent pendingIntent = PendingIntent.getActivity(view.getContext(), request.hashCode(), postAuthorizationIntent, 0);
                authorizationService.performAuthorizationRequest(request, pendingIntent);


            }
        }

        public static class SignOutListener implements Button.OnClickListener {

            private final LoginActivity mMainActivity;

            public SignOutListener(@NonNull LoginActivity mainActivity) {
                mMainActivity = mainActivity;
            }

            @Override
            public void onClick(View view) {
                mMainActivity.mAuthState = null;
                mMainActivity.clearAuthState();
                mMainActivity.enablePostAuthorizationFlows();
            }
        }

        public static class MakeApiCallListener implements Button.OnClickListener {

            private final LoginActivity mMainActivity;
            private AuthState mAuthState;
            private AuthorizationService mAuthorizationService;

            public MakeApiCallListener(@NonNull LoginActivity mainActivity, @NonNull AuthState authState, @NonNull AuthorizationService authorizationService) {
                mMainActivity = mainActivity;
                mAuthState = authState;
                mAuthorizationService = authorizationService;
            }

            @Override
            public void onClick(View view) {

                // code from the section 'Making API Calls' goes here
                mAuthState.performActionWithFreshTokens(mAuthorizationService, new AuthState.AuthStateAction() {
                    @SuppressLint("StaticFieldLeak")
                    @Override
                    public void execute(@Nullable String accessToken, @Nullable String idToken, @Nullable AuthorizationException ex) {
                        new AsyncTask<String, Void, JSONObject>() {

                            @Override
                            protected JSONObject doInBackground(String... tokens) {
                                OkHttpClient client = new OkHttpClient();
                                Request request = new Request.Builder()
                                        .url("https://www.googleapis.com/oauth2/v3/userinfo")
                                        .addHeader("Authorization", String.format("Bearer %s", tokens[0]))
                                        .build();
                                try {
                                    Response response = client.newCall(request).execute();
                                    String jsonBody = response.body().string();
                                    Log.d("MainActivity", String.format("user Info Response %s", jsonBody));
                                    return new JSONObject(jsonBody);
                                } catch (Exception exception) {
                                    Log.w("MainActivity", ex);
                                }
                                return null;
                            }

                            @Override
                            protected void onPostExecute(JSONObject userInfo) {
                                if (userInfo != null) {
                                    String fullName = userInfo.optString("name", null);
                                    String givenName = userInfo.optString("given_name", null);
                                    String familyName = userInfo.optString("family_name", null);
                                    imageUri = userInfo.optString("picture", null);
                                    if (!TextUtils.isEmpty(imageUri)) {
                                        Picasso.get()
                                                .load(imageUri)
                                                .placeholder(R.drawable.profile)
                                                .into(mMainActivity.mProfileView);
                                    }
                                    if (!TextUtils.isEmpty(fullName)) {
                                        mMainActivity.mFullName.setText(fullName);
                                    }
                                    if (!TextUtils.isEmpty(givenName)) {
                                        mMainActivity.mGivenName.setText(givenName);
                                    }
                                    if (!TextUtils.isEmpty(familyName)) {
                                        mMainActivity.mFamilyName.setText(familyName);
                                    }

                                    String message;
                                    if (userInfo.has("error")) {
                                        message = String.format("%s [%s]", mMainActivity.getString(R.string.request_failed), userInfo.optString("error_description", "No description"));
                                    } else {
                                        message = mMainActivity.getString(R.string.request_complete);
                                    }
                                    Snackbar.make(mMainActivity.mProfileView, message, Snackbar.LENGTH_SHORT)
                                            .show();


                                }
                            }
                        }.execute(accessToken);
                    }
                });

            }
        }



    }






