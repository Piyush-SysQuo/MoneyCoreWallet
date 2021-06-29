package com.mpay.wallet.View.Fragment.ContactPhone;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.provider.ContactsContract;
import android.provider.MediaStore;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;


import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.single.PermissionListener;
import com.mpay.wallet.R;
import com.mpay.wallet.View.Fragment.Confirm_Transfer.ConfirmTransferFragment;
import com.mpay.wallet.View.Fragment.Transfer.TransferFragment;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.net.Uri;

import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Locale;
import java.util.Objects;


public class PhoneContactFragment extends Fragment {
    ListView ListContactPhone;
    ImageView Iv_Contact_Back;
    EditText Edit_Contact_Search;
    View view;
    String MOBILE_NO = null;
    String NAME = null;
    ArrayList<ContactList> MaleList = new ArrayList<ContactList>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_phone_contact, container, false);
        Initilization();
        return view;
    }
    private void Initilization(){
        Edit_Contact_Search    = view.findViewById(R.id.Edit_Contact_Search);
        Iv_Contact_Back    = view.findViewById(R.id.Iv_Contact_Back);
        ListContactPhone    = view.findViewById(R.id.ListContactPhone);

        Iv_Contact_Back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().getSupportFragmentManager().popBackStack();
            }
        });
        CheckPermission1();

        ListContactPhone.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                TextView leftTextView = (TextView) view.findViewById(R.id.Tv_Adap_Contact_Number);
                TextView Name = (TextView) view.findViewById(R.id.Tv_Adap_Contact_Name);
                MOBILE_NO = leftTextView.getText().toString().trim();
                NAME = Name.getText().toString().trim();
                TransferFragment transferFragment = new TransferFragment();
                Bundle bundle = new Bundle();
                bundle.putString("MOBILE_NO", MOBILE_NO);
                bundle.putString("NAME", NAME);
                bundle.putString("TRANSACTION_TYPE", "TRANSFER");
                transferFragment.setArguments(bundle);
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.containerLayout, transferFragment).commit();
            }
        });


    }

    public void CheckPermission1()
    {
        Dexter.withContext(getActivity()).withPermission(Manifest.permission.WRITE_CONTACTS).withListener(new PermissionListener() {
            @Override
            public void onPermissionGranted(PermissionGrantedResponse permissionGrantedResponse) {
                CheckPermission2();
            }

            @Override
            public void onPermissionDenied(PermissionDeniedResponse permissionDeniedResponse) {

            }

            @Override
            public void onPermissionRationaleShouldBeShown(PermissionRequest permissionRequest, PermissionToken permissionToken) {
                permissionToken.continuePermissionRequest();
            }
        }).check();
    }
    public void CheckPermission2()
    {
        Dexter.withContext(getActivity()).withPermission(Manifest.permission.READ_CONTACTS).withListener(new PermissionListener() {
            @Override
            public void onPermissionGranted(PermissionGrantedResponse permissionGrantedResponse) {
                getView(view);
            }

            @Override
            public void onPermissionDenied(PermissionDeniedResponse permissionDeniedResponse) {

            }

            @Override
            public void onPermissionRationaleShouldBeShown(PermissionRequest permissionRequest, PermissionToken permissionToken) {
                permissionToken.continuePermissionRequest();
            }
        }).check();
    }
    public void getView(View v)  {
        try {
            Cursor cursor = getActivity().getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null, null, null, null);
            MaleList.clear();
            while(Objects.requireNonNull(cursor).moveToNext())
            {
                JSONObject jsonObject = new JSONObject();
                String name = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME));
                String number = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
                String _id = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone._ID));
                ContactList itemsModel = new ContactList(name,number);
                MaleList.add(itemsModel);
            }
            CustomAdapter adapter = null;
            Collections.sort(MaleList);
            adapter = new CustomAdapter(MaleList,getActivity());
            ListContactPhone.setAdapter(adapter);

            CustomAdapter finalAdapter = adapter;
            Edit_Contact_Search.addTextChangedListener(new TextWatcher() {

                @Override
                public void afterTextChanged(Editable s) {
                    // TODO Auto-generated method stub
                    /*String text = Edit_Contact_Search.getText().toString().toLowerCase(Locale.getDefault());
                    finalAdapter.getFilter().filter(text);*/
                    finalAdapter.getFilter().filter(s.toString());
                }

                @Override
                public void beforeTextChanged(CharSequence arg0, int arg1,
                                              int arg2, int arg3) {
                    // TODO Auto-generated method stub
                }

                @Override
                public void onTextChanged(CharSequence arg0, int arg1, int arg2,
                                          int arg3) {
                    // TODO Auto-generated method stub
                }
            });
        }catch (Exception e){
            e.printStackTrace();
        }
    }
