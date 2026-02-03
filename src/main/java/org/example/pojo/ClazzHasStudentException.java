package org.example.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
//@AllArgsConstructor
public class ClazzHasStudentException extends RuntimeException{
//    public ClazzHasStudentException() {
//        super();
//    }
//
    public ClazzHasStudentException(String message) {
        super(message);
    }
}
