package hbu.example.foodtrip_01;

import java.util.ArrayList;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RadioGroup;
import android.widget.TextView;


public class FoodTripActivity extends Activity {

	ArrayList<Restaurant> model = new ArrayList<Restaurant>();
	RestaurantAdapter adapter = null;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		// it must be the same with_Layout name
		setContentView(R.layout.activity_food_trip);

		Button save = (Button) findViewById(R.id.btn_save);
		save.setOnClickListener(onSave);

		ListView list = (ListView) findViewById(R.id.restaurants);
		adapter = new RestaurantAdapter();
		list.setAdapter(adapter);

	}

	// Interfaces (OnClickListener)-listen on all click events

	//
	private View.OnClickListener onSave = new View.OnClickListener() {

		@Override
		public void onClick(View v) {

			Restaurant r = new Restaurant(); // restore Restaurant r here

			EditText name = (EditText) findViewById(R.id.field_name);
			EditText address = (EditText) findViewById(R.id.field_address);
			RadioGroup types = (RadioGroup) findViewById(R.id.rgrp_types);
			
			switch (types.getCheckedRadioButtonId()) {
			case R.id.rbtn_sit_down:
				r.setType("sit_down");
				break;
			case R.id.rbtn_take_out:
				r.setType("take_out");
				break;
			case R.id.rbtn_delivery:
				r.setType("delivery");
				break;
			}

			r.setName(name.getText().toString());
			r.setAddress(address.getText().toString());

			
		
		// Clear texts
			((EditText) findViewById(R.id.field_name)).setText("");
			((EditText) findViewById(R.id.field_address)).setText("");

			// Clear Radio Buttons
			((RadioGroup) findViewById(R.id.rgrp_types)).clearCheck();

			// SetFocus
			((EditText) findViewById(R.id.field_name)).requestFocus();

			// add items in LView
			adapter.add(r);

		}

	};

	class RestaurantAdapter extends ArrayAdapter<Restaurant> {
		RestaurantAdapter() {
			
			super(FoodTripActivity.this, android.R.layout.simple_list_item_1,
					model);
			
			
		}

		// override here

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			View row = convertView;
			RestaurantHolder holder = null;

			if (row == null) {
				LayoutInflater inflater = getLayoutInflater();
				row = inflater.inflate(R.layout.row, parent, false);
				holder = new RestaurantHolder(row);
				row.setTag(holder);
			} else {
				holder = (RestaurantHolder) row.getTag();
			}

			holder.populateFrom(model.get(position));
			return (row);
		}

	}

	class RestaurantHolder {
		private TextView name = null;
		private TextView address = null;
		private ImageView icon = null;

		RestaurantHolder(View row) {
			name = (TextView) row.findViewById(R.id.row_name);
			address = (TextView) row.findViewById(R.id.row_address);
			icon = (ImageView) row.findViewById(R.id.row_icon);
		}

		void populateFrom(Restaurant r) {
			name.setText(r.getName());
			address.setText(r.getAddress());

			if (r.getType().equals("sit_down")) {
				icon.setImageResource(R.drawable.sitdown);
			} else if (r.getType().equals("take_out")) {
				icon.setImageResource(R.drawable.takeout);
			} else {
				icon.setImageResource(R.drawable.delivery);
			}
		}
	}

}
