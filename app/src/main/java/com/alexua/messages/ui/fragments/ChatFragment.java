package com.alexua.messages.ui.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import com.alexua.messages.R;
import com.alexua.messages.core.database.DBManager;
import com.alexua.messages.core.database.Database;
import com.alexua.messages.core.database.constants.DBConstants;
import com.alexua.messages.core.database.datas.Message;
import com.alexua.messages.core.server.dto.MessageDto;
import com.alexua.messages.core.server.dto.SendingMessageResponseDto;
import com.alexua.messages.core.server.socketapi.SocketHelper;
import com.alexua.messages.ui.base.BaseTextWatcher;
import com.android.volley.Response;
import com.android.volley.VolleyError;

import java.util.ArrayList;

public class ChatFragment extends Fragment {

    private static final String TAG = ChatFragment.class.getCanonicalName();
    public static final String FRAGMENT_TAG = "CHAT_FRAGMENT";

    public static final String TO_USER_ID = "toUserId";
    public static final String XCOORD = "xcoord";
    public static final String YCOORD = "ycoord";
    public static final String CHAT_GROUP_ID = "chatGroupId";
    private static final String LIST_KEY = "LIST_KEY";

    Double xcoord = null;
    Double ycoord = null;
    String toUserId = null;
    String chatGroupId = null;

    Button send = null;
    ArrayList<String> messagesArray = new ArrayList<>();
    ListView messagesList;
    EditText msg = null;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View fragmentView = inflater.inflate(R.layout.chat_fragment, container, false);
        if (savedInstanceState != null && savedInstanceState.getStringArrayList(LIST_KEY) != null) {
            messagesArray = savedInstanceState.getStringArrayList(LIST_KEY);
        }
        initView(fragmentView);
        initParameters(getArguments());
        return fragmentView;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putStringArrayList(LIST_KEY, messagesArray);
        super.onSaveInstanceState(outState);
    }

    private void initParameters(Bundle args) {
        ycoord = args.getDouble(YCOORD, Double.NaN);
        xcoord = args.getDouble(XCOORD, Double.NaN);
        toUserId = args.getString(TO_USER_ID, null);
        chatGroupId = args.getString(CHAT_GROUP_ID, null);
        if ((xcoord.isNaN() || ycoord.isNaN()) && toUserId == null && chatGroupId == null) {
            throw new IllegalArgumentException("You didn't set parameters for fragment");
        }
    }


    private void initView(View fragmentView) {
        msg = (EditText) fragmentView.findViewById(R.id.chat_fragment_msg);
        messagesList = (ListView) fragmentView.findViewById(R.id.listView);
        messagesList.setAdapter(new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_item, messagesArray));
        final Fragment fr = this;

        send = (Button) fragmentView.findViewById(R.id.chat_fragment_send);
        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SocketHelper.sendMessage(new MessageDto(), new SocketHelper.MessageHandler() {
                    @Override
                    public void receiveMessage(final String message) {
                        fr.getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                messagesArray.add(message);
                                messagesList.setAdapter(new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_item, messagesArray));
                            }
                        });
                    }
                });
            }
        });

        msg.addTextChangedListener(new BaseTextWatcher() {
            @Override
            public void afterTextChanged(Editable s) {
                send.setEnabled(!msg.getText().toString().isEmpty());
            }
        });
    }

    com.android.volley.Response.Listener<SendingMessageResponseDto> sendMessageListener = new Response.Listener<SendingMessageResponseDto>() {
        @Override
        public void onResponse(SendingMessageResponseDto response) {
            Database db = DBManager.getDatabase();
            long messageId = DBConstants.DEFAULT_ROW_ID;
            if (db.open()) {
                //TODO change
//                messageId = db.insertNewDBStorable(new Message(response.getData()));
            }
            Message msg = (Message) db.selectDBStorableByTypeAndId(Message.TypeID, messageId);
            db.close();
            System.out.println("Have sent");
        }
    };

    Response.ErrorListener errorSendMessageListener = new Response.ErrorListener() {
        @Override
        public void onErrorResponse(VolleyError error) {
            System.out.println("Error");
        }
    };

}
