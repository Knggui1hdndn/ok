package laptrinhandroid.fpoly.dnnhm3.Adapter.AdapterSanPham;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import laptrinhandroid.fpoly.dnnhm3.Fragment.SanPham.FragmentDanhMuc;
import laptrinhandroid.fpoly.dnnhm3.Fragment.SanPham.FragmentSanPham;

public class ViewSanPhamAdapter extends FragmentStateAdapter {
  FragmentSanPham sanPham;

    public ViewSanPhamAdapter(@NonNull FragmentActivity fragmentActivity,FragmentSanPham sanPham) {
        super(fragmentActivity);
        this.sanPham=sanPham;
    }




    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position){


            case 1:
                return new FragmentDanhMuc();

            default:
                return sanPham;
        }
    }

    @Override
    public int getItemCount() {
        return 2;
    }
}
