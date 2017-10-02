package com.hics.biofields.Views.Adapters;

import android.support.v4.app.Fragment;
import com.hics.biofields.R;
import com.hics.biofields.Views.Fragments.FormRequisitionFragment;
import com.hics.biofields.Views.Fragments.RequisitionFragment;
import com.hics.biofields.Views.Fragments.RequisitionOpenFragment;
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
    int[] colors={R.color.gray_tab,R.color.gray_tab,R.color.gray_tab,R.color.gray_tab};
    int[] textColors={R.color.gray_icon_unselected,R.color.gray_icon_unselected,R.color.gray_icon_unselected,R.color.gray_icon_unselected};
    int[] icons={R.drawable.ic_qbi_aut,R.drawable.ic_qbi_seg,R.drawable.ic_qbi_req,R.drawable.ic_qbi_logout};

    public RequisitionAdapter(FragmentManager fragmentManager)
    {
        super(fragmentManager);
        fragments.add(new RequisitionFragment());
        fragments.add(new RequisitionOpenFragment());
        fragments.add(new FormRequisitionFragment());
        fragments.add(new SettingsFragment());

        titles.add("Por Autorizar");
        titles.add("Seguimientos");
        titles.add("Solicitud");
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
        return textColors[position];
    }

    @Override
    public int getIconResource(int position) {
        return icons[position];
    }

}

