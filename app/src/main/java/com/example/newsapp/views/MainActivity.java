package com.example.newsapp.views;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.newsapp.R;
import com.example.newsapp.model.CheckInternetConnection;
import com.example.newsapp.model.SharedPreferenceConfig;
import com.facebook.AccessToken;
import com.facebook.AccessTokenTracker;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.common.api.OptionalPendingResult;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FacebookAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.login_button)
    LoginButton loginButton;

    @BindView(R.id.userNameId)
    TextView textView;

    @BindView(R.id.btId)
    Button button;

    @BindView(R.id.loginText)
    TextView loginText;

    @BindView(R.id.google_login_button)
    SignInButton signInButton;

    @BindView(R.id.signoutId)
    Button signOutButton;

    private CallbackManager mCallbackManager;
    private FirebaseAuth firebaseAuth;
    private FirebaseAuth.AuthStateListener authStateListener;
    private AccessTokenTracker accessTokenTracker;
    private static final String TAG = "Facebookauthentication";
    public static final String MY_PREFS_NAME = "loggedIn";

    private GoogleSignInClient googleSignInClient;
    private String TAG1 = "MainActivity";
    private int RC_SIGN_IN =  1;
    private FirebaseAuth mAuth;
    private SharedPreferenceConfig sharedPreferenceConfig;

    private CheckInternetConnection checkInternetConnection;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        InitializedVariables();
        UpdateActivityUi();
        nextButtonOnClick();
        facebookLogIn(); //facebook login
        //Google signIn
        googleSignIn();

    }

    private void InitializedVariables() {
        ButterKnife.bind(MainActivity.this);
        firebaseAuth = FirebaseAuth.getInstance();
        FacebookSdk.sdkInitialize(getApplicationContext());
        mCallbackManager = CallbackManager.Factory.create();
        loginButton.setReadPermissions("email", "public_profile");
        sharedPreferenceConfig = new SharedPreferenceConfig(MainActivity.this);
        mAuth = FirebaseAuth.getInstance();
        checkInternetConnection = new CheckInternetConnection(MainActivity.this);
    }

    private void googleSignIn() {
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();
        googleSignInClient = GoogleSignIn.getClient(this, gso);

        signInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (checkInternetConnection.hasConnection()) {
                    signIn();
                }else {
                    Toast.makeText(MainActivity.this, "There is no internet connection", Toast.LENGTH_SHORT).show();
                }
            }
        });

        signOutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (checkInternetConnection.hasConnection()) {
                    googleSignInClient.signOut();
                    signOutButton.setVisibility(View.GONE);
                    loginButton.setVisibility(View.VISIBLE);
                    signInButton.setVisibility(View.VISIBLE);
                    loginText.setVisibility(View.VISIBLE);
                    textView.setVisibility(View.GONE);
                    button.setVisibility(View.GONE);
                    sharedPreferenceConfig.LoginStatus(false);
                    sharedPreferenceConfig.SignInStatus(false);
                }else {
                    Toast.makeText(MainActivity.this, "There is no internet connection", Toast.LENGTH_SHORT).show();
                }

            }
        });
    }

    private void UpdateActivityUi() {
        if(sharedPreferenceConfig.read_login_status()){
            button.setVisibility(View.VISIBLE);
            textView.setVisibility(View.VISIBLE);
            loginText.setVisibility(View.GONE);
        }else {
            button.setVisibility(View.GONE);
            textView.setVisibility(View.GONE);
            textView.setVisibility(View.GONE);
            loginText.setVisibility(View.VISIBLE);
        }

        if (sharedPreferenceConfig.read_signin_status()){
            loginButton.setVisibility(View.GONE);
            signOutButton.setVisibility(View.VISIBLE);
            button.setVisibility(View.VISIBLE);
        }else {
            textView.setVisibility(View.GONE);
            loginButton.setVisibility(View.VISIBLE);
            signOutButton.setVisibility(View.GONE);
            button.setVisibility(View.GONE);
        }
    }

    private void signIn(){
        Intent signInIntent = googleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, RC_SIGN_IN);
       // signOutButton.setVisibility(View.VISIBLE);
    }

    private void nextButtonOnClick() {
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (checkInternetConnection.hasConnection()) {
                    Intent intent = new Intent(MainActivity.this, HomePage.class);
                    startActivity(intent);
                }
                else {
                    Toast.makeText(MainActivity.this, "There is no internet connection", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void facebookLogIn() {
        loginButton.registerCallback(mCallbackManager, new FacebookCallback<LoginResult>() {

            @Override
            public void onSuccess(LoginResult loginResult) {
                Log.d(TAG,"onSuccess" + loginResult);

                handleFacebookToken(loginResult.getAccessToken());
            }

            @Override
            public void onCancel() {
                Log.d(TAG,"onCancel");
            }

            @Override
            public void onError(FacebookException error) {
                Log.d(TAG,"onError");
            }
        });

        authStateListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null){
                    updateUi(user);
                }else {
                    updateUi(null);
                }
            }
        };

        accessTokenTracker = new AccessTokenTracker() {
            @Override
            protected void onCurrentAccessTokenChanged(AccessToken oldAccessToken, AccessToken currentAccessToken) {
                if (currentAccessToken == null) {
                    firebaseAuth.signOut();
                }
            }
        };
    }

    private void handleFacebookToken(AccessToken accessToken) {
        Log.d(TAG, "handleFacebookToken" + accessToken);
        if (checkInternetConnection.hasConnection()) {
            AuthCredential credential = FacebookAuthProvider.getCredential(accessToken.getToken());
            firebaseAuth.signInWithCredential(credential).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()) {
                        Log.d(TAG, "sign in with credential: sucessful");
                        FirebaseUser user = firebaseAuth.getCurrentUser();
                        updateUi(user);
                    } else {
                        Log.d(TAG, "sign in with credential: failure", task.getException());
                        Toast.makeText(MainActivity.this, "Authentication Failed", Toast.LENGTH_SHORT).show();
                        updateUi(null);
                    }
                }
            });
        }else {
            Toast.makeText(this, "There is no internet connection", Toast.LENGTH_SHORT).show();
        }
    }

    private void updateUi(FirebaseUser user) {
        if (user != null){
            textView.setVisibility(View.VISIBLE);
            textView.setText(user.getDisplayName());
            button.setVisibility(View.VISIBLE);
            button.setVisibility(View.VISIBLE);
            textView.setText(user.getDisplayName());
            signInButton.setVisibility(View.GONE);
            loginText.setVisibility(View.GONE);
            sharedPreferenceConfig.LoginStatus(true);
        }else {
            signInButton.setVisibility(View.VISIBLE);
            button.setVisibility(View.GONE);
            textView.setVisibility(View.GONE);
            loginText.setVisibility(View.VISIBLE);
            sharedPreferenceConfig.LoginStatus(false);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        mCallbackManager.onActivityResult(requestCode, resultCode, data);
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RC_SIGN_IN){
            Log.d(TAG, "onActivityResult called" + requestCode + data);
            if (resultCode == RESULT_OK) {
                Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
                handleSignInResult(task);
            }

        }
    }

    //for handling signin result
    private void handleSignInResult( Task<GoogleSignInAccount> task){
        try {
            GoogleSignInAccount acc = task.getResult(ApiException.class);
            Log.d(TAG, "Signed in Sucessfully", task.getException());
            Toast.makeText(this, "Signed in Sucessfully", Toast.LENGTH_SHORT).show();
            FireBaseGoogleAuth(acc);
        }catch (Exception ex){
            Log.d(TAG, "Signed in Failed", task.getException());
            Toast.makeText(this, "Signed in Failed", Toast.LENGTH_SHORT).show();
            FireBaseGoogleAuth(null);
        }

    }

    //for handling google signin
    private void FireBaseGoogleAuth( GoogleSignInAccount acc){
        AuthCredential authCredential = GoogleAuthProvider.getCredential(acc.getIdToken(), null);
        mAuth.signInWithCredential(authCredential).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()){
                    Log.d(TAG, "success", task.getException());
                    Toast.makeText(MainActivity.this, "Sucessfull", Toast.LENGTH_SHORT).show();
                    FirebaseUser mUser = mAuth.getCurrentUser();
                    GoogleSignInAccount ac = GoogleSignIn.getLastSignedInAccount(getApplicationContext());
                    Toast.makeText(MainActivity.this, ac.getDisplayName(), Toast.LENGTH_SHORT).show();
                    //update ui
                    sharedPreferenceConfig.LoginStatus(true);
                    sharedPreferenceConfig.SignInStatus(true);
                    loginButton.setVisibility(View.GONE);
                    loginText.setVisibility(View.GONE);
                    button.setVisibility(View.VISIBLE);
                    signOutButton.setVisibility(View.VISIBLE);
                    textView.setVisibility(View.VISIBLE);
                    textView.setText(mUser.getDisplayName());
                }else{
                    Log.d(TAG, "failed- task", task.getException());
                    Toast.makeText(MainActivity.this, "Failed", Toast.LENGTH_SHORT).show();
                    loginButton.setVisibility(View.VISIBLE);
                    loginText.setVisibility(View.VISIBLE);
                    signOutButton.setVisibility(View.GONE);
                    button.setVisibility(View.GONE);
                    textView.setVisibility(View.GONE);
                    sharedPreferenceConfig.LoginStatus(false);
                    sharedPreferenceConfig.SignInStatus(false);
                }
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        if (!sharedPreferenceConfig.read_signin_status()){
            Log.d(TAG, "onBackPressed called");

        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        firebaseAuth.addAuthStateListener(authStateListener);

    }

    @Override
    protected void onStop() {
        super.onStop();
        if (authStateListener != null){
            firebaseAuth.removeAuthStateListener(authStateListener);
        }
    }
}
