package com.example.codefest;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TableLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Hashtable;

public class MainActivity extends AppCompatActivity {

    EditText input_studentID, input_studentName, input_birthday, input_contact;
    Spinner spinner_program;
    Button btn_add, btn_update, btn_delete;
    ListView listView;
    String[] array_programs = {"BSIT", "BSCS", "BSCP"};
    String studentID, studentName, birthday, contact, program;

    StudentInfoTable studentInfoTable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ArrayList<String> studentIDs = new ArrayList<>();
        HashMap<String, String> studentNames = new HashMap<>();
        HashMap<String, String> birthdays = new HashMap<>();
        HashMap<String, String> contacts = new HashMap<>();
        HashMap<String, String> programs = new HashMap<>();

        input_studentID = findViewById(R.id.edtxt_student_id);
        input_studentName = findViewById(R.id.edtxt_student_name);
        input_birthday = findViewById(R.id.edtxt_birthday);
        input_contact = findViewById(R.id.edtxt_contact_no);
        btn_add = findViewById(R.id.btn_add);
        btn_update = findViewById(R.id.btn_update);
        btn_delete = findViewById(R.id.btn_delete);

        btn_add.setOnClickListener(new ButtonClicker(btn_add));
        btn_update.setOnClickListener(new ButtonClicker(btn_update));
        btn_delete.setOnClickListener(new ButtonClicker(btn_delete));

        input_studentID.addTextChangedListener(new InputText(input_studentID));
        input_studentName.addTextChangedListener(new InputText(input_studentName));
        input_birthday.addTextChangedListener(new InputText(input_birthday));
        input_contact.addTextChangedListener(new InputText(input_contact));

        studentInfoTable = new StudentInfoTable();
        listView = findViewById(R.id.lv_display);
        listView.setAdapter(studentInfoTable);

        spinner_program = findViewById(R.id.spinner_program);
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, array_programs);
        spinner_program.setAdapter(arrayAdapter);
        spinner_program.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                program = array_programs[position];
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                program = array_programs[0];
            }
        });
    }

    private class InputText implements TextWatcher {

        private EditText editText;

        public InputText(EditText editText) {
            this.editText = editText;
        }

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            String text = s.toString();

            if (editText.equals(input_studentID)) {
                studentID = text;
            } else if (editText.equals(input_studentName)) {
                studentName = text;
            } else if (editText.equals(input_contact)) {
                contact = text;
            } else if (editText.equals(input_birthday)) {
                birthday = text;
            }
        }

        @Override
        public void afterTextChanged(Editable s) {
            String text = s.toString();

            if (editText.equals(input_studentID)) {
                studentID = text;
            } else if (editText.equals(input_studentName)) {
                studentName = text;
            } else if (editText.equals(input_contact)) {
                contact = text;
            } else if (editText.equals(input_birthday)) {
                birthday = text;
            }
        }
    }

    private class ButtonClicker implements View.OnClickListener {
        private Button button;

        public ButtonClicker(Button button) {
            this.button = button;
        }

        @Override
        public void onClick(View v) {
            if (button.equals(btn_add)) {
                studentInfoTable.add(studentID, studentName, birthday, contact, program);
                studentInfoTable.notifyDataSetChanged();
            } else if (button.equals(btn_update)) {
                studentInfoTable.update(studentID, studentName, birthday, contact, program);
                studentInfoTable.notifyDataSetChanged();
            } else if (button.equals(btn_delete)) {
                studentInfoTable.delete(studentID);
                studentInfoTable.notifyDataSetChanged();
            }

            input_studentID.setText("");
            input_studentName.setText("");
            input_birthday.setText("");
            input_contact.setText("");
            spinner_program.setSelection(0);
        }
    }

    private class StudentInfoTable extends BaseAdapter {
        ArrayList<String> studentIDs;
        HashMap<String, String> studentNames;
        HashMap<String, String> birthdays;
        HashMap<String, String> contacts;
        HashMap<String, String> programs;

        public StudentInfoTable() {
            studentIDs = new ArrayList<>();
            studentNames = new HashMap<>();
            birthdays = new HashMap<>();
            contacts = new HashMap<>();
            programs = new HashMap<>();
        }

        @Override
        public int getCount() {
            return studentIDs.size();

        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            if (convertView == null) {
                convertView = getLayoutInflater().inflate(R.layout.custom_listview_items, parent, false);
            }

            TextView txt_studentID = convertView.findViewById(R.id.txt_student_id);
            TextView txt_studentName = convertView.findViewById(R.id.txt_student_name);
            TextView txt_contact = convertView.findViewById(R.id.txt_contact_no);
            TextView txt_program = convertView.findViewById(R.id.txt_program);
            TextView txt_birthday = convertView.findViewById(R.id.txt_birthday);

            String id = studentIDs.get(position);

            txt_studentID.setText(id);
            txt_studentName.setText(studentNames.get(id));
            txt_birthday.setText(birthdays.get(id));
            txt_contact.setText(contacts.get(id));
            txt_program.setText(programs.get(id));

            return convertView;
        }


        public void add(String id, String name, String birthday, String contact, String program) {
            if (studentIDs.size() <= 0) {
                studentIDs.add(id);
                studentNames.put(id, name);
                birthdays.put(studentID, birthday);
                contacts.put(studentID, contact);
                programs.put(studentID, program);
            } else if (!studentIDs.contains(studentID)) {
                studentIDs.add(id);
                studentNames.put(id, name);
                birthdays.put(studentID, birthday);
                contacts.put(studentID, contact);
                programs.put(studentID, program);
            }
        }

        @SuppressLint("NewApi")
        public void update(String id, String name, String birthday, String contact, String program) {
            studentNames.replace(id, name);
            birthdays.replace(id, birthday);
            contacts.replace(id, contact);
            programs.replace(id, program);
        }

        public void delete(String id) {
            studentIDs.remove(id);
            studentNames.remove(id);
            birthdays.remove(id);
            contacts.remove(id);
            programs.remove(id);
        }
    }
}