package com.mpay.wallet.View.Fragment.Space.View;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.mpay.wallet.R;
import com.mpay.wallet.Utils.Constants;
import com.mpay.wallet.Utils.SharedPreferenceAmount;
import com.mpay.wallet.View.Fragment.MyQRCode.MyQRCodeFragment;
import com.mpay.wallet.View.Fragment.Space.Adapter.SpaceAdapter;
import com.mpay.wallet.View.Fragment.Space.InterFace.SpaceTransferInterFace;
import com.mpay.wallet.View.Fragment.Space.Model.SpaceItemAdapter;
import com.mpay.wallet.View.Fragment.Transfer.TransferFragment;
import com.mpay.wallet.View.Fragment.TransferFromSpace.SpaceToSpaceFragment;
import com.mpay.wallet.View.Fragment.TransferFromSpace.SpaceToWalletFragment;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;

public class SpaceFragment extends Fragment implements SpaceTransferInterFace {

    private static String AMOUNT      = null;
    private static String SPACE_NAME  = null;
    private static String POSITION    = null;

    private String mParam1;
    private String mParam2;

    private View view;
    TextView Tv_Space_Tot_Amt;
    ImageView Iv_Space_Back;
    RecyclerView Recycler_Space;
    LinearLayout Layout_AddSpace;

    NumberFormat formatter;

    private List<SpaceItemAdapter> mList = new ArrayList<>();
    private SpaceAdapter mAdapter;
//------------------------------------------------------------------------------------------------\\
//------------------------------------------------------------------------------------------------//
    public SpaceFragment() {
        // Required empty public constructor
    }
//------------------------------------------------------------------------------------------------\\
//------------------------------------------------------------------------------------------------//
//------------------------------------------------------------------------------------------------\\
//------------------------------------------------------------------------------------------------//
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(AMOUNT);
            mParam2 = getArguments().getString(SPACE_NAME);
        }
    }
//------------------------------------------------------------------------------------------------\\
//------------------------------------------------------------------------------------------------//
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_space, container, false);
        Initilization();
        return view;
    }
//------------------------------------------------------------------------------------------------\\
//------------------------------------------------------------------------------------------------//
    private void Initilization() {
        formatter = new DecimalFormat("#0.00");
        Iv_Space_Back = view.findViewById(R.id.Iv_Space_Back);
        Tv_Space_Tot_Amt = view.findViewById(R.id.Tv_Space_Tot_Amt);
        Recycler_Space = view.findViewById(R.id.Recycler_Space);
        Layout_AddSpace = view.findViewById(R.id.Layout_AddSpace);

        double TotAmt = Double.parseDouble(SharedPreferenceAmount.getInstance(getActivity()).getString_TotAmount(Constants.TOTAL_AMOUNT).toString());
        Tv_Space_Tot_Amt.setText("€"+formatter.format(TotAmt));
        addList();
        adapter();

        Layout_AddSpace.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.containerLayout, new SpaceCreateFragment()).addToBackStack("Space").commit();
            }
        });
        Iv_Space_Back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().getSupportFragmentManager().popBackStack();
            }
        });
    }
//------------------------------------------------------------------------------------------------\\
//------------------------------------------------------------------------------------------------//
    private void addList(){
        mList.clear();
        SpaceItemAdapter itemAdapter = new SpaceItemAdapter();
        itemAdapter.setName("Space-1");
        itemAdapter.setAmount("€500.00");
        mList.add(itemAdapter);


        itemAdapter = new SpaceItemAdapter();
        itemAdapter.setName("Space-2");
        itemAdapter.setAmount("€300.00");
        mList.add(itemAdapter);


        itemAdapter = new SpaceItemAdapter();
        itemAdapter.setName("Space-3");
        itemAdapter.setAmount("€250.00");
        mList.add(itemAdapter);
    }
//------------------------------------------------------------------------------------------------\\
//------------------------------------------------------------------------------------------------//
    private void adapter(){
        try {
            mAdapter = new SpaceAdapter(mList, getActivity(), this);
            Recycler_Space.setLayoutManager(new LinearLayoutManager(getActivity(), RecyclerView.VERTICAL,false));
            Recycler_Space.setAdapter(mAdapter);
            mAdapter.notifyDataSetChanged();
        }catch (Exception e){
            e.printStackTrace();
        }
    }
//------------------------------------------------------------------------------------------------\\
//------------------------------------------------------------------------------------------------//
    @Override
    public void transferOnClicked(int position, String Amount, String SpaceName) {

        AMOUNT = Amount;
        SPACE_NAME = SpaceName;
        POSITION = String.valueOf(position);

        final BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(getActivity());
        bottomSheetDialog.setContentView(R.layout.bottom_dialog_transfer);

        RelativeLayout Layout_trnsfr_Btm_Dlg1   = bottomSheetDialog.findViewById(R.id.Layout_trnsfr_Btm_Dlg1);
        RelativeLayout Layout_trnsfr_Btm_Dlg2   = bottomSheetDialog.findViewById(R.id.Layout_trnsfr_Btm_Dlg2);

        bottomSheetDialog.show();

        Layout_trnsfr_Btm_Dlg1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SpaceToWalletFragment fragment = new SpaceToWalletFragment();
                Bundle args = new Bundle();
                args.putString("AMOUNT", AMOUNT);
                args.putString("SPACE_NAME", SPACE_NAME);
                args.putString("POSITION", POSITION);
                fragment.setArguments(args);

                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.containerLayout, fragment).addToBackStack("Space").commit();
                bottomSheetDialog.dismiss();
            }
        });

        Layout_trnsfr_Btm_Dlg2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SpaceToSpaceFragment fragment = new SpaceToSpaceFragment();
                Bundle args = new Bundle();
                args.putString("AMOUNT", AMOUNT);
                args.putString("SPACE_NAME", SPACE_NAME);
                args.putString("POSITION", POSITION);
                fragment.setArguments(args);

                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.containerLayout, fragment).addToBackStack("Space").commit();
                bottomSheetDialog.dismiss();
            }
        });
    }
//------------------------------------------------------------------------------------------------\\
//------------------------------------------------------------------------------------------------//
}