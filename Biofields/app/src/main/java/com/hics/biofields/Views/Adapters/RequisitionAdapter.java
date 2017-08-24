package com.hics.biofields.Views.Adapters;

import android.support.v4.app.Fragment;
import com.hics.biofields.R;
import com.hics.biofields.Views.Fragments.FormRequisitionFragment;
import com.hics.biofields.Views.Fragments.RequisitionFragment;
import com.hics.biofields.Views.Fragments.SettingsFragment;

import android.support.v4.app.FragmentManager;
import java.util.ArrayList;
import in.galaxyofandroid.awesometablayout.AwesomeTabBarAdapter;


/**
 * Created by david.barrera on 8/16/17.
 */

public class RequisitionAdapter  extends AwesomeTabBarAdapter
{
    ArrayList<Fragment> fragments=new ArrayList<>();
    ArrayList<String> titles=new ArrayList<>();
    int[] colors={R.color.colorPrimaryDark,R.color.colorPrimaryDark,R.color.colorPrimaryDark,R.color.colorPrimaryDark};
    int[] textColors={R.color.colorWhite};
    int[] icons={R.drawable.ic_folder,R.mipmap.ic_folder_tracing,R.drawable.ic_more,R.drawable.ic_close};

    public RequisitionAdapter(FragmentManager fragmentManager)
    {
        super(fragmentManager);
        fragments.add(new RequisitionFragment());
        fragments.add(new RequisitionFragment());
        fragments.add(new FormRequisitionFragment());
        fragments.add(new SettingsFragment());

        titles.add("Por Aprobar");
        titles.add("Seguimientos");
        titles.add("Solcitud");
        titles.add("Cerrar Sesión");
    }

    @Override
    public int getCount() {
        return fragments.size();
    }

    @Override
    public Fragment getItem(int position) {
        return fragments.get(position);
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return titles.get(position);
    }

    @Override
    public int getColorResource(int position) {
        return colors[position];
    }

    @Override
    public int getTextColorResource(int position) {
        return textColors[0];
    }

    @Override
    public int getIconResource(int position) {
        return icons[position];
    }
}

