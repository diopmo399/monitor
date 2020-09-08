package africa.atps.monitordata.exceptions;

import lombok.*;

public class CustomException extends Exception {
    public CustomException(String message){
        super(message);
    }
}
