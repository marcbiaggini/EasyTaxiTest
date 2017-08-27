package com.example.juanvilla.easytest.Fragments;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.juanvilla.easytest.Adapters.FavoriteAdapter;
import com.example.juanvilla.easytest.Helpers.DialogHelper;
import com.example.juanvilla.easytest.Interfaces.OnListFragmentInteractionListener;
import com.example.juanvilla.easytest.R;


/**
 * Created by juan.villa on 26/08/17.
 */
public class FavoriteListFragment extends android.support.v4.app.DialogFragment {

  public Dialog dialog;
  private RecyclerView list;
  FavoriteAdapter adapter;


  @Override
  public Dialog onCreateDialog(Bundle savedInstanceState) {
    AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
    LayoutInflater inflater = getActivity().getLayoutInflater();
    View view = inflater.inflate(R.layout.fragment_item_list, null);

    list = view.findViewById(R.id.list);
    list.setLayoutManager((new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false)));
    list.setAdapter(adapter);
    builder.setView(view);
    dialog = builder.create();
    dialog.getWindow().setLayout(ViewGroup.LayoutParams.WRAP_CONTENT, view.getHeight());
    dialog.getWindow().getAttributes().windowAnimations = R.style.MyAnimation_Window;
    DialogHelper.show((AlertDialog) dialog, getActivity());
    return dialog;
  }

  public void setAdapter(FavoriteAdapter adapter) {
    this.adapter=adapter;
  }

  @Override
  public void show(FragmentManager manager, String tag) {
    if (!isAdded()) {
      super.show(manager, tag);
    }
  }
}