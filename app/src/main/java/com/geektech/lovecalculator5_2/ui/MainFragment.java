package com.geektech.lovecalculator5_2.ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;

import com.geektech.lovecalculator5_2.App;
import com.geektech.lovecalculator5_2.R;
import com.geektech.lovecalculator5_2.databinding.FragmentMainBinding;
import com.geektech.lovecalculator5_2.network.LoveModel;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainFragment extends Fragment {
    private final String HOST = "love-calculator.p.rapidapi.com";
    private final String KEY = "94f5a78d20msh8c2f12c35d1395bp1fc58ajsn76a13fec0b29";
    FragmentMainBinding binding;
    NavController navController;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentMainBinding.inflate(getLayoutInflater());
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        navController = NavHostFragment.findNavController(this);
        initClick();
    }

    private void initClick() {
        binding.resultBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getDataFromLoveApi();
            }
        });
    }

    private void getDataFromLoveApi() {
        String firstName = binding.firstName.getText().toString();
        String secondName = binding.secondName.getText().toString();
        App.api.loveCalculate(firstName, secondName, HOST, KEY).enqueue(new Callback<LoveModel>() {
            @Override
            public void onResponse(Call<LoveModel> call, Response<LoveModel> response) {
                if (response.isSuccessful()) {
                    Bundle result = new Bundle();
                    assert response.body() != null;
                    String fName = response.body().firstName;
                    String sName = response.body().secondName;
                    String percentage = response.body().percentage;
                    String resultNum = response.body().result;
                    result.putString("fName", fName);
                    result.putString("sName", sName);
                    result.putString("result", resultNum);
                    result.putString("percentage", percentage);
                    navController.navigate(R.id.resultFragment, result);
                }
            }

            @Override
            public void onFailure(Call<LoveModel> call, Throwable t) {
                Toast.makeText(requireActivity(), "Error: " + t.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}