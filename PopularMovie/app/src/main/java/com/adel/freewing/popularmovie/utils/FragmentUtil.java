package com.adel.freewing.popularmovie.utils;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;

import com.adel.freewing.popularmovie.R;


public class FragmentUtil {
	public static void gotoFragment(AppCompatActivity activity, Bundle bundle, Fragment fragment, String tag) {
		gotoTheFragment(activity, bundle, fragment, tag);
	}
	public static void gotoFragment(AppCompatActivity activity,int id, Bundle bundle, Fragment fragment, String tag) {
		gotoTheFragment(activity,id, bundle, fragment, tag);
	}
	public static void gotoFragment(AppCompatActivity activity, Fragment fragment, String tag) {
		gotoTheFragment(activity, null, fragment, tag);
	}

	
	private static void gotoTheFragment(AppCompatActivity activity, Bundle bundle, Fragment fragment, String tag) {
		if (bundle != null) {
			fragment.setArguments(bundle);
		}

		FragmentTransaction ft = activity.getSupportFragmentManager()
				.beginTransaction();
		ft.replace(R.id.frame_container, fragment, tag);
		ft.commit();
	}

	private static void gotoTheFragment(AppCompatActivity activity,int id, Bundle bundle, Fragment fragment, String tag) {
		if (bundle != null) {
			fragment.setArguments(bundle);
		}

		FragmentTransaction ft = activity.getSupportFragmentManager()
				.beginTransaction();
		ft.replace(id, fragment, tag);
		ft.commit();
	}
}
