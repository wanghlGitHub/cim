package com.edi.im.common.exception;


import com.edi.im.common.enums.StatusEnum;

/**
 * Function:
 *
 * @author crossoverJie
 *         Date: 2018/8/25 15:26
 * @since JDK 1.8
 */
public class IMException extends GenericException {


    public IMException(String errorCode, String errorMessage) {
        super(errorMessage);
        this.errorCode = errorCode;
        this.errorMessage = errorMessage;
    }

    public IMException(Exception e, String errorCode, String errorMessage) {
        super(e, errorMessage);
        this.errorCode = errorCode;
        this.errorMessage = errorMessage;
    }

    public IMException(String message) {
        super(message);
        this.errorMessage = message;
    }

    public IMException(StatusEnum statusEnum) {
        super(statusEnum.getMessage());
        this.errorMessage = statusEnum.message();
        this.errorCode = statusEnum.getCode();
    }

    public IMException(StatusEnum statusEnum, String message) {
        super(message);
        this.errorMessage = message;
        this.errorCode = statusEnum.getCode();
    }

    public IMException(Exception oriEx) {
        super(oriEx);
    }

    public IMException(Throwable oriEx) {
        super(oriEx);
    }

    public IMException(String message, Exception oriEx) {
        super(message, oriEx);
        this.errorMessage = message;
    }

    public IMException(String message, Throwable oriEx) {
        super(message, oriEx);
        this.errorMessage = message;
    }


    public static boolean isResetByPeer(String msg) {
        if ("Connection reset by peer".equals(msg)) {
            return true;
        }
        return false;
    }

}
