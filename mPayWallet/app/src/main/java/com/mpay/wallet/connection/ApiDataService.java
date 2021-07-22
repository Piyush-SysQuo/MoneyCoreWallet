package com.mpay.wallet.connection;


import androidx.annotation.NonNull;

import com.mpay.wallet.Utils.Constants;
import com.mpay.wallet.View.Activity.Login.model.LoginModel;
import com.mpay.wallet.View.Activity.Login.model.LoginResponse;
import com.mpay.wallet.View.Activity.Signup.model.EmailCheckResponse;
import com.mpay.wallet.View.Activity.Signup.model.SignUpModel;
import com.mpay.wallet.View.Activity.Signup.model.SignUpResponse;
import com.mpay.wallet.View.Activity.Signup.model.ValidateEmailModel;

import org.json.JSONArray;

import java.util.HashMap;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

public interface ApiDataService {

    // User Registration
    @Multipart
    @POST("api/client/register")
    Call<SignUpResponse> signUpAPI(@Part(Constants.FIRST_NAME) String firstName,
                                   @Part(Constants.MIDDLE_NAME) String middleName,
                                   @Part(Constants.LAST_NAME) String lastName,
                                   @Part(Constants.PR_MOBLINE) String prPhone1,
                                   @Part(Constants.PASSWORD) String password,
                                   @Part(Constants.EMAIL) String email,
                                   @Part(Constants.DOB) String dob,
                                   @Part(Constants.IDTYPE) String idType,
                                   @Part(Constants.IDNUMBER) String idNumber,
                                   //@Part(Constants.INSTITUTIONCODE) String institutionCode,
                                   @Part MultipartBody.Part  front_image,
                                   @Part MultipartBody.Part  back_image);

    @POST("api/client/validate")
    Call<EmailCheckResponse> checkEmail(@Body ValidateEmailModel validateEmailModel);

    @POST("api/client/passwordReset")
    Call<EmailCheckResponse> passwordReset(@Body HashMap<String, String> fields);

    @POST("api/client/validate")
    Call<EmailCheckResponse> checkPhone(@Body HashMap<String, String> fields);
    @POST("api/client/login")
    Call<LoginResponse> login(@Body LoginModel loginModel);
}
