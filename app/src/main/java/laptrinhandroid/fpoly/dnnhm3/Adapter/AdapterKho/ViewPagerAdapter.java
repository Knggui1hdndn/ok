package laptrinhandroid.fpoly.dnnhm3.Adapter.AdapterKho;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import laptrinhandroid.fpoly.dnnhm3.Activity.QuanLyKho;
import laptrinhandroid.fpoly.dnnhm3.Fragment.Kho.TabListBill_QLKho_Fragment;
import laptrinhandroid.fpoly.dnnhm3.Fragment.Kho.TabListProduct_QLKho_Fragment;


public class ViewPagerAdapter extends FragmentStateAdapter {
    TabListBill_QLKho_Fragment qlKho_fragment;
    int maNV;
    public ViewPagerAdapter(@NonNull QuanLyKho fragment, TabListBill_QLKho_Fragment qlKho_fragment,int maNV) {
        super(fragment);
        this.maNV=maNV;
        this.qlKho_fragment=qlKho_fragment;
        Bundle bundle=new Bundle();
        bundle.putInt("maNV",maNV);
        this.qlKho_fragment.setArguments(bundle);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position){
            case 0:return new TabListProduct_QLKho_Fragment();
            case 1:return qlKho_fragment;
        }
        return new TabListProduct_QLKho_Fragment();
    }

    @Override
    public int getItemCount() {
        return 2;
    }
}
