package com.tasia.tong.command.handle;

import com.tasia.tong.command.command.BaseCommand;

public interface ICommandHandle {

    /**
     * 处理指令
     *
     * @param command
     */
    void handle(BaseCommand command);
}
