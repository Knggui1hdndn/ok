package laptrinhandroid.fpoly.dnnhm3.Adapter.AdpaterNhanVien;

import android.os.Bundle;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import laptrinhandroid.fpoly.dnnhm3.Fragment.NhanVien.FragmentLich;
import laptrinhandroid.fpoly.dnnhm3.Fragment.NhanVien.FragmentLuong;

public class AdapterPagerNhanVien extends FragmentStateAdapter {
    private int nhanVien;

    public AdapterPagerNhanVien(@NonNull FragmentActivity fragmentActivity, int nhanVien) {
        super(fragmentActivity);
        Log.d("ssssssssssw", "AdapterPagerNhanVien: " + nhanVien);
        this.nhanVien = nhanVien;
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        Bundle bundle = new Bundle();
        bundle.putInt("nv", nhanVien);
        if (position == 0) {
            FragmentLich fragmentLich = new FragmentLich();
            fragmentLich.setArguments(bundle);

            return fragmentLich;
        }
        FragmentLuong fragmentLuong = new FragmentLuong();
         fragmentLuong.setArguments(bundle);
        return fragmentLuong;
    }

    @Override
    public int getItemCount() {
        return 2;
    }
}
