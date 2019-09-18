package com.project.mounika.shareyourride;

import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.Button;
import android.widget.ExpandableListView;
import android.widget.TextView;

import java.util.HashMap;
import java.util.List;

public class ExpandableListAdapter extends BaseExpandableListAdapter  {
    DBHelper db ;

    private Context contx;
    private List<String> liDatHead;
    private HashMap<String,List<ListItem> > childData;


    public ExpandableListAdapter(Context context, List<String> headerData,
                                 HashMap<String, List<ListItem>> childData) {
        this.contx = context;
        this.liDatHead = headerData;
        this.childData = childData;
    }


    @Override
    public Object getChild(int grpPos, int childPos) {
        return this.childData.get(this.liDatHead.get(grpPos))
                .get(childPos).getLstItmLabel();
    }
    public boolean getChildcheck(int grpPos, int childPos) {
        return this.childData.get(this.liDatHead.get(grpPos))
                .get(childPos).getIfValidationNeeded();
    }

    @Override
    public long getChildId(int grpPos, int childPos) {
        return childPos;
    }

    @Override
    public View getChildView(final int grpPos, final int childPos,
                             boolean lstChildBool, View cvtView, ViewGroup prnt) {
        db= new DBHelper(contx);
        final String childText = (String) getChild(grpPos, childPos);
        String[] split=childText.split("\n");
        boolean mycheck = (boolean) getChildcheck(grpPos, childPos);
        if (cvtView == null) {
            LayoutInflater infalInflater = (LayoutInflater) this.contx
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            cvtView = infalInflater.inflate(R.layout.list_item, null);
        }

        TextView textview = (TextView) cvtView.findViewById(R.id.myrides);
        Button cancel = (Button) cvtView
                .findViewById(R.id.btnCancelledView);
if(mycheck)
    cancel.setVisibility(View.VISIBLE);
else
    cancel.setVisibility(View.GONE);
        textview.setText(childText);
        return cvtView;
    }

    @Override
    public int getChildrenCount(int grpPos) {
        return this.childData.get(this.liDatHead.get(grpPos))
                .size();
    }

    @Override
    public Object getGroup(int grpPos) {
        return this.liDatHead.get(grpPos);
    }

    @Override
    public int getGroupCount() {
        return this.liDatHead.size();
    }

    @Override
    public long getGroupId(int grpPos) {
        return grpPos;
    }

    @Override
    public View getGroupView(int grpPos, boolean isExpandClicked,
                             View cvrtView, ViewGroup prnt) {
        String headerTitle = (String) getGroup(grpPos);
        if (cvrtView == null) {
            LayoutInflater infalInflater = (LayoutInflater) this.contx
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            cvrtView = infalInflater.inflate(R.layout.list_group, null);
        }

        TextView lblListHeader = (TextView) cvrtView
                .findViewById(R.id.lblListHeader);
        lblListHeader.setTypeface(null, Typeface.BOLD);
        ExpandableListView mExpandableListView = (ExpandableListView) prnt;
        lblListHeader.setText(headerTitle);
        return cvrtView;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public boolean isChildSelectable(int grpPos, int childPos) {
        return true;
    }


}