package com.example.deliverable1;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

import branch.Branch;
import service.Service;

public class BranchItem extends ArrayAdapter<Branch> {
    private Activity context;
    List<Branch> branches;

    public BranchItem(Activity context, List<Branch> branches) {
        super(context, R.layout.activity_branch_item, branches);
        this.context = context;
        this.branches = branches;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        View listViewItem = inflater.inflate(R.layout.activity_branch_item, null, true);

        TextView textViewName = (TextView) listViewItem.findViewById(R.id.BranchName);
        TextView textViewService = (TextView) listViewItem.findViewById(R.id.ServiceName);

        Branch branch = branches.get(position);
        textViewName.setText(branch.getName());
        textViewService.setText(branch.getService());
        return listViewItem;
    }
}
