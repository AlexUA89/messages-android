package com.alexua.messages.ui.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.alexua.messages.R;
import com.alexua.messages.core.database.DBManager;
import com.alexua.messages.core.database.Database;
import com.alexua.messages.core.database.constants.DBConstants;
import com.alexua.messages.core.database.datas.Message;
import com.alexua.messages.core.preferences.SharedPrefHelper;
import com.alexua.messages.core.server.requestapi.ServerRequestAdapter;
import com.alexua.messages.core.server.requestapi.ServerResponse;
import com.alexua.messages.utils.TextWatcherForLazy;
import com.android.volley.Response;
import com.android.volley.VolleyError;

/**
 * Created by AlexUA on 11/17/2015.
 */
public class ChatFragment extends BaseFragment {

    private static final String TAG = ChatFragment.class.getCanonicalName();
    public static final String FRAGMENT_TAG = "CHAT_FRAGMENT";

    public static final String TO_USER_ID = "toUserId";
    public static final String XCOORD = "xcoord";
    public static final String YCOORD = "ycoord";
    public static final String CHAT_GROUP_ID = "chatGroupId";

    Double xcoord = null;
    Double ycoord = null;
    String toUserId = null;
    String chatGroupId = null;

    Button send = null;
    EditText msg = null;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View fragmentView = inflater.inflate(R.layout.fragment_chat, container, false);
        initView(fragmentView);
        initParameters(getArguments());
        return fragmentView;
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
        send = (Button) fragmentView.findViewById(R.id.chat_fragment_send);
        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Message message = new Message("hi " + System.currentTimeMillis(), xcoord, ycoord, toUserId, chatGroupId, SharedPrefHelper.getUserId(""), SharedPrefHelper.getUserName(""), "" + System.currentTimeMillis(), null);
                ServerRequestAdapter.sendMessage(message, sendMessageListener, errorSendMessageListener);
            }
        });

        msg.addTextChangedListener(new TextWatcherForLazy() {
            @Override
            public void afterTextChanged(Editable s) {
                send.setEnabled(!msg.getText().toString().isEmpty());
            }
        });
    }

    com.android.volley.Response.Listener<ServerResponse> sendMessageListener = new Response.Listener<ServerResponse>() {
        @Override
        public void onResponse(ServerResponse response) {
            Database db = DBManager.getDatabase();
            long messageId = DBConstants.DEFAULT_ROW_ID;
            if (db.open()) {
                messageId = db.insertNewDBStorable(new Message(response.getData()));
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
