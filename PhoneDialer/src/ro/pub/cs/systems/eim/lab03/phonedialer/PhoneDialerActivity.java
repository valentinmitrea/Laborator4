package ro.pub.cs.systems.eim.lab03.phonedialer;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


public class PhoneDialerActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_phone_dialer);
		
		// Set the OnClickListener
		PhoneDialerListener phoneDialerListener = new PhoneDialerListener();
		
		findViewById(R.id.backspace_image).setOnClickListener(phoneDialerListener);
		findViewById(R.id.button_call).setOnClickListener(phoneDialerListener);
		findViewById(R.id.button_contacts).setOnClickListener(phoneDialerListener);
		findViewById(R.id.button_hangup).setOnClickListener(phoneDialerListener);
		findViewById(R.id.button_0).setOnClickListener(phoneDialerListener);
		findViewById(R.id.button_1).setOnClickListener(phoneDialerListener);
		findViewById(R.id.button_2).setOnClickListener(phoneDialerListener);
		findViewById(R.id.button_3).setOnClickListener(phoneDialerListener);
		findViewById(R.id.button_4).setOnClickListener(phoneDialerListener);
		findViewById(R.id.button_5).setOnClickListener(phoneDialerListener);
		findViewById(R.id.button_6).setOnClickListener(phoneDialerListener);
		findViewById(R.id.button_7).setOnClickListener(phoneDialerListener);
		findViewById(R.id.button_8).setOnClickListener(phoneDialerListener);
		findViewById(R.id.button_9).setOnClickListener(phoneDialerListener);
		findViewById(R.id.button_star).setOnClickListener(phoneDialerListener);
		findViewById(R.id.button_hashtag).setOnClickListener(phoneDialerListener);
	}
	

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.phone_dialer, menu);
		
		return true;
	}
	

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings)
			return true;
		
		return super.onOptionsItemSelected(item);
	}
	
	
	class PhoneDialerListener implements OnClickListener {

		@Override
		public void onClick(View v) {
			EditText telephoneNumber = (EditText)findViewById(R.id.telephone_number);
			String telephone = telephoneNumber.getText().toString();
			
			if (v.getId() == R.id.backspace_image) {
				if (telephone.length() > 0)
					telephoneNumber.setText(telephone.substring(0, telephone.length() - 1));
			}
			else if (v.getId() == R.id.button_call) {
				if (telephone.length() > 0) {
					// Se porneste un Intent pentru a face apelul
					Intent intent = new Intent(Intent.ACTION_CALL);
					intent.setData(Uri.parse("tel:" + telephone));
					
					startActivity(intent);
				}
			}
			else if (v.getId() == R.id.button_contacts) {
				if (telephone.length() > 0) {
					Intent intent = new Intent("ro.pub.cs.systems.eim.lab04.contactsmanager.intent.action.ContactsManagerActivity");
					intent.putExtra("ro.pub.cs.systems.eim.lab04.contactsmanager.PHONE_NUMBER_KEY", telephone);
					
					startActivityForResult(intent, Constants.CONTACTS_MANAGER_REQUEST_CODE);
				}
				else
					Toast.makeText(getApplication(), getResources().getString(R.string.phone_error), Toast.LENGTH_LONG).show();
			}
			else if (v.getId() == R.id.button_hangup)
				finish();
			else
				telephoneNumber.setText(telephone + ((Button)findViewById(v.getId())).getText());
		}
		
	}

}
