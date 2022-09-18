package com.tasia.tong.exceptions.collector;

import java.util.ArrayList;
import java.util.List;
import lombok.Data;

@Data
public class ExceptionMessageCollector {

    private List<String> messageList;

    public ExceptionMessageCollector() {
        messageList = new ArrayList<>();
    }

    public void addExceptionMessage(String message) {
        this.messageList.add(message);
    }

    public boolean hasException(String message) {
        return this.messageList.isEmpty();
    }

    @Override
    public String toString() {
        return String.join(",", messageList);
    }
}
