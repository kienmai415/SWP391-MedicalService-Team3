/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ultil;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
 *
 * @author BB-MT
 */
public class Validation {

    public LocalDate formatDate(LocalDate date) {
        return LocalDate.parse(date.format(DateTimeFormatter.ofPattern("dd-MM-yyyy")));
    }
}
