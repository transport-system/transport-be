package com.transport.transport.scheduling;

import lombok.*;

import java.util.TimerTask;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Task extends TimerTask {
    private Runnable task;

    private Long id;

    @Override
    public void run() {
        task.run();
    }
}
