package com.ese2013.mensaunibe.util.gui;

import java.util.ArrayList;
import java.util.List;

import com.ese2013.mensaunibe.R;
import com.ese2013.mensaunibe.model.ModelMensa;
import com.ese2013.mensaunibe.model.ModelMenu;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

public class AdapterCustomMenulist extends BaseAdapter {
	private Context context;
//	private ViewGroup parent;
	private List<ModelMenu> menus;
	private LayoutInflater inflater;
	private int resource;

	public AdapterCustomMenulist(Context context, ArrayList<ModelMenu> list, int resource) {
		super();
		this.context = context; // ActivityMain
		this.menus = list;
		this.resource = resource; // the xml layout file, like this it gets dynamic
	}
	
	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		
		// get a reference to the parent view for the rating dialog
//		this.parent = parent;

		inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView = inflater.inflate(resource, parent, false);
        
        LinearLayout grid = (LinearLayout) rowView.findViewById(R.id.list_grid);
        
        // the actual fields that contain text
        ModelMenu menu = menus.get(position);
        if ( (TextView) rowView.findViewById(R.id.mensa) != null ) {
        	TextView mensa = (TextView) rowView.findViewById(R.id.mensa);
        	// get the mensa name for the menu overview (FragmentMenuList)
			ModelMensa mensaobj = menu.getMensa();
        	mensa.setText(mensaobj.getName());
        }
        TextView title = (TextView) rowView.findViewById(R.id.title);
        TextView desc = (TextView) rowView.findViewById(R.id.desc);
        TextView price = (TextView) rowView.findViewById(R.id.price);
        TextView date = (TextView) rowView.findViewById(R.id.date);
        
        title.setText(menu.getTitle());
        desc.setText(menu.getDesc());
        price.setText(menu.getPrice());
        date.setText(menu.getDate());

        // set the click listener for the list item
        // should open mensa details
        final OnClickListener rowListener = new OnClickListener() {
            @Override
            public void onClick(View rowView) {
            	// show the mensadetails for the clicked mensa
            	//fragment.selectItem(position);
            	// delete after developement, just to show that it works
            	Toast.makeText(context, "Menu clicked, show rating...", Toast.LENGTH_SHORT).show();
            	showRating();
            }
        };
        grid.setOnClickListener(rowListener);
            
		return rowView;
	}
	
	@Override
	public int getCount() {
		return menus.size();
	}
	
	@Override
	public ModelMenu getItem(int position) {
		return menus.get(position);
	}
	
	@Override
	public long getItemId(int position) {
		return position;
	}
	
	public void showRating() {
		final AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(context);
		
//		View dialogLayout = inflater.inflate(R.layout.dialog_rating, parent);
		final RatingBar rating = new RatingBar(context);
//		final RatingBar rating = (RatingBar) dialogLayout.findViewById(R.id.rating_bar);
		rating.setMax(5);
		rating.setStepSize(1.0f);
		rating.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
		
		LinearLayout parentLayout = new LinearLayout(context);
        parentLayout.setGravity(Gravity.CENTER);
        parentLayout.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
        parentLayout.addView(rating);

		dialogBuilder.setIcon(R.drawable.ic_star);
		dialogBuilder.setTitle("Menu bewerten");
		dialogBuilder.setView(parentLayout);

		// Buttons OK
		dialogBuilder.setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int which) {
				//txtView.setText(String.valueOf(rating.getProgress()));
				// send rating to server
				Toast.makeText(context, "Send Vote to server..." + String.valueOf(rating.getProgress()), Toast.LENGTH_SHORT).show();
				dialog.dismiss();
			}
		});
		
		// Button cancel
		dialogBuilder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int id) {
				dialog.cancel();
			}
		});

		dialogBuilder.create();
		dialogBuilder.show();
	}
}