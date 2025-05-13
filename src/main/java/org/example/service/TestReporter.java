package org.example.service;

import java.text.SimpleDateFormat;
import java.util.Date;

public class TestReporter {

    public static String generateSuccessMessage(long startTime, long endTime, long duration, String logs) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
        String startTimeFormatted = dateFormat.format(new Date(startTime));
        String endTimeFormatted = dateFormat.format(new Date(endTime));

        return String.format(
                "✅ The test was successful!\n%s\n%s - %s (%d ms)\n\n📜 *Logs:*\n```%s```",
                new SimpleDateFormat("MM/dd/yyyy").format(new Date(startTime)),
                startTimeFormatted,
                endTimeFormatted,
                duration,
                logs
        );
    }

    public static String generateFailureMessage(String errorMessage, String logs) {
        return String.format("❌ %s\n📜 *Logs:*\n```%s```", errorMessage, logs);
    }
}
