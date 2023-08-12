package com.omt.omtkosher;

import static android.content.Context.MODE_PRIVATE;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.material.button.MaterialButton;
import com.paypal.android.sdk.payments.PayPalConfiguration;
import com.paypal.android.sdk.payments.PayPalPayment;
import com.paypal.android.sdk.payments.PayPalService;
import com.paypal.android.sdk.payments.PaymentActivity;
import com.paypal.android.sdk.payments.PaymentConfirmation;

import org.json.JSONException;
import org.json.JSONObject;

import java.math.BigDecimal;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link PaymentFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class PaymentFragment extends Fragment {

    public static final String clientKey = "AchCx6GXbP3tBUJvPlyxD3zr8a1Soflq7x4vmg53kbLPsAUyKJlOVq0KfdYNAP4wnQV8U_E0b8HC2lGd";
    public static final int PAYPAL_REQUEST_CODE = 123;
    SharedPreferences.Editor myEdit;
    SharedPreferences sharedPreferences;

    // Paypal Configuration Object
    private static final PayPalConfiguration config = new PayPalConfiguration()
            .environment(PayPalConfiguration.ENVIRONMENT_PRODUCTION)
            .clientId(clientKey);

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public PaymentFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment PaymentFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static PaymentFragment newInstance(String param1, String param2) {
        PaymentFragment fragment = new PaymentFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_payment, container, false);

        NavHostFragment navHostFragment =
                (NavHostFragment) requireActivity().getSupportFragmentManager()
                        .findFragmentById(R.id.nav_host_fragment);
        assert navHostFragment != null;
        NavController navController = navHostFragment.getNavController();

        MaterialButton btn = v.findViewById(R.id.next3);
        ImageView paybtn = v.findViewById(R.id.paypalbtn);

        paybtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getPayment();
            }
        });

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean isPaymentDone = sharedPreferences.getBoolean("Payment",false);
                if(isPaymentDone){
                    navController.navigate(R.id.registrationFragment);
                }
                else{
                    Toast.makeText(requireActivity(), "Make the Payment - בצע את התשלום", Toast.LENGTH_SHORT).show();
                }

            }
        });

         sharedPreferences = requireActivity().getSharedPreferences("MySharedPref",MODE_PRIVATE);

         myEdit = sharedPreferences.edit();





        return v;
    }


    private void getPayment() {
        PayPalPayment payment = new PayPalPayment(new BigDecimal("27.36"), "USD", "Payment Fee",
                PayPalPayment.PAYMENT_INTENT_SALE);


        Intent intent = new Intent(requireContext(), PaymentActivity.class);

        intent.putExtra(PayPalService.EXTRA_PAYPAL_CONFIGURATION, config);
        intent.putExtra(PaymentActivity.EXTRA_PAYMENT, payment);

        startActivityForResult(intent, PAYPAL_REQUEST_CODE);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // If the result is from paypal
        if (requestCode == PAYPAL_REQUEST_CODE) {

            // If the result is OK i.e. user has not canceled the payment
            if (resultCode == Activity.RESULT_OK) {

                // Getting the payment confirmation
                assert data != null;
                PaymentConfirmation confirm = data.getParcelableExtra(PaymentActivity.EXTRA_RESULT_CONFIRMATION);

                // if confirmation is not null
                if (confirm != null) {
                    try {
                        myEdit.putBoolean("Payment", true);
                        myEdit.apply();
                        // Getting the payment details
                        String paymentDetails = confirm.toJSONObject().toString(4);
                        // on below line we are extracting json response and displaying it in a text view.
                        JSONObject payObj = new JSONObject(paymentDetails);
                        String payID = payObj.getJSONObject("response").getString("id");
                        String state = payObj.getJSONObject("response").getString("state");
                        Log.i("Payment",state+"id"+payID);
                        //paymentTV.setText("Payment " + state + "\n with payment id is " + payID);
                    } catch (JSONException e) {
                        // handling json exception on below line
                        Log.e("Error", "an extremely unlikely failure occurred: ", e);
                    }
                }
            } else if (resultCode == Activity.RESULT_CANCELED) {
                Toast.makeText(requireActivity(), "Payment Cancelled", Toast.LENGTH_SHORT).show();
            } else if (resultCode == PaymentActivity.RESULT_EXTRAS_INVALID) {
                Toast.makeText(requireActivity(), "Payment Invalid", Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);



        ((EnrollmentActivity) requireActivity()).binding.included1.tv.setBackgroundResource(R.drawable.circle);
        ((EnrollmentActivity) requireActivity()).binding.included2.tv.setBackgroundResource(R.drawable.circle);
        ((EnrollmentActivity) requireActivity()).binding.included3.tv.setBackgroundResource(R.drawable.circle);
        ((EnrollmentActivity) requireActivity()).binding.included4.tv.setBackgroundResource(R.drawable.circleundone);


        // Use the view as needed
        ((EnrollmentActivity) requireActivity()).binding.space1.setBackgroundColor(Color.parseColor("#3891f5"));
        ((EnrollmentActivity) requireActivity()).binding.space2.setBackgroundColor(Color.parseColor("#3891f5"));
        ((EnrollmentActivity) requireActivity()).binding.space3.setBackgroundColor(Color.parseColor("#9ec4f1"));

    }
}