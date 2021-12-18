package com.example.swdevclass.FragmentFile;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.swdevclass.MainActivity;
import com.example.swdevclass.R;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Fragment_Login#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Fragment_Login extends Fragment{

    public Fragment_Login() {
        // Required empty public constructor
    }
    public static Fragment_Login newInstance(String param1, String param2) {
        Fragment_Login fragment = new Fragment_Login();
        return fragment;
    }

    private FirebaseAuth mAuth = null;
    private GoogleSignInClient mGoogleSignInClient;
    private static final int RC_SIGN_IN = 9001;
    private SignInButton si_b;
    private Button btn_logout;
    private View v;
    private FirebaseUser user;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.fragment_login, container, false);
        TextView tvContents = (TextView)v.findViewById(R.id.tv_contents);
        si_b = v.findViewById(R.id.si_b);
        btn_logout = v.findViewById(R.id.btn_LogOut);


        mAuth = FirebaseAuth.getInstance();
        /*
        if (mAuth.getCurrentUser() != null) {
            ((MainActivity)getActivity()).replaceFragment(FitnessList_1.newInstance());
        }
         */
        // Configure Google Sign In
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken("381296291930-mbo5tiuqb30r10npq4qd99rjdt68on39.apps.googleusercontent.com")
                .requestEmail()
                .build();
        mGoogleSignInClient = GoogleSignIn.getClient(getActivity(), gso);
        si_b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                signIn();
            }
        });

        btn_logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                signOut();
            }
        });

        // Inflate the layout for this fragment
        return v;
    }

    // [START signin]
    private void signIn() {
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            try {
                // Google Sign In was successful, authenticate with Firebase
                GoogleSignInAccount account = task.getResult(ApiException.class);
                firebaseAuthWithGoogle(account);
            } catch (ApiException e) {
                Toast.makeText(getActivity().getApplicationContext(), "login failed", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void firebaseAuthWithGoogle(GoogleSignInAccount acct) {
        AuthCredential credential = GoogleAuthProvider.getCredential(acct.getIdToken(), null);
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(getActivity(), new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            //Snackbar.make(v.findViewById(R.id.layout_main), "Authentication Successed.", Snackbar.LENGTH_SHORT).show();
                            user = mAuth.getCurrentUser();
                            updateUI(user);
                        } else {
                            // If sign in fails, display a message to the user.
                            Snackbar.make(v.findViewById(R.id.layout_main), "Authentication Failed.", Snackbar.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    private void updateUI(FirebaseUser user) { //update ui code here
        if (user != null && ((MainActivity)getActivity()).fitnessArrayListControl.isExistEmail(user.getEmail())) {

            Fragment currentFragment = MainActivity.fragmentManager.findFragmentById(R.id.layout_main);
            MainActivity.fragmentStack.push(currentFragment);

            Bundle bundle = new Bundle();
            bundle.putString("User", user.getEmail());

            FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
            Fragment_MyFitnessList  fragment_myFitnessList = new Fragment_MyFitnessList();
            fragment_myFitnessList.setArguments(bundle);
            transaction.replace(R.id.layout_main, fragment_myFitnessList);
            transaction.commit();

        }
        else{
            Toast.makeText(getContext(), "You can't access this section.",Toast.LENGTH_SHORT).show();
        }
    }
    private void signOut(){
        mGoogleSignInClient.signOut().addOnCompleteListener(getActivity(), new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                    Toast.makeText(getContext(), "로그아웃 되었습니다.", Toast.LENGTH_SHORT).show();
            }
        });

    }
}