//------------------------------------------------------------------------------------------------\\
//------------------------------------------------------------------------------------------------//
//------------------------------------------------------------------------------------------------\\
//------------------------------------------------------------------------------------------------//
    public class CustomAdapter extends BaseAdapter implements Filterable {

        private List<ContactList> ContactListsl_Orignal;
        private List<ContactList> ContactListListFiltered;
        private Context context;

        public CustomAdapter(List<ContactList> ContactListsl, Context context) {
            this.ContactListsl_Orignal = ContactListsl;
            this.ContactListListFiltered = ContactListsl;
            this.context = context;
        }



        @Override
        public int getCount() {
            return ContactListListFiltered.size();
        }

        @Override
        public Object getItem(int position) {
            return ContactListListFiltered.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            View view = getLayoutInflater().inflate(R.layout.adapter_layout_contactphone,null);


            TextView names = view.findViewById(R.id.Tv_Adap_Contact_Name);
            TextView number = view.findViewById(R.id.Tv_Adap_Contact_Number);
            ImageView imageView = view.findViewById(R.id.Img_UserContact);

            names.setText(ContactListListFiltered.get(position).getName());
            number.setText(ContactListListFiltered.get(position).getNumber());
    //        imageView.setImageResource(ContactListListFiltered.get(position).getImages());

            /*view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Log.e("main activity","item clicked");
                    startActivity(new Intent(MainActivity.this,ItemsPreviewActivity.class).putExtra("items",ContactListListFiltered.get(position)));

                }
            });*/

            return view;
        }




        /*public Filter getFilter() {
            Filter filter = new Filter() {
                @Override
                protected FilterResults performFiltering(CharSequence constraint) {

                    FilterResults filterResults = new FilterResults();
                    if(constraint == null || constraint.length() == 0){
                        filterResults.count = ContactListsl.size();
                        filterResults.values = ContactListsl;

                    }else{
                        List<ContactList> resultsModel = new ArrayList<>();
                        String searchStr = constraint.toString().toLowerCase();

                        for(ContactList ContactList:ContactListsl){
                            if(ContactList.getName().contains(searchStr) || ContactList.getNumber().contains(searchStr)){
                                resultsModel.add(ContactList);

                            }
                            filterResults.count = resultsModel.size();
                            filterResults.values = resultsModel;
                        }


                    }

                    return filterResults;
                }

                @Override
                protected void publishResults(CharSequence constraint, FilterResults results) {

                    ContactListListFiltered = (List<ContactList>) results.values;
                    notifyDataSetChanged();

                }
            };
            return filter;
        }*/
        @Override
        public Filter getFilter() {
            Filter filter = new Filter() {

                @SuppressWarnings("unchecked")
                @Override
                protected void publishResults(CharSequence constraint,FilterResults results) {

                    ContactListListFiltered = (ArrayList<ContactList>) results.values; // has the filtered values
                    notifyDataSetChanged();  // notifies the data with new filtered values
                }

                @Override
                protected FilterResults performFiltering(CharSequence constraint) {
                    FilterResults results = new FilterResults();        // Holds the results of a filtering operation in values
                    ArrayList<ContactList> FilteredArrList = new ArrayList<ContactList>();

                    if (ContactListsl_Orignal == null) {
                        ContactListsl_Orignal = new ArrayList<ContactList>(ContactListListFiltered); // saves the original data in mOriginalValues
                    }

                    /********
                     *
                     *  If constraint(CharSequence that is received) is null returns the mOriginalValues(Original) values
                     *  else does the Filtering and returns FilteredArrList(Filtered)
                     *
                     ********/
                    if (constraint == null || constraint.length() == 0) {

                        // set the Original result to return
                        results.count = ContactListsl_Orignal.size();
                        results.values = ContactListsl_Orignal;
                    } else {
                        constraint = constraint.toString().toLowerCase();
                        for (int i = 0; i < ContactListsl_Orignal.size(); i++) {
                            String data = ContactListsl_Orignal.get(i).getName();
                            if (data.toLowerCase().startsWith(constraint.toString())) {
                                FilteredArrList.add(new ContactList(ContactListsl_Orignal.get(i).getName(),ContactListsl_Orignal.get(i).getNumber()));
                            }
                        }
                        // set the Filtered result to return
                        results.count = FilteredArrList.size();
                        results.values = FilteredArrList;
                    }
                    return results;
                }
            };
            return filter;
        }
    }
//------------------------------------------------------------------------------------------------\\
//------------------------------------------------------------------------------------------------//
}