package com.handhika.phonebook.model;

import com.handhika.phonebook.exception.CommonException;
import com.handhika.phonebook.exception.ExceptionInfo;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;
import org.springframework.http.HttpStatus;

@Getter
@Setter
@ToString
@Accessors(chain = true)
public class ResponseInfo {
    private HttpStatus httpStatus;
    private Object body;

    /**
     * set success with data
     *
     * @param data {@link Object} (generic)
     */
    public void setSuccess(Object data) {
        this.httpStatus = HttpStatus.OK;
        this.body = data;
    }

    /**
     * set http CREATED
     *
     * @param data {@link Object} (generic)
     */
    public void setCreated(Object data) {
        this.httpStatus = HttpStatus.CREATED;
        this.body = data;
    }

    /**
     * set http NO_CONTENT
     */
    public void setNoContent() {
        this.httpStatus = HttpStatus.NO_CONTENT;
        this.body = null;
    }

    /**
     * set response based on common exception
     *
     * @param commonException {@link CommonException}
     */
    public void setCommonException(CommonException commonException) {
        this.httpStatus = commonException.getHttpStatus();
        this.body = new ExceptionInfo(commonException.getCode(), commonException.getMessage());
    }


    /**
     * set exception using error message
     *
     * @param errorDescription error message
     */
    public void setException(String errorDescription) {
        setException(new Exception(errorDescription));
    }

    /**
     * set exception using java exception
     *
     * @param e {@link Exception}
     */
    public void setException(Exception e) {
        if (e instanceof CommonException) {
            setCommonException((CommonException) e);
        } else {
            setCommonException(new CommonException(e));
        }
    }
}
