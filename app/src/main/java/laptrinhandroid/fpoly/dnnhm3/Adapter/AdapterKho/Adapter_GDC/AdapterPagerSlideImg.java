package laptrinhandroid.fpoly.dnnhm3.Adapter.AdapterKho.Adapter_GDC;

import android.app.Activity;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import laptrinhandroid.fpoly.dnnhm3.Fragment.FragmentSlideImg;
import laptrinhandroid.fpoly.dnnhm3.R;

public class AdapterPagerSlideImg extends FragmentStateAdapter {
    private Activity activity;

    public AdapterPagerSlideImg(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
        activity = fragmentActivity;
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        FragmentSlideImg fragmentSlideImg = new FragmentSlideImg();
        Bundle bundle = new Bundle();
        switch (position) {
            case 0:
                bundle.putInt("img", R.drawable.a1);
                break;
            case 1:
                bundle.putInt("img", R.drawable.a2);
                break;
            case 2:
                bundle.putInt("img", R.drawable.a3);
                break;
            case 3:
                bundle.putInt("img", R.drawable.a4);

                break;
        }
        fragmentSlideImg.setArguments(bundle);

        return fragmentSlideImg;
    }

    @Override
    public int getItemCount() {
        return 4;
    }
}
