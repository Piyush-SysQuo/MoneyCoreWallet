package com.mpay.wallet.View.Fragment.Notification.View;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.material.snackbar.Snackbar;
import com.mpay.wallet.R;
import com.mpay.wallet.View.Fragment.MyQRCode.MyQRCodeFragment;
import com.mpay.wallet.View.Fragment.Notification.adapter.NotificationTransactionAdapter;
import com.mpay.wallet.View.Fragment.Notification.model.NotificationItemAdapter;
import com.mpay.wallet.View.Fragment.home.adapter.RecentTransactionAdapter;
import com.mpay.wallet.View.Fragment.home.model.ItemAdapter;
import com.mpay.wallet.View.Fragment.home.view.HomeFragment;

import java.util.ArrayList;
import java.util.List;

import rx.Notification;

public class NotificationFragment extends Fragment {
    private View view;
    private TextView Tv_Noti_ClearAll;
    private ImageView Iv_noti_Back;
    RecyclerView RecycleV_Notification;
    private List<NotificationItemAdapter> mList = new ArrayList<>();
    private NotificationTransactionAdapter mAdapter;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view =  inflater.inflate(R.layout.fragment_notification, container, false);

        Initilization();
        return view;
    }
    private void Initilization()
    {
        RecycleV_Notification     = view.findViewById(R.id.RecycleV_Notification);
        Tv_Noti_ClearAll     = view.findViewById(R.id.Tv_Noti_ClearAll);
        Iv_noti_Back     = view.findViewById(R.id.Iv_noti_Back);

        Back();
        addList();
        adapter();

        Iv_noti_Back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().getSupportFragmentManager().popBackStack();
            }
        });
    }
    private void addList(){
        try {
            NotificationItemAdapter itemAdapter = new NotificationItemAdapter();
            itemAdapter.setProfilePic(R.drawable.ic_transaction_blue);
            itemAdapter.setHeading("Transaction");
            itemAdapter.setAmount("Amount : €200");
            itemAdapter.setDate("10:30 AM");
            itemAdapter.setTransaction_status("Transcation complete!");
            mList.add(itemAdapter);

            itemAdapter = new NotificationItemAdapter();
            itemAdapter.setProfilePic(R.drawable.ic_transaction_white);
            itemAdapter.setHeading("Transaction");
            itemAdapter.setAmount("Amount : €100");
            itemAdapter.setDate("10:00 AM");
            itemAdapter.setTransaction_status("Transcation complete!");
            mList.add(itemAdapter);

            itemAdapter = new NotificationItemAdapter();
            itemAdapter.setProfilePic(R.drawable.ic_transaction_blue);
            itemAdapter.setHeading("Transaction");
            itemAdapter.setAmount("Amount : €250");
            itemAdapter.setDate("09:30 AM");
            itemAdapter.setTransaction_status("Transcation complete!");
            mList.add(itemAdapter);

            itemAdapter = new NotificationItemAdapter();
            itemAdapter.setProfilePic(R.drawable.ic_transaction_blue);
            itemAdapter.setHeading("Transaction");
            itemAdapter.setAmount("Amount : €300");
            itemAdapter.setDate("08:30 AM");
            itemAdapter.setTransaction_status("Transcation complete!");
            mList.add(itemAdapter);
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    private void adapter(){
        try {
            mAdapter = new NotificationTransactionAdapter(mList, getActivity());
            RecycleV_Notification.setLayoutManager(new LinearLayoutManager(getActivity(), RecyclerView.VERTICAL,false));
            RecycleV_Notification.setAdapter(mAdapter);
            mAdapter.notifyDataSetChanged();
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    public void Back()
    {
        view.setFocusableInTouchMode(true);
        view.requestFocus();
        view.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if( keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_UP) {
                    getFragmentManager().popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
                    return true;
                }
                return false;
            }
        });
    }
}