package lk.ijse.evosellbackend.util;

import java.util.UUID;

public class UtilProcess {
    public static String generateID() {
        return UUID.randomUUID().toString();
    }
}
