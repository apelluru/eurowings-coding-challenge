package de.eurowings.newsletter.vo;

import lombok.Data;

/**
 * @author Ashok Pelluru
 */
@Data
public class Error {

    private String code;
    private String message;

    public Error(String code, String message) {
        this.code = code;
        this.message = message;
    }
}
