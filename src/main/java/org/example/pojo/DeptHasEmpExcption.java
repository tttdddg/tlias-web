package org.example.pojo;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class DeptHasEmpExcption extends RuntimeException {
    public DeptHasEmpExcption(String message) {
        super(message);
    }
}
