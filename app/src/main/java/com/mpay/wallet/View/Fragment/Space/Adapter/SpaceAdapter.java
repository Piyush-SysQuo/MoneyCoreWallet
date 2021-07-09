package com.mpay.wallet.View.Fragment.Space.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.view.ContextThemeWrapper;
import androidx.appcompat.widget.PopupMenu;
import androidx.recyclerview.widget.RecyclerView;

import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.mpay.wallet.R;
import com.mpay.wallet.Utils.Constants;
import com.mpay.wallet.Utils.SharedPreferenceAmount;
import com.mpay.wallet.View.Fragment.Space.InterFace.SpaceTransferInterFace;
import com.mpay.wallet.View.Fragment.Space.Model.SpaceItemAdapter;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

public class SpaceAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private List<SpaceItemAdapter> mList;
    private Context mContext;
    private SpaceTransferInterFace spaceTransferInterFace;
    public SpaceAdapter(List<SpaceItemAdapter> list, Context context, SpaceTransferInterFace spaceTransferInterFace){
        super();
        mList = list;
        mContext = context;
        this.spaceTransferInterFace = spaceTransferInterFace;
    }
    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_add_space, parent, false);
        ViewHolder viewHolder = new ViewHolder(v);
        return viewHolder;
    }
    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int position) {
        SpaceItemAdapter itemAdapter = mList.get(position);
        try {
            ((ViewHolder) viewHolder).Tv_AdptSpaceName.setText(itemAdapter.getName());
            ((ViewHolder) viewHolder).Tv_AdptSpaceAmt.setText(itemAdapter.getAmount());
//            Button Transfer Clicked
            ((ViewHolder) viewHolder).Tv_AdptSpaceTransfer.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    spaceTransferInterFace.transferOnClicked(position, ((ViewHolder)viewHolder).Tv_AdptSpaceAmt.getText().toString().replaceAll("€", ""), ((ViewHolder)viewHolder).Tv_AdptSpaceName.getText().toString());
                }
            });
//            Image Clicked
            ((ViewHolder) viewHolder).Iv_AdptSpaceOption.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
//                    showPopupMenu(v, false, R.style.MyPopupOtherStyle);
                    boolean isWithIcons = false;
                    Context wrapper = new ContextThemeWrapper(mContext, R.style.MyPopupOtherStyle);

                    //init the popup
                    PopupMenu popup = new PopupMenu(wrapper, v);

                    //inflate menu
                    popup.getMenuInflater().inflate(R.menu.space_option_menu, popup.getMenu());

                    //implement click events
                    popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                        @Override
                        public boolean onMenuItemClick(MenuItem menuItem) {
                            mList.remove(position);
                            notifyDataSetChanged();
                            return true;
                        }
                    });
                    popup.show();
                }
            });
//            PieChar
            String Amnt1 =   itemAdapter.getAmount();
            String Amnt2 =   Amnt1.replaceAll("€", "");
            double Amount = Double.parseDouble(Amnt2);

            PieDataSet pieDataSet = new PieDataSet(pieChartDataSet(Amount),"");
            pieDataSet.setColors(mContext.getResources().getColor(R.color.blue3), mContext.getResources().getColor(R.color.button_color));
            pieDataSet.setValueLineColor(R.color.button_color);
            pieDataSet.setValueTextSize(0f);

            PieData pieData = new PieData(pieDataSet);

            ((ViewHolder) viewHolder).pieChart_Adpt_Space.setDrawSliceText(false);
            ((ViewHolder) viewHolder).pieChart_Adpt_Space.setData(pieData);
            ((ViewHolder) viewHolder).pieChart_Adpt_Space.setDrawCenterText(true);
            ((ViewHolder) viewHolder).pieChart_Adpt_Space.setDrawEntryLabels(true);
            ((ViewHolder) viewHolder).pieChart_Adpt_Space.setDrawMarkers(false);
            ((ViewHolder) viewHolder).pieChart_Adpt_Space.getDescription().setEnabled(false);
            ((ViewHolder) viewHolder).pieChart_Adpt_Space.setClickable(false);
            ((ViewHolder) viewHolder).pieChart_Adpt_Space.setHoleColor(mContext.getResources().getColor(R.color.white));

            ((ViewHolder) viewHolder).pieChart_Adpt_Space.getLegend().setEnabled(false);
            ((ViewHolder) viewHolder).pieChart_Adpt_Space.setCenterText("");


        }catch (Exception e){
            e.printStackTrace();
        }
    }
    @Override
    public int getItemCount() {
        return mList.size();
    }
    class ViewHolder extends RecyclerView.ViewHolder{
        private PieChart pieChart_Adpt_Space;
        private LinearLayout MainLayout_Adpt_AddSpace;
        private ImageView Iv_AdptSpaceOption;
        private TextView Tv_AdptSpaceName, Tv_AdptSpaceAmt, Tv_AdptSpaceTransfer;
        public ViewHolder(View itemView) {
            super(itemView);
            pieChart_Adpt_Space    = itemView.findViewById(R.id.pieChart_Adpt_Space);

            MainLayout_Adpt_AddSpace    = itemView.findViewById(R.id.MainLayout_Adpt_AddSpace);

            Iv_AdptSpaceOption          = itemView.findViewById(R.id.Iv_AdptSpaceOption);

            Tv_AdptSpaceName            = itemView.findViewById(R.id.Tv_AdptSpaceName);
            Tv_AdptSpaceAmt             = itemView.findViewById(R.id.Tv_AdptSpaceAmt);
            Tv_AdptSpaceTransfer        = itemView.findViewById(R.id.Tv_AdptSpaceTransfer);
        }
    }

    private ArrayList<PieEntry> pieChartDataSet(double Amt){
        ArrayList<PieEntry> dataSet = new ArrayList<PieEntry>();
        double TotAmt = Double.parseDouble(SharedPreferenceAmount.getInstance(mContext).getString_TotAmount(Constants.TOTAL_AMOUNT).toString());
        dataSet.add(new PieEntry((float) TotAmt)); // Total
        dataSet.add(new PieEntry((float) Amt)); // Total
        return  dataSet;
    }

    private void showPopupMenu(View anchor, boolean isWithIcons, int style) {
        //init the wrapper with style
        Context wrapper = new ContextThemeWrapper(mContext, style);

        //init the popup
        PopupMenu popup = new PopupMenu(wrapper, anchor);

        /*  The below code in try catch is responsible to display icons*/
        if (isWithIcons) {
            try {
                Field[] fields = popup.getClass().getDeclaredFields();
                for (Field field : fields) {
                    if ("mPopup".equals(field.getName())) {
                        field.setAccessible(true);
                        Object menuPopupHelper = field.get(popup);
                        Class<?> classPopupHelper = Class.forName(menuPopupHelper.getClass().getName());
                        Method setForceIcons = classPopupHelper.getMethod("setForceShowIcon", boolean.class);
                        setForceIcons.invoke(menuPopupHelper, true);
                        break;
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        //inflate menu
        popup.getMenuInflater().inflate(R.menu.space_option_menu, popup.getMenu());

        //implement click events
        popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {

                return true;
            }
        });
        popup.show();

    }

}