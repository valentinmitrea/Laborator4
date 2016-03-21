package ro.pub.cs.systems.eim.lab04.contactsmanager;

import java.util.ArrayList;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;


public class ContactsManagerActivity extends Activity {
	
	private Button showFields, save, cancel;
	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_contacts_manager);
		
		//se seteaza listener-ul pentru click pe butoane
		ContactsManagerListener contactsManagerListener = new ContactsManagerListener();
		
		showFields = (Button)findViewById(R.id.button_additional_fields);
		showFields.setOnClickListener(contactsManagerListener);
		
		save = (Button)findViewById(R.id.button_save);
		save.setOnClickListener(contactsManagerListener);
		
		cancel = (Button)findViewById(R.id.button_cancel);
		cancel.setOnClickListener(contactsManagerListener);
		
		//aplicatia de contacte poate fi pornita doar din interiorul aplicatiei de phone dialer
		Intent intent = getIntent();
		if (intent != null) {
			String phone = intent.getStringExtra("ro.pub.cs.systems.eim.lab04.contactsmanager.PHONE_NUMBER_KEY");
			if (phone != null)
				((EditText)findViewById(R.id.phone_number)).setText(phone);
			else
				Toast.makeText(this, getString(R.string.phone_error), Toast.LENGTH_LONG).show();
		}
	}
	

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.contacts_manager, menu);
		
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
	
	
	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent intent) {
		switch(requestCode) {
			case Constants.CONTACTS_MANAGER_REQUEST_CODE:
				setResult(resultCode, new Intent());
				finish();
				
				break;
		}
	}
	
	
	class ContactsManagerListener implements OnClickListener {

		@Override
		public void onClick(View v) {
			if (v.getId() == showFields.getId()) {
				LinearLayout layoutAdditionalFields = (LinearLayout)findViewById(R.id.layout_additional_fields);
				
				if (layoutAdditionalFields.getVisibility() == View.INVISIBLE) {
					layoutAdditionalFields.setVisibility(View.VISIBLE);
					showFields.setText(getString(R.string.hide_additional));
				}
				else {
					layoutAdditionalFields.setVisibility(View.INVISIBLE);
					showFields.setText(getString(R.string.show_additional));
				}
			}
			else if (v.getId() == save.getId()) {
				Intent intent = new Intent(ContactsContract.Intents.Insert.ACTION);
				intent.setType(ContactsContract.RawContacts.CONTENT_TYPE);
				
				String name = ((EditText)findViewById(R.id.contact_name)).getText().toString();
				String phone = ((EditText)findViewById(R.id.phone_number)).getText().toString();
				String email = ((EditText)findViewById(R.id.email_address)).getText().toString();
				String address = ((EditText)findViewById(R.id.contact_address)).getText().toString();
				String jobTitle = ((EditText)findViewById(R.id.job_title)).getText().toString();
				String company = ((EditText)findViewById(R.id.company)).getText().toString();
				String website = ((EditText)findViewById(R.id.website)).getText().toString();
				String im = ((EditText)findViewById(R.id.im)).getText().toString();
				
				if (name != null)
					intent.putExtra(ContactsContract.Intents.Insert.NAME, name);
				
				if (phone != null)
					intent.putExtra(ContactsContract.Intents.Insert.PHONE, phone);
				
				if (email != null)
					intent.putExtra(ContactsContract.Intents.Insert.EMAIL, email);
				
				if (address != null)
					intent.putExtra(ContactsContract.Intents.Insert.POSTAL, address);
				
				if (jobTitle != null)
					intent.putExtra(ContactsContract.Intents.Insert.JOB_TITLE, jobTitle);
				
				if (company != null)
					intent.putExtra(ContactsContract.Intents.Insert.COMPANY, company);
				
				ArrayList<ContentValues> contactData = new ArrayList<ContentValues>();
				if (website != null) {
					ContentValues websiteItem = new ContentValues();
					websiteItem.put(ContactsContract.Data.MIMETYPE, ContactsContract.CommonDataKinds.Website.CONTENT_ITEM_TYPE);
					websiteItem.put(ContactsContract.CommonDataKinds.Website.URL, website);
					contactData.add(websiteItem);
				}
				if (im != null) {
					ContentValues imItem = new ContentValues();
					imItem.put(ContactsContract.Data.MIMETYPE, ContactsContract.CommonDataKinds.Im.CONTENT_ITEM_TYPE);
					imItem.put(ContactsContract.CommonDataKinds.Im.DATA, im);
					contactData.add(imItem);
				}
				
				intent.putParcelableArrayListExtra(ContactsContract.Intents.Insert.DATA, contactData);
				startActivityForResult(intent, Constants.CONTACTS_MANAGER_REQUEST_CODE);
			}
			else if (v.getId() == cancel.getId()) {
				setResult(Activity.RESULT_CANCELED, new Intent());
				finish();
			}
		}
		
	}
	
}
