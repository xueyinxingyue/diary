package com.app.diary.ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.app.diary.R;

import com.app.diary.utils.DeepSeekClient;

public class ChatFragment extends Fragment {

    private TextView tvChatHistory;
    private EditText etMessage;
    private Button btnSend;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_chat, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        tvChatHistory = view.findViewById(R.id.tv_chat_history);
        etMessage = view.findViewById(R.id.et_message);
        btnSend = view.findViewById(R.id.btn_send);

        btnSend.setOnClickListener(v -> sendMessage());
    }

    private void sendMessage() {
        String userInput = etMessage.getText().toString().trim();
        if (userInput.isEmpty()) return;

        // 添加用户消息到聊天历史
        appendToChatHistory("你: " + userInput);
        etMessage.setText("");

        // 在后台线程调用API
        new Thread(() -> {
            String aiResponse = DeepSeekClient.sendMessage(userInput);

            // 回到主线程更新UI
            requireActivity().runOnUiThread(() -> {
                appendToChatHistory("小豹: " + aiResponse);
            });
        }).start();
    }

    private void appendToChatHistory(String message) {
        String currentHistory = tvChatHistory.getText().toString();
        tvChatHistory.setText(currentHistory + "\n\n" + message);
    }
}